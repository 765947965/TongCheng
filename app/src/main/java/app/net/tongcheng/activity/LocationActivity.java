package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import org.greenrobot.eventbus.Subscribe;

import app.net.tongcheng.R;
import app.net.tongcheng.model.CheckEvent;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/16 14:12
 */
public class LocationActivity extends BaseActivity implements View.OnClickListener {

    private ViewHolder mViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_layout);
        initView();
        setEventBus();
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.sll_main), this);
        mViewHolder.setOnClickListener(R.id.registration);
        mViewHolder.setOnClickListener(R.id.loding);
    }

    @Override
    public void loadData() {
        mHandler.sendEmptyMessage(101);
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        if (msg.what == 101) {
            mViewHolder.setVisibility(R.id.llt_loding, View.VISIBLE);
        }
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
            case R.id.registration://注册
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.loding://登录
                startActivity(new Intent(this, LodingActivity.class));
                break;
        }
    }

    @Subscribe
    public void onEvent(CheckEvent event) {
        if (event.getMsg().equals("loading_ok")) {
            finish();
        }
    }
}
