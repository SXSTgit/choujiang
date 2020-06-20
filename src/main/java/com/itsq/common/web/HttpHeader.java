package com.itsq.common.web;

/**
 * 发送请求时http请求header信息

 */
public class HttpHeader {

    /**
     * 加密后的用户id
     */
    private String userId;

    /**
     * 加密后的account id
     */
    private String accountId;

    /**
     * 授予给登陆用户的token信息
     */
    private String token;

    public HttpHeader(String userId,String accountId,String token){
        this.userId = userId;
        this.accountId = accountId;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
