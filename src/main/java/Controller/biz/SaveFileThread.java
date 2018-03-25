package Controller.biz;

import Controller.biz.ImgCompress;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.log4j.Logger;
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

    Logger logger =  Logger.getLogger(this.getClass());
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
        String path = "/home/apa-3/webapps/uploadFile";

        if (!file.isEmpty()) {
//            String path = request.getServletContext().getRealPath("/");


            File filepath = new File(path, filename);
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            try {
                file.transferTo(new File(path + File.separator + filename));


                /*if (file.getSize() > (1024 * 1024 * 1)){
                    try {
                        ImgCompress imgCompress = new ImgCompress(path + "/" + filename);
                        imgCompress.resizeFix(400, 400);
                    } catch (IOException e) {

                    }
                }*/
/*                Thumbnails.of(path+File.separator+filename)
                        .scale(0.6)
                        .toFile(path+File.separator+filename);
                System.out.println("----------------------------------------------------------------");*/
                return filename;
            } catch (IOException e) {
                    logger.info(e.getMessage()+this.getClass().getName()+"------"+new Date().toString());
            }
        }


        return null;
    }
}
