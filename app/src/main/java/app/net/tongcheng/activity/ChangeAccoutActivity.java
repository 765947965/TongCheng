package app.net.tongcheng.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import app.net.tongcheng.Business.OtherBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ChangeAccoutModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.OraLodingUser;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.OraLodingUserTools;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.VerificationCode;
import app.net.tongcheng.util.ViewHolder;

/**
 * Created by 76594 on 2016/6/17.
 */
public class ChangeAccoutActivity extends BaseActivity implements View.OnClickListener, DialogUtil.InputPasswordListener {

    private ViewHolder mViewHolder;
    private EditText et_new_phone, et_new_phone_agen, et_password;
    private OtherBusiness mOtherBusiness;
    private String Code = VerificationCode.getCode();
    private AlertDialog dialog;
    private String newPhone, newPhoneAgen, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_accout_layout);
        initView();
        mOtherBusiness = new OtherBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        et_new_phone = mViewHolder.getView(R.id.et_new_phone);
        et_new_phone_agen = mViewHolder.getView(R.id.et_new_phone_agen);
        et_password = mViewHolder.getView(R.id.et_password);
        mViewHolder.setOnClickListener(R.id.tv_sumbit);
    }

    @Override
    public void loadData() {
        mViewHolder.setText(R.id.tv_old_phone, TCApplication.getmUserInfo().getPhone());
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.LOADING:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    if (dialog != null && !isFinishing()) {
                        dialog.show();
                        ((EditText) dialog.findViewById(R.id.et_password)).setText("");
                    } else {
                        dialog = DialogUtil.showInoutPasswordDialog(this, this);
                    }
                }
                break;
            case APPCationStation.SUMBIT:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    ChangeAccoutModel mChangeAccoutModel = (ChangeAccoutModel) mConnectResult.getObject();
                    if (mChangeAccoutModel != null && !TextUtils.isEmpty(mChangeAccoutModel.getUid())) {
                        TCApplication.getmUserInfo().setPhone(newPhone);
                        TCApplication.getmUserInfo().setUid(mChangeAccoutModel.getUid());
                        TCApplication.setmUserInfo(TCApplication.getmUserInfo());
                        OraLodingUserTools.addolus(TCApplication.mContext, new OraLodingUser(TCApplication.getmUserInfo().getPhone(), System.currentTimeMillis()));
                        sendEventBusMessage("ALL.Refresh");
                        sendEventBusMessage("ALL.UpData");
                        DialogUtil.showTipsDialog(this, "更改成功!", new DialogUtil.OnConfirmListener() {
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
        }
    }

    @Override
    public void BusinessOnFail(int mLoding_Type) {
        ToastUtil.showToast("网络不可用,请检查网络连接");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sumbit:
                newPhone = et_new_phone.getText().toString();
                newPhoneAgen = et_new_phone_agen.getText().toString();
                password = et_password.getText().toString();
                if (TextUtils.isEmpty(newPhone) || newPhone.length() != 11) {
                    ToastUtil.showToast("请输入正确的新手机号码!");
                } else if (TextUtils.isEmpty(newPhoneAgen) || newPhoneAgen.length() != 11) {
                    ToastUtil.showToast("请输入正确的重复新手机号码!");
                } else if (!newPhone.equals(newPhoneAgen)) {
                    ToastUtil.showToast("两次输入的新手机号码不一致!");
                } else if (TCApplication.getmUserInfo().getPhone().equals(newPhoneAgen)) {
                    ToastUtil.showToast("新手机号码不能与旧手机号码一致!");
                } else if (TextUtils.isEmpty(password)) {
                    ToastUtil.showToast("请输入原设密码!");
                } else {
                    mOtherBusiness.getChangeAccoutCode(APPCationStation.LOADING, "获取验证码", Code);
                }
                break;
        }
    }

    @Override
    public void onSureInout(String code) {
        if (!Code.equals(code)) {
            ((TextView) dialog.findViewById(R.id.tv_title)).setText("验证码错误，请重新输入!");
            ((EditText) dialog.findViewById(R.id.et_password)).setText("");
        } else {
            dialog.dismiss();
            mOtherBusiness.changeAccout(APPCationStation.SUMBIT, "提交中...", TCApplication.getmUserInfo().getPhone(), newPhone, password);
        }
    }
}
