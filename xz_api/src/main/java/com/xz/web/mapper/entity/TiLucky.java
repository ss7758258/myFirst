package com.xz.web.mapper.entity;

import com.xz.framework.common.base.BasicBean;
import javax.persistence.*;

@Table(name = "ti_lucky")
public class TiLucky extends BasicBean {
    /**
     * 运势
     */
    @Id
    private Long id;

    /**
     * @2-星座id
     */
    @Column(name = "constellation_id")
    private Long constellationId;

    /**
     * @1-Test
     */
    @Column(name = "lucky_type1")
    private String luckyType1;

    /**
     * @1-Test
     */
    @Column(name = "lucky_type2")
    private String luckyType2;

    /**
     * @1-Test
     */
    @Column(name = "lucky_type3")
    private String luckyType3;

    /**
     * @1-Test
     */
    @Column(name = "lucky_type4")
    private String luckyType4;

    /**
     * @2-Test
     */
    @Column(name = "lucky_score1")
    private Integer luckyScore1;

    /**
     * @2-Test
     */
    @Column(name = "lucky_score2")
    private Integer luckyScore2;

    /**
     * @2-Test
     */
    @Column(name = "lucky_score3")
    private Integer luckyScore3;

    /**
     * @2-Test
     */
    @Column(name = "lucky_score4")
    private Integer luckyScore4;

    /**
     * @1-Test
     */
    @Column(name = "remind_today")
    private String remindToday;

    /**
     * @1-Test
     */
    @Column(name = "lucky_type_more1")
    private String luckyTypeMore1;

    /**
     * @1-Test
     */
    @Column(name = "lucky_type_more2")
    private String luckyTypeMore2;

    /**
     * @1-Test
     */
    @Column(name = "lucky_type_more3")
    private String luckyTypeMore3;

    /**
     * @1-Test
     */
    @Column(name = "lucky_type_more4")
    private String luckyTypeMore4;

    /**
     * @2-Test
     */
    @Column(name = "lucky_score_more1")
    private Integer luckyScoreMore1;

    /**
     * @2-Test
     */
    @Column(name = "lucky_score_more2")
    private Integer luckyScoreMore2;

    /**
     * @2-Test
     */
    @Column(name = "lucky_score_more3")
    private Integer luckyScoreMore3;

    /**
     * @2-Test
     */
    @Column(name = "lucky_score_more4")
    private Integer luckyScoreMore4;

    /**
     * @1-Test
     */
    @Column(name = "lucky_words1")
    private String luckyWords1;

    /**
     * @1-Test
     */
    @Column(name = "lucky_words2")
    private String luckyWords2;

    /**
     * @1-Test
     */
    @Column(name = "lucky_words3")
    private String luckyWords3;

    /**
     * @1-Test
     */
    @Column(name = "lucky_words4")
    private String luckyWords4;

    /**
     * @1-Test
     */
    @Column(name = "to_do")
    private String toDo;

    /**
     * @1-Test
     */
    @Column(name = "not_do")
    private String notDo;

    /**
     * @1-Test
     */
    @Column(name = "publish_time")
    private String publishTime;

    /**
     * @1-Test
     */
    @Column(name = "lucky_date")
    private String luckyDate;

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

    @Column(name = "status")
    private int status;

