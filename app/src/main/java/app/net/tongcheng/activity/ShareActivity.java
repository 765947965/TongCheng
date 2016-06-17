package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import app.net.tongcheng.Business.OtherBusiness;
import app.net.tongcheng.Business.ShareBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.MSGModel;
import app.net.tongcheng.model.ShareCodeModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ShareUtils;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/5/27 17:11
 */
public class ShareActivity extends BaseActivity implements View.OnClickListener {

    private ViewHolder mViewHolder;
    private ShareBusiness mShareBusiness;
    private OtherBusiness mOtherBusiness;
    private MSGModel mMSGModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_layout);
        setTitle("分享");
        initView();
        mShareBusiness = new ShareBusiness(this, this, mHandler);
        mOtherBusiness = new OtherBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.sll_main), this);
        mViewHolder.setOnClickListener(R.id.bt_WX);
        mViewHolder.setOnClickListener(R.id.bt_QQ);
        mViewHolder.setOnClickListener(R.id.bt_DX);
    }


    @Override
    public void loadData() {
        mHandler.sendEmptyMessage(10001);
        mHandler.sendEmptyMessage(10002);
        mShareBusiness.getShareCode(APPCationStation.LOADING, "");
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                ShareCodeModel mShareCodeModel = NativieDataUtils.getShareCodeModel();
                if (mShareCodeModel != null && !TextUtils.isEmpty(mShareCodeModel.getMy_invite_flag())) {
                    mViewHolder.setText(R.id.tv_share_code, mShareCodeModel.getMy_invite_flag());
                }
                break;
            case 10002:
                mMSGModel = NativieDataUtils.getMSGModel();
                if (mMSGModel == null || !NativieDataUtils.getTodyYMD().equals(mMSGModel.getUpdate())) {
                    mOtherBusiness.getMSGModel(APPCationStation.LOADINGAD, "");
                }
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.LOADING:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    ShareCodeModel mShareCodeModel = (ShareCodeModel) mConnectResult.getObject();
                    NativieDataUtils.setShareCodeModel(mShareCodeModel);
                    mHandler.sendEmptyMessage(10001);
                }
                break;
            case APPCationStation.LOADINGAD:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    MSGModel mMSGModel = (MSGModel) mConnectResult.getObject();
                    mMSGModel.setUpdate(NativieDataUtils.getTodyYMD());
                    NativieDataUtils.setMSGModel(mMSGModel);
                    this.mMSGModel = mMSGModel;
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoding_Type) {
        ToastUtil.showToast("网络不可用,请检查网络连接!");
    }

    @Override
    public void onClick(View v) {
        if (mMSGModel == null) {
            ToastUtil.showToast("网络不可用,请检查网络连接!");
            return;
        }
        String shareurl = null;
        switch (v.getId()) {
            case R.id.bt_WX:
                shareurl = mMSGModel.getInvite_url().replace("phone=%s", "phone=" + TCApplication.getmUserInfo().getPhone()).replace("channel=%s", "channel=weixinfriend");
                ShareUtils.SendIMInfo(this, Wechat.NAME, mMSGModel.getInvite_title(), mMSGModel.getInvite_sns_message(), shareurl);
                break;
            case R.id.bt_QQ:
                shareurl = mMSGModel.getInvite_url().replace("phone=%s", "phone=" + TCApplication.getmUserInfo().getPhone()).replace("channel=%s", "channel=qq");
                ShareUtils.SendIMInfo(this, QQ.NAME, mMSGModel.getInvite_title(), mMSGModel.getInvite_sns_message(), shareurl);
                break;
            case R.id.bt_DX:
                shareurl = mMSGModel.getInvite_url().replace("phone=%s", "phone=" + TCApplication.getmUserInfo().getPhone()).replace("channel=%s", "channel=sms");
                ShareUtils.SendIMInfo(this, ShortMessage.NAME, mMSGModel.getInvite_title(), mMSGModel.getInvite_sms_message(), shareurl);
                break;
        }
    }
}
