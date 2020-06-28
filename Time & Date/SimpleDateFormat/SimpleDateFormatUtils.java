package com.shijing.tobecomegod.utils;

/**
 * Description: 类的描述
 * Copyright  : Copyright (c) 2017
 * Company    : 重庆酷川科技有限公司 www.kuchuanyun.com
 * Author     : 李大发
 * Date       : 2017/11/6 on 13:50
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
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
public class SimpleDateFormatUtils {

    public String date2String(String pattern, Date date) {//"现在是yyyy年MM月dd日 HH(hh)时   mm分 ss秒 S毫秒
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
    public Date string2Date(String pattern, String date) {//"yyyy-MM-dd","2017-3-21"
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
