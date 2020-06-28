http://lbsyun.baidu.com/index.php?title=androidsdk

Android 地图 SDK v4.5.0

百度地图 Android SDK是一套基于Android 2.3及以上版本设备的应用程序接口。 您可以使用该套 SDK开发适用于Android系统移动设备的地图应用，通过调用地图SDK接口，您可以轻松访问百度地图服务和数据，构建功能丰富、交互性强的地图类应用程序。
自v4.0起，适配Android Wear，支持Android穿戴设备，新增室内图相关功能。
自v4.4起，新增步行AR导航、电动车骑行导航。
百度地图Android SDK提供的所有服务是免费的，接口使用无次数限制。您需申请密钥（key）后http://lbsyun.baidu.com/apiconsole/key， 才可使用百度地图Android SDK。任何非营利性产品请直接使用，商业目的产品使用前请参考使用须知。

2.下载SDK
http://lbsyun.baidu.com/index.php?title=androidsdk/sdkandev-download

3.使用Eclipse开发的开发者，将SO文件的压缩文件解压出来，把对应架构下的SO文件放入开发者自己APP的对应架构下的文件夹中（建议全部放入以提高程序兼容性）；将JAR文件拷贝到工程的libs目录下，这样即可在程序中使用Android定位SDK。
使用AndroidStutio的开发者除了上述操作外，还需要在Module的build.gradle中配置SO文件的使用，如下所示：

    //添加so库,与buildTypes { 同级
    sourceSets{
        main{
            jniLibs.srcDirs = ['libs']
        }
    }

4.添加混淆
##-----------Begin: proguard configuration for BaiDuMap----------
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**
##-----------End: proguard configuration for BaiDuMap------------

5.设置key, 写在<application>里面</application>
        <meta-data
            android:name="com.baidu.lbsapi.API_KEY"
            android:label="百度地图,设置AK，在Application标签中加入"
            android:value="XWYkQSut6NPoRQxLqGxdfgsdfgsdsdfasdfasdfOSOc35pXn4aa6"/>

6.添加权限
    <!--百度地图权限-->
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
    <uses-permission android:name="android.permission.READ_LOGS"/>
    <uses-permission android:name="android.permission.VIBRATE"/>
    <uses-permission android:name="android.permission.WAKE_LOCK"/>
    <uses-permission android:name="android.permission.WRITE_SETTINGS"/>

7.在application中初始化百度地图
        SDKInitializer.initialize(getApplicationContext());//初始化百度地图

8.xml中
    <com.baidu.mapapi.map.MapView
        android:id="@+id/mv_mapView"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    </com.baidu.mapapi.map.MapView>

9.代码中
参照智慧警务示例.

10.ScrollView嵌套百度地图的问题.
        //解决百度地图和ScrollView滑动事件冲突的问题
        mvMapView.getChildAt(0).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    nsvNestedScrollView.requestDisallowInterceptTouchEvent(false);
                } else {
                    nsvNestedScrollView.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });

11.所有API文档: http://mapopen-pub-androidsdk.cdn.bcebos.com/map/doc/v5.4.1/index.html
12.com.baidu.mapapi.map.MapView extends ViewGroup 方法: http://mapopen-pub-androidsdk.cdn.bcebos.com/map/doc/v5.4.1/index.html
public static void setCustomMapStylePath(String var0);//设置自定义地图样式的文件路径 该方法要放在地图初始化之前执行
public static void setIconCustom(int var0);//过时
public static void setMapCustomEnable(boolean var0);//设置个性化地图样式是否生效 该方法要在MapView创建完成之后执行
public static void setLoadCustomMapStyleFileMode(int var0);//设置个性化地图样式加载方式 该方法要放在地图初始化之前执行

