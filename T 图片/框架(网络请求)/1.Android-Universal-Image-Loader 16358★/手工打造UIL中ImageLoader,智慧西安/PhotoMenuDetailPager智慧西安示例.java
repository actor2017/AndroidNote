package cn.itcast.zhxa12.pager.impl.menu;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import cn.itcast.zhxa12.R;
import cn.itcast.zhxa12.bean.PhotoBean;
import cn.itcast.zhxa12.global.GlobalConstants;
import cn.itcast.zhxa12.pager.BaseMenuDetailPager;
import cn.itcast.zhxa12.utils.CacheUtils;
import cn.itcast.zhxa12.utils.image.MyImageLoader;
import okhttp3.Call;

import static cn.itcast.zhxa12.R.id.lvListNews;

/**
 * Created by zhengping on 2017/2/24,10:31.
 */

public class PhotoMenuDetailPager extends BaseMenuDetailPager implements View.OnClickListener {

    private ListView lvList;
    private List<PhotoBean.DataBean.NewsBean> news;
    private GridView gvList;
    private ImageView ivDisplay;

    public PhotoMenuDetailPager(Activity activity, ImageView ivDisplay) {
        super(activity);
        this.ivDisplay = ivDisplay;
        ivDisplay.setOnClickListener(this);
    }

    @Override
    public View initView() {
      /*  TextView tv = new TextView(activity);
        return tv;*/
        View view = View.inflate(activity, R.layout.pager_photo_menu_detail, null);
        lvList = (ListView) view.findViewById(R.id.lvList);
        gvList = (GridView) view.findViewById(R.id.gvList);
        return view;
    }

    @Override
    public void initData() {
        /*TextView tv = (TextView) view;
        tv.setText("我是组图菜单详情页");*/
        getDataFromServer();
    }

    private void getDataFromServer() {
        //使用Okhttp
        String url = GlobalConstants.URL_PHOTOS;
        //创建子线程访问网络
        //Handler-->子线和主线程的通信
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        parseJson(response);
                    }
                });
    }

    private void parseJson(String response) {
        Gson gson = new Gson();
        PhotoBean photoBean = gson.fromJson(response, PhotoBean.class);
        news = photoBean.data.news;

        lvList.setAdapter(new MyAdapter());
        gvList.setAdapter(new MyAdapter());

    }

    private boolean isListView = true;
    @Override
    public void onClick(View v) {
        //将ListView和GridView切换
        if(isListView) {
            lvList.setVisibility(View.GONE);
            gvList.setVisibility(View.VISIBLE);
            isListView = false;
            ivDisplay.setImageResource(R.drawable.icon_pic_grid_type);
        } else {
            lvList.setVisibility(View.VISIBLE);
            gvList.setVisibility(View.GONE);
            isListView = true;
            ivDisplay.setImageResource(R.drawable.icon_pic_list_type);
        }
    }

    class MyAdapter extends BaseAdapter {


        private DisplayImageOptions options;

        public MyAdapter() {
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.ic_stub)//加载中图片的设置
                    .showImageForEmptyUri(R.drawable.ic_empty)//url为null的情况下
                    .showImageOnFail(R.drawable.ic_error)//加载图片失败的时候的设置
                    .cacheInMemory(true)//是否缓存在内存
                    .cacheOnDisk(true)//是否缓存在硬盘
                    .considerExifParams(true)//exif:一张图片的附加信息  位置信息、时间信息、角度
                    .displayer(new FadeInBitmapDisplayer(500))//设置展示器
                    .build();
        }

        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public Object getItem(int position) {
            return news.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * imageView  url
         *
         * 1、访问url这个地址，把这张图片下载下来，下载到本地sdcard  子线程
         * 2、将图片变成bitmap对象
         * 3、将bitmap对象设置给imageView 主线程
         *
         * 线程之间的通信  Thread+handler
         *
         * 对图片进行缓存
         *
         *  三级缓存：内存缓存--本地缓存--网络缓存
         *
         *
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null) {
                convertView = View.inflate(activity, R.layout.item_photo_menu, null);
                holder = new ViewHolder();
                holder.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
                holder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //holder.ivImage.setImageBitmap();

            PhotoBean.DataBean.NewsBean newsBean = news.get(position);
            holder.tvContent.setText(newsBean.title);

            String url = GlobalConstants.URL_PREFIX + newsBean.listimage;
            //holder.ivImage.setTag(url);//我已经在NetCacheUtils里设置了tag
            MyImageLoader.getInstance().displayImage(url,holder.ivImage);

            return convertView;
        }
    }

    static class ViewHolder {
        ImageView ivImage;
        TextView tvContent;
    }
}
