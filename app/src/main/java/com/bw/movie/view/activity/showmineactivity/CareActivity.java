package com.bw.movie.view.activity.showmineactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.view.fragment.show_cinema_Fragment.Nearby_Fragment;
import com.bw.movie.view.fragment.show_cinema_Fragment.Recommend_Fragment;
import com.bw.movie.view.fragment.show_mine_Fragment.CinemaFragment;
import com.bw.movie.view.fragment.show_mine_Fragment.FilmFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CareActivity extends BaseActivity {

    @BindView(R.id.mine_care_film)
    RadioButton mineCareFilm;
    @BindView(R.id.mine_care_cinema)
    RadioButton mineCareCinema;
    @BindView(R.id.mine_care_radio)
    RadioGroup mineCareRadio;
    @BindView(R.id.mine_care_viewpager)
    ViewPager mineCareViewpager;
    private List<Fragment> list;
    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
       list=new ArrayList<>();
        list.add(new FilmFragment());
        list.add(new CinemaFragment());
        mineCareViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        mineCareRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.mine_care_film:
                        mineCareViewpager.setCurrentItem(0);
                        break;
                    case R.id.mine_care_cinema:
                        mineCareViewpager.setCurrentItem(1);
                        break;

                }
            }
        });
        mineCareViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        mineCareRadio.check(R.id.mine_care_film);
                        break;
                    case 1:
                        mineCareRadio.check(R.id.mine_care_cinema);
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
        return R.layout.activity_care;
    }

    @Override
    public void success(Object data) {

    }

    @Override
    public void fail(String error) {

    }
}
