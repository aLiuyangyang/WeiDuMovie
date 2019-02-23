package com.bw.movie.view.fragment.show_mine_Fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.alipay.sdk.app.PayTask;
import com.bw.movie.R;
import com.bw.movie.adapter.showmine_adapter.MineCareCinemaRecyAdapter;
import com.bw.movie.adapter.showmine_adapter.MineObligationRecyAdapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.AliBean;
import com.bw.movie.bean.ObligationBean;
import com.bw.movie.bean.PayBeanTwo;
import com.bw.movie.bean.PayResult;
import com.bw.movie.utils.Constant;
import com.bw.movie.view.activity.showfileactivity.ChoseseatActivity;
import com.bw.movie.wxapi.WXPayEntryActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ObligationFragment extends BaseFragment {
    @BindView(R.id.film_details_back)
    ImageView filmDetailsBack;
    @BindView(R.id.mine_obliagtion_recy)
    RecyclerView mine_obliagtion_recy;
    @BindView(R.id.search_none)
    RelativeLayout search_none;
    private MineObligationRecyAdapter mineObligationRecyAdapter;
    private int page=1;//第几页
    private int count=10;//每页显示数
    private int status=1;//状态
    private PopupWindow mPopupWindow;
    private RadioButton mWeixin_btn;
    private Button mPay_money;
    private RadioButton mZhifubao_btn;
    private List<ObligationBean.ResultBean> mResult1;

    private final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        //showAlert(PayDemoActivity.this, getString(R.string.pay_success) + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        //showAlert(PayDemoActivity.this, getString(R.string.pay_failed) + payResult);
                    }
                    break;
                }
            }
        }
    };

    @Override
    public void initView(View view) {
        ButterKnife.bind(this,view);
    }

    @Override
    public void initData(View view) {
        filmDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        mine_obliagtion_recy.setLayoutManager(linearLayoutManager);
        mineObligationRecyAdapter=new MineObligationRecyAdapter(getActivity());
        mine_obliagtion_recy.setAdapter(mineObligationRecyAdapter);

        mineObligationRecyAdapter.setOnClickOrderId(new MineObligationRecyAdapter.OnClickOrderId() {

            private String mOrderId1;

            @Override
            public void successed(String orderId,double price) {
                mOrderId1 = orderId;
                View contentView = LayoutInflater.from(getContext()).inflate(R.layout.pay_popupwindow, null);
                mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                //显示PopupWindow
                View rootview = LayoutInflater.from(getContext()).inflate(R.layout.pay_popupwindow, null);
                mPopupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

                mWeixin_btn = contentView.findViewById(R.id.weixin_btn);
                mZhifubao_btn = contentView.findViewById(R.id.zhifubao_btn);
                mPay_money = contentView.findViewById(R.id.pay_money);
                mPay_money.setText("支付"+price+"元");

                mPay_money.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mWeixin_btn.isChecked()){
                            Map<String, String> map = new HashMap<>();
                            map.put("payType",1+"");
                            map.put("orderId",mOrderId1);
                            setPost(Constant.Pay_Path,PayBeanTwo.class,map);
                        }else if(mZhifubao_btn.isChecked()){
                            Map<String, String> map = new HashMap<>();
                            map.put("payType",2+"");
                            map.put("orderId",mOrderId1);
                            setPost(Constant.Pay_Path,AliBean.class,map);
                            mPopupWindow.dismiss();
                        }
                    }
                });



            }

        });


    }
    @Override
    public void onResume() {
        super.onResume();
        setGet(String.format(Constant.BuyTicketRecord_Path,page,count,status),ObligationBean.class);
    }
    @Override
    public int getContent() {
        return R.layout.fragment_ticket;
    }

    @Override
    public void success(Object data) {
        if (data instanceof ObligationBean){
            ObligationBean obligationBean= (ObligationBean) data;
            mResult1 = obligationBean.getResult();
            if (obligationBean.getStatus().equals("0000")){
                List<ObligationBean.ResultBean> result = obligationBean.getResult();
                if (result.size()==0){
                    search_none.setVisibility(View.VISIBLE);
                }else {
                    mineObligationRecyAdapter.setList(result);
                }
            }
        }else if(data instanceof PayBeanTwo){
            final PayBeanTwo payBeanTwo = (PayBeanTwo) data;
            Intent intent = new Intent(getContext(),WXPayEntryActivity.class);
            intent.putExtra("paybean",payBeanTwo);
            startActivity(intent);
        }else if(data instanceof AliBean){
            AliBean aliBean= (AliBean) data;
            final String orderIndo = aliBean.getResult();
            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    PayTask alipay = new PayTask(getActivity());
                    Map <String,String> result = alipay.payV2(orderIndo,true);
                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };
            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }
    }



    @Override
    public void fail(String error) {

    }

}
