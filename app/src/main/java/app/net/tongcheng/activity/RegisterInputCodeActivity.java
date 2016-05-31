package app.net.tongcheng.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import org.greenrobot.eventbus.Subscribe;

import app.net.tongcheng.Business.OtherBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.CheckEvent;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.RegisterCode;
import app.net.tongcheng.model.UserInfo;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.OperationUtils;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/5/30 17:33
 */
public class RegisterInputCodeActivity extends BaseActivity implements View.OnClickListener {
    private ViewHolder mViewHolder;
    private String phone, invite_code;
    private EditText rgv2_phnum;
    private TextView cannotsevedcode;
    private int jishunum = Common.LasMine;
    private OtherBusiness mOtherBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_input_aouthcode_layout);
        setTitle("填写验证码");
        phone = getIntent().getStringExtra("phone");
        invite_code = getIntent().getStringExtra("invite_code");
        if (TextUtils.isEmpty(invite_code)) {
            invite_code = "0";
        }
        initView();
        setEventBus();
        mOtherBusiness = new OtherBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        cannotsevedcode = mViewHolder.getView(R.id.cannotsevedcode);
        rgv2_phnum = mViewHolder.getView(R.id.rgv2_phnum);
        mViewHolder.setOnClickListener(R.id.cannotsevedcode);
        mViewHolder.setOnClickListener(R.id.rg4v2_code_regorlog);
        getLeftClose().setOnClickListener(this);
    }

    @Override
    public void loadData() {
        mViewHolder.setText(R.id.yzcode_phnum, "验证码已发送至 +86" + phone);
        cannotsevedcode.setTextColor(Color.parseColor("#ababab"));
        cannotsevedcode.setEnabled(false);
        mHandler.sendEmptyMessage(1001);
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 1001:
                jishunum -= 1;
                if (jishunum < 0) {
                    jishunum = Common.LasMine;
                    mViewHolder.setText(R.id.timejs, "");
                    cannotsevedcode.setTextColor(Color.parseColor("#1160FD"));
                    cannotsevedcode.setEnabled(true);
                } else {
                    mViewHolder.setText(R.id.timejs, jishunum + "秒");
                    mHandler.sendEmptyMessageDelayed(1001, 1000);
                }
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.GETAOUTHCODE:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0 && !TextUtils.isEmpty(((RegisterCode) mConnectResult.getObject()).getAuthcode())) {
                    mViewHolder.setText(R.id.timejs, "请输入该验证码:" + ((RegisterCode) mConnectResult.getObject()).getAuthcode());
                } else {
                    mHandler.sendEmptyMessage(1001);
                }
                break;
            case APPCationStation.SUMBIT:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    UserInfo mUserInfo = (UserInfo) mConnectResult.getObject();
                    TCApplication.setmUserInfo(mUserInfo);
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoding_Type) {
        switch (mLoding_Type) {
            case APPCationStation.GETAOUTHCODE:
                cannotsevedcode.setTextColor(Color.parseColor("#1160FD"));
                cannotsevedcode.setEnabled(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cannotsevedcode:
                DialogUtil.showTipsDialog(this, "提示", "是否再次发送验证码?", "确定", "取消", new DialogUtil.OnConfirmListener() {
                    @Override
                    public void clickConfirm() {
                        cannotsevedcode.setTextColor(Color.parseColor("#ababab"));
                        cannotsevedcode.setEnabled(false);
                        mOtherBusiness.getRegisterAouthCode(APPCationStation.GETAOUTHCODE, "获取验证码...", phone);
                    }

                    @Override
                    public void clickCancel() {

                    }
                });
                break;
            case R.id.rg4v2_code_regorlog:
                if (TextUtils.isEmpty(rgv2_phnum.getText().toString())) {
                    ToastUtil.showResultToast("验证码不能为空!");
                    return;
                }
                mOtherBusiness.registerBusiness(APPCationStation.SUMBIT, "注册中...", phone, invite_code, rgv2_phnum.getText().toString());
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

    private void goWillBack(){
        DialogUtil.showTipsDialog(this, "提示", "验证码已下发，确定返回?", "确定", "取消", new DialogUtil.OnConfirmListener() {
            @Override
            public void clickConfirm() {
                RegisterInputCodeActivity.this.finish();
            }

            @Override
            public void clickCancel() {

            }
        });
    }

    @Subscribe
    public void onEvent(CheckEvent event) {
        if (event != null && event.getMsg().equals("loading_ok")) {
            finish();
        }
    }
}