public final BaiduMap 	getMap();//获取地图控制器
public final void 		setLogoPosition(LogoPosition var1);//设置Logo位置
public final 			LogoPosition getLogoPosition();//获取Logo位置
public final int 		getMapLevel();//获取当前地图级别对应比例尺大小
public void 			addView(View var1, LayoutParams var2);//向MapView中添加一个子View
public void 			removeView(View var1);//从MapView中移除一个子View
public void 			setPadding(int var1, int var2, int var3, int var4);
public void 			showZoomControls(boolean var1);//设置是否显示缩放控件
public void 			setZoomControlsPosition(Point var1);//设置缩放控件的位置，在 onMapLoadFinish 后生效
public void 			showScaleControl(boolean var1);//设置是否显示比例尺控件
public int 				getScaleControlViewWidth();//获取比例尺宽度
public int 				getScaleControlViewHeight();
public void 			setScaleControlPosition(Point var1);//设置比例尺控件的位置，在 onMapLoadFinish 后生效
public void 			onSaveInstanceState(Bundle var1);
public void 			onCreate(Context var1, Bundle var2);//用户重载这个方法时必须调用父类的这个方法 用于MapView保存地图状态
public final void 		setZOrderMediaOverlay(boolean var1);//设置多个MapView显示时, 当前SurfaceView在上面
public void 			setUpViewEventToMapView(MotionEvent var1);//设置MotionEvent
public void 			handleTouchDown(float var1, float var2);//外部传入手势触发的屏幕坐标, 触发地图单指的touchDown手势
public boolean 			handleTouchUp(float var1, float var2);//外部传入手势触发的屏幕坐标, 触发地图单指的touchUp手势
public boolean 			inRangeOfView(float var1, float var2);//判断当前触摸点是否在地图上
public boolean 			handleMultiTouch(float var1, float var2, float var3, float var4);//处理多指情况
public boolean 			handleTouchMove(float var1, float var2);//外部传入手势触发的屏幕坐标, 触发地图单指的touchMove手势
public void 			renderMap();//主动渲染地图：结合之前传入的手势操作，控制地图的绘制
public void 			cancelRenderMap();//取消主动渲染地图

public final void onResume();
public final void onPause();
public final void onDestroy();

public void removeViewAt(1);//ViewGroup方法, 不显示百度地图Logo


13.com.baidu.mapapi.map.BaiduMap 方法
public final Overlay 		addOverlay(OverlayOptions var1);//在地图上添加Marker
public final List<Overlay> 	addOverlays(List<OverlayOptions> var1);
public List<Marker> 		getMarkersInBounds(LatLngBounds var1);
public final void 			clear();//清除所有Overlay
public final void 			setMapStatus(MapStatusUpdate var1);//改变地图状态
                  			setMapStatus(MapStatusUpdateFactory.zoomTo(15));
                  			setMapStatus(MapStatusUpdateFactory.newLatLng(point));//设置地图新中心点
        MapStatus mMapStatus = new MapStatus.Builder().zoom(14).target(point).build();//定位到point, 不会显示覆盖物(箭头)
                  			setMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));

