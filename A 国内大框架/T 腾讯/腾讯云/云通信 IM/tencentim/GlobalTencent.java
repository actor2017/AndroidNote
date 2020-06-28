package com.ly.hihifriend.utils.tencentim;

/**
 * Description: 类的描述
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/4/14 on 16:21
 */
public class GlobalTencent {
    private static final int CONSTANT = 45648351;//随意+一个常量, 尽量避免重复

//    public static final int ON_NEW_MESSAGE = CONSTANT + 1;//当收到新消息
    public static final int ON_REFRESH_CONVERSATION = CONSTANT + 2;//当收到新消息
    public static final int TIM_GROUP_SYSTEM_CUSTOM_INFO = CONSTANT + 3;//系统群自定义消息

    public static final int TIM_TEXT_C2C = CONSTANT + 4;//收到C2C消息
    public static final int TIM_TEXT_GROUP = CONSTANT + 5;//收到群聊消息

    public static final int TIM_IMAGE_C2C = CONSTANT + 6;//收到C2C图片
    public static final int TIM_IMAGE_GROUP = CONSTANT + 7;//收到群聊图片

    public static final int TIM_VIDEO_C2C = CONSTANT + 8;//收到C2C视频
    public static final int TIM_VIDEO_GROUP = CONSTANT + 9;//收到群聊视频

    public static final int TIM_AUDIO_C2C = CONSTANT + 10;//收到C2C语音
    public static final int TIM_AUDIO_GROUP = CONSTANT + 11;//收到群聊语音

    public static final int TIM_CUSTOM = CONSTANT + 12;//收到自定义消息
}
