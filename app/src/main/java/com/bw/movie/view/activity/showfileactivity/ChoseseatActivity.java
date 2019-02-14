package com.bw.movie.view.activity.showfileactivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.utils.SeatTable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChoseseatActivity extends BaseActivity {

    @BindView(R.id.cinema_seat_table_text_beginTime)
    TextView mTextView_beginTime;
    @BindView(R.id.cinema_seat_table_text_endTime)
    TextView mTextView_endTime;
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
    private int movieId;//影片id
    private int cinemasId;//影院id
    public SeatTable seatTableView;
    private int mId;
    private String mScreeningHall;
    private double price;
    double totalPrice = 0;
    private PopupWindow mPopupWindow;

    @Override
    public void initView() {
        seatTableView = (SeatTable) findViewById(R.id.seatView);

    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        final Intent intent1 = getIntent();
        String name = intent1.getStringExtra("name");
        String resultName = intent1.getStringExtra("resultName");
        String address = intent1.getStringExtra("address");
        movieId = intent1.getIntExtra("movieId", 0);
        cinemasId = intent1.getIntExtra("cinemasId", 0);
        mId = intent1.getIntExtra("Id", 0);
        price = intent1.getDoubleExtra("price", 0);
        String scheduleTimeStart = intent1.getStringExtra("scheduleTimeStart");
        String scheduleTimeEnd = intent1.getStringExtra("scheduleTimeEnd");
        String schedulePlayHall = intent1.getStringExtra("schedulePlayHall");
        mTextView_beginTime.setText(scheduleTimeStart);
        mTextView_endTime.setText(scheduleTimeEnd);
        movieClass.setText(schedulePlayHall);
        cameraName.setText(name);
        cameraDetailsName.setText(address);
        movieName.setText(resultName);
        seatTableView.setScreenName(schedulePlayHall);//设置屏幕名称
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
                totalPrice += price;
                float  b   =  (float)(Math.round(totalPrice*100))/100;
                //String totalprice = String.format("%.2f", totalPrice);
                moviePrice.setText(b + "");
            }

            @Override
            public void unCheck(int row, int column) {
                totalPrice -= price;
                float  b   =  (float)(Math.round(totalPrice*100))/100;
                //String totalprice = String.format("%.2f", totalPrice);
                moviePrice.setText(b + "");
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(10, 15);

        payError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        paySuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });
    }

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(ChoseseatActivity.this).inflate(R.layout.pay_popupwindow, null);

        mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        //显示PopupWindow

        View rootview = LayoutInflater.from(ChoseseatActivity.this).inflate(R.layout.pay_popupwindow, null);

        mPopupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
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
    }

}
