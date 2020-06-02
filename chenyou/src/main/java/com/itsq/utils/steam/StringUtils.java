package com.itsq.utils.steam;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	
	public static String parseWhereString (HashMap<String, Object> params,String alias){
		StringBuffer buffer = new StringBuffer();
		if(params == null || params.isEmpty()){
			return "";
		}else{
			buffer.append(" where ");
			Set<Entry<String, Object>> set = params.entrySet();
			Iterator<Entry<String, Object>> iterator = set.iterator();
			Entry<String, Object> entry = null;
			while(iterator.hasNext()){
				buffer.append(" and ");
				entry = iterator.next();
				buffer.append(alias);
				if (!alias.equals("")) {
					buffer.append(".");
				}
				buffer.append(entry.getKey());
				buffer.append("=");
				buffer.append(" ? ");
				entry = null;
			}
			iterator = null;
			String temp = buffer.toString();
			int location = temp.indexOf("and");
			buffer = new StringBuffer();
			buffer.append(temp.substring(0, location));
			buffer.append(temp.substring(location+4));
			temp = null;
			return buffer.toString();
		}
	}

	/**
	 * 去掉开头结尾制定字符串
	 * @param stream
	 * @param trimstr
	 * @return
	 */
	public static String sideTrim(String stream, String trimstr) {
	    // null或者空字符串的时候不处理
	    if (stream == null || stream.length() == 0 || trimstr == null || trimstr.length() == 0) {
	      return stream;
	    }
	 
	    // 结束位置
	    int epos = 0;
	 
	    // 正规表达式
	    String regpattern = "[" + trimstr + "]*+";
	    Pattern pattern = Pattern.compile(regpattern, Pattern.CASE_INSENSITIVE);
	 
	    // 去掉结尾的指定字符 
	    StringBuffer buffer = new StringBuffer(stream).reverse();
	    Matcher matcher = pattern.matcher(buffer);
	    if (matcher.lookingAt()) {
	      epos = matcher.end();
	      stream = new StringBuffer(buffer.substring(epos)).reverse().toString();
	    }
	 
	    // 去掉开头的指定字符 
	    matcher = pattern.matcher(stream);
	    if (matcher.lookingAt()) {
	      epos = matcher.end();
	      stream = stream.substring(epos);
	    }
	 
	    // 返回处理后的字符串
	    return stream;
	}
	   /**
     * <p>Checks if a String is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     * @since 2.0
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * <p>Gets the substring before the last occurrence of a separator.
     * The separator is not returned.</p>
     *
     * <p>A <code>null</code> string input will return <code>null</code>.
     * An empty ("") string input will return the empty string.
     * An empty or <code>null</code> separator will return the input string.</p>
     *
     * <pre>
     * StringUtils.substringBeforeLast(null, *)      = null
     * StringUtils.substringBeforeLast("", *)        = ""
     * StringUtils.substringBeforeLast("abcba", "b") = "abc"
     * StringUtils.substringBeforeLast("abc", "c")   = "ab"
     * StringUtils.substringBeforeLast("a", "a")     = ""
     * StringUtils.substringBeforeLast("a", "z")     = "a"
     * StringUtils.substringBeforeLast("a", null)    = "a"
     * StringUtils.substringBeforeLast("a", "")      = "a"
     * </pre>
     *
     * @param str  the String to get a substring from, may be null
     * @param separator  the String to search for, may be null
     * @return the substring before the last occurrence of the separator,
     *  <code>null</code> if null String input
     * @since 2.0
     */
    public static String substringBeforeLast(String str, String separator) {
        if (isEmpty(str) || isEmpty(separator)) {
            return str;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }
    // Empty checks
    //-----------------------------------------------------------------------
    /**
     * <p>Checks if a String is empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the String.
     * That functionality is available in isBlank().</p>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}