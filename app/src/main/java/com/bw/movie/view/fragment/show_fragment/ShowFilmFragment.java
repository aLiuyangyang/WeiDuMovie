package com.bw.movie.view.fragment.show_fragment;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bw.movie.R;
import com.bw.movie.adapter.ShowFilm_NewShowing_Adapter;
import com.bw.movie.adapter.showfile_adapter.ShowFile_Banner_Adapter;
import com.bw.movie.adapter.showfile_adapter.ShowFilm_Coming_Adapter;
import com.bw.movie.adapter.showfile_adapter.ShowFilm_HotShop_Adapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.ShowFile_Banner_Info;
import com.bw.movie.bean.ShowFile_HotShopBean;
import com.bw.movie.bean.ShowFile_NewShowingBean;
import com.bw.movie.utils.Constant;
import com.bw.movie.utils.ImageViewAnimationHelper;
import com.bw.movie.view.activity.showfileactivity.ShowFileAllActivity;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * date:2019/1/25
 * author:刘洋洋,孙佳鑫(DELL)
 * function:影片展示
 */
public class ShowFilmFragment extends BaseFragment {
    @BindView(R.id.comingSooe_Recy)
    RecyclerView comingSooeRecy;
    @BindView(R.id.hotRecy)
    RecyclerView hotRecy;
    @BindView(R.id.newShowing_Recy)
    RecyclerView newShowingRecy;
    Unbinder unbinder;
    @BindView(R.id.area_places)
    ImageView area_places;
    @BindView(R.id.area_name)
    TextView areaName;
    @BindView(R.id.recyc_flow)
    RecyclerCoverFlow recycFlow;
    @BindView(R.id.hotAll_HotFile)
    ImageView hotAllHotFile;
    @BindView(R.id.hotAll_IsHot)
    ImageView hotAllIsHot;
    @BindView(R.id.hotAll_Coming)
    ImageView hotAllComing;
    @BindView(R.id.file_search_img)
    ImageView fileSearchImg;
    @BindView(R.id.file_search_edit)
    EditText fileSearchEdit;
    @BindView(R.id.file_search_text)
    TextView fileSearchText;
    @BindView(R.id.file_linearLayout_search)
    LinearLayout fileLinearLayoutSearch;
    @BindView(R.id.hfile)
    TextView hfile;
    @BindView(R.id.hview)
    View hview;
    @BindView(R.id.hot)
    RelativeLayout hot;
    @BindView(R.id.ntext)
    TextView ntext;
    @BindView(R.id.nview)
    View nview;
    @BindView(R.id.newshowing)
    RelativeLayout newshowing;
    @BindView(R.id.ctext)
    TextView ctext;
    @BindView(R.id.cview)
    View cview;
    @BindView(R.id.scrollview)
    ScrollView scrollview;
    @BindView(R.id.checked_layout)
    LinearLayout checkedLayout;
    @BindView(R.id.parent_layout)
    LinearLayout parentLayout;
    private int page;//当前页数
    private int count = 10;//每页请求的数量;
    private ShowFilm_HotShop_Adapter showFilm_hotShop_adapter;
    private ShowFilm_NewShowing_Adapter showFilm_newShowing_adapter;
    private ShowFilm_Coming_Adapter showFilm_coming_adapter;
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
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void initData(View view) {
        page = 1;
        //轮播图
        setGet(String.format(Constant.Banner_Path, page, count), ShowFile_Banner_Info.class);
        //热门电影
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hotRecy.setLayoutManager(linearLayoutManager);
        showFilm_hotShop_adapter = new ShowFilm_HotShop_Adapter(getContext());
        hotRecy.setAdapter(showFilm_hotShop_adapter);
       //正在热映
        LinearLayoutManager linearLayoutManagerc = new LinearLayoutManager(getActivity());
        linearLayoutManagerc.setOrientation(OrientationHelper.HORIZONTAL);
        newShowingRecy.setLayoutManager(linearLayoutManagerc);
        showFilm_newShowing_adapter = new ShowFilm_NewShowing_Adapter(getContext());
        newShowingRecy.setAdapter(showFilm_newShowing_adapter);
        //setGet(String.format(Constant.Banner_Path, page, count), ShowFile_Banner_Info.class);
        //即将上映
        LinearLayoutManager linearLayoutManagerj = new LinearLayoutManager(getContext());
        linearLayoutManagerj.setOrientation(LinearLayoutManager.HORIZONTAL);
        comingSooeRecy.setLayoutManager(linearLayoutManagerj);
        showFilm_coming_adapter = new ShowFilm_Coming_Adapter(getContext());
        comingSooeRecy.setAdapter(showFilm_coming_adapter);

        //搜索
        fileSearchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(fileLinearLayoutSearch, "translationX", 0f, -380f);
//      设置移动时间
                objectAnimator.setDuration(1000);
//      开始动画
                objectAnimator.start();

            }
        });

        fileSearchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(fileLinearLayoutSearch, "translationX", -380f, 0f);
