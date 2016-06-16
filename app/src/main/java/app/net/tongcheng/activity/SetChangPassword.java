package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

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

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/12 14:14
 */
public class SetChangPassword extends BaseActivity implements View.OnClickListener {


    private ViewHolder mViewHolder;
    private EditText rg4v2_npwdcodeinput;
    private String olpphone, oldpassword, password;
    private OtherBusiness mOtherBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_change_password);
        setTitle("修改密码");
        initView();
        mOtherBusiness = new OtherBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        rg4v2_npwdcodeinput = mViewHolder.getView(R.id.rg4v2_npwdcodeinput);
        mViewHolder.setOnClickListener(R.id.rg4v2_surecpwdbt);
        mViewHolder.setOnClickListener(R.id.rg4v2_not_surecpwdbt);
        mViewHolder.setVisibility(R.id.rg4v2_not_surecpwdbt, View.GONE);
        getLeftClose().setOnClickListener(this);
    }


    @Override
    public void loadData() {
        olpphone = getIntent().getStringExtra("olpphone");
        oldpassword = getIntent().getStringExtra("oldpassword");
        mViewHolder.setText(R.id.rg4v2_pwdcodeshow, "您的密码为: " + Misc.cryptDataByPwd(oldpassword));
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.SUMBIT:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    if (TCApplication.getmUserInfo() != null && olpphone.equals(TCApplication.getmUserInfo().getPhone())) {
                        TCApplication.getmUserInfo().setPwd(Misc.cryptDataByPwd(password.trim()));
                        TCApplication.setmUserInfo(TCApplication.getmUserInfo());
                        sendEventBusMessage("ALL.Refresh");
                        sendEventBusMessage("ALL.UpData");
                    }
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
            case R.id.rg4v2_surecpwdbt:
                password = rg4v2_npwdcodeinput.getText().toString();
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
                mOtherBusiness.registerChangePassword(APPCationStation.SUMBIT, "提交中...", olpphone, oldpassword, password);
                break;
            case R.id.bt_close:
                goWillBack();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 针对按返回键退出到注册登录界面
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goWillBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goWillBack() {
        DialogUtil.showTipsDialog(this, "提示", "确定不修改密码？", "确定", "取消", new DialogUtil.OnConfirmListener() {
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
