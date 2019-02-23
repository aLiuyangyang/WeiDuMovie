package com.bw.movie.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.PayBeanTwo;
import com.bw.movie.utils.MyApp;
import com.bw.movie.utils.WeiXinUtil;
import com.bw.movie.view.activity.showcinemaactivity.FailuerActivity;
import com.bw.movie.view.activity.showcinemaactivity.SuccessActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    private IWXAPI api;
    PayReq request = new PayReq();
    private PayBeanTwo payBeanTwo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.pay_result);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        payBeanTwo = (PayBeanTwo) intent.getSerializableExtra("paybean");
     //   float price = intent.getFloatExtra("price", 0);
        //payResultPayprice.setText(price+"元");
        api = WXAPIFactory.createWXAPI(this, WeiXinUtil.APP_ID);
        api.handleIntent(getIntent(), this);
        //
        request.appId = payBeanTwo.getAppId();
        request.partnerId  = payBeanTwo.getPartnerId();
        request.prepayId = payBeanTwo.getPrepayId();
        request.packageValue  = payBeanTwo.getPackageValue();
        request.nonceStr = payBeanTwo.getNonceStr();
        request.timeStamp = payBeanTwo.getTimeStamp();
        request.sign = payBeanTwo.getSign();
        api.sendReq(request);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Log.i("TAG",  resp.getType()+"");
        //用户已取消
        if(resp.getType()==ConstantsAPI.COMMAND_PAY_BY_WX){
            if (resp.errCode == -2) {
               // ToastUtil.getInstance().setToastUtil("用户已取消");
               // EventBus.getDefault().postSticky(new CommonBean("pay_no","0001"));
                Intent intent=new Intent(this,FailuerActivity.class);
                startActivity(intent);
                finish();
                Log.i("TAG",resp.getType()+"");
            }else if (resp.errCode == 0) {
               // ToastUtil.getInstance().setToastUtil("支付成功");
                //EventBus.getDefault().postSticky(new CommonBean("pay_ok","0000"));
                Intent intent=new Intent(this,SuccessActivity.class);
                startActivity(intent);
                finish();
            }else if (resp.errCode == -1) {
                Intent intent=new Intent(this,FailuerActivity.class);
                startActivity(intent);
                finish();
                //ToastUtil.getInstance().setToastUtil("支付错误,请稍后重试");
                finish();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
    }
}