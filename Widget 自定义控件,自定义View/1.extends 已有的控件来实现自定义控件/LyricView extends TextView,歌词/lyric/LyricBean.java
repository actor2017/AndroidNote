package com.itheima.mobileplayer.lyric;

/**
 * Description:歌词的JavaBean
 * Copyright  : Copyright (c) 2016
 * Company    : 传智播客
 * Author     : 独孤求败
 * Date       : 2016/10/18 10:48
 */
public class LyricBean {
    public int    startPoint;//这句歌词开始播放时间
    public String content;//这句歌词的内容

    public LyricBean(int startPoint, String content) {
        this.startPoint = startPoint;
        this.content = content;
    }

    @Override
    public String toString() {
        return "LyricBean{" +
                "startPoint=" + startPoint +
                ", content='" + content + '\'' +
                '}';
    }
}
