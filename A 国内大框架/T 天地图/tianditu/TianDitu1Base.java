package com.liaoin.intranetnew.utils.tianditu;

import android.graphics.Point;
import android.os.Message;
import android.view.View;

import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapController;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.Overlay;
import com.tianditu.android.maps.Projection;
import com.tianditu.android.maps.TMapLayerManager;
import com.tianditu.android.maps.TOfflineMapInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 天地图 基础类
 *      官网: http://lbs.tianditu.gov.cn/home.html
 *      入门指导: http://lbs.tianditu.gov.cn/android/guide.html
 *      参考类(开发文档): http://lbs.tianditu.gov.cn/android/class.html
 *      代码示例: http://lbs.tianditu.gov.cn/android/examples.html
 *      相关下载: http://lbs.tianditu.gov.cn/android/download.html
 *      离线地图下载: http://lbs.tianditu.gov.cn/android/offlinemapdownload.html
 *
 * @author    : 李大发
 * date       : 2020/4/2 on 17:58
 *
 * @version 1.0
 */
public class TianDitu1Base {

    ///////////////////////////////////////////////////////////////////////////
    // 1.1.地理坐标类
    ///////////////////////////////////////////////////////////////////////////
    public static void geoPoint() {
        float lat = 0f;
        float lon = 0f;
        GeoPoint geoPoint = new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
        boolean equals = geoPoint.equals(geoPoint);//判断两个点坐标是否相同
        int latitudeE6 = geoPoint.getLatitudeE6();//获得坐标的纬度，单位微度.
        int longitudeE6 = geoPoint.getLongitudeE6();// 获得坐标的经度，单位微度.
        String string = geoPoint.toString();//"经度,纬度" => "lng,lat"
    }


