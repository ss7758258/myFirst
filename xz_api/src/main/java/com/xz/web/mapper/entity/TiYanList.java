package com.xz.web.mapper.entity;

import com.xz.framework.common.base.BasicBean;
import javax.persistence.*;

@Table(name = "ti_yan_list")
public class TiYanList extends BasicBean {
    /**
     * 一言
     */
    @Id
    private Long id;

    /**
     * @2-星座id
     */
    @Column(name = "constellation_id")
    private Long constellationId;

    /**
     * @7-预览图
     */
    @Column(name = "prev_pic")
    private String prevPic;

    /**
     * @1-发布人
     */
    @Column(name = "publish_person")
    private String publishPerson;

    /**
     * @1-发布状态
     */
    @Column(name = "publish_status")
    private String publishStatus;

    /**
     * @1-发布时间
     */
    @Column(name = "publish_time")
    private String publishTime;

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

    public TiYanList(Long id, Long constellationId, String prevPic, String publishPerson, String publishStatus, String publishTime, String createTimestamp, String updateTimestamp) {
        this.id = id;
        this.constellationId = constellationId;
        this.prevPic = prevPic;
        this.publishPerson = publishPerson;
        this.publishStatus = publishStatus;
        this.publishTime = publishTime;
        this.createTimestamp = createTimestamp;
        this.updateTimestamp = updateTimestamp;
    }

    public TiYanList() {
        super();
    }

    /**
     * 获取一言
     *
     * @return id - 一言
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置一言
     *
     * @param id 一言
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
     * 获取@7-预览图
     *
     * @return prev_pic - @7-预览图
     */
    public String getPrevPic() {
        return prevPic;
    }

    /**
     * 设置@7-预览图
     *
     * @param prevPic @7-预览图
     */
    public void setPrevPic(String prevPic) {
        this.prevPic = prevPic == null ? null : prevPic.trim();
    }

    /**
     * 获取@1-发布人
     *
     * @return publish_person - @1-发布人
     */
    public String getPublishPerson() {
        return publishPerson;
    }

    /**
     * 设置@1-发布人
     *
     * @param publishPerson @1-发布人
     */
    public void setPublishPerson(String publishPerson) {
        this.publishPerson = publishPerson == null ? null : publishPerson.trim();
    }

    /**
     * 获取@1-发布状态
     *
     * @return publish_status - @1-发布状态
     */
    public String getPublishStatus() {
        return publishStatus;
    }

    /**
     * 设置@1-发布状态
     *
     * @param publishStatus @1-发布状态
     */
    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus == null ? null : publishStatus.trim();
    }

    /**
     * 获取@1-发布时间
     *
     * @return publish_time - @1-发布时间
     */
    public String getPublishTime() {
        return publishTime;
    }

    /**
     * 设置@1-发布时间
     *
     * @param publishTime @1-发布时间
     */
    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime == null ? null : publishTime.trim();
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