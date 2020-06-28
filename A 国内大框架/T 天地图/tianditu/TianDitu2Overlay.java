package com.liaoin.intranetnew.utils.tianditu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Handler;

import com.blankj.utilcode.util.ReflectUtils;
import com.tianditu.android.core.MapViewInternal;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.ItemizedOverlay;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MyLocationOverlay;
import com.tianditu.android.maps.Overlay;
import com.tianditu.android.maps.OverlayItem;
import com.tianditu.android.maps.Projection;
import com.tianditu.maps.VecMapView;
import com.tianditu.maps.mapCallbackNDK;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * description: 天地图 覆盖物类
 *
 * @author    : 李大发
 * date       : 2020/4/22 on 10:10
 * @version 1.0
 */
public class TianDitu2Overlay {

    ///////////////////////////////////////////////////////////////////////////
    // 2.1.分条目覆盖物类 ItemizedOverlay<Item extends OverlayItem>
    // ItemizedOverlay 是Overlay的一个基类,包含了一个 OverlayItem(标记点类) 列表。
    // 它从南到北的处理item，用于绘制、创建平移边界、为每个点绘制标记点，和维护一个焦点选中的item，
    // 同时也负责把一个屏幕点击匹配到item上去，分发焦点改变事件给注册的监听器。
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 调整Drawable的边界，使得（0，0）是这个drawable中心的中心点.
     * @param balloon 调整前的drawable对象
     * @return 调整后的drawable对象
     */
    public static Drawable boundCenter(ItemizedOverlay overlay, Drawable balloon) {
        return overlay.boundCenter(balloon);
    }
    /**
     * 调整Drawable的边界，使得（0，0）是这个drawable底部最后一行中心的一个像素.
     * @param balloon 调整前的drawable对象
     * @return 调整后的drawable对象
     */
    public static Drawable boundCenterBottom(ItemizedOverlay overlay, Drawable balloon) {
        return overlay.boundCenterBottom(balloon);
    }
//    /**
//     * 创建一个条目.子类通过该方法创建实体item.
//     * @param i 条目索引
//     * @return 创建的条目对象
//     */
//    public static OverlayItem createItem(ItemizedOverlay overlay, int i) {
//        return overlay.createItem(i);//protected 方法
//    }
    /**
     * 显示Overlay的所有条目.
     * @param shadow 是否显示标记的阴影
     */
    @Deprecated
    public static void draw(ItemizedOverlay overlay, Canvas canvas, MapView mapView, boolean shadow) {
        overlay.draw(canvas, mapView, shadow);
    }
    /**
     * 获得中心点.
     */
    public static GeoPoint getCenter(ItemizedOverlay overlay) {
        return overlay.getCenter();
    }
    /**
     * 返回获得焦点的条目.
     */
    public static OverlayItem getFocus(ItemizedOverlay overlay) {
        return overlay.getFocus();
    }
//    /**
//     * 返回指定索引对应的Item编号.
//     */
//    public static int getIndexToDraw(ItemizedOverlay overlay, int drawingOrder) {
//        return overlay.getIndexToDraw(drawingOrder);//protected
//    }
    /**
     * 返回指定索引对应的Item.
     */
    public static OverlayItem getItem(ItemizedOverlay overlay, int i) {
        return overlay.getItem(i);
    }
    /**
     * 返回最后获得焦点的条目索引.
     */
    public static int getLastFocusedIndex(ItemizedOverlay overlay) {
        return overlay.getLastFocusedIndex();
    }
    /**
     * 获得纬度跨度.
     */
    public static int getLatSpanE6(ItemizedOverlay overlay) {
        return overlay.getLatSpanE6();
    }
    /**
     * 获得经度跨度.
     */
    public static int getLonSpanE6(ItemizedOverlay overlay) {
        return overlay.getLonSpanE6();
    }
    /**
     * 获得下一个将要被选中的Item.
     * @param forwards 方向 true为向前，false为向后
     */
    public static OverlayItem nextFocus(ItemizedOverlay overlay, boolean forwards) {
        return overlay.nextFocus(forwards);
    }
//    /**
//     *  填充item数据.
//     */
//    public static void populate(ItemizedOverlay overlay) {
//        overlay.populate();//protected
//    }
    /**
     * 设置是否绘制焦点选中的item.
     */
    public static void setDrawFocusedItem(ItemizedOverlay overlay, boolean drawFocusedItem) {
        overlay.setDrawFocusedItem(drawFocusedItem);
    }
//    /**
//     * 设置最后获得焦点的条目索引.
//     */
//    public static void setLastFocusedIndex(ItemizedOverlay overlay, int lastFocusedIndex) {
//        overlay.setLastFocusedIndex(lastFocusedIndex);//protected
//    }
    /**
     * 设置获得焦点的条目.
     */
    public static void setFocus(ItemizedOverlay overlay, OverlayItem item) {
        overlay.setFocus(item);
    }
    /**
     * 设置焦点改变时的监听.
     */
    public static void setOnFocusChangeListener(ItemizedOverlay overlay,  ItemizedOverlay.OnFocusChangeListener listener) {
        overlay.setOnFocusChangeListener(listener);
    }


    ///////////////////////////////////////////////////////////////////////////
    // 2.3.当前位置覆盖物类 MyLocationOverlay
    // 位置覆盖类，绘制和获取我的位置，显示指南针
    // 为了开启这个overlay的功能，需要去调用enableMyLocation()和/或enableCompass(),
    // 或调用Activity中的Activity.onResume()方法。记住，当在后台是，
    // 要在Activity中的Activity.onPause()方法中调用相应的disableMyLocation()和/或disableCompass()关闭这个功能。
    ///////////////////////////////////////////////////////////////////////////

    //...
















