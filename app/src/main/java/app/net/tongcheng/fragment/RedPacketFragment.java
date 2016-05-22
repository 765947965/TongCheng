package app.net.tongcheng.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/16 15:42
 */
public class RedPacketFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ViewHolder mViewHolder;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;
    public static boolean isfirstloaddata;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(TCApplication.mContext).inflate(R.layout.fragment_red_packet_layout, null);
        initView(view);
        loadData();
        isfirstloaddata = true;
        return view;
    }


    private void initView(View view) {
        mViewHolder = new ViewHolder(view, this);
        mSwipeRefreshLayout = mViewHolder.getView(R.id.sfl_main);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refurush_color);
        mSwipeRefreshLayout.setProgressViewEndTarget(true, 150);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mListView = mViewHolder.getView(R.id.mListView);
        View mHeadView = LayoutInflater.from(TCApplication.mContext).inflate(R.layout.redpacket_fragment_headview_layout, null);
        mListView.addHeaderView(mHeadView);
        mListView.setAdapter(null);
    }

    @Override
    public void loadData() {//第一次加载或者主动加载
        //读取数据
        if (isfirstloaddata) {
            return;
        }
        mHandler.sendEmptyMessageDelayed(102, 500);
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        if (msg.what == 101) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (msg.what == 102) {
            mSwipeRefreshLayout.setRefreshing(true);
            onRefresh();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        //模拟延迟
        mHandler.sendEmptyMessageDelayed(101, 3000);
    }
}
