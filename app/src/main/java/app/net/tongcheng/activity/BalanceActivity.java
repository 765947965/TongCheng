package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import app.net.tongcheng.Business.RedBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.adapter.MyBaseAdapter;
import app.net.tongcheng.adapter.MyBaseRecyclerViewAdapter;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.CardListModel;
import app.net.tongcheng.model.CheckEvent;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.MoneyInfoModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.BanckCardUtil;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.MyRecyclerViewHolder;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;

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
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                mMoneyInfoModel = NativieDataUtils.getMoneyInfoModel();
                if (mMoneyInfoModel != null) {
                    mViewHolder.setText(R.id.tv_total, "总余额:￥" + mMoneyInfoModel.getData().getBalance() / 100d + "元");
                    mViewHolder.setText(R.id.tv_withdraw_ing, "提现中:￥" + mMoneyInfoModel.getData().getFetching_amount() / 100d + "元");
                    mViewHolder.setText(R.id.tv_can_withdraw, "可提现:￥" + mMoneyInfoModel.getData().getCanfetch_amount() / 100d + "元");
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
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
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
        }
    }

    @Override
    public void BusinessOnFail(int mLoding_Type) {
        ToastUtil.showToast("网络不可用,请检查网络连接!");
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
                if (mlist.size() == 0) {
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
                    startActivity(new Intent(TCApplication.mContext, MoneyOutInputActivity.class).putExtra("CardListModel.DataBean", mCardListModelDataBean).putExtra("money", mMoneyInfoModel.getData().getCanfetch_amount()));
                }
                break;
            case R.id.tv_banding_new_card:
                startActivity(new Intent(TCApplication.mContext, BandingNewCard.class));
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
            isload = false;
        }
    }
}
