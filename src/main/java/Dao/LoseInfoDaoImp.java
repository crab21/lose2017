package Dao;

import Entity.CommentInfoEntity;
import Entity.LoseInfoEntity;
import Entity.Page;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by k on 17-7-6.
 */
@Repository
public class LoseInfoDaoImp implements LoseInfoDao {
    @Autowired
    private SqlSession sqlSession;

    //    主页展示信息
    public List showInfo(Page page) {
        try {
            System.out.println(page.getPage()+"---------"+page.getPageCount());
            page.setPageCount((page.getPage()-1)*page.getPageSize());
            List<LoseInfoEntity> list = sqlSession.selectList("selectAllInfo",page);
            return list;
        } catch (Exception e) {
        }
        return null;
    }

    //添加表单信息
    public void insertLoseInfo(LoseInfoEntity loseInfoEntity) {
        int i = sqlSession.insert("insertLoseInfo", loseInfoEntity);
    }

    //查找某一个具体信息
    public Map findLoseOneInfo(int id) {
        Map map = new HashMap();
        LoseInfoEntity loseInfoEntity = sqlSession.selectOne("selectLoseOneById", id);

        if (loseInfoEntity != null) {
            List listComment = sqlSession.selectList("selectCommentInfo", id);
            map.put("loseInfo", loseInfoEntity);
            map.put("commentInfo", listComment);
        } else {
            map.put("flag", "没有这条信息...");
        }
        return map;
    }


    //    添加评论信息
    public Map insertCommentInfo(CommentInfoEntity commentInfoEntity) {
        int i = sqlSession.insert("insertComment", commentInfoEntity);
        Map map = new HashMap();
        map.put("flag", i);
//        map.put("id",commentInfoEntity.getId());
        return map;
    }

    //    添加评论数
    public void updateCommentCount(int id, int commentCount) {
        Map map = new HashMap();
        map.put("id", id);
        map.put("commentCount", commentCount);
        sqlSession.update("addCommentCount", map);
    }
}
