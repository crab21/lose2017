package Controller;


import Entity.LoseInfoEntity;
import Service.LoseInfoService;
import org.springframework.ui.Model;

/**
 * Created by k on 17-7-29.
 */
public class addLoseInfoThread implements Runnable {
    private Model model;
    private LoseInfoService loseInfoService;

    private String filename;

    public addLoseInfoThread(Model model, LoseInfoService loseInfoService, String filename) {
        this.model = model;
        this.loseInfoService = loseInfoService;
        this.filename = filename;
    }

    public void run() {
        LoseInfoEntity loseInfoEntity = (LoseInfoEntity) model.asMap().get("loseInfoEntity");
        if (filename != "") {
            loseInfoEntity.setLimg(filename);
        } else {
            loseInfoEntity.setLimg("123.jpg");
        }

        loseInfoService.addLoseInfo(loseInfoEntity);
    }
}