    ///////////////////////////////////////////////////////////////////////////
    // 1.2.地图视图类 MapView
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 是否有瓦片覆盖在地图的中心.
     */
    public static boolean canCoverCenter(MapView mapView) {
        return mapView.canCoverCenter();
    }
    /**
     * 显示缩放控件，可以选择是否请求获得焦点以便按钮操作.
     */
    public static void displayZoomControls(MapView mapView, boolean takeFocus) {
        mapView.displayZoomControls(takeFocus);
    }
    /**
     *  获得地图缓冲区路径.
     */
    public static String getCachePath(MapView mapView) {
        return mapView.getCachePath();
    }
    /**
     * 返回地图的MapController，这个对象可用于控制和驱动平移和缩放。
     */
    public static MapController getController(MapView mapView) {
        return mapView.getController();
    }
    /**
     * 获得双击放大是否可以
     */
    public static boolean getDoubleEnable(MapView mapView) {
        return mapView.getDoubleEnable();
    }
    /**
     * 当前纬线的跨度.
     */
    public static int getLatitudeSpan(MapView mapView) {
        return mapView.getLatitudeSpan();
    }
    /**
     * 当前经线的跨度.
     */
    public static int getLongitudeSpan(MapView mapView) {
        return mapView.getLongitudeSpan();
    }
    /**
     * 得到屏幕中心经纬度.
     */
    public static GeoPoint getMapCenter(MapView mapView) {
        return mapView.getMapCenter();
    }
    /**
     * 获得当前地图类型
     * @see #mapType()
     */
    public static int getMapType(MapView mapView) {
        return mapView.getMapType();
    }
    /**
     * 获得当前地图支持的最大比例尺 获得当前地图支持的最大比例尺，不同地图类型，支持的最大比例尺可能不同
     */
    public static int getMaxZoomLevel(MapView mapView) {
        return mapView.getMaxZoomLevel();
    }
    /**
     * 获得当前地图支持的最小比例尺 获得当前地图支持的最小比例尺，不同地图类型，支持的最小比例尺可能不同
     */
    public static int getMinZoomLevel(MapView mapView) {
        return mapView.getMinZoomLevel();
    }
    /**
     * 获得地图上的Overlay对象 获得地图上加载的Overlay对象列表
     */
    @Deprecated
    public static List<Overlay> getOverlays(MapView mapView) {
        return mapView.getOverlays();
    }
    /**
     * 获取屏幕像素坐标和经纬度对之间的转换接口
     */
    public static Projection getProjection(MapView mapView) {
        return mapView.getProjection();
    }
    /**
     * 已过时, 获取缩放控制器?
     */
    @Deprecated
    public static View getZoomControls(MapView mapView) {
        return mapView.getZoomControls();
    }
    /**
     * 当前地图的缩放级别.
     */
    public static int getZoomLevel(MapView mapView) {
        return mapView.getZoomLevel();
    }
    /**
     * 是否显示地名, 建议使用TMapLayerManager.setLayersShow()
     * @see #setLayersShow(TMapLayerManager, String[])
     */
    public static boolean isPlaceNameShow(MapView mapView) {
        return mapView.isPlaceNameShow();
    }
    /**
     * 是否显示影像地图 当前是否显示的为影像地图,建议使用 {@link #getMapType(MapView)}
     */
    public static boolean isSatellite(MapView mapView) {
        return mapView.isSatellite();
    }
    /**
     * 是否为街景(暂不支持)
     */
    @Deprecated
    public static boolean isStreetView(MapView mapView) {
        return mapView.isStreetView();
    }
    /**
     * 是否显示交通图(暂不支持)
     */
    @Deprecated
    public static boolean isTraffic(MapView mapView) {
        return mapView.isTraffic();
    }
    /**
     * 预加载 。
     */
    public static void preLoad(MapView mapView) {
        mapView.preLoad();
    }
    /**
     * 设置是否启用内置的缩放控件.
     */
    public static void setBuiltInZoomControls(MapView mapView, boolean on) {
        mapView.setBuiltInZoomControls(on);
    }
    /**
     * 设置地图缓冲区路径.
     */
    public static boolean setCachePath(MapView mapView, String newPath) {
        return mapView.setCachePath(newPath);
    }
    /**
     * 是否让双击放大可用(默认可用).
     */
    public static void setDoubleTapEnable(MapView mapView, boolean isEnable) {
        mapView.setDoubleTapEnable(isEnable);
    }
    /**
     * 设置在缩放动画过程中是否绘制overlay，默认为不绘制.
     */
    public static void setDrawOverlayWhenZooming(MapView mapView, boolean bDraw) {
        mapView.setDrawOverlayWhenZooming(bDraw);
    }
    /**
     * 设置Logo显示位置.
     * @see MapView#LOGO_NONE
     * @see MapView#LOGO_LEFT_TOP
     * @see MapView#LOGO_LEFT_BOTTOM
     * @see MapView#LOGO_RIGHT_TOP
     * @see MapView#LOGO_RIGHT_BOTTOM
     */
    public static void setLogoPos(MapView mapView, int pos) {
        mapView.setLogoPos(pos);
    }
    /**
     * 设置地图类型
     * @see #mapType()
     *
     * @param type 地图类型值
     */
    public static void setMapType(MapView mapView, int type) {
        mapView.setMapType(type);
    }
    /**
     * 设置离线地图数据信息，用于地图显示加载 离线地图设置之后会在程序显示时默认加载
     */
    @Deprecated
    public static void setOfflineMaps(MapView mapView, ArrayList<TOfflineMapInfo> maps) {
//        mapView.setOfflineMaps(maps);
    }
    /**
     * 设置是否显示地名.
     */
    public static void setPlaceName(MapView mapView, boolean on) {
        mapView.setPlaceName(on);
    }
    /**
     * 还未实现
     */
    @Deprecated
    public static void setReticleDrawMode(MapView mapView, MapView.ReticleDrawMode mode) {
        mapView.setReticleDrawMode(mode);
    }
    /**
     * 显示影像地图.
     */
    public static void setSatellite(MapView mapView, boolean on) {
        mapView.setSatellite(on);
    }
    /**
     * 设置比例尺变化的监听 当比例尺发生变化后，就调用设置的接口
     */
    public static void setScaleChangeListener(MapView mapView, MapView.OnScaleChangeListener listener) {
        mapView.setScaleChangeListener(listener);
    }
    /**
     * 设置是否显示街景(暂不支持)
     */
    @Deprecated
    public static void setStreetView(MapView mapView, boolean on) {
        mapView.setStreetView(on);
    }
    /**
     * 设置是否显示交通(暂不支持)
     */
    @Deprecated
    public static void setTraffic(MapView mapView, boolean on) {
        mapView.setTraffic(on);
    }


