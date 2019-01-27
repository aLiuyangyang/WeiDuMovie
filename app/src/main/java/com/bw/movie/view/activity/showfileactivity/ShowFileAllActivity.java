package com.bw.movie.view.activity.showfileactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.view.fragment.allfile_fragment.ComingFragment;
import com.bw.movie.view.fragment.allfile_fragment.HotFileFragment;
import com.bw.movie.view.fragment.allfile_fragment.IsHotFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowFileAllActivity extends BaseActivity {
    @BindView(R.id.all_but_hotfile)
    RadioButton allButHotfile;
    @BindView(R.id.all_but_ishot)
    RadioButton allButIshot;
    @BindView(R.id.all_but_Coming)
    RadioButton allButComing;
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
        Intent intent = getIntent();
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
