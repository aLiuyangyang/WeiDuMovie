package com.bw.movie.view.activity.showmineactivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.EventBusBean;
import com.bw.movie.bean.EventBusMessage;
import com.bw.movie.bean.PresonalMessageBean;
import com.bw.movie.bean.UpdateUserBean;
import com.bw.movie.bean.UploadHeadPicBean;
import com.bw.movie.presenter.IPresenter;
import com.bw.movie.utils.Constant;
import com.bw.movie.utils.MyApp;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Presonal_Message_Activity extends BaseActivity {
    @BindView(R.id.film_details_back)
    ImageView filmDetailsBack;
    @BindView(R.id.personal_icon)
    SimpleDraweeView personalIcon;
    @BindView(R.id.personal_nickName_textView)
    TextView personalNickNameTextView;
    @BindView(R.id.personal_sex_textView)
    TextView personalsex;
    @BindView(R.id.personal_month_textView)
    TextView personalmonth;
    @BindView(R.id.personal_phone_number)
    TextView personal_phone_number;
    @BindView(R.id.personal_emails_count)
    TextView personal_emails_count;
    @BindView(R.id.personal_reset_pwd)
    RelativeLayout personal_reset_pwd;
    @BindView(R.id.top_personal_but)
    Button top_personal_but;
    private final int REQUEST_CAMEAR = 100;
    private final int REQUEST_PICK = 200;
    private final int REQUEST_PICTRUE = 300;
    @BindView(R.id.top_personal_item)
    TextView topPersonalItem;
    @BindView(R.id.personal_textView_icon)
    TextView personalTextViewIcon;
    @BindView(R.id.personal_view_icon)
    View personalViewIcon;
    @BindView(R.id.personal_nickName)
    TextView personalNickName;
    @BindView(R.id.personal_view_nickName)
    View personalViewNickName;
    @BindView(R.id.personal_sex)
    TextView personalSex;
    @BindView(R.id.personal_view_sex)
    View personalViewSex;
    @BindView(R.id.personal_month)
    TextView personalMonth;
    @BindView(R.id.personal_message_relative)
    RelativeLayout personalMessageRelative;
    @BindView(R.id.personal_meassage_phone)
    TextView personalMeassagePhone;
    @BindView(R.id.personal_phone_message)
    View personalPhoneMessage;
    @BindView(R.id.personal_email_phone)
    RelativeLayout personalEmailPhone;
    @BindView(R.id.li4)
    LinearLayout li4;
    private String emil;
    private final String PATH_FILE = Environment.getExternalStorageDirectory() + "/file.png";
    private final String path = Environment.getExternalStorageDirectory() + "/image.png";
    private IPresenter iPresenter;
    private int mysex;//性别
    private PresonalMessageBean presonalMessageBean;
    private String names;
    @Override
    public void initView() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void message(EventBusMessage eventBusMessage) {
        if (eventBusMessage.getId() == 3) {
            setGet(Constant.Update_User, PresonalMessageBean.class);
        }
    }

    @Override
    public void initData() {
        iPresenter = new IPresenter(this);
        setGet(Constant.Update_User, PresonalMessageBean.class);
        filmDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setPopIcon() {
        View view = View.inflate(this, R.layout.pop_icon, null);
        Button open_camera = view.findViewById(R.id.open_camera);
        Button open_photo = view.findViewById(R.id.open_photo);
        Button open_close = view.findViewById(R.id.open_close);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(view,
                Gravity.BOTTOM, 0, 0);
        //打开相册
        open_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_PICK);
                popupWindow.dismiss();
            }
        });
        //打开相机
        open_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                startActivityForResult(intent, REQUEST_CAMEAR);
                popupWindow.dismiss();
            }
        });

        //关闭
        open_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void getUserAvatar(Map<String, String> map) {
        iPresenter.setRequestHead(Constant.UploadHeadPic_Path, map, UploadHeadPicBean.class);
    }

    @Override
    public int getContent() {
        return R.layout.activity_presonal__message_;
    }

    @Override
    public void success(Object data) {
        if (data instanceof PresonalMessageBean) {
            presonalMessageBean = (PresonalMessageBean) data;
            if (presonalMessageBean.getStatus().equals("0000")) {
                String headPic = presonalMessageBean.getResult().getHeadPic();
                Uri parse = Uri.parse(headPic);
                personalIcon.setImageURI(parse);
                personalNickNameTextView.setText(presonalMessageBean.getResult().getNickName());
                personal_phone_number.setText(presonalMessageBean.getResult().getPhone());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                personalmonth.setText(simpleDateFormat.format(presonalMessageBean.getResult().getLastLoginTime()));
                int sex = presonalMessageBean.getResult().getSex();
                if (sex == 1) {
                    personalsex.setText("男");
                } else {
                    personalsex.setText("女");
                }
                personal_emails_count.setText(presonalMessageBean.getResult().getEmail());
            } else {
                showToast(presonalMessageBean.getMessage());
            }
        } else if (data instanceof UploadHeadPicBean) {
            UploadHeadPicBean uploadHeadPicBean = (UploadHeadPicBean) data;
            if (uploadHeadPicBean.getStatus().equals("0000")) {
                EventBus.getDefault().post(new EventBusBean(1, uploadHeadPicBean.getHeadPath()));
                Uri parse = Uri.parse(uploadHeadPicBean.getHeadPath());
                personalIcon.setImageURI(parse);
            }
        } else if (data instanceof UpdateUserBean) {
            UpdateUserBean updateUserBean = (UpdateUserBean) data;
            if (updateUserBean.getStatus().equals("0000")) {
                EventBus.getDefault().post(new EventBusBean(2, updateUserBean.getResult().getNickName()));
                EventBus.getDefault().post(new EventBusMessage(3));
                personalNickNameTextView.setText(updateUserBean.getResult().getNickName());
                int sex = updateUserBean.getResult().getSex();
                if (sex == 1) {
                    personalsex.setText("男");
                } else {
                    personalsex.setText("女");
                }
                personal_emails_count.setText(emil);
            } else {
                showToast(updateUserBean.getMessage());
            }
        }
    }


    @Override
    public void fail(String error) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMEAR && resultCode == RESULT_OK) {
            //打开裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            //将图片设置给裁剪
            intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
            //设置是否支持裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出后图片大小
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            //返回到data
            intent.putExtra("return-data", true);
            //启动
            startActivityForResult(intent, REQUEST_PICTRUE);

        }
        if (requestCode == REQUEST_PICK && resultCode == RESULT_OK) {
            //打开裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            Uri uri = data.getData();
            //将图片设置给裁剪
            intent.setDataAndType(uri, "image/*");
            //设置是否可裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            //返回data
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_PICTRUE);
        }
        //获取剪切完的图片数据 bitmap
        if (requestCode == REQUEST_PICTRUE && resultCode == RESULT_OK) {
            Bitmap bitmap = data.getParcelableExtra("data");
            try {
                MyApp.saveBitmap(bitmap, PATH_FILE, 50);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Map<String, String> map = new HashMap<>();
            map.put("image", PATH_FILE);
            getUserAvatar(map);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @OnClick({R.id.top_personal_but, R.id.personal_icon, R.id.personal_reset_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_personal_but:
                View views = View.inflate(Presonal_Message_Activity.this, R.layout.pop_updatepass, null);
                final EditText updatapass_name = views.findViewById(R.id.updatapass_name);
                final RadioButton updatapass_sex = views.findViewById(R.id.updatapass_sex);
                final RadioButton updatapass_sex1 = views.findViewById(R.id.updatapass_sex1);
                final EditText updatapass_emil = views.findViewById(R.id.updatapass_emil);
                Button updatapass_but = views.findViewById(R.id.updatapass_but);
                final PopupWindow popupWindow = new PopupWindow(views, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchable(true);
                popupWindow.showAtLocation(views,
                        Gravity.CENTER, 0, 0);

                int sex = presonalMessageBean.getResult().getSex();
                if (sex == 1) {
                    updatapass_sex.setChecked(true);
                } else {
                    updatapass_sex1.setChecked(true);
                }
                updatapass_emil.setText(presonalMessageBean.getResult().getEmail());
                updatapass_name.setText(presonalMessageBean.getResult().getNickName());
                updatapass_but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        names = updatapass_name.getText().toString();
                        emil = updatapass_emil.getText().toString();
                        if (updatapass_sex.isChecked()) {
                            mysex = 1;
                        } else if (updatapass_sex1.isChecked()) {
                            mysex = 2;
                        }
                        Map<String, String> map = new HashMap<>();
                        map.put("nickName", names);
                        map.put("sex", mysex + "");
                        map.put("email", emil);
                        setPost(Constant.UserInfo_Path, UpdateUserBean.class, map);
                        popupWindow.dismiss();
                    }
                });
                break;
            case R.id.personal_icon:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String[] mStatenetwork = new String[]{
                            //写的权限
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            //读的权限
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            //入网权限
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            //WIFI权限
                            Manifest.permission.ACCESS_WIFI_STATE,
                            //读手机权限
                            Manifest.permission.READ_PHONE_STATE,
                            //网络权限
                            Manifest.permission.INTERNET,
                            //相机
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_APN_SETTINGS,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                    };
                    ActivityCompat.requestPermissions(Presonal_Message_Activity.this, mStatenetwork, 100);
                }
                setPopIcon();
                break;
            case R.id.personal_reset_pwd:
                Intent intent = new Intent(Presonal_Message_Activity.this, UpdatePassActivity.class);
                startActivity(intent);
                break;
        }
    }
}
