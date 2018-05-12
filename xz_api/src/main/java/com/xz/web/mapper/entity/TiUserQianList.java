package com.xz.web.mapper.entity;

import com.xz.framework.common.base.BasicBean;
import javax.persistence.*;

@Table(name = "ti_user_qian_list")
public class TiUserQianList extends BasicBean {
    /**
     * 一言
     */
    @Id
    private Long id;

    /**
     * @2-抽签时间
     */
    @Column(name = "qian_date")
    private String qianDate;

    /**
     * @4-状态-1=已拆,0=拆迁中
     */
    private Integer status;

    /**
     * @1-抽签人
     */
    @Column(name = "qian_name")
    private String qianName;

    /**
     * @1-抽签内容
     */
    @Column(name = "qian_content")
    private String qianContent;

    /**
     * @1-抽签人ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * @1-好友openId
     */
    @Column(name = "friend_open_id1")
    private String friendOpenId1;

    /**
     * @1-好友openId
     */
    @Column(name = "friend_open_id2")
    private String friendOpenId2;

    /**
     * @1-好友openId
     */
    @Column(name = "friend_open_id3")
    private String friendOpenId3;

    /**
     * @1-好友openId
     */
    @Column(name = "friend_open_id4")
    private String friendOpenId4;

    /**
     * @1-好友openId
     */
    @Column(name = "friend_open_id5")
    private String friendOpenId5;

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

    public TiUserQianList(Long id, String qianDate, Integer status, String qianName, String qianContent, Long userId, String friendOpenId1, String friendOpenId2, String friendOpenId3, String friendOpenId4, String friendOpenId5, String createTimestamp, String updateTimestamp) {
        this.id = id;
        this.qianDate = qianDate;
        this.status = status;
        this.qianName = qianName;
        this.qianContent = qianContent;
        this.userId = userId;
        this.friendOpenId1 = friendOpenId1;
        this.friendOpenId2 = friendOpenId2;
        this.friendOpenId3 = friendOpenId3;
        this.friendOpenId4 = friendOpenId4;
        this.friendOpenId5 = friendOpenId5;
        this.createTimestamp = createTimestamp;
        this.updateTimestamp = updateTimestamp;
    }

    public TiUserQianList() {
        super();
    }

    /**
     * 获取一言
     *
     * @return id - 一言
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置一言
     *
     * @param id 一言
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取@2-抽签时间
     *
     * @return qian_date - @2-抽签时间
     */
    public String getQianDate() {
        return qianDate;
    }

    /**
     * 设置@2-抽签时间
     *
     * @param qianDate @2-抽签时间
     */
    public void setQianDate(String qianDate) {
        this.qianDate = qianDate == null ? null : qianDate.trim();
    }

    /**
     * 获取@4-状态-1=已拆,0=拆迁中
     *
     * @return status - @4-状态-1=已拆,0=拆迁中
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置@4-状态-1=已拆,0=拆迁中
     *
     * @param status @4-状态-1=已拆,0=拆迁中
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取@1-抽签人
     *
     * @return qian_name - @1-抽签人
     */
    public String getQianName() {
        return qianName;
    }

    /**
     * 设置@1-抽签人
     *
     * @param qianName @1-抽签人
     */
    public void setQianName(String qianName) {
        this.qianName = qianName == null ? null : qianName.trim();
    }

    /**
     * 获取@1-抽签内容
     *
     * @return qian_content - @1-抽签内容
     */
    public String getQianContent() {
        return qianContent;
    }

    /**
     * 设置@1-抽签内容
     *
     * @param qianContent @1-抽签内容
     */
    public void setQianContent(String qianContent) {
        this.qianContent = qianContent == null ? null : qianContent.trim();
    }

    /**
     * 获取@1-抽签人ID
     *
     * @return user_id - @1-抽签人ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置@1-抽签人ID
     *
     * @param userId @1-抽签人ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取@1-好友openId
     *
     * @return friend_open_id1 - @1-好友openId
     */
    public String getFriendOpenId1() {
        return friendOpenId1;
    }

    /**
     * 设置@1-好友openId
     *
     * @param friendOpenId1 @1-好友openId
     */
    public void setFriendOpenId1(String friendOpenId1) {
        this.friendOpenId1 = friendOpenId1 == null ? null : friendOpenId1.trim();
    }

    /**
     * 获取@1-好友openId
     *
     * @return friend_open_id2 - @1-好友openId
     */
    public String getFriendOpenId2() {
        return friendOpenId2;
    }

    /**
     * 设置@1-好友openId
     *
     * @param friendOpenId2 @1-好友openId
     */
    public void setFriendOpenId2(String friendOpenId2) {
        this.friendOpenId2 = friendOpenId2 == null ? null : friendOpenId2.trim();
    }

    /**
     * 获取@1-好友openId
     *
     * @return friend_open_id3 - @1-好友openId
     */
    public String getFriendOpenId3() {
        return friendOpenId3;
    }

    /**
     * 设置@1-好友openId
     *
     * @param friendOpenId3 @1-好友openId
     */
    public void setFriendOpenId3(String friendOpenId3) {
        this.friendOpenId3 = friendOpenId3 == null ? null : friendOpenId3.trim();
    }

    /**
     * 获取@1-好友openId
     *
     * @return friend_open_id4 - @1-好友openId
     */
    public String getFriendOpenId4() {
        return friendOpenId4;
    }

    /**
     * 设置@1-好友openId
     *
     * @param friendOpenId4 @1-好友openId
     */
    public void setFriendOpenId4(String friendOpenId4) {
        this.friendOpenId4 = friendOpenId4 == null ? null : friendOpenId4.trim();
    }

    /**
     * 获取@1-好友openId
     *
     * @return friend_open_id5 - @1-好友openId
     */
    public String getFriendOpenId5() {
        return friendOpenId5;
    }

    /**
     * 设置@1-好友openId
     *
     * @param friendOpenId5 @1-好友openId
     */
    public void setFriendOpenId5(String friendOpenId5) {
        this.friendOpenId5 = friendOpenId5 == null ? null : friendOpenId5.trim();
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