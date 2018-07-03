package com.xz.web.bo.notifyRedis;

import java.io.Serializable;

public class Keyword2 implements Serializable {

    private String value;

    public Keyword2() {
    }

    public Keyword2(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
