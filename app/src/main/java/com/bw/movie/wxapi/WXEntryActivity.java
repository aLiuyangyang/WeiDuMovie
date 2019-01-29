package com.bw.movie.wxapi;

import android.content.Intent;
import android.content.SharedPreferences;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.WeixinBean;
import com.bw.movie.utils.Constant;
import com.bw.movie.utils.WeiXinUtil;
import com.bw.movie.view.activity.logandregactivity.ShowActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.util.HashMap;
import java.util.Map;


public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    public static String code;
    private SharedPreferences.Editor editor;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
//        WeiXinUtil.reg(WXEntryActivity.this).handleIntent(getIntent(),this);
        WeiXinUtil.reg(WXEntryActivity.this).handleIntent(getIntent(),this);
    }

    @Override
    public int getContent() {
        return R.layout.activity_wxentry;
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(final BaseResp baseResp) {
        switch (baseResp.errCode){
            case BaseResp.ErrCode.ERR_OK:
                //主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //得到code
                        code = ((SendAuth.Resp) baseResp).code;
                        showLog(code);
                        Map<String,String> map = new HashMap<>();
                        map.put("code",code);
                        setPost(Constant.Weixin_Path, WeixinBean.class, map);
                    }
                });
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:

                break;
        }
    }
    @Override
    public void success(Object data) {
        if (data instanceof WeixinBean){
            WeixinBean bean = (WeixinBean) data;
            if (((WeixinBean) data).getStatus().equals("0000")){
                /*int userId = bean.getResult().getUserId();
                String sessionId = bean.getResult().getSessionId();
                editor.putString("userId", userId+"");
                editor.putString("sessionId", sessionId);
                editor.commit();*/

                Intent intent = new Intent(WXEntryActivity.this,ShowActivity.class);
                startActivity(intent);
                finish();
            }

        }
    }

    @Override
    public void fail(String error) {

    }
}
