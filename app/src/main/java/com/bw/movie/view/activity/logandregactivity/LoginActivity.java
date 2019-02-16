package com.bw.movie.view.activity.logandregactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.utils.Constant;
import com.bw.movie.utils.CustomClickListener;
import com.bw.movie.utils.EncryptUtil;
import com.bw.movie.utils.Loading_view;
import com.bw.movie.utils.RegularUtil;
import com.bw.movie.utils.WeiXinUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
/**
 * date:2019/1/24
 * author:刘洋洋(DELL)
 * function:登录页面
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_phone)
    EditText loginPhone;
    @BindView(R.id.login_pwd)
    EditText loginPwd;
    @BindView(R.id.login_eye)
    ImageView loginEye;
    @BindView(R.id.login_auto)
    CheckBox loginAuto;
    @BindView(R.id.login_sign)
    TextView loginSign;
    @BindView(R.id.login_but)
    Button loginBut;
    @BindView(R.id.login_weixin)
    ImageView loginWeixin;
    @BindView(R.id.login_remember)
    CheckBox loginRemember;
    private Unbinder bind;
    private SharedPreferences sharedPreferences;//存储
    private SharedPreferences.Editor edit;

    @Override
    public void initView() {
        bind = ButterKnife.bind(this);//控件绑定
    }
    @Override
    public void initData() {
        sharedPreferences=getSharedPreferences("UserMessage",MODE_PRIVATE);
        edit = sharedPreferences.edit();
        //点击眼睛显示隐藏
        loginEye.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        loginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        break;
                    case MotionEvent.ACTION_DOWN:
                        loginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        break;
                }
                return true;
            }
        });
        //记住密码
        boolean rem_check = sharedPreferences.getBoolean("Rem_check", false);
        if (rem_check){
            String mPhone = sharedPreferences.getString("mPhone",null);
            String mPwd = sharedPreferences.getString("mPwd", null);
            loginPhone.setText(mPhone);
            loginPwd.setText(mPwd);
            loginRemember.setChecked(true);
        }
      //自动登录
        boolean auto_check = sharedPreferences.getBoolean("Auto_check", false);
        if (auto_check){
            String mPhone = loginPhone.getText().toString().trim();
            String mPwd = loginPwd.getText().toString().trim();
            loginRemember.setChecked(true);
            Map<String, String> map = new HashMap<>();
            map.put("phone", mPhone);
            map.put("pwd", EncryptUtil.encrypt(mPwd));
            setPost(Constant.Login_Path, LoginBean.class, map);
        }
        //自动登录勾选之后自己密码也勾选
        loginAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (loginAuto.isChecked()){
                    loginRemember.setChecked(true);
                }else{
                    edit.clear();
                    edit.commit();
                }
            }
        });

        loginBut.setOnClickListener(new CustomClickListener() {
            @Override
            protected void onSingleClick() {
                String mPhone = loginPhone.getText().toString().trim();
                String mPwd = loginPwd.getText().toString().trim();
                if (loginRemember.isChecked()) {
                    edit.putString("mPhone", mPhone);
                    edit.putString("mPwd", mPwd);
                    edit.putBoolean("Rem_check", true);
                    edit.commit();
                } else {
                    edit.clear();
                    edit.commit();
                }
                if (loginAuto.isChecked()) {
                    edit.putBoolean("Auto_check", true);
                    edit.commit();
                }
                if (mPhone.equals("") && mPwd.equals("")) {
                    showToast("手机号或密码不能为空");
                } else if (!RegularUtil.isMobile(mPhone)) {
                    showToast("手机号格式错误");
                }else {
                    Map<String, String> map = new HashMap<>();
                    map.put("phone", mPhone);
                    map.put("pwd", EncryptUtil.encrypt(mPwd));//加密密码
                    setPost(Constant.Login_Path, LoginBean.class, map);
                }
            }
            @Override
            protected void onFastClick() {
                showLog("onFastClick");
            }
        });
    }

    @Override
    public int getContent() {
        return R.layout.activity_login;
    }
    @Override public void success(Object data) {
      if (data instanceof LoginBean){
          LoginBean loginBean= (LoginBean) data;
          if (loginBean.getStatus().equals("0000")){
              showToast(loginBean.getMessage());
              edit.putString("sessionId",loginBean.getResult().getSessionId());
              edit.putString("userId",loginBean.getResult().getUserId()+"");
              edit.commit();
              Intent intent=new Intent(this,ShowActivity.class);
              startActivity(intent);
              finish();
          }else {
              showToast(loginBean.getMessage());
          }
      }
    }

    @Override
    public void fail(String error) {
       showLog(error);
    }
    @OnClick({R.id.login_sign, R.id.login_but,R.id.login_weixin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //跳转到注册页面
            case R.id.login_sign:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.login_weixin:
                //微信登录
                if (!WeiXinUtil.success(this)) {
                    showToast("请先安装应用");
                } else {
                    //  验证
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    WeiXinUtil.reg(this).sendReq(req);
                }
                break;
            }

    }

    @Override
    protected void onDestroy() {//解绑
        super.onDestroy();
        bind.unbind();
    }



}
