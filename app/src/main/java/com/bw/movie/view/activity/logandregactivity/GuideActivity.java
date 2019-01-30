package com.bw.movie.view.activity.logandregactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.view.fragment.guidepage_fragment.GuiderFourthFragment;
import com.bw.movie.view.fragment.guidepage_fragment.GuideFristFragment;
import com.bw.movie.view.fragment.guidepage_fragment.GuideSecondFragment;
import com.bw.movie.view.fragment.guidepage_fragment.GuideThirdFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * date:2019/1/24
 * author:刘洋洋(DELL)
 * function:引导页
 */
public class GuideActivity extends BaseActivity {
    @BindView(R.id.guide_pager)
    ViewPager guidePager;
    @BindView(R.id.group)
    RadioGroup group;
    private Unbinder bind;
    private List<Fragment> list;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    public void initView() {
        //绑定
        bind = ButterKnife.bind(this);
    }
    @Override
    public void initData() {
        list = new ArrayList<>();
        list.add(new GuideFristFragment());
        list.add(new GuideSecondFragment());
        list.add(new GuideThirdFragment());
        list.add(new GuiderFourthFragment());
        sharedPreferences = getSharedPreferences("number",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        guidePager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }
            @Override
            public int getCount() {
                return list.size();
            }
        });
        //滑动pager改变按钮
        guidePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        group.check(R.id.radio_fir);
                        break;
                    case 1:
                        group.check(R.id.radio_sen);
                        break;
                    case 2:
                        group.check(R.id.radio_thi);
                        break;
                    case 3:
                        editor.putInt("num",1);
                        editor.commit();
                        group.check(R.id.radio_fou);
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        //点击radioGroup
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_fir:
                        guidePager.setCurrentItem(0);
                        break;
                    case R.id.radio_sen:
                        guidePager.setCurrentItem(1);
                        break;
                    case R.id.radio_thi:
                        guidePager.setCurrentItem(2);
                        break;
                    case R.id.radio_fou:
                        guidePager.setCurrentItem(3);
                        break;
                    default:
                        break;
                }
            }
        });
        int num = sharedPreferences.getInt("num", 0);
        if (num > 0){
            Intent intent = new Intent(GuideActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public int getContent() {
        return R.layout.activity_guide;
    }

    @Override
    public void success(Object data) {

    }

    @Override
    public void fail(String error) {

    }
//解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
