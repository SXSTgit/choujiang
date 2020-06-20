package com.itsq.enums;

public enum EnumTokenType {
    MANAGER("manager","管理端"),
    BEARER("bearer","普通")
    ;

    private final String code;

    private final String text;

    EnumTokenType(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }



}
