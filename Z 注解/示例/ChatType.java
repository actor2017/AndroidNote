package com.ly.hihifriend.utils;

/**
 * Description: 聊天类型, 语音 & 视频 & 约会
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/6/6 on 09:57
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface ChatType {

    int ChatType_AUDIO = 0;
    int ChatType_VIDEO = 1;
    int ChatType_DATE = 2;

    int[] value() default {
        ChatType_AUDIO, ChatType_VIDEO, ChatType_DATE
    };
}
