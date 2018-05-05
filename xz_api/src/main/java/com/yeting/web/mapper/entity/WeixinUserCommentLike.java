package com.yeting.web.mapper.entity;

import com.yeting.framework.common.base.BasicBean;
import javax.persistence.*;

@Table(name = "weixin_user_comment_like")
public class WeixinUserCommentLike extends BasicBean {
    @Id
    @Column(name = "user_comment_like_id")
    private Long userCommentLikeId;

    @Column(name = "art_id")
    private Long artId;

    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "open_id")
    private String openId;

    public WeixinUserCommentLike(Long userCommentLikeId, Long artId, Long commentId, String openId) {
        this.userCommentLikeId = userCommentLikeId;
        this.artId = artId;
        this.commentId = commentId;
        this.openId = openId;
    }

    public WeixinUserCommentLike() {
        super();
    }

    /**
     * @return user_comment_like_id
     */
    public Long getUserCommentLikeId() {
        return userCommentLikeId;
    }

    /**
     * @param userCommentLikeId
     */
    public void setUserCommentLikeId(Long userCommentLikeId) {
        this.userCommentLikeId = userCommentLikeId;
    }

    /**
     * @return art_id
     */
    public Long getArtId() {
        return artId;
    }

    /**
     * @param artId
     */
    public void setArtId(Long artId) {
        this.artId = artId;
    }

    /**
     * @return comment_id
     */
    public Long getCommentId() {
        return commentId;
    }

    /**
     * @param commentId
     */
    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    /**
     * @return open_id
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * @param openId
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }
}