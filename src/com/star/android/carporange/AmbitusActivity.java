package com.star.android.carporange;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

@SuppressLint("HandlerLeak") public class AmbitusActivity extends Activity {
	
	private final int MAKE_TOAST = 1;
	
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	
	private LocationClient mLocationClient;
	private BDLocationListener myListener = new MyLocationListener();
	
	private double mLongitude;
	private double mLatitude;
	private boolean isFirstLoc = true;
	
	private PoiSearch mPoiSearch;
	private BitmapDescriptor mCurrentMarker;
	private LocationMode mCurrentMode = LocationMode.NORMAL;
	
	private Handler mHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ambitus);
		buildView();
	}

	private void buildView() {
		
		mHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case MAKE_TOAST :
					Toast.makeText(AmbitusActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
					Log.i("bb", "love");
					break;
				}
			};
		};
		
		mMapView = (MapView) findViewById(R.id.mapView);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		mBaiduMap.setMyLocationEnabled(true);
		
		
		mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000*10);
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}
	
	
	
	class MyLocationListener implements BDLocationListener{

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null){
				return;
			}
			
			mLongitude = location.getLongitude();
			mLatitude = location.getLatitude();
			
			MyLocationData locData = new MyLocationData.Builder()  
		    .accuracy(location.getRadius())  
		    // 此处设置开发者获取到的方向信息，顺时针0-360  
		    .direction(100).latitude(location.getLatitude())  
		    .longitude(location.getLongitude()).build();  
			// 设置定位数据   
			mBaiduMap.setMyLocationData(locData);
			mCurrentMarker = BitmapDescriptorFactory  
				    .fromResource(R.drawable.icon_geo); 
			mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
					mCurrentMode, true, mCurrentMarker));
			LatLng ll = new LatLng(location.getLatitude(),
					location.getLongitude());;
			if(isFirstLoc) {
				isFirstLoc = false;
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
			
			mPoiSearch = PoiSearch.newInstance();
			OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
				
				@SuppressWarnings("null")
				@Override
				public void onGetPoiResult(PoiResult result) {
					if(result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
						return;
					}
					MyPoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
					mBaiduMap.setOnMarkerClickListener(overlay);
					overlay.setData(result);
					overlay.addToMap();
					overlay.zoomToSpan();
				}
				
				@Override
				public void onGetPoiDetailResult(PoiDetailResult result) {
					Log.i("bb", "xx");
					Message m = new Message();
					m.what = MAKE_TOAST;
					if (result.error != SearchResult.ERRORNO.NO_ERROR) {
						m.obj = "抱歉，未找到结果";
					} else {
						m.obj = result.getName() + ": " + result.getAddress();
					}
					mHandler.sendMessage(m);
				}
			};
			mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
			mPoiSearch.searchNearby(new PoiNearbySearchOption().radius(1500)
					.keyword("诊所").location(ll)
					.pageCapacity(50).pageNum(0));
		}
		
	}
	
	class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}
		
		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			PoiInfo poi = getPoiResult().getAllPoi().get(index);
			mPoiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(poi.uid));
			return true;
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
		mPoiSearch.destroy();
	}
}
