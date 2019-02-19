package com.bw.movie.view.fragment.show_cinema_Fragment;

import android.view.View;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.CinemaPopupDetailsBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 范瑞旗
 * Date: 2019/1/27 13:32
 * Description: 影院弹框详情-Fragment
 */
public class CinemaPopupDetailFragment extends BaseFragment {

    @BindView(R.id.cinema_pw_text_position)
    TextView mTextView_address;

    @BindView(R.id.cinema_pw_text_phone)
    TextView mTextView_phone;

    @BindView(R.id.cinema_pw_text_underGround)
    TextView mTextView_underGround;

    @BindView(R.id.cinema_pw_text_bus)
    TextView mTextView_bus;

    @BindView(R.id.cinema_pw_text_self_driving)
    TextView mTextView_self_driving;

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData(View view) {
        ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void sjx(CinemaPopupDetailsBean detailsBean){

        mTextView_address.setText(detailsBean.getAddress());
        mTextView_phone.setText(detailsBean.getPhone());

        String vehicleRoute = detailsBean.getVehicleRoute();

        /*Pattern pattern = Pattern.compile("\\。");
        String[] img = pattern.split(vehicleRoute);*/

        mTextView_underGround.setText("雍和宫桥东、北小街豁口(二环辅)、北小街豁口、雍和宫桥北、雍和宫桥东(西行)");
        mTextView_bus.setText("44路、75路、特12路、特2路、13路、18路、116路、130路、684路、117路、机场巴士2线、909路、125路、674路、特16路、地铁2号线、地铁5号线、612路等.");
        mTextView_self_driving.setText("北京市出租车的起步价是13.0元、起步距离3.0公里、 每公里2.3元、燃油附加费1.0元（不超过3.0公里不收） ，请参考。");

    }

    @Override
    public int getContent() {
        return R.layout.fragment_cinema_detail_popup_detail;
    }

    @Override
    public void success(Object data) {

    }

    @Override
    public void fail(String error) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
