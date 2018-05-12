package com.xz.web.mapper.entity;

import com.xz.framework.common.base.BasicBean;
import java.util.Date;
import javax.persistence.*;

@Table(name = "ti_everyday_word")
public class TiEverydayWord extends BasicBean {
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片1
     */
    private String picture1;

    /**
     * 图片2
     */
    private String pictuer2;

    /**
     * 文字
     */
    private String words;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    public TiEverydayWord(Integer id, String title, String picture1, String pictuer2, String words, Date createTime) {
        this.id = id;
        this.title = title;
        this.picture1 = picture1;
        this.pictuer2 = pictuer2;
        this.words = words;
        this.createTime = createTime;
    }

    public TiEverydayWord() {
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
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取图片1
     *
     * @return picture1 - 图片1
     */
    public String getPicture1() {
        return picture1;
    }

    /**
     * 设置图片1
     *
     * @param picture1 图片1
     */
    public void setPicture1(String picture1) {
        this.picture1 = picture1 == null ? null : picture1.trim();
    }

    /**
     * 获取图片2
     *
     * @return pictuer2 - 图片2
     */
    public String getPictuer2() {
        return pictuer2;
    }

    /**
     * 设置图片2
     *
     * @param pictuer2 图片2
     */
    public void setPictuer2(String pictuer2) {
        this.pictuer2 = pictuer2 == null ? null : pictuer2.trim();
    }

    /**
     * 获取文字
     *
     * @return words - 文字
     */
    public String getWords() {
        return words;
    }

    /**
     * 设置文字
     *
     * @param words 文字
     */
    public void setWords(String words) {
        this.words = words == null ? null : words.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}