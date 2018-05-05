package com.yeting.web.bo.f20;

import java.io.Serializable;
import java.util.List;

public class F10Bo implements Serializable {

    //文章详细信息
    private Long feedId;
    private String musicUrl;
    private String title;
    private String createTime;
    private String imgSrc;
    private Integer viewCount;
    private Integer shareCount;
    private Integer collectCount;
    private Integer likeCount;
    private String preview;
    private Boolean like;
    private String backgroundUrl;
    private String commentUrl;
    private Boolean collection;


    //热门评论
    private List<F12Bo> f12BoList;

    //最新评论
    private List<F13Bo> f13BoList;

    public Long getFeedId() {
        return feedId;
    }

    public void setFeedId(Long feedId) {
        this.feedId = feedId;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public String getCommentUrl() {
        return commentUrl;
    }

    public void setCommentUrl(String commentUrl) {
        this.commentUrl = commentUrl;
    }

    public Boolean getCollection() {
        return collection;
    }

    public void setCollection(Boolean collection) {
        this.collection = collection;
    }

    public List<F12Bo> getF12BoList() {
        return f12BoList;
    }

    public void setF12BoList(List<F12Bo> f12BoList) {
        this.f12BoList = f12BoList;
    }

    public List<F13Bo> getF13BoList() {
        return f13BoList;
    }

    public void setF13BoList(List<F13Bo> f13BoList) {
        this.f13BoList = f13BoList;
    }

}
