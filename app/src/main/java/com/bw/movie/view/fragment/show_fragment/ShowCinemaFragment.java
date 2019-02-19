package com.bw.movie.view.fragment.show_fragment;


import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.view.activity.showfileactivity.AreaActivity;
import com.bw.movie.view.fragment.show_cinema_Fragment.Nearby_Fragment;
import com.bw.movie.view.fragment.show_cinema_Fragment.Recommend_Fragment;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date:2019/1/25
 * author:刘洋洋(DELL)
 * function:影院页面
 */
public class ShowCinemaFragment extends BaseFragment {
    @BindView(R.id.nearby_cinema_view)
    ViewPager nearby_cinema_view;
    @BindView(R.id.but_recommend)
    RadioButton butRecommend;
    @BindView(R.id.but_nearby)
    RadioButton butNearby;
    @BindView(R.id.area_place)
    ImageView areaPlace;
    @BindView(R.id.area_name)
    TextView areaName;
    @BindView(R.id.radio)
    RadioGroup radio;
    private List<Fragment> list = new ArrayList<>();

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }
    @Override
    public void initData(View view) {
        list.add(new Recommend_Fragment());
        list.add(new Nearby_Fragment());
        nearby_cinema_view.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.but_recommend:
                        nearby_cinema_view.setCurrentItem(0);
                        break;
                    case R.id.but_nearby:
                        nearby_cinema_view.setCurrentItem(1);
                        break;

                }
            }
        });
        areaPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),AreaActivity.class));
            }
        });
        areaName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityPicker.from(getActivity()) //activity或者fragment
                        .enableAnimation(true)	//启用动画效果，默认无
                        .setLocatedCity(new LocatedCity("杭州", "浙江", "101210101"))
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

                                    }
                                }, 3000);
                            }
                        })
                        .show();
            }
        });
        nearby_cinema_view.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
              switch (i){
                  case 0:
                      radio.check(R.id.but_recommend);
                      break;
                  case 1:
                      radio.check(R.id.but_nearby);
                      break;
              }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public int getContent() {
        return R.layout.fragment_show_cinema;
    }

    @Override
    public void success(Object data) {

    }

    @Override
    public void fail(String error) {

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
                        showToast("再按一次退出程序");
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