    ///////////////////////////////////////////////////////////////////////////
    // 1.3.布局类 MapView.LayoutParams
    // 每个子视图和MapView关联的布局信息。子视图要么相对于MapView(MODE_VIEW)放置，
    // 要么相对于MapView(MODE_MAP)正在显示的地图放置。
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 构造一个View的布局，并相对地图显示，即随地图移动而移动
     * @see MapView.LayoutParams#MODE_MAP 0 加的子视图是相对于地图的
     * @see MapView.LayoutParams#MODE_VIEW 1 加的子视图是相对于视图的
     *
     * @param point View显示的坐标点
     * @param align View的对齐方式:
     * @see MapView.LayoutParams#alignment          默认51, 居左居上
     * @see MapView.LayoutParams#LEFT               3 居左
     * @see MapView.LayoutParams#RIGHT              5 居右
     * @see MapView.LayoutParams#TOP                48 居上
     * @see MapView.LayoutParams#BOTTOM             80 居下
     * @see MapView.LayoutParams#CENTER_HORIZONTAL  1 水平居中
     * @see MapView.LayoutParams#CENTER_VERTICAL    16 垂直居中
     * @see MapView.LayoutParams#CENTER             17 居中
     * @see MapView.LayoutParams#TOP_LEFT           51
     * @see MapView.LayoutParams#BOTTOM_CENTER      81 底边居中
     */
    public static MapView.LayoutParams getLayoutParams(int width, int height, GeoPoint point, int align) {
        return new MapView.LayoutParams(width, height, point, align);
    }
    /**
     * 构造一个View的布局，并相对地图显示，即随地图移动而移动
     * @param x0 View相对point的x偏移量
     * @param y0 View相对point的y偏移量
     */
    public static MapView.LayoutParams getLayoutParams(int width, int height, GeoPoint point, int x0, int y0, int align) {
        return new MapView.LayoutParams(width, height, point, x0, y0, align);
    }
    /**
     * 构造一个View的布局，并相对父View显示，不随地图的移动而改变位置
     */
    public static MapView.LayoutParams getLayoutParams(int width, int height, int x0, int y0, int align) {
        return new MapView.LayoutParams(width, height, x0, y0, align);
    }


    ///////////////////////////////////////////////////////////////////////////
    // 1.4.地图类型类 MapView.TMapType
    // 地图类型类，定义了天地图支持的所有地图类型
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 目前支持矢量、影像和地形三种
     * @see com.tianditu.android.maps.MapView.TMapType#MAP_TYPE_UNSUPPOTED 不支持的地图类型
     * @see com.tianditu.android.maps.MapView.TMapType#MAP_TYPE_IMG 影像地图
     * @see com.tianditu.android.maps.MapView.TMapType#MAP_TYPE_VEC 矢量地图
     * @see com.tianditu.android.maps.MapView.TMapType#MAP_TYPE_TERRAIN 地形地图
     */
    public void mapType() {
    }


    ///////////////////////////////////////////////////////////////////////////
    // 1.6.地图控制类 MapController
    // 地图控制类，所有的地图操作均通过此类，需要通过MapView.getMapController()
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 对已给定的GeoPoint，开始动画显示地图.
     */
    public static void animateTo(MapView mapView, GeoPoint point) {
        getController(mapView).animateTo(point);
    }
    /**
     * 对已给定的GeoPoint，开始动画显示地图
     * @param message 如果动画自然结束，则分发给定的消息。如果动画中途被放弃，则不分发给定的消息
     */
    public static void animateTo(MapView mapView, GeoPoint point, Message message) {
        getController(mapView).animateTo(point, message);
    }
    /**
     * 设置中心点.
     */
    public static void setCenter(MapView mapView, GeoPoint point) {
        getController(mapView).setCenter(point);
    }
    /**
     * 设置比例尺.
     * @param zoomLevel 比例尺级别, 常用的有12~15
     * @return 当前比例尺
     */
    public static int setZoom(MapView mapView, int zoomLevel) {
        return getController(mapView).setZoom(zoomLevel);
    }
    /**
     * 停止平移.
     * @param jumpToFinish true则直接跳转到目的地，false停止在当前位置。
     */
    public static void stopAnimation(MapView mapView, boolean jumpToFinish) {
        getController(mapView).stopAnimation(jumpToFinish);
    }
    /**
     * 重新设置平移状态，使地图静止.
     */
    public static void stopPanning(MapView mapView) {
        getController(mapView).stopPanning();
    }
    /**
     * 放大一级.
     * @return true则成功放大，false则没进行放大操作。
     */
    public static boolean zoomIn(MapView mapView) {
        return getController(mapView).zoomIn();
    }
    /**
     * 以给定的点为中心放大一级.
     * @param xPixel 给定的x坐标
     * @param yPixel 给定的y坐标
     * @return true则成功放大，false则没进行放大操作。
     */
    public static boolean zoomInFixing(MapView mapView, int xPixel, int yPixel) {
        return getController(mapView).zoomInFixing(xPixel, yPixel);
    }
    /**
     *  缩小一级.
     * @return true则成功缩小，false则没进行缩小操作。
     */
    public static boolean zoomOut(MapView mapView) {
        return getController(mapView).zoomOut();
    }
    /**
     * 以给定的点为中心缩小一级.
     * @param xPixel 给定的x坐标
     * @param yPixel 给定的y坐标
     * @return true则成功缩小，false则没进行缩小操作。
     */
    public static boolean zoomOutFixing(MapView mapView, int xPixel, int yPixel) {
        return getController(mapView).zoomOutFixing(xPixel, yPixel);
    }
    /**
     * 尝试调整地图的缩放，以便显示给定的经纬度范围.
     * @param latSpanE6 (int) (lat * 1E6)
     * @param longSpanE6 (int) (lng * 1E6)
     */
    public static void zoomToSpan(MapView mapView, int latSpanE6, int longSpanE6) {
        getController(mapView).zoomToSpan(latSpanE6, longSpanE6);
    }


