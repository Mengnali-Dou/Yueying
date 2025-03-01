package com.yueying.backendapi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 共用方法
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
public class PublicMethods {

    /**
     * string转datetime
     * @param str string
     * @return datetime
     */
    public static Date stringConvertToDateTime(String str) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        try {
            date = dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * datetime转string
     * @param date datetime
     * @return string
     */
    public static String dateTimeConvertToString(Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * string转date
     * @param str string
     * @return date
     */
    public static Date stringConvertToDate(String str) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * date转string
     * @param date date
     * @return string
     */
    public static String dateConvertToString(Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    /**
     * string转time
     * @param str string
     * @return 时间
     */
    public static Date stringConvertToTime(String str) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        Date date = new Date();
        try {
            date = dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * time转string
     * @param time 时间
     * @return string
     */
    public static String timeConvertToString(Date time) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        return dateFormat.format(time);
    }
}
