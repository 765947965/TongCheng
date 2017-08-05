package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import app.net.tongcheng.Business.OtherBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.Misc;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;
import app.net.tongcheng.view.LineEditText;
import okhttp3.Response;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/12 14:48
 */
public class ChagnePassoword extends BaseActivity implements View.OnClickListener {


    private ViewHolder mViewHolder;
    private OtherBusiness mOtherBusiness;
    private LineEditText changepassword_old;
    private LineEditText changepassword_new;
    private String password;
    private int arg1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_layout);
        arg1 = getIntent().getIntExtra(Common.AGR1, 0);
        setTitle(arg1 == 0 ? "修改密码" : "更改钱包密码");
        initView();
        mOtherBusiness = new OtherBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        changepassword_old = mViewHolder.getView(R.id.changepassword_old);
        changepassword_new = mViewHolder.getView(R.id.changepassword_new);
        mViewHolder.setOnClickListener(R.id.changepassword_bt);
    }


    @Override
    public void loadData() {

    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {
        switch (mLoadType) {
            case APPCationStation.SUMBIT:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    if (arg1 == 0) {//修改登录密码
                        TCApplication.getmUserInfo().setPwd(Misc.cryptDataByPwd(password.trim()));
                        TCApplication.setmUserInfo(TCApplication.getmUserInfo());
                    }
                    DialogUtil.showTipsDialog(this, arg1 == 0 ? "密码修改成功" : "钱包密码修改成功", new DialogUtil.OnConfirmListener() {
                        @Override
                        public void clickConfirm() {
                            finish();
                        }

                        @Override
                        public void clickCancel() {

                        }
                    });
                    sendEventBusMessage("ALL.Refresh");
                    sendEventBusMessage("ALL.UpData");
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoadType, Response response) {
        if (response == null || response.code() != 403) {
            ToastUtil.showToast("网络不可用，请检查网络连接！");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changepassword_bt:
                String oldpassword = changepassword_old.getText().toString();
                password = changepassword_new.getText().toString();
                if (TextUtils.isEmpty(oldpassword)) {
                    ToastUtil.showToast("旧密码不能为空!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    ToastUtil.showToast("新密码不能为空!");
                    return;
                }
                if (!password.trim().matches("[0-9a-zA-Z]+")) {
                    ToastUtil.showToast("密码只能由数字或字母组成!");
                    return;
                }
                if (password.length() < 6) {
                    ToastUtil.showToast("新密码至少为6位!");
                    return;
                }
                if (password.equals(oldpassword)) {
                    ToastUtil.showToast("新密码不能与旧密码相同!");
                    return;
                }
                if (arg1 == 1) {
                    mOtherBusiness.changeWalletPassword(APPCationStation.SUMBIT, "提交中...", oldpassword, password);
                } else {
                    mOtherBusiness.registerChangePassword(APPCationStation.SUMBIT, "提交中...", TCApplication.getmUserInfo().getPhone(), Misc.cryptDataByPwd(oldpassword), password);
                }
                break;
        }

    }
}
