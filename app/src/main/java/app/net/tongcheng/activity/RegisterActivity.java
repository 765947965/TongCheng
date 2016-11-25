package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;

import app.net.tongcheng.Business.OtherBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.CheckEvent;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.RegisterInviteflagModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.Utils;
import app.net.tongcheng.util.VerificationCode;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/5/21 14:46
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private ViewHolder mViewHolder;
    private EditText et_phone, et_invite_code;
    private CheckBox checkBox;
    private TextView tv_protocol;
    private Button bt_register;
    private String phone, invite_code;
    private OtherBusiness mOtherBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        initView();
        setEventBus();
        setChechLoding(false);
        mOtherBusiness = new OtherBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.sll_main), this);
        et_phone = mViewHolder.getView(R.id.et_phone);
        et_invite_code = mViewHolder.getView(R.id.et_invite_code);
        checkBox = mViewHolder.getView(R.id.checkBox);
        tv_protocol = mViewHolder.getView(R.id.tv_protocol);
        bt_register = mViewHolder.getView(R.id.bt_register);
        mViewHolder.setOnClickListener(R.id.iv_clearphone);
        bt_register.setOnClickListener(this);
        et_phone.addTextChangedListener(this);
        tv_protocol.setOnClickListener(this);
    }


    @Override
    public void loadData() {
        Utils.setInputMethodVisiable(et_phone, 250);
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.CHECK:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    sendAouthCode();
                }
                break;
            case APPCationStation.GETAOUTHCODE:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    DialogUtil.showTipsDialog(this, "验证码已下发至您的手机,请注意查收!", new DialogUtil.OnConfirmListener() {
                        @Override
                        public void clickConfirm() {
                            startActivity(new Intent(TCApplication.mContext, RegisterInputCodeActivity.class).putExtra("phone", phone).putExtra("invite_code", invite_code));
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
        ToastUtil.showToast("网络不可用，请检查网络连接！");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clearphone://清除
                et_phone.setText("");
                break;
            case R.id.tv_protocol://查看合同
                startActivity(new Intent(TCApplication.mContext, PublicWebview.class).putExtra("title", "用户协议").putExtra("url", "http://user.8hbao.com/agreement.html?sn=" + VerificationCode.getCode2()));
                break;
            case R.id.bt_register://注册
                if (TextUtils.isEmpty(et_phone.getText().toString())) {
                    ToastUtil.showToast("请输入手机号码!");
                    return;
                }
                if (et_phone.getText().toString().length() != 11) {
                    ToastUtil.showToast("请输入正确的手机号码!");
                    return;
                }
                if (!checkBox.isChecked()) {
                    ToastUtil.showToast("请先阅读并同意用户协议!");
                    return;
                }
                phone = et_phone.getText().toString();
                invite_code = et_invite_code.getText().toString();
                if (!TextUtils.isEmpty(invite_code)) {
                    mOtherBusiness.registerInviteflagBusiness(APPCationStation.CHECK, "查询邀请码...", et_invite_code.getText().toString());
                } else {
                    sendAouthCode();
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    private void sendAouthCode() {
        DialogUtil.showTipsDialog(this, "提示", "同城商城将验证码发送到+86"
                + phone, "确定", "取消", new DialogUtil.OnConfirmListener() {
            @Override
            public void clickConfirm() {
                mOtherBusiness.getRegisterAouthCode(APPCationStation.GETAOUTHCODE, "获取验证码...", phone);
            }

            @Override
            public void clickCancel() {

            }
        });
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s.toString())) {
            mViewHolder.setVisibility(R.id.iv_clearphone, View.INVISIBLE);
        } else {
            mViewHolder.setVisibility(R.id.iv_clearphone, View.VISIBLE);
        }
        if (s.toString().length() == 11) {
            bt_register.setEnabled(true);
        } else {
            bt_register.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Subscribe
    public void onEvent(CheckEvent event) {
        if (event != null && event.getMsg().equals("loading_ok")) {
            finish();
        }
    }
}
