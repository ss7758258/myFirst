package com.yeting.web.mapper.ext;

import com.yeting.web.bo.f20.F10Bo;
import com.yeting.web.bo.f20.F11Bo;
import com.yeting.web.bo.f20.F12Bo;
import com.yeting.web.bo.f20.F13Bo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ArticleExtMapper {

    @Select({
            "select art_id as feedId, path as musicUrl, title, t1.create_time as createTime,",
            " preview as imgSrc, forward_num as shareCount, favorite_num as collectCount,",
            " praise_num as likeCount, view_num as viewCount, preview, background_url as backgroundUrl, comment_url as commentUrl",
            " from article as t1",
            " left join sys_file as t2 on voice_file_id=fid",
            " where art_id = #{0}"
    })
    F10Bo selectArtDetailByArtId(Long artId);

    @Select({
            "select comm_id as commentId, avatar as userHead, nick_name as userName, t1.create_time as createTime, content, content_url as contentUrl",
            " from comment as t1",
            " left join weixin_user as t2 on t1.create_open_id=t2.open_id",
            " where art_id = #{0} and top = 1",
            " order by t1.create_time DESC",
            " limit #{1}"
    })
    List<F11Bo> selectTopCommnetByArtId(Long artId, Integer pageSize);

    @Select({
            "select comm_id as commentId, avatar as userHead, case anonymous when 0 then nick_name when 1 then '匿名' end as userName,",
            " t1.create_time as createTime, content, praise_num as likeCount",
            " from comment as t1",
            " left join weixin_user as t2 on t1.create_open_id=t2.open_id",
            " where art_id = #{0}",
            " order by praise_num DESC",
            " limit #{1}"
    })
    List<F12Bo> selectHotCommnetByArtId(Long artId, Integer pageSize);

    @Select({
            "select comm_id as commentId, avatar as userHead, case anonymous when 0 then nick_name when 1 then '匿名' end as userName,",
            " t1.create_time as createTime, content, content_url as contentUrl, praise_num as likeCount",
            " from comment as t1",
            " left join weixin_user as t2 on t1.create_open_id=t2.open_id",
            " where art_id = #{0}",
            " order by t1.updated_time DESC",
            " limit #{1}"
    })
    List<F13Bo> selectNewCommnetByArtId(Long artId, Integer pageSize);

    @Update({
            "update article set praise_num=praise_num+1 where art_id = #{0}"
    })
    Integer updateArtLike(Long aLong);

    @Update({
            "update article set favorite_num=favorite_num+1 where art_id = #{0}"
    })
    void updateArtCollectionByArtId(Long aLong);

    @Update({
            "update article set forward_num=forward_num+1 where art_id = #{0}"
    })
    Integer updateArtShare(Long aLong);

    @Select({
            "select count(1)",
            " from weixin_user_art_like",
            " where art_id = #{0} and open_id = #{1}"
    })
    Integer selectArtLikeByArtIdAndUserId(Long artId, String openId);

    @Select({
            "select count(1)",
            " from weixin_user_comment_like",
            " where art_id = #{0} and open_id = #{2} and comment_id = #{1}"
    })
    Integer selectCommentLikeByArtIdAndCommentIdAndUserId(Long artId, Long commentId, String openId);

    @Update({
            "update article set view_num=view_num+1 where art_id = #{0}"
    })
    void updateViewNumByArtId(Long artId);

    @Select({
            "select count(1)",
            " from collection",
            " where art_id = #{0} and create_open_id = #{1}"
    })
    Integer selectCollectionByArtIdAndUserId(Long artId, String openId);

    @Update({
            "update article set praise_num=case when praise_num<1 then 0 else praise_num-1 end where art_id = #{0}"
    })
    Integer updateArtUnLike(Long aLong);

    @Update({
            "update article set favorite_num=case when forward_num<1 then 0 else forward_num-1 end where art_id = #{0}"
    })
    void updateArtUnCollectionByArtId(long feedId);

    @Delete({
            "delete from weixin_user_art_like where art_id = #{0} and open_id = #{1}"
    })
    void deleteByArtIdAndUserId(Long artId, String openId);

    @Select({
            "SELECT art_id as artId, title, t1.create_time as createTime, praise_num as praiseNum,",
            " view_num as viewNum, preview, path as backgroundUrl, comment_url as commentUrl,",
            " forward_num as forwardNum, forward_num as favoriteNum",
            " FROM article as t1",
            " left join sys_file as t2 on t1.background_url=t2.fid",
            " order by t1.create_time desc"
    })
    List<com.yeting.web.bo.f10.F10Bo> selectMainPage();

    @Delete({
            "delete from collection where art_id = #{0} and create_open_id = #{1}"
    })
    void deleteByArtIdAndOpenId(long artId, String openId);

    @Select({
            "select comm_id as commentId, avatar as userHead, case anonymous when 0 then nick_name when 1 then '匿名' end as userName,",
            " t1.create_time as createTime, content, content_url as contentUrl, praise_num as likeCount",
            " from comment as t1",
            " left join weixin_user as t2 on t1.create_open_id=t2.open_id",
            " where comm_id = #{0}"
    })
    F13Bo selectNewCommnetById(Long commentId);

    @Select({
            "select count(1)",
            " from collection as t1",
            " where art_id = #{0} and create_open_id = #{1}"
    })
    Integer selectIsCollectByArtIdAndOpenId(Long artId, String openId);

    @Select({
            "select path from sys_file where fid = #{0}"
    })
    String selectUrlByUrl(Long backgroundUrl);

    @Select({
            "select count(1) from article"
    })
    Integer selectArticleNum();

    @Select("SELECT path as backgroundUr" +
            " FROM article as t1" +
            " left join sys_file as t2 on t1.background_url=t2.fid where t1.background_url = #{0}")
    String selectBackUrlByArtId(String backgroundUrl);
}
