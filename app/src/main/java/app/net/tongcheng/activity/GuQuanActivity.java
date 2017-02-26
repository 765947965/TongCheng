package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import app.net.tongcheng.Business.RedBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.MoneyInfoModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.ErrorInfoUtil;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;
import app.net.tongcheng.view.LineEditText;
import app.net.tongcheng.view.OnListnerShearch;

/**
 * Created by 76594 on 2017/2/25.
 */
public class GuQuanActivity extends BaseActivity implements View.OnClickListener, OnListnerShearch {
    private ViewHolder mViewHolder;
    private MoneyInfoModel mMoneyInfoModel;
    private RedBusiness mRedBusiness;
    private LineEditText et_phone, et_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guquan);
        setTitle("给我的直推用户充值转账");
        mMoneyInfoModel = (MoneyInfoModel) getIntent().getSerializableExtra(Common.AGR1);
        initView();
        mRedBusiness = new RedBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        et_phone = mViewHolder.getView(R.id.et_phone);
        et_money = mViewHolder.getView(R.id.et_money);
        mViewHolder.setOnClickListener(R.id.etPut);
        et_phone.setShearchListner(this);
        et_money.setShearchListner(this);
        mViewHolder.setText(R.id.tvTips, mMoneyInfoModel.getData().getFreze_account_key() + "余额:" + String.valueOf(mMoneyInfoModel.getData().getFreze_account() / 100d) + "\r\n我成为代理时间:" + mMoneyInfoModel.getData().getIs_agent_time());
        mViewHolder.setText(R.id.tvInputTips, mMoneyInfoModel.getData().getAgent_remit_account_tips());
    }


    @Override
    public void loadData() {

    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mHttpLoadType, ConnectResult mConnectResult) {
        switch (mHttpLoadType) {
            case APPCationStation.SUMBIT:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    DialogUtil.showTipsDialog(this, "充值成功", new DialogUtil.OnConfirmListener() {
                        @Override
                        public void clickConfirm() {
                            sendEventBusMessage("money_rushe");
                            finish();
                        }

                        @Override
                        public void clickCancel() {

                        }
                    });
                } else {
                    String message = "充值失败!";
                    if (mConnectResult != null && mConnectResult.getObject() != null) {
                        String mErrorMessage = TextUtils.isEmpty(((BaseModel) mConnectResult.getObject()).getMessage()) ? ErrorInfoUtil.getErrorMessage(((BaseModel) mConnectResult.getObject()).getResult()) : ((BaseModel) mConnectResult.getObject()).getMessage();
                        message = TextUtils.isEmpty(mErrorMessage) ? message : mErrorMessage;
                    }
                    DialogUtil.showTipsDialog(this, message, null);
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mHttpLoadType) {
        DialogUtil.showTipsDialog(this, "网络不可用,请检查网络连接!", null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.etPut:
                try {
                    final int inputMoney = Integer.valueOf(et_money.getText().toString());
                    if (inputMoney <= 0) {
                        ToastUtil.showToast("输入的金额必须大于0");
                    } else if (inputMoney > mMoneyInfoModel.getData().getFreze_account() / 100) {
                        ToastUtil.showToast("输入的金额不能大于赠送期权余额");
                    } else if (!checkInput(inputMoney)) {
                        ToastUtil.showToast("请输入正确的合伙人套餐金额");
                    } else {
                        DialogUtil.showTipsDialog(this, "请确认手机号", et_phone.getText().toString(), "确定", "取消", new DialogUtil.OnConfirmListener() {
                            @Override
                            public void clickConfirm() {
                                mRedBusiness.zsQiQuan(APPCationStation.SUMBIT, "提交中...", et_phone.getText().toString(), inputMoney);
                            }

                            @Override
                            public void clickCancel() {

                            }
                        });
                    }
                } catch (Exception e) {
                    ToastUtil.showToast("请正确的金额");
                }
                break;
        }
    }

    private boolean checkInput(int inputMoney) {
        // 输入合法返回true 输入不合法返回false;
        if (mMoneyInfoModel == null || mMoneyInfoModel.getData().getGoods_price() == null || mMoneyInfoModel.getData().getGoods_price().size() == 0) {
            return false;
        } else {
            for (Integer ig : mMoneyInfoModel.getData().getGoods_price()) {
                if (ig != null && ig == inputMoney) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public void Search(String text) {
        if (et_phone.getText().toString().length() > 0) {
            mViewHolder.setVisibility(R.id.rl4v2_clearpnum, View.VISIBLE);
        } else {
            mViewHolder.setVisibility(R.id.rl4v2_clearpnum, View.GONE);
        }
        if (et_phone.getText().toString().length() == 11 && et_money.getText().toString().length() > 0) {
            mViewHolder.getView(R.id.etPut).setEnabled(true);
        } else {
            mViewHolder.getView(R.id.etPut).setEnabled(false);
        }
    }
}
