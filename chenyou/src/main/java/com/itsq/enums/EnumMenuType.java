package com.itsq.enums;


public enum EnumMenuType {
    MENU("MENU", "菜单"),
    POINT("POINT", "功能点"),
    API("API", "API")
    ;

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    private final String code;
    private final String text;

    EnumMenuType(String code, String text) {
        this.code = code;
        this.text = text;
    }
}
