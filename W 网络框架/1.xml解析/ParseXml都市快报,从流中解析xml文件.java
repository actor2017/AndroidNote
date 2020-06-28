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
 * 解析Xml文件
 */
public class ParseXml {

	/**
	 * 从流中解析xml文件
	 * 
	 * @param is
	 *            输入流
	 * @return List<NewsBean> ，null表示解析失败
	 */
	public static List<NewsBean> parseXmlFomStream(InputStream is) {
		// 1. 获取Xml解析器
		XmlPullParser parser = Xml.newPullParser();
		// 2. 设置数据
		try {
			parser.setInput(is, "utf-8");
			// 3. 解析xml文件
			int type = parser.getEventType();
			List<NewsBean> list = new ArrayList<NewsBean>();
			NewsBean bean= null;
			while(type != XmlPullParser.END_DOCUMENT){
				//获取xml文件的开始或者接受标签
				String tag = parser.getName();
				switch (type) {
					case XmlPullParser.START_TAG://开始标签
						if ("item".equals(tag)) {
							bean = new  NewsBean();
						}else if("title".equals(tag)){//标题
							String title = parser.nextText();
							System.out.println(title);
							bean.setTitle(title);
						}else if("des".equals(tag)){//新闻的描述
							String des = parser.nextText();
							System.out.println(des);
							bean.setDes(des);
						}else if("image".equals(tag)){//图片的url
							String imageUrl = parser.nextText();
							System.out.println(imageUrl);
							bean.setIamgeUrl(imageUrl);
						}else if("comment".equals(tag)){//评论
							String comment = parser.nextText();
							System.out.println(comment);
							bean.setComment(comment);
						}
						break;
					case XmlPullParser.END_TAG://结束标签
						if ("item".equals(tag)) {
							list.add(bean);
						}
						break;
					default:
						break;
				}
				
				//把游标向下移动一步
				type = parser.next();
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
