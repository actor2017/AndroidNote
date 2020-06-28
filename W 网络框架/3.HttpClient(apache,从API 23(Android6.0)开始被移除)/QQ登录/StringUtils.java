package com.itheima.qq;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * String字符串的工具类
 */
public class StringUtils {

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
}
