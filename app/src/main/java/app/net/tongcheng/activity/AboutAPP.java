package app.net.tongcheng.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.github.yoojia.zxing.qrcode.Encoder;

import app.net.tongcheng.Business.OtherBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.MSGModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.UiUtil;
import app.net.tongcheng.util.Utils;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/17 11:20
 */
public class AboutAPP extends BaseActivity implements View.OnClickListener {
    private ViewHolder mViewHolder;
    private MSGModel mMSGModel;
    private OtherBusiness mOtherBusiness;
    private boolean isHadnew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);
        setTitle("关于同城商城");
        initView();
        mOtherBusiness = new OtherBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        mViewHolder.setOnClickListener(R.id.llt_share);
        mViewHolder.setOnClickListener(R.id.llt_about);
        mViewHolder.setOnClickListener(R.id.llt_kf);
        mViewHolder.setOnClickListener(R.id.llt_new_ver);
    }

    @Override
    public void loadData() {
        mHandler.sendEmptyMessage(10001);
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                mMSGModel = NativieDataUtils.getMSGModel();
                if (mMSGModel == null || !NativieDataUtils.getTodyYMD().equals(mMSGModel.getUpdate())) {
                    mOtherBusiness.getMSGModel(APPCationStation.LOADINGAD, "");
                }
                mHandler.sendEmptyMessage(10002);
                break;
            case 10002:
                if (mMSGModel == null) {
                    return;
                }
                mViewHolder.setText(R.id.version_value, Utils.getVersionName());
                int dimension = (int) UiUtil.dp2px(getResources(), 130f);
                Encoder encoder = new Encoder.Builder()
                        .setBackgroundColor(0xFFFFFF) // 指定背景颜色，默认为白色
                        .setCodeColor(0xFF000000) // 指定编码块颜色，默认为黑色
                        .setOutputBitmapWidth(dimension) // 生成图片宽度
                        .setOutputBitmapHeight(dimension) // 生成图片高度
                        .setOutputBitmapPadding(0) // 设置为没有白边
                        .build();
                ((ImageView) mViewHolder.getView(R.id.iv_qrcode)).setImageBitmap(encoder.encode(mMSGModel.getInvite_url().replace("phone=%s", "phone=" + TCApplication.getmUserInfo().getPhone()).replace("channel=%s", "channel=" + Common.BrandName)));
                if (!TextUtils.isEmpty(mMSGModel.getUpdate_addr()) && !Utils.getVersionName().equals(mMSGModel.getUpdate_ver())) {
                    if (isHadnew) {
                        isHadnew = false;
                        sendEventBusMessage("HadUpVer");
                    }
                    mViewHolder.setText(R.id.upappver, mMSGModel.getUpdate_ver());
                } else {
                    mViewHolder.setText(R.id.upappver, "");
                }
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.LOADINGAD:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    MSGModel mMSGModel = (MSGModel) mConnectResult.getObject();
                    mMSGModel.setUpdate(NativieDataUtils.getTodyYMD());
                    NativieDataUtils.setMSGModel(mMSGModel);
                    this.mMSGModel = mMSGModel;
                    isHadnew = true;
                    mHandler.sendEmptyMessage(10002);
                }
                break;
            case APPCationStation.LOADING:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    MSGModel mMSGModel = (MSGModel) mConnectResult.getObject();
                    mMSGModel.setUpdate(NativieDataUtils.getTodyYMD());
                    NativieDataUtils.setMSGModel(mMSGModel);
                    this.mMSGModel = mMSGModel;
                    isHadnew = true;
                    mHandler.sendEmptyMessage(10002);
                    checkVer();
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
        switch (v.getId()) {
            case R.id.llt_share:
                startActivity(new Intent(TCApplication.mContext, PublicWebview.class).putExtra("title", "分享说明").putExtra("url", "http://user.8hbao.com:8060/share_instructions.html"));
                break;
            case R.id.llt_about:
                startActivity(new Intent(TCApplication.mContext, PublicWebview.class).putExtra("title", "关于同城商城").putExtra("url", "http://user.8hbao.com:8060/about.html"));
                break;
            case R.id.llt_kf:
                callPhone();
                break;
            case R.id.llt_new_ver:
                if (Utils.isNetWorkAvailable()) {
                    // 网络可用 实时请求网络
                    mOtherBusiness.getMSGModel(APPCationStation.LOADING, "");
                } else {
                    checkVer();
                }
                break;
        }
    }

    private void callPhone() {
        if (mMSGModel == null || TextUtils.isEmpty(mMSGModel.getService_phone())) {
            ToastUtil.showToast("网络不可用,请检查网络连接!");
        }
        DialogUtil.showTipsDialog(this, "是否要打电话给客服？", "(服务时间 9:00-22:00；周末 9:00-18:00)", "确认", "取消", new DialogUtil.OnConfirmListener() {

            @Override
            public void clickConfirm() {
                try {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + mMSGModel.getService_phone()));
                    startActivity(intent);
                } catch (Exception e) {
                }
            }

            @Override
            public void clickCancel() {

            }
        });
    }

    private void checkVer() {
        if (mMSGModel == null) {
            ToastUtil.showToast("网络不可用,请检查网络连接!");
        }
        if (!TextUtils.isEmpty(mMSGModel.getUpdate_addr()) && !Utils.getVersionName().equals(mMSGModel.getUpdate_ver())) {
            // 更新弹窗
            DialogUtil.showTipsDialog(this, "发现新版本", mMSGModel.getUpdate_tips() + "", "确定更新", "下次再说", new DialogUtil.OnConfirmListener() {
                @Override
                public void clickConfirm() {
                    Intent uppdate = new Intent("android.intent.action.VIEW", Uri.parse(mMSGModel.getUpdate_addr()));
                    uppdate.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(uppdate);
                }

                @Override
                public void clickCancel() {

                }
            });
        } else {
            DialogUtil.showTipsDialog(this, "当前已是最新版本!", null);
        }
    }

}
