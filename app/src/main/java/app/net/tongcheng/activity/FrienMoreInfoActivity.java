package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import org.greenrobot.eventbus.Subscribe;

import app.net.tongcheng.Business.FriendBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.CheckEvent;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.FriendModel;
import app.net.tongcheng.model.FriendsBean;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;
import app.net.tongcheng.view.FriendMoreInfoMenuDialog;

/**
 * Created by 76594 on 2016/6/17.
 */
public class FrienMoreInfoActivity extends BaseActivity implements View.OnClickListener, FriendMoreInfoMenuDialog.FriendMoreInfoMenuDialogListener {
    private ViewHolder mViewHolder;
    private FriendsBean itemdata;
    private FriendBusiness mFriendBusiness;
    private FriendMoreInfoMenuDialog mFriendMoreInfoMenuDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frien_more_info_layout);
        setTitle("详细资料");
        initView();
        setEventBus();
        mFriendBusiness = new FriendBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        mViewHolder.setOnClickListener(R.id.tv_sendRed);
        mViewHolder.setOnClickListener(R.id.iv_head_image);
        getRightIV().setVisibility(View.VISIBLE);
        getRightIV().setImageResource(R.drawable.menu);
        getRightIV().setOnClickListener(this);
    }

    @Override
    public void loadData() {
        itemdata = (FriendsBean) getIntent().getSerializableExtra("FriendsBean");
        if (itemdata != null) {
            if (!TextUtils.isEmpty(itemdata.getPicture()) && itemdata.getPicture().startsWith("http")) {
                mViewHolder.setImage(R.id.iv_head_image, itemdata.getPicture(), 0, 360);
            } else {
                mViewHolder.setImage(R.id.iv_head_image, itemdata.getPictureRED());
            }
            mViewHolder.setText(R.id.tv_remark, TextUtils.isEmpty(itemdata.getRemark()) ? itemdata.getPhone() : itemdata.getRemark());
            if (!TextUtils.isEmpty(itemdata.getSex())) {
                if ("男".equals(itemdata.getSex())) {
                    mViewHolder.setImage(R.id.iv_sex, R.drawable.one_profile_male_left_dark);
                } else if ("女".equals(itemdata.getSex())) {
                    mViewHolder.setImage(R.id.iv_sex, R.drawable.one_profile_female_left_dark);
                }
            }
            if (!TextUtils.isEmpty(itemdata.getName())) {
                mViewHolder.setText(R.id.tv_name, "昵称: " + itemdata.getName()).setVisibility(View.VISIBLE);
            }
            mViewHolder.setText(R.id.tv_phone, "手机号: " + itemdata.getPhone());
            mViewHolder.setText(R.id.tv_uid, itemdata.getUid());
            if (!TextUtils.isEmpty(itemdata.getSignature())) {
                mViewHolder.setVisibility(R.id.llt_signe, View.VISIBLE);
                mViewHolder.setText(R.id.tv_signe, itemdata.getSignature());
            }
            if (!TextUtils.isEmpty(itemdata.getProvince()) && !TextUtils.isEmpty(itemdata.getCity())) {
                mViewHolder.setVisibility(R.id.llt_address, View.VISIBLE);
                mViewHolder.setText(R.id.tv_address, itemdata.getProvince() + "-" + itemdata.getCity());
            }
        }
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {
        switch (mLoadType) {
            case APPCationStation.SUMBIT://删除成功
                FriendModel mFriendModel = NativieDataUtils.getFriendModel();
                if (mFriendModel != null && NativieDataUtils.getTodyYMD().equals(mFriendModel.getUpdate())) {
                    mFriendModel.setUpdate("00000000");
                    NativieDataUtils.setFriendModel(mFriendModel);
                }
                sendEventBusMessage("FriendFragment.Refresh");
                ToastUtil.showToast("删除成功");
                finish();
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoadType) {

    }

    @Override
    public void onClick(View v) {
        if (itemdata == null) {
            ToastUtil.showToast("获取好友资料失败!");
            return;
        }
        switch (v.getId()) {
            case R.id.tv_sendRed:
                startActivity(new Intent(TCApplication.mContext, PersonalRedEnvelopeConfig.class).putExtra("uids", itemdata.getUid()).putExtra("name", itemdata.getRemark()).putExtra("nums", 1));
                break;
            case R.id.iv_head_image:
                if (!TextUtils.isEmpty(itemdata.getPicture()) && itemdata.getPicture().startsWith("http")) {
                    startActivity(new Intent(TCApplication.mContext, ShowImageActivity.class).putExtra("url", itemdata.getPicture()));
                }
                break;
            case R.id.ivRight:
                if (mFriendMoreInfoMenuDialog == null) {
                    mFriendMoreInfoMenuDialog = new FriendMoreInfoMenuDialog(this, this);
                }
                mFriendMoreInfoMenuDialog.show();
                break;
        }
    }


    @Subscribe
    public void onEvent(CheckEvent event) {
        if (event != null) {
            try {
                if (event.getMsg().startsWith("changeFriendUserName=")) {
                    mViewHolder.setText(R.id.tv_remark, event.getMsg().split("=")[1]);
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onUpdateRemark() {
        startActivity(new Intent(this, changeFriendUserName.class)
                .putExtra(Common.AGR1, itemdata.getRemark())
                .putExtra(Common.AGR2, itemdata.getPhone()));
    }

    @Override
    public void deleteFriend() {
        DialogUtil.showTipsDialog(this, "删除联系人", "将联系人"
                        + (TextUtils.isEmpty(itemdata.getRemark()) ? itemdata.getPhone() : itemdata.getRemark())
                        + "删除", "确定", "取消",
                new DialogUtil.OnConfirmListener() {
                    @Override
                    public void clickConfirm() {
                        mFriendBusiness.deleteFriend(APPCationStation.SUMBIT, "删除中...", itemdata.getVer(), itemdata.getUid());
                    }

                    @Override
                    public void clickCancel() {

                    }
                });
    }
}
