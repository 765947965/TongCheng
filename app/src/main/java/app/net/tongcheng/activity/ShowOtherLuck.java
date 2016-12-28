package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import app.net.tongcheng.Business.RedBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.adapter.MyBaseAdapter;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.FriendModel;
import app.net.tongcheng.model.FriendsBean;
import app.net.tongcheng.model.GiftsBean;
import app.net.tongcheng.model.SplideGiftModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;

/**
 * Created by 76594 on 2016/6/14.
 */
public class ShowOtherLuck extends BaseActivity implements View.OnClickListener {

    private ViewHolder mViewHolder;
    private GiftsBean mGiftsBean;
    private ListView mListView;
    private RedBusiness mRedBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_other_luck_layout);
        initView();
        mRedBusiness = new RedBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        mListView = mViewHolder.getView(R.id.listview);
        mViewHolder.setOnClickListener(R.id.reddllclosebt);
    }

    @Override
    public void loadData() {
        mGiftsBean = (GiftsBean) getIntent().getSerializableExtra("mGiftsBean");
        if (mGiftsBean == null) {
            return;
        }
        mViewHolder.setText(R.id.reddetails_from_nametext, "来自" + mGiftsBean.getFromnickname() + "的红包");
        if (mGiftsBean.getMoney_type() == 0) {
            mViewHolder.setImage(R.id.money_typeimage, R.drawable.moneytype_0);
        } else if (mGiftsBean.getMoney_type() == 1) {
            mViewHolder.setImage(R.id.money_typeimage, R.drawable.moneytype_1);
        }
        mViewHolder.setText(R.id.tipstext, mGiftsBean.getTips());

        double money_temp = Double.parseDouble(mGiftsBean.getMoney().replace(",", "")) / 100d;
        double received_money_d = mGiftsBean.getReceived_money() / 100d;
        mViewHolder.setText(R.id.moneytext, money_temp + "");
        mViewHolder.setText(R.id.moneytext_dj, "元");
        mViewHolder.setText(R.id.infored, "共领取" + received_money_d + "元," + mGiftsBean.getHas_open() + "/" + mGiftsBean.getSplitsnumber() + "个");

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

        mRedBusiness.getListGiftInfo(APPCationStation.LOADING, "", mGiftsBean.getGift_id());
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {
        switch (mLoadType) {
            case APPCationStation.LOADING:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    SplideGiftModel mSplideGiftModel = (SplideGiftModel) mConnectResult.getObject();
                    if (mSplideGiftModel != null && mSplideGiftModel.getReceived_user_gift_info() != null && mSplideGiftModel.getReceived_user_gift_info().size() > 0) {
                        setData(mSplideGiftModel);
                    }
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
            case R.id.reddllclosebt:
                finish();
                break;
        }
    }

    private void setData(SplideGiftModel data) {
        String fastuid = null;
        double fastmoney = 0;
        for (SplideGiftModel.ReceivedUserGiftInfoBean item : data.getReceived_user_gift_info()) {
            if (item.getMoney() > fastmoney) {
                fastuid = item.getUid();
            }
        }
        final String fastuidlast = fastuid;
        mListView.setAdapter(new MyBaseAdapter<SplideGiftModel.ReceivedUserGiftInfoBean>(mListView, TCApplication.mContext, data.getReceived_user_gift_info(), R.layout.show_other_luck_list_item_layout) {
            @Override
            protected void convert(ViewHolder holder, SplideGiftModel.ReceivedUserGiftInfoBean item, List<SplideGiftModel.ReceivedUserGiftInfoBean> list, int position) {
                if (!TextUtils.isEmpty(item.getPicture()) && item.getPicture().startsWith("http")) {
                    holder.setImage(R.id.iv_list_head_image, item.getPicture(), R.drawable.defaultuserimage, 360);
                } else {
                    holder.setImage(R.id.iv_list_head_image, R.drawable.defaultuserimage);
                }
                holder.setText(R.id.name, TextUtils.isEmpty(item.getName()) ? "用户" : item.getName());
                if (TextUtils.isEmpty(item.getThankyou())) {
                    holder.setVisibility(R.id.gxy, View.GONE);
                } else {
                    holder.setText(R.id.gxy, item.getThankyou());
                    holder.setVisibility(R.id.gxy, View.VISIBLE);
                }
                holder.setText(R.id.time, TextUtils.isEmpty(item.getOpen_time()) ? "" : item.getOpen_time());
                holder.setText(R.id.money, item.getMoney() / 100d + "元");
                if (item.getUid().equals(fastuidlast)) {
                    holder.setVisibility(R.id.sq, View.VISIBLE);
                } else {
                    holder.setVisibility(R.id.sq, View.INVISIBLE);
                }
            }

            @Override
            protected void MyonItemClick(AdapterView<?> parent, View view, SplideGiftModel.ReceivedUserGiftInfoBean item, List<SplideGiftModel.ReceivedUserGiftInfoBean> list, int position, long id) {

            }
        });
    }
}
