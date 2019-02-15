package com.bw.movie.view.fragment.show_fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.PresonalMessageBean;
import com.bw.movie.utils.Constant;
import com.bw.movie.view.activity.showmineactivity.CareActivity;
import com.bw.movie.view.activity.showmineactivity.Presonal_Message_Activity;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * date:2019/1/24
 * author:刘洋洋(DELL)
 * function:我的页面
 */
public class ShowMineFragment extends BaseFragment {

    @BindView(R.id.personal_meassage)
    RelativeLayout personalMeassage;
    Unbinder unbinder;
    @BindView(R.id.my_care)
    RelativeLayout my_care;
    @BindView(R.id.personal_record_attention)
    ImageView personalRecordAttention;
    @BindView(R.id.personal_top_image)
    SimpleDraweeView personal_top_image;
    @BindView(R.id.personal_name)
    TextView personal_name;
    Unbinder unbinder1;
    @BindView(R.id.personal_image)
    ImageView personalImage;
    @BindView(R.id.personal_message_item)
    ImageView personalMessageItem;
    @BindView(R.id.personal_mine_message)
    RelativeLayout personalMineMessage;
    @BindView(R.id.personal_ticket_record)
    ImageView personalTicketRecord;
    @BindView(R.id.personal_record)
    LinearLayout personalRecord;
    @BindView(R.id.personal_advice_feedback)
    ImageView personalAdviceFeedback;
    @BindView(R.id.personal_latest_version)
    ImageView personalLatestVersion;
    @BindView(R.id.personal_log_out)
    ImageView personalLogOut;
    @BindView(R.id.personal_feedback)
    LinearLayout personalFeedback;
    Unbinder unbinder2;

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void initData(View view) {
        setGet(Constant.Update_User, PresonalMessageBean.class);
    }

    @Override
    public int getContent() {
        return R.layout.fragment_show_mine;
    }

    @OnClick({R.id.personal_meassage, R.id.my_care, R.id.personal_log_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.personal_meassage:
                Intent intent = new Intent(getActivity(), Presonal_Message_Activity.class);
                startActivity(intent);
                break;
            case R.id.my_care:
                Intent intentcare = new Intent(getActivity(), CareActivity.class);
                startActivity(intentcare);
                break;
        }
    }

    @Override
    public void success(Object data) {
        if (data instanceof PresonalMessageBean) {
            PresonalMessageBean presonalMessageBean = (PresonalMessageBean) data;
            if (presonalMessageBean.getStatus().equals("0000")) {
                Uri parse = Uri.parse(presonalMessageBean.getResult().getHeadPic());
                personal_top_image.setImageURI(parse);
                personal_name.setText(presonalMessageBean.getResult().getNickName());
            }
        }
    }

    @Override
    public void fail(String error) {

    }

    private long exitTime = 0;

    private void getFocus() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

                    //双击退出
                    if (System.currentTimeMillis() - exitTime > 2000) {

                        exitTime = System.currentTimeMillis();
                        showToast("再按一次退出程序");
                    } else {
                        getActivity().finish();
                        System.exit(0);
                    }
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getFocus();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder2 = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
