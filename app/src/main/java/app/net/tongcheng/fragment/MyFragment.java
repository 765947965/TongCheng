package app.net.tongcheng.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.activity.MyUserInfoActivity;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/16 15:55
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {

    private ViewHolder mViewHolder;
    public static boolean isfirstloaddata;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my_layout, null);
        initView(view);
        isfirstloaddata = false;
        return view;
    }

    private void initView(View view) {
        mViewHolder = new ViewHolder(view, this);
        mViewHolder.setText(R.id.tv_title, "我");
        mViewHolder.setVisibility(R.id.bt_close, View.GONE);
        mViewHolder.setOnClickListener(R.id.rlt_user_info);
    }

    @Override
    public void loadData() {
        //读取数据
        if (isfirstloaddata) {
            return;
        }
        isfirstloaddata = true;
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {

    }

    @Override
    public void BusinessOnFail(int mLoding_Type) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlt_user_info://我的信心
                startActivity(new Intent(TCApplication.mContext, MyUserInfoActivity.class));
                break;
        }
    }

}
