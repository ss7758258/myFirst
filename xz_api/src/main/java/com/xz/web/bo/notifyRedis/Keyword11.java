package com.xz.web.bo.notifyRedis;

import java.io.Serializable;

public class Keyword11 implements Serializable {

    private String value;
    private String color = "#5961dd";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
