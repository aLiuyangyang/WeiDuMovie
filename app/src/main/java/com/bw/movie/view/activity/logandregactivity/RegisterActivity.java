package com.bw.movie.view.activity.logandregactivity;

import android.content.Context;
import android.content.Intent;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
    @BindView(R.id.reg_sex)
    EditText regSex;
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
    private String mSex;

    @Override
    public void initView() {
        bind = ButterKnife.bind(this);
    }

    @Override
    public void initData() {

    }

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
                Intent intent=new Intent(this,ShowActivity.class);
                startActivity(intent);
                finish();
            } else {
                showToast(registerBean.getMessage());
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
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(RegisterActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                Calendar startDate = Calendar.getInstance();
                Calendar endDate = Calendar.getInstance();

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
                        .isCenterLabel(false)
                        //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .build();
                pvTime.show();

                break;
            case R.id.reg_but:
                Map<String,String> map=new HashMap<>();
                String mName = regName.getText().toString().trim();
                mSex = regSex.getText().toString().trim();
                String mDtae = regDate.getText().toString().trim();
                mPhone = regPhone.getText().toString().trim();
                String mEmil = regEmil.getText().toString().trim();
                mPass = regPass.getText().toString().trim();
                int sex = isSex(mSex);
                if (mName.equals("")|| mSex.equals("")||mDtae.equals("")|| mPhone.equals("")||mEmil.equals("")|| mPass.equals("")){
                    showToast("请填写完整");
                }else if (!RegularUtil.isMobile(mPhone)){
                    showToast("请填写正确的手机号");
                }else if (!RegularUtil.isEmail(mEmil)){
                    showToast("请填写正确的邮箱格式");
                }else if (!RegularUtil.isPassword(mPass)){
                    showToast("密码不能有特殊符号");
                } else {
                    map.put("nickName",mName);
                    map.put("phone", mPhone);
                    map.put("pwd",EncryptUtil.encrypt(mPass));//加密密码
                    map.put("pwd2",EncryptUtil.encrypt(mPass));//加密密码
                    map.put("sex",sex+"");
                    map.put("birthday",mDtae);
                    map.put("email",mEmil);
                    setPost(Constant.Register_Path,RegisterBean.class,map);
                }
                break;
        }
    }

    private int isSex(String sex) {
        if (sex.equals("男")){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
