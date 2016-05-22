package app.net.tongcheng.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
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
//    private String mTextViewArray[] = {"红包", "生活", "好友", "分享", "我的"};
    // 默认状态图片数组
    private int iconNormals[] = {R.drawable.home_tab_red_black, R.drawable.home_tab_life_black, R.drawable.home_tab_frend_black, R.drawable.home_tab_share_black, R.drawable.home_tab_my_black};
    // 激活状态图片数组
    private int iconActivateds[] = {R.drawable.home_tab_red, R.drawable.home_tab_life, R.drawable.home_tab_frend, R.drawable.home_tab_share, R.drawable.home_tab_my};

    private boolean flag = false;


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
        mTabHost.setTabType(MaterialTab.TYPE_LARGE_ICON);
        mTabHost.setPrimaryColor(Color.parseColor("#FFFFFF"));
        mTabHost.setAccentColor(Color.parseColor("#80CCCCCC"));
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            mTabHost.addTab(
                    mTabHost.newTab()
                            .setIcon(iconNormals[i], iconActivateds[i])
                            .setSelectorVisibility(View.GONE)
                            .setTabListener(this)
            );
        }
    }

    @Override
    public void loadData() {
        if(mRedPacketFragment != null){
            // 首页加载数据
            mRedPacketFragment.loadData();
        }
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

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
        if (mRedPacketFragment != null && mRedPacketFragment.isAdded() && mRedPacketFragment.isVisible()) {
            getSupportFragmentManager().putFragment(outState, RedPacketFragment.class.getName(), mRedPacketFragment);
        }
        if (mLifeFragment != null && mLifeFragment.isAdded() && mLifeFragment.isVisible()) {
            getSupportFragmentManager().putFragment(outState, LifeFragment.class.getName(), mLifeFragment);
        }
        if (mFriendFragment != null && mFriendFragment.isAdded() && mFriendFragment.isVisible()) {
            getSupportFragmentManager().putFragment(outState, FriendFragment.class.getName(), mFriendFragment);
        }
        if (mShareFragment != null && mShareFragment.isAdded() && mShareFragment.isVisible()) {
            getSupportFragmentManager().putFragment(outState, ShareFragment.class.getName(), mShareFragment);
        }
        if (mMyFragment != null && mMyFragment.isAdded() && mMyFragment.isVisible()) {
            getSupportFragmentManager().putFragment(outState, MyFragment.class.getName(), mMyFragment);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Fragment temp_mRedPacketFragment = getSupportFragmentManager().getFragment(savedInstanceState, RedPacketFragment.class.getName());
        if (temp_mRedPacketFragment != null) {
            mRedPacketFragment = (RedPacketFragment) temp_mRedPacketFragment;
        }
        Fragment temp_mLifeFragment = getSupportFragmentManager().getFragment(savedInstanceState, LifeFragment.class.getName());
        if (temp_mLifeFragment != null) {
            mLifeFragment = (LifeFragment) temp_mLifeFragment;
        }
        Fragment temp_mFriendFragment = getSupportFragmentManager().getFragment(savedInstanceState, FriendFragment.class.getName());
        if (temp_mFriendFragment != null) {
            mFriendFragment = (FriendFragment) temp_mFriendFragment;
        }
        Fragment temp_mShareFragment = getSupportFragmentManager().getFragment(savedInstanceState, ShareFragment.class.getName());
        if (temp_mShareFragment != null) {
            mShareFragment = (ShareFragment) temp_mShareFragment;
        }
        Fragment temp_mMyFragment = getSupportFragmentManager().getFragment(savedInstanceState, MyFragment.class.getName());
        if (temp_mMyFragment != null) {
            mMyFragment = (MyFragment) temp_mMyFragment;
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    public static void setTabHintSpotVisibility(int item, int visibility) {
        if (mTabHost != null) {
            mTabHost.setTabHintSpotVisibility(item, visibility);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
