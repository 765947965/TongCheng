package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import app.net.tongcheng.Business.OtherBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.StartPageModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.Misc;
import app.net.tongcheng.util.OperationUtils;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;

/**
 * Created by 76594 on 2016/5/31.
 */
public class RegisterChangPassword extends BaseActivity implements View.OnClickListener {
    private ViewHolder mViewHolder;
    private EditText rg4v2_npwdcodeinput;
    private String password;
    private OtherBusiness mOtherBusiness;
    private String startPageUrl;
    private int times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_change_password);
        initView();
        mOtherBusiness = new OtherBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        rg4v2_npwdcodeinput = mViewHolder.getView(R.id.rg4v2_npwdcodeinput);
        mViewHolder.setOnClickListener(R.id.rg4v2_surecpwdbt);
        mViewHolder.setOnClickListener(R.id.rg4v2_not_surecpwdbt);
        getLeftClose().setOnClickListener(this);
    }

    @Override
    public void loadData() {
        mViewHolder.setText(R.id.rg4v2_pwdcodeshow, "您的密码为: " + Misc.cryptDataByPwd(TCApplication.getmUserInfo().getPwd()));
        mOtherBusiness.getStartPageImage(APPCationStation.GETSTARTPAGE, "");
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
                            if (startPageUrl != null) {
                                // 开启启动页
                                RegisterChangPassword.this.startActivity(new Intent(TCApplication.mContext, StartPageActivity.class).putExtra("startPageUrl", startPageUrl).putExtra("times", times + 1));
                            } else {
                                // 开启主页
                                RegisterChangPassword.this.startActivity(new Intent(TCApplication.mContext, MainActivity.class));
                            }
                            RegisterChangPassword.this.finish();
                        }

                        @Override
                        public void clickCancel() {

                        }
                    });
                }
                break;
            case APPCationStation.GETSTARTPAGE:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    StartPageModel mStartPageModel = (StartPageModel) mConnectResult.getObject();
                    long nowTime = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()));
                    long startTime = Long.parseLong(mStartPageModel.getStart_time());
                    long endTime = Long.parseLong(mStartPageModel.getEnd_time());
                    String times_str = OperationUtils.getString("start_page_show_times");
                    times = 0;
                    if (!TextUtils.isEmpty(times_str)) {
                        if (times_str.substring(0, 8).equals(new SimpleDateFormat("yyyyMMdd").format(new Date()))) {
                            times = Integer.parseInt(times_str.substring(8));
                        }
                    }
                    if (nowTime >= startTime && nowTime <= endTime && times <= mStartPageModel.getShow_times_daily()) {
                        startPageUrl = mStartPageModel.getPic_prefix() + mStartPageModel.getPic_xhdpi();
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
            case R.id.bt_close:
            case R.id.rg4v2_not_surecpwdbt:
                goWillBack();
                break;
            case R.id.rg4v2_surecpwdbt://修改密码
                password = rg4v2_npwdcodeinput.getText().toString();
                if (TextUtils.isEmpty(password)) {
                    ToastUtil.showResultToast("新密码不能为空!");
                    return;
                }
                if (!password.trim().matches("[0-9a-zA-Z]+")) {
                    ToastUtil.showResultToast("密码只能由数字或字母组成!");
                    return;
                }
                if (password.length() < 4) {
                    ToastUtil.showResultToast("新密码太短!");
                    return;
                }
                mOtherBusiness.registerChangePassword(APPCationStation.SUMBIT, "提交中...", password);
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
                if (startPageUrl != null) {
                    // 开启启动页
                    RegisterChangPassword.this.startActivity(new Intent(TCApplication.mContext, StartPageActivity.class).putExtra("startPageUrl", startPageUrl).putExtra("times", times + 1));
                } else {
                    // 开启主页
                    RegisterChangPassword.this.startActivity(new Intent(TCApplication.mContext, MainActivity.class));
                }
                RegisterChangPassword.this.finish();
            }

            @Override
            public void clickCancel() {

            }
        });
    }
}
