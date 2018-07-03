package com.xz.web.mapper.entity;

import com.xz.framework.common.base.BasicBean;
import javax.persistence.*;

@Table(name = "ti_lucky_basic")
public class TiLuckyBasic extends BasicBean {
    /**
     * 星座id
     */
    @Id
    private Integer id;

    /**
     * 综合运势
     */
    private Integer no1;

    /**
     * 桃花指数
     */
    private Integer no2;

    /**
     * 孤独指数
     */
    private Integer no3;

    /**
     * 出轨率
     */
    private Integer no4;

    @Column(name = "constellation_id")
    private Integer constellationId;

    public TiLuckyBasic(Integer id, Integer no1, Integer no2, Integer no3, Integer no4, Integer constellationId) {
        this.id = id;
        this.no1 = no1;
        this.no2 = no2;
        this.no3 = no3;
        this.no4 = no4;
        this.constellationId = constellationId;
    }

    public TiLuckyBasic() {
        super();
    }

    /**
     * 获取星座id
     *
     * @return id - 星座id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置星座id
     *
     * @param id 星座id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取综合运势
     *
     * @return no1 - 综合运势
     */
    public Integer getNo1() {
        return no1;
    }

    /**
     * 设置综合运势
     *
     * @param no1 综合运势
     */
    public void setNo1(Integer no1) {
        this.no1 = no1;
    }

    /**
     * 获取桃花指数
     *
     * @return no2 - 桃花指数
     */
    public Integer getNo2() {
        return no2;
    }

    /**
     * 设置桃花指数
     *
     * @param no2 桃花指数
     */
    public void setNo2(Integer no2) {
        this.no2 = no2;
    }

    /**
     * 获取孤独指数
     *
     * @return no3 - 孤独指数
     */
    public Integer getNo3() {
        return no3;
    }

    /**
     * 设置孤独指数
     *
     * @param no3 孤独指数
     */
    public void setNo3(Integer no3) {
        this.no3 = no3;
    }

    /**
     * 获取出轨率
     *
     * @return no4 - 出轨率
     */
    public Integer getNo4() {
        return no4;
    }

    /**
     * 设置出轨率
     *
     * @param no4 出轨率
     */
    public void setNo4(Integer no4) {
        this.no4 = no4;
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
}