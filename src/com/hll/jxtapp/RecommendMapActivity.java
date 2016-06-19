package com.hll.jxtapp;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import android.app.Activity;
import android.os.Bundle;

/**
 * 推荐页面，位置定位地图
 * @author liaoyun 2016-6-5
 */
public class RecommendMapActivity extends Activity {
	private MapView mapView;
	private BaiduMap baiduMap;
	private LocationClient locationClient;
	//图标
	private BitmapDescriptor mapDescriptor;
	//是否首次定位 
	private boolean isFirstLoc=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_recommend_map);
		//获取地图控件引用
		mapView = (MapView) findViewById(R.id.bmapView);
		BaiduMap baiduMap = mapView.getMap();
		//普通地图
		baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		//开启定位图层
		baiduMap.setMyLocationEnabled(true);
		locationClient = new LocationClient(getApplicationContext());
		locationClient.registerLocationListener(locationListener);
		this.setLocationOption();
		locationClient.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}
	BDLocationListener locationListener = new BDLocationListener() {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if(location==null && mapView==null){
				return;
			}
			MyLocationData locationData = new MyLocationData.Builder().accuracy(location.getRadius())
					.direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
			//设置定位信息
			baiduMap.setMyLocationData(locationData);
			if(isFirstLoc==true){
				isFirstLoc=false;
			}
			LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
			//设置地图中心点以及缩放级别
			MapStatusUpdate mstu = MapStatusUpdateFactory.newLatLngZoom(ll, 16);
			baiduMap.animateMapStatus(mstu);
		}
	};
	//设置定位 参数
	private void setLocationOption(){
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		//设置定位模式
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
		//返回的定位结果是百度经纬度,默认值gcj02
		//设置发起定位请求的间隔时间为5000ms
		option.setScanSpan(5000);
		//返回的定位结果包含地址信息
		option.setIsNeedAddress(true);
		//返回的定位结果包含手机机头的方向
		option.setNeedDeviceDirect(true);
		//返回的定位结果是百度经纬度,默认值gcj02
		option.setCoorType("bd09ll"); 
		locationClient.setLocOption(option);
	}
}
