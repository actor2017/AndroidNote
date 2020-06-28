package com.ithaima.citynews.net;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import android.content.Context;
import com.ithaima.citynews.R;
import com.ithaima.citynews.bean.NewsBean;
import com.ithaima.citynews.xmlparse.ParseXml;
/**
 * ����ͨ�Ź�����
 */
public class NetWork {
	/**
	 * �ӷ�����������������
	 * 
	 * @return List<NewsBean> �����е����ŷŵ������У����null��ʾʧ����
	 */
	//��MainActivity����private List<NewsBean> mNewsList = NetWork.requestNetWork(MainActivity.this);
	public static List<NewsBean> requestNetWork(Context context) {
		
		//�������ļ��ж�ȡ���磬����ά��
		String path = context.getResources().getString(R.string.serverip);
		try {
			// 1. дһ��URL
			URL url = new URL(path);
			// 2. �����URL������
			HttpURLConnection conn 	= (HttpURLConnection) url.openConnection();
			// 3. �����������
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(3000);
			
			// 4. ��ȡ���������ص���Ӧ״̬��
			// * 2xx �ɹ� 3xxx�ض��� 4xxx�ͻ��˴��� 5xxx����������
			int code = conn.getResponseCode();
			if (code == 200) {
				// 5. ��ȡ���������صĶ�����������
				InputStream is = conn.getInputStream();
				List<NewsBean> list = ParseXml.parseXmlFomStream(is);
				return list;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
