package Controller.biz;

import Controller.biz.ImgCompress;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by k on 17-7-29.
 */
public class SaveFileThread implements Runnable {
    MultipartFile file;
    String filename;

    public SaveFileThread(MultipartFile file, String filename) {
        this.file = file;
        this.filename = filename;
    }

    public void run() {
        saveFile(file, filename);
    }

    //上传文件  单独的方法
    public String saveFile(MultipartFile file, String filename) {
//        String path = "/home/k/uploadFile";
        String path = "/home/apache-three/webapps/uploadFile";
        if (!file.isEmpty()) {
//            String path = request.getServletContext().getRealPath("/");


            File filepath = new File(path, filename);
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            try {
                file.transferTo(new File(path + File.separator + filename));


                if (file.getSize() > (1024 * 1024 * 1)){
                    try {
                        ImgCompress imgCompress = new ImgCompress(path + "/" + filename);
                        imgCompress.resizeFix(400, 400);
                    } catch (IOException e) {

                    }
                }
                return filename;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return null;
    }
}