//      设置移动时间
                objectAnimator.setDuration(1000);
//      开始动画
                objectAnimator.start();
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });


        //开始定位，这里模拟一下定位
        mlocationClient = new AMapLocationClient(getActivity());
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
                        CityPicker.from(getActivity()).locateComplete(new LocatedCity(city, province, cityCode), LocateState.SUCCESS);
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

    }

    @Override
    public int getContent() {
        return R.layout.fragment_show_film;
    }

    @Override
    public void success(Object data) {
        if (data instanceof ShowFile_HotShopBean) {
            //热门
            ShowFile_HotShopBean hotShopBean = (ShowFile_HotShopBean) data;
            if (hotShopBean.getStatus().equals("0000")) {
                showFilm_hotShop_adapter.setMlist(hotShopBean.getResult());
            }
            setGet(String.format(Constant.NWESHOWING_Path, page, count), ShowFile_NewShowingBean.class);
        } else if (data instanceof ShowFile_NewShowingBean) {
            //正在上映
            ShowFile_NewShowingBean showFile_newShowingBean = (ShowFile_NewShowingBean) data;
            if (showFile_newShowingBean.getStatus().equals("0000")) {
                showFilm_newShowing_adapter.setLlist(showFile_newShowingBean.getResult());
            }
        } else if (data instanceof ShowFile_Banner_Info) {
            //即将上映
            ShowFile_Banner_Info showFile_banner_info = (ShowFile_Banner_Info) data;
            showFilm_coming_adapter.setClist(showFile_banner_info.getResult());
            ShowFile_Banner_Adapter showFile_banner_adapter = new ShowFile_Banner_Adapter(getContext(), showFile_banner_info.getResult());
            recycFlow.setAdapter(showFile_banner_adapter);

            recycFlow.smoothScrollToPosition(4);
            final ImageViewAnimationHelper imageViewAnimationHelper = new ImageViewAnimationHelper(getContext(), checkedLayout, 1, 31);
            recycFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
                @Override
                public void onItemSelected(int position) {
                    imageViewAnimationHelper.startAnimation(position);
                }
            });
            setGet(String.format(Constant.Hotfile_Path, page, count), ShowFile_HotShopBean.class);

        }
    }

    @Override
    public void fail(String error){
        showLog(error);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.hotAll_HotFile,R.id.area_places,R.id.hotAll_IsHot, R.id.hotAll_Coming,R.id.area_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hotAll_HotFile:
                Intent intent = new Intent(getActivity(), ShowFileAllActivity.class);
                intent.putExtra("flag", 1);
                startActivity(intent);
                break;
            case R.id.hotAll_IsHot:
                Intent intenti = new Intent(getActivity(), ShowFileAllActivity.class);
                intenti.putExtra("flag", 2);
                startActivity(intenti);
                break;
            case R.id.hotAll_Coming:
                Intent intentc = new Intent(getActivity(), ShowFileAllActivity.class);
                intentc.putExtra("flag", 3);
                startActivity(intentc);
                break;
            case R.id.area_places:
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                    String[] mStatenetwork = new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.CHANGE_WIFI_STATE,
                            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                            Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_ADMIN,
                    };
                    ActivityCompat.requestPermissions(getActivity(), mStatenetwork, 100);
                }
                CityPicker.from(getActivity()) //activity或者fragment
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
                                    CityPicker.from(getActivity()).locateComplete(new LocatedCity(city, province, cityCode), LocateState.SUCCESS);
                                }
                            }, 1000);
                        }
                    })
                    .show();
                break;
        }
    }

    private long exitTime=0;
    private void getFocus() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

                    //双击退出
                    if (System.currentTimeMillis() - exitTime > 2000) {

                        exitTime = System.currentTimeMillis();
                        showToast(getContext(),"再按一次退出程序");
                    } else {
                        getActivity().finish();
                        System.exit(0);
                    }
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getFocus();
    }

}
