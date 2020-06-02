package com.itsq.utils;

import org.joda.time.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sq
 * @date 2020/2/27  16:06
 *  不要乱动
 */
public class DateUtils {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATETIME_UNSIGNED_FORMAT = "yyyyMMddHHmmss";
    private static final String DATE_UNSIGNED_FORMAT = "yyyyMMdd";

    public DateUtils() {
    }

    public static String getUnsignedDateTime() {
        DateTime dateTime = DateTime.now();
        return dateTime.toString("yyyyMMddHHmmss");
    }

    public static String getUnsignedDate() {
        DateTime dateTime = DateTime.now();
        return dateTime.toString("yyyyMMdd");
    }

    public static String getCurrentDate() {
        DateTime dateTime = DateTime.now();
        return dateTime.toString("yyyy-MM-dd");
    }

    public static String getCurrentDateTime() {
        DateTime dateTime = DateTime.now();
        return dateTime.toString("yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurrentDateTime(String pattern) {
        DateTime dateTime = DateTime.now();
        return dateTime.toString(pattern);
    }

    public static Date formatDate(String dateStr) throws ParseException {
        return formatDate(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date formatDate(String dateStr, String pattern) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.parse(dateStr);
    }

    public static String formatDateToPattern(String dateStr, String pattern) {
        DateTime dateTime = new DateTime(dateStr);
        return dateTime.toString(pattern);
    }

    public static String formatDateToStr(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString("yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDateToStr(Date date, String pattern) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(pattern);
    }

    public static boolean dateIsAfterCuttrntDate(String dateStr) {
        DateTime d1 = new DateTime(dateStr);
        DateTime d2 = DateTime.now();
        return d1.isAfter(d2);
    }

    public static boolean dateIsBeforeCuttrntDate(String dateStr) {
        DateTime d1 = new DateTime(dateStr);
        DateTime d2 = DateTime.now();
        return d1.isBefore(d2);
    }

    public static boolean dateIsEqualCuttrntDate(String dateStr) {
        return dateIsEqual(dateStr, getCurrentDate());
    }

    public static boolean dateIsEqual(String date1, String date2) {
        DateTime d1 = new DateTime(date1);
        DateTime d2 = new DateTime(date2);
        return d1.isEqual(d2);
    }

    public static String getYesterday() {
        DateTime dt = new DateTime();
        DateTime yesterday = dt.minusDays(1);
        return yesterday.toString("yyyy-MM-dd HH:mm:ss");
    }

    public static String getTomorrowDay() {
        DateTime dt = new DateTime();
        DateTime yesterday = dt.plusDays(1);
        return yesterday.toString("yyyy-MM-dd HH:mm:ss");
    }

    public static int daysOf2Day(String day1, String day2) {
        DateTime begin = new DateTime(day1);
        DateTime end = new DateTime(day2);
        Period p = new Period(begin, end, PeriodType.days());
        return p.getDays();
    }

    public static boolean containsDay(String day1, String day2, String day3) {
        DateTime begin = new DateTime(day1);
        DateTime end = new DateTime(day2);
        Interval i = new Interval(begin, end);
        return i.contains(new DateTime(day3));
    }

    public static long get2DaysMillis(String day1, String day2) {
        DateTime begin = new DateTime(day1);
        DateTime end = new DateTime(day2);
        Duration d = new Duration(begin, end);
        return d.getMillis();
    }

    public static int getYear() {
        DateTime dt = new DateTime();
        return dt.getYear();
    }

    public static int getMonthOfYear() {
        DateTime dt = new DateTime();
        return dt.getMonthOfYear();
    }

    public static int getDayOfMonth() {
        DateTime dt = new DateTime();
        return dt.getDayOfMonth();
    }

    public static int getHourOfDay() {
        DateTime dt = new DateTime();
        return dt.getHourOfDay();
    }

    public static int getMinuteOfHour() {
        DateTime dt = new DateTime();
        return dt.getMinuteOfHour();
    }

    public static int getSecondOfMinute() {
        DateTime dt = new DateTime();
        return dt.getSecondOfMinute();
    }

    public static int getMillisOfSecond() {
        DateTime dt = new DateTime();
        return dt.getMillisOfSecond();
    }

    public static String getFirstdayOfMonth() {
        DateTime dt = new DateTime();
        DateTime lastday = dt.dayOfMonth().withMinimumValue();
        return lastday.toString("yyyy-MM-dd");
    }

    public static String getLastdayOfMonth() {
        DateTime dt = new DateTime();
        DateTime lastday = dt.dayOfMonth().withMaximumValue();
        return lastday.toString("yyyy-MM-dd");
    }

    public static String getDayOfWeek() {
        return getDayOfWeek(getCurrentDate());
    }

    public static String getDayOfWeek(String dateStr) {
        String currentWeek = "";
        DateTime dt = new DateTime(dateStr);
        switch(dt.getDayOfWeek()) {
            case 1:
                currentWeek = "星期一";
                break;
            case 2:
                currentWeek = "星期二";
                break;
            case 3:
                currentWeek = "星期三";
                break;
            case 4:
                currentWeek = "星期四";
                break;
            case 5:
                currentWeek = "星期五";
                break;
            case 6:
                currentWeek = "星期六";
                break;
            case 7:
                currentWeek = "星期日";
        }

        return currentWeek;
    }

    public static int getBeforeCurrentMonth() {
        DateTime dt = new DateTime();
        DateTime beforeMonth = dt.minusMonths(1);
        return beforeMonth.getMonthOfYear();
    }

    public static void main(String[] args) {
        System.out.println(formatDateToStr(new Date()));
    }
}