public final MapStatus 		getMapStatus();
public final LatLngBounds 	getMapStatusLimit();//设置地图的可移动区域 V4.5.0版本起
public final void 			setMapStatusLimits(LatLngBounds var1);
public final void 			animateMapStatus(MapStatusUpdate var1, int var2);
public float 				getZoomToBound(int var1, int var2, int var3, int var4, int var5, int var6);
public final void 			animateMapStatus(MapStatusUpdate var1);
public final void 			setMapType(int var1);//BaiduMap.MAP_TYPE_SATELLITE   none, normal, satellite(卫星)
public final int 			getMapType();
public j 					getmGLMapView();
public final float 			getMaxZoomLevel();
public final void 			setMaxAndMinZoomLevel(float max, float min);//设置地图最大以及最小缩放级别，地图支持的最大最小级别分别为[4-21] V5.0.0版本起
public final float 			getMinZoomLevel();
public final Projection 	getProjection();
public final UiSettings 	getUiSettings();
public final void 			setBuildingsEnabled(boolean var1);
public final boolean 		isBuildingsEnabled();
public final void 			setMyLocationEnabled(boolean var1);
public final boolean 		isMyLocationEnabled();
public final void 			setMyLocationData(MyLocationData var1);
public final MyLocationData getLocationData();
public final void 			setMyLocationConfiguration(MyLocationConfiguration var1);
public final MyLocationConfiguration getLocationConfiguration();
public void 				addHeatMap(HeatMap var1);//添加热力图
public final void 			setBaiduHeatMapEnabled(boolean var1);
public final void 			setIndoorEnable(boolean var1);
public void 				setCompassPosition(Point var1);
public void 				setCompassIcon(Bitmap var1);
public final Point 			getCompassPosition();
public final boolean 		isBaiduHeatMapEnabled();
public final boolean 		isSupportBaiduHeatMap();
public final void 			setTrafficEnabled(boolean var1);
public boolean 				setCustomTrafficColor(String var1, String var2, String var3, String var4);
public final boolean 		isTrafficEnabled();
public final void 			showMapPoi(boolean isShow);//控制是否显示底图默认标注, 默认显示
public final void 			showMapIndoorPoi(boolean var1);
public final void 			setViewPadding(int var1, int var2, int var3, int var4);
public final void 			snapshot(BaiduMap.SnapshotReadyCallback var1);
public final void 			snapshotScope(Rect var1, BaiduMap.SnapshotReadyCallback var2);
public void 				showInfoWindow(InfoWindow var1);//显示 InfoWindow, 该接口会先隐藏其他已添加的InfoWindow, 再添加新的InfoWindow
public void 				showInfoWindow(InfoWindow var1, boolean isHideOthers);//5.4.0版本新增接口 显示 InfoWindow, 该接口可以设置是否在添加InfoWindow之前，先隐藏其他已经添加的InfoWindow
public void 				showInfoWindows(List<InfoWindow> var1);//5.4.0版本新增接口 添加多个InfoWindow
public List<InfoWindow> 	getAllInfoWindows();
public void 				hideInfoWindow();//隐藏地图上的所有InfoWindow
public void 				hideInfoWindow(InfoWindow var1);//5.4.0版本新增接口 清除特定的InfoWindow
public void 				setPixelFormatTransparent(boolean var1);
public final void 			setOnMapStatusChangeListener(BaiduMap.OnMapStatusChangeListener var1);
public final void 			setOnMapTouchListener(BaiduMap.OnMapTouchListener var1);
public final void setOnMapClickListener(new BaiduMap.OnMapClickListener() {//点击事件
    public void onMapClick(LatLng point) {
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(point));
    }
    public boolean onMapPoiClick(MapPoi poi) {//地图内 Poi(底图标注) 单击事件
        return false;
    }
});
public void 		setOnMapLoadedCallback(BaiduMap.OnMapLoadedCallback var1);
public void 		setOnMapRenderCallbadk(BaiduMap.OnMapRenderCallback var1);
public final void 	setOnMapDoubleClickListener(BaiduMap.OnMapDoubleClickListener var1);//双击
public final void 	setOnMapLongClickListener(BaiduMap.OnMapLongClickListener var1);//长按
public final void 	setOnPolylineClickListener(BaiduMap.OnPolylineClickListener var1);//折线点击
public final void setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {//覆盖物(地图标注)点击
            @Override
            public boolean onMarkerClick(Marker marker) {
                showInfoWindow(marker.getPosition(), "负责人:".concat(getNoNullString(reportName)));
                return true;//若响应点击事件，返回true，否则返回false
            }
        });
public final void setOnMarkerDragListener(BaiduMap.OnMarkerDragListener var1);
public final void setOnMyLocationClickListener(BaiduMap.OnMyLocationClickListener var1);
public final void setOnMapDrawFrameCallback(BaiduMap.OnMapDrawFrameCallback var1);
public final void setOnBaseIndoorMapListener(BaiduMap.OnBaseIndoorMapListener var1);
public final void setOnMapRenderValidDataListener(BaiduMap.OnMapRenderValidDataListener var1);
public final void setOnSynchronizationListener(BaiduMap.OnSynchronizationListener var1);
public MapBaseIndoorMapInfo getFocusedBaseIndoorMapInfo();
public SwitchFloorError 	switchBaseIndoorMapFloor(String var1, String var2);
public boolean 				isBaseIndoorMapMode();
public final void 			removeMarkerClickListener(BaiduMap.OnMarkerClickListener var1);
public TileOverlay 			addTileLayer(TileOverlayOptions var1);
public void 				hideSDKLayer();
public void 				showSDKLayer();//显示SDK图层，从实景模式（步行AR导航）进入普通导航模式时调用
public void 				changeLocationLayerOrder(boolean var1);
public void 				setCompassEnable(boolean var1);
public float[] 				getProjectionMatrix();
public float[] 				getViewMatrix();
public void 				setOverlayUnderPoi(boolean var1);//设置覆盖物图层相对于Poi图层的图层顺序 默认覆盖物图层在Poi图层之上 通过调用该接口，设置设置覆盖物图层在Poi图层之下，可以解决覆盖物遮盖Poi名称的问题，比如Polyline遮挡路线名称 V5.4.0版本新增接口


