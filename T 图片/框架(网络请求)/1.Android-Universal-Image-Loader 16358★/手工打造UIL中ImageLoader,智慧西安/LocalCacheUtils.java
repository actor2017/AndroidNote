package cn.itcast.zhxa12.utils.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

import cn.itcast.zhxa12.utils.MD5Encoder;

import static android.R.attr.bitmap;

/**
 * Created by zhengping on 2017/2/28,10:13.
 * 本地缓存
 */

public class LocalCacheUtils {

    public void saveBitmap2Local(Bitmap bitmap,String url) {
        try {
            //sdcard
            File sdcardRoot = Environment.getExternalStorageDirectory();
            File appDir = new File(sdcardRoot, "zhxa12");
            if(!appDir.exists()||appDir.isFile()) {
                appDir.mkdirs();
            }
            String fileName = MD5Encoder.encode(url);
            File savedFile = new File(appDir,fileName);
			//Bitmap压缩保存
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(savedFile));
        } catch (Exception e) {

        }


    }

    public Bitmap getBitmapFromLocal(String url) {
        try {
            //sdcard
            File sdcardRoot = Environment.getExternalStorageDirectory();
            File appDir = new File(sdcardRoot, "zhxa12");
            if(!appDir.exists()||appDir.isFile()) {
                appDir.mkdirs();
            }
            String fileName = MD5Encoder.encode(url);
            File savedFile = new File(appDir,fileName);
            Bitmap bitmap = BitmapFactory.decodeFile(savedFile.getAbsolutePath());
            return bitmap;
        } catch (Exception e) {
        }
        return null;
    }
}
