package app.net.tongcheng.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.view.View;

import com.alibaba.fastjson.JSON;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import app.net.tongcheng.Business.FriendBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.fragment.BaseFragment;
import app.net.tongcheng.fragment.FriendFragment;
import app.net.tongcheng.fragment.LifeFragment;
import app.net.tongcheng.fragment.MyFragment;
import app.net.tongcheng.fragment.RedPacketFragment;
import app.net.tongcheng.fragment.ShareFragment;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.CheckEvent;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.ContentModel;
import app.net.tongcheng.model.UpContentJSONModel;
import app.net.tongcheng.model.UpContentModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.ContentsUtil;
import app.net.tongcheng.util.NativieDataUtils;
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
    private FriendBusiness mFriendBusiness;
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
        setEventBus();
        mFriendBusiness = new FriendBusiness(this, this, mHandler);
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
        mPager.setOffscreenPageLimit(5);
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
        if (mRedPacketFragment != null) {
            // 首页加载数据
            mRedPacketFragment.loadData();
        }
        // 上传本地通讯录
        UpContentModel mUpContentModel = NativieDataUtils.getUpContentModel();
        if (mUpContentModel == null || !NativieDataUtils.getTodyYMD().equals(mUpContentModel.getUpdate())) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UpContentJSONModel mUpContentJSONModel = new UpContentJSONModel();
                    List<ContentModel> mData = ContentsUtil.getContacts(TCApplication.mContext);
                    if (mData == null || mData.size() == 0) return;
                    mUpContentJSONModel.setContactlist(mData);
                    mUpContentJSONModel.setMac(((TelephonyManager) TCApplication.mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());
                    Message mMessage = new Message();
                    mMessage.what = 10001;
                    mMessage.obj = JSON.toJSONString(mUpContentJSONModel);
                    mHandler.sendMessage(mMessage);
                }
            }).start();
        }
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                mFriendBusiness.uploadContens(APPCationStation.SUMBIT, "", (String) msg.obj);
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.SUMBIT:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    UpContentModel mUpContentModel = (UpContentModel) mConnectResult.getObject();
                    mUpContentModel.setUpdate(NativieDataUtils.getTodyYMD());
                    //NativieDataUtils.setUpContentModel(mUpContentModel);
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoding_Type) {

    }

    /**
     * tab选择处理
     *
     * @param tab
     */
    @Override
    public void onTabSelected(MaterialTab tab) {
        flag = true;
        mPager.setCurrentItem(tab.getPosition(), false);
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

    public static void setTabHintSpotVisibility(int item, int visibility) {
        if (mTabHost != null) {
            mTabHost.setTabHintSpotVisibility(item, visibility);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(CheckEvent event) {
        if (event != null) {
            switch (event.getMsg()) {
                case "MainActivity.page.0":
                    mPager.setCurrentItem(0);
                    break;
                case "FriendFragment.Refresh":
                    if (mFriendFragment != null && FriendFragment.isfirstloaddata) {
                        FriendFragment.isfirstloaddata = false;
                        if (mPager.getCurrentItem() == 2) {
                            mFriendFragment.loadData();
                        }
                    }
                    break;
            }
        }
    }
}
