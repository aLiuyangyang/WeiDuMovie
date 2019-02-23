package com.bw.movie.view.activity.showfileactivity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.view.fragment.allfile_fragment.ComingFragment;
import com.bw.movie.view.fragment.allfile_fragment.HotFileFragment;
import com.bw.movie.view.fragment.allfile_fragment.IsHotFragment;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * date:2019/1/26
 * author:刘洋洋(DELL)
 * function: 影片全部信息
 */
public class ShowFileAllActivity extends BaseActivity {
    @BindView(R.id.all_but_hotfile)
    RadioButton allButHotfile;
    @BindView(R.id.all_but_ishot)
    RadioButton allButIshot;
    @BindView(R.id.all_but_Coming)
    RadioButton allButComing;
    @BindView(R.id.area_place)
    ImageView areaPlace;
    @BindView(R.id.area_name)
    TextView areaName;
    @BindView(R.id.all_frame)
    ViewPager allFrame;
    @BindView(R.id.all_radio)
    RadioGroup allRadio;
    private List<Fragment> list=new ArrayList<>();
    private int flag;//判断到哪个页面
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private String adCode;
    private String cityCode;
    private String province;
    private String city;
    private double longitude;
    private double latitude;
    @Override
    public void initView() {
        ButterKnife.bind(this);
    }
    @Override
    public void initData() {
        //开始定位，这里模拟一下定位
        mlocationClient = new AMapLocationClient(this);
        //设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if(aMapLocation!=null){
                    if(aMapLocation.getErrorCode() == 0){
                        //获取纬度
                        latitude = aMapLocation.getLatitude();
                        //获取经度
                        longitude = aMapLocation.getLongitude();
                        //城市信息
                        city = aMapLocation.getCity();
                        //省信息
                        province = aMapLocation.getProvince();
                        //城市编码
                        cityCode = aMapLocation.getCityCode();
                        //地区编码
                        adCode = aMapLocation.getAdCode();
                        CityPicker.from(ShowFileAllActivity.this).locateComplete(new LocatedCity(city, province, cityCode), LocateState.SUCCESS);
                        areaName.setText(city);
                    }
                }
            }
        });
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        final Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 0);
        list.add(new HotFileFragment());
        list.add(new IsHotFragment());
        list.add(new ComingFragment());
        allFrame.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        allFrame.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        flag=1;
                        allRadio.check(R.id.all_but_hotfile);
                        break;
                    case 1:
                        flag=2;
                        allRadio.check(R.id.all_but_ishot);
                        break;
                    case 2:
                        flag=3;
                        allRadio.check(R.id.all_but_Coming);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        areaName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                    String[] mStatenetwork = new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.CHANGE_WIFI_STATE,
                            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                            Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_ADMIN,
                    };
                    ActivityCompat.requestPermissions(ShowFileAllActivity.this, mStatenetwork, 100);
                }
                CityPicker.from(ShowFileAllActivity.this) //activity或者fragment
                        .enableAnimation(true)	//启用动画效果，默认无
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                areaName.setText(data.getName());
                            }
                            @Override
                            public void onCancel(){
                            }
                            @Override
                            public void onLocate() {
                                //定位接口，需要APP自身实现，这里模拟一下定位
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        CityPicker.from(ShowFileAllActivity.this).locateComplete(new LocatedCity(city, province, cityCode), LocateState.SUCCESS);
                                    }
                                }, 1000);
                            }
                        })
                        .show();
            }
        });
        allRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.all_but_hotfile:
                        flag=1;
                        allFrame.setCurrentItem(0);
                        break;
                    case R.id.all_but_ishot:
                        flag=2;
                        allFrame.setCurrentItem(1);
                        break;
                    case R.id.all_but_Coming:
                        flag=3;
                        allFrame.setCurrentItem(2);
                        break;
                }
            }
        });
        showandhide(flag);
    }

    private void showandhide(int flag) {
        if (flag==1){
            allFrame.setCurrentItem(0);
            allRadio.check(R.id.all_but_hotfile);
        }else if (flag==2){
            allFrame.setCurrentItem(1);
            allRadio.check(R.id.all_but_ishot);
        }else if (flag==3){
            allFrame.setCurrentItem(2);
            allRadio.check(R.id.all_but_Coming);
        }
    }

    @Override
    public int getContent() {
        return R.layout.activity_show_file_all;
    }

    @Override
    public void success(Object data) {

    }
    @Override
    public void fail(String error) {

    }

}
