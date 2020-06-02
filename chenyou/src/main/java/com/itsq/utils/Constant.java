package com.itsq.utils;

/**
 * @author sq
 * @date 2020/2/28  11:51
 */
public class Constant {
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";
    public static final String SPLIT = "#";
    public static final Integer NUMBER_ZORE = 0;
    public static final Integer NUMBER_ONE = 1;
    public static final Integer NUMBER_TWO = 2;
    public static final Integer NUMBER_THRRE = 3;
    public static final Integer NUMBER_FOUR = 4;
    public static final String STRING_ZORE = "0";
    public static final String SUPER_ADMIN_ID = "1";
    public static final String DATA_SCOPE = "dataScope";

    public Constant() {
    }

    public static enum SysLogEnum {
        LOG_TYPE_ADD("add", "新增"),
        LOG_TYPE_DEL("delete", "删除"),
        LOG_TYPE_UPDATE("update", "更新"),
        LOG_TYPE_VIEW("view", "查看"),
        LOG_TYPE_LOGIN("login", "登录"),
        LOG_TYPE_LAYOUT("layout", "退出");

        private String code;
        private String name;

        private SysLogEnum(String code, String name) {
            this.setCode(code);
            this.setName(name);
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
