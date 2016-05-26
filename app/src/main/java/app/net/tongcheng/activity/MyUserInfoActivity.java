package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import app.net.tongcheng.R;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/5/25 17:23
 */
public class MyUserInfoActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    private ViewHolder mViewHolder;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_user_info_layout);
        setTitle("我");
        initView();
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.mSwipeRefreshLayout), this);
        mSwipeRefreshLayout = mViewHolder.getView(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refurush_color);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void loadData() {

    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {

    }
}
