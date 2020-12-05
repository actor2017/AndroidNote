package com.yys.land.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.IdentifyGraphicsOverlayResult;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.util.ListenableList;
import com.yys.land.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;

/**
 * description: 选择位置
 * company    : 重庆元山元科技有限公司 http://www.ysytech.net/
 * @author    : 李大发
 * date       : 2020/10/6 on 13:55
 * @deprecated 啥地图啊??
 */
@Deprecated
public class ChoiceActivity extends BaseActivity implements OnClickListener {

    private Basemap vectorMap;
    private Basemap imageMap;
    private ArcGISMap arcGISMap;
    private MapView mapView;
    private LocationDisplay locationDisplay;
    private GraphicsOverlay markerOverlay;
    private PictureMarkerSymbol markerSymbol;
    private Graphic touchGraphic;
    private TextView tvAddress;
    private String address;
    private ImageView ivCompass;
    private String destination;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        setTitle("选择位置");
        destination = getIntent().getStringExtra("address");
        mapView = findViewById(R.id.map_view);
        ImageView ivLocation = findViewById(R.id.iv_location);
        ImageView ivChange = findViewById(R.id.iv_change);
        ImageView ivAdd = findViewById(R.id.iv_add);
        ImageView ivSurplus = findViewById(R.id.iv_surplus);
        Button btnConfirm = findViewById(R.id.btn_confirm);
        tvAddress = findViewById(R.id.tv_address);
        ivCompass = findViewById(R.id.iv_compass);
        ivLocation.setOnClickListener(this);
        ivChange.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        ivSurplus.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        ivCompass.setOnClickListener(this);
        ArcGISTiledLayer vectorTiledLayer = new ArcGISTiledLayer(getResources().getString(R.string.vector_layer));
        ArcGISTiledLayer imageTiledLayer = new ArcGISTiledLayer(getResources().getString(R.string.image_layer));
        ArcGISTiledLayer markerTiledLayer = new ArcGISTiledLayer(getResources().getString(R.string.address_layer));
        vectorMap = new Basemap(vectorTiledLayer);
        imageMap = new Basemap();
        imageMap.getBaseLayers().add(imageTiledLayer);
        imageMap.getBaseLayers().add(markerTiledLayer);
        arcGISMap = new ArcGISMap(vectorMap);
        mapView.setMap(arcGISMap);
        mapView.setAttributionTextVisible(false);
        markerOverlay = new GraphicsOverlay();
        mapView.getGraphicsOverlays().add(markerOverlay);
        BitmapDrawable pinStarBlueDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.icon_marker);
        markerSymbol = new PictureMarkerSymbol(pinStarBlueDrawable);
        markerSymbol.setWidth(30);
        markerSymbol.setHeight(30);
        markerSymbol.setOffsetY(15);
        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud6294051632,none,TRB3LNBHPB8DL50JT115");
        if (!TextUtils.isEmpty(destination)) {
            return;
        }
        mapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mapView) {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                android.graphics.Point point = new android.graphics.Point((int) e.getX(), (int) e.getY());
                Point point1 = mapView.screenToLocation(point);
                if (markerOverlay.getGraphics().size() == 1) {
                    markerOverlay.getGraphics().set(0, new Graphic(point1, markerSymbol));
                    geocode(point1.getX(), point1.getY());
                    return false;
                } else if (markerOverlay.getGraphics().size() == 0) {
                    markerOverlay.getGraphics().add(new Graphic(point1, markerSymbol));
                    geocode(point1.getX(), point1.getY());
                    return false;
                }
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onDown(MotionEvent e) {
                android.graphics.Point point = new android.graphics.Point((int) e.getX(), (int) e.getY());
                ListenableFuture<IdentifyGraphicsOverlayResult> resultListenableFuture = mapView.identifyGraphicsOverlayAsync(markerOverlay, point, 10, false, 1);
                resultListenableFuture.addDoneListener(new Runnable() {
                    @Override
                    public void run() {
                        IdentifyGraphicsOverlayResult identifyGraphicsOverlayResult = null;
                        try {
                            identifyGraphicsOverlayResult = resultListenableFuture.get();
                            List<Graphic> graphics = identifyGraphicsOverlayResult.getGraphics();
                            if (graphics.size() > 0) {
                                Graphic dragGraphic = graphics.get(0);
                                ListenableList<Graphic> graphicList = markerOverlay.getGraphics();
                                for (Graphic graphic : graphicList) {
                                    if (graphic.getGeometry().equals(dragGraphic.getGeometry())) {
                                        touchGraphic = graphic;
                                        break;
                                    }
                                }
                            }
                        } catch (ExecutionException e1) {
                            e1.printStackTrace();
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                return super.onDown(e);
            }

            @Override
            public boolean onUp(MotionEvent e) {
                if (touchGraphic != null) {
                    Point geometry = (Point) touchGraphic.getGeometry();
                    geocode(geometry.getX(), geometry.getY());
                    touchGraphic = null;
                }
                return super.onUp(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (touchGraphic != null) {
                    android.graphics.Point point = new android.graphics.Point((int) e2.getX(), (int) e2.getY());
                    Point screenToLocation = mapView.screenToLocation(point);
                    touchGraphic.setGeometry(screenToLocation);
                    return false;
                } else {
                    return super.onScroll(e1, e2, distanceX, distanceY);
                }
            }
        });
        if (!TextUtils.isEmpty(destination)) {
            reverseGeocode(destination);
            return;
        }
        locationDisplay = mapView.getLocationDisplay();
        locationDisplay.setShowLocation(false);
        locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.COMPASS_NAVIGATION);
        locationDisplay.startAsync();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_compass:
                if (ivCompass.isSelected()) {
                    ivCompass.setSelected(false);
                    locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.COMPASS_NAVIGATION);
                } else {
                    ivCompass.setSelected(true);
                    locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.NAVIGATION);
                }
                break;
            case R.id.iv_location:
                locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.RECENTER);
                break;
            case R.id.iv_change:
                if (arcGISMap.getBasemap() == vectorMap) {
                    arcGISMap.setBasemap(imageMap);
                } else {
                    arcGISMap.setBasemap(vectorMap);
                }
                break;
            case R.id.iv_add:
                double mapScale1 = mapView.getMapScale();
                mapView.setViewpointScaleAsync(mapScale1 - 1000);
                break;
            case R.id.iv_surplus:
                double mapScale2 = mapView.getMapScale();
                mapView.setViewpointScaleAsync(mapScale2 + 1000);
                break;
            case R.id.btn_confirm:
                if (address != null) {
                    Intent intent = new Intent();
                    intent.putExtra("fanhuide", address);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    toast("请选择地点");
                }
                break;
            default:
                break;
        }
    }

    private void reverseGeocode(String address) {
        String url = "http://api.tianditu.gov.cn/geocoder?ds={\"keyWord\":\"" + address + "\"}&tk=f99ef6f9a96af41ca391b597a11cd599";
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void geocode(double lon, double lat) {
        String url = "http://api.tianditu.gov.cn/geocoder?postStr={'lon':" + lon + ",'lat':" + lat + ",'ver':1}&type=geocode&tk=f99ef6f9a96af41ca391b597a11cd599";
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject result = jsonObject.getJSONObject("result");
                    JSONObject location = result.getJSONObject("location");
                    address = result.getString("formatted_address");
                    double longitude = location.getDouble("lon");
                    double latitude = location.getDouble("lat");
                    DecimalFormat decimalFormat = new DecimalFormat("#.000000");
                    String lonStr = decimalFormat.format(longitude);
                    String latStr = decimalFormat.format(latitude);
                    tvAddress.setText(address + "\nlon:" + lonStr + "," + "lat:" + latStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.dispose();
    }
}
