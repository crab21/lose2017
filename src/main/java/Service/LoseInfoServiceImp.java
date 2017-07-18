package Service;

import Dao.LoseInfoDao;
import Dao.LoseInfoDaoImp;
import Entity.CommentInfoEntity;
import Entity.LoseInfoEntity;
import Entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by k on 17-7-6.
 */
@Service

public class LoseInfoServiceImp implements LoseInfoService {

    @Autowired
    private LoseInfoDao loseInfoDao;

    @Transactional
    public List findAllInfo(Page page) {
        return loseInfoDao.showInfo(page);
    }

    @Transactional
    public void addLoseInfo(LoseInfoEntity loseInfoEntity) {
        loseInfoDao.insertLoseInfo(loseInfoEntity);
    }

    @Transactional
    public Map findLoseOne(int id) {
        return loseInfoDao.findLoseOneInfo(id);
    }

    @Transactional
    public Map addCommentInfo(CommentInfoEntity commentInfoEntity) {
        return loseInfoDao.insertCommentInfo(commentInfoEntity);
    }

    @Transactional
    public void addCommentCount(int id, int commentCount) {
        loseInfoDao.updateCommentCount(id, commentCount);
    }
}
