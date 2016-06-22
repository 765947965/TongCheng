package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import app.net.tongcheng.Business.RedBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.CardListModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.MoneyInfoModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.Utils;
import app.net.tongcheng.util.ViewHolder;

/**
 * Created by 76594 on 2016/6/18.
 */
public class MoneyOutInputActivity extends BaseActivity implements View.OnClickListener {
    private ViewHolder mViewHolder;
    private CardListModel.DataBean mCardListModelDataBean;
    private MoneyInfoModel mMoneyInfoModel;
    private EditText money_input;
    private RedBusiness mRedBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money_out_input_layout);
        setTitle("提现");
        findViewById(R.id.viewBaseLine).setVisibility(View.GONE);
        initView();
        mRedBusiness = new RedBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        mViewHolder.setOnClickListener(R.id.bt_withdraw_action);
        money_input = mViewHolder.getView(R.id.money_input);
        Utils.limitDecimalDigits(money_input, 2);
        mViewHolder.setOnClickListener(R.id.tv_description);
    }

    @Override
    public void loadData() {
        mCardListModelDataBean = (CardListModel.DataBean) getIntent().getSerializableExtra("CardListModel.DataBean");
        mMoneyInfoModel = (MoneyInfoModel) getIntent().getSerializableExtra("MoneyInfoModel");
        if (mCardListModelDataBean != null && mMoneyInfoModel != null) {
            mViewHolder.setText(R.id.tv_canout, "可提现: " + mMoneyInfoModel.getData().getCanfetch_amount() / 100d);
            mViewHolder.setText(R.id.tv_banck_name, mCardListModelDataBean.getBank_name());
            mViewHolder.setText(R.id.tv_banck_card, "储蓄卡 " + "(****" + mCardListModelDataBean.getBank_card_no().substring(mCardListModelDataBean.getBank_card_no().length() - 4) + ")");
            mViewHolder.setText(R.id.tv_description, Html.fromHtml("<u>" + mMoneyInfoModel.getData().getDescription() + "<u>"));
            mViewHolder.setText(R.id.tv_tips, TextUtils.isEmpty(mMoneyInfoModel.getData().getTips()) ? "" : mMoneyInfoModel.getData().getTips());
        }
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.MONEYOUT://提现
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    sendEventBusMessage("money_rushe");
                    DialogUtil.showTipsDialog(this, "提现成功!", new DialogUtil.OnConfirmListener() {
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
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoding_Type) {
        ToastUtil.showToast("网络不可用,请检查网络连接!");
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
                    final String outMoney = money_input.getText().toString();
                    if (TextUtils.isEmpty(outMoney)) {
                        ToastUtil.showToast("请输入提现金额!");
                    } else if (Double.valueOf(outMoney) <= 0) {
                        ToastUtil.showToast("提现金额必须大于0!");
                    } else if (Double.valueOf(outMoney) > mMoneyInfoModel.getData().getCanfetch_amount() / 100d) {
                        ToastUtil.showToast("提现金额不能大于可提现金额!");
                    } else {
                        DialogUtil.showTipsDialog(this, "提示", mMoneyInfoModel.getData().getDescription() + "预计到账" + Double.valueOf(outMoney) * 0.9d + "元", "确定", "取消", new DialogUtil.OnConfirmListener() {
                            @Override
                            public void clickConfirm() {
                                mRedBusiness.moneyOut(APPCationStation.MONEYOUT, "提现中...", mCardListModelDataBean.getId(), Double.valueOf(outMoney) * 100d);
                            }

                            @Override
                            public void clickCancel() {

                            }
                        });
                    }
                } catch (Exception e) {
                    ToastUtil.showToast("请输入正确的提现金额!");
                }
                break;
            case R.id.tv_description:
                startActivity(new Intent(TCApplication.mContext, PublicWebview.class).putExtra("title", "手续费").putExtra("url", "http://user.8hbao.com:8060/service_fee.html"));
                break;
        }
    }
}
