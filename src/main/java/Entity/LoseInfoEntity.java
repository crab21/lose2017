package Entity;

import org.springframework.stereotype.Component;

/**
 * Created by k on 17-7-6.
 */
@Component
public class LoseInfoEntity {
    private int id;
    private String ltitle;
    private int ltype;
    private String limg;
    private String linfo;
    private String ltime;
    private int lcomment;
    private String location;

    public LoseInfoEntity() {
    }

    public LoseInfoEntity(int id, String ltitle, int ltype, String limg, String linfo, String ltime, int lcomment, String location) {
        this.id = id;
        this.ltitle = ltitle;
        this.ltype = ltype;
        this.limg = limg;
        this.linfo = linfo;
        this.ltime = ltime;
        this.lcomment = lcomment;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLtitle() {
        return ltitle;
    }

    public void setLtitle(String ltitle) {
        this.ltitle = ltitle;
    }

    public int getLtype() {
        return ltype;
    }

    public void setLtype(int ltype) {
        this.ltype = ltype;
    }

    public String getLimg() {
        return limg;
    }

    public void setLimg(String limg) {
        this.limg = limg;
    }

    public String getLinfo() {
        return linfo;
    }

    public void setLinfo(String linfo) {
        this.linfo = linfo;
    }

    public String getLtime() {
        return ltime;
    }

    public void setLtime(String ltime) {
        this.ltime = ltime;
    }

    public int getLcomment() {
        return lcomment;
    }

    public void setLcomment(int lcomment) {
        this.lcomment = lcomment;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}