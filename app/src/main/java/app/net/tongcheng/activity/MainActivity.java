package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import app.net.tongcheng.R;
import app.net.tongcheng.fragment.BaseFragment;
import app.net.tongcheng.fragment.FriendFragment;
import app.net.tongcheng.fragment.LifeFragment;
import app.net.tongcheng.fragment.MyFragment;
import app.net.tongcheng.fragment.RedPacketFragment;
import app.net.tongcheng.fragment.ShareFragment;
import app.net.tongcheng.util.ViewHolder;
import app.net.tongcheng.view.materialtabs.MaterialTab;
import app.net.tongcheng.view.materialtabs.MaterialTabHost;
import app.net.tongcheng.view.materialtabs.MaterialTabListener;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/16 14:19
 */
public class MainActivity extends BaseActivity implements MaterialTabListener, ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewHolder mViewHolder;
    private ViewPager mPager;
    private ViewPagerAdapter pagerAdapter;
    private static MaterialTabHost mTabHost;
    private RedPacketFragment mRedPacketFragment;
    private LifeFragment mLifeFragment;
    private FriendFragment mFriendFragment;
    private ShareFragment mShareFragment;
    private MyFragment mMyFragment;
    private List<BaseFragment> listFragment = new ArrayList<>();
    // 定义数组来存放按钮图片
    private String mTextViewArray[] = {"红包", "生活", "好友", "分享", "我的"};
    // 默认状态图片数组
    private int iconNormals[] = {R.mipmap.home_tab_tuandai_black, R.mipmap.home_tab_tozi_black, R.mipmap.home_tab_faxian_black, R.mipmap.home_tab_chifu_black, R.mipmap.home_tab_tuandai_black2};
    // 激活状态图片数组
    private int iconActivateds[] = {R.mipmap.home_tab_tuandai, R.mipmap.home_tab_tozi, R.mipmap.home_tab_faxian, R.mipmap.home_tab_chifu, R.mipmap.home_tab_tuandai2};

    private boolean flag = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                BaseFragment bf = listFragment.get(msg.getData().getInt("position"));
                if (bf != null && bf.isfirstloaddata()) {
                    bf.setIsfirstloaddata(false);
                    bf.loadDataAndPull();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        setCanSlidingClose(false);
        initView();
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.rlt_main), this);
        mPager = mViewHolder.getView(R.id.viewpager);
        mTabHost = mViewHolder.getView(R.id.tabHost);
        mRedPacketFragment = new RedPacketFragment();
        mLifeFragment = new LifeFragment();
        mFriendFragment = new FriendFragment();
        mShareFragment = new ShareFragment();
        mMyFragment = new MyFragment();
        listFragment.add(mRedPacketFragment);
        listFragment.add(mLifeFragment);
        listFragment.add(mFriendFragment);
        listFragment.add(mShareFragment);
        listFragment.add(mMyFragment);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.addOnPageChangeListener(this);
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            mTabHost.addTab(
                    mTabHost.newTab()
                            .setIcon(iconNormals[i], iconActivateds[i])
                            .setText(mTextViewArray[i])
                            .setSelectorVisibility(View.GONE)
                            .setTabListener(this)
            );
        }
    }

    @Override
    public void loadData() {

    }

    /**
     * tab选择处理
     *
     * @param tab
     */
    @Override
    public void onTabSelected(MaterialTab tab) {
        flag = true;
        mPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (!flag) {
            mTabHost.setSelectedNavigationItem(position, positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {
        mTabHost.setSelectedNavigationItem(position);
        Message mMessage = new Message();
        Bundle mBundle = new Bundle();
        mBundle.putInt("position", position);
        mMessage.setData(mBundle);
        mHandler.sendMessageDelayed(mMessage, 100);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (0 == state && flag) {
            flag = false;
        }
    }

    @Override
    public void onClick(View v) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public BaseFragment getItem(int arg0) {
            return listFragment.get(arg0);
        }

        @Override
        public int getCount() {
            return listFragment.size();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, RedPacketFragment.class.getName(), mRedPacketFragment);
        getSupportFragmentManager().putFragment(outState, LifeFragment.class.getName(), mLifeFragment);
        getSupportFragmentManager().putFragment(outState, FriendFragment.class.getName(), mFriendFragment);
        getSupportFragmentManager().putFragment(outState, ShareFragment.class.getName(), mShareFragment);
        getSupportFragmentManager().putFragment(outState, MyFragment.class.getName(), mMyFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mRedPacketFragment = (RedPacketFragment) getSupportFragmentManager().getFragment(savedInstanceState, RedPacketFragment.class.getName());
        mLifeFragment = (LifeFragment) getSupportFragmentManager().getFragment(savedInstanceState, LifeFragment.class.getName());
        mFriendFragment = (FriendFragment) getSupportFragmentManager().getFragment(savedInstanceState, FriendFragment.class.getName());
        mShareFragment = (ShareFragment) getSupportFragmentManager().getFragment(savedInstanceState, ShareFragment.class.getName());
        mMyFragment = (MyFragment) getSupportFragmentManager().getFragment(savedInstanceState, MyFragment.class.getName());
        super.onRestoreInstanceState(savedInstanceState);
    }

    public static void setTabHintSpotVisibility(int item, int visibility) {
        if (mTabHost != null) {
            mTabHost.setTabHintSpotVisibility(item, visibility);
        }
    }
}
