http://lbsyun.baidu.com/index.php?title=androidsdk

Android ��ͼ SDK v4.5.0

�ٶȵ�ͼ Android SDK��һ�׻���Android 2.3�����ϰ汾�豸��Ӧ�ó���ӿڡ� ������ʹ�ø��� SDK����������Androidϵͳ�ƶ��豸�ĵ�ͼӦ�ã�ͨ�����õ�ͼSDK�ӿڣ����������ɷ��ʰٶȵ�ͼ��������ݣ��������ܷḻ��������ǿ�ĵ�ͼ��Ӧ�ó���
��v4.0������Android Wear��֧��Android�����豸����������ͼ��ع��ܡ�
��v4.4����������AR�������綯�����е�����
�ٶȵ�ͼAndroid SDK�ṩ�����з�������ѵģ��ӿ�ʹ���޴������ơ�����������Կ��key����http://lbsyun.baidu.com/apiconsole/key�� �ſ�ʹ�ðٶȵ�ͼAndroid SDK���κη�Ӫ���Բ�Ʒ��ֱ��ʹ�ã���ҵĿ�Ĳ�Ʒʹ��ǰ��ο�ʹ����֪��

2.����SDK
http://lbsyun.baidu.com/index.php?title=androidsdk/sdkandev-download

3.ʹ��Eclipse�����Ŀ����ߣ���SO�ļ���ѹ���ļ���ѹ�������Ѷ�Ӧ�ܹ��µ�SO�ļ����뿪�����Լ�APP�Ķ�Ӧ�ܹ��µ��ļ����У�����ȫ����������߳�������ԣ�����JAR�ļ����������̵�libsĿ¼�£����������ڳ�����ʹ��Android��λSDK��
ʹ��AndroidStutio�Ŀ����߳������������⣬����Ҫ��Module��build.gradle������SO�ļ���ʹ�ã�������ʾ��

    //���so��,��buildTypes { ͬ��
    sourceSets{
        main{
            jniLibs.srcDirs = ['libs']
        }
    }

4.��ӻ���
##-----------Begin: proguard configuration for BaiDuMap----------
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**
##-----------End: proguard configuration for BaiDuMap------------

5.����key, д��<application>����</application>
        <meta-data
            android:name="com.baidu.lbsapi.API_KEY"
            android:label="�ٶȵ�ͼ,����AK����Application��ǩ�м���"
            android:value="XWYkQSut6NPoRQxLqGxdfgsdfgsdsdfasdfasdfOSOc35pXn4aa6"/>

6.���Ȩ��
    <!--�ٶȵ�ͼȨ��-->
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

7.��application�г�ʼ���ٶȵ�ͼ
        SDKInitializer.initialize(getApplicationContext());//��ʼ���ٶȵ�ͼ

8.xml��
    <com.baidu.mapapi.map.MapView
        android:id="@+id/mv_mapView"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    </com.baidu.mapapi.map.MapView>

9.������
�����ǻ۾���ʾ��.

10.ScrollViewǶ�װٶȵ�ͼ������.
        //����ٶȵ�ͼ��ScrollView�����¼���ͻ������
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

11.����API�ĵ�: http://mapopen-pub-androidsdk.cdn.bcebos.com/map/doc/v5.4.1/index.html
12.com.baidu.mapapi.map.MapView extends ViewGroup ����: http://mapopen-pub-androidsdk.cdn.bcebos.com/map/doc/v5.4.1/index.html
public static void setCustomMapStylePath(String var0);//�����Զ����ͼ��ʽ���ļ�·�� �÷���Ҫ���ڵ�ͼ��ʼ��֮ǰִ��
public static void setIconCustom(int var0);//��ʱ
public static void setMapCustomEnable(boolean var0);//���ø��Ի���ͼ��ʽ�Ƿ���Ч �÷���Ҫ��MapView�������֮��ִ��
public static void setLoadCustomMapStyleFileMode(int var0);//���ø��Ի���ͼ��ʽ���ط�ʽ �÷���Ҫ���ڵ�ͼ��ʼ��֮ǰִ��

