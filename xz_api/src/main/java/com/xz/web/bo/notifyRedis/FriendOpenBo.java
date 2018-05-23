package com.xz.web.bo.notifyRedis;

import java.io.Serializable;

public class FriendOpenBo implements Serializable{

    private String touser;
    private String templateId;
    private String page;
    private String emphasisKeyword;
    private FriendOpenDataBo friendOpenDataBo;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getEmphasisKeyword() {
        return emphasisKeyword;
    }

    public void setEmphasisKeyword(String emphasisKeyword) {
        this.emphasisKeyword = emphasisKeyword;
    }

    public FriendOpenDataBo getFriendOpenDataBo() {
        return friendOpenDataBo;
    }

    public void setFriendOpenDataBo(FriendOpenDataBo friendOpenDataBo) {
        this.friendOpenDataBo = friendOpenDataBo;
    }
}
