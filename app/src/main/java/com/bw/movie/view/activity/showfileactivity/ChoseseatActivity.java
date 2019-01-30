package com.bw.movie.view.activity.showfileactivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.utils.SeatTable;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date:2019/1/28
 * author:孙佳鑫(DELL)
 * function: 选座
 */
public class ChoseseatActivity extends BaseActivity {

    @BindView(R.id.cinema_seat_table_text_beginTime)
    TextView mTextView_beginTime;

    @BindView(R.id.cinema_seat_table_text_endTime)
    TextView mTextView_endTime;
    double totalPrice = 0;

    public SeatTable seatTableView;
    @BindView(R.id.camera_name)
    TextView cameraName;
    @BindView(R.id.camera_details_name)
    TextView cameraDetailsName;
    @BindView(R.id.movie_name)
    TextView movieName;
    @BindView(R.id.movie_class)
    TextView movieClass;
    @BindView(R.id.seatView)
    SeatTable seatView;
    @BindView(R.id.movie_price)
    TextView moviePrice;
    @BindView(R.id.pay_success)
    ImageView paySuccess;
    @BindView(R.id.pay_error)
    ImageView payError;
    private double mPrice;

    @Override
    public void initView() {
        Intent intent=getIntent();
        mPrice = intent.getDoubleExtra("price", 0);
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);

        seatTableView = (SeatTable) findViewById(R.id.seatView);
        seatTableView.setScreenName("8号厅荧幕");//设置屏幕名称
        seatTableView.setMaxSelected(3);//设置最多选中

        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if (column == 2) {
                    return false;

                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if (row == 6 && column == 6) {
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                totalPrice+=mPrice;
                String totalprice = String.format("%.2f", totalPrice);
                moviePrice.setText(totalprice+"");

            }

            @Override
            public void unCheck(int row, int column) {
                totalPrice-=mPrice;
                String totalprice = String.format("%.2f", totalPrice);
                moviePrice.setText(totalprice+"");


            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(10, 15);

    }


    @Override
    public int getContent() {
        return R.layout.activity_choseseat;
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
