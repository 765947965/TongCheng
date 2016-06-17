package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/12 9:36
 */
public class AccountSetActivity extends BaseActivity implements View.OnClickListener {
    private ViewHolder mViewHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_set_layout);
        setTitle("账号设置");
        initView();
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        mViewHolder.setOnClickListener(R.id.setts_switch_on_relayout);
        mViewHolder.setOnClickListener(R.id.setts_retrieve_password_relayout);
        mViewHolder.setOnClickListener(R.id.setts_change_password_relayout);
        mViewHolder.setOnClickListener(R.id.setaccout_sign_out);
        mViewHolder.setOnClickListener(R.id.setts_change_accout);
    }

    @Override
    public void loadData() {

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
            case R.id.setts_switch_on_relayout:
                startActivity(new Intent(TCApplication.mContext, SwitchOnActivity.class));
                break;
            case R.id.setts_change_accout:
                startActivity(new Intent(TCApplication.mContext, ChangeAccoutActivity.class));
                break;
            case R.id.setts_retrieve_password_relayout:
                startActivity(new Intent(TCApplication.mContext, SetRetrievePassword.class));
                break;
            case R.id.setts_change_password_relayout:
                startActivity(new Intent(TCApplication.mContext, ChagnePassoword.class));
                break;
            case R.id.setaccout_sign_out:
                DialogUtil.showTipsDialog(this, "提示", "确定退出登录？", "确定", "取消", new DialogUtil.OnConfirmListener() {
                    @Override
                    public void clickConfirm() {
                        sendEventBusMessage("MainActivity.Close");
                        TCApplication.setmUserInfo(null);
                        startActivity(new Intent(TCApplication.mContext, LocationActivity.class));
                        finish();
                    }

                    @Override
                    public void clickCancel() {

                    }
                });
                break;
        }
    }
}
