package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import app.net.tongcheng.Business.OtherBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.OperationUtils;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.Utils;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/11/17 20:24
 */

public class SetWalletActivity extends BaseActivity implements View.OnClickListener {

    private ViewHolder mViewHolder;
    private OtherBusiness mOtherBusiness;
    private EditText changepassword_old, changepassword_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_wallet_layout);
        setTitle("设置钱包密码");
        initView();
        mOtherBusiness = new OtherBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        mViewHolder.setOnClickListener(R.id.changepassword_bt);
        changepassword_old = mViewHolder.getView(R.id.changepassword_old);
        changepassword_new = mViewHolder.getView(R.id.changepassword_new);
    }

    @Override
    public void loadData() {
        mOtherBusiness.getWalletPasswordType(APPCationStation.LOADING, "加载中...");
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {
        switch (mLoadType) {
            case APPCationStation.LOADING:
                if (mConnectResult != null && mConnectResult.getObject() != null) {
                    BaseModel mBaseModel = (BaseModel) mConnectResult.getObject();
                    if (mBaseModel.getResult() == 81) {
                        mViewHolder.getView(R.id.changepassword_bt).setEnabled(true);
                    } else if (mBaseModel.getResult() == 0) {
                        OperationUtils.PutBoolean(OperationUtils.walletPassword, true);
                        DialogUtil.showTipsDialog(this, "您已设置过钱包密码!", new DialogUtil.OnConfirmListener() {
                            @Override
                            public void clickConfirm() {
                                finish();
                            }

                            @Override
                            public void clickCancel() {

                            }
                        });
                    }
                }
                break;
            case APPCationStation.SUMBIT:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    OperationUtils.PutBoolean(OperationUtils.walletPassword, true);
                    DialogUtil.showTipsDialog(this, "钱包密码设置成功!", new DialogUtil.OnConfirmListener() {
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
    public void BusinessOnFail(int mLoadType) {
        ToastUtil.showToast("网络不可用，请检查网络链接!");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changepassword_bt:
                setPassword();
                break;
        }
    }

    private void setPassword() {
        String newStr = changepassword_old.getText().toString();
        String newStrAgen = changepassword_new.getText().toString();
        if (TextUtils.isEmpty(newStr)) {
            changepassword_old.setError("钱包密码不能为空!");
            ToastUtil.showToast("钱包密码不能为空!");
        } else if (TextUtils.isEmpty(newStrAgen)) {
            changepassword_new.setError("确认钱包密码不能为空!");
            ToastUtil.showToast("确认钱包密码不能为空!");
        } else if (!Utils.checkPassword(newStr)) {
            changepassword_old.setError("钱包密码只能为数字或字母!");
            ToastUtil.showToast("钱包密码只能为数字或字母!");
        } else if (!Utils.checkPassword(newStrAgen)) {
            changepassword_new.setError("确认钱包密码只能为数字或字母!");
            ToastUtil.showToast("确认钱包密码只能为数字或字母!");
        } else if (newStr.length() < 6) {
            changepassword_old.setError("钱包密码至少为6位!");
            ToastUtil.showToast("钱包密码至少为6位!");
        } else if (newStrAgen.length() < 6) {
            changepassword_new.setError("确认钱包密码至少为6位!");
            ToastUtil.showToast("确认钱包密码至少为6位!");
        } else if (!newStr.equals(newStrAgen)) {
            changepassword_new.setError("确认密码和密码不一致!");
            ToastUtil.showToast("确认密码和密码不一致!");
        } else {
            mOtherBusiness.setWalletPassword(APPCationStation.SUMBIT, "提交中...", newStr);
        }
    }
}
