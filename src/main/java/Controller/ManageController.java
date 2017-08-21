package Controller;

import Service.ManageService;
import com.google.gson.Gson;
import org.apache.ibatis.annotations.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by k on 8/6/17.
 */
@Controller
@Scope("prototype")
@RequestMapping("manage/")
public class ManageController {
    @Autowired
    private ManageService manageService;

    @RequestMapping("goodsConfirm")
    public String goodsConfirm(@RequestParam("flag") String flag, @RequestParam("id") int id, HttpServletResponse response) {
//       标记  是认领还是还原未认领状态
        System.out.println(flag+"------"+id);
        int commentFlag = 0;
        if (flag != null && flag != "") {
            if (flag.equals("confirm_yes")) {
                commentFlag = 2;
            }
            if (flag.equals("confirm_no")) {
                commentFlag = 0;
            }
        } else {
            commentFlag = 3;
        }

        if (commentFlag == 3) {
            setResponseInfo(response, "no");
        } else {
            Map map = new HashMap();
            map.put("flag", commentFlag);
            map.put("id", id);

            manageService.goodsConfirm(map);
            setResponseInfo(response, "no");
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
