package com.ithaima.citynews;

import java.util.List;

import com.ithaima.citynews.bean.NewsBean;
import com.loopj.android.image.SmartImageView;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	private List<NewsBean>	mNewsList;
	private ListView	lvNews;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lvNews = (ListView) findViewById(R.id.lv);
		lvNews.setAdapter(new NewsAdapter());
	}

	private class NewsAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return mNewsList==null ? 0:mNewsList.size();
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(MainActivity.this, R.layout.adapter_news_item, null);
			}
			SmartImageView iv = (SmartImageView) convertView.findViewById(R.id.iv);
			iv.setImageUrl(bean.getIamgeUrl());
			return convertView;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}
	}
}
