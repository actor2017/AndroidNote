https://github.com/mabeijianxi/small-video-record
利用FFmpeg视频录制微信小视频与其压缩处理
Android端音频视频采集，底层利用FFmpeg编码压缩处理（small-video-record2已从C到Java全面开源）！

使用：
small-video-record1(small-video-record1 使用步骤)
https://github.com/mabeijianxi/small-video-record/blob/master/document/1.x_using_help.md

small-video-record2(small-video-record2 使用步骤)
https://github.com/mabeijianxi/small-video-record/blob/master/document/2.x_using_help.md

特点：
边采集边编码。
利用FFmpeg自定义录制各种时长、分辨率、码率、帧率、转码速度的视频。
small-video-record2已解耦FFmpeg，可根据自己需求定制FFmpeg。
暴露FFmpeg命令操作接口，可自定义更多功能。
small-video-record2 支持全平台，如果你手机 cpu 是64位的将达到秒编！
可选择本地视频进行个性化压缩，如果你手机 cpu 是64位的速度将相对很快。
录制简单，几行代码完成集成，几个参数搞定录制。

关于small-video-record2：
small-video-record2 源码编译:
你需要拥有ndk环境、AndroidStudio版本大于2.2、AndroidStudio装有Cmake插件。


// record1, record2 的使用: 经使用, 一直报错: java.lang.UnsatisfiedLinkError, 配置了也没用!!!



///////////////////////////////////////////////////////
// record1 的使用:
///////////////////////////////////////////////////////
1.添加依赖
//https://github.com/mabeijianxi/small-video-record 视频录制&压缩
implementation 'com.mabeijianxi:small-video-record:1.2.2'

2.如果要录制视频, 在AndroidManifest.xml中添加
<activity android:name="mabeijianxi.camera.MediaRecorderActivity"/>

3.Application中初始化视频录制
private MediaRecorderConfig mediaRecorderConfig;

    //视频录制&压缩
    private void initSmallVideoRecord() {
        // Set the cache path for video
        File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                VCamera.setVideoCachePath(dcim + "/mabeijianxi/");
            } else {
                VCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/", "/sdcard-ext/") + "/mabeijianxi/");
            }
        } else {
            VCamera.setVideoCachePath(dcim + "/mabeijianxi/");
        }
        VCamera.setDebugMode(isAppDebug());
        VCamera.initialize(this);


        //视频录制配置
        mediaRecorderConfig = new MediaRecorderConfig.Buidler()
                .doH264Compress(new AutoVBRMode()
//                        .setVelocity(BaseMediaBitrateConfig.Velocity.ULTRAFAST)
                )
                .setMediaBitrateConfig(new AutoVBRMode()
//                        .setVelocity(BaseMediaBitrateConfig.Velocity.ULTRAFAST)
                )
                .smallVideoWidth(480)
                .smallVideoHeight(360)
                .recordTimeMax(6 * 1000)
                .maxFrameRate(20)
                .captureThumbnailsTime(1)
                .recordTimeMin((int) (1.5 * 1000))
                .build();


        //本地视频压缩配置
        localMediaConfigBuidler = new LocalMediaConfig.Buidler()
//                .setVideoPath(path)//设置要压缩的视频path
                .captureThumbnailsTime(1)
                .doH264Compress(new AutoVBRMode())
                .setFramerate(15)
                .setScale(1.0f);
    }

4. 使用
//开始录制视频
MediaRecorderActivity.goSmallVideoRecorder(this, activity.getClass().getName(), mediaRecorderConfig);

//压缩视频, 见底部 compressVideo() 方法.


5.报错:
如果报错 "java.lang.UnsatisfiedLinkError" , 就在 gradle.properties 中配置: android.useDeprecatedNdk=true”,
    and then configure “ndk {abiFilters "armeabi", "armeabi-v7a"}” at “build. gradle” in the main module





///////////////////////////////////////////////////////
// record2 的使用:
///////////////////////////////////////////////////////
1.添加依赖
//https://github.com/mabeijianxi/small-video-record 视频录制&压缩
implementation 'com.mabeijianxi:small-video-record2:2.0.3@aar'

2.如果要录制视频, 在AndroidManifest.xml中添加
<activity android:name="com.mabeijianxi.smallvideorecord2.MediaRecorderActivity"/>

3.Application中初始化视频录制
public MediaRecorderConfig mediaRecorderConfig;
public LocalMediaConfig.Buidler localMediaConfigBuidler;

//视频录制&压缩
    private void initSmallVideoRecord() {
        File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                JianXiCamera.setVideoCachePath(dcim + "/muvideo/");
            } else {
                JianXiCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/", "/sdcard-ext/") + "/muvideo/");
            }
        } else {
            JianXiCamera.setVideoCachePath(dcim + "/muvideo/");
        }
        // 初始化拍摄，遇到问题可选择开启此标记，以方便生成日志
        JianXiCamera.initialize(isAppDebug(), null);


        //初始化视频录制配置, 只初始化一次, 在Application or Activity中
        mediaRecorderConfig = new MediaRecorderConfig.Buidler()
                .fullScreen(false)
                .smallVideoWidth(360)
                .smallVideoHeight(480)
                .recordTimeMax(6000)
                .recordTimeMin(1500)
                .maxFrameRate(20)
                .videoBitrate(600000)
                .captureThumbnailsTime(1)
                .build();


        //本地视频压缩配置
        localMediaConfigBuidler = new LocalMediaConfig.Buidler()
//                .setVideoPath(FileUtils.getCacheDir().getPath())//设置要压缩的视频path
                .captureThumbnailsTime(1)
                .doH264Compress(new AutoVBRMode())
                .setFramerate(15)
                .setScale(1.0f);
    }

4. 使用
//开始录制视频
MediaRecorderActivity.goSmallVideoRecorder(activity, activity.getClass().getName(), mediaRecorderConfig);

/**
 * 压缩视频
 * @param videoPath 视频路径
 * @param listener 回调监听
 */
public static void compressVideo(String videoPath, OnCompressedListener listener) {
	if (listener == null) {
		return;
	}
	ThreadUtils.executeByCpu(new ThreadUtils.SimpleTask<OnlyCompressOverBean>() {
		@Override
		public OnlyCompressOverBean doInBackground() throws Throwable {
			//开始压缩
			return new LocalMediaCompress(MyApplication.instance.localMediaConfigBuidler
					.setVideoPath(videoPath)
					.build())
					.startCompress();
		}
		@Override
		public void onSuccess(OnlyCompressOverBean result) {
			listener.onCompressed(result);
		}
	});
}
public interface OnCompressedListener {
	void onCompressed(OnlyCompressOverBean bean);
}

混淆:
##-----Begin: proguard configuration for small-video-record2-----
-keep class com.mabeijianxi.smallvideorecord2.** {*;}
##-----End: proguard configuration for small-video-record2-------




