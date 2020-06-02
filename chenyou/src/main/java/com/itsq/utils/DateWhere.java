package com.itsq.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sq
 * @date 2020/3/24  11:20
 */
public class DateWhere {
    public static Map<String, Object> where(String time){
        Map<String,Object> map = new HashMap(2);
        if (time != null && !time.equals("")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date today = null;
            try {
                today = formatter.parse(time);
                map.put("today",today);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(today);
            cal.add(Calendar.DATE, 1);
            String preTomorrow = formatter.format(cal.getTime());
            Date tomorrow = null;
            try {
                tomorrow = formatter.parse(preTomorrow);
                map.put("tomorrow",tomorrow);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
