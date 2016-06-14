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
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.Misc;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;
import app.net.tongcheng.view.LineEditText;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_layout);
        setTitle("修改密码");
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
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.SUMBIT:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    TCApplication.getmUserInfo().setPwd(Misc.cryptDataByPwd(password.trim()));
                    TCApplication.setmUserInfo(TCApplication.getmUserInfo());
                    DialogUtil.showTipsDialog(this, "密码修改成功!", new DialogUtil.OnConfirmListener() {
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
    public void BusinessOnFail(int mLoding_Type) {
        ToastUtil.showToast("网络不可用,请检查网络连接");
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
                if (password.length() < 4) {
                    ToastUtil.showToast("新密码太短!");
                    return;
                }
                if (password.equals(oldpassword)) {
                    ToastUtil.showToast("新密码不能与旧密码相同!");
                    return;
                }
                mOtherBusiness.registerChangePassword(APPCationStation.SUMBIT, "提交中...", TCApplication.getmUserInfo().getPhone(), Misc.cryptDataByPwd(oldpassword), password);
                break;
        }

    }
}