package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import app.net.tongcheng.Business.RedBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.adapter.MyBaseAdapter;
import app.net.tongcheng.model.BanckListModel;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;

/**
 * Created by 76594 on 2016/6/14.
 */
public class ChangeBanckName extends BaseActivity {

    private ViewHolder mViewHolder;
    private ListView mListView;
    private RedBusiness mRedBusiness;
    private List<BanckListModel.DataBean> mdatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_banck_layout);
        setTitle("银行");
        initView();
        mRedBusiness = new RedBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), null);
        mListView = mViewHolder.getView(R.id.mListView);
    }

    @Override
    public void loadData() {
        mHandler.sendEmptyMessage(10001);
        mRedBusiness.getBanckCardList(APPCationStation.LOADING, "");
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                BanckListModel mBanckListModel = NativieDataUtils.getBanckListModel();
                mdatas.clear();
                if (mBanckListModel != null && mBanckListModel.getData() != null && mBanckListModel.getData().size() > 0) {
                    mdatas.addAll(mBanckListModel.getData());
                }
                if (mListView.getAdapter() == null) {
                    mListView.setAdapter(new MyBaseAdapter<BanckListModel.DataBean>(mListView, TCApplication.mContext, mdatas, R.layout.change_banck_name_list_item) {
                        @Override
                        protected void convert(ViewHolder holder, BanckListModel.DataBean item, List<BanckListModel.DataBean> list, int position) {
                            holder.setImage(R.id.iv_title, item.getLogo_url());
                            holder.setText(R.id.tv_cardname, item.getBank_name());
                        }

                        @Override
                        protected void MyonItemClick(AdapterView<?> parent, View view, BanckListModel.DataBean item, List<BanckListModel.DataBean> list, int position, long id) {
                            sendEventBusMessage("bank_name=" + item.getBank_name());
                            finish();
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
                    BanckListModel mBanckListModel = (BanckListModel) mConnectResult.getObject();
                    NativieDataUtils.setBanckListModel(mBanckListModel);
                    mHandler.sendEmptyMessage(10001);
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoding_Type) {
        ToastUtil.showToast("网络不可用,请检查网络连接!");
    }
}
