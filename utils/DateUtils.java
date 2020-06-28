package com.kuchuanyun.wisdompolicehy4sd.utils;

import android.support.annotation.IntRange;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Description: 日期工具
 * Copyright  : Copyright (c) 2018
 * Company    : 重庆酷川科技有限公司 www.kuchuanyun.com
 * Author     : 李大发
 * Date       : 2018/8/13 on 16:09
 *
 * G 年代标志符
 * yyyy 年
 * MM 月
 * dd 日
 * hh 时 12小时制(01-12)
 * HH 时 24小时制(00-23)
 * mm 分
 * ss 秒
 * S 毫秒
 * D 一年中的第几天
 * E 星期几
 * F 一月中第几个星期(以每个月1号为第一周,8号为第二周为标准计算)
 * w 一年中第几个星期
 * W 一月中第几个星期(不同于F的计算标准,是以星期为标准计算星期数,例如1号是星期三,是当月的第一周,那么5号为星期日就已经是当月的第二周了)
 * a 上午 / 下午 标记符
 * k 时 (24小时制,其值与H的不同点在于,当数值小于10时,前面不会有0)
 * K 时 (12小时值,其值与h的不同点在于,当数值小于10时,前面不会有0)
 * z 时区
 */

public class DateUtils extends android.text.format.DateUtils {

    /**
     * 获取相对今天的Calendar, 1:明天    -1:昨天
     * @return
     */
    public static Calendar getIndexCalendar(int index) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + index);
        return calendar;
    }

    /**
     * 把Data转换成年月日时分秒
     * @param date
     * @return
     */
    public static String getStringyMdHms(Date date) {
        return date2String("yyyy-MM-dd HH:mm:ss", date);
    }

    /**
     * 获取相对今天的Date, 1:明天    -1:昨天
     */
    public static Date getIndexDate(int index) {
        return getIndexCalendar(index).getTime();
    }

    /**
     * 返回String类型指定天数年月日
     */
    public static String getIndexStringyMd(int index) {
        return date2String("yyyy-MM-dd", getIndexDate(index));
    }

    /**
     * 返回String类型指定天数时分秒
     */
    public static String getIndexStringHms(int index) {
        return date2String("HH:mm:ss", getIndexDate(index));
    }

    /**
     * 返回String类型指定天数年月日时分秒
     */
    public static String getIndexStringyMdHms(int index) {
        return date2String("yyyy-MM-dd HH:mm:ss", getIndexDate(index));
    }

    /**
     * 返回指定天数开始时间
     */
    public static Date getIndexDateDayBegin(int index) {
        Calendar calendar = getIndexCalendar(index);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取String类型指定天数开始时间的年月日时分秒
     * @param index
     * @return
     */
    public static String getIndexStringyMdHmsDayBegin(int index) {
        return date2String("yyyy-MM-dd HH:mm:ss", getIndexDateDayBegin(index));
    }

    /**
     * 获取指定天数结束时间
     */
    public static Date getIndexDateDayEnd(int index) {
        Calendar calendar = getIndexCalendar(index);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取String类型指定天数结束时间的年月日时分秒
     * @param index
     * @return
     */
    public static String getIndexStringyMdHmsDayEnd(int index) {
        return date2String("yyyy-MM-dd HH:mm:ss", getIndexDateDayEnd(index));
    }

    /**
     * 获取相对本周的指定周的指定天
     * @param weekIndex 0:本周    1:下一周   -1:上一周
     * @param dayIndex 本周第几天,星期日开始,0-6
     */
    public static Date getIndexDateDayOfWeek(int weekIndex, @IntRange(from = 0, to = 6) int dayIndex) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.WEEK_OF_MONTH, calendar.get(Calendar.WEEK_OF_MONTH) + weekIndex);
        calendar.set(Calendar.DAY_OF_WEEK, dayIndex + 1);//星期日开始,星期日=1,取值1-7
//         int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);//星期日1-7
//         if (dayofweek == 1) {
//             dayofweek += 7;
//         }
//         calendar.add(Calendar.DAY_OF_MONTH, 2 - dayofweek);
        return calendar.getTime();
    }

    /**
     * 获取指定周的结束时间
     */
//    public static Date getEndDayOfWeek(int index){
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(getBeginDayOfWeek(index));
//        cal.add(Calendar.DAY_OF_WEEK, 6);
//        Date weekEndSta = cal.getTime();
//        return getDayEndTime(weekEndSta);
//    }

    public static String date2String(String pattern, Date date) {//"现在是yyyy年MM月dd日 HH(hh)时   mm分 ss秒 S毫秒
        //星期E 今年的第D天  这个月的第F星期   今年的第w个星期   这个月的第W个星期  今天的a k1~24制时间 K0-11小时制时间 z时区"
        // new Date()
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);//现在是2017年01月01日 13(01)时   11分 44秒 433毫秒
        // 星期星期日 今年的第1天  这个月的第1星期   今年的第1个星期 这个月的第1个星期  今天的下午 131~24制时间 10-11小时制时间 CST时区
    }

    /**
     * 如果解析失败,返回null
     *
     * @param pattern
     * @param date
     * @return
     */
    public static Date string2Date(String pattern, String date) {//"yyyy-MM-dd","2017-3-21"
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    //前面是否跟0
    private static String head0(int date) {
        return date > 9 ? String.valueOf(date) : "0" + date;
    }
}
