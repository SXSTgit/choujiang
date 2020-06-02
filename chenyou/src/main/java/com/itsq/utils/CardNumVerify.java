package com.itsq.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sq
 * @Classname CardNumVerify
 * @Date 2019/12/14 下午12:54
 * @Description
 */
public class CardNumVerify {
    /**
     * 检验身份证是否合法
     * @return true-合法；false-不合法
     */
    public static boolean cardNumIsLegal(String cardNum){
        String info = "";
        final char[] VERIFY_CODE = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' }; // 18位身份证中最后一位校验码
        final int[] VERIFY_CODE_WEIGHT = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };// 18位身份证中，各个数字的生成校验码时的权值
        // 前17位验证
        if (cardNum.length() == 18) {
            int falseNum = 0;
            for (int i = 0; i < 17; i++) {
                char ch = cardNum.charAt(i);
                if (!Character.isDigit(ch)) {
                    falseNum += 1;
                }
            }
            if (falseNum > 0) {
                info = "身份证前17位须为阿拉伯数字";
            } else {
                // 出身日期验证
                int falseDate = 0;
                String birthDate = cardNum.substring(6, 14);
                String year = cardNum.substring(6, 10);
                String month = cardNum.substring(10, 12);
                String day = cardNum.substring(12, 14);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String str = sdf.format(new Date());

                if (Integer.parseInt(year) % 4 == 0) {
                    if (month.equals("02")) {
                        if (day.compareTo("29") > 0 || day.equals("00")) {
                            falseDate += 1;
                        }
                    } else if (Integer.parseInt(day) > 0 || Integer.parseInt(day) < 8
                            || Integer.parseInt(day) % 2 == 0) {
                        if (day.compareTo("31") > 0 || day == "00") {
                            falseDate += 1;
                        }
                    } else if (Integer.parseInt(day) > 0 || Integer.parseInt(day) < 8
                            || Integer.parseInt(day) % 2 == 1) {
                        if (day.compareTo("30") > 0 || day == "00") {
                            falseDate += 1;
                        }
                    } else if (Integer.parseInt(day) > 7 || Integer.parseInt(day) < 13
                            || Integer.parseInt(day) % 2 == 1) {
                        if (day.compareTo("31") > 0 || day == "00") {
                            falseDate += 1;
                        }
                    } else if (Integer.parseInt(day) > 7 || Integer.parseInt(day) < 13
                            || Integer.parseInt(day) % 2 == 0) {
                        if (day.compareTo("30") > 0 || day == "00") {
                            falseDate += 1;
                        }
                    }
                } else {
                    if (month.equals("02")) {
                        if (day.compareTo("28") > 0 || day.equals("00")) {
                            falseDate += 1;
                        }
                    } else if (Integer.parseInt(day) > 0 || Integer.parseInt(day) < 8
                            || Integer.parseInt(day) % 2 == 0) {
                        if (day.compareTo("31") > 0 || "00".equals(day)) {
                            falseDate += 1;
                        }
                    } else if (Integer.parseInt(day) > 0 || Integer.parseInt(day) < 8
                            || Integer.parseInt(day) % 2 == 1) {
                        if (day.compareTo("30") > 0 || "00".equals(day)) {
                            falseDate += 1;
                        }
                    } else if (Integer.parseInt(day) > 7 || Integer.parseInt(day) < 13
                            || Integer.parseInt(day) % 2 == 1) {
                        if (day.compareTo("31") > 0 || "00".equals(day)) {
                            falseDate += 1;
                        }
                    } else if (Integer.parseInt(day) > 7 || Integer.parseInt(day) < 13
                            || Integer.parseInt(day) % 2 == 0) {
                        if (day.compareTo("30") > 0 || "00".equals(day)) {
                            falseDate += 1;
                        }
                    }
                }

                if (birthDate.compareTo(str) > 0 || birthDate.compareTo("19000101") < 0 || month.compareTo("12") > 0
                        || "00".equals(month)) {
                    falseDate += 1;
                }

                if (falseDate > 0) {
                    info = "身份证上的出生日期非法";
                } else {
                    // 地区验证
                    String[] aCity = { "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35",
                            "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62",
                            "63", "64", "65", "71", "81", "82", "91" };
                    String cityID = cardNum.substring(0, 2);
                    int trueCity = 0;
                    for (int i = 0; i < aCity.length; i++) {
                        if (aCity[i].equals(cityID)) {
                            trueCity += 1;
                        }
                    }
                    if (trueCity == 0) {
                        info = "你的身份证地区非法";
                    } else {
                        // 校验位验证
                        int sum = 0;
                        for (int i = 0; i < 17; i++) {
                            char ch = cardNum.charAt(i);
                            sum += ((int) (ch - '0')) * VERIFY_CODE_WEIGHT[i];
                        }
                        if (VERIFY_CODE[sum % 11] != cardNum.toUpperCase().charAt(17)) {
                            info = "身份证号码错误";
                        }
                    }
                }
            }
        } else {
            info = "身份证不等于18位错误";
        }

        if (CommonUtils.isNullOrEmpty(info)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 对18位身份证，a、转换成15位；b、截取18位的最后10位对15位身份证，截取15位的最后10位
     *
     * @param idNo
     * @return
     */
    public static Map<String, String> getShortIdNo(String idNo) {
        Map<String, String> shortIdMap = new HashMap<>();
        if (!CommonUtils.isNullOrEmpty(idNo)) {
            // 如果传入身份证为18位，截取保存到shortIdNo1中
            String substring = idNo.substring(idNo.length() - 10);
            if (18 == idNo.length()) {
                shortIdMap.put("shortIdNo1", substring.toUpperCase());
                // 将18位转为15位，做一次15位的截取
                String id15No = getId15No(idNo);
                shortIdMap.put("shortIdNo2",id15No.substring(id15No.length() - 10));
            } else if (15 == idNo.length()) {
                // 15位截取放到shortIdNo2中
                shortIdMap.put("shortIdNo2", substring);
            }
        }
        return shortIdMap;
    }

    /**
     * 将18为身份证转为15位，删除出身年份的头两位和最后一位
     *
     * @param idNo
     * @return
     */
    public static String getId15No(String idNo) {
        String locationCode = idNo.substring(0, 6);
        String bDayAndV = idNo.substring(8, 17);
        return locationCode + bDayAndV;
    }

    /**
     * 根据身份证号判断性别
     */
    public static String getGenderByCardNum(String cardNum){
        //根据身份证号判断性别，第17位判断男女（奇数为男，偶数为女）
        if(1 == Integer.parseInt(cardNum.substring(16,17))%2){
            return "男";
        }else{
            return "女";
        }
    }

}
