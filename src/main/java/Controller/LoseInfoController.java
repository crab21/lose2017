package Controller;

import Controller.biz.SaveFileThread;
import Service.WeiRequest;
import Controller.biz.addLoseInfoThread;
import Entity.CommentInfoEntity;
import Entity.LoseInfoEntity;
import Entity.Page;
import Entity.SearchEntity;
import Service.Componment.ProgressEntity;
import Service.LoseInfoService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import wpy.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by k on 17-7-6.
 */
@Controller
@Scope("prototype")
@RequestMapping("page/index")
public class LoseInfoController {

    @Autowired
    private LoseInfoService loseInfoService;


    @Autowired
    WeiRequest weiRequest;

    //微信朋友圈分享
    @RequestMapping("weixinshare")
    public String weixinshare(@RequestParam("urls")String urls, HttpServletResponse response, HttpServletRequest request) {
        Map map = weiRequest.getAllInfo();
        map.put("url", urls);
//        拿到签名
        String signature = sha1Signature(map);
        if (signature != null) {
            map.put("signature", signature);

            setResponseInfo(response, map);
        } else {
            setResponseInfo(response, "no");
        }
        return null;
    }

//sha1加密

    public String sha1Signature(Map map) {
        String signature = "jsapi_ticket=" + map.get("jsapi_ticket") + "&noncestr=" + map.get("noncestr")
                + "&timestamp=" + map.get("timestamp") + "&url=" + map.get("url");
        try {
            //指定sha1算法
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(signature.getBytes());
            //获取字节数组
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString().toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    //展示主页信息
    @RequestMapping("loseAllInfo")
    public String showInfo(@ModelAttribute("page") Page page, HttpServletRequest request, HttpServletResponse response) {
        List list = loseInfoService.findAllInfo(page);

        setResponseInfo(response, list);
        return null;
    }

    //通过id,展示其中一个信息
    @RequestMapping(value = "showOne", method = RequestMethod.POST)
    public String showOne(@RequestParam("id") int id, HttpServletResponse response) {
        Map map = loseInfoService.findLoseOne(id);
        setResponseInfo(response, map);
        return null;
    }


    //    处理前端的页面数据，在此先进行封装成对象
    @ModelAttribute
    public void setLoseInfoEntity(String ltitle,
                                  String ltype, String location, String linfo, String lphone, MultipartFile file, Model model) {
        if (ltitle != null && ltype != null && location != null && linfo != null && lphone != null) {
            if (file != null) {
                if (!file.isEmpty()) {
                    model.addAttribute("file", file);
                }
            } else {
                System.out.println("空的");
            }

            LoseInfoEntity loseInfoEntity = new LoseInfoEntity();

            loseInfoEntity.setLtitle(ltitle);
//        判断类型    设置属于的类型的值  0---失物申报  1---失物上交
            if (ltype.equals("失物申报")) {
                loseInfoEntity.setLtype(0);
            } else {
                loseInfoEntity.setLtype(1);
            }

            loseInfoEntity.setLocation(location);
            loseInfoEntity.setLinfo(linfo);
            loseInfoEntity.setLphone(lphone);
//        给视图模型的前端页面添加数据
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            loseInfoEntity.setLtime(simpleDateFormat.format(new Date()));
            model.addAttribute("loseInfoEntity", loseInfoEntity);


        }

    }

    //表单提交  多线程处理
    @RequestMapping(value = "subs")
    public String sub(HttpServletRequest request, HttpServletResponse response, Model model) {
        String filename = "";
        MultipartFile file = (MultipartFile) model.asMap().get("file");
//    上传文件,返回文件名
        if (file != null) {
            filename = file.getOriginalFilename();
            String ftype = filename.substring(filename.lastIndexOf("."), filename.length()).toLowerCase();
            filename = new Date().getTime() + "" + filename.substring(0, filename.lastIndexOf(".")) + ftype;
      //    filename = new Date().getTime()  +""+ ftype;

            try {
                Runnable run1 = new SaveFileThread(file, filename);
                Thread thread = new Thread(run1);
                thread.start();
            }catch (MaxUploadSizeExceededException ex){
                System.out.println("文件过大");
            }

        }
        Thread thread2 = new Thread(new addLoseInfoThread(model, loseInfoService, filename));
        thread2.start();
        setResponseInfo(response, "ok");
        return null;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleException(Exception e) throws Exception {
        System.out.println("文件过大");
        return new ModelAndView("upload").addObject("msg", "文件太大！");
    }


    @RequestMapping("getProgress")
    public String initCreateInfo(HttpServletRequest request, HttpServletResponse response) {
        ProgressEntity status = (ProgressEntity) request.getSession().getAttribute("status");

        if (status == null) {
            setResponseInfo(response, "no");
            return null;
        } else {
            String pro = status.toString();
            setResponseInfo(response, pro);
        }
        return null;
    }


    @RequestMapping("addComment")
    public String addComment(@ModelAttribute("commentInfoEntity") CommentInfoEntity commentInfoEntity,
                             HttpServletResponse response) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        commentInfoEntity.setLctime(simpleDateFormat.format(new Date()));
        int commentCount = commentInfoEntity.getId();
        commentInfoEntity.setId(0);
//  mapInfo有从后台返回的主键
        Map mapInfo = loseInfoService.addCommentInfo(commentInfoEntity);

        Map map = new HashMap();
        map.put("commentInfo", commentInfoEntity);

        int i = (Integer) mapInfo.get("flag");
        if (i > 0) {
            commentCount++;
            loseInfoService.addCommentCount(commentInfoEntity.getLid(), commentCount);
            map.put("flag", "留言成功");
            setResponseInfo(response, map);
        } else {
            map.put("flag", "留言失败");
            setResponseInfo(response, map);
        }
        return null;
    }


    @RequestMapping("searchs")
    public String searchByType(@ModelAttribute("searchEntity") SearchEntity searchEntity, HttpServletResponse response) {
        List list = loseInfoService.searchInfo(searchEntity);
        setResponseInfo(response, list);
        return null;
    }


    @RequestMapping("deleteOne")
    public String deleteOne(@RequestParam("id") int id, HttpServletResponse response) throws Exception {
        loseInfoService.deleteOne(id);
        Test te = new Test(id);
        te.sends();
        setResponseInfo(response, "ok");
        return null;
    }

    //ajax交互时候 后台的数据处理
    public void setResponseInfo(HttpServletResponse response, Object object) {
        Gson gs = new Gson();
        System.out.println("json:" + gs.toJson(object));
        PrintWriter out = null;
        try {
            //设置页面不缓存
            response.setCharacterEncoding("UTF-8");
            // 拿到响应的头
            out = response.getWriter();
            // 写入数据
            out.print(gs.toJson(object));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }


    @RequestMapping("manage")
    public String  manage(@RequestParam("lusername")String lusername,@RequestParam("lpassword")String lpassword,HttpServletRequest request,HttpServletResponse response){
        if(lusername.equals("admin") && lpassword.equals("s123")) {
            request.getSession().setAttribute("manage_flag", "manage_ok");
            System.out.println("ok");
            return "jump/manage_jump";
        }
        return "manage";
    }

    @RequestMapping("manage_validate")
    public String manage_validate(HttpServletRequest request,HttpServletResponse response){
        String flag = (String) request.getSession().getAttribute("manage_flag");
        if(flag != null){
            if(flag.equals("manage_ok")){
                setResponseInfo(response,"ok");
            }else {
                setResponseInfo(response,"failed");
            }
        }else{
            setResponseInfo(response,"failed");
        }

        return null;
    }
    @RequestMapping("deleteCommentById")
    public String deleteCommentById(@RequestParam("id")int id,HttpServletResponse response){
        loseInfoService.deleteOneComment(id);
        setResponseInfo(response,"ok");
        return null;
    }

}
