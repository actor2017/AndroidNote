https://github.com/mabeijianxi/small-video-record
����FFmpeg��Ƶ¼��΢��С��Ƶ����ѹ������
Android����Ƶ��Ƶ�ɼ����ײ�����FFmpeg����ѹ������small-video-record2�Ѵ�C��Javaȫ�濪Դ����

ʹ�ã�
small-video-record1(small-video-record1 ʹ�ò���)
https://github.com/mabeijianxi/small-video-record/blob/master/document/1.x_using_help.md

small-video-record2(small-video-record2 ʹ�ò���)
https://github.com/mabeijianxi/small-video-record/blob/master/document/2.x_using_help.md

�ص㣺
�߲ɼ��߱��롣
����FFmpeg�Զ���¼�Ƹ���ʱ�����ֱ��ʡ����ʡ�֡�ʡ�ת���ٶȵ���Ƶ��
small-video-record2�ѽ���FFmpeg���ɸ����Լ�������FFmpeg��
��¶FFmpeg��������ӿڣ����Զ�����๦�ܡ�
small-video-record2 ֧��ȫƽ̨��������ֻ� cpu ��64λ�Ľ��ﵽ��࣡
��ѡ�񱾵���Ƶ���и��Ի�ѹ����������ֻ� cpu ��64λ���ٶȽ���Ժܿ졣
¼�Ƽ򵥣����д�����ɼ��ɣ����������㶨¼�ơ�

����small-video-record2��
small-video-record2 Դ�����:
����Ҫӵ��ndk������AndroidStudio�汾����2.2��AndroidStudioװ��Cmake�����


// record1, record2 ��ʹ��: ��ʹ��, һֱ����: java.lang.UnsatisfiedLinkError, ������Ҳû��!!!



///////////////////////////////////////////////////////
// record1 ��ʹ��:
///////////////////////////////////////////////////////
1.�������
//https://github.com/mabeijianxi/small-video-record ��Ƶ¼��&ѹ��
implementation 'com.mabeijianxi:small-video-record:1.2.2'

2.���Ҫ¼����Ƶ, ��AndroidManifest.xml�����
<activity android:name="mabeijianxi.camera.MediaRecorderActivity"/>

3.Application�г�ʼ����Ƶ¼��
private MediaRecorderConfig mediaRecorderConfig;

    //��Ƶ¼��&ѹ��
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


        //��Ƶ¼������
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


        //������Ƶѹ������
        localMediaConfigBuidler = new LocalMediaConfig.Buidler()
//                .setVideoPath(path)//����Ҫѹ������Ƶpath
                .captureThumbnailsTime(1)
                .doH264Compress(new AutoVBRMode())
                .setFramerate(15)
                .setScale(1.0f);
    }

4. ʹ��
//��ʼ¼����Ƶ
MediaRecorderActivity.goSmallVideoRecorder(this, activity.getClass().getName(), mediaRecorderConfig);

//ѹ����Ƶ, ���ײ� compressVideo() ����.


5.����:
������� "java.lang.UnsatisfiedLinkError" , ���� gradle.properties ������: android.useDeprecatedNdk=true��,
    and then configure ��ndk {abiFilters "armeabi", "armeabi-v7a"}�� at ��build. gradle�� in the main module





///////////////////////////////////////////////////////
// record2 ��ʹ��:
///////////////////////////////////////////////////////
1.�������
//https://github.com/mabeijianxi/small-video-record ��Ƶ¼��&ѹ��
implementation 'com.mabeijianxi:small-video-record2:2.0.3@aar'

2.���Ҫ¼����Ƶ, ��AndroidManifest.xml�����
<activity android:name="com.mabeijianxi.smallvideorecord2.MediaRecorderActivity"/>

3.Application�г�ʼ����Ƶ¼��
public MediaRecorderConfig mediaRecorderConfig;
public LocalMediaConfig.Buidler localMediaConfigBuidler;

//��Ƶ¼��&ѹ��
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
        // ��ʼ�����㣬���������ѡ�����˱�ǣ��Է���������־
        JianXiCamera.initialize(isAppDebug(), null);


        //��ʼ����Ƶ¼������, ֻ��ʼ��һ��, ��Application or Activity��
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


        //������Ƶѹ������
        localMediaConfigBuidler = new LocalMediaConfig.Buidler()
//                .setVideoPath(FileUtils.getCacheDir().getPath())//����Ҫѹ������Ƶpath
                .captureThumbnailsTime(1)
                .doH264Compress(new AutoVBRMode())
                .setFramerate(15)
                .setScale(1.0f);
    }

4. ʹ��
//��ʼ¼����Ƶ
MediaRecorderActivity.goSmallVideoRecorder(activity, activity.getClass().getName(), mediaRecorderConfig);

/**
 * ѹ����Ƶ
 * @param videoPath ��Ƶ·��
 * @param listener �ص�����
 */
public static void compressVideo(String videoPath, OnCompressedListener listener) {
	if (listener == null) {
		return;
	}
	ThreadUtils.executeByCpu(new ThreadUtils.SimpleTask<OnlyCompressOverBean>() {
		@Override
		public OnlyCompressOverBean doInBackground() throws Throwable {
			//��ʼѹ��
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

����:
##-----Begin: proguard configuration for small-video-record2-----
-keep class com.mabeijianxi.smallvideorecord2.** {*;}
##-----End: proguard configuration for small-video-record2-------




