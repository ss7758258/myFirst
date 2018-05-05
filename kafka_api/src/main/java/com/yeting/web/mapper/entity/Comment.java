package com.yeting.web.mapper.entity;

import com.yeting.framework.common.base.BasicBean;
import javax.persistence.*;

public class Comment extends BasicBean {
    /**
     * 评论ID
     */
    @Id
    @Column(name = "comm_id")
    private Long commId;

    /**
     * 评论人昵称
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞数
     */
    @Column(name = "praise_num")
    private Integer praiseNum;

    /**
     * 是否匿名 0:否  1:是
     */
    private Integer anonymous;

    /**
     * 是否精选 0:否 1:是
     */
    private Integer top;

    /**
     * 被回复评论ID
     */
    @Column(name = "origin_id")
    private Long originId;

    /**
     * 文章id
     */
    @Column(name = "art_id")
    private Long artId;

    /**
     * 经度
     */
    private Long lon;

    /**
     * 纬度
     */
    private Long lat;

    /**
     * 地址
     */
    private String address;

    @Column(name = "content_url")
    private String contentUrl;

    @Column(name = "create_open_id")
    private String createOpenId;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "updated_open_id")
    private String updatedOpenId;

    @Column(name = "updated_time")
    private String updatedTime;

    public Comment(Long commId, String name, String avatar, String content, Integer praiseNum, Integer anonymous, Integer top, Long originId, Long artId, Long lon, Long lat, String address, String contentUrl, String createOpenId, String createTime, String updatedOpenId, String updatedTime) {
        this.commId = commId;
        this.name = name;
        this.avatar = avatar;
        this.content = content;
        this.praiseNum = praiseNum;
        this.anonymous = anonymous;
        this.top = top;
        this.originId = originId;
        this.artId = artId;
        this.lon = lon;
        this.lat = lat;
        this.address = address;
        this.contentUrl = contentUrl;
        this.createOpenId = createOpenId;
        this.createTime = createTime;
        this.updatedOpenId = updatedOpenId;
        this.updatedTime = updatedTime;
    }

    public Comment() {
        super();
    }

    /**
     * 获取评论ID
     *
     * @return comm_id - 评论ID
     */
    public Long getCommId() {
        return commId;
    }

    /**
     * 设置评论ID
     *
     * @param commId 评论ID
     */
    public void setCommId(Long commId) {
        this.commId = commId;
    }

    /**
     * 获取评论人昵称
     *
     * @return name - 评论人昵称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置评论人昵称
     *
     * @param name 评论人昵称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取头像
     *
     * @return avatar - 头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置头像
     *
     * @param avatar 头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    /**
     * 获取评论内容
     *
     * @return content - 评论内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置评论内容
     *
     * @param content 评论内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取点赞数
     *
     * @return praise_num - 点赞数
     */
    public Integer getPraiseNum() {
        return praiseNum;
    }

    /**
     * 设置点赞数
     *
     * @param praiseNum 点赞数
     */
    public void setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
    }

    /**
     * 获取是否匿名 0:否  1:是
     *
     * @return anonymous - 是否匿名 0:否  1:是
     */
    public Integer getAnonymous() {
        return anonymous;
    }

    /**
     * 设置是否匿名 0:否  1:是
     *
     * @param anonymous 是否匿名 0:否  1:是
     */
    public void setAnonymous(Integer anonymous) {
        this.anonymous = anonymous;
    }

    /**
     * 获取是否精选 0:否 1:是
     *
     * @return top - 是否精选 0:否 1:是
     */
    public Integer getTop() {
        return top;
    }

    /**
     * 设置是否精选 0:否 1:是
     *
     * @param top 是否精选 0:否 1:是
     */
    public void setTop(Integer top) {
        this.top = top;
    }

    /**
     * 获取被回复评论ID
     *
     * @return origin_id - 被回复评论ID
     */
    public Long getOriginId() {
        return originId;
    }

    /**
     * 设置被回复评论ID
     *
     * @param originId 被回复评论ID
     */
    public void setOriginId(Long originId) {
        this.originId = originId;
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
     * 获取经度
     *
     * @return lon - 经度
     */
    public Long getLon() {
        return lon;
    }

    /**
     * 设置经度
     *
     * @param lon 经度
     */
    public void setLon(Long lon) {
        this.lon = lon;
    }

    /**
     * 获取纬度
     *
     * @return lat - 纬度
     */
    public Long getLat() {
        return lat;
    }

    /**
     * 设置纬度
     *
     * @param lat 纬度
     */
    public void setLat(Long lat) {
        this.lat = lat;
    }

    /**
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * @return content_url
     */
    public String getContentUrl() {
        return contentUrl;
    }

    /**
     * @param contentUrl
     */
    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl == null ? null : contentUrl.trim();
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