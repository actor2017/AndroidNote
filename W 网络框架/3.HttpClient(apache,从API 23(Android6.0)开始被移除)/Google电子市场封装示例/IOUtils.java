package cn.itcast.googleplay12.utils;

import java.io.Closeable;
import java.io.IOException;

import android.util.Log;

public class IOUtils {

	public static final String TAG = "IOUtils";

	/** 关闭流 */
	public static boolean close(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
			}
		}
		return true;
	}
}
