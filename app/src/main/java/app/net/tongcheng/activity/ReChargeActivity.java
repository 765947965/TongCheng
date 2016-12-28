package app.net.tongcheng.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import app.net.tongcheng.Business.RedBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.adapter.MyBaseAdapter;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.RechargeInfoModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;

/**
 * Created by 76594 on 2016/6/11.
 */
public class ReChargeActivity extends BaseActivity implements View.OnClickListener {
    private ViewHolder mViewHolder;
    private RedBusiness mRedBusiness;
    private List<RechargeInfoModel.DataBean> datas = new ArrayList<>();
    private ReChargeListAdapter mReChargeListAdapter;
    private ListView mListView;
    private RechargeInfoModel.DataBean selectbean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge_layout);
        setTitle("充值套餐");
        initView();
        mRedBusiness = new RedBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.ssl_main), this);
        mListView = mViewHolder.getView(R.id.mListView);
        mViewHolder.setOnClickListener(R.id.bt_recharge);
    }


    @Override
    public void loadData() {
        mHandler.sendEmptyMessage(10001);
        mRedBusiness.rechargeInfo(APPCationStation.LOADING, "");
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                RechargeInfoModel mRechargeInfoModel = NativieDataUtils.getRechargeInfoModel();
                if (mRechargeInfoModel != null && mRechargeInfoModel.getData() != null && mRechargeInfoModel.getData().size() > 0) {
                    datas.clear();
                    datas.addAll(mRechargeInfoModel.getData());
                    selectbean = datas.get(0);
                    mViewHolder.setText(R.id.tv_info, selectbean.getGoodsInfo());
                }
                if (mReChargeListAdapter == null) {
                    mReChargeListAdapter = new ReChargeListAdapter(mListView, TCApplication.mContext, datas, R.layout.recharge_item_layout);
                    mListView.setAdapter(mReChargeListAdapter);
                }
                mReChargeListAdapter.notifyDataSetChanged();
                break;
        }

    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {
        switch (mLoadType) {
            case APPCationStation.LOADING:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    RechargeInfoModel mRechargeInfoModel = (RechargeInfoModel) mConnectResult.getObject();
                    mRechargeInfoModel.setUpdate(NativieDataUtils.getTodyYMD());
                    NativieDataUtils.setRechargeInfoModel(mRechargeInfoModel);
                    mHandler.sendEmptyMessage(10001);
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoadType) {
        ToastUtil.showToast("网络不可用,请检查网络连接!");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_recharge:
                if (selectbean == null) {
                    ToastUtil.showToast("网络不可用,请检查网络连接!");
                } else {
                    startActivity(new Intent(this, NextRecharge.class).putExtra("RechargeInfoModel.DataBean", selectbean));
                }
                break;
        }
    }


    class ReChargeListAdapter extends MyBaseAdapter<RechargeInfoModel.DataBean> {
        public ReChargeListAdapter(AbsListView mListView, Context context, List<RechargeInfoModel.DataBean> datas, int itemLayoutId) {
            super(mListView, context, datas, itemLayoutId);
        }

        @Override
        protected void convert(ViewHolder holder, RechargeInfoModel.DataBean item, List<RechargeInfoModel.DataBean> list, int position) {
            holder.setText(R.id.tv_item_title, item.getGoodsName());
            if (selectbean == null || !selectbean.getGoodsID().equals(item.getGoodsID())) {
                holder.setImage(R.id.iv_check, R.drawable.uncheck);
            } else {
                holder.setImage(R.id.iv_check, R.drawable.check);
            }
        }

        @Override
        protected void MyonItemClick(AdapterView<?> parent, View view, RechargeInfoModel.DataBean item, List<RechargeInfoModel.DataBean> list, int position, long id) {
            selectbean = item;
            mViewHolder.setText(R.id.tv_info, selectbean.getGoodsInfo());
            mReChargeListAdapter.notifyDataSetChanged();
        }
    }

}
