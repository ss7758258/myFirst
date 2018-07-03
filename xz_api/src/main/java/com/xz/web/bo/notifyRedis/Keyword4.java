package com.xz.web.bo.notifyRedis;

import java.io.Serializable;

public class Keyword4 implements Serializable {

    private String value;

    public Keyword4() {
    }

    public Keyword4(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
