package com.xz.web.entity;

import com.xz.framework.common.base.BasicBean;

public class Access extends BasicBean {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column access.id
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column access.resource_id
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    private Integer resourceId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column access.resource_name
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    private String resourceName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column access.resource_code
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    private String resourceCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column access.status
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column access.userid
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    private Integer userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column access.username
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    private String username;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column access.add_flag
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    private Boolean addFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column access.del_flag
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    private Boolean delFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column access.upd_flag
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    private Boolean updFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column access.view_flag
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    private Boolean viewFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column access.create_timestamp
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    private String createTimestamp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column access.update_timestamp
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    private String updateTimestamp;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table access
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public Access(Integer id, Integer resourceId, String resourceName, String resourceCode, Integer status, Integer userid, String username, Boolean addFlag, Boolean delFlag, Boolean updFlag, Boolean viewFlag, String createTimestamp, String updateTimestamp) {
        this.id = id;
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.resourceCode = resourceCode;
        this.status = status;
        this.userid = userid;
        this.username = username;
        this.addFlag = addFlag;
        this.delFlag = delFlag;
        this.updFlag = updFlag;
        this.viewFlag = viewFlag;
        this.createTimestamp = createTimestamp;
        this.updateTimestamp = updateTimestamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table access
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public Access() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column access.id
     *
     * @return the value of access.id
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column access.id
     *
     * @param id the value for access.id
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column access.resource_id
     *
     * @return the value of access.resource_id
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public Integer getResourceId() {
        return resourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column access.resource_id
     *
     * @param resourceId the value for access.resource_id
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column access.resource_name
     *
     * @return the value of access.resource_name
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column access.resource_name
     *
     * @param resourceName the value for access.resource_name
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column access.resource_code
     *
     * @return the value of access.resource_code
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public String getResourceCode() {
        return resourceCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column access.resource_code
     *
     * @param resourceCode the value for access.resource_code
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode == null ? null : resourceCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column access.status
     *
     * @return the value of access.status
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column access.status
     *
     * @param status the value for access.status
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column access.userid
     *
     * @return the value of access.userid
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column access.userid
     *
     * @param userid the value for access.userid
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column access.username
     *
     * @return the value of access.username
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column access.username
     *
     * @param username the value for access.username
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column access.add_flag
     *
     * @return the value of access.add_flag
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public Boolean getAddFlag() {
        return addFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column access.add_flag
     *
     * @param addFlag the value for access.add_flag
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public void setAddFlag(Boolean addFlag) {
        this.addFlag = addFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column access.del_flag
     *
     * @return the value of access.del_flag
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public Boolean getDelFlag() {
        return delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column access.del_flag
     *
     * @param delFlag the value for access.del_flag
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column access.upd_flag
     *
     * @return the value of access.upd_flag
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public Boolean getUpdFlag() {
        return updFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column access.upd_flag
     *
     * @param updFlag the value for access.upd_flag
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public void setUpdFlag(Boolean updFlag) {
        this.updFlag = updFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column access.view_flag
     *
     * @return the value of access.view_flag
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public Boolean getViewFlag() {
        return viewFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column access.view_flag
     *
     * @param viewFlag the value for access.view_flag
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public void setViewFlag(Boolean viewFlag) {
        this.viewFlag = viewFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column access.create_timestamp
     *
     * @return the value of access.create_timestamp
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public String getCreateTimestamp() {
        return createTimestamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column access.create_timestamp
     *
     * @param createTimestamp the value for access.create_timestamp
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public void setCreateTimestamp(String createTimestamp) {
        this.createTimestamp = createTimestamp == null ? null : createTimestamp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column access.update_timestamp
     *
     * @return the value of access.update_timestamp
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public String getUpdateTimestamp() {
        return updateTimestamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column access.update_timestamp
     *
     * @param updateTimestamp the value for access.update_timestamp
     *
     * @mbggenerated Thu May 10 18:29:41 CST 2018
     */
    public void setUpdateTimestamp(String updateTimestamp) {
        this.updateTimestamp = updateTimestamp == null ? null : updateTimestamp.trim();
    }
}