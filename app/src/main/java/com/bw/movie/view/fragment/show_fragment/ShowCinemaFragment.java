package com.bw.movie.view.fragment.show_fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.view.fragment.show_cinema_Fragment.Nearby_Fragment;
import com.bw.movie.view.fragment.show_cinema_Fragment.Recommend_Fragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
    @BindView(R.id.radio)
    RadioGroup radio;
    Unbinder unbinder;
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


}
