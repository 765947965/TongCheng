package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import app.net.tongcheng.Business.OtherBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.InviteInfo;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;
import okhttp3.Response;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/11/19 15:36
 */

public class InviterActivity extends BaseActivity implements View.OnClickListener {
    private ViewHolder mViewHolder;
    private EditText inputCode;
    private Button bt_submit;
    private OtherBusiness mOtherBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inviter_layout);
        setTitle("谁邀请我");
        initView();
        mOtherBusiness = new OtherBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        inputCode = mViewHolder.getView(R.id.inputCode);
        bt_submit = mViewHolder.setOnClickListener(R.id.bt_submit);
    }


    @Override
    public void loadData() {
        mOtherBusiness.getInvitationCode(APPCationStation.LOADING, "加载中...");
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {
        switch (mLoadType) {
            case APPCationStation.LOADING:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    InviteInfo mInviteInfo = (InviteInfo) mConnectResult.getObject();
                    if (!TextUtils.isEmpty(mInviteInfo.getInviteflag())) {
                        inputCode.setText(mInviteInfo.getInviteflag());
                        inputCode.setHintTextColor(getResources().getColor(R.color.text_content));
                        mViewHolder.setText(R.id.tv_tips_1, "邀请人账号:   " + mInviteInfo.getInviteByUID()).setVisibility(View.VISIBLE);
                        mViewHolder.setText(R.id.tv_tips_2, "邀请人手机号码:   " + mInviteInfo.getInviteByPhone()).setVisibility(View.VISIBLE);
                        mViewHolder.setText(R.id.tv_tips_3, "邀请关系建立时间:   " + mInviteInfo.getInviteTime()).setVisibility(View.VISIBLE);
                    }
                } else {
                    inputCode.setEnabled(true);
                    mViewHolder.setVisibility(R.id.bt_submit, View.VISIBLE);
                }
                break;
            case APPCationStation.SUMBIT:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    DialogUtil.showTipsDialog(this, "提交成功!", new DialogUtil.OnConfirmListener() {
                        @Override
                        public void clickConfirm() {
                            finish();
                        }

                        @Override
                        public void clickCancel() {

                        }
                    });
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoadType, Response response) {
        switch (mLoadType) {
            case APPCationStation.SUMBIT:
                bt_submit.setEnabled(true);
                break;
        }
        if (response == null || response.code() != 403) {
            ToastUtil.showToast("网络不可用，请检查网络连接！");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                if (inputCode.getText().toString().length() == 0) {
                    ToastUtil.showToast("请输入邀请码");
                } else {
                    bt_submit.setEnabled(false);
                    mOtherBusiness.setInvitationCode(APPCationStation.SUMBIT, "提交中...", inputCode.getText().toString());
                }
                break;
        }
    }
}
