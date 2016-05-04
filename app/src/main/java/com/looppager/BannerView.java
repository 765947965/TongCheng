package com.looppager;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.viewpagerindicator.IconPageIndicator;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import app.net.tongcheng.R;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/2/26 17:04
 */
public class BannerView extends FrameLayout {

    private Context mContext;

    private LoopViewPager mLoopViewPager;
    private IconPageIndicator mIconPageIndicator;
    private long period;
    private boolean havaData;
    private int Currentitem;
    private int items;
    private ScheduledExecutorService mSEService;

    private Handler bannerHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1) {
                // 设定viewPager当前页面
                if (Currentitem % items == mLoopViewPager.getCurrentItem()) {
                    Currentitem++;
                    mLoopViewPager.setCurrentItem(mLoopViewPager.getCurrentItem() + 1);
                } else {
                    Currentitem = mLoopViewPager.getCurrentItem();//当用户手动滑动Banner后停留在该页一个时间段
                }
            }
        }
    };


    public BannerView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BannerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.banner_layout, this, true);
        mLoopViewPager = (LoopViewPager) view.findViewById(R.id.mLoopViewPager);
        mIconPageIndicator = (IconPageIndicator) view.findViewById(R.id.mIconPageIndicator);
    }

    /**
     * @param adapter
     * @param items        items 数量
     * @param scrollFactor 动画执行时间
     * @param period       翻页间隔时间
     */
    public void setAdapter(PagerAdapter adapter, int items, double scrollFactor, long period) {
        if (mSEService != null) {
            mSEService.shutdown();
            mSEService = null;
        }
        havaData = true;
        this.items = items;
        this.period = period;
        mLoopViewPager.setAdapter(adapter);
        if (scrollFactor != 0) {
            mLoopViewPager.setScrollDurationFactor(scrollFactor);
        }
        mIconPageIndicator.setPadding(10, 0, 10, 0);
        mIconPageIndicator.setViewPager(mLoopViewPager);
        Currentitem = 0;
        if (period > 0) {
            if (mSEService == null) {
                mSEService = Executors.newSingleThreadScheduledExecutor();
                mSEService.scheduleAtFixedRate(new ViewPagerTask(), period, period, TimeUnit.SECONDS);
            }
        }
    }

    public void onPause() {
        if (havaData && period > 0 && mSEService != null) {
            mSEService.shutdown();
            mSEService = null;
        }
    }

    public void onResume() {
        if (havaData && period > 0) {
            if (mSEService != null) {
                mSEService.shutdown();
                mSEService = null;
            }
            if (mSEService == null) {
                mSEService = Executors.newSingleThreadScheduledExecutor();
                mSEService.scheduleAtFixedRate(new ViewPagerTask(), period, period, TimeUnit.SECONDS);
            }
        }
    }

    private class ViewPagerTask implements Runnable {

        @Override
        public void run() {
            bannerHandler.sendEmptyMessage(1);
        }
    }


}
