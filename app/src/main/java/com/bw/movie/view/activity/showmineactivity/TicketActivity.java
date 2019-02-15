package com.bw.movie.view.activity.showmineactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;

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
