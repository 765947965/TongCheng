package app.net.tongcheng.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kevin.wraprecyclerview.WrapRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import app.net.tongcheng.Business.RedBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.adapter.RedListAdapter;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.ExcreteRedModel;
import app.net.tongcheng.model.GiftsBean;
import app.net.tongcheng.model.RedModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.ErrorInfoUtil;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.Utils;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/4 10:43
 */
public class RedListActivity extends BaseActivity implements View.OnClickListener, RedListAdapter.RedListAdapterSetDialog, SwipeRefreshLayout.OnRefreshListener {
    private ViewHolder mViewHolder, mHeadViewHolder;
    private String year, direct;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private WrapRecyclerView mRecyclerView;
    private RedListAdapter mRedListAdapter;
    private RedBusiness mRedBusiness;
    private List<GiftsBean> mDatas;
    private RedModel mRedModel;
    private AlertDialog mAlertDialog;
    private int selectRedModel;
    private boolean isFilterExcrete;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.red_list_data_layout);
        year = NativieDataUtils.getTodyY();
        direct = "received";
        initView();
        mRedBusiness = new RedBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        mSwipeRefreshLayout = mViewHolder.getView(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refurush_color);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mViewHolder.setOnClickListener(R.id.iv_close);
        mRecyclerView = mViewHolder.getView(R.id.mRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TCApplication.mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        View headView = LayoutInflater.from(TCApplication.mContext).inflate(R.layout.red_list_head_data_layout, null);
        mHeadViewHolder = new ViewHolder(headView, this);
        mHeadViewHolder.setOnClickListener(R.id.myred_changeredtype);
        mHeadViewHolder.setOnClickListener(R.id.redslat_yearchange_layout);
        mHeadViewHolder.setText(R.id.redslat_yearchange, year);
        mHeadViewHolder.setText(R.id.myred_changeredtype, "收到的红包");
        mRecyclerView.addHeaderView(headView);
        initPOPView();
    }

    private void initPOPView() {
        View popView = LayoutInflater.from(RedListActivity.this).inflate(
                R.layout.red_popupwindow, null);
        mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#00000000"));
        mPopupWindow.setBackgroundDrawable(dw);
        TextView ppwindow_redall = (TextView) popView
                .findViewById(R.id.ppwindow_redall);
        TextView ppwindow_rednot = (TextView) popView
                .findViewById(R.id.ppwindow_rednot);
        TextView sendoutred_bt = (TextView) popView
                .findViewById(R.id.sendoutred_bt);
        ppwindow_redall.setOnClickListener(this);
        ppwindow_rednot.setOnClickListener(this);
        sendoutred_bt.setOnClickListener(this);
    }

    @Override
    public void loadData() {
        if (NativieDataUtils.getTodyY().compareTo("2016") < 0) {
            DialogUtil.showTipsDialog(this, "手机时间不正确，请调整手机时间后刷新！", null);
            return;
        }
        mHandler.sendEmptyMessageDelayed(10001, 100);
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                mRedModel = NativieDataUtils.getRedModel(year, direct);
                if (mRedModel == null || !NativieDataUtils.getTodyYMD().equals(mRedModel.getUpdate())) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    mRedBusiness.getRedList(APPCationStation.LOADING, "", year, direct);
                }
                mHandler.sendEmptyMessage(10003);
                break;
            case 10002:
                mSwipeRefreshLayout.setRefreshing(false);
                mHandler.sendEmptyMessage(10003);
                break;
            case 10003:
                // 显示数据
                if (mRedListAdapter == null) {
                    mDatas = new ArrayList<>();
                    mRedListAdapter = new RedListAdapter(this, mDatas, mSwipeRefreshLayout, mRedBusiness, this);
                    mRecyclerView.setAdapter(mRedListAdapter);
                }
                mDatas.clear();
                if (mRedModel != null && mRedModel.getGifts() != null && mRedModel.getGifts().size() > 0) {
                    if ("received".equals(direct) && isFilterExcrete) {
                        for (GiftsBean mGiftsBean : mRedModel.getGifts()) {
                            if (mGiftsBean.getHas_open() == 0) {
                                mDatas.add(mGiftsBean);
                            }
                        }
                    } else {
                        mDatas.addAll(mRedModel.getGifts());
                    }
                }
                mRedListAdapter.notifyDataSetChanged();
                // 统计金额
                mHeadViewHolder.setText(R.id.myred_allmoneynum, mDatas.size() + "个");
                if ("received".equals(direct) && isFilterExcrete) {
                    mHeadViewHolder.setText(R.id.myred_allmoney, "未知");
                } else if ("received".equals(direct) && !isFilterExcrete) {
                    double money_all = 0;
                    for (GiftsBean mGiftsBean : mDatas) {
                        if (mGiftsBean.getHas_open() == 1) {
                            money_all += mGiftsBean.getMoney();
                        }
                    }
                    mHeadViewHolder.setText(R.id.myred_allmoney, money_all / 100d + "元");
                } else {
                    double money_all = 0;
                    for (GiftsBean mGiftsBean : mDatas) {
                        money_all += mGiftsBean.getMoney();
                    }
                    mHeadViewHolder.setText(R.id.myred_allmoney, money_all / 100d + "元");
                }
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.LOADING:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    final RedModel mRedModel = (RedModel) mConnectResult.getObject();
                    mRedModel.setUpdate(NativieDataUtils.getTodyYMD());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // 排序(耗时)
                            if (mRedModel.getGifts() != null && mRedModel.getGifts().size() > 0) {
                                Collections.sort(mRedModel.getGifts());
                            }
                            NativieDataUtils.setRedModel(mRedModel, year, direct);
                            RedListActivity.this.mRedModel = mRedModel;
                            mHandler.sendEmptyMessage(10002);
                        }
                    });
                }
                break;
            case APPCationStation.EXCRETERED:
                if (mConnectResult != null && mConnectResult.getObject() != null) {
                    ExcreteRedModel mExcreteRedModel = (ExcreteRedModel) mConnectResult.getObject();
                    if (mExcreteRedModel.getResult() == 0) {
                        GiftsBean itemdata = mDatas.get(selectRedModel);
                        itemdata.setHas_open(1);
                        itemdata.setMoney(mExcreteRedModel.getAward_money());
                        itemdata.setOpen_time(Utils.sdformat.format(new Date()));
                        mRedListAdapter.notifyDataSetChanged();
                        NativieDataUtils.setRedModel(mRedModel, year, direct);
                        if (mAlertDialog != null && mAlertDialog.isShowing()) {
                            mAlertDialog.dismiss();
                        }
                        // 启动详情页
                        startActivity(new Intent(TCApplication.mContext, RedShareInfoActivity.class).putExtra("GiftsBean", itemdata));
                    } else {
                        if (mAlertDialog != null && mAlertDialog.isShowing()) {
                            ((ImageView) mAlertDialog.findViewById(R.id.red_anim_image)).setImageResource(R.drawable.rpopen);
                            mAlertDialog.findViewById(R.id.red_anim_image).setEnabled(true);
                            ((TextView) mAlertDialog.findViewById(R.id.red_errortext)).setText(ErrorInfoUtil.getErrorMessage(mExcreteRedModel.getResult()));
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoding_Type) {
        switch (mLoding_Type) {
            case APPCationStation.LOADING:
                mSwipeRefreshLayout.setRefreshing(false);
                break;
            case APPCationStation.EXCRETERED:
                if (mAlertDialog != null && mAlertDialog.isShowing()) {
                    ((ImageView) mAlertDialog.findViewById(R.id.red_anim_image)).setImageResource(R.drawable.rpopen);
                    mAlertDialog.findViewById(R.id.red_anim_image).setEnabled(true);
                    ((TextView) mAlertDialog.findViewById(R.id.red_errortext)).setText("请检查网络连接!");
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.myred_changeredtype://改变类型
                if (mSwipeRefreshLayout.isRefreshing()) {
                    ToastUtil.showToast("正在同步数据...");
                    return;
                }
                showPop(mHeadViewHolder.getView(R.id.myred_changeredtype));
                break;
            case R.id.redslat_yearchange_layout://改变年份
                if (mSwipeRefreshLayout.isRefreshing()) {
                    ToastUtil.showToast("正在同步数据...");
                    return;
                }
                break;
            case R.id.ppwindow_redall:
                mPopupWindow.dismiss();
                if ("received".equals(direct) && !isFilterExcrete) {
                    return;
                }
                direct = "received";
                isFilterExcrete = false;
                mHeadViewHolder.setText(R.id.myred_changeredtype, "收到的红包");
                mHeadViewHolder.setText(R.id.sendorrecevedtext, "已收到");
                loadData();
                break;
            case R.id.ppwindow_rednot:
                mPopupWindow.dismiss();
                if ("received".equals(direct) && isFilterExcrete) {
                    return;
                }
                direct = "received";
                isFilterExcrete = true;
                mHeadViewHolder.setText(R.id.myred_changeredtype, "未拆红包");
                mHeadViewHolder.setText(R.id.sendorrecevedtext, "已收到");
                loadData();
                break;
            case R.id.sendoutred_bt:
                mPopupWindow.dismiss();
                if ("sended".equals(direct)) {
                    return;
                }
                direct = "sended";
                mHeadViewHolder.setText(R.id.myred_changeredtype, "发出的红包");
                mHeadViewHolder.setText(R.id.sendorrecevedtext, "已发出");
                loadData();
                break;
        }
    }

    /**
     * 显示popWindow
     */
    public void showPop(View parent) {
        if (mPopupWindow == null) {
            return;
        }
        mPopupWindow.showAsDropDown(parent);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.update();
    }

    @Override
    public void onRefresh() {
        if (NativieDataUtils.getTodyY().compareTo("2016") < 0) {
            DialogUtil.showTipsDialog(this, "手机时间不正确，请调整手机时间后刷新！", null);
            return;
        }
        mRedBusiness.getRedList(APPCationStation.LOADING, "", year, direct);
    }

    @Override
    public void setmAlertDialog(AlertDialog mAlertDialog, int selectRedModel) {
        this.mAlertDialog = mAlertDialog;
        this.selectRedModel = selectRedModel;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }
}
