package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by k on 17-7-23.
 */
@Controller
public class Index {
    @RequestMapping("index")
    public String index(){
        return "index";
    }
}
