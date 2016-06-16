package app.net.tongcheng.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.net.tongcheng.Business.ShareBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.activity.ShareActivity;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.ShareTipsModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/16 15:54
 */
public class ShareFragment extends BaseFragment implements View.OnClickListener {
    private ViewHolder mViewHolder;
    public static boolean isfirstloaddata;
    private ShareBusiness mShareBusiness;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        className = this.getClass().getSimpleName();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_share_layout, null);
        initView(view);
        isfirstloaddata = false;
        mShareBusiness = new ShareBusiness(this, getActivity(), mHandler);
        return view;
    }

    private void initView(View view) {
        mViewHolder = new ViewHolder(view, this);
        mViewHolder.setVisibility(R.id.viewBaseLine, View.GONE);
        mViewHolder.setText(R.id.tv_title, "分享");
        mViewHolder.setVisibility(R.id.bt_close, View.GONE);
        mViewHolder.setOnClickListener(R.id.bt_share);
    }


    @Override
    public void loadData() {
        if (isfirstloaddata) {
            return;
        }
        isfirstloaddata = true;
        mHandler.sendEmptyMessage(10001);
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                ShareTipsModel mShareTipsModel = NativieDataUtils.getShareTipsModel();
                if (mShareTipsModel == null || !NativieDataUtils.getTodyYMD().equals(mShareTipsModel.getUpdate())) {
                    mShareBusiness.getShareTips(APPCationStation.LOADING, "");
                }
                if (mShareTipsModel != null && !TextUtils.isEmpty(mShareTipsModel.getContent())) {
                    mViewHolder.setText(R.id.tv_share_tips, mShareTipsModel.getContent());
                }
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.LOADING:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    ShareTipsModel mShareTipsModel = (ShareTipsModel) mConnectResult.getObject();
                    mShareTipsModel.setUpdate(NativieDataUtils.getTodyYMD());
                    if (!TextUtils.isEmpty(mShareTipsModel.getContent())) {
                        NativieDataUtils.setShareTipsModel(mShareTipsModel);
                        mHandler.sendEmptyMessage(10001);
                    }
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
            case R.id.bt_share://分享
                startActivity(new Intent(TCApplication.mContext, ShareActivity.class));
                break;
        }
    }
}
