package com.yeting.web.mapper.ext;

import com.yeting.web.bo.f20.F70Bo;
import com.yeting.web.bo.f30.F40Bo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CommentExtMapper {

    @Update({
            "update comment set praise_num=praise_num+1 where comm_id = #{0}"
    })
    Integer updateCommentLike(Long aLong);

    @Select({
            "select comm_id as commentId, avatar as userHead, nick_name as userName, t1.create_time as createTime, content, path as contentUrl,",
            " praise_num as likeCount",
            " from comment as t1",
            " left join weixin_user as t2 on t1.create_open_id=t2.open_id",
            " left join sys_file as t3 on t1.content_url=t3.fid",
            " where art_id = #{0}",
            " order by praise_num DESC"
    })
    List<F70Bo> selectCommentListByArtId(Long artId);

    @Update({
            "update comment set praise_num=case when praise_num<1 then 0 else praise_num-1 end where comm_id = #{0}"
    })
    Integer updateCommentUnLike(Long aLong);

    @Delete({
            "delete from weixin_user_comment_like where comment_id = #{1} and art_id = #{0} and open_id = #{2}"
    })
    void deleteByFeedIdAndCommentId(Long feedId, Long commentId, String openId);

    @Select({
            "select name",
            " from comment as t1",
            " where comm_id = #{0}"
    })
    String selectByPrimaryKey(String s);

    @Select({
            "select comm_id as commId, t1.name, t1.avatar, t1.content, t1.praise_num as praiseNum, t1.anonymous, top,",
            " t1.origin_id as originId, t2.art_id as artId, lon, lat, address, t3.path as commentUrl,",
            " t1.create_open_id as createOpenId, t1.create_time as createTime, t1.updated_open_id as updateOpenId,",
            " t1.updated_time as updateTime, t2.title",
            " from comment as t1",
            " left join article as t2 on t1.art_id=t2.art_id",
            " left join sys_file as t3 on t2.comment_url=t3.fid",
            " where t1.create_open_id = #{0} and t2.art_id is not null",
            " order by t1.updated_time desc"
    })
    List<F40Bo> selectCommentListByOpenId(String openId);

    @Select({
            "select comm_id as commentId, avatar as userHead, nick_name as userName, t1.create_time as createTime, content, path as contentUrl,",
            " praise_num as likeCount",
            " from comment as t1",
            " left join weixin_user as t2 on t1.create_open_id=t2.open_id",
            " left join sys_file as t3 on t1.content_url=t3.fid",
            " where art_id = #{0}",
            " order by t1.create_time DESC",
            " limit #{1},#{2}"
    })
    List<F70Bo> selectNewCommentListByArtId(Long artId, Integer commentPageSize, Integer newCommentPageSize);

    @Select({
            "select count(1) from comment"
    })
    Integer selectTotalComment();
}
