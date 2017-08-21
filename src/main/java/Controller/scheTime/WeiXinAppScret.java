package Controller.scheTime;

import Service.WeiRequest;
import Dao.ManageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by k on 8/20/17.
 */
@Component
public class WeiXinAppScret {
    @Autowired
    ManageDao manageDao;

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void disk() {
        String access_token = new WeiRequest().access_token();
        if (access_token != null && access_token != "" && access_token != " ") {
            manageDao.changeWeiXinAppScret(access_token);
        }
    }

}
