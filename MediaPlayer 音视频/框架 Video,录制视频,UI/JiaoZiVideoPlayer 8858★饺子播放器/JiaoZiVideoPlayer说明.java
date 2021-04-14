https://github.com/lipangit/JiaoZiVideoPlayer

高度自定义的安卓视频播放器, 好像不能直播: http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8

Q群: 490442439 2群: 761899104 验证信息:jzvd

主要特点
可以完全自定义UI和任何功能
一行代码切换播放引擎，支持的视频格式和协议取决于播放引擎，android.media.MediaPlayer ijkplayer
完美检测列表滑动
可实现全屏播放，小窗播放
能在ListView、ViewPager和ListView、ViewPager和Fragment等多重嵌套模式下全屏工作
可以在加载、暂停、播放等各种状态中正常进入全屏和退出全屏
多种视频适配屏幕的方式，可铺满全屏，可以全屏剪裁
重力感应自动进入全屏
全屏后手势修改进度和音量
Home键退出界面暂停播放，返回界面继续播放
WebView嵌套本地视频控件
demo中添加视频缓存的例子
使用步骤
通读ReadMe
下载安装demo apk jiaozivideoplayer-6.3.1.apk，各个页面都进入一次，各个按钮点一次
下载调试develop分支，有针对性的通过效果找到实现的源码
看自定义相关的WIKI，实现自己的需求
入门文档 1
入门文档 2

使用
即便是自定义UI，或者对Library有过修改，也是这五步骤来使用播放器。

1.添加类库
implementation 'cn.jzvd:jiaozivideoplayer:7.6.0'

2.添加布局
<cn.jzvd.JzvdStd
    android:id="@+id/jz_video"
    android:layout_width="match_parent"
    android:layout_height="200dp" />


3.设置视频地址、缩略图地址、标题
JzvdStd jzvdStd = (JzvdStd) findViewById(R.id.videoplayer);
//设置缩略图地址
Glide.with(this).load(url).into(jzvdStd.posterImageView);
//设置视频地址、标题
jzvdStd.setUp(url, title);

jzvdStd.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4", "饺子闭眼睛");
jzvdStd.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");


4.在Activity中
@Override
protected void onPause() {
    super.onPause();
    Jzvd.releaseAllVideos();
}
@Override
public void onBackPressed() {
    if (Jzvd.backPress()) {
        return;
    }
    super.onBackPressed();
}


5.在AndroidManifest.xml中
<activity
    android:name=".MainActivity"
    android:configChanges="orientation|screenSize|keyboardHidden"
    android:screenOrientation="portrait" /> <!-- or android:screenOrientation="landscape"-->


6.在proguard-rules.pro中按需添加
##------Begin: proguard configuration for JiaoZiVideoPlayer------
-keep public class cn.jzvd.JZMediaSystem {*; }
-keep public class cn.jzvd.demo.CustomMedia.CustomMedia {*; }
-keep public class cn.jzvd.demo.CustomMedia.JZMediaIjk {*; }
-keep public class cn.jzvd.demo.CustomMedia.JZMediaSystemAssertFolder {*; }

-keep class tv.danmaku.ijk.media.player.** {*; }
-dontwarn tv.danmaku.ijk.media.player.*
-keep interface tv.danmaku.ijk.media.player.** { *; }
##-------End: proguard configuration for JiaoZiVideoPlayer-------


Wiki
常规使用
QuickStart
列表播放
小窗播放
直接全屏播放
API
自定义
自定义代码
自定义代码示例
自定义UI
自定义UI示例
