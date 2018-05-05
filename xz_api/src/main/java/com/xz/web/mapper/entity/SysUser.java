package com.xz.web.mapper.entity;

import com.xz.framework.common.base.BasicBean;
import javax.persistence.*;

@Table(name = "sys_user")
public class SysUser extends BasicBean {
    /**
     * 用户id
     */
    @Id
    private Long uid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 显示名
     */
    private String nickname;

    /**
     * 角色ID列表, 多角色逗号分割
     */
    private String role;

    /**
     * 头像
     */
    private String avatar;

    @Column(name = "create_open_id")
    private String createOpenId;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "updated_open_id")
    private String updatedOpenId;

    @Column(name = "updated_time")
    private String updatedTime;

    public SysUser(Long uid, String username, String password, String nickname, String role, String avatar, String createOpenId, String createTime, String updatedOpenId, String updatedTime) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.avatar = avatar;
        this.createOpenId = createOpenId;
        this.createTime = createTime;
        this.updatedOpenId = updatedOpenId;
        this.updatedTime = updatedTime;
    }

    public SysUser() {
        super();
    }

    /**
     * 获取用户id
     *
     * @return uid - 用户id
     */
    public Long getUid() {
        return uid;
    }

    /**
     * 设置用户id
     *
     * @param uid 用户id
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取显示名
     *
     * @return nickname - 显示名
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置显示名
     *
     * @param nickname 显示名
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * 获取角色ID列表, 多角色逗号分割
     *
     * @return role - 角色ID列表, 多角色逗号分割
     */
    public String getRole() {
        return role;
    }

    /**
     * 设置角色ID列表, 多角色逗号分割
     *
     * @param role 角色ID列表, 多角色逗号分割
     */
    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
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

    public String getCreateOpenId() {
        return createOpenId;
    }

    public void setCreateOpenId(String createOpenId) {
        this.createOpenId = createOpenId;
    }

    public String getUpdatedOpenId() {
        return updatedOpenId;
    }

    public void setUpdatedOpenId(String updatedOpenId) {
        this.updatedOpenId = updatedOpenId;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }
}