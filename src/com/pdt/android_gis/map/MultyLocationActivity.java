package com.pdt.android_gis.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnCameraChangeListener;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Gradient;
import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.model.TileOverlayOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.AMap.OnMarkerDragListener;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.UiSettings;
import com.pdt.android_gis.R;
import com.pdt.android_gis.R.drawable;
import com.pdt.android_gis.R.id;
import com.pdt.android_gis.R.layout;
import com.pdt.android_gis.AppManager;
import com.pdt.android_gis.MainActivity;
import com.pdt.android_gis.database.DBManager;
import com.pdt.android_gis.database.MyDatabaseHelper;
import com.pdt.android_gis.set.SetActivity;
import com.pdt.android_gis.ui.markercluster.MarkerCluster;
import com.pdt.android_gis.util.Constants;
import com.pdt.android_gis.util.MakerColor;
import com.pdt.android_gis.util.RssMapColor;
import com.pdt.android_gis.util.ToastUtil;

/**
 * 地图主界面
 * */
public class MultyLocationActivity extends Activity implements LocationSource,
		AMapLocationListener, OnCheckedChangeListener, OnMarkerClickListener, 
		OnMarkerDragListener, OnMapLoadedListener,OnClickListener, SensorEventListener, OnCameraChangeListener{
	private AMap aMap;
	private MapView mapView;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	private RadioGroup mGPSModeGroup;
	private LatLng markPoint;// 标记点坐标
	private MarkerOptions markerOption;
	private Marker marker2;// 有跳动效果的marker对象
	private TextView markerText;
	private String mLocationDesTextView;// 定位描述信息
	private ImageButton markerButton;// 在当前位置添加Marker
	private ImageButton markerButton1;// 清除Marker

	private View colorPanel;
	
	private String TAG = "Location";	
	public static MyDatabaseHelper dbHelper;
	public static SQLiteDatabase db;
	private ArrayList<Marker> markers = new ArrayList<Marker>();
	private ArrayList<Marker> markers1 = new ArrayList<Marker>();
	private ArrayList<Polyline> polylines = new ArrayList<Polyline>();
	protected static final int Menu_Set = Menu.FIRST;
	protected static final int Menu_Exit = Menu.FIRST+1;
	
	private long exitTime = 0;
	private UiSettings mUiSettings;
	private Marker marker;// 定位雷达小图标
	//方向传感器 控制蓝箭头方向
	private SensorManager SensorManager;
	private Sensor mSensor;
	private float bearing = 0;
	private int color = 0;
	private Polyline polyline;
	private LatLngBounds.Builder builder = new LatLngBounds.Builder();
	//数据库游标
	private Cursor cursor;
	//热力图参数
	private static final int[] ALT_HEATMAP_GRADIENT_COLORS = {
		Color.argb(0, 0, 255, 255),
		Color.argb(255 / 3 * 2, 0, 255, 0), 
		Color.rgb(125, 191, 0),
		Color.rgb(185, 71, 0),
		Color.rgb(255, 0, 0) 
		};
    public static final float[] ALT_HEATMAP_GRADIENT_START_POINTS = { 0.0f,
		0.10f, 0.20f, 0.60f, 1.0f };
    public static final Gradient ALT_HEATMAP_GRADIENT = new Gradient(
		ALT_HEATMAP_GRADIENT_COLORS, ALT_HEATMAP_GRADIENT_START_POINTS);
	//Marker点聚合
    private ArrayList<MarkerOptions> markerOptionsList = new ArrayList<MarkerOptions>();// 所有的marker
	private ArrayList<MarkerOptions> markerOptionsListInView = new ArrayList<MarkerOptions>();// 视野内的marker
	private int height;// 屏幕高度(px)
	private int width;// 屏幕宽度(px)
	private int gridSize = 100;// marker点区域大小
    Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0) {
				resetMarks();// 更新markers
			}
		}
	};
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
		setContentView(R.layout.activity_multy_location);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		getWindow().setBackgroundDrawable(null);//背景设置为空
		//获取屏幕分辨率
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;
		init();
		initDataAndHeatMap();//热力图
		AppManager.getAppManager().addActivity(this);
	}

	private void initDataAndHeatMap() {
		// TODO Auto-generated method stub
		LatLng[] latlngs = new LatLng[500];
		double x = 30.679879;
		double y = 104.064855;
 
		for (int i = 0; i < 500; i++) {
			double x_ = 0;
			double y_ = 0;
			x_ = Math.random() * 0.5 - 0.25;
			y_ = Math.random() * 0.5 - 0.25;
			latlngs[i] = new LatLng(x + x_, y + y_);
		}
		HeatmapTileProvider heatmapTileProvider = new HeatmapTileProvider.Builder()
				.data(Arrays.asList(latlngs)).gradient(ALT_HEATMAP_GRADIENT)

				.build();
		aMap.addTileOverlay(new TileOverlayOptions().tileProvider(heatmapTileProvider));
	}

	/**
	 * 初始化
	 */
	private void init() {
		
		markerButton = (ImageButton) findViewById(R.id.imageButton1);
		markerButton.setOnClickListener(this);
		
		markerButton1 = (ImageButton) findViewById(R.id.imageButton2);
		markerButton1.setOnClickListener(this);
		//颜色控件
		colorPanel = (View) findViewById(R.id.colorpanel);
		colorPanel.setDrawingCacheEnabled(true);
		Constants.color60Panel = (View) findViewById(R.id.greenpanel);
		Constants.color60Panel.setDrawingCacheEnabled(true);
		Constants.color70Panel = (View) findViewById(R.id.lightgreenpanel);
		Constants.color70Panel.setDrawingCacheEnabled(true);
		Constants.color80Panel = (View) findViewById(R.id.lightbluepanel);
		Constants.color80Panel.setDrawingCacheEnabled(true);
		Constants.color90Panel = (View) findViewById(R.id.bluepanel);
		Constants.color90Panel.setDrawingCacheEnabled(true);
		Constants.color100Panel = (View) findViewById(R.id.yellowpanel);
		Constants.color100Panel.setDrawingCacheEnabled(true);
		Constants.color110Panel = (View) findViewById(R.id.lightyellowpanel);
		Constants.color110Panel.setDrawingCacheEnabled(true);
		Constants.color120Panel = (View) findViewById(R.id.pinkpanel);
		Constants.color120Panel.setDrawingCacheEnabled(true);
		Constants.color130Panel = (View) findViewById(R.id.redpanel);
		Constants.color130Panel.setDrawingCacheEnabled(true);
		
		if (aMap == null) {
			aMap = mapView.getMap();
			mUiSettings = aMap.getUiSettings();
			mUiSettings.setCompassEnabled(true);
			setUpMap();
		}
		
		mGPSModeGroup = (RadioGroup) findViewById(R.id.gps_radio_group);
		mGPSModeGroup.setOnCheckedChangeListener(this);
		//创建数据库导入测试数据
		dbHelper = new MyDatabaseHelper(this, "BaseMap.db", null, 1);//1代表版本号
		dbHelper.getWritableDatabase();
		db = dbHelper.getWritableDatabase();
		//方向传感器
		SensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = SensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		//从.db导入到sqlite数据库
		Constants.dbM = new DBManager(this);
		Constants.dbM.openDatabase();
		Constants.database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);
		
	}

	/**
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
		
		aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		aMap.setOnCameraChangeListener(this);
		// 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
		
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.gps_locate_button:
			// 设置定位的类型为定位模式
			aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
			break;
		case R.id.gps_cover_button:
			// 设置定位的类型为 覆盖模式
			aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
//			aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					coverMap();
				}
			}).start();		
			
			break;
		case R.id.gps_topology_button:
			
			//若未登录则跳转到登录界面进行身份验证，否则不跳转
			if(!MainActivity.loginFlag){
				Intent multyIntent=new Intent(MultyLocationActivity.this,MainActivity.class);
				startActivity(multyIntent);
			}		  
			// 设置定位的类型为拓扑模式
			aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					topologyMap();
					
				}
			}).start();
			
			break;
		}

	}

	private void topologyMap() {
		// TODO Auto-generated method stub
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
		double longitude0 = 0;
		double latitude0 = 0;
		double longitude = 0;
		double latitude = 0;
		double longitude1 = 0;
		double latitude1 = 0;
		double rss = 0;
		double rss0 = 0;
		double rss1 = 0;
		int cnt = 0;
		String basename = null;
		//查询baseinfo表中所有的基站数据(条件查询需设置query中的参数)
		cursor = db.query("baseinfo", null, null, null, null, null, null, null);		
		if(cursor.moveToFirst()){
			do{
				//遍历Cursor对象 取出要用到的数据
				longitude = cursor.getDouble(cursor.getColumnIndex("Longitude"));
				latitude = cursor.getDouble(cursor.getColumnIndex("Latitude"));
				basename = cursor.getString(cursor.getColumnIndex("BaseName"));
				markBase(longitude, latitude, basename);
				
			}while(cursor.moveToNext());
		}
		
		//轨迹绘制
		cursor = Constants.database.query("coverdata", null, null, null, null, null, null, null);		
		if(cursor.moveToFirst()){
			do{
				//遍历Cursor对象 取出要用到的数据
				longitude = cursor.getDouble(cursor.getColumnIndex("Longitude"));
				latitude = cursor.getDouble(cursor.getColumnIndex("Latitude"));
				rss = cursor.getInt(cursor.getColumnIndex("Rss"));
				if(longitude0 == 0 && latitude0 == 0){
					
					longitude0 = longitude;
					latitude0 = latitude;
					rss0 = rss;
					//标记起点位置
					markBegin(longitude0, latitude0);
					cnt++;
					
				}else{
					//设计判断 使当连续两点间距离很远时 不连线 防止错误数据的影响
					if(Math.abs(latitude-latitude0)<0.01 && Math.abs(longitude-longitude0)<0.01){
						plotLine(longitude0,latitude0,longitude,latitude,rss0);
						longitude0 = longitude;
						latitude0 = latitude;
						rss0 = rss;
					}else if (Math.abs(latitude-latitude1)<0.01 && Math.abs(longitude-longitude1)<0.01) {
						plotLine(longitude1,latitude1,longitude,latitude,rss1);
						longitude1 = longitude0;
						latitude1 = latitude0;
						longitude0 = longitude;
						latitude0 = latitude;
						rss0 = rss;
					}else{
//						markEnd(longitude0, latitude0);
						longitude1 = longitude0;
						latitude1 = latitude0;
						rss1 = rss0;
						longitude0 = longitude;
						latitude0 = latitude;
						rss0 = rss;
					}
					cnt++;
					
				}
				
			}while(cnt<=1000 && cursor.moveToNext());
		}
		//标记终点位置
		markEnd(longitude0, latitude0);
//		cursor.close();
		cnt= 0;
		
	}

	private void markEnd(double longitude, double latitude) {
		// TODO Auto-generated method stub
		MarkerOptions markerOption = new MarkerOptions();
		LatLng markend = new LatLng(latitude, longitude);// 标记点坐标
		markerOption.position(markend);
		//反向地址解析  获取经纬度对应地址
		Geocoder geo = new Geocoder(MultyLocationActivity.this, Locale.getDefault());    
	    List<Address> addresses;
		try {
			addresses = geo.getFromLocation(latitude,longitude, 1);
		    if (addresses.isEmpty()) {    
		        Log.i("location", "addressed is Empty");    
		    }    
		    else {    
		        if (addresses.size() > 0) {    
//		            Log.i("location", addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName()); 
		            markerOption.title(addresses.get(0).getFeatureName());
		        }    
		    } 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		markerOption.visible(true);
		markerOption.draggable(false);
		markerOption.icon(BitmapDescriptorFactory
				.fromResource(R.drawable.end));
		Marker marker;
		marker = aMap.addMarker(markerOption);
		//将Marker加入Marker簇中 用于清除Markers
		markers1.add(marker);
		builder.include(markend);
		aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));
	}

	private void markBegin(double longitude, double latitude) {
		// TODO Auto-generated method stub
		MarkerOptions markerOption = new MarkerOptions();
		LatLng markbegin = new LatLng(latitude, longitude);// 标记点坐标
		markerOption.position(markbegin);
		//反向地址解析  获取经纬度对应地址
		Geocoder geo = new Geocoder(MultyLocationActivity.this, Locale.getDefault());    
	    List<Address> addresses;
		try {
			addresses = geo.getFromLocation(latitude,longitude, 1);
		    if (addresses.isEmpty()) {    
		        Log.i("location", "addressed is Empty");    
		    }    
		    else {    
		        if (addresses.size() > 0) {    
//		            Log.i("location", addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName()); 
		            markerOption.title(addresses.get(0).getFeatureName());
		        }    
		    } 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		markerOption.visible(true);
		markerOption.draggable(false);
		markerOption.icon(BitmapDescriptorFactory
				.fromResource(R.drawable.begin));
		Marker marker;
		marker = aMap.addMarker(markerOption);
		//将Marker加入Marker簇中 用于清除Markers
		markers1.add(marker);
		builder.include(markbegin);
		
	}

	private void plotLine(double longitude0, double latitude0,
			double longitude, double latitude, double rss0) {
		// TODO Auto-generated method stub
		Constants.rssmapcolor = new RssMapColor();
//		color = (int) Constants.rssmapcolor.hm.get(rss0);
		color = 1;
		LatLng begin = new LatLng(latitude0, longitude0);
		LatLng end = new LatLng(latitude, longitude);
		 // 绘制轨迹线
		polyline = aMap.addPolyline((new PolylineOptions())
                .add(begin, end)
                .width(10).setDottedLine(false).geodesic(true)
                .color(color));
		polylines.add(polyline);
		
	}

	private void markBase(double longitude, double latitude, String basename) {
		// TODO Auto-generated method stub
		MarkerOptions markerOption = new MarkerOptions();
		LatLng markPoint = new LatLng(latitude, longitude);// 标记点坐标
		markerOption.position(markPoint);
		markerOption.title(basename);
		markerOption.perspective(true);
		markerOption.draggable(false);
		markerOption.icon(BitmapDescriptorFactory
				.fromResource(R.drawable.basestation));
		Marker marker;
		marker = aMap.addMarker(markerOption);
		//将Marker加入Marker簇中 用于清除Markers
		markers1.add(marker);
		// marker旋转90度
		marker.setRotateAngle(0);
		
	}

	private void coverMap() {
		// TODO Auto-generated method stub
		double longitude = 0;
		double latitude = 0;
		double rss = 0;
        int cnt = 0;
		//查询coverdata表中所有的数据(条件查询需设置query中的参数)
		cursor = db.query("coverdata", null, null, null, null, null, null, null);
		
//		LatLngBounds bounds = new LatLngBounds.Builder().build();		
		LatLngBounds.Builder builder = new LatLngBounds.Builder();	
		LatLng markPoint1;
		
		if(cursor.moveToFirst()){
			do{
				//遍历Cursor对象 取出要用到的数据
				longitude = cursor.getDouble(cursor.getColumnIndex("Longitude"));
				latitude = cursor.getDouble(cursor.getColumnIndex("Latitude"));
				rss = cursor.getInt(cursor.getColumnIndex("Rss"));
				markPoints(longitude, latitude, rss);
				cnt++;
				// 设置所有maker显示在当前可视区域地图中
				markPoint1 = new LatLng(latitude,longitude);
				builder.include(markPoint1);
				
			}while(cnt<=100 && cursor.moveToNext());
		}
		builder.include(markPoint);
		aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));
//		cursor.close();
		cnt = 0;
		
	}

	private void markPoints(double longitude, double latitude, double rss) {
		// TODO Auto-generated method stub
		MarkerOptions pointmarker = new MarkerOptions();
		LatLng markPoint = new LatLng(latitude, longitude);// 标记点坐标
		pointmarker.position(markPoint); 
		
		//反向地址解析  获取经纬度对应地址 需要联网查询地址 受网速影响
		Geocoder geo = new Geocoder(MultyLocationActivity.this, Locale.getDefault());    
	    List<Address> addresses;
		try {
			addresses = geo.getFromLocation(latitude,longitude, 1);
		    if (addresses.isEmpty()) {    
		        Log.i("location", "addressed is Empty");    
		    }    
		    else {    
		        if (addresses.size() > 0) {    
//		            Log.i("location", addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName()); 
		    		pointmarker.title(addresses.get(0).getFeatureName());
		        }    
		    } 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		
		pointmarker.perspective(true);
		pointmarker.draggable(true);
        //根据不同信号强度标记不同的颜色
		Constants.rssmapcolor = new RssMapColor();
		pointmarker.icon(BitmapDescriptorFactory.defaultMarker(12));//(float) Constants.rssmapcolor.hashmap.get(rss)
		
		Marker marker;
		marker = aMap.addMarker(pointmarker);
		//添加到List 便于清除
		markers.add(marker);
		// 用于聚合点
		markerOptionsList.add(pointmarker);
		// marker旋转0度
//		marker.setRotateAngle(0);
		
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		
		//方向传感器
		SensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		
		SensorManager.unregisterListener(this);
		super.onPause();
		mapView.onPause();
		deactivate();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	/**
	 * 此方法已经废弃
	 */
	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (mListener != null && amapLocation != null) {
			if (amapLocation != null
					&& amapLocation.getAMapException().getErrorCode() == 0) {
				markPoint = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
				mLocationDesTextView = amapLocation.getAddress();
				mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
//				marker.setPosition(new LatLng(amapLocation.getLatitude(), amapLocation
//						.getLongitude()));// 定位雷达小图标
//				float bearing = aMap.getCameraPosition().bearing;
//				ToastUtil.show(this, String.valueOf(bearing));
				aMap.setMyLocationRotateAngle(bearing);// 设置小蓝点旋转角度
				
			} else {
				Log.e("AmapErr","Location ERR:" + amapLocation.getAMapException().getErrorCode());
			}
		}
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用destroy()方法
			// 其中如果间隔时间为-1，则定位只定一次
			// 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
			mAMapLocationManager.requestLocationData(
					LocationProviderProxy.AMapNetwork, 2 * 1000, 10, this);
		}
	}

	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destroy();
		}
		mAMapLocationManager = null;
	}
	
	/**
	 * 在地图上添加marker
	 */
	private void addMarkersToMap() {

//		Log.d(TAG, "Add Marker");
		markerOption = new MarkerOptions();
		markerOption.position(markPoint);
		markerOption.title(mLocationDesTextView);
		markerOption.perspective(true);
		markerOption.draggable(true);
		markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		marker2 = aMap.addMarker(markerOption);
		markerOptionsList.add(markerOption);
		markers.add(marker2);
		// marker旋转0度
//		marker2.setRotateAngle(0);

	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 获取屏幕所有marker
		case R.id.imageButton1:
		
			addMarkersToMap();// 往地图上添加marker
//			onMapLoaded();
			
//			if (aMap != null) {
//				List<Marker> markers = aMap.getMapScreenMarkers();
//				if (markers == null || markers.size() == 0) {
//					ToastUtil.show(this, "当前屏幕内没有Marker");
//					return;
//				}
//				String tile = "屏幕内有：";
//				for (Marker marker : markers) {
//					tile = tile + " " + marker.getTitle();
//
//				}
//				ToastUtil.show(this, tile);
//
//			}
			
			break;
		case R.id.imageButton2:
			
			for (Marker marker: markers) {
				marker.remove();
              }
			
			for (Marker marker: markers1) {
				marker.remove();
              }
			
			for (Polyline polyline: polylines) {
				polyline.remove();
              }
			
			markerOptionsList.clear();
			
			break;
		default:
			break;
		}
	}
	
	/**
	 * 监听拖动marker时事件回调
	 */
	@Override
	public void onMarkerDrag(Marker marker) {
		String curDes = marker.getTitle() + "拖动时当前位置:(lat,lng)\n("
				+ marker.getPosition().latitude + ","
				+ marker.getPosition().longitude + ")";
		markerText.setText(curDes);
	}
	
	/**
	 * 监听拖动marker结束事件回调
	 */
	@Override
	public void onMarkerDragEnd(Marker marker) {
		markerText.setText(marker.getTitle() + "停止拖动");
	}

	/**
	 * 监听开始拖动marker事件回调
	 */
	@Override
	public void onMarkerDragStart(Marker marker) {
		markerText.setText(marker.getTitle() + "开始拖动");
	}
	
	/**
	 * 监听amap地图加载成功事件回调
	 */
	@Override
	public void onMapLoaded() {
		// 设置所有maker显示在当前可视区域地图中
		LatLngBounds bounds = new LatLngBounds.Builder()
				.include(Constants.JINNIU).include(Constants.CHENGDU)
				.include(markPoint).include(Constants.WUHOU).build();
		aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
	}
	
	/**
	 * 对marker标注点点击响应事件
	 */
	@Override
	public boolean onMarkerClick(final Marker marker) {
		
		if (aMap != null) {
		}
//		markerText.setText("您点击的是" + marker.getTitle());
		return false;
	}
	/**
	 * 菜单键功能
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);

		menu.add(0,Menu_Set,0,"设置");
		menu.add(0,Menu_Exit,0,"注销账户");
		return true;
//		return super.onCreateOptionsMenu(menu);
	}
	/**
	 * 菜单键相应
	 */

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
//		return super.onOptionsItemSelected(item);
		super.onOptionsItemSelected(item);
		  switch(item.getItemId())
		  {
		  case Menu_Set:
			  Intent multyIntent=new Intent(MultyLocationActivity.this,SetActivity.class);
			  startActivity(multyIntent);
		      break;
		  case Menu_Exit:
			  new  AlertDialog.Builder(this).setMessage("确定注销帐户？" ).setPositiveButton("确定",
					  new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int which) {
					  
					  MainActivity.loginFlag = false;
					  
					  }}).setNegativeButton("取消",null).show();     
		      break;
		  }
		  return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            
