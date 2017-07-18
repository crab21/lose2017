package Service.Componment;

import org.apache.commons.fileupload.ProgressListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * Created by k on 17-7-12.
 */
@Component
public class FileUploadProgressListener implements ProgressListener {
    @Autowired
    private HttpSession session;

    public void setSession(HttpSession session) {
        this.session = session;
        ProgressEntity progress = new ProgressEntity();
        session.setAttribute("status", progress);
    }


    /*
       * pBytesRead 到目前为止读取文件的比特数 pContentLength 文件总大小 pItems 目前正在读取第几个文件
       */
    public void update(long pBytesRead, long pContentLength, int pItems) {
        ProgressEntity status = (ProgressEntity) session.getAttribute("status");
        status.setpBytesRead(pBytesRead);
        status.setpContentLength(pContentLength);
        status.setpItems(pItems);
        session.getAttribute("status");
    }
}
