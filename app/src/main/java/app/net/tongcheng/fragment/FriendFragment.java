package app.net.tongcheng.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import app.net.tongcheng.Business.FriendBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.activity.AddFriendActivity;
import app.net.tongcheng.activity.PersonalRedEnvelopeConfig;
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
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.Utils;
import app.net.tongcheng.util.ViewHolder;
import app.net.tongcheng.view.MailList_abcList;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/16 15:53
 */
public class FriendFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, TextWatcher, View.OnTouchListener {
    private ViewHolder mViewHolder;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView, mRecyclerViewH;
    private ImageView ivRight;
    private MailList_abcList mMailList;
    private EditText et_search;
    private TextView tv_show, tv_next;
    private FriendBusiness mFriendBusiness;
    private FriendModel mFriendModel, mFriendModelTemp;
    private List<FriendsBean> mDataVList = new ArrayList<>();//竖直方向
    private List<FriendsBean> mDataHList = new ArrayList<>();//横方向
    private HashMap<String, Integer> searmap = new HashMap<>();//检索用
    private FriendVAdater mFriendVAdater;
    private FriendHAdater mFriendHAdater;
    private String[] jss = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "~"};
    public static boolean isfirstloaddata;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        className = this.getClass().getSimpleName();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_friend_layout, null);
        initView(view);
        isfirstloaddata = false;
        mFriendBusiness = new FriendBusiness(this, getActivity(), mHandler);
        return view;
    }

    private void initView(View view) {
        mViewHolder = new ViewHolder(view, this);
        mViewHolder.setText(R.id.tv_title, "好友");
        mViewHolder.setVisibility(R.id.viewBaseLine, View.GONE);
        mViewHolder.setVisibility(R.id.bt_close, View.GONE);
        et_search = mViewHolder.getView(R.id.et_search);
        et_search.addTextChangedListener(this);
        mMailList = mViewHolder.getView(R.id.mlist_abclist);
        mMailList.setOnTouchListener(this);
        tv_show = mViewHolder.getView(R.id.tv_show);
        tv_next = mViewHolder.getView(R.id.tv_next);
        tv_next.setOnClickListener(this);
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
        ivRight = mViewHolder.setVisibility(R.id.ivRight, View.VISIBLE);
        ivRight.setImageResource(R.drawable.image_add_friend);
        ivRight.setOnClickListener(this);
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
                    mFriendVAdater = new FriendVAdater(TCApplication.mContext, mDataVList, R.layout.personalredenvelopeadapter, mHandler, et_search, mSwipeRefreshLayout);
                    mRecyclerView.setAdapter(mFriendVAdater);
                    mFriendHAdater = new FriendHAdater(TCApplication.mContext, mDataHList, R.layout.horizontallistviewadapter, mHandler, mSwipeRefreshLayout);
                    mRecyclerViewH.setAdapter(mFriendHAdater);
                }
                searmap.clear();
                mDataVList.clear();
                mDataHList.clear();
                if (mFriendModel != null && mFriendModel.getFriends() != null) {
                    String searchstr = et_search.getText().toString().toUpperCase();
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
                if (mDataHList.size() > 0) {
                    tv_next.setEnabled(true);
                    tv_next.setText("下一步(" + mDataHList.size() + ")");
                } else {
                    tv_next.setEnabled(false);
                    tv_next.setText("下一步");
                }
                // 添加索引
                if (mDataVList.size() < 6) {
                    return;
                }
                for (int i = 0; i < mDataVList.size(); i++) {
                    if (i == 0) {
                        searmap.put(mDataVList.get(i).getFY(), i);
                    } else {
                        if (!mDataVList.get(i - 1).getFY().equals(mDataVList.get(i).getFY())) {
                            searmap.put(mDataVList.get(i).getFY(), i);
                        }
                    }
                }
                for (int i = 0; i < jss.length; i++) {
                    if (i == 0) {
                        if (searmap.get(jss[i]) == null) {
                            searmap.put(jss[i], 0);
                        }
                    } else {
                        if (searmap.get(jss[i]) == null) {
                            searmap.put(jss[i], searmap.get(jss[i - 1]));
                        }
                    }
                }
                break;
            case 10004:
                // 处理好友资料
                mFriendBusiness.getFriendsInfo(APPCationStation.LOADINGAD, "", JSON.toJSONString(mFriendModelTemp).replace("friends", "friendslist"));
                break;
            case 10005:
                tv_show.setVisibility(View.GONE);
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
        if (mSwipeRefreshLayout.isRefreshing()) {
            ToastUtil.showToast("数据同步中...");
            return;
        }
        switch (v.getId()) {
            case R.id.tv_next:
                StringBuilder uidstrb = new StringBuilder();
                for (FriendsBean mFriendsBean : mDataHList) {
                    uidstrb.append(mFriendsBean.getUid() + "|");
                }
                String uids = uidstrb.toString();
                uids = uids.substring(0, uids.length() - 1);
                startActivity(new Intent(TCApplication.mContext, PersonalRedEnvelopeConfig.class).putExtra("uids", uids).putExtra("name", mDataHList.get(0).getRemark()).putExtra("nums", mDataHList.size()));
                break;
            case R.id.ivRight://添加好友
                startActivity(new Intent(TCApplication.mContext, AddFriendActivity.class));
                break;
        }
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
                    mSwipeRefreshLayout.setRefreshing(false);
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
        if (mSwipeRefreshLayout.isRefreshing()) {
            return;
        }
        mHandler.sendEmptyMessage(10003);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            ToastUtil.showToast("数据同步中...");
            return true;
        }
        if (mDataVList.isEmpty()) {
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            float one_vy = (float) v.getHeight() / 27f;
            float ey = event.getY();
            int key = (int) (ey / one_vy);
            if (key < 0 || key > 26) {
                return true;
            }
            String key_value = jss[key];

            // 开启显示提示框
            if ("~".equals(key_value)) {
                tv_show.setText("#");
                tv_show.setVisibility(View.VISIBLE);
            } else {
                tv_show.setText(key_value);
                tv_show.setVisibility(View.VISIBLE);
            }
            if (mDataVList.size() > 5) {
                mRecyclerView.smoothScrollToPosition(searmap.get(key_value));
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mHandler.removeMessages(10005);
            mHandler.sendEmptyMessageDelayed(10005, 1000);
        }
        return true;
    }
}
