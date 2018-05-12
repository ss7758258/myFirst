package com.xz.web.mapper.entity;

import com.xz.framework.common.base.BasicBean;
import javax.persistence.*;

@Table(name = "tc_constellation")
public class TcConstellation extends BasicBean {
    /**
     * 星座表
     */
    @Id
    private Long id;

    /**
     * @1-星座
     */
    @Column(name = "constellation_name")
    private String constellationName;

    /**
     * @1-月份开始日期
     */
    @Column(name = "start_date")
    private String startDate;

    /**
     * @1-结束日期
     */
    @Column(name = "end_date")
    private String endDate;

    /**
     * @7-背景图片
     */
    @Column(name = "picture_url")
    private String pictureUrl;

    /**
     * @1-备注
     */
    private String remark;

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

    public TcConstellation(Long id, String constellationName, String startDate, String endDate, String pictureUrl, String remark, String createTimestamp, String updateTimestamp) {
        this.id = id;
        this.constellationName = constellationName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pictureUrl = pictureUrl;
        this.remark = remark;
        this.createTimestamp = createTimestamp;
        this.updateTimestamp = updateTimestamp;
    }

    public TcConstellation() {
        super();
    }

    /**
     * 获取星座表
     *
     * @return id - 星座表
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置星座表
     *
     * @param id 星座表
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取@1-星座
     *
     * @return constellation_name - @1-星座
     */
    public String getConstellationName() {
        return constellationName;
    }

    /**
     * 设置@1-星座
     *
     * @param constellationName @1-星座
     */
    public void setConstellationName(String constellationName) {
        this.constellationName = constellationName == null ? null : constellationName.trim();
    }

    /**
     * 获取@1-月份开始日期
     *
     * @return start_date - @1-月份开始日期
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * 设置@1-月份开始日期
     *
     * @param startDate @1-月份开始日期
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? null : startDate.trim();
    }

    /**
     * 获取@1-结束日期
     *
     * @return end_date - @1-结束日期
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * 设置@1-结束日期
     *
     * @param endDate @1-结束日期
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    /**
     * 获取@7-背景图片
     *
     * @return picture_url - @7-背景图片
     */
    public String getPictureUrl() {
        return pictureUrl;
    }

    /**
     * 设置@7-背景图片
     *
     * @param pictureUrl @7-背景图片
     */
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl == null ? null : pictureUrl.trim();
    }

    /**
     * 获取@1-备注
     *
     * @return remark - @1-备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置@1-备注
     *
     * @param remark @1-备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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