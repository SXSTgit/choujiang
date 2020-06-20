package com.itsq.token;

/**
 * @author: sq
 * @Description:
 * @Date: 2018-10-19 17:47
 */
public class CookieConstants {


    public static final String SESSION_AUTH_TOKEN = "session_authToken";
    public static final String SESSION_USER_ID = "userId";

    /**
     * APP AuthToken 最长过期时间（1年）
     */
    public static final int AUTH_TOKEN_AGE_MAX = 30 * 24 * 3600;
    public static final String AUTH_TOKEN_NAME = "_MCH_AT";

    /**
     * token默认名称
     */
    public static final String AUTH_TOKEN_NAME_DEFAULT = "token";

}
