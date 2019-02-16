package com.bw.movie.view.activity.showmineactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.FeedBackBean;
import com.bw.movie.utils.Constant;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * date:2019/2/14
 * author:孙佳鑫(孙佳鑫)
 * function:意见反馈
 */
public class OpinionActivity extends BaseActivity {


    @BindView(R.id.mine_edit)
    EditText mineEdit;
    @BindView(R.id.opin_submit)
    Button opinSubmit;
    private String mineEdits;
    @BindView(R.id.film_details_back)
    ImageView filmDetailsBack;

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        filmDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int getContent() {
        return R.layout.activity_opinion;
    }

    @Override
    public void success(Object data) {
        if (data instanceof FeedBackBean) {
            FeedBackBean feedBackBean = (FeedBackBean) data;
            if (feedBackBean.getStatus().equals("0000")) {
                Intent intent = new Intent(this, OpinSuccessActivity.class);
                startActivity(intent);
                finish();
            } else {
                showToast(feedBackBean.getMessage());
            }
        }
    }
    @Override
    public void fail(String error) {

    }

    @OnClick({R.id.opin_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.opin_submit:
                mineEdits = mineEdit.getText().toString().trim();
                if (mineEdits.equals("")) {
                    showToast("请填写您的意见");
                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put("content", mineEdits);
                    setPost(Constant.RecordFeedBack_Path, FeedBackBean.class, map);
                    break;
                }
        }
    }
}
