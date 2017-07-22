package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import app.net.tongcheng.Business.RedBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.adapter.MyBaseRecyclerViewAdapter;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.MoneyOutListModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.MyRecyclerViewHolder;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;

/**
 * Created by 76594 on 2016/6/11.
 */
public class TiXianListActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ViewHolder mViewHolder;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RedBusiness mRedBusiness;
    private List<MoneyOutListModel.DataBean> mLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tixian_layout);
        setTitle("提现记录");
        initView();
        mRedBusiness = new RedBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.flt_main), this);
        mSwipeRefreshLayout = mViewHolder.getView(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refurush_color);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = mViewHolder.getView(R.id.mRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TCApplication.mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void loadData() {
        mHandler.sendEmptyMessage(10001);
        mRedBusiness.moneyOutList(APPCationStation.LOADING, "");
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                MoneyOutListModel mMoneyOutListModel = NativieDataUtils.getMoneyOutListModel();
//                if (mMoneyOutListModel == null || !NativieDataUtils.getTodyYMD().equals(mMoneyOutListModel.getUpdate())) {
//                    mRedBusiness.moneyOutList(APPCationStation.LOADING, "");
//                }
                mLists.clear();
                if (mMoneyOutListModel != null && mMoneyOutListModel.getData() != null && mMoneyOutListModel.getData().size() > 0) {
                    mLists.addAll(mMoneyOutListModel.getData());
                }
                if (mRecyclerView.getAdapter() == null) {
                    mRecyclerView.setAdapter(new MyBaseRecyclerViewAdapter<MoneyOutListModel.DataBean>(TCApplication.mContext, mLists, R.layout.tixian_list_item_layout) {
                        @Override
                        public void onItemClick(View view, MoneyOutListModel.DataBean itemdata, List<MoneyOutListModel.DataBean> list, int position) {
                            startActivity(new Intent(TCApplication.mContext, TiXianMoreInfo.class).putExtra("id", itemdata.getId()));
                        }

                        @Override
                        public void onCreateItemView(MyRecyclerViewHolder holder, MoneyOutListModel.DataBean itemdata, List<MoneyOutListModel.DataBean> list, int position) {
                            holder.setImage(R.id.iv_title, itemdata.getLogo_url());
                            holder.setText(R.id.tv_name, "提现至" + itemdata.getBank_name() + "(" + itemdata.getBank_card_no().substring(itemdata.getBank_card_no().length() - 4) + ")");
                            holder.setText(R.id.tv_time, itemdata.getAddtime());
                            holder.setText(R.id.tv_money, Math.abs(itemdata.getMoney()) / 100d + "");
                            switch (itemdata.getStatus()) {
                                case "finish":
                                    holder.setText(R.id.tv_status, "提现成功");
                                    break;
                                case "fail":
                                    holder.setText(R.id.tv_status, "提现失败");
                                    break;
                                case "paid":
                                    holder.setText(R.id.tv_status, "银行处理中");
                                    break;
                                case "apply":
                                    holder.setText(R.id.tv_status, "提交申请中");
                                    break;
                            }
                        }
                    });
                }
                mRecyclerView.getAdapter().notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {
        switch (mLoadType) {
            case APPCationStation.LOADING:
                mSwipeRefreshLayout.setRefreshing(false);
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    MoneyOutListModel mMoneyOutListModel = (MoneyOutListModel) mConnectResult.getObject();
                    mMoneyOutListModel.setUpdate(NativieDataUtils.getTodyYMD());
                    NativieDataUtils.setMoneyOutListModel(mMoneyOutListModel);
                    mHandler.sendEmptyMessage(10001);
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoadType) {
        ToastUtil.showToast("网络不可用,请检查网络连接!");
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        mRedBusiness.moneyOutList(APPCationStation.LOADING, "");
    }
}