    //下方一些api还没去找出处.
    /**
     * 是否能旋转??
     */
    public static void enableRotate(MapView mapView, boolean rotate, MyLocationOverlay locationOverlay) {
        mapView.enableRotate(rotate);

        mapView.isCustomTileService();//??
        mapView.isWebMercatorCRS();//?
        mapView.toggleCoordinateSys();
        mapView.getVectorDrawTile();
        mapView.getMapRotation();
        mapView.getLookDownAngle();
        mapView.setVectorDrawTile(true);
        mapView.setMapRotation(30F);
        mapView.setLookDownAngle(-30F);//俯视
        // 设置地图服务端数据源网址，
        mapView.setCustomTileService("http://t1.tianditu.cn/DataServer?");



        locationOverlay.enableMyLocation();//显示我的位置
        locationOverlay.enableCompass();//显示指南针
        GeoPoint myLocation = locationOverlay.getMyLocation();
    }
    public static void stopLocation(MyLocationOverlay locationOverlay) {
        //Compass 关闭指南针
        if (locationOverlay.isCompassEnabled()) locationOverlay.disableCompass();
        //关闭定位
        if (locationOverlay.isMyLocationEnabled()) locationOverlay.disableMyLocation();
    }

    /**
     * 移除全部覆盖物.
     */
    public static void removeAllOverlay(MapView mapView) {
        mapView.removeAllOverlay();
    }

    /**
     * 添加覆盖物
     */
    public static void addOverlay(MapView mapView, Overlay overlay) {
        mapView.addOverlay(overlay);
    }

    /**
     * 添加覆盖物
     */
    public static void addOverlay(MapView mapView, Overlay overlay, int index) {
        mapView.addOverlay(overlay, index);
    }

    public static void removeOverlay(MapView mapView, Overlay overlay) {
        mapView.removeOverlay(overlay);
    }


    ///////////////////////////////////////////////////////////////////////////
    // 覆盖物类 Overlay: http://lbs.tianditu.gov.cn/android/mobileClass/com/tianditu/android/maps/Overlay.html
    ///////////////////////////////////////////////////////////////////////////

    /**
     * http://lbs.tianditu.gov.cn/android/mobileClass/com/tianditu/android/maps/MyLocationOverlay.html
     * 位置覆盖类，绘制和获取我的位置，显示指南针
     * Overlay是一个覆盖，它绘制用户当前在地图上的位置（精准度），和/或一个嵌入的指南针。子类能覆盖方法dispatchTap()去处理对当前位置的点击。
     *
     * 为了开启这个overlay的功能，需要去调用enableMyLocation()和/或enableCompass(), 或调用Activity中的Activity.onResume
     * ()方法。记住，当在后台是，要在Activity中的Activity.onPause()方法中调用相应的disableMyLocation()和/或disableCompass()
     * 关闭这个功能。
     */
    public static MyLocationOverlay getMyLocationOverlay(Context context, MapView mapView) {
        MyLocationOverlay overlay = new MyLocationOverlay(context, mapView) {
            @Override
            public void onLocationChanged(Location location) {
                super.onLocationChanged(location);
            }
        };
//        overlay.enableMyLocation();
//        overlay.enableCompass();
//        overlay.disableMyLocation();//Activity.onPause()方法中调用
//        overlay.disableCompass();//Activity.onPause()方法中调用
        return overlay;
    }

    /**
     * 获取面积
     * @param geoPoints 坐标列表
     */
    public static double toArea(MapView mapView, List<GeoPoint> geoPoints) {
        Projection projection = mapView.getProjection();
        return projection.toArea(geoPoints);
    }

    /**
     * 清空地图缓存.(根据测试, 一般不用调用这个方法)
     */
    public static void removeCache(MapView mapView) {
        mapView.removeCache();
    }


    /**
     * 在Activity的 onStart 重新设置 g_callbackNDK
     * 因为 {@link #onDestroy(MapView)} 方法会置这个全局变量为null
     * @Override
     * protected void onStart() {
     *     TianDitu2Overlay.reSetg_callbackNDK(mapView);
     *     super.onStart();
     * }
     */
    public static void reSetg_callbackNDK(MapView mapView) {
        if (VecMapView.g_callbackNDK == null) {
            MapViewInternal mInternal = ReflectUtils.reflect(mapView).field("mInternal").get();
            VecMapView mTileView = ReflectUtils.reflect(mInternal).field("mTileView").get();
            Handler m_Handler = ReflectUtils.reflect(mTileView).field("m_Handler").get();
            VecMapView.g_callbackNDK = new mapCallbackNDK(mapView.getContext(), m_Handler, mTileView.getGLProject(), mTileView);
        }
    }

    /**
     * 在Activity/Fragment销毁的时候调用
     * version: {@link MapView#SDK_VERSION}
     * @see VecMapView#onDestroy()
     */
    public static void onDestroy(MapView mapView) {
//        mapView.onDestroy();

        MapViewInternal mInternal = ReflectUtils.reflect(mapView).field("mInternal").get();
        VecMapView mTileView = ReflectUtils.reflect(mInternal).field("mTileView").get();
        TimerTask mgetmapHeaderTask = ReflectUtils.reflect(mTileView).field("mgetmapHeaderTask").get();
        Timer mgetheaderTimer = ReflectUtils.reflect(mTileView).field("mgetheaderTimer").get();
        mgetmapHeaderTask.cancel();
        mgetheaderTimer.purge();
        mgetmapHeaderTask = null;
        mgetheaderTimer = null;
//        AndroidJni.UnInitMapEngine();//v3.0 不写这句, 否则返回首页后地图会卡顿
        mTileView.g_callbackNDK.onDestroy();
        mTileView.g_callbackNDK = null;
    }
}
