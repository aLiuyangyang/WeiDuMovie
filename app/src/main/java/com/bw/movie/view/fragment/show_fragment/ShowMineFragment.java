package com.bw.movie.view.fragment.show_fragment;


import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.EventBusBean;
import com.bw.movie.bean.EventBusMessage;
import com.bw.movie.bean.NewVersionBean;
import com.bw.movie.bean.PresonalMessageBean;
import com.bw.movie.bean.ShowCinemaBean;
import com.bw.movie.bean.SignBean;
import com.bw.movie.utils.Constant;
import com.bw.movie.view.activity.logandregactivity.LoginActivity;
import com.bw.movie.view.activity.showfileactivity.DetailsActivity;
import com.bw.movie.view.activity.showmineactivity.CareActivity;
import com.bw.movie.view.activity.showmineactivity.OpinionActivity;
import com.bw.movie.view.activity.showmineactivity.Presonal_Message_Activity;
import com.bw.movie.view.activity.showmineactivity.SystemMessageActivity;
import com.bw.movie.view.activity.showmineactivity.TicketActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

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
    @BindView(R.id.system_mess)
    ImageView system_mess;
    @BindView(R.id.my_care)
    RelativeLayout my_care;
    @BindView(R.id.mine_retuen)
    RelativeLayout mine_retuen;
    @BindView(R.id.personal_latest_version)
    ImageView personal_latest_version;
    @BindView(R.id.mine_NewVersion)
    RelativeLayout mine_NewVersion;
    @BindView(R.id.personal_record_attention)
    ImageView personalRecordAttention;
    @BindView(R.id.personal_top_image)
    SimpleDraweeView personal_top_image;
    @BindView(R.id.personal_name)
    TextView personal_name;
    private SharedPreferences sharedPreferences;//存储
    private SharedPreferences.Editor edit;
    private int flag;//判断标识符
    private NewVersionBean newVersionBean;
    private String downloadUrl;
    private boolean isUser;

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);


    }

    @Override
    public void initData(View view) {
        sharedPreferences=getContext().getSharedPreferences("UserMessage",MODE_PRIVATE);
        edit = sharedPreferences.edit();
        isUser = sharedPreferences.getBoolean("isUser", false);
        setGet(Constant.Update_User, PresonalMessageBean.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void message(EventBusBean eventBusMessage) {
        if (eventBusMessage.getId() == 1){
            String flag = eventBusMessage.getFlag();
            Uri parse = Uri.parse(flag);
            personal_top_image.setImageURI(parse);
        }else if (eventBusMessage.getId() == 2){
            String flag = eventBusMessage.getFlag();
            personal_name.setText(flag);
        }

    }
    @Override
    public int getContent() {
        return R.layout.fragment_show_mine;
    }
    @OnClick({R.id.personal_meassage,R.id.system_mess,R.id.mine_NewVersion,R.id.my_care,R.id.mine_sign,R.id.rela_ticket,R.id.my_Opinion,R.id.mine_retuen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.system_mess:
                if (isUser) {
                    Intent intentmess=new Intent(getActivity(),SystemMessageActivity.class);
                    getActivity().startActivity(intentmess);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("您还没有登录，确认要去登录吗?");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                break;
            //版本更新
            case R.id.mine_NewVersion:
                ObjectAnimator rotation = ObjectAnimator.ofFloat(personal_latest_version, "rotation", 0.0f, 720f);
                rotation.setDuration(1000);
                rotation.start();
                setGet(Constant.NewVersion_Path,NewVersionBean.class);
                break;
            case R.id.mine_sign://用户签到
                setGet(Constant.UserSignIn_Path,SignBean.class);
                break;
            case R.id.personal_meassage://个人信息
                if (isUser) {
                    Intent intent = new Intent(getActivity(), Presonal_Message_Activity.class);
                    startActivity(intent);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("您还没有登录，确认要去登录吗?");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                break;
            case R.id.my_care://我的关注
                if (isUser) {
                    Intent intentcare=new Intent(getActivity(),CareActivity.class);
                    startActivity(intentcare);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("您还没有登录，确认要去登录吗?");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                break;
            case R.id.rela_ticket:
                if (isUser) {
                    Intent intentticket=new Intent(getActivity(),TicketActivity.class);
                    startActivity(intentticket);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("您还没有登录，确认要去登录吗?");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

                break;
            case R.id.my_Opinion:
                if (isUser) {
                    Intent intentopin=new Intent(getActivity(),OpinionActivity.class);
                    startActivity(intentopin);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("您还没有登录，确认要去登录吗?");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);

                        }
                    });
                    builder.setNegativeButton("取消", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

                break;
            case R.id.mine_retuen:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("您确认要退出维度影院吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edit.clear();
                        edit.commit();
                        Intent intent=new Intent(getActivity(),LoginActivity.class);
                        getActivity().startActivity(intent);
                    }
                });
                builder.setNegativeButton("取消", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
    }



    public static void openBrowser(Context context, String url){
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } else {
            Toast.makeText(context.getApplicationContext(), "请下载浏览器", Toast.LENGTH_SHORT).show();
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
      }else if (data instanceof NewVersionBean){
          newVersionBean = (NewVersionBean) data;
          if (newVersionBean.getStatus().equals("0000")){
              flag = newVersionBean.getFlag();
              downloadUrl = newVersionBean.getDownloadUrl();
              if (flag==1){
                  AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                  builder.setMessage("您有新版本的需要更新");
                  builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          openBrowser(getContext(), downloadUrl);
                      }
                  });
                  builder.setNegativeButton("取消", null);
                  AlertDialog alertDialog = builder.create();
                  alertDialog.show();
              }else {
                  showToast("没新版本，不需要更新");
              }
          }else {
              showToast(newVersionBean.getMessage());
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
        EventBus.getDefault().unregister(this);
    }
}
