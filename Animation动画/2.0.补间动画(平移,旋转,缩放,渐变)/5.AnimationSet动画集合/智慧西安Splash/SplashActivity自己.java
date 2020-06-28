package com.heima.wisdomxian.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.heima.wisdomxian.R;
import com.heima.wisdomxian.global.GlobalConstants;
import com.heima.wisdomxian.utils.SharedPreferencesUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * 闪屏界面
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //初始化动画,动画设置监听,旋转,缩放,透明度 补间动画 集合		★★★★★含动画加速器★★★★★
        initAnimation();
        //初始化图片加载器
        initImageLoader(this);
    }

    //初始化图片加载器
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPoolSize(5);					//线程池数量
        config.threadPriority(Thread.NORM_PRIORITY - 2);		//设置线程的优先级
        config.denyCacheImageMultipleSizesInMemory();			//是否允许在内存中保存同一张图片不同尺寸的bitmap对象
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());	//缓存文件的命名策略, 以md5(url)命名
        config.memoryCacheSize(2 * 1024 * 1024);			//设置内存缓存大小2M
        config.diskCacheSize(50 * 1024 * 1024);				// 50 MiB  磁盘缓存大小
        config.diskCacheFileCount(2000);				//缓存文件最大数量
        config.tasksProcessingOrder(QueueProcessingType.FIFO);		//任务处理订单(队列处理类型.fifo先进先出)
        config.writeDebugLogs();					// Remove for release app

        // Initialize ImageLoader with configuration.初始化ImageLoader与配置。
        ImageLoader.getInstance().init(config.build());
    }

    /**
     * 补间动画：只改变view的绘制流程，不改变View的真实属性
     * 帧动画：gif图片
     * 属性动画：android3.0之后才有的，改变View真实的属性
     * Activity跳转的动画效果,在startActivity后调用overridePendingTransition();
     */
    private void initAnimation() {
        ImageView iv_splash = (ImageView) findViewById(R.id.iv_splash);

        //1、旋转动画
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setFillAfter(true);

        //2.缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation
                .RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setFillAfter(true);

        //3.渐变动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(2000);
        rotateAnimation.setFillAfter(true);

        //4.动画集合,参数代表的含义是，动画集合中的动画是否使用自身的动画插入器
        AnimationSet animationSet = new AnimationSet(true);

        //动画插入器指的就是动画变化速率的控制    加速
        animationSet.setInterpolator(new AccelerateInterpolator());

        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        iv_splash.startAnimation(animationSet);

        //对动画进行监听
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //跳转界面
                //是否是第一次运行软件的判断  往sp中取一个值用来判断
                boolean isRead = SharedPreferencesUtils.getBoolean(SplashActivity.this,
                        GlobalConstants.GRIDE_FINISHED, false);
                Intent intent = new Intent();
                if (isRead) {
                    //跳转主界面
                    intent.setClass(SplashActivity.this, MainActivity.class);
                } else {
                    //跳转新手引导界面
                    intent.setClass(SplashActivity.this, GuideActivity.class);
                }
                startActivity(intent);
                //将当前界面finish掉
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

}
