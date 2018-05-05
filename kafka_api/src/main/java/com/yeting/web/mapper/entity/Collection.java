package com.yeting.web.mapper.entity;

import com.yeting.framework.common.base.BasicBean;
import javax.persistence.*;

public class Collection extends BasicBean {
    /**
     * 文章id
     */
    @Id
    private Long id;

    /**
     * 文章id
     */
    @Column(name = "art_id")
    private Long artId;

    @Column(name = "create_open_id")
    private String createOpenId;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "updated_open_id")
    private String updatedOpenId;

    @Column(name = "updated_time")
    private String updatedTime;

    public Collection(Long id, Long artId, String createOpenId, String createTime, String updatedOpenId, String updatedTime) {
        this.id = id;
        this.artId = artId;
        this.createOpenId = createOpenId;
        this.createTime = createTime;
        this.updatedOpenId = updatedOpenId;
        this.updatedTime = updatedTime;
    }

    public Collection() {
        super();
    }

    /**
     * 获取文章id
     *
     * @return id - 文章id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置文章id
     *
     * @param id 文章id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取文章id
     *
     * @return art_id - 文章id
     */
    public Long getArtId() {
        return artId;
    }

    /**
     * 设置文章id
     *
     * @param artId 文章id
     */
    public void setArtId(Long artId) {
        this.artId = artId;
    }

    /**
     * @return create_open_id
     */
    public String getCreateOpenId() {
        return createOpenId;
    }

    /**
     * @param createOpenId
     */
    public void setCreateOpenId(String createOpenId) {
        this.createOpenId = createOpenId == null ? null : createOpenId.trim();
    }

    /**
     * @return create_time
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    /**
     * @return updated_open_id
     */
    public String getUpdatedOpenId() {
        return updatedOpenId;
    }

    /**
     * @param updatedOpenId
     */
    public void setUpdatedOpenId(String updatedOpenId) {
        this.updatedOpenId = updatedOpenId == null ? null : updatedOpenId.trim();
    }

    /**
     * @return updated_time
     */
    public String getUpdatedTime() {
        return updatedTime;
    }

    /**
     * @param updatedTime
     */
    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime == null ? null : updatedTime.trim();
    }
}