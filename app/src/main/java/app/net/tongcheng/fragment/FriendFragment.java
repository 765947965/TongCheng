package app.net.tongcheng.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.HashMap;

import app.net.tongcheng.Business.FriendBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.FriendModel;
import app.net.tongcheng.model.UpFriendInfoModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/16 15:53
 */
public class FriendFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ViewHolder mViewHolder;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView, mRecyclerViewH;
    private FriendBusiness mFriendBusiness;
    private FriendModel mFriendModel;
    public static boolean isfirstloaddata;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_friend_layout, null);
        initView(view);
        mFriendBusiness = new FriendBusiness(this, getActivity(), mHandler);
        return view;
    }

    private void initView(View view) {
        mViewHolder = new ViewHolder(view, this);
        mViewHolder.setText(R.id.tv_title, "好友");
        mViewHolder.setVisibility(R.id.bt_close, View.GONE);
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

                break;
            case 10004:
                // 处理好友资料
                mFriendBusiness.getFriendsInfo(APPCationStation.LOADINGAD, "", JSON.toJSONString(mFriendModel).replace("friends", "friendslist"));
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
                                for (FriendModel.FriendsBean mFriendsBean : mFriendModel.getFriends()) {
                                    StringBuilder SearchStringBuilder = new StringBuilder();
                                    SearchStringBuilder.append(mFriendsBean.getPhone() + ",");
                                    SearchStringBuilder.append(mFriendsBean.getRemark() + ",");
                                    if (TextUtils.isEmpty(mFriendsBean.getRemark())) continue;
                                    char[] remarks = mFriendsBean.getRemark().toCharArray();
                                    StringBuilder SearchStringBuilder_Q = new StringBuilder();
                                    StringBuilder SearchStringBuilder_S = new StringBuilder();
                                    for (char c : remarks) {
                                        String resultp = Pinyin.toPinyin(c);
                                        SearchStringBuilder_Q.append(resultp);
                                        SearchStringBuilder_S.append(resultp.substring(0, 1));
                                    }
                                    SearchStringBuilder.append(SearchStringBuilder_Q.toString() + ",");
                                    SearchStringBuilder.append(SearchStringBuilder_S.toString() + ",");
                                    mFriendsBean.setSearchString(SearchStringBuilder.toString());
                                }
                                mFriendModel.setUpdate(NativieDataUtils.getTodyYMD());
                                NativieDataUtils.setFriendModel(mFriendModel);
                                FriendFragment.this.mFriendModel = mFriendModel;
                                mHandler.sendEmptyMessage(10002);
                                mHandler.sendEmptyMessage(10004);
                            }
                        }).start();
                    } else if (mFriendModel.getResult() == 64) {
                        FriendFragment.this.mFriendModel.setUpdate(NativieDataUtils.getTodyYMD());
                        NativieDataUtils.setFriendModel(FriendFragment.this.mFriendModel);
                    }
                }
                break;
            case APPCationStation.LOADINGAD:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    final UpFriendInfoModel mUpFriendInfoModel = (UpFriendInfoModel) mConnectResult.getObject();
                    if (mUpFriendInfoModel.getFriendslist() != null && mUpFriendInfoModel.getFriendslist().size() > 0) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HashMap<String, FriendModel.FriendsBean> maps = new HashMap<>();
                                for (FriendModel.FriendsBean mFriendsBean : mUpFriendInfoModel.getFriendslist()) {
                                    maps.put(mFriendsBean.getUid(), mFriendsBean);
                                }
                                for (FriendModel.FriendsBean mFriendsBean : mFriendModel.getFriends()) {
                                    FriendModel.FriendsBean mFriendsBeanNew = maps.get(mFriendsBean.getUid());
                                    if (mFriendsBeanNew != null) {
                                        mFriendsBean.setInfo(mFriendsBeanNew.getProvince(), mFriendsBeanNew.getPicture(), mFriendsBeanNew.getPicmd5(),
                                                mFriendsBeanNew.getCompany(), mFriendsBeanNew.getProfession(), mFriendsBeanNew.getSchool(), mFriendsBeanNew.getSex(),
                                                mFriendsBeanNew.getBirthday(), mFriendsBeanNew.getSignature(), mFriendsBeanNew.getCity(), mFriendsBeanNew.getName());
                                        if (!TextUtils.isEmpty(mFriendsBean.getName())) {
                                            StringBuilder SearchStringBuilder = new StringBuilder();
                                            SearchStringBuilder.append(mFriendsBean.getSearchString());
                                            char[] names = mFriendsBean.getName().toCharArray();
                                            StringBuilder SearchStringBuilder_Q = new StringBuilder();
                                            StringBuilder SearchStringBuilder_S = new StringBuilder();
                                            for (char c : names) {
                                                String resultp = Pinyin.toPinyin(c);
                                                SearchStringBuilder_Q.append(resultp);
                                                SearchStringBuilder_S.append(resultp.substring(0, 1));
                                            }
                                            SearchStringBuilder.append(SearchStringBuilder_Q.toString() + ",");
                                            SearchStringBuilder.append(SearchStringBuilder_S.toString() + ",");
                                            mFriendsBean.setSearchString(SearchStringBuilder.toString());
                                        }
                                    }
                                }
                                NativieDataUtils.setFriendModel(mFriendModel);
                                mHandler.sendEmptyMessage(10002);
                            }
                        }).start();
                    }
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoding_Type) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {

    }
}
