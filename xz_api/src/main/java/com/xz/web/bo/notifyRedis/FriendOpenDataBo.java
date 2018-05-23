package com.xz.web.bo.notifyRedis;

import java.io.Serializable;

public class FriendOpenDataBo implements Serializable {

    /*private String serviceType;
    private String friendNickname;
    private String openTime;
    private String remark;*/

    private Keyword11 keyword1;
    private Keyword2 keyword2;
    private Keyword3 keyword3;
    private Keyword4 keyword4;

    public Keyword11 getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(Keyword11 keyword1) {
        this.keyword1 = keyword1;
    }

    public Keyword2 getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(Keyword2 keyword2) {
        this.keyword2 = keyword2;
    }

    public Keyword3 getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(Keyword3 keyword3) {
        this.keyword3 = keyword3;
    }

    public Keyword4 getKeyword4() {
        return keyword4;
    }

    public void setKeyword4(Keyword4 keyword4) {
        this.keyword4 = keyword4;
    }

}