//https://www.jianshu.com/p/fdd1ba783495
if (points.size() == 2) {//绘制折线
    PolylineOptions polylineOptions= new PolylineOptions();//参数设置类
    polylineOptions.width(10);//宽度,单位：像素
    polylineOptions.color(0xAAFF0000);//设置折线颜色
    polylineOptions.points(points);//折线顶点坐标集
    baiduMap.addOverlay(polylineOptions);
} else if (points.size() >= 3) {//绘制多边形, 必须>=3才能绘多边形, 否则报错
    PolygonOptions polygonOptions = new PolygonOptions();
    polygonOptions.points(points);//多边形顶点坐标集
    polygonOptions.stroke(new Stroke(5, 0xAAFF0000));//边框样式
    polygonOptions.fillColor(0xAAFF0000);//填充颜色
    baiduMap.addOverlay(polygonOptions);//在地图上增加mPolygon 图层
}



14.坐标点
LatLng point = new LatLng(lat, lng);//坐标点

15.Overlay 覆盖物, 方法
public boolean isVisible()
public void setVisible(boolean visible);//★★★动态设置覆盖物可见性(但还是可以点击...)★★★
public int getZIndex()
public void setZIndex(int var1)
public Bundle getExtraInfo();//★★★
public void setExtraInfo(Bundle var1)
public void remove();//★★★移除覆盖物★★★
  子类:
      Marker
	  GroundOverlay
	  Polyline
	  Dot
	  Text
	  Circle
	  Polygon
	  Arc
	  ItemizedOverlay

