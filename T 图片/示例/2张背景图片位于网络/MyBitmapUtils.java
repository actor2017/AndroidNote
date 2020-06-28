package cn.itcast.myredboyclient.utils;

import java.io.File;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.widget.ImageView;

public class MyBitmapUtils {

	public void displaySelector(final ImageView iv, final String url1,
			final String url2) {

		final HttpUtils utils = new HttpUtils();
		// 将图片下载到sd卡中
		//category/icon_category_mon_red.png
		
		
		//   /sdcard/icon_category_mon_red.png
		final String fileName1 = "/sdcard/"
				+ url1.substring(url1.lastIndexOf("/") + 1);
		final String fileName2 = "/sdcard/"
				+ url2.substring(url2.lastIndexOf("/") + 1);
		// 先下载第一张图片
		utils.download(url1, fileName1, new RequestCallBack<File>() {

			@Override
			public void onSuccess(ResponseInfo<File> arg0) {
				// 第一张图片下载完成之后，再开始下载第二张图片
				utils.download(url2, fileName2, new RequestCallBack<File>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {

					}

					@Override
					public void onSuccess(ResponseInfo<File> arg0) {
						// 此时两种图片都下载完成了
						Bitmap bitmap1 = BitmapFactory.decodeFile(fileName1);
						Bitmap bitmap2 = BitmapFactory.decodeFile(fileName2);
						setSelector(iv, bitmap1, bitmap2);
					}
				});
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}
		});

	}

	// StateListDrawable就是xml文件中的selector
	private void setSelector(ImageView iv, Bitmap b1, Bitmap b2) {
		// new出一个状态选择器的对象
		StateListDrawable stateListDrawable = new StateListDrawable();
		// 添加规则，在什么样的状态下显示什么图片
		stateListDrawable.addState(new int[] { android.R.attr.state_pressed},
				new BitmapDrawable(b2));
		// 什么规则都不添加代表默认显示的图片
		stateListDrawable.addState(new int[] {}, new BitmapDrawable(b1));
		iv.setImageDrawable(stateListDrawable);
	}
}
