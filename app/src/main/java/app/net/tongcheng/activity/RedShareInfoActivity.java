package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.looppager.BannerView;

import java.util.List;

import app.net.tongcheng.Business.LifeBusiness;
import app.net.tongcheng.Business.RedBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.adapter.LoopBaseAdapter;
import app.net.tongcheng.model.ADListModel;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.FriendModel;
import app.net.tongcheng.model.FriendsBean;
import app.net.tongcheng.model.GiftsBean;
import app.net.tongcheng.model.UserMoreInfoModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.Utils;
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
    private RedBusiness mRedBusiness;
    private BannerView mBannerView;
    private ADListModel mADListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.red_details_layout);
        mGiftsBean = (GiftsBean) getIntent().getSerializableExtra("GiftsBean");
        initView();
        mLifeBusiness = new LifeBusiness(this, this, mHandler);
        mRedBusiness = new RedBusiness(this, this, mHandler);
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
        mViewHolder.setOnClickListener(R.id.reddetails_from_tips);
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
        double money_temp = Double.parseDouble(mGiftsBean.getMoney().replace(",", "")) / (double) 100;
        mViewHolder.setText(R.id.reddetails_from_money, money_temp + "");
        mViewHolder.setText(R.id.reddetails_from_money_dj, "元");
        mViewHolder.setText(R.id.reddetails_from_tips, mGiftsBean.getTips());

        if (mGiftsBean.getDirect().equals("sended")) {
            try {
                // 查看红包拆开情况
                if (mGiftsBean.getReturned_money() == 0) {
                    mViewHolder.setText(R.id.textinfosendred, Utils.sdformat_6.format(Utils.sdformat.parse(mGiftsBean.getCreate_time())) + " 包好 ,已领取" + mGiftsBean.getHas_open() + "/" + mGiftsBean.getSplitsnumber() + "个,共" + mGiftsBean.getReceived_money() / 100d + "元/" + money_temp + "元");
                } else {
                    mViewHolder.setText(R.id.textinfosendred, Utils.sdformat_6.format(Utils.sdformat.parse(mGiftsBean.getCreate_time())) + " 包好 ,已领取" + mGiftsBean.getHas_open() + "/" + mGiftsBean.getSplitsnumber() + "个,共" + mGiftsBean.getReceived_money() / 100d + "元/" + Double.parseDouble(mGiftsBean.getMoney().replace(",", "")) / 100d + "元,已退回" + mGiftsBean.getReturned_money() / 100d + "元");
                }
                if ("has_sended".equals(mGiftsBean.getStatus())) {
                    mViewHolder.setText(R.id.tv_linqutips, "领取中...");
                } else if ("has_packed".equals(mGiftsBean.getStatus())) {
                    mViewHolder.setText(R.id.tv_linqutips, "已包好");
                } else if ("has_ended".equals(mGiftsBean.getStatus())) {
                    if (mGiftsBean.getHas_open() == mGiftsBean.getSplitsnumber()) {
                        mViewHolder.setText(R.id.tv_linqutips, "已领完");
                    } else {
                        mViewHolder.setText(R.id.tv_linqutips, "已过期");
                    }
                }
                // 查看手气
                mViewHolder.setVisibility(R.id.showathorinfo, View.VISIBLE).setOnClickListener(this);
            } catch (Exception e) {
            }
            // 设置头像
            UserMoreInfoModel mUserMoreInfoModel = NativieDataUtils.getUserMoreInfoModel();
            if (mUserMoreInfoModel != null && !TextUtils.isEmpty(mUserMoreInfoModel.getPicture()) && !TextUtils.isEmpty(mUserMoreInfoModel.getPicurl_prefix())) {
                mViewHolder.setImage(R.id.reddetails_from_iamge, mUserMoreInfoModel.getPicurl_prefix() + mUserMoreInfoModel.getPicture(), R.drawable.red_dialog_head_image, 360);
            }
        } else {
            mViewHolder.setText(R.id.reddetails_from_money_time, money_temp + "已存入钱包");
            if (mGiftsBean.getFrom().matches("[0-9]+")) {
                // 显示答谢
                mViewHolder.setVisibility(R.id.llt_daxie, View.VISIBLE);
                mViewHolder.setOnClickListener(R.id.surethangkstext);
            }
            // 设置头像
            if (mGiftsBean.getFrom().matches("[0-9]+")) {
                FriendModel mFriendModel = NativieDataUtils.getFriendModel();
                if (mFriendModel != null && mFriendModel.getFriends() != null && mFriendModel.getFriends().size() > 0) {
                    for (FriendsBean mFriendsBean : mFriendModel.getFriends()) {
                        if (mFriendsBean.getUid().equals(mGiftsBean.getFrom())) {
                            if (!TextUtils.isEmpty(mFriendsBean.getPicture())) {
                                mViewHolder.setImage(R.id.reddetails_from_iamge, mFriendsBean.getPicture(), R.drawable.red_dialog_head_image, 360);
                            }
                            break;
                        }

                    }
                }
            }
        }
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
            case APPCationStation.SUMBIT:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    DialogUtil.showTipsDialog(this, "答谢成功!", null);
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
            case R.id.reddllclosebt:
                finish();
                break;
            case R.id.showathorinfo:
                startActivity(new Intent(TCApplication.mContext, ShowOtherLuck.class).putExtra("mGiftsBean", mGiftsBean));
                break;
            case R.id.surethangkstext:
                String thankyou = ((EditText) mViewHolder.getView(R.id.mythankstext)).getText().toString();
                if (TextUtils.isEmpty(thankyou)) {
                    ToastUtil.showToast("答谢语不能为空!");
                    return;
                }
                mRedBusiness.sendThank(APPCationStation.SUMBIT, "提交中...", thankyou, mGiftsBean.getGift_id());
                break;
            case R.id.reddetails_from_tips:
                startActivity(new Intent(TCApplication.mContext, TextTipsActivity.class).putExtra("tips", mGiftsBean.getTips()));
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
