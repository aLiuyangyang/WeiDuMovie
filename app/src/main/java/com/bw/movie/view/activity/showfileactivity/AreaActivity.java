package com.bw.movie.view.activity.showfileactivity;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.bw.movie.R;

/**
 * date:2019/1/30
 * author:孙佳鑫(孙佳鑫)
 * function:
 */
public class AreaActivity extends Activity {
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        mBaiduMap.setMyLocationEnabled(true);
        MyLocationData locationData = new MyLocationData.Builder()
                //40.0481292144,116.3065021771
                .latitude(40.0481292144)
                .longitude(116.3065021771)
                .build();
// 设置定位数据
        mBaiduMap.setMyLocationData(locationData);
        BitmapDescriptor marker = BitmapDescriptorFactory
                .fromResource(R.drawable.ic_launcher_background);
        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, marker);
        mBaiduMap.setMyLocationConfigeration(config);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
