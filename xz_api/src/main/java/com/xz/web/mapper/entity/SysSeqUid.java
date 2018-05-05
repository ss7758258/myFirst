package com.xz.web.mapper.entity;

import com.xz.framework.common.base.BasicBean;
import javax.persistence.*;

@Table(name = "sys_seq_uid")
public class SysSeqUid extends BasicBean {
    @Id
    @Column(name = "seq_id")
    private Long seqId;

    private String module;

    @Column(name = "seq_uid")
    private Long seqUid;

    /**
     * 步长
     */
    @Column(name = "max_unit")
    private Integer maxUnit;

    public SysSeqUid(Long seqId, String module, Long seqUid, Integer maxUnit) {
        this.seqId = seqId;
        this.module = module;
        this.seqUid = seqUid;
        this.maxUnit = maxUnit;
    }

    public SysSeqUid() {
        super();
    }

    /**
     * @return seq_id
     */
    public Long getSeqId() {
        return seqId;
    }

    /**
     * @param seqId
     */
    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }

    /**
     * @return module
     */
    public String getModule() {
        return module;
    }

    /**
     * @param module
     */
    public void setModule(String module) {
        this.module = module == null ? null : module.trim();
    }

    /**
     * @return seq_uid
     */
    public Long getSeqUid() {
        return seqUid;
    }

    /**
     * @param seqUid
     */
    public void setSeqUid(Long seqUid) {
        this.seqUid = seqUid;
    }

    /**
     * 获取步长
     *
     * @return max_unit - 步长
     */
    public Integer getMaxUnit() {
        return maxUnit;
    }

    /**
     * 设置步长
     *
     * @param maxUnit 步长
     */
    public void setMaxUnit(Integer maxUnit) {
        this.maxUnit = maxUnit;
    }
}