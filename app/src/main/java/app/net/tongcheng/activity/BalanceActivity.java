package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.net.tongcheng.Business.OtherBusiness;
import app.net.tongcheng.Business.RedBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.adapter.MyBaseAdapter;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.CardListModel;
import app.net.tongcheng.model.CheckEvent;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.MoneyInfoModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.OperationUtils;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;
import okhttp3.Response;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/5/26 17:22
 */
public class BalanceActivity extends BaseActivity implements View.OnClickListener {

    private ViewHolder mViewHolder;
    private ListView mListView;
    private Button bt_withdraw_action;
    private RedBusiness mRedBusiness;
    private OtherBusiness mOtherBusiness;
    private String checkcardId;
    private CardListModel.DataBean mCardListModelDataBean;
    private MoneyInfoModel mMoneyInfoModel;
    private CardListModel mCardListModel;
    private List<CardListModel.DataBean> mlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balance_layout);
        setTitle("余额");
        initView();
        setEventBus();
        mRedBusiness = new RedBusiness(this, this, mHandler);
        mOtherBusiness = new OtherBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.rlt_main), this);
        getRightBtn().setVisibility(View.VISIBLE);
        getRightBtn().setText("提现记录");
        getRightBtn().setOnClickListener(this);
        setTileLineGONE();
        mListView = mViewHolder.getView(R.id.mListView);
        bt_withdraw_action = mViewHolder.getView(R.id.bt_withdraw_action);
        mViewHolder.setOnClickListener(R.id.bt_withdraw_action);
        mViewHolder.setOnClickListener(R.id.tv_banding_new_card);
    }


    @Override
    public void loadData() {
        mHandler.sendEmptyMessageDelayed(10001, 100);
        mHandler.sendEmptyMessageDelayed(10003, 100);
        mHandler.sendEmptyMessageDelayed(10002, 100);
        mHandler.sendEmptyMessageDelayed(10004, 100);
        mViewHolder.setText(R.id.tv_phone, "用户账号: " + TCApplication.getmUserInfo().getPhone());
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                mMoneyInfoModel = NativieDataUtils.getMoneyInfoModel();
                mViewHolder.setVisibility(R.id.btGuQaun, View.GONE);
                if (mMoneyInfoModel != null && !TextUtils.isEmpty(mMoneyInfoModel.getData().getBalance_key())) {
                    mViewHolder.setText(R.id.tv_total, mMoneyInfoModel.getData().getBalance_key() + ": " + mMoneyInfoModel.getData().getBalance() / 100d);
                    mViewHolder.setText(R.id.tv_withdraw_ing, mMoneyInfoModel.getData().getFetching_amount_key() + ": " + mMoneyInfoModel.getData().getFetching_amount() / 100d);
                    mViewHolder.setText(R.id.tv_can_withdraw, mMoneyInfoModel.getData().getCanfetch_amount_key() + ": " + mMoneyInfoModel.getData().getCanfetch_amount() / 100d);
                    mViewHolder.setText(R.id.tv_can_recharge_ye, mMoneyInfoModel.getData().getCharge_amount_key() + ": " + mMoneyInfoModel.getData().getCharge_amount() / 100d);
                    mViewHolder.setText(R.id.tv_can_ke_jie_dong, mMoneyInfoModel.getData().getFreze_account_key() + ": " + mMoneyInfoModel.getData().getFreze_account() / 100d);
                    if (mMoneyInfoModel.getData().getIs_agent() == 1) {
                        mViewHolder.setVisibility(R.id.btGuQaun, View.VISIBLE).setOnClickListener(this);
                    }
                } else {
                    mViewHolder.setText(R.id.tv_total, "查询中...");
                }
                break;
            case 10002:
                mRedBusiness.getMoneyInfo(APPCationStation.LOADING, "");
                mRedBusiness.getCarList(APPCationStation.LOADINGAD, "");
                break;
            case 10003://显示银行卡
                checkcardId = null;
                mCardListModelDataBean = null;
                mCardListModel = NativieDataUtils.getCardListModel();
                mlist.clear();
                if (mCardListModel != null && mCardListModel.getData() != null && mCardListModel.getData().size() > 0) {
                    mlist.addAll(mCardListModel.getData());
                }
                if (mListView.getAdapter() == null) {
                    mListView.setAdapter(new MyBaseAdapter<CardListModel.DataBean>(mListView, TCApplication.mContext, mlist, R.layout.balance_card_info_list_item) {
                        @Override
                        protected void convert(ViewHolder holder, CardListModel.DataBean item, List<CardListModel.DataBean> list, int position) {
                            holder.setImage(R.id.iv_card_title, item.getLogo_url());
                            holder.setText(R.id.tv_name, item.getCard_holder());
                            holder.setText(R.id.tv_cardinfo, item.getBank_name() + "(****" + item.getBank_card_no().substring(item.getBank_card_no().length() - 4) + ")");
                            holder.setImage(R.id.iv_check, item.getIs_default() == 1 ? R.drawable.check : R.drawable.uncheck);
                            if (item.getIs_default() == 1) {
                                checkcardId = item.getId();
                                mCardListModelDataBean = item;
                            }
                        }

                        @Override
                        protected void MyonItemClick(AdapterView<?> parent, View view, CardListModel.DataBean item, List<CardListModel.DataBean> list, int position, long id) {
                            if (item.getIs_default() == 0) {
                                changeMCard(item.getId());
                            }
                        }
                    });
                    mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            deletMyCard(mlist.get(position).getId());
                            return true;
                        }
                    });
                }
                ((ArrayAdapter) mListView.getAdapter()).notifyDataSetChanged();
                break;
            case 10004:
                if (!OperationUtils.getBoolean(OperationUtils.walletPassword)) {
                    mOtherBusiness.getWalletPasswordType(APPCationStation.WALLETPASSWORD, "");
                }
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {
        switch (mLoadType) {
            case APPCationStation.LOADING:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    MoneyInfoModel mMoneyInfoModel = (MoneyInfoModel) mConnectResult.getObject();
                    NativieDataUtils.setMoneyInfoModel(mMoneyInfoModel);
                    mHandler.sendEmptyMessage(10001);
                }
                break;
            case APPCationStation.LOADINGAD:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    CardListModel mCardListModel = (CardListModel) mConnectResult.getObject();
                    NativieDataUtils.setCardListModel(mCardListModel);
                    mHandler.sendEmptyMessage(10003);
                }
                break;
            case APPCationStation.SUMBIT:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    mRedBusiness.getCarList(APPCationStation.LOADINGAD, "");
                    DialogUtil.showTipsDialog(this, "设置成功!", null);
                }
                break;
            case APPCationStation.MONEYOUT://提现
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    DialogUtil.showTipsDialog(this, "提现成功!", new DialogUtil.OnConfirmListener() {
                        @Override
                        public void clickConfirm() {
                            // 进入提现列表
                            startActivity(new Intent(TCApplication.mContext, TiXianListActivity.class));
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
                    map_value.put("withdrawmoney", mMoneyInfoModel.getData().getCanfetch_amount() / 100d + "");
                    MobclickAgent.onEventValue(this, "withdraw", map_value, Double.valueOf(mMoneyInfoModel.getData().getCanfetch_amount() / 100d).intValue());
                }
                break;
            case APPCationStation.DCARD://删除银行卡
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    mRedBusiness.getCarList(APPCationStation.LOADINGAD, "");
                    DialogUtil.showTipsDialog(this, "删除银行卡成功!", null);
                }
                break;
            case APPCationStation.WALLETPASSWORD:
                if (mConnectResult != null && mConnectResult.getObject() != null) {
                    if (((BaseModel) mConnectResult.getObject()).getResult() == 81) {
                        OperationUtils.PutBoolean(OperationUtils.walletPassword, false);
                    } else if (((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                        OperationUtils.PutBoolean(OperationUtils.walletPassword, true);
                    }
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoadType, Response response) {
        if (response == null || response.code() != 403) {
            ToastUtil.showToast("网络不可用，请检查网络连接！");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRight://提现记录
                startActivity(new Intent(TCApplication.mContext, TiXianListActivity.class));
                break;
            case R.id.bt_withdraw_action://提现
                if (mMoneyInfoModel == null || mCardListModel == null) {
                    ToastUtil.showToast("网络不可用,请检查网络连接!");
                    return;
                }
                if (!OperationUtils.getBoolean(OperationUtils.walletPassword)) {
                    DialogUtil.showTipsDialog(this, "提示", "请先设置钱包密码!", "确定", "取消", new DialogUtil.OnConfirmListener() {
                        @Override
                        public void clickConfirm() {
                            startActivity(new Intent(TCApplication.mContext, SetWalletActivity.class));
                        }

                        @Override
                        public void clickCancel() {

                        }
                    });
                } else if (mlist.size() == 0) {
                    DialogUtil.showTipsDialog(this, "提示", "请先绑定银行卡!", "确定", "取消", new DialogUtil.OnConfirmListener() {
                        @Override
                        public void clickConfirm() {
                            startActivity(new Intent(TCApplication.mContext, BandingNewCard.class));
                        }

                        @Override
                        public void clickCancel() {

                        }
                    });
                } else if (checkcardId == null) {
                    DialogUtil.showTipsDialog(this, "请选择默认银行卡!", null);
                } else if (mMoneyInfoModel.getData().getCanfetch_amount() == 0) {
                    DialogUtil.showTipsDialog(this, "已无可提现金额", null);
                } else {
                    // 提现
//                    mRedBusiness.moneyOut(APPCationStation.MONEYOUT, "提现中...", checkcardId, mMoneyInfoModel.getData().getCanfetch_amount());
                    startActivity(new Intent(TCApplication.mContext, MoneyOutInputActivity.class).putExtra("CardListModel.DataBean", mCardListModelDataBean).putExtra("MoneyInfoModel", mMoneyInfoModel));
                }
                break;
            case R.id.tv_banding_new_card:
                startActivity(new Intent(TCApplication.mContext, BandingNewCard.class));
                break;
            case R.id.btGuQaun:
                startActivity(new Intent(TCApplication.mContext, GuQuanActivity.class).putExtra(Common.AGR1, mMoneyInfoModel));
                break;
        }
    }

    private void changeMCard(final String carId) {
        DialogUtil.showTipsDialog(this, "提示", "确定选择此卡为默认卡吗？", "确定", "取消", new DialogUtil.OnConfirmListener() {
            @Override
            public void clickConfirm() {
                mRedBusiness.changeCard(APPCationStation.SUMBIT, "提交中...", carId);
            }

            @Override
            public void clickCancel() {

            }
        });
    }

    private void deletMyCard(final String carId) {
        DialogUtil.showTipsDialog(this, "提示", "确定删除该银行卡吗？", "确定", "取消", new DialogUtil.OnConfirmListener() {
            @Override
            public void clickConfirm() {
                mRedBusiness.deleteCard(APPCationStation.DCARD, "提交中...", carId);
            }

            @Override
            public void clickCancel() {

            }
        });
    }

    @Subscribe
    public void onEvent(CheckEvent event) {
        if (event != null && event.getMsg().equals("balance_rushe")) {
            mRedBusiness.getCarList(APPCationStation.LOADINGAD, "");
        } else if (event != null && event.getMsg().equals("money_rushe")) {
            mRedBusiness.getMoneyInfo(APPCationStation.LOADING, "");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMoneyInfoModel == null || mCardListModel == null) {
            isLoadData = false;
        }
    }
}