16.OverlayOptions 覆盖物选项
父类: abstract OverlayOptions//覆盖物, 抽象方法, 必须是子类实现
子类: 1.DotOptions//圆点
            public LatLng getCenter();//获取圆点圆心坐标
			public int getColor();//获取圆点的颜色
			public int getRadius();//获取圆点的半径，单位：像素
			public DotOptions center(LatLng var1);//设置圆点的圆心坐标
			public DotOptions color(int var1);//设置圆点的颜色
			public DotOptions radius(int var1);//设置圆点的半径，单位：像素, 默认为 5px
			public DotOptions visible(boolean var1);//设置圆点是否可见
			public boolean isVisible();//获取圆点是否可见
			public DotOptions zIndex(int var1);//设置圆点的 zIndex
			public int getZIndex();//获取圆点的 zIndex
			public DotOptions extraInfo(Bundle var1);//设置圆点的额外信息
			public Bundle getExtraInfo();//获取圆点的额外信息
	  2.TextOptions//文字
	        public String getText()
			public LatLng getPosition();//地理坐标
			public int getBgColor();//获取背景颜色
			public int getFontColor();//获取文字颜色
			public int getFontSize();//获取字体大小
			public Typeface getTypeface();//字体
			public float getAlignX();//水平对齐方式
			public float getAlignY();//垂直对齐方式
			public float getRotate();//旋转角度
			public TextOptions text(String var1)
			public TextOptions position(LatLng var1);//设置地理坐标
			public TextOptions bgColor(int var1);//背景颜色
			public TextOptions fontColor(int var1);//字体颜色，默认黑色
			public TextOptions fontSize(int var1);//字体大小
			public TextOptions typeface(Typeface var1);//字体
			public TextOptions align(int alignX, int alignY);//对齐方式，默认居中对齐
			public TextOptions rotate(float var1);//设置旋转角度，逆时针
			public TextOptions visible(boolean var1)
			public boolean isVisible()
			public TextOptions zIndex(int var1);//设置文字覆盖物 zIndex
			public int getZIndex();//获取文字覆盖物zIndex
			public TextOptions extraInfo(Bundle var1)
			public Bundle getExtraInfo()
	  3.MarkerOptions//标注 http://mapopen-pub-androidsdk.cdn.bcebos.com/map/doc/v5.4.1/index.html
	        public MarkerOptions icon(BitmapDescriptor var1);//设置 Marker 覆盖物的图标，相同图案的 icon 的 marker 最好使用同一个 BitmapDescriptor 对象以节省内存空间
		    public MarkerOptions animateType(MarkerOptions.MarkerAnimateType var1);//none,drop,grow,jump;  添加动画，目前支持掉下和生长两种
		    public MarkerOptions.MarkerAnimateType getAnimateType();//获取marker动画类型
		    public MarkerOptions alpha(float var1);//设置 Marker 覆盖物图标的透明度，取值为[0,1]
		    public MarkerOptions scaleX(float var1);//覆盖物X方向缩放, 必须点击坐标点附近距离, 放大的部分点击没响应
		    public MarkerOptions scaleY(float var1);//覆盖物X方向缩放
		    public float getAlpha();//获取Marker图标透明度
		    public BitmapDescriptor getIcon();//获取 Marker 覆盖物的图标
		    public MarkerOptions icons(ArrayList<BitmapDescriptor> var1);//设置 Marker 覆盖物的图标，相同图案的 icon 的 marker 最好使用同一个 BitmapDescriptor 对象以节省内存空间
		    public ArrayList<BitmapDescriptor> getIcons();//返回Marker的动画帧列表，动画的描点和大小以第一帧为准，建议图片大小保持一致
		    public MarkerOptions period(int var1);//设置多少帧刷新一次图片资源，Marker动画的间隔时间，值越小动画越快
		    public int getPeriod();//得到多少帧刷新一次图片资源，值越小动画越快
		    public MarkerOptions position(LatLng var1);//设置 marker 覆盖物的位置坐标
		    public LatLng getPosition();//获取 marker 覆盖物的位置坐标
		    public MarkerOptions perspective(boolean var1);//是否开启 marker 覆盖物近大远小效果，默认开启
		    public boolean isPerspective();//marker 覆盖物是否开启近大远小效果
		    public MarkerOptions yOffset(int var1);//Marker坐标的Y偏移量
		    public MarkerOptions draggable(boolean var1);//marker 是否允许拖拽
		    public boolean isFlat();//marker 是否平贴地图
		    public MarkerOptions flat(boolean var1);//设置 marker设置 是否平贴地图
		    public boolean isDraggable();//覆盖物是否可以拖拽
		    public MarkerOptions anchor(float var1, float var2);//设置 marker 覆盖物的锚点比例，默认（0.5f, 1.0f）水平居中，垂直下对齐
		    public float getAnchorX();//获取 marker 覆盖物水平方向锚点比例
		    public float getAnchorY()//获取 marker 覆盖物垂直方向锚点比例
		    public MarkerOptions rotate(float var1);//marker 覆盖物旋转角度，逆时针
		    public float getRotate();//获取 marker 覆盖物旋转角度
		    public MarkerOptions title(String var1);//过时
		    public String getTitle();//过时
		    public MarkerOptions fixedScreenPosition(Point var1);//设置 Marker 覆盖物屏幕位置点
		    public MarkerOptions infoWindow(InfoWindow var1);//设置 Marker 绑定的InfoWindow(直接显示InfoWindow)
		    public MarkerOptions visible(boolean var1);//设置 marker 覆盖物的可见性(初始化时设置, 不能动态设置可见性, 使用 overlay.setVisible();)
		    public boolean isVisible();//marker 覆盖物的可见性
		    public MarkerOptions zIndex(int var1);//设置 marker 覆盖物的 zIndex
		    public int getZIndex();//获取 marker 覆盖物的 zIndex
		    public MarkerOptions extraInfo(Bundle var1);//★★★设置 marker 覆盖物的额外信息★★★
		    public Bundle getExtraInfo();//获取marker覆盖物的额外信息
	  4.GroundOverlayOptions//地形图图层((例如:自定义景区大图, 把大图放进去https://blog.csdn.net/u013184970/article/details/84023652)
	        public GroundOverlayOptions image(BitmapDescriptor image);//设置 Ground 覆盖物的图片信息
			public BitmapDescriptor getImage();//覆盖物的图片信息
			public GroundOverlayOptions position(LatLng var1);//设置 Ground 覆盖物位置信息方式一，与 dimensions(int, int)配合使用
			public LatLng getPosition();//覆盖物位置信息
			public GroundOverlayOptions dimensions(int width);//设置 ground 覆盖物的宽度，单位：米， 高度按图片宽高比例
			public GroundOverlayOptions dimensions(int width, int height);//设置 ground 覆盖物的宽度和高度，单位：米
			public int getWidth();//覆盖物的宽度，单位：米
			public int getHeight();//覆盖物的高度，单位：米
			public GroundOverlayOptions anchor(float anchorX, float anchorY);//当使用 setPosition()方式设置覆盖物地理位置时，设置 ground 覆盖物的锚点比例，默认为 0.5f，水平和垂直方向都居中对齐
			public float getAnchorX();//水平方向锚点比例
			public float getAnchorY()//垂直方向锚点比例
			public GroundOverlayOptions positionFromBounds(LatLngBounds bounds);//设置 ground 覆盖物的位置信息方式二，设置西南与东北坐标范围
			public LatLngBounds getBounds();//地理坐标范围
			public GroundOverlayOptions transparency(float var1);//设置 ground 覆盖物透明度
			public float getTransparency();//覆盖物透明度
			public GroundOverlayOptions visible(boolean var1)
			public boolean isVisible()
			public GroundOverlayOptions zIndex(int var1);//设置 ground 覆盖物的 zIndex
			public int getZIndex();//覆盖物的 zIndex
			public GroundOverlayOptions extraInfo(Bundle var1)
			public Bundle getExtraInfo()
	  5.ArcOptions//弧线形 http://mapopen-pub-androidsdk.cdn.bcebos.com/map/doc/v5.4.1/index.html
	        public int getColor();//获取弧线的颜色
			public LatLng getStartPoint();//弧线的起点坐标
			public LatLng getMiddlePoint();//弧线的中点坐标
			public LatLng getEndPoint();//弧线的终点坐标
			public int getWidth();//弧线的线宽， 单位：像素
			public ArcOptions color(int var1);//弧线的颜色
			public ArcOptions points(LatLng start, LatLng middle, LatLng end);//设置弧线的起点、中点、终点坐标
			public ArcOptions width(int var1);//设置弧线的线宽
			public ArcOptions visible(boolean var1)
			public boolean isVisible()
			public ArcOptions zIndex(int var1);//设置弧线的 zIndex
			public int getZIndex();//弧线的 zIndex
			public ArcOptions extraInfo(Bundle var1)
			public Bundle getExtraInfo()
	  6.CircleOptions//圆形
	        public LatLng getCenter()
			public int getFillColor()
			public int getRadius()
			public Stroke getStroke()
			public CircleOptions center(LatLng var1)
			public CircleOptions fillColor(int var1)
			public CircleOptions radius(int var1)
			public CircleOptions stroke(Stroke var1)
			public CircleOptions visible(boolean var1)
			public boolean isVisible()
			public CircleOptions zIndex(int var1)
			public int getZIndex()
			public CircleOptions extraInfo(Bundle var1)
			public Bundle getExtraInfo()
	  7.PolygonOptions//多边形
	        public int getFillColor()
			public List<LatLng> getPoints()
			public Stroke getStroke()
			public PolygonOptions fillColor(int var1)
			public PolygonOptions points(List<LatLng> var1)
			public PolygonOptions stroke(Stroke var1)
			public PolygonOptions visible(boolean var1)
			public boolean isVisible()
			public PolygonOptions zIndex(int var1)
			public PolygonOptions zIndex(int var1)
			public PolygonOptions extraInfo(Bundle var1)
			public Bundle getExtraInfo()
	  8.PolylineOptions//折线

