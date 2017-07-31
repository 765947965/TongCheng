package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.net.tongcheng.Business.MyBusiness;
import app.net.tongcheng.Business.OtherBusiness;
import app.net.tongcheng.Business.RedBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.CardListModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.MoneyInfoModel;
import app.net.tongcheng.model.MoneyOutInputBean;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.ErrorInfoUtil;
import app.net.tongcheng.util.OperationUtils;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.Utils;
import app.net.tongcheng.util.ViewHolder;
import app.net.tongcheng.view.InputObjectDialog;
import okhttp3.Response;

/**
 * Created by 76594 on 2016/6/18.
 */
public class MoneyOutInputActivity extends BaseActivity implements View.OnClickListener, InputObjectDialog.InvestPayObjectDialogListener {
    private ViewHolder mViewHolder;
    private CardListModel.DataBean mCardListModelDataBean;
    private MoneyInfoModel mMoneyInfoModel;
    private EditText money_input;
    private Button bt_withdraw_action;
    private RedBusiness mRedBusiness;
    private OtherBusiness mOtherBusiness;
    private InputObjectDialog mDialog;
    private String outMoney;
    private double realAmount;//真实可提现金额
    private MyBusiness mMyBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money_out_input_layout);
        setTitle("提现");
        findViewById(R.id.viewBaseLine).setVisibility(View.GONE);
        initView();
        mRedBusiness = new RedBusiness(this, this, mHandler);
        mOtherBusiness = new OtherBusiness(this, this, mHandler);
        mMyBusiness = new MyBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        bt_withdraw_action = mViewHolder.setOnClickListener(R.id.bt_withdraw_action);
        money_input = mViewHolder.getView(R.id.money_input);
        Utils.limitDecimalDigits(money_input, 2);
        mViewHolder.setOnClickListener(R.id.tv_description);
        mViewHolder.setOnClickListener(R.id.tv_no_card);
    }

    @Override
    public void loadData() {
        mCardListModelDataBean = (CardListModel.DataBean) getIntent().getSerializableExtra("CardListModel.DataBean");
        mMoneyInfoModel = (MoneyInfoModel) getIntent().getSerializableExtra("MoneyInfoModel");
        realAmount = (mMoneyInfoModel.getData().getCanfetch_amount() - mMoneyInfoModel.getData().getMin_cash_amount());
        if (mCardListModelDataBean != null && mMoneyInfoModel != null) {
            mViewHolder.setText(R.id.tv_canout, "可提现: " + (realAmount / 100d > 0 ? realAmount / 100d : 0));
            mViewHolder.setText(R.id.tv_banck_name, mCardListModelDataBean.getBank_name());
            mViewHolder.setText(R.id.tv_banck_card, "储蓄卡 " + "(****" + mCardListModelDataBean.getBank_card_no().substring(mCardListModelDataBean.getBank_card_no().length() - 4) + ")");
            mViewHolder.setText(R.id.tv_description, Html.fromHtml("<u>" + mMoneyInfoModel.getData().getDescription_plus() + "</u>"));
            mViewHolder.setText(R.id.tv_tips, TextUtils.isEmpty(mMoneyInfoModel.getData().getTips_plus()) ? "" : mMoneyInfoModel.getData().getTips_plus());
        }
        if (!OperationUtils.getBoolean(OperationUtils.hadCertification, true)) {
            mHandler.sendEmptyMessageDelayed(10001, 200);
        }
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                mMyBusiness.queryCertificationStatus(APPCationStation.CHECK, "");
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {
        switch (mLoadType) {
            case APPCationStation.MONEYOUT://提现
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    sendEventBusMessage("money_rushe");
                    DialogUtil.showTipsDialog(this, ((MoneyOutInputBean) mConnectResult.getObject()).getMessage(), new DialogUtil.OnConfirmListener() {
                        @Override
                        public void clickConfirm() {
                            finish();
                        }

                        @Override
                        public void clickCancel() {

                        }
                    });
                    Map<String, String> map_value = new HashMap<>();
                    map_value.put("bank_name", mCardListModelDataBean.getBank_name());
                    map_value.put("branch_name", mCardListModelDataBean.getBranch_name());
                    map_value.put("card_holder", mCardListModelDataBean.getCard_holder());
                    map_value.put("bank_card_no", mCardListModelDataBean.getBank_card_no());
                    map_value.put("withdrawmoney", money_input.getText().toString());
                    MobclickAgent.onEventValue(this, "withdraw", map_value, Double.valueOf(money_input.getText().toString()).intValue());
                } else {
                    String message = "提现失败!";
                    if (mConnectResult != null && mConnectResult.getObject() != null) {
                        String mErrorMessage = TextUtils.isEmpty(((BaseModel) mConnectResult.getObject()).getMessage()) ? ErrorInfoUtil.getErrorMessage(((BaseModel) mConnectResult.getObject()).getResult()) : ((BaseModel) mConnectResult.getObject()).getMessage();
                        message = TextUtils.isEmpty(mErrorMessage) ? message : mErrorMessage;
                    }
                    DialogUtil.showTipsDialog(this, message, null);
                }
                break;
            case APPCationStation.CHECKWALLETPASSWORD:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                    mRedBusiness.moneyOut(APPCationStation.MONEYOUT, "提现中...", mCardListModelDataBean.getId(), Double.valueOf(outMoney) * 100d);
                } else {
                    if (mDialog != null) {
                        mDialog.submitInputFailure();
                    }
                }
                break;
            case APPCationStation.CHECK:
                try {
                    String jsonStr = (String) mConnectResult.getObject();
                    JSONObject json = new JSONObject(jsonStr);
                    if (json.getInt("result") == 41) {
                        OperationUtils.PutBoolean(OperationUtils.hadCertification, true, true);
                        sendEventBusMessage("MyFragment.Refresh");
                    } else if (json.getInt("result") == 42) {
                        mViewHolder.setVisibility(R.id.tv_no_card, View.VISIBLE);
                        mViewHolder.setText(R.id.tv_no_card, Html.fromHtml("<font color=#FF6666>您账号当前未进行实名认证不能提现，请先到\"我\"-->\"账号实名认证\"进行实名认证。</font><br><font color=#0C82F5><u>现在就去实名认证</u></font>"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoadType, Response response) {
        switch (mLoadType) {
            case APPCationStation.CHECKWALLETPASSWORD:
                if (mDialog != null) {
                    mDialog.submitInputFailure();
                }
                break;
        }
        DialogUtil.showTipsDialog(this, "网络不可用,请检查网络连接!", null);
    }

    @Override
    public void onClick(View v) {
        if (mCardListModelDataBean == null || mMoneyInfoModel == null) {
            ToastUtil.showToast("获取提现信息失败!");
            return;
        }
        switch (v.getId()) {
            case R.id.bt_withdraw_action:
                try {
                    outMoney = money_input.getText().toString();
                    if (realAmount * (1d - mMoneyInfoModel.getData().getFee_ratio()) - mMoneyInfoModel.getData().getExtra_fee() <= 0) {
                        DialogUtil.showTipsDialog(this, mMoneyInfoModel.getData().getDescription_plus() + " 当前可提现余额不足，无法提现!", "确定", null);
                    } else if (TextUtils.isEmpty(outMoney)) {
                        DialogUtil.showTipsDialog(this, "请输入提现金额!", "确定", null);
                    } else if (Double.valueOf(outMoney) * (1d - mMoneyInfoModel.getData().getFee_ratio()) - mMoneyInfoModel.getData().getExtra_fee() / 100 <= 0) {
                        DialogUtil.showTipsDialog(this, mMoneyInfoModel.getData().getDescription_plus() + " 当前输入金额不足于扣除手续费和银行转账费用，无法提现！", "确定", null);
                    } else if (Double.valueOf(outMoney) > realAmount / 100d) {
                        DialogUtil.showTipsDialog(this, "提现金额不能大于可提现金额", "确定", null);
                    } else {
//                        DialogUtil.showTipsDialog(this, "提示", mMoneyInfoModel.getData().getDescription_plus() + "预计到账" + Double.valueOf(outMoney) * (1d - mMoneyInfoModel.getData().getFee_ratio()) + "元", "确定", "取消", new DialogUtil.OnConfirmListener() {
//                            @Override
//                            public void clickConfirm() {
//                                mRedBusiness.moneyOut(APPCationStation.MONEYOUT, "提现中...", mCardListModelDataBean.getId(), Double.valueOf(outMoney) * 100d);
//                            }
//
//                            @Override
//                            public void clickCancel() {
//
//                            }
//                        });
                        if (mDialog == null) {
                            mDialog = new InputObjectDialog(MoneyOutInputActivity.this, true, MoneyOutInputActivity.this);
                        }
                        mDialog.showPasswordDialog((Double.valueOf(outMoney) * (1d - mMoneyInfoModel.getData().getFee_ratio()) - mMoneyInfoModel.getData().getExtra_fee() / 100d), mMoneyInfoModel.getData().getDescription_plus() + " 预计到账");
                    }
                } catch (Exception e) {
                    ToastUtil.showToast("请输入正确的提现金额!");
                }
                break;
            case R.id.tv_description:
                startActivity(new Intent(TCApplication.mContext, PublicWebview.class).putExtra("title", "手续费").putExtra("url", "http://user.zjtongchengshop.com:8060/service_fee.html"));
                break;
            case R.id.tv_no_card:
                startActivity(new Intent(this, SubmitCertification.class));
                break;
        }
    }

    @Override
    public void submitPassword(String password) {
        mOtherBusiness.checkWalletPassword(APPCationStation.CHECKWALLETPASSWORD, "校验中...", password);
    }

    @Override
    public void submitCode(String code) {

    }

    @Override
    public void getCode(InputObjectDialog.InvestSendCodeType mType) {

    }
}
