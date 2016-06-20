package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import app.net.tongcheng.Business.RedBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.CardListModel;
import app.net.tongcheng.model.ConnectResult;
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
    private double money;
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
    }

    @Override
    public void loadData() {
        mCardListModelDataBean = (CardListModel.DataBean) getIntent().getSerializableExtra("CardListModel.DataBean");
        money = getIntent().getDoubleExtra("money", 0d);
        if (mCardListModelDataBean != null && money > 0) {
            mViewHolder.setText(R.id.tv_canout, "可提现: ￥" + money / 100d);
            mViewHolder.setText(R.id.tv_banck_name, mCardListModelDataBean.getBank_name());
            mViewHolder.setText(R.id.tv_banck_card, "储蓄卡 " + "(****" + mCardListModelDataBean.getBank_card_no().substring(mCardListModelDataBean.getBank_card_no().length() - 4) + ")");
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
        if (mCardListModelDataBean == null) {
            ToastUtil.showToast("获取银行卡信息失败!");
            return;
        }
        switch (v.getId()) {
            case R.id.bt_withdraw_action:
                try {
                    String outMoney = money_input.getText().toString();
                    if (TextUtils.isEmpty(outMoney)) {
                        ToastUtil.showToast("请输入提现金额!");
                    } else if (Double.valueOf(outMoney) <= 0) {
                        ToastUtil.showToast("提现金额必须大于0!");
                    } else if (Double.valueOf(outMoney) > money / 100d) {
                        ToastUtil.showToast("提现金额不能大于可提现金额!");
                    } else {
                        mRedBusiness.moneyOut(APPCationStation.MONEYOUT, "提现中...", mCardListModelDataBean.getId(), Double.valueOf(outMoney) * 100d);
                    }
                } catch (Exception e) {
                    ToastUtil.showToast("请输入正确的提现金额!");
                }
                break;
        }
    }
}
