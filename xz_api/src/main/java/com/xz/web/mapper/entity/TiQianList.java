package com.xz.web.mapper.entity;

import com.xz.framework.common.base.BasicBean;
import javax.persistence.*;

@Table(name = "ti_qian_list")
public class TiQianList extends BasicBean {
    /**
     * 一签表
     */
    @Id
    private Long id;

    /**
     * @1-签库ID
     */
    @Column(name = "qian_lib_id")
    private Long qianLibId;

    /**
     * @1-签名
     */
    private String name;

    /**
     * @1-内容
     */
    private String content;

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

    public TiQianList(Long id, Long qianLibId, String name, String content, String createTimestamp, String updateTimestamp) {
        this.id = id;
        this.qianLibId = qianLibId;
        this.name = name;
        this.content = content;
        this.createTimestamp = createTimestamp;
        this.updateTimestamp = updateTimestamp;
    }

    public TiQianList() {
        super();
    }

    /**
     * 获取一签表
     *
     * @return id - 一签表
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置一签表
     *
     * @param id 一签表
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取@1-签库ID
     *
     * @return qian_lib_id - @1-签库ID
     */
    public Long getQianLibId() {
        return qianLibId;
    }

    /**
     * 设置@1-签库ID
     *
     * @param qianLibId @1-签库ID
     */
    public void setQianLibId(Long qianLibId) {
        this.qianLibId = qianLibId;
    }

    /**
     * 获取@1-签名
     *
     * @return name - @1-签名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置@1-签名
     *
     * @param name @1-签名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取@1-内容
     *
     * @return content - @1-内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置@1-内容
     *
     * @param content @1-内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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