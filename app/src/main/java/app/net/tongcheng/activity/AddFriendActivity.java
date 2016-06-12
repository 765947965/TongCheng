package app.net.tongcheng.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import app.net.tongcheng.Business.FriendBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.ContentModel;
import app.net.tongcheng.model.FriendModel;
import app.net.tongcheng.model.FriendsBean;
import app.net.tongcheng.model.UpContentJSONModel;
import app.net.tongcheng.model.UpFriendInfoModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/12 17:22
 */
public class AddFriendActivity extends BaseActivity implements View.OnClickListener {

    private ViewHolder mViewHolder;
    private FriendBusiness mFriendBusiness;
    private EditText searchtext;
    private FriendModel mFriendModel;
    private TextView addbt;
    private String name, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend_layout);
        setTitle("添加好友");
        initView();
        mFriendBusiness = new FriendBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        mViewHolder.setOnClickListener(R.id.seartchbt);
        searchtext = mViewHolder.getView(R.id.searchtext);
        addbt = mViewHolder.getView(R.id.addbt);
        addbt.setOnClickListener(this);
    }


    @Override
    public void loadData() {
        mFriendModel = NativieDataUtils.getFriendModel();
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.CHECK:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    UpFriendInfoModel mUpFriendInfoModel = (UpFriendInfoModel) mConnectResult.getObject();
                    if (mUpFriendInfoModel.getFriendslist() == null || mUpFriendInfoModel.getFriendslist().size() == 0) {
                        mViewHolder.setVisibility(R.id.itemaddfriend, View.GONE);
                        DialogUtil.showTipsDialog(this, "没有查询到相关好友", null);
                    } else {
                        mViewHolder.setVisibility(R.id.itemaddfriend, View.VISIBLE);
                        FriendsBean mFriendsBean = mUpFriendInfoModel.getFriendslist().get(0);
                        if (!TextUtils.isEmpty(mFriendsBean.getPicture())) {
                            mViewHolder.setImage(R.id.friendist_tx, mFriendsBean.getPicture());
                        } else {
                            mViewHolder.setImage(R.id.friendist_tx, R.drawable.content5);
                        }
                        if (!TextUtils.isEmpty(mFriendsBean.getName())) {
                            mViewHolder.setText(R.id.name, mFriendsBean.getName());
                        } else {
                            mViewHolder.setText(R.id.name, "用户");
                        }
                        if (!TextUtils.isEmpty(mFriendsBean.getSex())) {
                            if ("男".equals(mFriendsBean.getSex())) {
                                mViewHolder.setImage(R.id.seximage, R.drawable.one_profile_male_left_dark).setVisibility(View.VISIBLE);
                            } else if ("女".equals(mFriendsBean.getSex())) {
                                mViewHolder.setImage(R.id.seximage, R.drawable.one_profile_female_left_dark).setVisibility(View.VISIBLE);
                            }
                        } else {
                            mViewHolder.setVisibility(R.id.seximage, View.INVISIBLE);
                        }
                        name = TextUtils.isEmpty(mFriendsBean.getName()) ? "用户" : mFriendsBean.getName();
                        phone = mFriendsBean.getMobileNumber();
                        addbt.setText("添加");
                        addbt.setEnabled(true);
                        mViewHolder.setText(R.id.phone, mFriendsBean.getMobileNumber());
                        if (mFriendModel != null && mFriendModel.getFriends() != null && mFriendModel.getFriends().size() > 0) {
                            for (FriendsBean mFriendsBeanSun : mFriendModel.getFriends()) {
                                if (mFriendsBeanSun.getUid().equals(mFriendsBean.getUid())) {
                                    addbt.setText("已添加");
                                    addbt.setEnabled(false);
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            case APPCationStation.SUMBIT:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    if (mFriendModel != null && NativieDataUtils.getTodyYMD().equals(mFriendModel.getUpdate())) {
                        mFriendModel.setUpdate("00000000");
                        NativieDataUtils.setFriendModel(mFriendModel);
                    }
                    sendEventBusMessage("FriendFragment.Refresh");
                    DialogUtil.showTipsDialog(this, "添加好友成功!", null);
                } else {
                    DialogUtil.showTipsDialog(this, "添加好友失败!", null);
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoding_Type) {
        ToastUtil.showToast("网络不可用,请检查网络！");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.seartchbt:
                String searchstr = searchtext.getText().toString();
                if (searchstr.length() < 6) {
                    ToastUtil.showToast("请输入正确的查询号码");
                } else {
                    mFriendBusiness.checkFriend(APPCationStation.CHECK, "查询中...", searchstr);
                }
                break;
            case R.id.addbt:
                UpContentJSONModel mUpContentJSONModel = new UpContentJSONModel();
                List<ContentModel> mData = new ArrayList<>();
                List<String> phones = new ArrayList<>();
                phones.add(phone);
                mData.add(new ContentModel(System.currentTimeMillis(), name, phones));
                mUpContentJSONModel.setContactlist(mData);
                mUpContentJSONModel.setMac(((TelephonyManager) TCApplication.mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());
                mFriendBusiness.uploadContens(APPCationStation.SUMBIT, "添加中...", JSON.toJSONString(mUpContentJSONModel));
                break;
        }
    }
}
