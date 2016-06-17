package app.net.tongcheng.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import app.net.tongcheng.Business.MyBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.activity.AboutAPP;
import app.net.tongcheng.activity.AccountSetActivity;
import app.net.tongcheng.activity.FeedbackActivity;
import app.net.tongcheng.activity.MyUserInfoActivity;
import app.net.tongcheng.activity.PublicWebview;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.UserMoreInfoModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.FriendBeanUtils;
import app.net.tongcheng.util.NativieDataUtils;
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
    private MyBusiness mMyBusiness;
    public static boolean isfirstloaddata;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        className = this.getClass().getSimpleName();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my_layout, null);
        initView(view);
        mMyBusiness = new MyBusiness(this, getActivity(), mHandler);
        isfirstloaddata = false;
        return view;
    }

    private void initView(View view) {
        mViewHolder = new ViewHolder(view, this);
        mViewHolder.setText(R.id.tv_title, "我");
        mViewHolder.setVisibility(R.id.bt_close, View.GONE);
        mViewHolder.setOnClickListener(R.id.rlt_user_info);
        mViewHolder.setOnClickListener(R.id.llt_abouttc);
        mViewHolder.setOnClickListener(R.id.llt_fk);
        mViewHolder.setOnClickListener(R.id.llt_zh);
    }

    @Override
    public void loadData() {
        //读取数据
        if (isfirstloaddata) {
            return;
        }
        isfirstloaddata = true;
        if (TCApplication.isHasNEW) {
            mViewHolder.setVisibility(R.id.iv_new, View.VISIBLE);
        } else {
            mViewHolder.setVisibility(R.id.iv_new, View.GONE);
        }
        mHandler.sendEmptyMessageDelayed(10001, 100);
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                UserMoreInfoModel mUserMoreInfoModel = NativieDataUtils.getUserMoreInfoModel();
                if (mUserMoreInfoModel == null || !NativieDataUtils.getTodyYMD().equals(mUserMoreInfoModel.getUpdate())) {
                    mMyBusiness.getuserInfo(APPCationStation.LOADING, "");
                }
                if (mUserMoreInfoModel == null) {
                    mUserMoreInfoModel = new UserMoreInfoModel();
                }
                setData(mUserMoreInfoModel);
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.LOADING:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    UserMoreInfoModel mUserMoreInfoModel = (UserMoreInfoModel) mConnectResult.getObject();
                    mUserMoreInfoModel.setUpdate(NativieDataUtils.getTodyYMD());
                    NativieDataUtils.setUserMoreInfoModel(mUserMoreInfoModel);
                    mHandler.sendEmptyMessage(10001);
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
            case R.id.rlt_user_info://我的信心
                startActivity(new Intent(TCApplication.mContext, MyUserInfoActivity.class));
                break;
            case R.id.llt_abouttc:
                startActivity(new Intent(TCApplication.mContext, AboutAPP.class));
                break;
            case R.id.llt_fk:
                startActivity(new Intent(TCApplication.mContext, FeedbackActivity.class));
                break;
            case R.id.llt_zh:
                startActivity(new Intent(TCApplication.mContext, AccountSetActivity.class));
                break;
        }
    }

    private void setData(UserMoreInfoModel mUserMoreInfoModel) {
        if (!TextUtils.isEmpty(mUserMoreInfoModel.getPicture()) && !TextUtils.isEmpty(mUserMoreInfoModel.getPicurl_prefix())) {
            mViewHolder.setImage(R.id.iv_head_image, mUserMoreInfoModel.getPicurl_prefix() + mUserMoreInfoModel.getPicture(), 0, 360);
        } else {
            mViewHolder.setImage(R.id.iv_head_image, R.drawable.content5);
        }
        if (!TextUtils.isEmpty(mUserMoreInfoModel.getName())) {
            mViewHolder.setText(R.id.iv_name, mUserMoreInfoModel.getName());
        } else {
            mViewHolder.setText(R.id.iv_name, "用户");
        }
        mViewHolder.setText(R.id.iv_phone, TCApplication.getmUserInfo().getPhone());
    }

}
