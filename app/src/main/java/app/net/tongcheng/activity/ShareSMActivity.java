package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

import app.net.tongcheng.Business.ShareBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.ShareTipsModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ViewHolder;

/**
 * Created by 76594 on 2016/6/20.
 */
public class ShareSMActivity extends BaseActivity {

    private ViewHolder mViewHolder;
    private ShareBusiness mShareBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_sm_layout);
        setTitle("分享说明");
        initView();
        mShareBusiness = new ShareBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.ssl_main));
    }

    @Override
    public void loadData() {
        mHandler.sendEmptyMessage(10001);
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                ShareTipsModel mShareTipsModel = NativieDataUtils.getShareTipsModel();
                if (mShareTipsModel == null || !NativieDataUtils.getTodyYMD().equals(mShareTipsModel.getUpdate())) {
                    mShareBusiness.getShareTips(APPCationStation.LOADING, "");
                }
                if (mShareTipsModel != null && !TextUtils.isEmpty(mShareTipsModel.getContent())) {
                    mViewHolder.setText(R.id.tv_share_tips, mShareTipsModel.getContent());
                }
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.LOADING:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    ShareTipsModel mShareTipsModel = (ShareTipsModel) mConnectResult.getObject();
                    mShareTipsModel.setUpdate(NativieDataUtils.getTodyYMD());
                    if (!TextUtils.isEmpty(mShareTipsModel.getContent())) {
                        NativieDataUtils.setShareTipsModel(mShareTipsModel);
                        mHandler.sendEmptyMessage(10001);
                    }
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoding_Type) {

    }
}
