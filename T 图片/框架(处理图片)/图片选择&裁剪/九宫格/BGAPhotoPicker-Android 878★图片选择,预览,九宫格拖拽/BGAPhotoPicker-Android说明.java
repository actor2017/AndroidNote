https://github.com/bingoogolapple/BGAPhotoPicker-Android

将 MeiqiaSDK-Android 里的图库单独抽出来开源，将其中的 GridView、ListView 和 RelativeLayout 换成 RecyclerView 和 Toolbar，方便在以后的项目中直接依赖使用。Demo 中模仿了微信朋友圈的部分功能，详细用法请查看 Demo。希望能该库帮正在做这几个功能的猿友节省开发时间。

 单图选择
 多图选择
 拍照选择
 图片选择预览（支持微博长图）、缩放查看
 图片预览（支持微博长图）、缩放查看
 支持 glide、picasso、universal-image-loader、xutils 图片加载库
 支持配置列表滚动时是否暂停加载图片，列表停止滚动时恢复加载图片(用 xutils 作为图片加载库时该配置无效)
 正方形、圆形头像、带边框的圆形头像控件
 朋友圈列表界面的九宫格图片控件
 发布朋友圈界面的可拖拽排序的九宫格图片控件
 覆盖相应的资源文件来定制界面

1.添加 Gradle 依赖
Download bga-photopicker 后面的「latestVersion」指的是左边这个 Download 徽章后面的「数字」，请自行替换。请不要再来问我「latestVersion」是什么了

由于需要支持微博长图预览，该库中已经引入了 PhotoView 的源码并进行了修改，所以你的项目中就不要再重复引入 PhotoView 了

dependencies {
    // -------------------- 以下4个库是必须依赖的 ----------------------------
    implementation 'cn.bingoogolapple:bga-photopicker:latestVersion@aar'
    implementation 'com.android.support:appcompat-v7:27.0.1'
    implementation 'com.android.support:support-v4:27.0.1'
    implementation 'com.android.support:recyclerview-v7:27.0.1'
    implementation 'cn.bingoogolapple:bga-baseadapter:1.2.7@aar'
    // -------------------- 以上4个库是必须依赖的 ----------------------------

    // 目前支持常见的 4 种图片加载库，必须在下面四个图片加载库中选择一个添加依赖
    implementation 'com.github.bumptech.glide:glide:4.3.1'
//    implementation 'com.squareup.picasso:picasso:2.5.2'
//    implementation 'com.nostra13.universalimageloader:universal-image-loader:1.9.5'
//    implementation 'org.xutils:xutils:3.5.0'
}

//九宫格
<cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout
    android:id="@+id/bga_nine_photo"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:layout_marginTop="18dp"
    android:layout_marginRight="27dp"
    app:bga_npl_itemCornerRadius="3dp"	//Item 条目圆角尺寸，默认值为 0dp
    app:bga_npl_itemSpanCount="3"		//列数,默认值为 3，当该值大于 3 并且数据源里只有四张图片时不会显示成 2 列
    app:bga_npl_itemWhiteSpacing="8dp"	//Item 间的水平和垂直间距，默认值为 4dp
    app:bga_npl_itemWidth="0dp"			//item 的尺寸，优先级高于 bga_npl_otherWhiteSpacing，默认值为 0dp
	app:bga_npl_otherWhiteSpacing="54dp"//出去九宫格部分的空白区域的尺寸，默认值为 100dp(必填,否则右边会填不满)
    app:bga_npl_placeholderDrawable="@mipmap/bga_pp_ic_holder_light"//占位图资源，默认值为 R.mipmap.bga_pp_ic_holder_light
	app:bga_npl_showAsLargeWhenOnlyOne="true">//当只有一张图片时，是否显示成大图，默认值为 true
</cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout>

//如果报错:
java.lang.NoSuchMethodError: No virtual method placeholder(I)Lcom/bumptech/glide/request/RequestOptions; in class Lcom/bumptech/glide/request/RequestOptions; or its super classes (declaration of 'com.bumptech.glide.request.RequestOptions' appears in /data/app/com.ly.hihifriend-1/split_lib_dependencies_apk.apk)

在使用这个框架前初始化:
BGAImage.setImageLoader(new MyBGAImageLoader());//Glide加载报错,自定义加载
