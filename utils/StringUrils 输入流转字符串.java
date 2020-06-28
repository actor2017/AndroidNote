package com.itheima.qq;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * String�ַ����Ĺ�����
 */
public class StringUrils {

	/**
	 * ����������һ���ַ���
	 * 
	 * @param is
	 *            ������
	 * @return ת�����ַ��������null��ʾת��ʧ��
	 */
	public static String parseStream2String(InputStream is) {
		//�ڴ������
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
     * ��Ƶ/����ʱ��(��λms) --> 01:25:08
     * @param time
     * @return 00:00:00 ���� 00:00
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
