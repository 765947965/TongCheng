package app.net.tongcheng.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.net.tongcheng.Business.ShareBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.activity.PublicWebview;
import app.net.tongcheng.activity.ShareActivity;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.ShareTipsModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.MD5;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ViewHolder;
import okhttp3.Response;

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
        View view = inflater.inflate(R.layout.fragment_share_layout, null);
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
        mViewHolder.setOnClickListener(R.id.tv_share_num);
    }


    @Override
    public void loadData() {
        if (isfirstloaddata) {
            return;
        }
        isfirstloaddata = true;
        mHandler.sendEmptyMessage(10001);
        mShareBusiness.getShareTips(APPCationStation.LOADING, "");
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                ShareTipsModel mShareTipsModel = NativieDataUtils.getShareTipsModel();
                if (mShareTipsModel != null && !TextUtils.isEmpty(mShareTipsModel.getContent())) {
                    mViewHolder.setText(R.id.tv_share_tips, mShareTipsModel.getContent());
                    if (!TextUtils.isEmpty(mShareTipsModel.getInvite_charge_success_num())) {
                        mViewHolder.setText(R.id.tv_share_num, Html.fromHtml("<u>" + mShareTipsModel.getInvite_charge_success_num() + "</u>"));
                    }
                }
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {
        switch (mLoadType) {
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
    public void BusinessOnFail(int mLoadType, Response response) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_share://分享
                startActivity(new Intent(TCApplication.mContext, ShareActivity.class));
                break;
            case R.id.tv_share_num://分享人数
                String url = "http://user.zjtongchengshop.com/my_invite_list.php?uid=" + TCApplication.getmUserInfo().getUid() + "&sign=" + MD5.toMD5(TCApplication.getmUserInfo().getUid() + Common.SIGN_KEY);
                startActivity(new Intent(TCApplication.mContext, PublicWebview.class).putExtra("title", "我的邀请人列表").putExtra("url", url));
                break;
        }
    }
}
