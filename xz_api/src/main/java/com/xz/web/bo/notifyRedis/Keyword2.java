package com.xz.web.bo.notifyRedis;

import java.io.Serializable;

public class Keyword2 implements Serializable {

    private String keyword2;

    public Keyword2() {
    }

    public Keyword2(String keyword2) {
        this.keyword2 = keyword2;
    }

    public String getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(String keyword2) {
        this.keyword2 = keyword2;
    }
}
