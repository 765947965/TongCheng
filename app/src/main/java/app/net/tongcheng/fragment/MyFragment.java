package app.net.tongcheng.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * @date: 2016/4/16 15:55
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {

    private ViewHolder mViewHolder;
    private PullToRefreshScrollView pullScroll;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_my_layout, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mViewHolder = new ViewHolder(view, this);
        pullScroll = mViewHolder.getView(R.id.pullScroll);
        pullScroll.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                // TODO Auto-generated method stub
                String label = DateUtils.formatDateTime(TCApplication.mContext, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
                        | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                loadData();
            }
        });
    }

    private void loadData() {
        //读取数据 模拟延迟
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                pullScroll.onRefreshComplete();
            }
        }.sendEmptyMessageDelayed(1, 5000);
    }

    @Override
    public void loadDataAndPull() {
        pullScroll.getRefreshableView().scrollTo(0, 0);
        pullScroll.showRefresh();
        Log.i("aaac","myset");
    }

    @Override
    public void onClick(View v) {

    }
}
