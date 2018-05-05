package com.yeting.web.mapper.entity;

import com.yeting.framework.common.base.BasicBean;
import javax.persistence.*;

@Table(name = "weixin_user_collection")
public class WeixinUserCollection extends BasicBean {
    @Id
    @Column(name = "user_collection_id")
    private Long userCollectionId;

    @Column(name = "art_id")
    private Long artId;

    @Column(name = "open_id")
    private String openId;

    public WeixinUserCollection(Long userCollectionId, Long artId, String openId) {
        this.userCollectionId = userCollectionId;
        this.artId = artId;
        this.openId = openId;
    }

    public WeixinUserCollection() {
        super();
    }

    /**
     * @return user_collection_id
     */
    public Long getUserCollectionId() {
        return userCollectionId;
    }

    /**
     * @param userCollectionId
     */
    public void setUserCollectionId(Long userCollectionId) {
        this.userCollectionId = userCollectionId;
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