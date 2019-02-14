package com.bw.movie.view.activity.showmineactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.UpdatePassBean;
import com.bw.movie.utils.Constant;
import com.bw.movie.utils.EncryptUtil;
import com.bw.movie.view.activity.logandregactivity.LoginActivity;
import com.bw.movie.view.activity.logandregactivity.RegisterActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdatePassActivity extends BaseActivity {

    @BindView(R.id.personal_pass)
    EditText personalPass;
    @BindView(R.id.personal_resetPass)
    EditText personalResetPass;
    @BindView(R.id.personal_okPass)
    EditText personalOkPass;
    @BindView(R.id.myokpass)
    Button myokpass;
    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
       myokpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        String pass = personalPass.getText().toString().trim();
        String resetPass = personalResetPass.getText().toString().trim();
        String okPass = personalOkPass.getText().toString().trim();
        if (resetPass.equals("") && okPass.equals("")&&pass.equals("")){
            showToast("请填写完整");
        }else {
            Map<String, String> map = new HashMap<>();
            map.put("oldPwd", EncryptUtil.encrypt(pass));
            map.put("newPwd", EncryptUtil.encrypt(resetPass));//加密密码
            map.put("newPwd2", EncryptUtil.encrypt(okPass));
            setPost(Constant.UpdataPass_Path, UpdatePassBean.class, map);
        }
            }
        });
    }

    @Override
    public int getContent() {
        return R.layout.activity_update_pass;
    }

    @Override
    public void success(Object data) {
       if (data instanceof UpdatePassBean){
           UpdatePassBean updatePassBean= (UpdatePassBean) data;
           if (updatePassBean.getStatus().equals("0000")){
               showToast(updatePassBean.getMessage());
               Intent intent=new Intent(this,LoginActivity.class);
               startActivity(intent);
               finish();
           }else {
               showToast(updatePassBean.getMessage());
           }
       }
    }

    @Override
    public void fail(String error) {
     showToast(error);
    }
}