//	        	cursor.close();
	        	Constants.dbM.closeDatabase();
				AppManager.getAppManager().AppExit(this);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		//方向传感器 目的是使箭头方向随着手机的指向转动
		float azimuth_angle = event.values[0];
//	    float pitch_angle = event.values[1];
//	    float roll_angle = event.values[2];
		bearing = -azimuth_angle;
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onCameraChange(CameraPosition arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onCameraChangeFinish(CameraPosition cameraPosition) {
		handler.sendEmptyMessage(0);// 更新界面marker
	}
	
	/**
	 * 获取视野内的marker 根据聚合算法合成自定义的marker 显示视野内的marker
	 */
	private void resetMarks() {
		// 开始刷新界面
		Projection projection = aMap.getProjection();
		Point p = null;
		markerOptionsListInView.clear();
		// 获取在当前视野内的marker;提高效率
		for (MarkerOptions mp : markerOptionsList) {
			p = projection.toScreenLocation(mp.getPosition());
			if (p.x < 0 || p.y < 0 || p.x > width || p.y > height) {
				// 不添加到计算的列表中
			} else {
				markerOptionsListInView.add(mp);
			}
		}
		// 自定义的聚合类MarkerCluster
		ArrayList<MarkerCluster> clustersMarker = new ArrayList<MarkerCluster>();
		for (MarkerOptions mp : markerOptionsListInView) {
			if (clustersMarker.size() == 0) {
				clustersMarker.add(new MarkerCluster(MultyLocationActivity.this, mp,
						projection, gridSize));// 100根据自己需求调整
			} else {
				boolean isIn = false;
				for (MarkerCluster cluster : clustersMarker) {
					if (cluster.getBounds().contains(mp.getPosition())) {
						cluster.addMarker(mp);
						isIn = true;
						break;
					}
				}
				if (!isIn) {
					clustersMarker.add(new MarkerCluster(MultyLocationActivity.this, mp,
							projection, gridSize));
				}
			}
		}
		// 先清除地图上markers覆盖物
		for (Marker marker: markers) {
			marker.remove();
          }
		Marker marker;
		for (MarkerCluster markerCluster : clustersMarker) {
			markerCluster.setpositionAndIcon();// 设置聚合点的位置和icon
			marker = aMap.addMarker(markerCluster.getOptions());// 重新添加
			markers.add(marker);
		}
	}
	
	 /* 将所有覆盖物显示在可视范围地图上 */
//    protected void showAllOverlay() {
//            // TODO Auto-generated method stub
//    	LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        List<Marker> markers = aMap.getMapScreenMarkers();
//        for (Marker marker2 : markers) {
//                builder.include(marker2.getPosition());
//        }
//        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build()
//                        ,markers.size()));
//    }
	
}
