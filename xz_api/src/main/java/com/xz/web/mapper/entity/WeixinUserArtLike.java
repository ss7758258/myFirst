package com.xz.web.mapper.entity;

import com.xz.framework.common.base.BasicBean;
import javax.persistence.*;

@Table(name = "weixin_user_art_like")
public class WeixinUserArtLike extends BasicBean {
    @Id
    @Column(name = "user_art_like_id")
    private Long userArtLikeId;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "art_id")
    private Long artId;

    public WeixinUserArtLike(Long userArtLikeId, String openId, Long artId) {
        this.userArtLikeId = userArtLikeId;
        this.openId = openId;
        this.artId = artId;
    }

    public WeixinUserArtLike() {
        super();
    }

    /**
     * @return user_art_like_id
     */
    public Long getUserArtLikeId() {
        return userArtLikeId;
    }

    /**
     * @param userArtLikeId
     */
    public void setUserArtLikeId(Long userArtLikeId) {
        this.userArtLikeId = userArtLikeId;
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
}