https://github.com/yanzhenjie/Album
Album是一个MD风格的开源相册，支持国际化，支持国际化扩展；主要功能模块：选择图片与视频、拍照、录视频、画廊（支持本地和网络）。
技术交流群：547839514

我的主页：http://www.yanzhenjie.com
我的微博：http://weibo.com/yanzhenjieit

图片剪切框架：https://github.com/yanzhenjie/Durban
Http请求框架：https://github.com/yanzhenjie/NoHttp

特性
Album is a Material Design style album, it provides three functions: album, camera and gallery.
Select images, selecte videos, or select pictures and videos.
Take a picture, record a video, or use camera in album list.
Preview pictures and videos in the gallery or select pictures and videos in the gallery.
画册是一种材料设计风格的画册，它提供了画册、相机和画廊三个功能。
选择图片，选择视频，或者选择图片和视频。
在相册列表中拍照、录制视频或使用相机。
预览图片和视频在画廊或选择图片和视频在画廊。


用法:
1.添加依赖
implementation 'com.yanzhenjie:album:2.1.3'//图片选择,预览图片(包括网络图片)

2.添加权限(好像不用添加了??)
<!--Album的权限,拍照,图片选择,图片大图预览(包括网络图片)-->
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>

3.Application中配置
/**
 * 配置画廊
 */
Album.initialize(AlbumConfig.newBuilder(this)
        .setAlbumLoader(new GlideAlbumLoader()) // 设置Album加载器。
        .setLocale(Locale.getDefault()) //Locale.CHINA 比如强制设置在任何语言下都用中文显示。
        .build());

4.选择图片
//图像和视频混合选项
AlbumUtils.selectVideo$Image//自己封装工具类

//图片选择
AlbumUtils.selectImage
如果你接着需要对图片进行裁剪，那么你可以紧接着调用Durban。

//视频选择
AlbumUtils.selectVideo

//拍照
AlbumUtils.takePhoto

//录制视频
AlbumUtils.recordVideo

//通过AlbumFile path预览图片
AlbumUtils.galleryAlbum

//通过String path预览图片
AlbumUtils.gallery
如果你接着需要对图片进行裁剪，那么你可以紧接着调用Durban。

5.选择照片&视频 属性区别
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


AlbumFile 是图像和视频选择的结果，图像和视频的属性是不同的，下面列出了它们的不同属性。
1.Image
public int getMediaType(); // File type, the image is AlbumFile.TYPE_IMAGE.  媒体类型
public String getPath(); // File path, must not be empty.  文件路径
public String getBucketName(); // The name of the folder where the file is located.  文件所在文件夹的名称
public String getMimeType(); // File MimeType, for example: image/jpeg.  文件类型
public long getAddDate(); // File to add date, must have.  文件要添加日期
public float getLatitude(); // The latitude of the file, may be zero.  文件的纬度,可能为零
public float getLongitude(); // The longitude of the file, may be zero.  文件的经度,可能为零
public long getSize(); // File size in bytes.  文件大小
public String getThumbPath(); // This is a small thumbnail.  这是一个小缩略图

2.Video
public int getMediaType(); // File type, the video is AlbumFile.TYPE_VIDEO.  媒体类型
public String getPath(); // File path, must not be empty.  文件路径
public String getBucketName(); // The name of the folder where the file is located.  文件所在文件夹的名称
public String getMimeType(); // File MimeType, for example: image/jpeg.  文件类型
public long getAddDate(); // File to add date, must have.  文件要添加日期
public float getLatitude(); // The latitude of the file, may be zero.  文件的纬度,可能为零
public float getLongitude(); // The longitude of the file, may be zero.  文件的经度,可能为零
public long getSize(); // File size in bytes.  文件大小
public long getDuration(); // Video duration, must have.  视频时长
public String getThumbPath(); // This is a small thumbnail.  这是一个小缩略图


6.添加混淆
##------------Begin: proguard configuration for Album------------
-dontwarn com.yanzhenjie.album.**
-dontwarn com.yanzhenjie.mediascanner.**
##------------End: proguard configuration for Album--------------

