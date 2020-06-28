package cn.itcast.zhxa12.utils.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zhengping on 2017/2/28,8:58.
 * 网络缓存
 */

public class NetCacheUtils {

    private LocalCacheUtils localCacheUtils;
    private MemoryCacheUtils memoryCacheUtils;

    public NetCacheUtils(LocalCacheUtils localCacheUtils, MemoryCacheUtils memoryCacheUtils) {
        this.localCacheUtils = localCacheUtils;
        this.memoryCacheUtils = memoryCacheUtils;
    }

    public void getImageFromServer(String url, ImageView iv) {
		iv.setTag(url);

        //根据url下载图片
        //把图片变成bitmap对象
        //设置imageView
        //Thread+handler
        MyAsyncTask task = new MyAsyncTask();
        task.execute(url,iv);//启动异步任务

    }

    //三个参数的含义：
    //第一个泛型的含义：和doInBackground方法中的参数类型一致，还要和execute方法中参数类型一致。我们一般用此参数传递数据
    //第二个泛型的含义：和onProgressUpdate方法中的参数类型一致。我们一般会用此参数更新界面的进度
    //第三个泛型的含义：和doInBackground返回值类型保持一致，并且和onPostExecute方法参数类型保持一致
    class MyAsyncTask extends AsyncTask<Object, Integer, Bitmap> {


        private ImageView iv;
        private String url;

        //在执行异步任务之前，会进行的方法回调
        //我们一般会在此方法中做一些耗时操作前的准备工作，比如弹出一个ProgressBar
        //此方法运行在主线程中
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //处理耗时操作  此方法运行在子线程
        @Override
        protected Bitmap doInBackground(Object... params) {
            url = (String) params[0];
            iv = (ImageView) params[1];
            Bitmap bitmap = downloadImage(url);
            return bitmap;
        }

        //在异步任务的进度发生改变的时候，会进行的方法回调
        //我们一般会在这个方法中更新界面的进度
        //此方法运行在主线程中
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        //在执行异步任务结束之后，会进行的方法回调
        //我们一般会在此方法中，做一些收尾的事情，比如说把之前弹出的ProgressBar隐藏起来
        //此方法运行在主线程中
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            String savedTag = (String) iv.getTag();
            //存在imageView中标签和异步任务中的url进行比较,防止数据错乱,如果不判断呢??
            if(bitmap != null && savedTag.equals(url)) {
                iv.setImageBitmap(bitmap);
                //把图片存在sdcard中
                localCacheUtils.saveBitmap2Local(bitmap,url);
                memoryCacheUtils.saveBitmap2Memory(bitmap, url);
            }
        }
    }

    private Bitmap downloadImage(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setReadTimeout(5 * 1000);//服务器5s没响应
            conn.setConnectTimeout(5 * 1000);//5s没连接上服务器
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if(responseCode == 200) {
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
