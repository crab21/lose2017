package Dao;

import Entity.CommentInfoEntity;
import Entity.LoseInfoEntity;
import Entity.Page;
import Entity.SearchEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by k on 17-7-6.
 */
public interface LoseInfoDao {
    public List showInfo(Page page);

    public void insertLoseInfo(LoseInfoEntity loseInfoEntity);

    Map findLoseOneInfo(int id);

    Map insertCommentInfo(CommentInfoEntity commentInfoEntity);

    void updateCommentCount(int id,int commentCount);

    List selectSearchInfo(SearchEntity searchEntity);
}
