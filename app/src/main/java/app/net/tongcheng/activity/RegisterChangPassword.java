package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import app.net.tongcheng.Business.OtherBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.StartPageModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.Misc;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.GeneralDateUtils;
import app.net.tongcheng.util.Utils;
import app.net.tongcheng.util.ViewHolder;

/**
 * Created by 76594 on 2016/5/31.
 */
public class RegisterChangPassword extends BaseActivity implements View.OnClickListener {
    private ViewHolder mViewHolder;
    private EditText rg4v2_npwdcodeinput;
    private String password;
    private OtherBusiness mOtherBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_change_password);
        setTitle("修改密码");
        initView();
        setCheckLoad(false);
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
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {
        switch (mLoadType) {
            case APPCationStation.SUMBIT:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    TCApplication.getmUserInfo().setPwd(Misc.cryptDataByPwd(password.trim()));
                    TCApplication.setmUserInfo(TCApplication.getmUserInfo());
                    DialogUtil.showTipsDialog(this, "密码修改成功!", new DialogUtil.OnConfirmListener() {
                        @Override
                        public void clickConfirm() {
                            boolean isStartTX = GeneralDateUtils.getBoolean(Utils.getVersionName());
                            if (!isStartTX) {
                                // 开启新特性
                                GeneralDateUtils.PutBoolean(Utils.getVersionName(), true);
                                RegisterChangPassword.this.startActivity(new Intent(TCApplication.mContext, NewVerTXActivity.class).putExtra("isMain", true));
                            } else {
                                // 开启主页
                                RegisterChangPassword.this.startActivity(new Intent(TCApplication.mContext, MainActivity.class));
                            }
                            Map<String, String> map_value = new HashMap<>();
                            map_value.put("uuid", Misc.cryptDataByPwd(TCApplication.getmUserInfo().getPhone() + TCApplication.getmUserInfo().getPwd()));
                            MobclickAgent.onEventValue(RegisterChangPassword.this, "Register", map_value, 1);
                            RegisterChangPassword.this.finish();
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
        ToastUtil.showToast("网络不可用，请检查网络连接！");
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
                StartPageModel mStartPageModel = NativieDataUtils.getStartPageModel(true);
                if (mStartPageModel != null) {
                    // 开启启动页
                    RegisterChangPassword.this.startActivity(new Intent(TCApplication.mContext, StartPageActivity.class).putExtra("mStartPageModel", mStartPageModel));
                } else {
                    // 开启主页
                    RegisterChangPassword.this.startActivity(new Intent(TCApplication.mContext, MainActivity.class));
                }
                Map<String, String> map_value = new HashMap<>();
                map_value.put("uuid", Misc.cryptDataByPwd(TCApplication.getmUserInfo().getPhone() + TCApplication.getmUserInfo().getPwd()));
                MobclickAgent.onEventValue(RegisterChangPassword.this, "Register", map_value, 1);
                RegisterChangPassword.this.finish();
            }

            @Override
            public void clickCancel() {

            }
        });
    }
}
