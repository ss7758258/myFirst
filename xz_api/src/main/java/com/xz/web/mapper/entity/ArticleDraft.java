package com.xz.web.mapper.entity;

import com.xz.framework.common.base.BasicBean;
import javax.persistence.*;

@Table(name = "article_draft")
public class ArticleDraft extends BasicBean {
    @Id
    @Column(name = "art_draft_id")
    private Long artDraftId;

    /**
     * 文章id
     */
    @Column(name = "art_id")
    private Long artId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 文章预览图
     */
    private String preview;

    /**
     * 点赞数
     */
    @Column(name = "praise_num")
    private Integer praiseNum;

    /**
     * 转发数
     */
    @Column(name = "forward_num")
    private Integer forwardNum;

    /**
     * 收藏数
     */
    @Column(name = "favorite_num")
    private Integer favoriteNum;

    /**
     * 语音文件id
     */
    @Column(name = "voice_file_id")
    private Long voiceFileId;

    /**
     * 发布用户id
     */
    @Column(name = "publicer_id")
    private Long publicerId;

    @Column(name = "text_editer")
    private String textEditer;

    @Column(name = "voice_editer")
    private String voiceEditer;

    @Column(name = "pre_release_time")
    private String preReleaseTime;

    private Integer status;

    /**
     * 浏览量
     */
    @Column(name = "view_num")
    private Integer viewNum;

    @Column(name = "create_open_id")
    private String createOpenId;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "updated_open_id")
    private String updatedOpenId;

    @Column(name = "updated_time")
    private String updatedTime;

    public ArticleDraft(Long artDraftId, Long artId, String title, String content, String preview, Integer praiseNum, Integer forwardNum, Integer favoriteNum, Long voiceFileId, Long publicerId, String textEditer, String voiceEditer, String preReleaseTime, Integer status, Integer viewNum, String createOpenId, String createTime, String updatedOpenId, String updatedTime) {
        this.artDraftId = artDraftId;
        this.artId = artId;
        this.title = title;
        this.content = content;
        this.preview = preview;
        this.praiseNum = praiseNum;
        this.forwardNum = forwardNum;
        this.favoriteNum = favoriteNum;
        this.voiceFileId = voiceFileId;
        this.publicerId = publicerId;
        this.textEditer = textEditer;
        this.voiceEditer = voiceEditer;
        this.preReleaseTime = preReleaseTime;
        this.status = status;
        this.viewNum = viewNum;
        this.createOpenId = createOpenId;
        this.createTime = createTime;
        this.updatedOpenId = updatedOpenId;
        this.updatedTime = updatedTime;
    }

    public ArticleDraft() {
        super();
    }

    /**
     * @return art_draft_id
     */
    public Long getArtDraftId() {
        return artDraftId;
    }

    /**
     * @param artDraftId
     */
    public void setArtDraftId(Long artDraftId) {
        this.artDraftId = artDraftId;
    }

    /**
     * 获取文章id
     *
     * @return art_id - 文章id
     */
    public Long getArtId() {
        return artId;
    }

    /**
     * 设置文章id
     *
     * @param artId 文章id
     */
    public void setArtId(Long artId) {
        this.artId = artId;
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
     * 获取内容
     *
     * @return content - 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取文章预览图
     *
     * @return preview - 文章预览图
     */
    public String getPreview() {
        return preview;
    }

    /**
     * 设置文章预览图
     *
     * @param preview 文章预览图
     */
    public void setPreview(String preview) {
        this.preview = preview == null ? null : preview.trim();
    }

    /**
     * 获取点赞数
     *
     * @return praise_num - 点赞数
     */
    public Integer getPraiseNum() {
        return praiseNum;
    }

    /**
     * 设置点赞数
     *
     * @param praiseNum 点赞数
     */
    public void setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
    }

    /**
     * 获取转发数
     *
     * @return forward_num - 转发数
     */
    public Integer getForwardNum() {
        return forwardNum;
    }

    /**
     * 设置转发数
     *
     * @param forwardNum 转发数
     */
    public void setForwardNum(Integer forwardNum) {
        this.forwardNum = forwardNum;
    }

    /**
     * 获取收藏数
     *
     * @return favorite_num - 收藏数
     */
    public Integer getFavoriteNum() {
        return favoriteNum;
    }

    /**
     * 设置收藏数
     *
     * @param favoriteNum 收藏数
     */
    public void setFavoriteNum(Integer favoriteNum) {
        this.favoriteNum = favoriteNum;
    }

    /**
     * 获取语音文件id
     *
     * @return voice_file_id - 语音文件id
     */
    public Long getVoiceFileId() {
        return voiceFileId;
    }

    /**
     * 设置语音文件id
     *
     * @param voiceFileId 语音文件id
     */
    public void setVoiceFileId(Long voiceFileId) {
        this.voiceFileId = voiceFileId;
    }

    /**
     * 获取发布用户id
     *
     * @return publicer_id - 发布用户id
     */
    public Long getPublicerId() {
        return publicerId;
    }

    /**
     * 设置发布用户id
     *
     * @param publicerId 发布用户id
     */
    public void setPublicerId(Long publicerId) {
        this.publicerId = publicerId;
    }

    /**
     * @return text_editer
     */
    public String getTextEditer() {
        return textEditer;
    }

    /**
     * @param textEditer
     */
    public void setTextEditer(String textEditer) {
        this.textEditer = textEditer == null ? null : textEditer.trim();
    }

    /**
     * @return voice_editer
     */
    public String getVoiceEditer() {
        return voiceEditer;
    }

    /**
     * @param voiceEditer
     */
    public void setVoiceEditer(String voiceEditer) {
        this.voiceEditer = voiceEditer == null ? null : voiceEditer.trim();
    }

    /**
     * @return pre_release_time
     */
    public String getPreReleaseTime() {
        return preReleaseTime;
    }

    /**
     * @param preReleaseTime
     */
    public void setPreReleaseTime(String preReleaseTime) {
        this.preReleaseTime = preReleaseTime == null ? null : preReleaseTime.trim();
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取浏览量
     *
     * @return view_num - 浏览量
     */
    public Integer getViewNum() {
        return viewNum;
    }

    /**
     * 设置浏览量
     *
     * @param viewNum 浏览量
     */
    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    /**
     * @return create_open_id
     */
    public String getCreateOpenId() {
        return createOpenId;
    }

    /**
     * @param createOpenId
     */
    public void setCreateOpenId(String createOpenId) {
        this.createOpenId = createOpenId == null ? null : createOpenId.trim();
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

    /**
     * @return updated_open_id
     */
    public String getUpdatedOpenId() {
        return updatedOpenId;
    }

    /**
     * @param updatedOpenId
     */
    public void setUpdatedOpenId(String updatedOpenId) {
        this.updatedOpenId = updatedOpenId == null ? null : updatedOpenId.trim();
    }

    /**
     * @return updated_time
     */
    public String getUpdatedTime() {
        return updatedTime;
    }

    /**
     * @param updatedTime
     */
    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime == null ? null : updatedTime.trim();
    }
}