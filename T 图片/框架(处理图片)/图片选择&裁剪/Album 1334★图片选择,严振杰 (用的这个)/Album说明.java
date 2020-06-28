https://github.com/yanzhenjie/Album
Album��һ��MD���Ŀ�Դ��ᣬ֧�ֹ��ʻ���֧�ֹ��ʻ���չ����Ҫ����ģ�飺ѡ��ͼƬ����Ƶ�����ա�¼��Ƶ�����ȣ�֧�ֱ��غ����磩��
��������Ⱥ��547839514

�ҵ���ҳ��http://www.yanzhenjie.com
�ҵ�΢����http://weibo.com/yanzhenjieit

ͼƬ���п�ܣ�https://github.com/yanzhenjie/Durban
Http�����ܣ�https://github.com/yanzhenjie/NoHttp

����
Album is a Material Design style album, it provides three functions: album, camera and gallery.
Select images, selecte videos, or select pictures and videos.
Take a picture, record a video, or use camera in album list.
Preview pictures and videos in the gallery or select pictures and videos in the gallery.
������һ�ֲ�����Ʒ��Ļ��ᣬ���ṩ�˻��ᡢ����ͻ����������ܡ�
ѡ��ͼƬ��ѡ����Ƶ������ѡ��ͼƬ����Ƶ��
������б������ա�¼����Ƶ��ʹ�������
Ԥ��ͼƬ����Ƶ�ڻ��Ȼ�ѡ��ͼƬ����Ƶ�ڻ��ȡ�


�÷�:
1.�������
implementation 'com.yanzhenjie:album:2.1.3'//ͼƬѡ��,Ԥ��ͼƬ(��������ͼƬ)

2.���Ȩ��(�����������??)
<!--Album��Ȩ��,����,ͼƬѡ��,ͼƬ��ͼԤ��(��������ͼƬ)-->
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>

3.Application������
/**
 * ���û���
 */
Album.initialize(AlbumConfig.newBuilder(this)
        .setAlbumLoader(new GlideAlbumLoader()) // ����Album��������
        .setLocale(Locale.getDefault()) //Locale.CHINA ����ǿ���������κ������¶���������ʾ��
        .build());

4.ѡ��ͼƬ
//ͼ�����Ƶ���ѡ��
AlbumUtils.selectVideo$Image//�Լ���װ������

//ͼƬѡ��
AlbumUtils.selectImage
����������Ҫ��ͼƬ���вü�����ô����Խ����ŵ���Durban��

//��Ƶѡ��
AlbumUtils.selectVideo

//����
AlbumUtils.takePhoto

//¼����Ƶ
AlbumUtils.recordVideo

//ͨ��AlbumFile pathԤ��ͼƬ
AlbumUtils.galleryAlbum

//ͨ��String pathԤ��ͼƬ
AlbumUtils.gallery
����������Ҫ��ͼƬ���вü�����ô����Խ����ŵ���Durban��

5.ѡ����Ƭ&��Ƶ ��������
Image
public int getMediaType(); // File type, the image is AlbumFile.TYPE_IMAGE.
public String getPath(); // File path, must not be empty.
public String getBucketName(); // The name of the folder where the file is located.
public String getMimeType(); // File MimeType, for example: image/jpeg.
public long getAddDate(); // File to add date, must have.
public float getLatitude(); // The latitude of the file, may be zero.
public float getLongitude(); // The longitude of the file, may be zero.
public long getSize(); // File size in bytes.
public String getThumbPath(); // This is a small thumbnail.

Video
public int getMediaType(); // File type, the video is AlbumFile.TYPE_VIDEO.
public String getPath(); // File path, must not be empty.
public String getBucketName(); // The name of the folder where the file is located.
public String getMimeType(); // File MimeType, for example: image/jpeg.
public long getAddDate(); // File to add date, must have.
public float getLatitude(); // The latitude of the file, may be zero.
public float getLongitude(); // The longitude of the file, may be zero.
public long getSize(); // File size in bytes.
public long getDuration(); // Video duration, must have.
public String getThumbPath(); // This is a small thumbnail.


AlbumFile ��ͼ�����Ƶѡ��Ľ����ͼ�����Ƶ�������ǲ�ͬ�ģ������г������ǵĲ�ͬ���ԡ�
1.Image
public int getMediaType(); // File type, the image is AlbumFile.TYPE_IMAGE.  ý������
public String getPath(); // File path, must not be empty.  �ļ�·��
public String getBucketName(); // The name of the folder where the file is located.  �ļ������ļ��е�����
public String getMimeType(); // File MimeType, for example: image/jpeg.  �ļ�����
public long getAddDate(); // File to add date, must have.  �ļ�Ҫ�������
public float getLatitude(); // The latitude of the file, may be zero.  �ļ���γ��,����Ϊ��
public float getLongitude(); // The longitude of the file, may be zero.  �ļ��ľ���,����Ϊ��
public long getSize(); // File size in bytes.  �ļ���С
public String getThumbPath(); // This is a small thumbnail.  ����һ��С����ͼ

2.Video
public int getMediaType(); // File type, the video is AlbumFile.TYPE_VIDEO.  ý������
public String getPath(); // File path, must not be empty.  �ļ�·��
public String getBucketName(); // The name of the folder where the file is located.  �ļ������ļ��е�����
public String getMimeType(); // File MimeType, for example: image/jpeg.  �ļ�����
public long getAddDate(); // File to add date, must have.  �ļ�Ҫ�������
public float getLatitude(); // The latitude of the file, may be zero.  �ļ���γ��,����Ϊ��
public float getLongitude(); // The longitude of the file, may be zero.  �ļ��ľ���,����Ϊ��
public long getSize(); // File size in bytes.  �ļ���С
public long getDuration(); // Video duration, must have.  ��Ƶʱ��
public String getThumbPath(); // This is a small thumbnail.  ����һ��С����ͼ


6.��ӻ���
##------------Begin: proguard configuration for Album------------
-dontwarn com.yanzhenjie.album.**
-dontwarn com.yanzhenjie.mediascanner.**
##------------End: proguard configuration for Album--------------

