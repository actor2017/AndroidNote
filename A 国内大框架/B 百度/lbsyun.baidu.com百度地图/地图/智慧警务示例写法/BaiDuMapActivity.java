package com.kuchuan.wisdompolice.activity;

import android.content.Intent;
import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.jaeger.library.StatusBarUtil;
import com.kuchuan.wisdompolice.R;
import com.kuchuan.wisdompolice.global.Global;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 百度地图
 */
public class BaiDuMapActivity extends BaseActivity {

    @BindView(R.id.mv_mapView)
    MapView mvMapView;
    private BaiduMap baiduMap;
	private BitmapDescriptor baiduBitmap;//onDestroy时回收 bitmap 资源

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_du_map);
        ButterKnife.bind(this);

        StatusBarUtil.setTranslucentForImageView(this, 30, null);//设置透明度
        Intent intent = getIntent();
        double lat = intent.getDoubleExtra(Global.LAT, 0);
        double lng = intent.getDoubleExtra(Global.LNG, 0);
        if (lat == 0 || lng == 0) {//龙里县新公安局的坐标
            lat = 26.463341;
            lng = 106.990324;
        }
        baiduMap = mvMapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//设置地图样式为普通,有三种
        baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15));
		baiduBitmap = BitmapDescriptorFactory.fromResource(R.drawable.baidumap_location_icon);
        LatLng point = new LatLng(lat, lng);//坐标点
        // 构建MarkerOption，用于在地图上添加Marker
        OverlayOptions marker = new MarkerOptions().position(point).icon(baiduBitmap);
        baiduMap.addOverlay(marker);//在地图上添加Marker,并显示.如果不添加,不会报错,但是不会有Marker显示
        baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(point));
    }

	@Override
    protected void onResume() {
        super.onResume();
        mvMapView.onResume();//MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
    }

    @Override
    protected void onPause() {
        super.onPause();
        mvMapView.onPause();//MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvMapView.onDestroy();//MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        baiduBitmap.recycle();//回收 bitmap 资源
    }
}
