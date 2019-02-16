package com.bw.movie.view.activity.showfileactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.BuyBean;
import com.bw.movie.bean.PayBeanTwo;
import com.bw.movie.bean.PayTranDataBean;
import com.bw.movie.utils.Constant;
import com.bw.movie.utils.SeatTable;
import com.bw.movie.wxapi.WXPayEntryActivity;

import org.greenrobot.eventbus.EventBus;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

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
    private float mB;
    int num=0;
    private PopupWindow mPopupWindow;

    @Override
    public void initView() {

        ButterKnife.bind(this);

        seatTableView = (SeatTable) findViewById(R.id.seatView);

    }

    @Override
    public void initData() {

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
                num++;
                totalPrice += price;
                mB = (float) (Math.round(totalPrice * 100)) / 100;
                //String totalprice = String.format("%.2f", totalPrice);
                moviePrice.setText(mB + "");
            }

            @Override
            public void unCheck(int row, int column) {
                num--;
                totalPrice -= price;
                float mB = (float) (Math.round(totalPrice * 100)) / 100;
                //String totalprice = String.format("%.2f", totalPrice);
                moviePrice.setText(mB + "");
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
                if (num==0){
                    Toast.makeText(ChoseseatActivity.this,"请选择座位！",Toast.LENGTH_SHORT).show();
                }else {
                    SharedPreferences sjx = getSharedPreferences("UserMessage",MODE_PRIVATE);
                    String userId = sjx.getString("userId", "");
                    String sign=userId+mId+num+"movie";
                    //String jmSign = EncryptUtil.encrypt(sign);
                    String jmSign = MD5(sign);

                    Map<String, String> map = new HashMap<>();
                    map.put("scheduleId",mId+"");
                    map.put("amount",num+"");
                    map.put("sign",jmSign);

                    setPost(Constant.DingDan_Path,BuyBean.class,map);


                }

            }
        });
    }

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(ChoseseatActivity.this).inflate(R.layout.pay_popupwindow, null);

        mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        //显示PopupWindow

        View rootview = LayoutInflater.from(ChoseseatActivity.this).inflate(R.layout.pay_popupwindow, null);

        mPopupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

        RadioButton weixin_btn = contentView.findViewById(R.id.weixin_btn);
        RadioButton zhifubao_btn = contentView.findViewById(R.id.zhifubao_btn);
        Button pay_money = contentView.findViewById(R.id.pay_money);

        pay_money.setText("支付"+mB+"元");

    }

    @Override
    public int getContent() {
        return R.layout.activity_choseseat;
    }

    @Override
    public void success(Object data) {
        if (data instanceof BuyBean) {
            BuyBean buyBean = (BuyBean) data;
            String message = buyBean.getMessage();
            Toast.makeText(ChoseseatActivity.this, message, Toast.LENGTH_SHORT).show();

            if (message.equals("下单成功")) {  //下单成功则弹出支付
                String orderId = buyBean.getOrderId();
                PayTranDataBean dataBean = new PayTranDataBean(orderId, (int) totalPrice);

                Map<String, String> map = new HashMap<>();
                map.put("payType",1+"");
                map.put("orderId",orderId);
                setPost(Constant.Pay_Path,PayBeanTwo.class,map);

            }
        }
        if(data instanceof PayBeanTwo){
            PayBeanTwo payBeanTwo = (PayBeanTwo) data;
            Intent intent = new Intent(this,WXPayEntryActivity.class);
            intent.putExtra("paybean",payBeanTwo);
            startActivity(intent);
        }

    }

    @Override
    public void fail(String error) {

    }

    /**
     * MD5加密
     *
     * @param sourceStr
     * @return
     */
    public String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
