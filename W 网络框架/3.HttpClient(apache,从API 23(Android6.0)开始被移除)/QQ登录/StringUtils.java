package com.itheima.qq;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * String�ַ����Ĺ�����
 */
public class StringUtils {

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
}
