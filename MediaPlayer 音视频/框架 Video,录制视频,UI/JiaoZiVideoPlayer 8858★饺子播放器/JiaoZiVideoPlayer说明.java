https://github.com/lipangit/JiaoZiVideoPlayer

�߶��Զ���İ�׿��Ƶ������, ������ֱ��: http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8

QȺ: 490442439 2Ⱥ: 761899104 ��֤��Ϣ:jzvd

��Ҫ�ص�
������ȫ�Զ���UI���κι���
һ�д����л��������棬֧�ֵ���Ƶ��ʽ��Э��ȡ���ڲ������棬android.media.MediaPlayer ijkplayer
��������б���
��ʵ��ȫ�����ţ�С������
����ListView��ViewPager��ListView��ViewPager��Fragment�ȶ���Ƕ��ģʽ��ȫ������
�����ڼ��ء���ͣ�����ŵȸ���״̬����������ȫ�����˳�ȫ��
������Ƶ������Ļ�ķ�ʽ��������ȫ��������ȫ������
������Ӧ�Զ�����ȫ��
ȫ���������޸Ľ��Ⱥ�����
Home���˳�������ͣ���ţ����ؽ����������
WebViewǶ�ױ�����Ƶ�ؼ�
demo�������Ƶ���������
ʹ�ò���
ͨ��ReadMe
���ذ�װdemo apk jiaozivideoplayer-6.3.1.apk������ҳ�涼����һ�Σ�������ť��һ��
���ص���develop��֧��������Ե�ͨ��Ч���ҵ�ʵ�ֵ�Դ��
���Զ�����ص�WIKI��ʵ���Լ�������
�����ĵ� 1
�����ĵ� 2

ʹ��
�������Զ���UI�����߶�Library�й��޸ģ�Ҳ�����岽����ʹ�ò�������

1.������
implementation 'cn.jzvd:jiaozivideoplayer:7.6.0'

2.��Ӳ���
<cn.jzvd.JzvdStd
    android:id="@+id/jz_video"
    android:layout_width="match_parent"
    android:layout_height="200dp" />


3.������Ƶ��ַ������ͼ��ַ������
JzvdStd jzvdStd = (JzvdStd) findViewById(R.id.videoplayer);
//��������ͼ��ַ
Glide.with(this).load(url).into(jzvdStd.posterImageView);
//������Ƶ��ַ������
jzvdStd.setUp(url, title);

jzvdStd.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4", "���ӱ��۾�");
jzvdStd.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");


4.��Activity��
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


5.��AndroidManifest.xml��
<activity
    android:name=".MainActivity"
    android:configChanges="orientation|screenSize|keyboardHidden"
    android:screenOrientation="portrait" /> <!-- or android:screenOrientation="landscape"-->


6.��proguard-rules.pro�а������
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
����ʹ��
QuickStart
�б���
С������
ֱ��ȫ������
API
�Զ���
�Զ������
�Զ������ʾ��
�Զ���UI
�Զ���UIʾ��
