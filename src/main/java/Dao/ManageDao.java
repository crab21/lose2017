package Dao;

import java.util.Map;

/**
 * Created by k on 8/6/17.
 */
public interface ManageDao {
    void goodsConfirm(Map flag);
    void changeWeiXinAppScret(String appScret);
    String getWeiXinAppScret();
}
