package com.xz.web.bo.notifyRedis;

import java.io.Serializable;

public class LuckyRemindDataBo implements Serializable {

    private String lucky;
    private String nickname;
    private String remind;
    private String remark;

    public String getLucky() {
        return lucky;
    }

    public void setLucky(String lucky) {
        this.lucky = lucky;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
