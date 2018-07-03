package com.xz.web.bo.notifyRedis;

import java.io.Serializable;

public class Keyword1 implements Serializable{

    private Keyword11 keyword1;

    public Keyword1() {
    }

    public Keyword1(Keyword11 keyword1) {
        this.keyword1 = keyword1;
    }

    public Keyword11 getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(Keyword11 keyword1) {
        this.keyword1 = keyword1;
    }
}
