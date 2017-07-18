package Controller;

import Entity.CommentInfoEntity;
import Entity.LoseInfoEntity;
import Entity.Page;
import Service.Componment.ProgressEntity;
import Service.LoseInfoService;
import Service.LoseInfoServiceImp;
import com.google.gson.Gson;
import org.apache.tools.ant.taskdefs.Sleep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
public class LoseInfo {


    @Autowired
    private LoseInfoService loseInfoService;

    //展示主页信息
    @RequestMapping("loseAllInfo")
    public String showInfo(@ModelAttribute("page") Page page, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(page.getPage() + "--------" + page.getPageCount());
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
                                  String ltype, String location, String linfo, MultipartFile file, Model model) {
        System.out.println(ltitle + "-" + ltype + "-" + location + "-" + linfo + "--" + file);
        if (ltitle != null && ltype != null && location != null && linfo != null) {
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
//        给视图模型的前端页面添加数据
            loseInfoEntity.setLtime(new Date().toLocaleString().toString());
            model.addAttribute("loseInfoEntity", loseInfoEntity);


        }

    }


    @RequestMapping(value = "subs")
    public String sub(HttpServletRequest request, HttpServletResponse response, Model model) {
        String filename = "";
        MultipartFile file = (MultipartFile) model.asMap().get("file");
//    上传文件,返回文件名
        if (file != null) {
            filename = saveFile(file, request);
        }
        LoseInfoEntity loseInfoEntity = (LoseInfoEntity) model.asMap().get("loseInfoEntity");
        if (filename != "") {
            loseInfoEntity.setLimg(filename);
        } else {
            loseInfoEntity.setLimg("123.jpg");
        }
        loseInfoService.addLoseInfo(loseInfoEntity);


        setResponseInfo(response, "ok");
        return null;
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

    //上传文件  单独的方法
    public String saveFile(MultipartFile file, HttpServletRequest request) {
        if (!file.isEmpty()) {
            String path = request.getServletContext().getRealPath("/uploadFile/");
            String filename = file.getOriginalFilename();

            String ftype = filename.substring(filename.lastIndexOf("."), filename.length()).toLowerCase();
            filename = new Date().getTime() + "" + filename.substring(0, filename.lastIndexOf(".")) + ftype;
            File filepath = new File(path, filename);
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            try {
                file.transferTo(new File(path + File.separator + filename));
                return filename;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @RequestMapping("addComment")
    public String addComment(@ModelAttribute("commentInfoEntity") CommentInfoEntity commentInfoEntity,
                             HttpServletResponse response) {

        commentInfoEntity.setLctime(new Date().toLocaleString());
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
}