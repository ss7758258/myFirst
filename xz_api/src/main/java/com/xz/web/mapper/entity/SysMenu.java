package com.xz.web.mapper.entity;

import com.xz.framework.common.base.BasicBean;
import javax.persistence.*;

@Table(name = "sys_menu")
public class SysMenu extends BasicBean {
    @Id
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "menu_name")
    private String menuName;

    private String url;

    private Boolean deleted;

    private String icon;

    /**
     * 优先级
     */
    private Integer indexs;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "updated_time")
    private String updatedTime;

    public SysMenu(Long menuId, String menuName, String url, Boolean deleted, String icon, Integer indexs, Long parentId, String createTime, String updatedTime) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.url = url;
        this.deleted = deleted;
        this.icon = icon;
        this.indexs = indexs;
        this.parentId = parentId;
        this.createTime = createTime;
        this.updatedTime = updatedTime;
    }

    public SysMenu() {
        super();
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
     * @return menu_name
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * @param menuName
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * @return deleted
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * @param deleted
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * @return icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * 获取优先级
     *
     * @return indexs - 优先级
     */
    public Integer getIndexs() {
        return indexs;
    }

    /**
     * 设置优先级
     *
     * @param indexs 优先级
     */
    public void setIndexs(Integer indexs) {
        this.indexs = indexs;
    }

    /**
     * @return parent_id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
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