    public TiLucky(Long id, Long constellationId, String luckyType1, String luckyType2, String luckyType3, String luckyType4, Integer luckyScore1, Integer luckyScore2, Integer luckyScore3, Integer luckyScore4, String remindToday, String luckyTypeMore1, String luckyTypeMore2, String luckyTypeMore3, String luckyTypeMore4, Integer luckyScoreMore1, Integer luckyScoreMore2, Integer luckyScoreMore3, Integer luckyScoreMore4, String luckyWords1, String luckyWords2, String luckyWords3, String luckyWords4, String toDo, String notDo, String publishTime, String luckyDate, String createTimestamp, String updateTimestamp, int status) {
        this.id = id;
        this.constellationId = constellationId;
        this.luckyType1 = luckyType1;
        this.luckyType2 = luckyType2;
        this.luckyType3 = luckyType3;
        this.luckyType4 = luckyType4;
        this.luckyScore1 = luckyScore1;
        this.luckyScore2 = luckyScore2;
        this.luckyScore3 = luckyScore3;
        this.luckyScore4 = luckyScore4;
        this.remindToday = remindToday;
        this.luckyTypeMore1 = luckyTypeMore1;
        this.luckyTypeMore2 = luckyTypeMore2;
        this.luckyTypeMore3 = luckyTypeMore3;
        this.luckyTypeMore4 = luckyTypeMore4;
        this.luckyScoreMore1 = luckyScoreMore1;
        this.luckyScoreMore2 = luckyScoreMore2;
        this.luckyScoreMore3 = luckyScoreMore3;
        this.luckyScoreMore4 = luckyScoreMore4;
        this.luckyWords1 = luckyWords1;
        this.luckyWords2 = luckyWords2;
        this.luckyWords3 = luckyWords3;
        this.luckyWords4 = luckyWords4;
        this.toDo = toDo;
        this.notDo = notDo;
        this.publishTime = publishTime;
        this.luckyDate = luckyDate;
        this.createTimestamp = createTimestamp;
        this.updateTimestamp = updateTimestamp;
        this.status = status;
    }

    public TiLucky() {
        super();
    }

    /**
     * 获取运势
     *
     * @return id - 运势
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置运势
     *
     * @param id 运势
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取@2-星座id
     *
     * @return constellation_id - @2-星座id
     */
    public Long getConstellationId() {
        return constellationId;
    }

    /**
     * 设置@2-星座id
     *
     * @param constellationId @2-星座id
     */
    public void setConstellationId(Long constellationId) {
        this.constellationId = constellationId;
    }

    /**
     * 获取@1-Test
     *
     * @return lucky_type1 - @1-Test
     */
    public String getLuckyType1() {
        return luckyType1;
    }

    /**
     * 设置@1-Test
     *
     * @param luckyType1 @1-Test
     */
    public void setLuckyType1(String luckyType1) {
        this.luckyType1 = luckyType1 == null ? null : luckyType1.trim();
    }

    /**
     * 获取@1-Test
     *
     * @return lucky_type2 - @1-Test
     */
    public String getLuckyType2() {
        return luckyType2;
    }

    /**
     * 设置@1-Test
     *
     * @param luckyType2 @1-Test
     */
    public void setLuckyType2(String luckyType2) {
        this.luckyType2 = luckyType2 == null ? null : luckyType2.trim();
    }

    /**
     * 获取@1-Test
     *
     * @return lucky_type3 - @1-Test
     */
    public String getLuckyType3() {
        return luckyType3;
    }

    /**
     * 设置@1-Test
     *
     * @param luckyType3 @1-Test
     */
    public void setLuckyType3(String luckyType3) {
        this.luckyType3 = luckyType3 == null ? null : luckyType3.trim();
    }

    /**
     * 获取@1-Test
     *
     * @return lucky_type4 - @1-Test
     */
    public String getLuckyType4() {
        return luckyType4;
    }

    /**
     * 设置@1-Test
     *
     * @param luckyType4 @1-Test
     */
    public void setLuckyType4(String luckyType4) {
        this.luckyType4 = luckyType4 == null ? null : luckyType4.trim();
    }

    /**
     * 获取@2-Test
     *
     * @return lucky_score1 - @2-Test
     */
    public Integer getLuckyScore1() {
        return luckyScore1;
    }

    /**
     * 设置@2-Test
     *
     * @param luckyScore1 @2-Test
     */
    public void setLuckyScore1(Integer luckyScore1) {
        this.luckyScore1 = luckyScore1;
    }

    /**
     * 获取@2-Test
     *
     * @return lucky_score2 - @2-Test
     */
    public Integer getLuckyScore2() {
        return luckyScore2;
    }

