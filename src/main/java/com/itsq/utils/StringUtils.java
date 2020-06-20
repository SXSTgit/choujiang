package com.itsq.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 字符串工具类，继承lang3字符串工具类
 * @author L.com
 */
public final class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String encode(String str){
        String encode=null;
        try {
            encode = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encode;
    }
    
    /**
     * 获取UUID，去掉`-`的
     * @return {String}
     * 
     */
    public static String generateStr () {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    /**
     * 要求外部订单号必须唯一。
     * @return {String}
     */
    public  static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);
        key = key + System.currentTimeMillis();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * 字符串格式化
     * 
     * use: format("my name is {0}, and i like {1}!", "L.cm", "java")
     * 
     * int long use {0,number,#}
     * 
     * @param s 
     * @param args
     * @return {String}转换后的字符串
     */
    public static String format(String s, Object... args) {
        return MessageFormat.format(s, args);
    }
    
    /**
     * 替换某个字符
     * @param str
     * @param regex
     * @param args
     * @return {String}
     */
    public static String replace(String str,String regex,String... args){
        int length = args.length;
        for (int i = 0; i < length; i++) {
            str=str.replaceFirst(regex, args[i]);
        }
        return str;
    }

    /**
     *  随机字符串
     */
    private static final String INT_TEMP = "0123456789";
    private static final String STR_TEMP = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String ALL_TEMP = INT_TEMP + STR_TEMP;

    private static final Random RANDOM = new Random();

    /**
     * 生成的随机数类型
     */
    public enum RandomType {
        /**
         * 整数
         */
        INT,
        /**
         * 字符串
         */
        STRING,
        /**
         * 所有类型
         */
        ALL
    }

    /**
     * 随机数生成
     * @param count
     * @return {String}
     */
    public static String random(int count, RandomType randomType) {
        if (count == 0) {
            return "";
        }
        if (count < 0) {
            throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
        }
        char[] buffer = new char[count];
        for (int i = 0; i < count; i++) {
            if (randomType.equals(RandomType.INT)) {
                buffer[i] = INT_TEMP.charAt(RANDOM.nextInt(INT_TEMP.length()));
            } else if (randomType.equals(RandomType.STRING)) {
                buffer[i] = STR_TEMP.charAt(RANDOM.nextInt(STR_TEMP.length()));
            }else {
                buffer[i] = ALL_TEMP.charAt(RANDOM.nextInt(ALL_TEMP.length()));
            }
        }
        return new String(buffer);
    }

    /**
     * 将异步通知的参数转化为Map
     *
     * @param request {HttpServletRequest}
     * @return 转化后的Map
     */
    public static Map<String, String> toMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return params;
    }
    public static void main(String[] args) {
        System.out.println(random(32, RandomType.ALL));
    }
}