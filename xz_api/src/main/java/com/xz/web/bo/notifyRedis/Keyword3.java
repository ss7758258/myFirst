package com.xz.web.bo.notifyRedis;

import java.io.Serializable;

public class Keyword3 implements Serializable {

    private String value;

    public Keyword3() {
    }

    public Keyword3(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
