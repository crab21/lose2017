package Service;

import Entity.CommentInfoEntity;
import Entity.LoseInfoEntity;
import Entity.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by k on 17-7-6.
 */

public interface LoseInfoService {

    public List findAllInfo(Page page);

    public void addLoseInfo(LoseInfoEntity loseInfoEntity);

    public Map findLoseOne(int id);

    Map addCommentInfo(CommentInfoEntity commentInfoEntity);

    void addCommentCount(int id,int commentCount);
}
