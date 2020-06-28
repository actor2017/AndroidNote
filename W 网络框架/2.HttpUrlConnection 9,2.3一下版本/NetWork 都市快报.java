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
 * 网络通信工具类
 */
public class NetWork {
	/**
	 * 从服务器请求新闻数据
	 * 
	 * @return List<NewsBean> 把所有的新闻放到集合中，如果null表示失败了
	 */
	//在MainActivity中有private List<NewsBean> mNewsList = NetWork.requestNetWork(MainActivity.this);
	public static List<NewsBean> requestNetWork(Context context) {
		
		//从配置文件中读取网络，方便维护
		String path = context.getResources().getString(R.string.serverip);
		try {
			// 1. 写一个URL
			URL url = new URL(path);
			// 2. 用这个URL打开连接
			HttpURLConnection conn 	= (HttpURLConnection) url.openConnection();
			// 3. 设置请求参数
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(3000);
			
			// 4. 获取服务器返回的响应状态码
			// * 2xx 成功 3xxx重定向 4xxx客户端错误 5xxx服务器错误
			int code = conn.getResponseCode();
			if (code == 200) {
				// 5. 获取服务器返回的二进制输入流
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
