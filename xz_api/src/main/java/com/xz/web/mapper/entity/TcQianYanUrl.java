package com.xz.web.mapper.entity;

import com.xz.framework.common.base.BasicBean;
import javax.persistence.*;
import java.io.Serializable;

@Table(name = "tc_qian_yan_url")
public class TcQianYanUrl extends BasicBean implements Serializable {
    @Id
    private Long id;

    /**
     * @1-一言url
     */
    @Column(name = "yan_url")
    private String yanUrl;

    /**
     * @1-一签url
     */
    @Column(name = "qian_url")
    private String qianUrl;

    public TcQianYanUrl(Long id, String yanUrl, String qianUrl) {
        this.id = id;
        this.yanUrl = yanUrl;
        this.qianUrl = qianUrl;
    }

    public TcQianYanUrl() {
        super();
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取@1-一言url
     *
     * @return yan_url - @1-一言url
     */
    public String getYanUrl() {
        return yanUrl;
    }

    /**
     * 设置@1-一言url
     *
     * @param yanUrl @1-一言url
     */
    public void setYanUrl(String yanUrl) {
        this.yanUrl = yanUrl == null ? null : yanUrl.trim();
    }

    /**
     * 获取@1-一签url
     *
     * @return qian_url - @1-一签url
     */
    public String getQianUrl() {
        return qianUrl;
    }

    /**
     * 设置@1-一签url
     *
     * @param qianUrl @1-一签url
     */
    public void setQianUrl(String qianUrl) {
        this.qianUrl = qianUrl == null ? null : qianUrl.trim();
    }
}