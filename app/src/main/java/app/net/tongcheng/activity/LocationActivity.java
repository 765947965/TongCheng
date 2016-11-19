package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import org.greenrobot.eventbus.Subscribe;

import app.net.tongcheng.Business.OtherBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.CheckEvent;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.StartPageModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.GeneralDateUtils;
import app.net.tongcheng.util.Utils;
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
    private OtherBusiness mOtherBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_layout);
        initView();
        setEventBus();
        setChechLoding(false);
        mOtherBusiness = new OtherBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.sll_main), this);
        mViewHolder.setOnClickListener(R.id.registration);
        mViewHolder.setOnClickListener(R.id.loding);
    }

    @Override
    public void loadData() {
        mHandler.sendEmptyMessageDelayed(10001, 2000);
        StartPageModel mStartPageModel = NativieDataUtils.getStartPageModel(false);//启动页更新检查
        if (mStartPageModel == null || !NativieDataUtils.getTodyYMD().equals(mStartPageModel.getUpdate())) {
            mOtherBusiness.getStartPageImage(APPCationStation.GETSTARTPAGE, "");
        }
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        if (msg.what == 10001) {
            if (TCApplication.getmUserInfo() == null) {
                mViewHolder.setVisibility(R.id.llt_loding, View.VISIBLE);
            } else {
                boolean isStartTX = GeneralDateUtils.getBoolean(Utils.getVersionName());
                if (!isStartTX) {
                    // 开启新特性
                    GeneralDateUtils.PutBoolean(Utils.getVersionName(), true);
                    startActivity(new Intent(TCApplication.mContext, NewVerTXActivity.class).putExtra("isMain", true));
                } else {
                    StartPageModel mStartPageModel = NativieDataUtils.getStartPageModel(true);
                    if (mStartPageModel != null) {
                        // 开启启动页
                        startActivity(new Intent(TCApplication.mContext, StartPageActivity.class).putExtra("mStartPageModel", mStartPageModel));
                    } else {
                        // 开启主页
                        startActivity(new Intent(TCApplication.mContext, MainActivity.class));
                    }
                }
                finish();
            }
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.GETSTARTPAGE:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    StartPageModel mStartPageModel = (StartPageModel) mConnectResult.getObject();
                    mStartPageModel.setUpdate(NativieDataUtils.getTodyYMD());
                    mStartPageModel.setTodayShowTimesupdate(mStartPageModel.getUpdate());
                    mStartPageModel.setTodayShowTimes(0);
                    NativieDataUtils.setStartPageModel(mStartPageModel);
                }
                break;
        }
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
