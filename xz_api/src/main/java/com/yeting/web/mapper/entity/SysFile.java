package com.yeting.web.mapper.entity;

import com.yeting.framework.common.base.BasicBean;
import javax.persistence.*;

@Table(name = "sys_file")
public class SysFile extends BasicBean {
    /**
     * 文件id
     */
    @Id
    private Long fid;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 文件路径
     */
    private String path;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "create_time")
    private String createTime;

    public SysFile(Long fid, String filename, String path, String createUser, String createTime) {
        this.fid = fid;
        this.filename = filename;
        this.path = path;
        this.createUser = createUser;
        this.createTime = createTime;
    }

    public SysFile() {
        super();
    }

    /**
     * 获取文件id
     *
     * @return fid - 文件id
     */
    public Long getFid() {
        return fid;
    }

    /**
     * 设置文件id
     *
     * @param fid 文件id
     */
    public void setFid(Long fid) {
        this.fid = fid;
    }

    /**
     * 获取文件名
     *
     * @return filename - 文件名
     */
    public String getFilename() {
        return filename;
    }

    /**
     * 设置文件名
     *
     * @param filename 文件名
     */
    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    /**
     * 获取文件路径
     *
     * @return path - 文件路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置文件路径
     *
     * @param path 文件路径
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * @return create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
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
}