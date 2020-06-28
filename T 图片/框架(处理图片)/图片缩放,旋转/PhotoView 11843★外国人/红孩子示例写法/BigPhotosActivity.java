package cn.itheima.redboy.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import cn.itheima.redboy.R;
import cn.itheima.redboy.global.GlobalConstants;
import com.github.chrisbanes.photoview.PhotoView;

/**
 * 显示大图片,可缩放
 * 需intent传入2个参数,示例写法:
 * Intent intent = new Intent(MyActivity.this,BigPhotosActivity.class);
 * 1.商品号:"position"
 * intent.putExtra("position",vp_viewpager.getCurrentItem());
 *
 * 2.大图的集合:集合名字:bigImgsList
 * intent.putExtra("bigImgsList",bigImgsList);
 * startActivity(intent);
 *
 * 3.示例
 */
public class BigPhotosActivity extends AppCompatActivity {

    private ArrayList<String> bigImgsList;//获取传递过来的大图片的地址集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_photos);

        //获取传递过来的图片编号
        int position = getIntent().getIntExtra("position", 0);
        //获取传递过来的大图片的地址集合
        bigImgsList = getIntent().getStringArrayListExtra("bigImgsList");
        //viewpager
        final ViewPager vp_viewpager = (ViewPager) findViewById(R.id.vp_viewpager);
        //小圆点指示器
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        //适配器
        vp_viewpager.setAdapter(new mAdapter());
        //绑定小圆点
        indicator.setViewPager(vp_viewpager);   //在ViewPager设置Adapter之后才能绑定,不然报错

        vp_viewpager.setCurrentItem(position);      //设置现在展示的pager
    }

    //适配器
    private class mAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return bigImgsList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //把ImageView换成PhotoView即可,PhotoView 继承了 ImageView
            PhotoView photoView = new PhotoView(BigPhotosActivity.this);
            Glide
                    .with(BigPhotosActivity.this) // 指定Context
                    .load(GlobalConstants.PREF_PIC_URL_PREFIX+bigImgsList.get(position))// 指定图片的URL
                    .placeholder(R.mipmap.ic_launcher)// 指定图片未成功加载前显示的图片
                    .error(R.mipmap.ic_launcher)// 指定图片加载失败显示的图片
                    //.override(300, 300)//指定图片的尺寸(不设置可注销)
                    .skipMemoryCache(false)// 跳过内存缓存
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//跳过磁盘缓存(ALL:不跳过,NONE:跳过)
                    //.transform(new GlideRoundTransform(this,50))//圆形图片,圆角图片...
                    .into(photoView);//指定显示图片的ImageView

            container.addView(photoView);

            return photoView;
        }
    }
}
