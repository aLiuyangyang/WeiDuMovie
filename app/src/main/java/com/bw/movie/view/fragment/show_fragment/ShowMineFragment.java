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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.PresonalMessageBean;
import com.bw.movie.bean.SignBean;
import com.bw.movie.utils.Constant;
import com.bw.movie.view.activity.showmineactivity.CareActivity;
import com.bw.movie.view.activity.showmineactivity.OpinionActivity;
import com.bw.movie.view.activity.showmineactivity.Presonal_Message_Activity;
import com.bw.movie.view.activity.showmineactivity.TicketActivity;
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
    @BindView(R.id.mine_sign)
    TextView mineSign;
    @BindView(R.id.rela_ticket)
    RelativeLayout rela_ticket;
    @BindView(R.id.my_Opinion)
    RelativeLayout  my_Opinion;
    @BindView(R.id.my_care)
    RelativeLayout my_care;
    @BindView(R.id.mine_retuen)
    RelativeLayout mine_retuen;
    @BindView(R.id.personal_record_attention)
    ImageView personalRecordAttention;
    @BindView(R.id.personal_top_image)
    SimpleDraweeView personal_top_image;
    @BindView(R.id.personal_name)
    TextView personal_name;

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
    @OnClick({R.id.personal_meassage,R.id.my_care,R.id.mine_sign,R.id.rela_ticket,R.id.my_Opinion,R.id.mine_retuen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_sign://用户签到
                setGet(Constant.UserSignIn_Path,SignBean.class);
                break;
            case R.id.personal_meassage://个人信息
                Intent intent=new Intent(getActivity(),Presonal_Message_Activity.class);
                startActivity(intent);
                break;
            case R.id.my_care://我的关注
                Intent intentcare=new Intent(getActivity(),CareActivity.class);
                startActivity(intentcare);
                break;
            case R.id.rela_ticket:
                Intent intentticket=new Intent(getActivity(),TicketActivity.class);
                startActivity(intentticket);
                break;
            case R.id.my_Opinion:
                Intent intentopin=new Intent(getActivity(),OpinionActivity.class);
                startActivity(intentopin);
                break;
            case R.id.mine_retuen:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setIcon(R.mipmap.ic_launcher_round);

                builder.setTitle("标题栏");
                builder.setMessage("正文部分，简单的文本");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("取消", null);
                builder.setNeutralButton("中立", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
    }
    @Override
    public void success(Object data) {
      if (data instanceof PresonalMessageBean){
          PresonalMessageBean presonalMessageBean = (PresonalMessageBean) data;
          if (presonalMessageBean.getStatus().equals("0000")){
              Uri parse = Uri.parse(presonalMessageBean.getResult().getHeadPic());
              personal_top_image.setImageURI(parse);
              personal_name.setText(presonalMessageBean.getResult().getNickName());
          }
      }else if (data instanceof SignBean){
          SignBean signBean= (SignBean) data;
          if (signBean.getStatus().equals("0000")){
              showToast(signBean.getMessage());
          }else {
              showToast(signBean.getMessage());
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
}
