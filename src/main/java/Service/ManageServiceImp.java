package Service;

import Dao.ManageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by k on 8/6/17.
 */
@Service

public class ManageServiceImp implements ManageService {

    @Autowired
    private ManageDao manageDao;


    @Transactional
    public void goodsConfirm(Map flag) {
        manageDao.goodsConfirm(flag);
    }
}
