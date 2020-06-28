package com.kuchuan.education.bean;

/**
 * Description: 更新下载状态
 * Copyright  : Copyright (c) 2017
 * Company    : 酷川科技 www.kuchuanyun.com
 * Author     : 李小松
 * Date       : 2017/8/31 on 11:32.
 */

public class UpdateDownloadStatus {
    public String packageName;//需要更新
    public UpdateDownloadStatus(String packageName){
        this.packageName = packageName;
    }
}