public final BaiduMap 	getMap();//��ȡ��ͼ������
public final void 		setLogoPosition(LogoPosition var1);//����Logoλ��
public final 			LogoPosition getLogoPosition();//��ȡLogoλ��
public final int 		getMapLevel();//��ȡ��ǰ��ͼ�����Ӧ�����ߴ�С
public void 			addView(View var1, LayoutParams var2);//��MapView�����һ����View
public void 			removeView(View var1);//��MapView���Ƴ�һ����View
public void 			setPadding(int var1, int var2, int var3, int var4);
public void 			showZoomControls(boolean var1);//�����Ƿ���ʾ���ſؼ�
public void 			setZoomControlsPosition(Point var1);//�������ſؼ���λ�ã��� onMapLoadFinish ����Ч
public void 			showScaleControl(boolean var1);//�����Ƿ���ʾ�����߿ؼ�
public int 				getScaleControlViewWidth();//��ȡ�����߿��
public int 				getScaleControlViewHeight();
public void 			setScaleControlPosition(Point var1);//���ñ����߿ؼ���λ�ã��� onMapLoadFinish ����Ч
public void 			onSaveInstanceState(Bundle var1);
public void 			onCreate(Context var1, Bundle var2);//�û������������ʱ������ø����������� ����MapView�����ͼ״̬
public final void 		setZOrderMediaOverlay(boolean var1);//���ö��MapView��ʾʱ, ��ǰSurfaceView������
public void 			setUpViewEventToMapView(MotionEvent var1);//����MotionEvent
public void 			handleTouchDown(float var1, float var2);//�ⲿ�������ƴ�������Ļ����, ������ͼ��ָ��touchDown����
public boolean 			handleTouchUp(float var1, float var2);//�ⲿ�������ƴ�������Ļ����, ������ͼ��ָ��touchUp����
public boolean 			inRangeOfView(float var1, float var2);//�жϵ�ǰ�������Ƿ��ڵ�ͼ��
public boolean 			handleMultiTouch(float var1, float var2, float var3, float var4);//�����ָ���
public boolean 			handleTouchMove(float var1, float var2);//�ⲿ�������ƴ�������Ļ����, ������ͼ��ָ��touchMove����
public void 			renderMap();//������Ⱦ��ͼ�����֮ǰ��������Ʋ��������Ƶ�ͼ�Ļ���
public void 			cancelRenderMap();//ȡ��������Ⱦ��ͼ

public final void onResume();
public final void onPause();
public final void onDestroy();

public void removeViewAt(1);//ViewGroup����, ����ʾ�ٶȵ�ͼLogo


13.com.baidu.mapapi.map.BaiduMap ����
public final Overlay 		addOverlay(OverlayOptions var1);//�ڵ�ͼ�����Marker
public final List<Overlay> 	addOverlays(List<OverlayOptions> var1);
public List<Marker> 		getMarkersInBounds(LatLngBounds var1);
public final void 			clear();//�������Overlay
public final void 			setMapStatus(MapStatusUpdate var1);//�ı��ͼ״̬
                  			setMapStatus(MapStatusUpdateFactory.zoomTo(15));
                  			setMapStatus(MapStatusUpdateFactory.newLatLng(point));//���õ�ͼ�����ĵ�
        MapStatus mMapStatus = new MapStatus.Builder().zoom(14).target(point).build();//��λ��point, ������ʾ������(��ͷ)
                  			setMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));

