package com.itsq.common.bean;

public enum ErrorEnum {
    SUCCESS(0, "success"),
    FAIL(-1, "error"),
    ERROR_SERVER(50000, "服务异常,请稍后重试"),
    UPLOAD_ERROR(10016, "文件上传失败！"),
    USER_NOT_EXITES(401000,"用户不存在"),
    USER_AUTH_FAILED(401001, "用户授权失败"),
    USER_INFO_ERROR(401004,"用户名或密码错误"),
    SIGN_VERIFI_ERROR(401002,"token解析错误"),
    SIGN_VERIFI_EXPIRE(401003,"token已过期"),
    MANAGER_NOT_ROLE(401005,"没有此权限"),
    INVALID_PARAM(400001, "非法参数"),
    USER_EXITES(100001,"用户已存在"),
    USER_PASSWORDERROR(100008,"密码错误"),
    USER_ISSTATUS(100007,"该账户已被冻结"),
    USER_PHONE(100017,"该手机号已存在"),
    USER_ADMINNAME(100016,"该登录名已存在"),
    USER_IDENTITY(100015,"该身份证已存在"),
    USER_IDENTITYNOT(100014,"身份证格式不正确"),
    USER_ISNULL(100081,"收件人信息为空"),

    ;


    private Integer code;
    private String message;

    ErrorEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }


    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
