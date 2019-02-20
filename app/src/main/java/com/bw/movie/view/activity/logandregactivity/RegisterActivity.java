package com.bw.movie.view.activity.logandregactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.utils.Constant;
import com.bw.movie.utils.EncryptUtil;
import com.bw.movie.utils.RegularUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * date:2019/1/25
 * author:刘洋洋(DELL)
 * function:注册页面
 */
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.reg_name)
    EditText regName;
    @BindView(R.id.reg_sexman)
    RadioButton reg_sexman;
    @BindView(R.id.reg_sexwoman)
    RadioButton reg_sexwoman;
    @BindView(R.id.reg_date)
    TextView regDate;
    @BindView(R.id.reg_phone)
    EditText regPhone;
    @BindView(R.id.reg_emil)
    EditText regEmil;
    @BindView(R.id.reg_pass)
    EditText regPass;
    @BindView(R.id.reg_but)
    Button regBut;
    private Unbinder bind;
    private String mPhone,mPass;
    private  int regSex;//性别
    private SharedPreferences sharedPreferences;//存储
    private SharedPreferences.Editor edit;
    @Override
    public void initView() {
        bind = ButterKnife.bind(this);
        sharedPreferences=getSharedPreferences("UserMessage",MODE_PRIVATE);
        edit=sharedPreferences.edit();
    }
    @Override
    public void initData() { }
    @Override
    public int getContent() {
        return R.layout.activity_register;
    }
    @Override
    public void success(Object data) {
        if (data instanceof RegisterBean) {
            RegisterBean registerBean = (RegisterBean) data;
            if (registerBean.getStatus().equals("0000")) {
                //注册成功之后到首页
                showToast(registerBean.getMessage());
                Map<String, String> map = new HashMap<>();
                map.put("phone", mPhone);
                map.put("pwd", EncryptUtil.encrypt(mPass));
                setPost(Constant.Login_Path, LoginBean.class, map);

            } else {
                showToast(registerBean.getMessage());
            }
        }else if(data instanceof LoginBean){
            LoginBean loginBean= (LoginBean) data;
            if(loginBean.getStatus().equals("0000")){
                edit.putString("sessionId",loginBean.getResult().getSessionId());
                edit.putString("userId",loginBean.getResult().getUserId()+"");
                edit.putBoolean("isUser",true);
                edit.commit();
                Intent intent=new Intent(this,ShowActivity.class);
                startActivity(intent);
            }

        }
    }
    @Override
    public void fail(String error) {
       showLog(error);
    }
    @OnClick({R.id.reg_date, R.id.reg_but})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reg_date:
                //时间
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(RegisterActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                Calendar startDate = Calendar.getInstance();
                Calendar endDate = Calendar.getInstance();
                calendar.set(year-20,month,day);
                startDate.set(year-100,0,1);
                endDate.set(year,month,day);
                TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String time = sDateFormat.format(date);
                        regDate.setText(time+"");
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})
                        // 默认全部显示
                        .setCancelText("取消")
                        //取消按钮文字
                        .setSubmitText("确定")
                        //确认按钮文字
                        .setOutSideCancelable(true)
                        //点击屏幕，点在控件外部范围时，是否取消显示
                        .setRangDate(startDate,endDate)
                        //起始终止年月日设定
                        .setDate(calendar)
                        .isCenterLabel(false)
                        //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .build();
                pvTime.show();

                break;
            case R.id.reg_but:
                Map<String,String> map=new HashMap<>();
                String mName = regName.getText().toString().trim();
                if (reg_sexman.isChecked()){
                    regSex=1;
                }else if (reg_sexwoman.isChecked()){
                    regSex=2;
                }
                String mDtae = regDate.getText().toString().trim();
                mPhone = regPhone.getText().toString().trim();
                String mEmil = regEmil.getText().toString().trim();
                mPass = regPass.getText().toString().trim();
                if (mName.equals("")||mDtae.equals("")|| mPhone.equals("")||mEmil.equals("")|| mPass.equals("")){
                    showToast("请填写完整");
                }else if (!RegularUtil.isMobile(mPhone)){
                    showToast("请填写正确的手机号");
                }else if (!RegularUtil.isEmail(mEmil)){
                    showToast("请填写正确的邮箱格式");
                }else {
                    map.put("nickName",mName);
                    map.put("phone", mPhone);
                    map.put("pwd",EncryptUtil.encrypt(mPass));//加密密码
                    map.put("pwd2",EncryptUtil.encrypt(mPass));//加密密码
                    map.put("sex",regSex+"");
                    map.put("birthday",mDtae);
                    map.put("email",mEmil);
                    setPost(Constant.Register_Path,RegisterBean.class,map);
                }
               /* Intent intent=new Intent(this,ShowActivity.class);
                startActivity(intent);*/
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