public final MapStatus 		getMapStatus();
public final LatLngBounds 	getMapStatusLimit();//���õ�ͼ�Ŀ��ƶ����� V4.5.0�汾��
public final void 			setMapStatusLimits(LatLngBounds var1);
public final void 			animateMapStatus(MapStatusUpdate var1, int var2);
public float 				getZoomToBound(int var1, int var2, int var3, int var4, int var5, int var6);
public final void 			animateMapStatus(MapStatusUpdate var1);
public final void 			setMapType(int var1);//BaiduMap.MAP_TYPE_SATELLITE   none, normal, satellite(����)
public final int 			getMapType();
public j 					getmGLMapView();
public final float 			getMaxZoomLevel();
public final void 			setMaxAndMinZoomLevel(float max, float min);//���õ�ͼ����Լ���С���ż��𣬵�ͼ֧�ֵ������С����ֱ�Ϊ[4-21] V5.0.0�汾��
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
public void 				addHeatMap(HeatMap var1);//�������ͼ
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
public final void 			showMapPoi(boolean isShow);//�����Ƿ���ʾ��ͼĬ�ϱ�ע, Ĭ����ʾ
public final void 			showMapIndoorPoi(boolean var1);
public final void 			setViewPadding(int var1, int var2, int var3, int var4);
public final void 			snapshot(BaiduMap.SnapshotReadyCallback var1);
public final void 			snapshotScope(Rect var1, BaiduMap.SnapshotReadyCallback var2);
public void 				showInfoWindow(InfoWindow var1);//��ʾ InfoWindow, �ýӿڻ���������������ӵ�InfoWindow, ������µ�InfoWindow
public void 				showInfoWindow(InfoWindow var1, boolean isHideOthers);//5.4.0�汾�����ӿ� ��ʾ InfoWindow, �ýӿڿ��������Ƿ������InfoWindow֮ǰ�������������Ѿ���ӵ�InfoWindow
public void 				showInfoWindows(List<InfoWindow> var1);//5.4.0�汾�����ӿ� ��Ӷ��InfoWindow
public List<InfoWindow> 	getAllInfoWindows();
public void 				hideInfoWindow();//���ص�ͼ�ϵ�����InfoWindow
public void 				hideInfoWindow(InfoWindow var1);//5.4.0�汾�����ӿ� ����ض���InfoWindow
public void 				setPixelFormatTransparent(boolean var1);
public final void 			setOnMapStatusChangeListener(BaiduMap.OnMapStatusChangeListener var1);
public final void 			setOnMapTouchListener(BaiduMap.OnMapTouchListener var1);
public final void setOnMapClickListener(new BaiduMap.OnMapClickListener() {//����¼�
    public void onMapClick(LatLng point) {
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(point));
    }
    public boolean onMapPoiClick(MapPoi poi) {//��ͼ�� Poi(��ͼ��ע) �����¼�
        return false;
    }
});
public void 		setOnMapLoadedCallback(BaiduMap.OnMapLoadedCallback var1);
public void 		setOnMapRenderCallbadk(BaiduMap.OnMapRenderCallback var1);
public final void 	setOnMapDoubleClickListener(BaiduMap.OnMapDoubleClickListener var1);//˫��
public final void 	setOnMapLongClickListener(BaiduMap.OnMapLongClickListener var1);//����
public final void 	setOnPolylineClickListener(BaiduMap.OnPolylineClickListener var1);//���ߵ��
public final void setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {//������(��ͼ��ע)���
            @Override
            public boolean onMarkerClick(Marker marker) {
                showInfoWindow(marker.getPosition(), "������:".concat(getNoNullString(reportName)));
                return true;//����Ӧ����¼�������true�����򷵻�false
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
public void 				showSDKLayer();//��ʾSDKͼ�㣬��ʵ��ģʽ������AR������������ͨ����ģʽʱ����
public void 				changeLocationLayerOrder(boolean var1);
public void 				setCompassEnable(boolean var1);
public float[] 				getProjectionMatrix();
public float[] 				getViewMatrix();
public void 				setOverlayUnderPoi(boolean var1);//���ø�����ͼ�������Poiͼ���ͼ��˳�� Ĭ�ϸ�����ͼ����Poiͼ��֮�� ͨ�����øýӿڣ��������ø�����ͼ����Poiͼ��֮�£����Խ���������ڸ�Poi���Ƶ����⣬����Polyline�ڵ�·������ V5.4.0�汾�����ӿ�


//https://www.jianshu.com/p/fdd1ba783495
if (points.size() == 2) {//��������
    PolylineOptions polylineOptions= new PolylineOptions();//����������
    polylineOptions.width(10);//���,��λ������
    polylineOptions.color(0xAAFF0000);//����������ɫ
    polylineOptions.points(points);//���߶������꼯
    baiduMap.addOverlay(polylineOptions);
} else if (points.size() >= 3) {//���ƶ����, ����>=3���ܻ�����, ���򱨴�
    PolygonOptions polygonOptions = new PolygonOptions();
    polygonOptions.points(points);//����ζ������꼯
    polygonOptions.stroke(new Stroke(5, 0xAAFF0000));//�߿���ʽ
    polygonOptions.fillColor(0xAAFF0000);//�����ɫ
    baiduMap.addOverlay(polygonOptions);//�ڵ�ͼ������mPolygon ͼ��
}



14.�����
LatLng point = new LatLng(lat, lng);//�����

15.Overlay ������, ����
public boolean isVisible()
public void setVisible(boolean visible);//���ﶯ̬���ø�����ɼ���(�����ǿ��Ե��...)����
public int getZIndex()
public void setZIndex(int var1)
public Bundle getExtraInfo();//����
public void setExtraInfo(Bundle var1)
public void remove();//�����Ƴ����������
  ����:
      Marker
	  GroundOverlay
	  Polyline
	  Dot
	  Text
	  Circle
	  Polygon
	  Arc
	  ItemizedOverlay

16.OverlayOptions ������ѡ��
����: abstract OverlayOptions//������, ���󷽷�, ����������ʵ��
����: 1.DotOptions//Բ��
            public LatLng getCenter();//��ȡԲ��Բ������
			public int getColor();//��ȡԲ�����ɫ
			public int getRadius();//��ȡԲ��İ뾶����λ������
			public DotOptions center(LatLng var1);//����Բ���Բ������
			public DotOptions color(int var1);//����Բ�����ɫ
			public DotOptions radius(int var1);//����Բ��İ뾶����λ������, Ĭ��Ϊ 5px
			public DotOptions visible(boolean var1);//����Բ���Ƿ�ɼ�
			public boolean isVisible();//��ȡԲ���Ƿ�ɼ�
			public DotOptions zIndex(int var1);//����Բ��� zIndex
			public int getZIndex();//��ȡԲ��� zIndex
			public DotOptions extraInfo(Bundle var1);//����Բ��Ķ�����Ϣ
			public Bundle getExtraInfo();//��ȡԲ��Ķ�����Ϣ
	  2.TextOptions//����
	        public String getText()
			public LatLng getPosition();//��������
			public int getBgColor();//��ȡ������ɫ
			public int getFontColor();//��ȡ������ɫ
			public int getFontSize();//��ȡ�����С
			public Typeface getTypeface();//����
			public float getAlignX();//ˮƽ���뷽ʽ
			public float getAlignY();//��ֱ���뷽ʽ
			public float getRotate();//��ת�Ƕ�
			public TextOptions text(String var1)
			public TextOptions position(LatLng var1);//���õ�������
			public TextOptions bgColor(int var1);//������ɫ
			public TextOptions fontColor(int var1);//������ɫ��Ĭ�Ϻ�ɫ
			public TextOptions fontSize(int var1);//�����С
			public TextOptions typeface(Typeface var1);//����
			public TextOptions align(int alignX, int alignY);//���뷽ʽ��Ĭ�Ͼ��ж���
			public TextOptions rotate(float var1);//������ת�Ƕȣ���ʱ��
			public TextOptions visible(boolean var1)
			public boolean isVisible()
			public TextOptions zIndex(int var1);//�������ָ����� zIndex
			public int getZIndex();//��ȡ���ָ�����zIndex
			public TextOptions extraInfo(Bundle var1)
			public Bundle getExtraInfo()
	  3.MarkerOptions//��ע http://mapopen-pub-androidsdk.cdn.bcebos.com/map/doc/v5.4.1/index.html
	        public MarkerOptions icon(BitmapDescriptor var1);//���� Marker �������ͼ�꣬��ͬͼ���� icon �� marker ���ʹ��ͬһ�� BitmapDescriptor �����Խ�ʡ�ڴ�ռ�
		    public MarkerOptions animateType(MarkerOptions.MarkerAnimateType var1);//none,drop,grow,jump;  ��Ӷ�����Ŀǰ֧�ֵ��º���������
		    public MarkerOptions.MarkerAnimateType getAnimateType();//��ȡmarker��������
		    public MarkerOptions alpha(float var1);//���� Marker ������ͼ���͸���ȣ�ȡֵΪ[0,1]
		    public MarkerOptions scaleX(float var1);//������X��������, ����������㸽������, �Ŵ�Ĳ��ֵ��û��Ӧ
		    public MarkerOptions scaleY(float var1);//������X��������
		    public float getAlpha();//��ȡMarkerͼ��͸����
		    public BitmapDescriptor getIcon();//��ȡ Marker �������ͼ��
		    public MarkerOptions icons(ArrayList<BitmapDescriptor> var1);//���� Marker �������ͼ�꣬��ͬͼ���� icon �� marker ���ʹ��ͬһ�� BitmapDescriptor �����Խ�ʡ�ڴ�ռ�
		    public ArrayList<BitmapDescriptor> getIcons();//����Marker�Ķ���֡�б����������ʹ�С�Ե�һ֡Ϊ׼������ͼƬ��С����һ��
		    public MarkerOptions period(int var1);//���ö���֡ˢ��һ��ͼƬ��Դ��Marker�����ļ��ʱ�䣬ֵԽС����Խ��
		    public int getPeriod();//�õ�����֡ˢ��һ��ͼƬ��Դ��ֵԽС����Խ��
		    public MarkerOptions position(LatLng var1);//���� marker �������λ������
		    public LatLng getPosition();//��ȡ marker �������λ������
		    public MarkerOptions perspective(boolean var1);//�Ƿ��� marker ���������ԶСЧ����Ĭ�Ͽ���
		    public boolean isPerspective();//marker �������Ƿ�������ԶСЧ��
		    public MarkerOptions yOffset(int var1);//Marker�����Yƫ����
		    public MarkerOptions draggable(boolean var1);//marker �Ƿ�������ק
		    public boolean isFlat();//marker �Ƿ�ƽ����ͼ
		    public MarkerOptions flat(boolean var1);//���� marker���� �Ƿ�ƽ����ͼ
		    public boolean isDraggable();//�������Ƿ������ק
		    public MarkerOptions anchor(float var1, float var2);//���� marker �������ê�������Ĭ�ϣ�0.5f, 1.0f��ˮƽ���У���ֱ�¶���
		    public float getAnchorX();//��ȡ marker ������ˮƽ����ê�����
		    public float getAnchorY()//��ȡ marker �����ﴹֱ����ê�����
		    public MarkerOptions rotate(float var1);//marker ��������ת�Ƕȣ���ʱ��
		    public float getRotate();//��ȡ marker ��������ת�Ƕ�
		    public MarkerOptions title(String var1);//��ʱ
		    public String getTitle();//��ʱ
		    public MarkerOptions fixedScreenPosition(Point var1);//���� Marker ��������Ļλ�õ�
		    public MarkerOptions infoWindow(InfoWindow var1);//���� Marker �󶨵�InfoWindow(ֱ����ʾInfoWindow)
		    public MarkerOptions visible(boolean var1);//���� marker ������Ŀɼ���(��ʼ��ʱ����, ���ܶ�̬���ÿɼ���, ʹ�� overlay.setVisible();)
		    public boolean isVisible();//marker ������Ŀɼ���
		    public MarkerOptions zIndex(int var1);//���� marker ������� zIndex
		    public int getZIndex();//��ȡ marker ������� zIndex
		    public MarkerOptions extraInfo(Bundle var1);//�������� marker ������Ķ�����Ϣ����
		    public Bundle getExtraInfo();//��ȡmarker������Ķ�����Ϣ
	  4.GroundOverlayOptions//����ͼͼ��((����:�Զ��徰����ͼ, �Ѵ�ͼ�Ž�ȥhttps://blog.csdn.net/u013184970/article/details/84023652)
	        public GroundOverlayOptions image(BitmapDescriptor image);//���� Ground �������ͼƬ��Ϣ
			public BitmapDescriptor getImage();//�������ͼƬ��Ϣ
			public GroundOverlayOptions position(LatLng var1);//���� Ground ������λ����Ϣ��ʽһ���� dimensions(int, int)���ʹ��
			public LatLng getPosition();//������λ����Ϣ
			public GroundOverlayOptions dimensions(int width);//���� ground ������Ŀ�ȣ���λ���ף� �߶Ȱ�ͼƬ��߱���
			public GroundOverlayOptions dimensions(int width, int height);//���� ground ������Ŀ�Ⱥ͸߶ȣ���λ����
			public int getWidth();//������Ŀ�ȣ���λ����
			public int getHeight();//������ĸ߶ȣ���λ����
			public GroundOverlayOptions anchor(float anchorX, float anchorY);//��ʹ�� setPosition()��ʽ���ø��������λ��ʱ������ ground �������ê�������Ĭ��Ϊ 0.5f��ˮƽ�ʹ�ֱ���򶼾��ж���
			public float getAnchorX();//ˮƽ����ê�����
			public float getAnchorY()//��ֱ����ê�����
			public GroundOverlayOptions positionFromBounds(LatLngBounds bounds);//���� ground �������λ����Ϣ��ʽ�������������붫�����귶Χ
			public LatLngBounds getBounds();//�������귶Χ
			public GroundOverlayOptions transparency(float var1);//���� ground ������͸����
			public float getTransparency();//������͸����
			public GroundOverlayOptions visible(boolean var1)
			public boolean isVisible()
			public GroundOverlayOptions zIndex(int var1);//���� ground ������� zIndex
			public int getZIndex();//������� zIndex
			public GroundOverlayOptions extraInfo(Bundle var1)
			public Bundle getExtraInfo()
	  5.ArcOptions//������ http://mapopen-pub-androidsdk.cdn.bcebos.com/map/doc/v5.4.1/index.html
	        public int getColor();//��ȡ���ߵ���ɫ
			public LatLng getStartPoint();//���ߵ��������
			public LatLng getMiddlePoint();//���ߵ��е�����
			public LatLng getEndPoint();//���ߵ��յ�����
			public int getWidth();//���ߵ��߿� ��λ������
			public ArcOptions color(int var1);//���ߵ���ɫ
			public ArcOptions points(LatLng start, LatLng middle, LatLng end);//���û��ߵ���㡢�е㡢�յ�����
			public ArcOptions width(int var1);//���û��ߵ��߿�
			public ArcOptions visible(boolean var1)
			public boolean isVisible()
			public ArcOptions zIndex(int var1);//���û��ߵ� zIndex
			public int getZIndex();//���ߵ� zIndex
			public ArcOptions extraInfo(Bundle var1)
			public Bundle getExtraInfo()
	  6.CircleOptions//Բ��
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
	  7.PolygonOptions//�����
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
	  8.PolylineOptions//����

BitmapDescriptor baiduBitmap = BitmapDescriptorFactory.fromResource(R.drawable.baidumap_location_icon);//��ֻ����1������,onDestroyʱ���� bitmap ��Դ
BitmapDescriptor baiduBitmap = BitmapDescriptorFactory.fromView(view);//����view, ���кܶ���������
//����marker
MarkerOptions marker = new MarkerOptions().position(point).icon(baiduBitmap);// ����MarkerOption�������ڵ�ͼ�����Marker
baiduBitmap.recycle();//���� bitmap ��Դ


17.MapStatusUpdateFactory http://mapopen-pub-androidsdk.cdn.bcebos.com/map/doc/v5.4.1/index.html
public static MapStatusUpdate newMapStatus(MapStatus var0);//���õ�ͼ��״̬
public static MapStatusUpdate newLatLng(LatLng var0);//���õ�ͼ�����ĵ�
public static MapStatusUpdate newLatLngBounds(LatLngBounds var0);//������ʾ����Ļ�еĵ�ͼ����Χ
public static MapStatusUpdate newLatLngBounds(LatLngBounds bounds, int width, int height);//������ʾ�ڹ涨����еĵ�ͼ����Χ
public static MapStatusUpdate newLatLngZoom(LatLng latLng, float zoom);//���õ�ͼ���ĵ��Լ����ż���
public static MapStatusUpdate scrollBy(int xPixel, int yPixel);//�������ƶ���ͼ���ĵ�
public static MapStatusUpdate zoomBy(float amount);//���ݸ����������ŵ�ͼ����
public static MapStatusUpdate zoomBy(float amount, Point focus);//���ݸ��������Լ���������Ļ�������ŵ�ͼ����
public static MapStatusUpdate zoomIn();//�Ŵ��ͼ���ż���
public static MapStatusUpdate zoomOut();//��С��ͼ���ż���
public static MapStatusUpdate zoomTo(float zoom);//���õ�ͼ���ż���
public static MapStatusUpdate newLatLngBounds(LatLngBounds bounds, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom);//������ʾ��ָ�������MapView��padding�еĵ�ͼ����Χ
public static MapStatusUpdate newLatLngZoom(LatLngBounds bounds, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom);//����Padding���õ���Χ�ĺ������ż���


18.InfoWindow http://mapopen-pub-androidsdk.cdn.bcebos.com/map/doc/v5.4.1/index.html
   ע��: InfoWindow ÿ�ζ�new, ���������ص������
public InfoWindow(View view, LatLng position, int yOffset);//ͨ������� view ����һ�� InfoWindow, ��ʱֻ�����ø�view����һ��Bitmap�����ڵ�ͼ�У������¼��ɿ�����ʵ��
public InfoWindow(BitmapDescriptor bd, LatLng position, int yOffset, InfoWindow.OnInfoWindowClickListener listener);//ͨ������� BitmapDescriptor ����һ�� InfoWindow
public InfoWindow(View view, LatLng position, int yOffset, boolean isFitDensityDpi, int targetDensityDpi);//����ָ���������ܶȶԴ����view����InfoWindow, ��ʱֻ�����ø�view����һ��Bitmap�����ڵ�ͼ�У������¼��ɿ�����ʵ��
public LatLng getPosition();//��ȡλ��
public void setPosition(LatLng var1)
public BitmapDescriptor getBitmapDescriptor();//��ȡInfoWindow��BitmapDescriptor��Դ
public void setBitmapDescriptor(BitmapDescriptor var1);//����InfoWindow��BitmapDescriptor����
public View getView();
public void setView(View var1)
public int getYOffset()
public void setYOffset(int var1)
public String getTag()
public void setTag(String var1)


//�ٶ�λ�������
//com.baidu.mapapi.search.geocode.GeoCoder ��ַ������, ��Ҫ��ѡ"��������", ����û�������,�����ڵ�ַ�;�γ��֮�����ת���ķ���
GeoCoder geoCoder = GeoCoder.newInstance();
geoCoder.geocode(new GeoCodeOption().city("city").address("address"));//����,���ݵ�ַ�õ���γ��
geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(point));//�����,���ݾ�γ�ȵõ���ַ
geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
        //��ȡ���������(���ݵ�ַ��ȡpoint)
        if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
            String address = result.getAddress();
            LatLng location = result.getLocation();
            int i = result.describeContents();
            result.setAddress("address");
        }//else û�м��������
    }
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {//���߳�
        //��ȡ������������(����point��ȡ��ַ)
        if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
            String address = result.getAddress();
            ReverseGeoCodeResult.AddressComponent addressDetail = result.getAddressDetail();
            String businessCircle = result.getBusinessCircle();
            LatLng location = result.getLocation();
            int i = result.describeContents();
            List<PoiInfo> poiList = result.getPoiList();
        }//else û���ҵ��������
    }
});
geoCoder.destroy();
























