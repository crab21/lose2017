package Dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by k on 8/6/17.
 */
@Repository
public class ManageDaoImp implements ManageDao {
    @Resource(name = "sqlSession")
    private SqlSession sqlSession;

    public void goodsConfirm(Map map) {

        sqlSession.update("goodsConfirm", map);
    }

    public void changeWeiXinAppScret(String appScret) {
        sqlSession.update("updateWeiXinAppScret", appScret);
    }

    public String getWeiXinAppScret() {
        String getWeiXinAppScret = sqlSession.selectOne("selectWeiXinAppScret");
        return getWeiXinAppScret;
    }
}
