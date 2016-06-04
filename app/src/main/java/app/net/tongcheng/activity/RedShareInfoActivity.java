package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.looppager.BannerView;

import java.util.List;

import app.net.tongcheng.Business.LifeBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.adapter.LoopBaseAdapter;
import app.net.tongcheng.model.ADListModel;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.GiftsBean;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/3 16:42
 */
public class RedShareInfoActivity extends BaseActivity implements View.OnClickListener {

    private ViewHolder mViewHolder;
    private GiftsBean mGiftsBean;
    private LifeBusiness mLifeBusiness;
    private BannerView mBannerView;
    private ADListModel mADListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.red_details_layout);
        mGiftsBean = (GiftsBean) getIntent().getSerializableExtra("GiftsBean");
        initView();
        mLifeBusiness = new LifeBusiness(this, this, mHandler);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBannerView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBannerView.onResume();
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        mBannerView = mViewHolder.getView(R.id.image_banner);
        mViewHolder.setOnClickListener(R.id.reddllclosebt);
    }

    @Override
    public void loadData() {
        mHandler.sendEmptyMessageDelayed(10001, 100);
        if (mGiftsBean == null) {
            return;
        }
        mViewHolder.setText(R.id.reddetails_namete_typetext, mGiftsBean.getName());
        if ("personwithcommand".equals(mGiftsBean.getSub_type())) {
            mViewHolder.setVisibility(R.id.lingimage, View.VISIBLE);
        } else if ("groupnocommand".equals(mGiftsBean.getSub_type())) {
            mViewHolder.setVisibility(R.id.qunimage, View.VISIBLE);
        } else if ("groupwithcommand".equals(mGiftsBean.getSub_type())) {
            mViewHolder.setVisibility(R.id.qunimage, View.VISIBLE);
            mViewHolder.setVisibility(R.id.lingimage, View.VISIBLE);
        }
        if (mGiftsBean.getMoney_type() == 0) {
            mViewHolder.setImage(R.id.money_typeimage, R.drawable.moneytype_0);
            mViewHolder.setVisibility(R.id.money_typeimage, View.VISIBLE);
        } else if (mGiftsBean.getMoney_type() == 1) {
            mViewHolder.setImage(R.id.money_typeimage, R.drawable.moneytype_1);
            mViewHolder.setVisibility(R.id.money_typeimage, View.VISIBLE);
        }
        if (!TextUtils.isEmpty(mGiftsBean.getFromnickname())) {
            mViewHolder.setText(R.id.reddetails_from_nametext, mGiftsBean.getFromnickname() + "的" + mGiftsBean.getName());
        } else {
            if ("aixin_money".equals(mGiftsBean.getFrom().trim()) || "system".equals(mGiftsBean.getFrom().trim())) {
                mViewHolder.setText(R.id.reddetails_from_nametext, mGiftsBean.getName());
            } else {
                mViewHolder.setText(R.id.reddetails_from_nametext, mGiftsBean.getFrom().trim() + "的" + mGiftsBean.getName());
            }
        }
        double money_temp = mGiftsBean.getMoney() / (double) 100;
        mViewHolder.setText(R.id.reddetails_from_money, money_temp + "");
        mViewHolder.setText(R.id.reddetails_from_money_dj, "元");
        mViewHolder.setText(R.id.reddetails_from_money_time, money_temp + "已存入钱包");
        mViewHolder.setText(R.id.reddetails_from_tips, mGiftsBean.getTips());
        // 设置头像
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                ADListModel mADListModel = NativieDataUtils.getADListDataModel();
                if (mADListModel == null || !NativieDataUtils.getTodyYMD().equals(mADListModel.getUpdate())) {
                    mLifeBusiness.getADData(APPCationStation.LOADINGAD, "");
                }
                if (mADListModel == null || mADListModel.getPn() == null || mADListModel.getPn().size() == 0) {
                    return;
                }
                this.mADListModel = mADListModel;
                setADListData();
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.LOADINGAD:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    ADListModel mADListModel = (ADListModel) mConnectResult.getObject();
                    mADListModel.setUpdate(NativieDataUtils.getTodyYMD());
                    NativieDataUtils.setADListDataModel(mADListModel);
                    mHandler.sendEmptyMessage(10001);
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoding_Type) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reddllclosebt:
                finish();
                break;
        }
    }

    private void setADListData() {
        mBannerView.setAdapter(new LoopBaseAdapter<ADListModel.PnBean>(TCApplication.mContext, mADListModel.getPn(), R.layout.row_banner) {
            @Override
            public void createView(ViewHolder mViewHolder, final ADListModel.PnBean item, List<ADListModel.PnBean> mDatas, int position) {
                if (item != null) {
                    mViewHolder.setImage(R.id.banner_top_IV, mADListModel.getUrlprefix() + item.getN());
                    mViewHolder.getView(R.id.rlt_main).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!TextUtils.isEmpty(item.getTo()) && item.getTo().startsWith("http")) {
                                startActivity(new Intent(TCApplication.mContext, PublicWebview.class).putExtra("title", "同城商城").putExtra("url", item.getTo()));
                            }
                        }
                    });
                }
            }
        }, mADListModel.getPn().size(), 1.0d, 2);
    }
}
