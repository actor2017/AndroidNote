package com.kuchuan.wisdompolicehy.utils;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Description: 把bitmap转换成png并存储到指定目录,返回File/String路径,如果没有转换成功,返回null
 * Copyright  : Copyright (c) 2017
 * Company    : 酷川科技
 * Author     : 李小松
 * Date       : 2017/5/13 on 16:38.
 */

public class BitmapUtils {

    /**
     * todo 加入BitmapFactory的功能等...
     * 保存成一个路径,.png文件
     * @param bitmap
     * @param savePath 保存路径,可以为null
     * @param pngName png名称,不能为null,示例:getClass().getSimpleName() + pngName
     * @return 存储路径,存储错误返回null
     */
    public static String save2Path(Bitmap bitmap, @Nullable String savePath, @NonNull String pngName) {
        File file = save2PngFile(bitmap, savePath, pngName);
        if (file == null) return null;
        return file.getAbsolutePath();
    }

    /**
     * bitmap转换成.png的文件,如果要回显,用Glide实现,减少OOM几率
     * @param bitmap
     * @param savePath 要保存到哪个文件夹,可以null
     * @param pngName  给png取个名字,示例:getClass().getSimpleName() + pngName
     * @return 如果转换失败, return null
     * @throws IOException
     */
    public static File save2PngFile(Bitmap bitmap, @Nullable String savePath, @NonNull String pngName) {
        if (!pngName.toLowerCase().endsWith(".png")) {
            pngName = pngName.concat(".png");
        }
        if (savePath == null) {
            savePath = FileUtils.getExternalStorageDir();
        }
        File file = new File(savePath);
        if (!file.exists()) {//必须做判断,否则不行
            file.mkdirs();
        }
        file = new File(savePath, pngName);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)) {
                out.flush();
                out.close();
                return file;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
