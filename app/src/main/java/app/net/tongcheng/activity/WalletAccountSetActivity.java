package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import app.net.tongcheng.Business.OtherBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.OperationUtils;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/11/17 20:08
 */

public class WalletAccountSetActivity extends BaseActivity implements View.OnClickListener {
    private ViewHolder mViewHolder;
    private OtherBusiness mOtherBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_account_set_layout);
        setTitle("钱包密码设置");
        initView();
        mOtherBusiness = new OtherBusiness(this, this, mHandler);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (OperationUtils.getBoolean(OperationUtils.walletPassword)) {
            mViewHolder.setVisibility(R.id.setts_set_wallet_password, View.GONE);
        } else {
            mViewHolder.setVisibility(R.id.setts_set_wallet_password, View.VISIBLE);
        }
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        mViewHolder.setOnClickListener(R.id.setts_set_wallet_password);
        mViewHolder.setOnClickListener(R.id.setts_retrieve_password_relayout);
        mViewHolder.setOnClickListener(R.id.setts_change_password_relayout);
    }

    @Override
    public void loadData() {
        if (!OperationUtils.getBoolean(OperationUtils.walletPassword)) {
            mOtherBusiness.getWalletPasswordType(APPCationStation.LOADING, "");
        }
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.LOADING:
                if (mConnectResult != null && mConnectResult.getObject() != null) {
                    if (((BaseModel) mConnectResult.getObject()).getResult() == 81) {
                        OperationUtils.PutBoolean(OperationUtils.walletPassword, false);
                        mViewHolder.setVisibility(R.id.setts_set_wallet_password, View.VISIBLE);
                    } else if (((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                        OperationUtils.PutBoolean(OperationUtils.walletPassword, true);
                        mViewHolder.setVisibility(R.id.setts_set_wallet_password, View.GONE);
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
            case R.id.setts_set_wallet_password://设置钱包密码
                startActivity(new Intent(TCApplication.mContext, SetWalletActivity.class));
                break;
            case R.id.setts_retrieve_password_relayout://找回钱包密码

                break;
            case R.id.setts_change_password_relayout://修改钱包密码

                break;
        }
    }
}
