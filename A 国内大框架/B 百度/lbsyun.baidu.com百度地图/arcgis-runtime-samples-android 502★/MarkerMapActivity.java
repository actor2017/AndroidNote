package com.yys.land.activity;

//import android.annotation.SuppressLint;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.graphics.Color;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.actor.myandroidframework.utils.SPUtils;
//import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
//import com.esri.arcgisruntime.concurrent.ListenableFuture;
//import com.esri.arcgisruntime.geometry.AreaUnit;
//import com.esri.arcgisruntime.geometry.AreaUnitId;
//import com.esri.arcgisruntime.geometry.GeodeticCurveType;
//import com.esri.arcgisruntime.geometry.Geometry;
//import com.esri.arcgisruntime.geometry.GeometryEngine;
//import com.esri.arcgisruntime.geometry.LinearUnit;
//import com.esri.arcgisruntime.geometry.LinearUnitId;
//import com.esri.arcgisruntime.geometry.Point;
//import com.esri.arcgisruntime.geometry.PointCollection;
//import com.esri.arcgisruntime.geometry.Polygon;
//import com.esri.arcgisruntime.geometry.SpatialReferences;
//import com.esri.arcgisruntime.io.RequestConfiguration;
//import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
//import com.esri.arcgisruntime.loadable.LoadStatus;
//import com.esri.arcgisruntime.loadable.LoadStatusChangedEvent;
//import com.esri.arcgisruntime.loadable.LoadStatusChangedListener;
//import com.esri.arcgisruntime.location.LocationDataSource;
//import com.esri.arcgisruntime.mapping.ArcGISMap;
//import com.esri.arcgisruntime.mapping.Basemap;
//import com.esri.arcgisruntime.mapping.view.Callout;
//import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
//import com.esri.arcgisruntime.mapping.view.Graphic;
//import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
//import com.esri.arcgisruntime.mapping.view.IdentifyGraphicsOverlayResult;
//import com.esri.arcgisruntime.mapping.view.LocationDisplay;
//import com.esri.arcgisruntime.mapping.view.MapView;
//import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
//import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
//import com.esri.arcgisruntime.symbology.SimpleRenderer;
//import com.esri.arcgisruntime.util.ListenableList;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.yys.land.R;
//import com.yys.land.application.MyApplication;
//import com.yys.land.bean.GatherBean;
//import com.yys.land.bean.MarkerPoint;
//import com.yys.land.bean.PoiListBean;
//import com.yys.land.utils.DouglasPeuckerUtil;
//import com.yys.land.utils.Global;
//import com.yys.land.utils.InputTools;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.callback.StringCallback;
//
//import java.math.BigDecimal;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//import java.util.concurrent.ExecutionException;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import io.objectbox.Box;
//import okhttp3.Call;
//
///**
// * description: 落图测量
// * company    : 重庆元山元科技有限公司 http://www.ysytech.net/
// * @author    : 李大发
// * date       : 2020/10/6 on 13:54
// * @deprecated 啥地图啊??
// */
@Deprecated
public class MarkerMapActivity extends BaseActivity {
//
//    @BindView(R.id.map_view)
//    MapView         mapView;
//    @BindView(R.id.iv_compass)
//    ImageView ivCompass;
//    @BindView(R.id.iv_po)
//    ImageView iv_po;
//    @BindView(R.id.ic_recicon)
//    ImageView ic_recicon;
//    @BindView(R.id.tv_info)
//    TextView tvInfo;
//    @BindView(R.id.keepTxt)
//    TextView keepTxt;
//    @BindView(R.id.ll_record)
//    LinearLayout llRecord;
//    @BindView(R.id.ll_control)
//    LinearLayout llControl;
//
//    private LocationDisplay locationDisplay;
//    private Basemap vectorMap;
//    private Basemap imageMap;
//    private ArcGISMap arcGISMap;
//    private PictureMarkerSymbol markerSymbol;
//    private Point currentPoint;
//    private GraphicsOverlay markerOverlay;
//    private Graphic touchGraphic;
//    private Graphic lineGraphic;
//    private double areaResult;
//    private double lengthResult;
//    private GatherBean gatherBean;
//    private PictureMarkerSymbol lockMarkerSymbol;
//    int currentStatus = 0;          // 0 点击定位  1 自动定位
//    GraphicsOverlay pointGraphicOverlay;
//    GraphicsOverlay linePointOverlay;
//    boolean isFirst = true;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_marker_map/*measure*/);
//        ButterKnife.bind(this);
//        setTitle("落图测量");
//        gatherBean = (GatherBean) getIntent().getSerializableExtra("bean");
//        initMap();
//        setData();
//    }
//
//    private void setData() {
//        if (gatherBean != null) {
//            setTitle(gatherBean.getTitle());
//            String coordinate = gatherBean.getCoordinate();
//            List<MarkerPoint> markerPoints = new Gson().fromJson(coordinate, new TypeToken<List<MarkerPoint>>() {
//            }.getType());
//            System.out.println("点数：" + markerPoints.size());
//            if (markerPoints.size() > 45) {
//                List<MarkerPoint> points1 = DouglasPeuckerUtil.DouglasPeucker(markerPoints, 1.35);
//                System.out.println("优化点数：" + points1.size());
//                for (MarkerPoint markerPoint : points1) {
//                    markerOverlay.getGraphics().add(new Graphic(new Point(markerPoint.getLongitude(), markerPoint.getLatitude(), SpatialReferences.getWgs84()), markerSymbol));
//                }
//            } else {
//                for (MarkerPoint markerPoint : markerPoints) {
//                    markerOverlay.getGraphics().add(new Graphic(new Point(markerPoint.getLongitude(), markerPoint.getLatitude(), SpatialReferences.getWgs84()), markerSymbol));
//                }
//            }
//            PointCollection pointCollection = new PointCollection(SpatialReferences.getWgs84());
//            for (Graphic markerOverlayGraphic : markerOverlay.getGraphics()) {
//                Point geometry = (Point) markerOverlayGraphic.getGeometry();
//                pointCollection.add(new Point(geometry.getX(), geometry.getY()));
//            }
//            if (pointCollection.size() > 1) {
//                Polygon polygon = new Polygon(pointCollection);
//                lineGraphic.setGeometry(polygon);
//            }
//            calculate();
//        }
//    }
//
//    private void initMap() {
//        RequestConfiguration requestConfiguration = new RequestConfiguration();
//        requestConfiguration.getHeaders().put("referer", "http://map.tianditu.gov.cn/");
//        ArcGISTiledLayer vectorTiledLayer = new ArcGISTiledLayer(getResources().getString(R.string.vector_layer));
//        ArcGISTiledLayer imageTiledLayer = new ArcGISTiledLayer(getResources().getString(R.string.image_layer));
//        ArcGISTiledLayer markerTiledLayer = new ArcGISTiledLayer(getResources().getString(R.string.address_layer));
//        vectorTiledLayer.setRequestConfiguration(requestConfiguration);
//        imageTiledLayer.setRequestConfiguration(requestConfiguration);
//        markerTiledLayer.setRequestConfiguration(requestConfiguration);
//        vectorMap = new Basemap(vectorTiledLayer);
//        imageMap = new Basemap();
//        imageMap.getBaseLayers().add(imageTiledLayer);
//        imageMap.getBaseLayers().add(markerTiledLayer);
//        arcGISMap = new ArcGISMap(vectorMap);
//        mapView.setMap(arcGISMap);
//        mapView.setAttributionTextVisible(false);
//        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud6294051632,none,TRB3LNBHPB8DL50JT115");
////        BitmapDrawable makerDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.icon_marker);
//        BitmapDrawable makerDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.ic_dotred);
//        BitmapDrawable lockMarker = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.icon_marker_lock);
//        SimpleLineSymbol lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.RED, 2);
//        markerSymbol = new PictureMarkerSymbol(makerDrawable);
//        lockMarkerSymbol = new PictureMarkerSymbol(lockMarker);
//        markerSymbol.setWidth(14);
//        markerSymbol.setHeight(14);
//        markerSymbol.setOffsetY(1);
//        lockMarkerSymbol.setWidth(30);
//        lockMarkerSymbol.setHeight(30);
//        lockMarkerSymbol.setOffsetY(15);
//        locationDisplay = mapView.getLocationDisplay();
//        markerOverlay = new GraphicsOverlay();
//        GraphicsOverlay lineOverlay = new GraphicsOverlay();
//        mapView.getGraphicsOverlays().add(markerOverlay);
//        mapView.getGraphicsOverlays().add(lineOverlay);
//        lineGraphic = new Graphic(new Polygon(new PointCollection(SpatialReferences.getWgs84())), lineSymbol);
//        lineOverlay.getGraphics().add(lineGraphic);
////        locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.COMPASS_NAVIGATION);
//        pointGraphicOverlay = new GraphicsOverlay();//设置自动点
////        SimpleMarkerSymbol pointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 10);
//        SimpleRenderer pointRenderer = new SimpleRenderer(markerSymbol);
//        pointGraphicOverlay.setRenderer(pointRenderer);
//        mapView.getGraphicsOverlays().add(pointGraphicOverlay);
//        linePointOverlay = new GraphicsOverlay();//设置自动线
//        mapView.getGraphicsOverlays().add(linePointOverlay);
//        locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.COMPASS_NAVIGATION);
//        locationDisplay.startAsync();
//        locationDisplay.addLocationChangedListener(new LocationDisplay.LocationChangedListener() {
//            @Override
//            public void onLocationChanged(LocationDisplay.LocationChangedEvent locationChangedEvent) {
//                LocationDataSource.Location location = locationChangedEvent.getLocation();
//                if (null == currentPoint) {
//                    currentPoint = location.getPosition();
//                    if (currentStatus == 1) {
//                        autoRecordPoind(location.getPosition());
//                    }
//                } else {
//                    if (currentPoint != location.getPosition()) {
//                        if (currentStatus == 1) {
//                            autoRecordPoind(location.getPosition());
//                        }
//                    }
//                }
//                currentPoint = location.getPosition();
//
//                if (null != gatherBean && isFirst) {
//                    List<MarkerPoint> markerPoints = new Gson().fromJson(gatherBean.getCoordinate(), new TypeToken<List<MarkerPoint>>() {
//                    }.getType());
//                    mapView.setViewpointCenterAsync(new Point(markerPoints.get(0).getLongitude(), markerPoints.get(0).getLatitude(), SpatialReferences.getWgs84()));
//                    double mScale = mapView.getMapScale();
//                    mapView.setViewpointScaleAsync(mScale * 0.0005);
//                    isFirst = false;
//                }
//            }
//        });
//        arcGISMap.addLoadStatusChangedListener(new LoadStatusChangedListener() {
//            @Override
//            public void loadStatusChanged(LoadStatusChangedEvent loadStatusChangedEvent) {
//                LoadStatus newLoadStatus = loadStatusChangedEvent.getNewLoadStatus();
//                if (newLoadStatus == LoadStatus.LOADED) {
//                    llRecord.setVisibility(View.VISIBLE);
//                    llControl.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//        mapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mapView) {
//            @Override
//            public boolean onSingleTapConfirmed(MotionEvent e) {
//                if (currentStatus == 0) {
//                    android.graphics.Point point = new android.graphics.Point((int) e.getX(), (int) e.getY());
//                    ListenableFuture<IdentifyGraphicsOverlayResult> resultListenableFuture = mapView.identifyGraphicsOverlayAsync(markerOverlay, point, 10, false, 1);
//                    resultListenableFuture.addDoneListener(new Runnable() {
//                        @Override
//                        public void run() {
//                            IdentifyGraphicsOverlayResult identifyGraphicsOverlayResult = null;
//                            try {
//                                identifyGraphicsOverlayResult = resultListenableFuture.get();
//                                List<Graphic> graphics = identifyGraphicsOverlayResult.getGraphics();
//                                if (graphics.size() > 0) {
//                                    Graphic dragGraphic = graphics.get(0);
//                                    ListenableList<Graphic> graphicList = markerOverlay.getGraphics();
//                                    for (Graphic graphic : graphicList) {
//                                        if (graphic.getGeometry().equals(dragGraphic.getGeometry())) {
//                                            int index = graphicList.indexOf(graphic);
//                                            showCallOut(index);
//                                            break;
//                                        }
//                                    }
//                                } else {
//                                    recordPoint(mapView.screenToLocation(point));
//                                }
//                            } catch (Exception e1) {
//                                e1.printStackTrace();
//                            }
//                        }
//                    });
//                }
//                return super.onSingleTapConfirmed(e);
//            }
//
//            @Override
//            public boolean onDown(MotionEvent e) {
//                if (currentStatus == 0) {
//                    android.graphics.Point point = new android.graphics.Point((int) e.getX(), (int) e.getY());
//                    ListenableFuture<IdentifyGraphicsOverlayResult> resultListenableFuture = mapView.identifyGraphicsOverlayAsync(markerOverlay, point, 10, false, 1);
//                    resultListenableFuture.addDoneListener(new Runnable() {
//                        @Override
//                        public void run() {
//                            IdentifyGraphicsOverlayResult identifyGraphicsOverlayResult = null;
//                            try {
//                                identifyGraphicsOverlayResult = resultListenableFuture.get();
//                                List<Graphic> graphics = identifyGraphicsOverlayResult.getGraphics();
//                                if (graphics.size() > 0) {
//                                    Graphic dragGraphic = graphics.get(0);
//                                    ListenableList<Graphic> graphicList = markerOverlay.getGraphics();
//                                    for (Graphic graphic : graphicList) {
//                                        if (graphic.getGeometry().equals(dragGraphic.getGeometry())) {
//                                            touchGraphic = graphic;
//                                            break;
//                                        }
//                                    }
//                                }
//                            } catch (ExecutionException | InterruptedException e1) {
//                                e1.printStackTrace();
//                            }
//                        }
//                    });
//                }
//                return super.onDown(e);
//            }
//
//            @Override
//            public boolean onUp(MotionEvent e) {
//                if (currentStatus == 0) {
//                    if (touchGraphic != null) {
//                        if (markerOverlay.getGraphics().size() > 1) {
//                            calculate();
//                        }
//                        touchGraphic = null;
//                    }
//                }
//                return super.onUp(e);
//            }
//
//            @Override
//            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//                if (currentStatus == 1) {
//                    return super.onScroll(e1, e2, distanceX, distanceY);
//                }
//                if (touchGraphic != null && touchGraphic.getZIndex() == 0) {
//                    android.graphics.Point point = new android.graphics.Point((int) e2.getX(), (int) e2.getY());
//                    Point screenToLocation = mapView.screenToLocation(point);
//                    touchGraphic.setGeometry(screenToLocation);
//                    PointCollection pointCollection = new PointCollection(SpatialReferences.getWgs84());
//                    for (Graphic markerOverlayGraphic : markerOverlay.getGraphics()) {
//                        Point geometry = (Point) markerOverlayGraphic.getGeometry();
//                        pointCollection.add(new Point(geometry.getX(), geometry.getY()));
//                    }
//                    if (pointCollection.size() > 1) {
//                        Polygon polygon = new Polygon(pointCollection);
//                        lineGraphic.setGeometry(polygon);
//                    }
//                    return false;
//                } else {
//                    return super.onScroll(e1, e2, distanceX, distanceY);
//                }
//
//            }
//        });
//    }
//
//    private void showCallOut(int index) {
//        Graphic graphic = markerOverlay.getGraphics().get(index);
//        Point geometry = (Point) graphic.getGeometry();
//        View view = LayoutInflater.from(activity).inflate(R.layout.callout_layout, null);
//        TextView tvLon = view.findViewById(R.id.tv_lon);
//        TextView tvLat = view.findViewById(R.id.tv_lat);
//        TextView btnDelete = view.findViewById(R.id.btn_delete);
//        TextView btnCancel = view.findViewById(R.id.btn_cancel);
//        ImageView ivLock = view.findViewById(R.id.iv_lock);
//        if (graphic.getZIndex() == 1) {
//            ivLock.setSelected(true);
//        }
//        DecimalFormat decimalFormat = new DecimalFormat("#.000000");
//        tvLon.setText(decimalFormat.format(geometry.getX()));
//        tvLat.setText(decimalFormat.format(geometry.getY()));
//        Callout callout = mapView.getCallout();
//        callout.setContent(view);
//        callout.setLocation(geometry);
//        callout.show();
//        ivLock.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ivLock.isSelected()) {
//                    ivLock.setSelected(false);
//                    graphic.setSymbol(markerSymbol);
//                    graphic.setZIndex(0);
//                } else {
//                    graphic.setZIndex(1);
//                    graphic.setSymbol(lockMarkerSymbol);
//                    ivLock.setSelected(true);
//                }
//                callout.dismiss();
//            }
//        });
//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                markerOverlay.getGraphics().remove(index);
//                PointCollection pointCollection = new PointCollection(SpatialReferences.getWgs84());
//                for (Graphic markerOverlayGraphic : markerOverlay.getGraphics()) {
//                    Point geometry = (Point) markerOverlayGraphic.getGeometry();
//                    pointCollection.add(new Point(geometry.getX(), geometry.getY()));
//                }
//                if (pointCollection.size() > 0) {
//                    Polygon polygon = new Polygon(pointCollection);
//                    lineGraphic.setGeometry(polygon);
//                }
//                callout.dismiss();
//            }
//        });
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callout.dismiss();
//            }
//        });
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mapView.pause();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mapView.resume();
//        if (ivCompass.isSelected()) {
//            locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.NAVIGATION);
//        } else {
//            locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.COMPASS_NAVIGATION);
//        }
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        arcGISMap.cancelLoad();
//        mapView.dispose();
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//    }
//
//    @OnClick({R.id.iv_po, R.id.iv_compass, R.id.iv_location, R.id.iv_change, R.id.ll_record,
//            R.id.iv_add, R.id.iv_surplus, R.id.iv_clear, R.id.iv_save})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.iv_po:
//                markerOverlay.getGraphics().clear();
//                lineGraphic.setGeometry(null);
//                tvInfo.setVisibility(View.GONE);
//                points.clear();
//                linePointOverlay.getGraphics().clear();
//                pointGraphicOverlay.getGraphics().clear();
//                if (iv_po.isSelected()) {
//                    iv_po.setSelected(false);
//                    currentStatus = 0;
//                    keepTxt.setText("记录位置");
//                    ic_recicon.setImageResource(R.drawable.icon_record);
//                } else {
//                    keepTxt.setText("走动勘界");
//                    iv_po.setSelected(true);
//                    currentStatus = 1;
//                    autoRecordPoind(currentPoint);
//                    ic_recicon.setImageResource(R.drawable.ic_autoloc);
//                }
//                break;
//            case R.id.iv_compass:
//                if (ivCompass.isSelected()) {
//                    ivCompass.setSelected(false);
//                    locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.COMPASS_NAVIGATION);
//                } else {
//                    ivCompass.setSelected(true);
//                    locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.NAVIGATION);
//                }
//                break;
//            case R.id.iv_location:
//                locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.RECENTER);
//
////                mapView.setViewpointCenterAsync(new Point(106.583152, 29.540572, SpatialReferences.getWgs84()));
//                break;
//            case R.id.iv_change://切换图层
//                if (arcGISMap.getBasemap() == vectorMap) {
//                    arcGISMap.setBasemap(imageMap);
//                } else {
//                    arcGISMap.setBasemap(vectorMap);
//                }
//                break;
//            case R.id.ll_record:
//                if (currentStatus == 0) {
//                    recordPoint(currentPoint);
//                    keepTxt.setTextColor(Color.RED);
//                } else {
//                    toast("当前为自动记录模式！请切换为手动记录");
//                }
//                break;
//            case R.id.iv_add://缩放
//                try {
//                    double mapScale1 = mapView.getMapScale();
//                    mapView.setViewpointScaleAsync(mapScale1 - 1000);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    toast("正在获取数据，可以退出稍候重试！");
//                }
//                break;
//            case R.id.iv_surplus://缩放
//                try {
//                    double mapScale2 = mapView.getMapScale();
//                    mapView.setViewpointScaleAsync(mapScale2 + 1000);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    toast("正在获取数据，可以退出稍候重试！");
//                }
//                break;
//            case R.id.iv_clear:
//                new AlertDialog.Builder(this)
//                        .setMessage("确定要清除所有信息吗？")
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                markerOverlay.getGraphics().clear();
//                                tvInfo.setVisibility(View.GONE);
//                                lineGraphic.setGeometry(null);
//                                linePointOverlay.getGraphics().clear();
//                                pointGraphicOverlay.getGraphics().clear();
//                                points.clear();
//                                dialog.dismiss();
//                            }
//                        })
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }).show();
//                break;
//            case R.id.iv_save:
//                if (currentStatus == 0) {
//                    if (markerOverlay.getGraphics().size() < 2) {
//                        toast("至少两个点以上才能保存");
//                        return;
//                    }
//                } else {
//                    if (pointGraphicOverlay.getGraphics().size() < 2) {
//                        toast("至少两个点以上才能保存");
//                        return;
//                    }
//                }
//                EditText editText = new EditText(this);
//                if (gatherBean != null) {
//                    editText.setText(gatherBean.getTitle());
//                }
//                Box<GatherBean> gatherBeanBox = MyApplication.instance.boxStore.boxFor(GatherBean.class);
//                new AlertDialog.Builder(this).setTitle("请输入标题")
//                        .setIcon(R.drawable.ic_launcher)
//                        .setView(editText)
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                GatherBean bean = new GatherBean();
//                                String title = editText.getText().toString().trim();
//                                if (gatherBean != null) {
//                                    bean.setId(gatherBean.getId());
//                                }
//                                if (TextUtils.isEmpty(title)) {
//                                    toast("请输入标题");
//                                    return;
//                                }
//                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
//                                List<MarkerPoint> pointList = new ArrayList<>();
//                                if (currentStatus == 0) {
//                                    for (Graphic markerOverlayGraphic : markerOverlay.getGraphics()) {
//                                        Point geometry = (Point) markerOverlayGraphic.getGeometry();
//                                        pointList.add(new MarkerPoint(geometry.getX(), geometry.getY()));
//                                    }
//                                } else {
//                                    for (Graphic markerOverlayGraphic : pointGraphicOverlay.getGraphics()) {
//                                        Point geometry = (Point) markerOverlayGraphic.getGeometry();
//                                        pointList.add(new MarkerPoint(geometry.getX(), geometry.getY()));
//                                    }
//                                }
//                                String coordinate = new Gson().toJson(pointList);
//                                bean.setCoordinate(coordinate);
//                                bean.setArea(String.valueOf(areaResult));
//                                bean.setLength(String.valueOf(lengthResult));
//                                bean.setTime(df.format(new Date()));
//                                bean.setTitle(title);
//                                gatherBeanBox.put(bean);
//                                InputTools.HideKeyboard(tvInfo);
//                                if (Global.isLogin() && !Global.isLoginType1()) {
//                                    sendData(title, coordinate, lengthResult + "", areaResult + "");
//                                } else {
//                                    sendData2(title, coordinate, lengthResult + "", areaResult + "");
//                                }
////                                toast("保存成功");
//                            }
//                        }).setNegativeButton("取消", null).show();
//                break;
//            default:
//                break;
//        }
//    }
//
//    public void sendData(String title, String projectextent, String zc, String mj) {
//        showLoadingDialog();
//        OkHttpUtils
//                .post()
//                .url(Global.saveDywZbxx)
//                .addParams("name", title)
//                .addParams("projectextent", projectextent)
//                .addParams("zc", zc)
//                .addParams("mj", mj)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        e.printStackTrace();
//                        dismissLoadingDialog();
//                        toast("保存失败！");
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        dismissLoadingDialog();
//                        try {
//                            org.json.JSONObject object = new org.json.JSONObject(response);
//                            if (object.optString("status").equals("200")) {
//                                toast("保存成功！");
//                                finish();
//                            } else {
//                                toast(object.optString("msg"));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            toast("保存失败！");
//                        }
//                    }
//                });
//    }
//
//    public void sendData2(String title, String projectextent, String zc, String mj) {
//        showLoadingDialog();
//        OkHttpUtils
//                .post()
//                .url(Global.addMap)
//                .addParams("USERID2", SPUtils.getStringNoNull(Global.USERID))
//                .addParams("MC2", title)
//                .addParams("DTXX2", projectextent)
//                .addParams("ZC2", zc)
//                .addParams("MJ2", mj)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        e.printStackTrace();
//                        toast("保存失败！");
//                        dismissLoadingDialog();
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        dismissLoadingDialog();
//                        try {
//                            org.json.JSONObject object = new org.json.JSONObject(response);
//                            if (object.optBoolean("success")) {
//                                toast("保存成功！");
//                                finish();
//                            } else {
//                                toast(object.optString("msg"));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            toast("保存失败！");
//                        }
//                    }
//                });
//    }
//
//    List<Point> points = new ArrayList<>();
//    long lastRecTime = 0;
//
//    public void autoRecordPoind(Point point) {
//        try {
//            long curRecTime = System.currentTimeMillis();
//            if ((curRecTime - lastRecTime) >= 5 * 1000) {
//                points.add(point);
//                Graphic pointGraphic = new Graphic(point);
//                pointGraphicOverlay.getGraphics().add(pointGraphic);
//                if (points.size() > 1) {
//                    PointCollection pointCollection = new PointCollection(SpatialReferences.getWgs84());
//                    pointCollection.addAll(points);
//                    Polygon polygon = new Polygon(pointCollection);
//                    lineGraphic.setGeometry(polygon);
//                    drawArcgisLine(pointCollection);
//                    autoCalculate();
//                }
//                lastRecTime = curRecTime;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void drawArcgisLine(PointCollection mPoints) {
//        com.esri.arcgisruntime.geometry.Polyline mPolyline = new com.esri.arcgisruntime.geometry.Polyline(mPoints);//点画线，mPoints为坐标集合
//        SimpleLineSymbol lineSym = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.RED, 2);
//        Graphic graphicTrail = new Graphic(mPolyline, lineSym);
//        linePointOverlay.getGraphics().add(graphicTrail);
//    }
//
//    private void recordPoint(Point point) {
//        try {
//            Graphic graphic = new Graphic(point, markerSymbol);
//            markerOverlay.getGraphics().add(graphic);
//            PointCollection pointCollection = new PointCollection(SpatialReferences.getWgs84());
//            for (Graphic markerOverlayGraphic : markerOverlay.getGraphics()) {
//                Point geometry = (Point) markerOverlayGraphic.getGeometry();
//                pointCollection.add(new Point(geometry.getX(), geometry.getY()));
//            }
//            if (pointCollection.size() > 1) {
//                Polygon polygon = new Polygon(pointCollection);
//                lineGraphic.setGeometry(polygon);
//                calculate();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void calculate() {
//        tvInfo.setVisibility(View.VISIBLE);
//        Geometry geometry = lineGraphic.getGeometry();
//        Polygon project = (Polygon) GeometryEngine.project(geometry, SpatialReferences.getWgs84());
//        double area = GeometryEngine.areaGeodetic(project, new AreaUnit(AreaUnitId.SQUARE_METERS), GeodeticCurveType.NORMAL_SECTION);
//        double length = GeometryEngine.lengthGeodetic(project, new LinearUnit(LinearUnitId.METERS), GeodeticCurveType.NORMAL_SECTION);
//        ListenableList<Graphic> graphics = markerOverlay.getGraphics();
//        if (graphics.size() == 2) {
//            length = length / 2;
//        }
//        areaResult = new BigDecimal(area).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
//        lengthResult = new BigDecimal(length).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
//        double areaMu = new BigDecimal(area * 0.0015).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
//        tvInfo.setText("面积：" + areaResult + "㎡/" + areaMu + "亩" + "\n周长：" + lengthResult + "m");
//    }
//
//    private void autoCalculate() {
//        tvInfo.setVisibility(View.VISIBLE);
//        Geometry geometry = lineGraphic.getGeometry();
//        Polygon project = (Polygon) GeometryEngine.project(geometry, SpatialReferences.getWgs84());
//        double area = GeometryEngine.areaGeodetic(project, new AreaUnit(AreaUnitId.SQUARE_METERS), GeodeticCurveType.NORMAL_SECTION);
//        double length = GeometryEngine.lengthGeodetic(project, new LinearUnit(LinearUnitId.METERS), GeodeticCurveType.NORMAL_SECTION);
//        ListenableList<Graphic> graphics = pointGraphicOverlay.getGraphics();
//        if (graphics.size() == 2) {
//            length = length / 2;
//        }
//        areaResult = new BigDecimal(area).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
//        lengthResult = new BigDecimal(length).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
//        double areaMu = new BigDecimal(area * 0.0015).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
//        tvInfo.setText("面积：" + areaResult + "㎡/" + areaMu + "亩" + "\n周长：" + lengthResult + "m");
//    }
}
