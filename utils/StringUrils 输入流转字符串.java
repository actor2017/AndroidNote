package com.itheima.qq;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * String字符串的工具类
 */
public class StringUrils {

	/**
	 * 从流中生成一个字符串
	 * 
	 * @param is
	 *            输入流
	 * @return 转换的字符串，如果null表示转换失败
	 */
	public static String parseStream2String(InputStream is) {
		//内存输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = -1;
		byte[] buffer = new byte[1024*8];
		try {
			while((len=is.read(buffer))!=-1){
				baos.write(buffer, 0, len);
			}
			String text = new String(baos.toByteArray());
			return text;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    /**
     * 视频/音乐时长(单位ms) --> 01:25:08
     * @param time
     * @return 00:00:00 或者 00:00
     */
    public static String formatDuration(int time) {
        int h = time / HOUR;
        int m = time % HOUR / MIN;
        int s = time % MIN / SEC;
        if (h > 0) {//00:00:00
            return String.format("%02d:%02d:%02d", h, m, s);
        } else {//00:00
            return String.format("%02d:%02d", m, s);
        }
    }
}
