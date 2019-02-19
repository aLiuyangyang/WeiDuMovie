package com.bw.movie.view.activity.showmineactivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.adapter.showmine_adapter.SystemMessageAdapter;
import com.bw.movie.base.BaseActivity;

import com.bw.movie.bean.SelectSystemBean;
import com.bw.movie.bean.SelectUserNoReadBean;
import com.bw.movie.bean.StatusBean;
import com.bw.movie.utils.Constant;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SystemMessageActivity extends BaseActivity {
    @BindView(R.id.system_message)
    TextView systemMessage;
    @BindView(R.id.system_message_list)
    XRecyclerView systemMessageList;
    @BindView(R.id.film_details_back)
    ImageView filmDetailsBack;
    private int page;
    private SystemMessageAdapter systemMessageAdapter;
    private List<SelectSystemBean.ResultBean> result;

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
        page = 1;
        //查询用户未读
        setGet(Constant.MessageCount_Path,SelectUserNoReadBean.class);
        //查询消息
        LinearLayoutManager layoutManager = new LinearLayoutManager(SystemMessageActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        systemMessageList.setLayoutManager(layoutManager);
        //
        systemMessageAdapter = new SystemMessageAdapter(this);
        systemMessageList.setAdapter(systemMessageAdapter);
        systemMessageList.setPullRefreshEnabled(true);
        systemMessageList.setLoadingMoreEnabled(true);

        systemMessageList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                setGet(String.format(Constant.SysMsgList_Path,page),SelectSystemBean.class);
            }

            @Override            public void onLoadMore() {
                setGet(String.format(Constant.SysMsgList_Path,page),SelectSystemBean.class);
            }
        });
        /** 系统消息读取状态修改 */
        systemMessageAdapter.setIsReadClick(new SystemMessageAdapter.IsReadClick() {
            @Override
            public void isReadClick(int id) {
                setGet(String.format(Constant.SysMsgStatus_Path,id),StatusBean.class);
            }
        });

    }

    @Override
    public int getContent() {
        return R.layout.activity_system_message;
    }

    @Override
    public void success(Object data) {
        if (data instanceof SelectUserNoReadBean) {
            SelectUserNoReadBean selectUserNoReadBean = (SelectUserNoReadBean) data;
            systemMessage.setText("系统消息（" + selectUserNoReadBean.getCount()+ "条未读）");
            page = 1;
            setGet(String.format(Constant.SysMsgList_Path,page),SelectSystemBean.class);

        }else if(data instanceof SelectSystemBean){
            SelectSystemBean bean = (SelectSystemBean) data;
            if (bean.getStatus().equals("0000")){
                result = bean.getResult();
                if (result.size()>0) {
                    if (page == 1) {
                        systemMessageAdapter.setData(result);
                        systemMessageList.refreshComplete();
                    } else {
                        page++;
                        systemMessageAdapter.addData(result);
                        systemMessageList.loadMoreComplete();
                    }
                    systemMessageList.refreshComplete();
                }
            }

        }else if (data instanceof StatusBean){
            setGet(Constant.MessageCount_Path,SelectUserNoReadBean.class);
        }

    }

    @Override
    public void fail(String error) {

    }
}