BitmapDescriptor baiduBitmap = BitmapDescriptorFactory.fromResource(R.drawable.baidumap_location_icon);//可只创建1个对象,onDestroy时回收 bitmap 资源
BitmapDescriptor baiduBitmap = BitmapDescriptorFactory.fromView(view);//除了view, 还有很多其他方法
//创建marker
MarkerOptions marker = new MarkerOptions().position(point).icon(baiduBitmap);// 构建MarkerOption，用于在地图上添加Marker
baiduBitmap.recycle();//回收 bitmap 资源


17.MapStatusUpdateFactory http://mapopen-pub-androidsdk.cdn.bcebos.com/map/doc/v5.4.1/index.html
public static MapStatusUpdate newMapStatus(MapStatus var0);//设置地图新状态
public static MapStatusUpdate newLatLng(LatLng var0);//设置地图新中心点
public static MapStatusUpdate newLatLngBounds(LatLngBounds var0);//设置显示在屏幕中的地图地理范围
public static MapStatusUpdate newLatLngBounds(LatLngBounds bounds, int width, int height);//设置显示在规定宽高中的地图地理范围
public static MapStatusUpdate newLatLngZoom(LatLng latLng, float zoom);//设置地图中心点以及缩放级别
public static MapStatusUpdate scrollBy(int xPixel, int yPixel);//按像素移动地图中心点
public static MapStatusUpdate zoomBy(float amount);//根据给定增量缩放地图级别
public static MapStatusUpdate zoomBy(float amount, Point focus);//根据给定增量以及给定的屏幕坐标缩放地图级别
public static MapStatusUpdate zoomIn();//放大地图缩放级别
public static MapStatusUpdate zoomOut();//缩小地图缩放级别
public static MapStatusUpdate zoomTo(float zoom);//设置地图缩放级别
public static MapStatusUpdate newLatLngBounds(LatLngBounds bounds, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom);//设置显示在指定相对与MapView的padding中的地图地理范围
public static MapStatusUpdate newLatLngZoom(LatLngBounds bounds, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom);//根据Padding设置地理范围的合适缩放级别


