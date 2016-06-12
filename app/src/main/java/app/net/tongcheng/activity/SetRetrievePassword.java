package app.net.tongcheng.activity;

import android.content.Intent;
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
import app.net.tongcheng.util.DialogUtil.OnConfirmListener;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.Utils;
import app.net.tongcheng.util.ViewHolder;
import app.net.tongcheng.view.LineEditText;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/12 11:13
 */
public class SetRetrievePassword extends BaseActivity implements View.OnClickListener {

    private ViewHolder mViewHolder;
    private LineEditText setrpd4v2_phnum;
    private OtherBusiness mOtherBusiness;
    private String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_retriever_password_layout);
        setTitle("忘记密码");
        initView();
        mOtherBusiness = new OtherBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        setrpd4v2_phnum = mViewHolder.getView(R.id.setrpd4v2_phnum);
        mViewHolder.setOnClickListener(R.id.setrpd4v2bt);
    }

    @Override
    public void loadData() {
        mHandler.sendEmptyMessage(10001);
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                if (TCApplication.getmUserInfo() != null) {
                    setrpd4v2_phnum.setText(TCApplication.getmUserInfo().getPhone());
                }
                Utils.setInputMethodVisiable(setrpd4v2_phnum, 250);
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.SUMBIT:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    DialogUtil.showTipsDialog(this, "验证码已发送至您手机上,请注意查收!", new OnConfirmListener() {
                        @Override
                        public void clickConfirm() {
                            startActivity(new Intent(TCApplication.mContext, SetRetrievePasswordInputCOde.class).putExtra("phone", phone));
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setrpd4v2bt:
                phone = setrpd4v2_phnum.getText().toString();
                if (TextUtils.isEmpty(phone) || phone.length() != 11) {
                    ToastUtil.showToast("请输入正确的手机号码");
                    return;
                }
                DialogUtil.showTipsDialog(this, "提示", "同城商城将验证码发送到+86" + phone, "确定", "取消", new OnConfirmListener() {
                    @Override
                    public void clickConfirm() {
                        mOtherBusiness.getPassword(APPCationStation.SUMBIT, "请求中...", phone);
                    }

                    @Override
                    public void clickCancel() {

                    }
                });
                break;
        }
    }
}