    ///////////////////////////////////////////////////////////////////////////
    // 1.7.坐标转换接口 Projection
    // 坐标变换接口，主要用于屏幕与地理坐标的转换, 可以通过MapView.getProjection()获得其实例
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 把屏幕坐标转换成地理坐标.
     * @param x 屏幕X坐标
     * @param y 屏幕Y坐标
     */
    public static GeoPoint fromPixels(MapView mapView, int x, int y) {
        return getProjection(mapView).fromPixels(x, y);
    }
    /**
     * 计算距离. 计算以米为单位的距离对应的屏幕距离
     * @param meters 实际的地理距离
     * @return  转换的屏幕距离
     */
    public static float metersToEquatorPixels(MapView mapView, float meters) {
        return getProjection(mapView).metersToEquatorPixels(meters);
    }
    /**
     * 地理坐标转换成屏幕坐标.
     * @param in 地理坐标
     * @param out 转换后的屏幕坐标，为null则把转换后的坐标返回
     * @return 转换后的屏幕坐标
     */
    public static Point toPixels(MapView mapView, GeoPoint in, Point out) {
        return getProjection(mapView).toPixels(in, out);
    }


    ///////////////////////////////////////////////////////////////////////////
    // 1.9.图层管理类 TMapLayerManager
    // 图层管理类，通过此类管理天地图所支持的所有地图类型和图层
    // 可以使用本类中获得的地图类型和图层类型来设置地图显示内容
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 构造接口，必须传入有效的MapView对象
     */
    public static TMapLayerManager getTMapLayerManager(MapView mapView) {
        return new TMapLayerManager(mapView);
    }
    /**
     * 获得指定地图类型下所有可选的图层名称
     * @param type 地图类型, {@link #mapType()}
     */
    public static String[] getLayers(TMapLayerManager layerManager, int type) {
        return layerManager.getLayers(type);
    }
    /**
     * 获得正在显示的图层名称，不包括底图的名称
     */
    public static String[] getLayersShow(TMapLayerManager layerManager) {
        return layerManager.getLayersShow();
    }
    /**
     *  获得SDK支持的所有地图名称
     */
    public static String[] getMaps(TMapLayerManager layerManager) {
        return layerManager.getMaps();
    }
    /**
     *  根据传入的地图名称获得对应的类型，地图名称可以使用 getMaps 获得
     */
    public static int getMapType(TMapLayerManager layerManager, String name) {
        return layerManager.getMapType(name);
    }
    /**
     *  设置地图需要显示的图层信息
     */
    public static void setLayersShow(TMapLayerManager layerManager, String[] layers) {
        layerManager.setLayersShow(layers);
    }

    //http://lbs.tianditu.gov.cn/android/class.html
    //public com.tianditu.android.core.MapLayer getMapLayer()//获得天地图的图层对象
}
