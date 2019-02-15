package com.bw.movie.view.activity.showmineactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.ObligationBean;
import com.bw.movie.view.fragment.show_mine_Fragment.CinemaFragment;
import com.bw.movie.view.fragment.show_mine_Fragment.FilmFragment;
import com.bw.movie.view.fragment.show_mine_Fragment.ObligationFragment;
import com.bw.movie.view.fragment.show_mine_Fragment.StocksFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TicketActivity extends BaseActivity {

    @BindView(R.id.ticket_obligation)
    RadioButton ticketObligation;
    @BindView(R.id.ticket_stocks)
    RadioButton ticketStocks;
    @BindView(R.id.ticket_radio)
    RadioGroup ticketRadio;
    @BindView(R.id.ticket_viewpager)
    ViewPager ticketViewpager;
    private List<Fragment> list;
    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        list=new ArrayList<>();
        list.add(new ObligationFragment());
        list.add(new StocksFragment());
        ticketViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        ticketRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.ticket_obligation:
                        ticketViewpager.setCurrentItem(0);
                        break;
                    case R.id.ticket_stocks:
                        ticketViewpager.setCurrentItem(1);
                        break;

                }
            }
        });
        ticketViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        ticketRadio.check(R.id.ticket_obligation);
                        break;
                    case 1:
                        ticketRadio.check(R.id.ticket_stocks);
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
        return R.layout.activity_ticket;
    }

    @Override
    public void success(Object data) {
    }

    @Override
    public void fail(String error) {

    }
}
