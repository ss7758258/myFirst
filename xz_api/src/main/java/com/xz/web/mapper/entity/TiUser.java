package com.xz.web.mapper.entity;

import com.xz.framework.common.base.BasicBean;

import java.util.Date;
import javax.persistence.*;

@Table(name = "ti_user")
public class TiUser extends BasicBean {
    /**
     * 员工id
     */
    @Id
    @Column(name = "user_id")
    private Long userId;

    /**
     * 员工工号
     */
    @Column(name = "user_code")
    private String userCode;

    /**
     * 员工姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 员工职位名称
     */
    @Column(name = "position_name")
    private String positionName;

    /**
     * 员工归属网点
     */
    @Column(name = "dept_code")
    private String deptCode;

    /**
     * 员工联系方式
     */
    @Column(name = "tel_phone")
    private String telPhone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    private String passwd;

    public TiUser(Long userId, String userCode, String userName, String positionName, String deptCode, String telPhone, String email, String remark, Date createTime, Date updateTime, String passwd) {
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.positionName = positionName;
        this.deptCode = deptCode;
        this.telPhone = telPhone;
        this.email = email;
        this.remark = remark;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.passwd = passwd;
    }

    public TiUser() {
        super();
    }

    /**
     * 获取员工id
     *
     * @return user_id - 员工id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置员工id
     *
     * @param userId 员工id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取员工工号
     *
     * @return user_code - 员工工号
     */
    public String getUserCode() {
        return userCode;
    }

    /**
     * 设置员工工号
     *
     * @param userCode 员工工号
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    /**
     * 获取员工姓名
     *
     * @return user_name - 员工姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置员工姓名
     *
     * @param userName 员工姓名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取员工职位名称
     *
     * @return position_name - 员工职位名称
     */
    public String getPositionName() {
        return positionName;
    }

    /**
     * 设置员工职位名称
     *
     * @param positionName 员工职位名称
     */
    public void setPositionName(String positionName) {
        this.positionName = positionName == null ? null : positionName.trim();
    }

    /**
     * 获取员工归属网点
     *
     * @return dept_code - 员工归属网点
     */
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * 设置员工归属网点
     *
     * @param deptCode 员工归属网点
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    /**
     * 获取员工联系方式
     *
     * @return tel_phone - 员工联系方式
     */
    public String getTelPhone() {
        return telPhone;
    }

    /**
     * 设置员工联系方式
     *
     * @param telPhone 员工联系方式
     */
    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone == null ? null : telPhone.trim();
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
}