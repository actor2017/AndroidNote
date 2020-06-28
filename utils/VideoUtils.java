package com.ly.hihifriend.utils;

/**
 * Description: 视频帮助类
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/6/10 on 02:51
 */
public class VideoUtils {

    public static int[] getVideoSize(String videoPath) {
        android.media.MediaMetadataRetriever mmr = new android.media.MediaMetadataRetriever();
        mmr.setDataSource(videoPath);
        String duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);//时长(毫秒)
        String width = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);//宽
        String height = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);//高
        mmr.release();
        if (width != null && height != null) return new int[]{Integer.parseInt(width), Integer.parseInt(height)};
        return new int[]{90, 120};
    }
}
