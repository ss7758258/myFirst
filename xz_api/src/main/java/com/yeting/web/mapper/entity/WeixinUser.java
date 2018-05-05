package com.yeting.web.mapper.entity;

import com.yeting.framework.common.base.BasicBean;
import javax.persistence.*;

@Table(name = "weixin_user")
public class WeixinUser extends BasicBean {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "nick_name")
    private String nickName;

    /**
     * 电话号码
     */
    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "update_time")
    private String updateTime;

    /**
     * 是否可用 是 否
     */
    @Column(name = "is_disabled")
    private String isDisabled;

    @Column(name = "head_image")
    private String headImage;

    /**
     * 男 女
     */
    private String gender;

    private String passwd;

    private String address;

    public WeixinUser(Long userId, String openId, String userName, String nickName, String phoneNo, String createTime, String updateTime, String isDisabled, String headImage, String gender, String passwd, String address) {
        this.userId = userId;
        this.openId = openId;
        this.userName = userName;
        this.nickName = nickName;
        this.phoneNo = phoneNo;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDisabled = isDisabled;
        this.headImage = headImage;
        this.gender = gender;
        this.passwd = passwd;
        this.address = address;
    }

    public WeixinUser() {
        super();
    }

    /**
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
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
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * @return nick_name
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * @param nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * 获取电话号码
     *
     * @return phone_no - 电话号码
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * 设置电话号码
     *
     * @param phoneNo 电话号码
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo == null ? null : phoneNo.trim();
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
     * @return update_time
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    /**
     * 获取是否可用 是 否
     *
     * @return is_disabled - 是否可用 是 否
     */
    public String getIsDisabled() {
        return isDisabled;
    }

    /**
     * 设置是否可用 是 否
     *
     * @param isDisabled 是否可用 是 否
     */
    public void setIsDisabled(String isDisabled) {
        this.isDisabled = isDisabled == null ? null : isDisabled.trim();
    }

    /**
     * @return head_image
     */
    public String getHeadImage() {
        return headImage;
    }

    /**
     * @param headImage
     */
    public void setHeadImage(String headImage) {
        this.headImage = headImage == null ? null : headImage.trim();
    }

    /**
     * 获取男 女
     *
     * @return gender - 男 女
     */
    public String getGender() {
        return gender;
    }

    /**
     * 设置男 女
     *
     * @param gender 男 女
     */
    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    /**
     * @return passwd
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * @param passwd
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}