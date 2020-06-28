package com.ithaima.citynews.xmlparse;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.ithaima.citynews.R.string;
import com.ithaima.citynews.bean.NewsBean;

/**
 * ����Xml�ļ�
 */
public class ParseXml {

	/**
	 * �����н���xml�ļ�
	 * 
	 * @param is
	 *            ������
	 * @return List<NewsBean> ��null��ʾ����ʧ��
	 */
	public static List<NewsBean> parseXmlFomStream(InputStream is) {
		// 1. ��ȡXml������
		XmlPullParser parser = Xml.newPullParser();
		// 2. ��������
		try {
			parser.setInput(is, "utf-8");
			// 3. ����xml�ļ�
			int type = parser.getEventType();
			List<NewsBean> list = new ArrayList<NewsBean>();
			NewsBean bean= null;
			while(type != XmlPullParser.END_DOCUMENT){
				//��ȡxml�ļ��Ŀ�ʼ���߽��ܱ�ǩ
				String tag = parser.getName();
				switch (type) {
					case XmlPullParser.START_TAG://��ʼ��ǩ
						if ("item".equals(tag)) {
							bean = new  NewsBean();
						}else if("title".equals(tag)){//����
							String title = parser.nextText();
							System.out.println(title);
							bean.setTitle(title);
						}else if("des".equals(tag)){//���ŵ�����
							String des = parser.nextText();
							System.out.println(des);
							bean.setDes(des);
						}else if("image".equals(tag)){//ͼƬ��url
							String imageUrl = parser.nextText();
							System.out.println(imageUrl);
							bean.setIamgeUrl(imageUrl);
						}else if("comment".equals(tag)){//����
							String comment = parser.nextText();
							System.out.println(comment);
							bean.setComment(comment);
						}
						break;
					case XmlPullParser.END_TAG://������ǩ
						if ("item".equals(tag)) {
							list.add(bean);
						}
						break;
					default:
						break;
				}
				
				//���α������ƶ�һ��
				type = parser.next();
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
