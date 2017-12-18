package com.shsxt.xm.server;

public enum SmsType {

    REGISTER(1),
    NOTIFY(2);

    private Integer type;

    SmsType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