    /**
     * 设置@2-Test
     *
     * @param luckyScore2 @2-Test
     */
    public void setLuckyScore2(Integer luckyScore2) {
        this.luckyScore2 = luckyScore2;
    }

    /**
     * 获取@2-Test
     *
     * @return lucky_score3 - @2-Test
     */
    public Integer getLuckyScore3() {
        return luckyScore3;
    }

    /**
     * 设置@2-Test
     *
     * @param luckyScore3 @2-Test
     */
    public void setLuckyScore3(Integer luckyScore3) {
        this.luckyScore3 = luckyScore3;
    }

    /**
     * 获取@2-Test
     *
     * @return lucky_score4 - @2-Test
     */
    public Integer getLuckyScore4() {
        return luckyScore4;
    }

    /**
     * 设置@2-Test
     *
     * @param luckyScore4 @2-Test
     */
    public void setLuckyScore4(Integer luckyScore4) {
        this.luckyScore4 = luckyScore4;
    }

    /**
     * 获取@1-Test
     *
     * @return remind_today - @1-Test
     */
    public String getRemindToday() {
        return remindToday;
    }

    /**
     * 设置@1-Test
     *
     * @param remindToday @1-Test
     */
    public void setRemindToday(String remindToday) {
        this.remindToday = remindToday == null ? null : remindToday.trim();
    }

    /**
     * 获取@1-Test
     *
     * @return lucky_type_more1 - @1-Test
     */
    public String getLuckyTypeMore1() {
        return luckyTypeMore1;
    }

    /**
     * 设置@1-Test
     *
     * @param luckyTypeMore1 @1-Test
     */
    public void setLuckyTypeMore1(String luckyTypeMore1) {
        this.luckyTypeMore1 = luckyTypeMore1 == null ? null : luckyTypeMore1.trim();
    }

    /**
     * 获取@1-Test
     *
     * @return lucky_type_more2 - @1-Test
     */
    public String getLuckyTypeMore2() {
        return luckyTypeMore2;
    }

    /**
     * 设置@1-Test
     *
     * @param luckyTypeMore2 @1-Test
     */
    public void setLuckyTypeMore2(String luckyTypeMore2) {
        this.luckyTypeMore2 = luckyTypeMore2 == null ? null : luckyTypeMore2.trim();
    }

    /**
     * 获取@1-Test
     *
     * @return lucky_type_more3 - @1-Test
     */
    public String getLuckyTypeMore3() {
        return luckyTypeMore3;
    }

    /**
     * 设置@1-Test
     *
     * @param luckyTypeMore3 @1-Test
     */
    public void setLuckyTypeMore3(String luckyTypeMore3) {
        this.luckyTypeMore3 = luckyTypeMore3 == null ? null : luckyTypeMore3.trim();
    }

    /**
     * 获取@1-Test
     *
     * @return lucky_type_more4 - @1-Test
     */
    public String getLuckyTypeMore4() {
        return luckyTypeMore4;
    }

    /**
     * 设置@1-Test
     *
     * @param luckyTypeMore4 @1-Test
     */
    public void setLuckyTypeMore4(String luckyTypeMore4) {
        this.luckyTypeMore4 = luckyTypeMore4 == null ? null : luckyTypeMore4.trim();
    }

    /**
     * 获取@2-Test
     *
     * @return lucky_score_more1 - @2-Test
     */
    public Integer getLuckyScoreMore1() {
        return luckyScoreMore1;
    }

    /**
     * 设置@2-Test
     *
     * @param luckyScoreMore1 @2-Test
     */
    public void setLuckyScoreMore1(Integer luckyScoreMore1) {
        this.luckyScoreMore1 = luckyScoreMore1;
    }

    /**
     * 获取@2-Test
     *
     * @return lucky_score_more2 - @2-Test
     */
    public Integer getLuckyScoreMore2() {
        return luckyScoreMore2;
    }

    /**
     * 设置@2-Test
     *
     * @param luckyScoreMore2 @2-Test
     */
    public void setLuckyScoreMore2(Integer luckyScoreMore2) {
        this.luckyScoreMore2 = luckyScoreMore2;
    }

