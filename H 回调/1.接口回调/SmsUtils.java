
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemClock;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Description: 短信工具类,1.回调短信条数和备份/还原进度,2.备份短信,3.还原短信,本方法需要try--catch
 * 需要改进:1.备份的时候该判断一下短信是否存在,2.还原的时候,还原的短信的右边的时间,在模拟器上全部一样的??
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/2/11 on 18:23.
 * <p>
 * <uses-permission android:name="android.permission.READ_SMS"/>
 * <uses-permission android:name="android.permission.WRITE_SMS"/>
 */

public class SmsUtils {
    /**
     * @return 默认的路径, 在data/data/packageName/files/instant-run/smsBackUp.backup
     */
    public static File defaultbackupPath(Context ctx) {
        return new File(ctx.getFilesDir().getAbsolutePath(), "/smsBackUp.backup");
    }

    /**
     * 短信备份,file:要保存到什么地方.  cllback:短信条数,备份/还原短信的回调
     * file传参的示例路径写法:
     * File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
     * +"/smsBackUp.backup");
     */
    public static void SmsBackup(Context ctx, File backupPath, OnSmsCallback
            callback) throws IOException {
        //1. 读取短信数据库: data/data/com.android.provider.telephony/databases/mmssms.db/sms
        final Cursor cursor = ctx.getContentResolver().query(Uri.parse("content://sms"), new
                String[]{"address", "date", "read", "type", "body"}, null, null, null);

        //获取短信总数, select count(*) from xx;
        callback.getSmsCount(cursor.getCount());           //返回当前游标的数据数量,回调

        final ArrayList<SmsInfo> list = new ArrayList<>();

        if (cursor != null) {
            int progress = 0;               //进度初始化
            while (cursor.moveToNext()) {   //这儿写成while,不要写成if,调试了很久
                SmsInfo smsInfo = new SmsInfo();
                smsInfo.address = cursor.getString(cursor.getColumnIndex("address"));
                smsInfo.date = cursor.getString(cursor.getColumnIndex("date"));
                smsInfo.read = cursor.getString(cursor.getColumnIndex("read"));
                smsInfo.type = cursor.getString(cursor.getColumnIndex("type"));
                smsInfo.body = cursor.getString(cursor.getColumnIndex("body"));

                list.add(smsInfo);                      //把对象添加进短信
                SystemClock.sleep(500);                 //模拟耗时
                callback.getSmsProgress(++progress);      //回调短信进度
            }
        }
        //关闭数据库
        cursor.close();

        //创建Gson的对象
        Gson gson = new Gson();
        String json = gson.toJson(list);//将集合装换成json类的String
        FileOutputStream fos = new FileOutputStream(backupPath);
        fos.write(json.getBytes());
        fos.flush();
        fos.close();
    }

    /**
     * 短信还原
     *
     * @param ctx
     * @param backupPath
     * @param callback
     * @throws IOException
     */
    public static void SmsRestore(Context ctx, File backupPath, OnSmsCallback
            callback) throws IOException {
        //将json流还原成list集合
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<SmsInfo>>() {
        }.getType();
        ArrayList<SmsInfo> list = gson.fromJson(new FileReader(backupPath), type);

        //获取短信总数, select count(*) from xx;
        callback.getSmsCount(list.size());           //返回当前短信的数量,回调

        //2. 遍历集合, 插入数据库
        ContentResolver resolver = ctx.getContentResolver();

        int progress = 0;               //进度初始化
        for (SmsInfo smsInfo : list) {
            //查询数据库判,断该短信是否存在,如果短信已在数据库,就不还原了
//          query(Uri uri, String[] projection,String selection, String[] selectionArgs,String
// sortOrder)
            Cursor cursor = resolver.query(Uri.parse("content://sms"), null, "address=? and " +
                    "type=? and read=? and body=? and date=?", new String[]{smsInfo.address,
                    smsInfo.type, smsInfo.read, smsInfo.body, smsInfo.date}, null);

            boolean exist = false;
            if (cursor != null) {
                if (cursor.moveToNext()) {
                    //短信已经存在, 无需插入-->循环继续
                    //break, return, continue
                    exist = true;
                }
                cursor.close();
                //如果短信已经存在
                if (exist) {
                    SystemClock.sleep(500);                 //模拟耗时
                    callback.getSmsProgress(++progress);      //回调短信进度
                    continue;
                }
                ContentValues values = new ContentValues();
                values.put("address", smsInfo.address);
                values.put("type", smsInfo.type);
                values.put("read", smsInfo.read);
                values.put("body", smsInfo.body);
                values.put("date", smsInfo.date);

                resolver.insert(Uri.parse("content://sms"), values);
            }
            SystemClock.sleep(500);                 //模拟耗时
            callback.getSmsProgress(++progress);      //回调短信进度
        }
    }

    /**
     * 短信的回调接口
     */
    public interface OnSmsCallback {
        //获取短信总数的回调方法
        void getSmsCount(int smscount);

        //获取短信进度的回调
        void getSmsProgress(int smsprogress);
    }

    //短信信息组件
    private static class SmsInfo {
        String address;
        String date;
        String read;
        String type;
        String body;
    }
}
