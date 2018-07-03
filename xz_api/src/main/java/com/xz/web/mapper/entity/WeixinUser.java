package com.xz.web.mapper.entity;

import com.xz.framework.common.base.BasicBean;
import javax.persistence.*;

@Table(name = "weixin_user")
public class WeixinUser extends BasicBean {
    /**
     * 微信用户id
     */
    @Id
    private Long id;

    /**
     * @2-星座id
     */
    @Column(name = "constellation_id")
    private Long constellationId;

    /**
     * @1-微信openId
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * @1-真实姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * @1-昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * @1-电话号码
     */
    @Column(name = "phone_no")
    private String phoneNo;

    /**
     * @1-是否可用 是 否
     */
    @Column(name = "is_disabled")
    private String isDisabled;

    /**
     * @1-头像
     */
    @Column(name = "head_image")
    private String headImage;

    /**
     * @1-性别男 女
     */
    private String gender;

    /**
     * @1-密码
     */
    private String passwd;

    /**
     * @1-地址
     */
    private String address;

    /**
     * @9-创建时间-DATETIME
     */
    @Column(name = "create_timestamp")
    private String createTimestamp;

    /**
     * @9-更新时间-DATETIME
     */
    @Column(name = "update_timestamp")
    private String updateTimestamp;

    public WeixinUser(Long id, Long constellationId, String openId, String userName, String nickName, String phoneNo, String isDisabled, String headImage, String gender, String passwd, String address, String createTimestamp, String updateTimestamp) {
        this.id = id;
        this.constellationId = constellationId;
        this.openId = openId;
        this.userName = userName;
        this.nickName = nickName;
        this.phoneNo = phoneNo;
        this.isDisabled = isDisabled;
        this.headImage = headImage;
        this.gender = gender;
        this.passwd = passwd;
        this.address = address;
        this.createTimestamp = createTimestamp;
        this.updateTimestamp = updateTimestamp;
    }

    public WeixinUser() {
        super();
    }

    /**
     * 获取微信用户id
     *
     * @return id - 微信用户id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置微信用户id
     *
     * @param id 微信用户id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取@2-星座id
     *
     * @return constellation_id - @2-星座id
     */
    public Long getConstellationId() {
        return constellationId;
    }

    /**
     * 设置@2-星座id
     *
     * @param constellationId @2-星座id
     */
    public void setConstellationId(Long constellationId) {
        this.constellationId = constellationId;
    }

    /**
     * 获取@1-微信openId
     *
     * @return open_id - @1-微信openId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置@1-微信openId
     *
     * @param openId @1-微信openId
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * 获取@1-真实姓名
     *
     * @return user_name - @1-真实姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置@1-真实姓名
     *
     * @param userName @1-真实姓名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取@1-昵称
     *
     * @return nick_name - @1-昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置@1-昵称
     *
     * @param nickName @1-昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * 获取@1-电话号码
     *
     * @return phone_no - @1-电话号码
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * 设置@1-电话号码
     *
     * @param phoneNo @1-电话号码
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo == null ? null : phoneNo.trim();
    }

    /**
     * 获取@1-是否可用 是 否
     *
     * @return is_disabled - @1-是否可用 是 否
     */
    public String getIsDisabled() {
        return isDisabled;
    }

    /**
     * 设置@1-是否可用 是 否
     *
     * @param isDisabled @1-是否可用 是 否
     */
    public void setIsDisabled(String isDisabled) {
        this.isDisabled = isDisabled == null ? null : isDisabled.trim();
    }

    /**
     * 获取@1-头像
     *
     * @return head_image - @1-头像
     */
    public String getHeadImage() {
        return headImage;
    }

    /**
     * 设置@1-头像
     *
     * @param headImage @1-头像
     */
    public void setHeadImage(String headImage) {
        this.headImage = headImage == null ? null : headImage.trim();
    }

    /**
     * 获取@1-性别男 女
     *
     * @return gender - @1-性别男 女
     */
    public String getGender() {
        return gender;
    }

    /**
     * 设置@1-性别男 女
     *
     * @param gender @1-性别男 女
     */
    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    /**
     * 获取@1-密码
     *
     * @return passwd - @1-密码
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * 设置@1-密码
     *
     * @param passwd @1-密码
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    /**
     * 获取@1-地址
     *
     * @return address - @1-地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置@1-地址
     *
     * @param address @1-地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取@9-创建时间-DATETIME
     *
     * @return create_timestamp - @9-创建时间-DATETIME
     */
    public String getCreateTimestamp() {
        return createTimestamp;
    }

    /**
     * 设置@9-创建时间-DATETIME
     *
     * @param createTimestamp @9-创建时间-DATETIME
     */
    public void setCreateTimestamp(String createTimestamp) {
        this.createTimestamp = createTimestamp == null ? null : createTimestamp.trim();
    }

    /**
     * 获取@9-更新时间-DATETIME
     *
     * @return update_timestamp - @9-更新时间-DATETIME
     */
    public String getUpdateTimestamp() {
        return updateTimestamp;
    }

    /**
     * 设置@9-更新时间-DATETIME
     *
     * @param updateTimestamp @9-更新时间-DATETIME
     */
    public void setUpdateTimestamp(String updateTimestamp) {
        this.updateTimestamp = updateTimestamp == null ? null : updateTimestamp.trim();
    }
}