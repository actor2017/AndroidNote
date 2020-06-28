package com.dbjtech.tracker.en;

import java.util.ArrayList;
import java.util.List;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMapView;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.MapController.AnimationType;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class ViewRealtimeOSM extends Activity {

	private MapView mMapView;
	private MapController mController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_realtime_osm);
		mMapView = (MapView) findViewById(R.id.myOSMmapview);
		mMapView.setTileSource(TileSourceFactory.MAPNIK);
		mMapView.setBuiltInZoomControls(true);
		mMapView.setClickable(true);
		mMapView.setMultiTouchControls(true);
		mMapView.setPressed(true);
		mMapView.setMapListener(new MapListener() {
			
			@Override
			public boolean onZoom(ZoomEvent arg0) {
				System.out.println("zoom:"+arg0);
				return true;
			}
			
			@Override
			public boolean onScroll(ScrollEvent arg0) {
				System.out.println("scroll:"+arg0);
				return true;
			}
		});
		
		
		mController = mMapView.getController();

		//
		GeoPoint gp = new GeoPoint(39.901873, 116.326655);
		// mController.animateTo(gp, AnimationType.EXPONENTIALDECELERATING);
		mController.setZoom(10);
		mController.setCenter(gp);
//		mMapView.getOverlays().add(new CustomOverlay(this, null));
		// mMapView.refreshDrawableState();

	}

	class CustomOverlay extends ItemizedOverlay<OverlayItem> {
		private List<OverlayItem> listData = new ArrayList<OverlayItem>();
		private double mLat1 = 39.90923;// 39.9022; // point1纬度
		private double mLon1 = 116.397428;// 116.3822; // point1经度

		public CustomOverlay(Drawable pDefaultMarker,
				ResourceProxy pResourceProxy) {
			super(pDefaultMarker, pResourceProxy);
		}

		public CustomOverlay(Context context, ResourceProxy proxy) {
			super(context.getResources()
					.getDrawable(R.drawable.marker_gpsvalid),
					new DefaultResourceProxyImpl(context));
			GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
			listData.add(new OverlayItem("p1", "point1", p1));
			System.out.println("CustomOverlay:" + listData.size());
		}

		@Override
		public boolean onSnapToItem(int arg0, int arg1, Point arg2,
				IMapView arg3) {
			return true;
		}

		@Override
		protected OverlayItem createItem(int arg0) {
			return listData.get(arg0);
		}

		@Override
		public int size() {
			return listData.size();
		}

		@Override
		protected boolean onTap(int arg0) {
			System.out.println("int:" + arg0);
			mMapView.getController().animateTo(listData.get(arg0).getPoint());
			return super.onTap(arg0);
		}

	}

}
