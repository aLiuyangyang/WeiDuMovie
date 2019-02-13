package com.bw.movie.view.activity.showfileactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.view.activity.AreaActivity;
import com.bw.movie.view.fragment.allfile_fragment.ComingFragment;
import com.bw.movie.view.fragment.allfile_fragment.HotFileFragment;
import com.bw.movie.view.fragment.allfile_fragment.IsHotFragment;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
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
    private int flag;

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
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
        areaPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ShowFileAllActivity.this,AreaActivity.class);
                startActivity(intent1);
            }
        });
        areaName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityPicker.from(ShowFileAllActivity.this) //activity或者fragment
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
