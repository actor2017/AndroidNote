package com.gaosu.mobilesafe1.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 工具类: 把InputStream中的数据转成字符串
 * 
 * @author gaosu
 * 
 */
public class InputStream2String {
	/**
	 * 方法的功能: 把InputStream中的数据转成字符串
	 * 
	 * @param is
	 *            InputStream输入流
	 * @return 返回字符串, 如果返回null, 表示转换失败;
	 */
	public static String beginSwitch(InputStream is) {
		// 创建内存输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = -1;
		// 创建一个byte数组
		byte[] buffer = new byte[1024 * 8];
		try {
			// 频繁的读写操作
			// len不等于-1的时候就一直读取输入流中的数据
			while ((len = is.read(buffer)) != -1) {
				// 把流中的数据写到byte数组中;
				baos.write(buffer, 0, len);
			}
			// toByteArray()方法是把数据转成byte数组,通过string构造方法将byte数组转成字符串;
			String text = new String(baos.toByteArray());
			// 返回这个字符串
			return text;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