18.InfoWindow http://mapopen-pub-androidsdk.cdn.bcebos.com/map/doc/v5.4.1/index.html
   注意: InfoWindow 每次都new, 否则会出现重叠的情况
public InfoWindow(View view, LatLng position, int yOffset);//通过传入的 view 构造一个 InfoWindow, 此时只是利用该view生成一个Bitmap绘制在地图中，监听事件由开发者实现
public InfoWindow(BitmapDescriptor bd, LatLng position, int yOffset, InfoWindow.OnInfoWindowClickListener listener);//通过传入的 BitmapDescriptor 构造一个 InfoWindow
public InfoWindow(View view, LatLng position, int yOffset, boolean isFitDensityDpi, int targetDensityDpi);//根据指定的像素密度对传入的view构造InfoWindow, 此时只是利用该view生成一个Bitmap绘制在地图中，监听事件由开发者实现
public LatLng getPosition();//获取位置
public void setPosition(LatLng var1)
public BitmapDescriptor getBitmapDescriptor();//获取InfoWindow的BitmapDescriptor资源
public void setBitmapDescriptor(BitmapDescriptor var1);//更新InfoWindow的BitmapDescriptor属性
public View getView();
public void setView(View var1)
public int getYOffset()
public void setYOffset(int var1)
public String getTag()
public void setTag(String var1)


//百度位置逆编码
//com.baidu.mapapi.search.geocode.GeoCoder 地址解析类, 需要勾选"检索功能", 否则没有这个类,用于在地址和经纬度之间进行转换的服务
GeoCoder geoCoder = GeoCoder.newInstance();
geoCoder.geocode(new GeoCodeOption().city("city").address("address"));//编码,根据地址得到经纬度
geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(point));//逆编码,根据经纬度得到地址
geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
        //获取地理编码结果(根据地址获取point)
        if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
            String address = result.getAddress();
            LatLng location = result.getLocation();
            int i = result.describeContents();
            result.setAddress("address");
        }//else 没有检索到结果
    }
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {//主线程
        //获取反向地理编码结果(根据point获取地址)
        if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
            String address = result.getAddress();
            ReverseGeoCodeResult.AddressComponent addressDetail = result.getAddressDetail();
            String businessCircle = result.getBusinessCircle();
            LatLng location = result.getLocation();
            int i = result.describeContents();
            List<PoiInfo> poiList = result.getPoiList();
        }//else 没有找到检索结果
    }
});
geoCoder.destroy();
























