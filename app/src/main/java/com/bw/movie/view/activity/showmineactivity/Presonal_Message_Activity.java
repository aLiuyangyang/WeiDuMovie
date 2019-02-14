package com.bw.movie.view.activity.showmineactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.MessageList;
import com.bw.movie.bean.PresonalMessageBean;
import com.bw.movie.bean.UploadHeadPicBean;
import com.bw.movie.presenter.IPresenter;
import com.bw.movie.utils.Constant;
import com.bw.movie.utils.MyApp;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

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
    private final int REQUEST_CAMEAR = 100;
    private final int REQUEST_PICK = 200;
    private final int REQUEST_PICTRUE = 300;
    private final String PATH_FILE=Environment.getExternalStorageDirectory()+"/file.png";
    private final String path =Environment.getExternalStorageDirectory()+ "/image.png";
    private IPresenter iPresenter;

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        iPresenter=new IPresenter(this);
        setGet(Constant.Update_User, PresonalMessageBean.class);
        filmDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        personalIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPopIcon();
            }
        });
        personal_reset_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(Presonal_Message_Activity.this,UpdatePassActivity.class);
              startActivity(intent);
              finish();
            }
        });
    }
    private void setPopIcon() {
      View view=View.inflate(this,R.layout.pop_icon,null);
        Button open_camera=view.findViewById(R.id.open_camera);
        Button open_photo=view.findViewById(R.id.open_photo);
        Button open_close=view.findViewById(R.id.open_close);
        final PopupWindow popupWindow=new PopupWindow(view,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(view,
                Gravity.BOTTOM, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
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
                startActivityForResult(intent,REQUEST_CAMEAR);
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
    private void getUserAvatar(Map<String,String> map){
        iPresenter.setRequestHead(Constant.UploadHeadPic_Path,map,UploadHeadPicBean.class);
    }
    @Override
    public int getContent() {
        return R.layout.activity_presonal__message_;
    }

    @Override
    public void success(Object data) {
        if (data instanceof PresonalMessageBean) {
            PresonalMessageBean presonalMessageBean = (PresonalMessageBean) data;
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
        }else if (data instanceof UploadHeadPicBean){
            UploadHeadPicBean uploadHeadPicBean= (UploadHeadPicBean) data;
            if (uploadHeadPicBean.getStatus().equals("0000")){

                Uri parse = Uri.parse(uploadHeadPicBean.getHeadPath());
                personalIcon.setImageURI(parse);
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
            Bitmap bitmap =data.getParcelableExtra("data");
            try {
                MyApp.saveBitmap(bitmap,PATH_FILE,50);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Map<String,String> map=new HashMap<>();
            map.put("image",PATH_FILE);
            getUserAvatar(map);
        }
    }
}
