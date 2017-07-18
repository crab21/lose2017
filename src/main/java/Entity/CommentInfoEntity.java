package Entity;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by k on 17-7-6.
 */
@Component
public class CommentInfoEntity {
    private int id;
    private String lcommentInfo;
    private int lid;
    private String lctime;
    private String des_info;

    public CommentInfoEntity() {
    }

    public CommentInfoEntity(int id, String lcommentInfo, int lid, String lctime, String des_info) {
        this.id = id;
        this.lcommentInfo = lcommentInfo;
        this.lid = lid;
        this.lctime = lctime;
        this.des_info = des_info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLcommentInfo() {
        return lcommentInfo;
    }

    public void setLcommentInfo(String lcommentInfo) {
        this.lcommentInfo = lcommentInfo;
    }

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public String getLctime() {
        return lctime;
    }

    public void setLctime(String lctime) {
        this.lctime = lctime;
    }

    public String getDes_info() {
        return des_info;
    }

    public void setDes_info(String des_info) {
        this.des_info = des_info;
    }
}
