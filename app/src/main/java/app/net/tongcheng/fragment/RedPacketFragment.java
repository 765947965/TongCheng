package app.net.tongcheng.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import app.net.tongcheng.Business.RedBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.activity.BalanceActivity;
import app.net.tongcheng.activity.PayMoneyActivity;
import app.net.tongcheng.activity.PublicWebview;
import app.net.tongcheng.activity.ReChargeActivity;
import app.net.tongcheng.activity.RedListActivity;
import app.net.tongcheng.activity.RedShareInfoActivity;
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
 * @date: 2016/4/16 15:42
 */
public class RedPacketFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, RedListAdapter.RedListAdapterSetDialog {

    private ViewHolder mViewHolder;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RedListAdapter mRedListAdapter;
    private RedBusiness mRedBusiness;
    private List<GiftsBean> mDatas;
    private RedModel mRedModel;
    private AlertDialog mAlertDialog;
    private int selectRedModel;
    private int notERedNum;
    public static boolean isfirstloaddata;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(TCApplication.mContext).inflate(R.layout.fragment_red_packet_layout, null);
        initView(view);
        isfirstloaddata = false;
        mRedBusiness = new RedBusiness(this, getActivity(), mHandler);
        return view;
    }


    private void initView(View view) {
        mViewHolder = new ViewHolder(view, this);
        mSwipeRefreshLayout = mViewHolder.getView(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refurush_color);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = mViewHolder.getView(R.id.mRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TCApplication.mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mViewHolder.setOnClickListener(R.id.llt_fukuang);
        mViewHolder.setOnClickListener(R.id.llt_balance);
        mViewHolder.setOnClickListener(R.id.llt_redlist);
        mViewHolder.setOnClickListener(R.id.llt_rechage);
        mViewHolder.setOnClickListener(R.id.llt_rechage_remark);
    }

    @Override
    public void loadData() {//第一次加载或者主动加载
        //读取数据
        if (isfirstloaddata) {
            return;
        }
        if (NativieDataUtils.getTodyY().compareTo("2016") < 0) {
            DialogUtil.showTipsDialog(getActivity(), "手机时间不正确，请调整手机时间后刷新！", null);
            return;
        }
        isfirstloaddata = true;
        mHandler.sendEmptyMessageDelayed(10001, 100);
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                mRedModel = NativieDataUtils.getRedModel(NativieDataUtils.getTodyY(), "received");
                if (mRedModel == null || !NativieDataUtils.getTodyYMD().equals(mRedModel.getUpdate())) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    mRedBusiness.getRedList(APPCationStation.LOADING, "", NativieDataUtils.getTodyY(), "received");
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
                    mRedListAdapter = new RedListAdapter(getActivity(), mDatas, mSwipeRefreshLayout, mRedBusiness, this);
                    mRecyclerView.setAdapter(mRedListAdapter);
                }
                mDatas.clear();
                if (mRedModel != null && mRedModel.getGifts() != null && mRedModel.getGifts().size() > 0) {
                    mDatas.addAll(mRedModel.getGifts());
                }
                mRedListAdapter.notifyDataSetChanged();
                break;
            case 10004:
                if (notERedNum > 0) {
                    if (notERedNum == 1) {
                        setmAlertDialog(DialogUtil.getExcreteRedDilaog(getActivity(), mRedModel.getGifts().get(0), mRedBusiness), 0);
                    } else {
                        DialogUtil.showExcreteRedTipsDilaog(getActivity(), notERedNum);
                    }
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
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // 排序(耗时)
                            if (mRedModel.getGifts() != null && mRedModel.getGifts().size() > 0) {
                                Collections.sort(mRedModel.getGifts());
                            }
                            NativieDataUtils.setRedModel(mRedModel, NativieDataUtils.getTodyY(), "received");
                            RedPacketFragment.this.mRedModel = mRedModel;
                            mHandler.sendEmptyMessage(10002);
                            // 提示未拆红包
                            notERedNum = 0;
                            String datestr_today = Utils.sdformat_3.format(new Date());
                            if (mRedModel.getGifts() != null && mRedModel.getGifts().size() > 0) {
                                for (GiftsBean mGiftsBean : mRedModel.getGifts()) {
                                    if (mGiftsBean.getHas_open() == 1) {
                                        break;
                                    }
                                    if (mGiftsBean.getHas_open() == 0) {
                                        if (datestr_today.compareTo(mGiftsBean.getExp_time()) > 0) {
                                            break;
                                        }
                                    }
                                    notERedNum += 1;
                                }
                            }
                            mHandler.sendEmptyMessage(10004);
                        }
                    }).start();
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
                        NativieDataUtils.setRedModel(mRedModel, NativieDataUtils.getTodyY(), "received");
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
            case R.id.llt_fukuang://付款
                //startActivity(new Intent(TCApplication.mContext, PayMoneyActivity.class));
                break;
            case R.id.llt_balance://余额
                startActivity(new Intent(TCApplication.mContext, BalanceActivity.class));
                break;
            case R.id.llt_redlist://红包列表
                if (mSwipeRefreshLayout.isRefreshing()) {
                    ToastUtil.showToast("正在同步数据...");
                    return;
                }
                startActivity(new Intent(TCApplication.mContext, RedListActivity.class));
                break;
            case R.id.llt_rechage:
                startActivity(new Intent(TCApplication.mContext, ReChargeActivity.class));
                break;
            case R.id.llt_rechage_remark:
                String url = "http://user.8hbao.com:8060/usercenter/mobile_chargelog.php?phone=" + TCApplication.getmUserInfo().getPhone() + "&uid=" + TCApplication.getmUserInfo().getUid();
                startActivity(new Intent(TCApplication.mContext, PublicWebview.class).putExtra("title", "充值记录").putExtra("url", url));
                break;
        }
    }

    @Override
    public void onRefresh() {
        if (NativieDataUtils.getTodyY().compareTo("2016") < 0) {
            DialogUtil.showTipsDialog(getActivity(), "手机时间不正确，请调整手机时间后刷新！", null);
            return;
        }
        mRedBusiness.getRedList(APPCationStation.LOADING, "", NativieDataUtils.getTodyY(), "received");
    }

    @Override
    public void setmAlertDialog(AlertDialog mAlertDialog, int selectRedModel) {
        this.mAlertDialog = mAlertDialog;
        this.selectRedModel = selectRedModel;
    }
}
