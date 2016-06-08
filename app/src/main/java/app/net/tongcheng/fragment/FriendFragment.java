package app.net.tongcheng.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import app.net.tongcheng.Business.FriendBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.adapter.FriendHAdater;
import app.net.tongcheng.adapter.FriendVAdater;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.FriendModel;
import app.net.tongcheng.model.FriendsBean;
import app.net.tongcheng.model.UpFriendInfoModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.FriendBeanUtils;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.Utils;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/16 15:53
 */
public class FriendFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, TextWatcher {
    private ViewHolder mViewHolder;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView, mRecyclerViewH;
    private EditText et_search;
    private FriendBusiness mFriendBusiness;
    private FriendModel mFriendModel, mFriendModelTemp;
    private List<FriendsBean> mDataVList = new ArrayList<>();//竖直方向
    private List<FriendsBean> mDataHList = new ArrayList<>();//横方向
    private FriendVAdater mFriendVAdater;
    private FriendHAdater mFriendHAdater;
    public static boolean isfirstloaddata;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_friend_layout, null);
        initView(view);
        isfirstloaddata = false;
        mFriendBusiness = new FriendBusiness(this, getActivity(), mHandler);
        return view;
    }

    private void initView(View view) {
        mViewHolder = new ViewHolder(view, this);
        mViewHolder.setText(R.id.tv_title, "好友");
        mViewHolder.setVisibility(R.id.bt_close, View.GONE);
        et_search = mViewHolder.getView(R.id.et_search);
        et_search.addTextChangedListener(this);
        mSwipeRefreshLayout = mViewHolder.getView(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refurush_color);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = mViewHolder.getView(R.id.mRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TCApplication.mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerViewH = mViewHolder.getView(R.id.mRecyclerViewH);
        LinearLayoutManager linearLayoutManagerH = new LinearLayoutManager(TCApplication.mContext);
        linearLayoutManagerH.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerViewH.setLayoutManager(linearLayoutManagerH);
    }

    @Override
    public void loadData() {
        if (isfirstloaddata) {
            return;
        }
        isfirstloaddata = true;
        mHandler.sendEmptyMessageDelayed(10001, 100);
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                mFriendModel = NativieDataUtils.getFriendModel();
                if (mFriendModel != null && mFriendModel.getFriends() != null) {
                    for (FriendsBean mFriendsBean : mFriendModel.getFriends()) {
                        mFriendsBean.setSelect(false);
                    }
                }
                if (mFriendModel == null || !NativieDataUtils.getTodyYMD().equals(mFriendModel.getUpdate())) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    mFriendBusiness.getFriends(APPCationStation.LOADING, "", mFriendModel == null ? "1.0" : mFriendModel.getVer());
                }
                mHandler.sendEmptyMessage(10003);
                break;
            case 10002:
                mSwipeRefreshLayout.setRefreshing(false);
                mHandler.sendEmptyMessage(10003);
                break;
            case 10003:
                if (mFriendVAdater == null) {
                    mFriendVAdater = new FriendVAdater(TCApplication.mContext, mDataVList, R.layout.personalredenvelopeadapter, mHandler, et_search);
                    mRecyclerView.setAdapter(mFriendVAdater);
                    mFriendHAdater = new FriendHAdater(TCApplication.mContext, mDataHList, R.layout.horizontallistviewadapter, mHandler);
                    mRecyclerViewH.setAdapter(mFriendHAdater);
                }
                mDataVList.clear();
                mDataHList.clear();
                if (mFriendModel != null && mFriendModel.getFriends() != null) {
                    String searchstr = et_search.getText().toString();
                    boolean issearchEmpty = TextUtils.isEmpty(searchstr);
                    for (FriendsBean mFriendsBean : mFriendModel.getFriends()) {
                        if (issearchEmpty || mFriendsBean.getSearchString().contains(searchstr)) {
                            mDataVList.add(mFriendsBean);
                        }
                        if (mFriendsBean.isSelect()) {
                            mDataHList.add(mFriendsBean);
                        }
                    }
                }
                mFriendVAdater.notifyDataSetChanged();
                mFriendHAdater.notifyDataSetChanged();
                break;
            case 10004:
                // 处理好友资料
                mFriendBusiness.getFriendsInfo(APPCationStation.LOADINGAD, "", JSON.toJSONString(mFriendModelTemp).replace("friends", "friendslist"));
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.LOADING:
                if (mConnectResult != null && mConnectResult.getObject() != null) {
                    final FriendModel mFriendModel = (FriendModel) mConnectResult.getObject();
                    if (mFriendModel.getResult() == 0) {
                        // 处理拼音
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if (mFriendModel.getFriends() != null && mFriendModel.getFriends().size() > 0) {
                                    for (FriendsBean mFriendsBean : mFriendModel.getFriends()) {
                                        FriendBeanUtils.pinYin4Remark(mFriendsBean);
                                        mFriendsBean.setPictureRED(FriendBeanUtils.getImageID());
                                    }
                                    Collections.sort(mFriendModel.getFriends());
                                }
                                mFriendModel.setUpdate(NativieDataUtils.getTodyYMD());
                                FriendFragment.this.mFriendModelTemp = mFriendModel;
                                mHandler.sendEmptyMessage(10004);
                            }
                        }).start();
                    } else if (mFriendModel.getResult() == 64) {
                        FriendFragment.this.mFriendModel.setUpdate(NativieDataUtils.getTodyYMD());
                        NativieDataUtils.setFriendModel(FriendFragment.this.mFriendModel);
                        mHandler.sendEmptyMessage(10002);
                    }
                }
                break;
            case APPCationStation.LOADINGAD:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    final UpFriendInfoModel mUpFriendInfoModel = (UpFriendInfoModel) mConnectResult.getObject();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (mUpFriendInfoModel.getFriendslist() != null && mUpFriendInfoModel.getFriendslist().size() > 0) {
                                HashMap<String, FriendsBean> maps = new HashMap<>();
                                for (FriendsBean mFriendsBean : mUpFriendInfoModel.getFriendslist()) {
                                    maps.put(mFriendsBean.getUid(), mFriendsBean);
                                }
                                for (FriendsBean mFriendsBean : mFriendModelTemp.getFriends()) {
                                    FriendsBean mFriendsBeanNew = maps.get(mFriendsBean.getUid());
                                    if (mFriendsBeanNew != null) {
                                        mFriendsBean.setInfo(mFriendsBeanNew.getProvince(), mFriendsBeanNew.getPicture(), mFriendsBeanNew.getPicmd5(),
                                                mFriendsBeanNew.getCompany(), mFriendsBeanNew.getProfession(), mFriendsBeanNew.getSchool(), mFriendsBeanNew.getSex(),
                                                mFriendsBeanNew.getBirthday(), mFriendsBeanNew.getSignature(), mFriendsBeanNew.getCity(), mFriendsBeanNew.getName());
                                        if (!TextUtils.isEmpty(mFriendsBean.getName())) {
                                            FriendBeanUtils.pinYin4Name(mFriendsBean);
                                        }
                                    }
                                }
                            }
                            NativieDataUtils.setFriendModel(mFriendModelTemp);
                            mFriendModel = mFriendModelTemp;
                            mFriendModelTemp = null;
                            mHandler.sendEmptyMessage(10002);
                        }
                    }).start();
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoding_Type) {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        if (mDataHList.size() > 0 && Utils.isNetWorkAvailable()) {
            DialogUtil.showTipsDialog(getActivity(), "提示", "刷新将会清空已选择好友，是否刷新？", "确定", "取消", new DialogUtil.OnConfirmListener() {
                @Override
                public void clickConfirm() {
                    mFriendBusiness.getFriends(APPCationStation.LOADING, "", mFriendModel == null ? "1.0" : mFriendModel.getVer());
                }

                @Override
                public void clickCancel() {

                }
            });
        } else {
            mFriendBusiness.getFriends(APPCationStation.LOADING, "", mFriendModel == null ? "1.0" : mFriendModel.getVer());
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mHandler.sendEmptyMessage(10003);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
