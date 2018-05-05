package com.yeting.web.mapper.entity;

import com.yeting.framework.common.base.BasicBean;
import javax.persistence.*;

@Table(name = "my_collection_view")
public class MyCollectionView extends BasicBean {
    /**
     * 文章id
     */
    @Column(name = "art_id")
    private Long artId;

    /**
     * 文章id
     */
    private Long id;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "create_open_id")
    private String createOpenId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    public MyCollectionView(Long artId, Long id, String createTime, String createOpenId, String title, String content) {
        this.artId = artId;
        this.id = id;
        this.createTime = createTime;
        this.createOpenId = createOpenId;
        this.title = title;
        this.content = content;
    }

    public MyCollectionView() {
        super();
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
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取内容
     *
     * @return content - 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}