package com.itheima.mobileplayer.lyric;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:加载歌词
 * Copyright  : Copyright (c) 2016
 * Company    : 传智播客
 * Author     : 独孤求败
 * Date       : 2016/10/18 14:43
 */
public class LyricLoader {

    static  File rootFile = new File("/mnt/sdcard/Download/");

    /**
     * 从歌词文件中解析歌词
     */
    public static List<LyricBean> loadLyricsFormFile(File file){
        List<LyricBean> list = new ArrayList<>();
        if(file == null || !file.exists()){
            list.add(new LyricBean(0,"么有找到歌词！"));
            return list;
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String text = br.readLine();
            while(!TextUtils.isEmpty(text)){
                //[00:00.91]小苹果
                LyricBean lyricBean = parserLine(text);
                list.add(lyricBean);
                text = br.readLine();
            }

            br.close();

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            list.add(new LyricBean(0,"么有找到歌词！"));
            return list;
        }
    }

    /** 解析出一行歌词的起始时间 [01:45.51] 小苹果 */
    private static LyricBean parserLine(String text) {
        String[] arr = text.split("]");
        //歌词内容
        String content = arr[arr.length - 1];

        int time = 0;
        String[] arrs = arr[0].split(":");
        // [01 45.51
        String minStr = arrs[0].substring(1);

        // 45.51
        String[] arr2 = arrs[1].split("\\.");

        // 45 51
        String secStr = arr2[0];
        String mSecStr = arr2[1];
        //把 时 分 秒 都转成毫秒
        time = Integer.parseInt(minStr) * 60 * 1000
                + Integer.parseInt(secStr) *  1000
                + Integer.parseInt(mSecStr) * 10 ;
        return  new LyricBean(time,content);
    }

    /**
     * 根据音乐名称加载歌词
     */
    public static List<LyricBean> loadLyricsFormFile(String title) {
        List<LyricBean> list = new ArrayList<>();
        if (TextUtils.isEmpty(title)) {
            list.add(new LyricBean(0,"么有找到歌词！"));
            return list;
        }

        //从.lrc文件中加载歌词
        File f1 = new File(rootFile, title + ".lrc");
        if(f1.exists()){
            return  loadLyricsFormFile(f1);
        }

        //从.txt文件中加载歌词
        File f2 = new File(rootFile, title + ".txt");
        if(f2.exists()){
            return  loadLyricsFormFile(f2);
        }
        //联网下载歌词

        list.add(new LyricBean(0,"么有找到歌词！"));
        return list;
    }
}