    /**
     * 获取@2-Test
     *
     * @return lucky_score_more3 - @2-Test
     */
    public Integer getLuckyScoreMore3() {
        return luckyScoreMore3;
    }

    /**
     * 设置@2-Test
     *
     * @param luckyScoreMore3 @2-Test
     */
    public void setLuckyScoreMore3(Integer luckyScoreMore3) {
        this.luckyScoreMore3 = luckyScoreMore3;
    }

    /**
     * 获取@2-Test
     *
     * @return lucky_score_more4 - @2-Test
     */
    public Integer getLuckyScoreMore4() {
        return luckyScoreMore4;
    }

    /**
     * 设置@2-Test
     *
     * @param luckyScoreMore4 @2-Test
     */
    public void setLuckyScoreMore4(Integer luckyScoreMore4) {
        this.luckyScoreMore4 = luckyScoreMore4;
    }

    /**
     * 获取@1-Test
     *
     * @return lucky_words1 - @1-Test
     */
    public String getLuckyWords1() {
        return luckyWords1;
    }

    /**
     * 设置@1-Test
     *
     * @param luckyWords1 @1-Test
     */
    public void setLuckyWords1(String luckyWords1) {
        this.luckyWords1 = luckyWords1 == null ? null : luckyWords1.trim();
    }

    /**
     * 获取@1-Test
     *
     * @return lucky_words2 - @1-Test
     */
    public String getLuckyWords2() {
        return luckyWords2;
    }

    /**
     * 设置@1-Test
     *
     * @param luckyWords2 @1-Test
     */
    public void setLuckyWords2(String luckyWords2) {
        this.luckyWords2 = luckyWords2 == null ? null : luckyWords2.trim();
    }

    /**
     * 获取@1-Test
     *
     * @return lucky_words3 - @1-Test
     */
    public String getLuckyWords3() {
        return luckyWords3;
    }

    /**
     * 设置@1-Test
     *
     * @param luckyWords3 @1-Test
     */
    public void setLuckyWords3(String luckyWords3) {
        this.luckyWords3 = luckyWords3 == null ? null : luckyWords3.trim();
    }

    /**
     * 获取@1-Test
     *
     * @return lucky_words4 - @1-Test
     */
    public String getLuckyWords4() {
        return luckyWords4;
    }

    /**
     * 设置@1-Test
     *
     * @param luckyWords4 @1-Test
     */
    public void setLuckyWords4(String luckyWords4) {
        this.luckyWords4 = luckyWords4 == null ? null : luckyWords4.trim();
    }

    /**
     * 获取@1-Test
     *
     * @return to_do - @1-Test
     */
    public String getToDo() {
        return toDo;
    }

    /**
     * 设置@1-Test
     *
     * @param toDo @1-Test
     */
    public void setToDo(String toDo) {
        this.toDo = toDo == null ? null : toDo.trim();
    }

    /**
     * 获取@1-Test
     *
     * @return not_do - @1-Test
     */
    public String getNotDo() {
        return notDo;
    }

    /**
     * 设置@1-Test
     *
     * @param notDo @1-Test
     */
    public void setNotDo(String notDo) {
        this.notDo = notDo == null ? null : notDo.trim();
    }

    /**
     * 获取@1-Test
     *
     * @return publish_time - @1-Test
     */
    public String getPublishTime() {
        return publishTime;
    }

    /**
     * 设置@1-Test
     *
     * @param publishTime @1-Test
     */
    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime == null ? null : publishTime.trim();
    }

    /**
     * 获取@1-Test
     *
     * @return lucky_date - @1-Test
     */
    public String getLuckyDate() {
        return luckyDate;
    }

    /**
     * 设置@1-Test
     *
     * @param luckyDate @1-Test
     */
    public void setLuckyDate(String luckyDate) {
        this.luckyDate = luckyDate == null ? null : luckyDate.trim();
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}