package com.xz.web.mapper.entity;

import com.xz.framework.common.base.BasicBean;
import java.util.Date;
import javax.persistence.*;

@Table(name = "ti_lucky_more")
public class TiLuckyMore extends BasicBean {
    @Id
    private Integer id;

    @Column(name = "constellation_id")
    private Integer constellationId;

    /**
     * 工作运势
     */
    @Column(name = "gz_lucky")
    private Integer gzLucky;

    /**
     * 健康运势
     */
    @Column(name = "jk_lucky")
    private Integer jkLucky;

    /**
     * 财运运势
     */
    @Column(name = "cy_lucky")
    private Integer cyLucky;

    /**
     * 爱情运势
     */
    @Column(name = "aq_lucky")
    private Integer aqLucky;

    private String doing;

    @Column(name = "not_do")
    private String notDo;

    @Column(name = "create_time")
    private Date createTime;

    public TiLuckyMore(Integer id, Integer constellationId, Integer gzLucky, Integer jkLucky, Integer cyLucky, Integer aqLucky, String doing, String notDo, Date createTime) {
        this.id = id;
        this.constellationId = constellationId;
        this.gzLucky = gzLucky;
        this.jkLucky = jkLucky;
        this.cyLucky = cyLucky;
        this.aqLucky = aqLucky;
        this.doing = doing;
        this.notDo = notDo;
        this.createTime = createTime;
    }

    public TiLuckyMore() {
        super();
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return constellation_id
     */
    public Integer getConstellationId() {
        return constellationId;
    }

    /**
     * @param constellationId
     */
    public void setConstellationId(Integer constellationId) {
        this.constellationId = constellationId;
    }

    /**
     * 获取工作运势
     *
     * @return gz_lucky - 工作运势
     */
    public Integer getGzLucky() {
        return gzLucky;
    }

    /**
     * 设置工作运势
     *
     * @param gzLucky 工作运势
     */
    public void setGzLucky(Integer gzLucky) {
        this.gzLucky = gzLucky;
    }

    /**
     * 获取健康运势
     *
     * @return jk_lucky - 健康运势
     */
    public Integer getJkLucky() {
        return jkLucky;
    }

    /**
     * 设置健康运势
     *
     * @param jkLucky 健康运势
     */
    public void setJkLucky(Integer jkLucky) {
        this.jkLucky = jkLucky;
    }

    /**
     * 获取财运运势
     *
     * @return cy_lucky - 财运运势
     */
    public Integer getCyLucky() {
        return cyLucky;
    }

    /**
     * 设置财运运势
     *
     * @param cyLucky 财运运势
     */
    public void setCyLucky(Integer cyLucky) {
        this.cyLucky = cyLucky;
    }

    /**
     * 获取爱情运势
     *
     * @return aq_lucky - 爱情运势
     */
    public Integer getAqLucky() {
        return aqLucky;
    }

    /**
     * 设置爱情运势
     *
     * @param aqLucky 爱情运势
     */
    public void setAqLucky(Integer aqLucky) {
        this.aqLucky = aqLucky;
    }

    /**
     * @return doing
     */
    public String getDoing() {
        return doing;
    }

    /**
     * @param doing
     */
    public void setDoing(String doing) {
        this.doing = doing == null ? null : doing.trim();
    }

    /**
     * @return not_do
     */
    public String getNotDo() {
        return notDo;
    }

    /**
     * @param notDo
     */
    public void setNotDo(String notDo) {
        this.notDo = notDo == null ? null : notDo.trim();
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}