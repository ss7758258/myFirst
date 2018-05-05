package com.yeting.web.mapper.entity;

import com.yeting.framework.common.base.BasicBean;
import javax.persistence.*;

@Table(name = "sys_role_menu")
public class SysRoleMenu extends BasicBean {
    @Id
    @Column(name = "role_menu_id")
    private Long roleMenuId;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "create_open_id")
    private String createOpenId;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "updated_open_id")
    private String updatedOpenId;

    @Column(name = "updated_time")
    private String updatedTime;

    public SysRoleMenu(Long roleMenuId, Integer roleId, Long menuId, String createOpenId, String createTime, String updatedOpenId, String updatedTime) {
        this.roleMenuId = roleMenuId;
        this.roleId = roleId;
        this.menuId = menuId;
        this.createOpenId = createOpenId;
        this.createTime = createTime;
        this.updatedOpenId = updatedOpenId;
        this.updatedTime = updatedTime;
    }

    public SysRoleMenu() {
        super();
    }

    /**
     * @return role_menu_id
     */
    public Long getRoleMenuId() {
        return roleMenuId;
    }

    /**
     * @param roleMenuId
     */
    public void setRoleMenuId(Long roleMenuId) {
        this.roleMenuId = roleMenuId;
    }

    /**
     * @return role_id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * @return menu_id
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * @param menuId
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
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