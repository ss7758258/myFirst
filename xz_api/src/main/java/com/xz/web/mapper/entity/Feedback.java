package com.xz.web.mapper.entity;

import com.xz.framework.common.base.BasicBean;
import javax.persistence.*;

public class Feedback extends BasicBean {
    /**
     * 评论ID
     */
    @Id
    private Long id;

    /**
     * 反馈人昵称
     */
    private String name;

    /**
     * 微信号
     */
    @Column(name = "weixin_no")
    private String weixinNo;

    /**
     * 微信昵称
     */
    @Column(name = "weixin_name")
    private String weixinName;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 纬度
     */
    private Long lat;

    /**
     * 经度
     */
    private Long lon;

    /**
     * 地址信息，通过经纬度获取
     */
    private String address;

    @Column(name = "create_open_id")
    private String createOpenId;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "updated_open_id")
    private String updatedOpenId;

    @Column(name = "updated_time")
    private String updatedTime;

    @Column(name = "head_image")
    private String headImage;

    public Feedback(Long id, String name, String weixinNo, String weixinName, String phone, String content, Long lat, Long lon, String address, String createOpenId, String createTime, String updatedOpenId, String updatedTime, String headImage) {
        this.id = id;
        this.name = name;
        this.weixinNo = weixinNo;
        this.weixinName = weixinName;
        this.phone = phone;
        this.content = content;
        this.lat = lat;
        this.lon = lon;
        this.address = address;
        this.createOpenId = createOpenId;
        this.createTime = createTime;
        this.updatedOpenId = updatedOpenId;
        this.updatedTime = updatedTime;
        this.headImage = headImage;
    }

    public Feedback() {
        super();
    }

    /**
     * 获取评论ID
     *
     * @return id - 评论ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置评论ID
     *
     * @param id 评论ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取反馈人昵称
     *
     * @return name - 反馈人昵称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置反馈人昵称
     *
     * @param name 反馈人昵称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取微信号
     *
     * @return weixin_no - 微信号
     */
    public String getWeixinNo() {
        return weixinNo;
    }

    /**
     * 设置微信号
     *
     * @param weixinNo 微信号
     */
    public void setWeixinNo(String weixinNo) {
        this.weixinNo = weixinNo == null ? null : weixinNo.trim();
    }

    /**
     * 获取微信昵称
     *
     * @return weixin_name - 微信昵称
     */
    public String getWeixinName() {
        return weixinName;
    }

    /**
     * 设置微信昵称
     *
     * @param weixinName 微信昵称
     */
    public void setWeixinName(String weixinName) {
        this.weixinName = weixinName == null ? null : weixinName.trim();
    }

    /**
     * 获取手机号码
     *
     * @return phone - 手机号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号码
     *
     * @param phone 手机号码
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取反馈内容
     *
     * @return content - 反馈内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置反馈内容
     *
     * @param content 反馈内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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
     * 获取地址信息，通过经纬度获取
     *
     * @return address - 地址信息，通过经纬度获取
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址信息，通过经纬度获取
     *
     * @param address 地址信息，通过经纬度获取
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }
}