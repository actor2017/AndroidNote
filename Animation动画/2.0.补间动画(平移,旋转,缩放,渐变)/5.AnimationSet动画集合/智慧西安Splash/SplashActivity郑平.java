package cn.itcast.zhxa12.activity;

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
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import cn.itcast.zhxa12.R;
import cn.itcast.zhxa12.global.GlobalConstants;
import cn.itcast.zhxa12.utils.PrefUtils;
import cn.sharesdk.framework.ShareSDK;

/**
 * 全屏效果的显示：
 * AppCompatActivity:作为上下版本兼容的ActionBar效果
 * ActionBar在3.0之后，如何在2.x的系统版本中也拥有actionBar的效果，这时候就应该找AppCompatActivity
 * <p>
 * AppCompatActivity的使用方式：
 * 1、需要在AppcompatActivity的子类，配置Theme.AppCompat的主题
 * <p>
 * 对于我们这个项目，我们不需要使用ActionBar效果，所以我们可以不使用AppCompatActivity
 */
public class SplashActivity extends Activity {

    private RelativeLayout rlRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        rlRoot = (RelativeLayout) findViewById(R.id.rlRoot);
        initAnimation();
        initImageLoader(this);
        ShareSDK.initSDK(this);

    }

    /**
     * 补间动画：只改变view的绘制流程，不改变View的真实属性
     * 帧动画：gif图片
     * 属性动画：android3.0之后才有的，改变View真实的属性
     * Activity跳转的动画效果,在startActivity后调用overridePendingTransition();
     */
    private void initAnimation() {
        //1、旋转动画
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, Animation
                .RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setFillAfter(true);//保持动画结束时候的状态
        //rotateAnimation.setInterpolator();
        //2、缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f,1.0f,0.0f,1.0f,Animation
                .RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setFillAfter(true);
        //3、渐变动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);


        //TranslateAnimation

        //动画集合,参数代表的含义是，动画集合中的动画是否使用自身的动画插入器
        AnimationSet animationSet = new AnimationSet(true);
        //动画插入器指的就是动画变化速率的控制
        animationSet.setInterpolator(new AccelerateInterpolator());
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        //animationSet.start();
        rlRoot.startAnimation(animationSet);

        //对动画进行监听
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //跳转界面
                //是否是第一次运行软件的判断  往sp中取一个值用来判断
                boolean isFirstIn = PrefUtils.getBoolean(SplashActivity.this, GlobalConstants.KEY_FIRST_IN, true);
                Intent intent = new Intent();
                if(isFirstIn) {
                    //跳转新手引导界面
                    intent.setClass(SplashActivity.this, GuideActivity.class);
                } else {
                    //跳转主界面
                    intent.setClass(SplashActivity.this, MainActivity.class);
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

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);//设置线程的优先级
        config.denyCacheImageMultipleSizesInMemory();//是否允许在内存中保存同一张图片不同尺寸的bitmap对象
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());//图片下载下来的文件命名
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB  sdcard缓存大小
        config.tasksProcessingOrder(QueueProcessingType.FIFO);//
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
}
