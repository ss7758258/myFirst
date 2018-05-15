package com.xz.web.vo.everydayQian;

import com.xz.framework.common.base.BasicBean;
import com.xz.framework.utils.StringUtil;

public class X511 extends BasicBean {
    private Long id;
    private String qianDate;
    private Integer status;
    private String qianName;
    private String qianContent;
    private Long userId;
    private Integer isMyQian=0;
    private Integer alreadyOpen=0;
    private String ownerOpenId;
    private String friendOpenId1;
    private String friendOpenId2;
    private String friendOpenId3;
    private String friendOpenId4;
    private String friendOpenId5;
    private String ownerHeadImage;
    private String ownerNickName;
    private String friendHeadImage1;
    private String friendHeadImage2;
    private String friendHeadImage3;
    private String friendHeadImage4;
    private String friendHeadImage5;
    private String createTimestamp;
    private String updateTimestamp;

    private int qianOpenSize;

    public int getQianOpenSize() {
        return qianOpenSize;
    }

    public void setQianOpenSize(int qianOpenSize) {
        this.qianOpenSize = qianOpenSize;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsMyQian() {
        return isMyQian;
    }

    public void setIsMyQian(Integer isMyQian) {
        this.isMyQian = isMyQian;
    }

    public Integer getAlreadyOpen() {
        return alreadyOpen;
    }

    public void setAlreadyOpen(Integer alreadyOpen) {
        this.alreadyOpen = alreadyOpen;
    }

    public String getQianDate() {
        return qianDate;
    }

    public void setQianDate(String qianDate) {
        this.qianDate = qianDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getQianName() {
        return qianName;
    }

    public void setQianName(String qianName) {
        this.qianName = qianName;
    }

    public String getQianContent() {
        return qianContent;
    }

    public void setQianContent(String qianContent) {
        this.qianContent = qianContent;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFriendOpenId1() {
        return friendOpenId1;
    }

    public void setFriendOpenId1(String friendOpenId1) {
        this.friendOpenId1 = friendOpenId1;
    }

    public String getFriendOpenId2() {
        return friendOpenId2;
    }

    public void setFriendOpenId2(String friendOpenId2) {
        this.friendOpenId2 = friendOpenId2;
    }

    public String getFriendOpenId3() {
        return friendOpenId3;
    }

    public void setFriendOpenId3(String friendOpenId3) {
        this.friendOpenId3 = friendOpenId3;
    }

    public String getFriendOpenId4() {
        return friendOpenId4;
    }

    public void setFriendOpenId4(String friendOpenId4) {
        this.friendOpenId4 = friendOpenId4;
    }

    public String getFriendOpenId5() {
        return friendOpenId5;
    }

    public void setFriendOpenId5(String friendOpenId5) {
        this.friendOpenId5 = friendOpenId5;
    }

    public String getFriendHeadImage1() {
        return friendHeadImage1;
    }

    public void setFriendHeadImage1(String friendHeadImage1) {
        this.friendHeadImage1 = friendHeadImage1;
    }

    public String getFriendHeadImage2() {
        return friendHeadImage2;
    }

    public void setFriendHeadImage2(String friendHeadImage2) {
        this.friendHeadImage2 = friendHeadImage2;
    }

    public String getFriendHeadImage3() {
        return friendHeadImage3;
    }

    public void setFriendHeadImage3(String friendHeadImage3) {
        this.friendHeadImage3 = friendHeadImage3;
    }

    public String getFriendHeadImage4() {
        return friendHeadImage4;
    }

    public void setFriendHeadImage4(String friendHeadImage4) {
        this.friendHeadImage4 = friendHeadImage4;
    }

    public String getFriendHeadImage5() {
        return friendHeadImage5;
    }

    public void setFriendHeadImage5(String friendHeadImage5) {
        this.friendHeadImage5 = friendHeadImage5;
    }

    public String getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(String createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(String updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public String getOwnerOpenId() {
        return ownerOpenId;
    }

    public void setOwnerOpenId(String ownerOpenId) {
        this.ownerOpenId = ownerOpenId;
    }

    public String getOwnerHeadImage() {
        return ownerHeadImage;
    }

    public void setOwnerHeadImage(String ownerHeadImage) {
        this.ownerHeadImage = ownerHeadImage;
    }

    public String getOwnerNickName() {
        return ownerNickName;
    }

    public void setOwnerNickName(String ownerNickName) {
        if(StringUtil.isEmpty(ownerNickName))
        {
            this.ownerNickName = "";
        }
        this.ownerNickName = StringUtil.Base64ToStr(ownerNickName);
    }
}