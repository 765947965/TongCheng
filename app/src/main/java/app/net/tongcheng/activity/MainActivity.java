package app.net.tongcheng.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import app.net.tongcheng.Business.FriendBusiness;
import app.net.tongcheng.Business.OtherBusiness;
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
import app.net.tongcheng.model.MSGModel;
import app.net.tongcheng.model.UpContentJSONModel;
import app.net.tongcheng.model.UpContentModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.CastToUtil;
import app.net.tongcheng.util.ContentsUtil;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.GeneralDateUtils;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.OperationUtils;
import app.net.tongcheng.util.Utils;
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
    private OtherBusiness mOtherBusiness;
    private List<BaseFragment> listFragment = new ArrayList<>();
    // 定义数组来存放按钮图片
//    private String mTextViewArray[] = {"红包", "生活", "好友", "分享", "我的"};
    // 默认状态图片数组
    private int iconNormals[] = {R.drawable.home_tab_red_black, R.drawable.home_tab_life_black, R.drawable.home_tab_frend_black, R.drawable.home_tab_share_black, R.drawable.home_tab_my_black};
    // 激活状态图片数组
    private int iconActivateds[] = {R.drawable.home_tab_red, R.drawable.home_tab_life, R.drawable.home_tab_frend, R.drawable.home_tab_share, R.drawable.home_tab_my};

    private boolean flag = false;

    private String to;

    private boolean showRefreshRedList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        setCanSlidingClose(false);
        initView();
        setEventBus();
        mFriendBusiness = new FriendBusiness(this, this, mHandler);
        mOtherBusiness = new OtherBusiness(this, this, mHandler);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (showRefreshRedList) {
            // 从通知栏调过来刷新数据
            showRefreshRedList = false;
            RedPacketFragment.isfirstloaddata = false;
            if (mPager != null && mRedPacketFragment != null) {
                // 首页加载数据
                mPager.setCurrentItem(0);
                mRedPacketFragment.loadData();
            }
        }
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
        louData();
        mHandler.sendEmptyMessageDelayed(10002, 500);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showRefreshRedList = true;
    }

    private void louData() {
        // 上传本地通讯录
        canUpLoadContents();
        // 更新msg
        MSGModel mMSGModel = NativieDataUtils.getMSGModel();
        mOtherBusiness.getMSGModel(APPCationStation.LOADINGAD, "");
        if (mMSGModel != null && !TextUtils.isEmpty(mMSGModel.getUpdate_addr()) && !Utils.getVersionName().equals(mMSGModel.getUpdate_ver())) {
            TCApplication.isHasNEW = true;
            setTabHintSpotVisibility(4, View.VISIBLE);
        }
        //没有设置钱包密码 查询
        if (!OperationUtils.getBoolean(OperationUtils.walletPassword)) {
            mOtherBusiness.getWalletPasswordType(APPCationStation.WALLETPASSWORD, "");
        }
    }

    /**
     * 检测是否有跳转
     */
    private void goTo() {
        to = getIntent().getStringExtra("to");
        if (!TextUtils.isEmpty(to)) {
            if (to.startsWith("http")) {
                startActivity(new Intent(TCApplication.mContext, PublicWebview.class).putExtra("title", "活动").putExtra("url", to));
            } else {
                Intent mIntent = CastToUtil.getIntent(to);
                if (mIntent != null) {
                    startActivity(mIntent);
                }
            }
        }
    }

    /**
     * 检测是否可以上传通讯录
     */
    private void canUpLoadContents() {
        UpContentModel mUpContentModel = NativieDataUtils.getUpContentModel();
        if (mUpContentModel == null || !NativieDataUtils.getTodyYMD().equals(mUpContentModel.getUpdate())) {
            int mContentType = GeneralDateUtils.getInt(GeneralDateUtils.CONTACTS_SWITCH, false);
            if (mContentType == 2) {
                // 直接上传通讯录
                upLoadContents();
            } else if (mContentType == 0) {
                // 用户还没有设置过是否上传通讯录 提示用户是否上传通讯录
                DialogUtil.showTipsDialog(this, "提示", "同城商城请求读取系统通讯录,\r\n以匹配同城好友", "接受", "拒绝", new DialogUtil.OnConfirmListener() {
                    @Override
                    public void clickConfirm() {
                        GeneralDateUtils.PutInt(GeneralDateUtils.CONTACTS_SWITCH, 2, false);
                        upLoadContents();
                    }

                    @Override
                    public void clickCancel() {
                        GeneralDateUtils.PutInt(GeneralDateUtils.CONTACTS_SWITCH, 1, false);
                    }
                });
            }
        }
    }

    /**
     * 执行读取通讯录
     */
    private void upLoadContents() {
        // 读取通讯录并上传
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

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001://上传通讯录
                mFriendBusiness.uploadContens(APPCationStation.SUMBIT, "", (String) msg.obj);
                break;
            case 10002://跳转
                goTo();
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.SUMBIT://通讯录上传成功
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    UpContentModel mUpContentModel = (UpContentModel) mConnectResult.getObject();
                    mUpContentModel.setUpdate(NativieDataUtils.getTodyYMD());
                    NativieDataUtils.setUpContentModel(mUpContentModel);
                }
                break;
            case APPCationStation.LOADINGAD://MSG更新成功
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    MSGModel mMSGModel = (MSGModel) mConnectResult.getObject();
                    mMSGModel.setUpdate(NativieDataUtils.getTodyYMD());
                    NativieDataUtils.setMSGModel(mMSGModel);
                    if (!mMSGModel.getAgent_id().equals(TCApplication.getmUserInfo().getAgent_id())) {
                        TCApplication.getmUserInfo().setAgent_id(mMSGModel.getAgent_id());
                        OperationUtils.setUserInfo(JSON.toJSONString(TCApplication.getmUserInfo()));
                    }
                    if (!TextUtils.isEmpty(mMSGModel.getUpdate_addr()) && mMSGModel.getUpdate_addr().startsWith("http")) {
                        final String addr = mMSGModel.getUpdate_addr();
                        TCApplication.isHasNEW = true;
                        setTabHintSpotVisibility(4, View.VISIBLE);
                        sendEventBusMessage("MyFragment.Refresh");
                        DialogUtil.showTipsDialog(this, "发现新版本", mMSGModel.getUpdate_tips() + "", "确定更新", "下次再说", new DialogUtil.OnConfirmListener() {
                            @Override
                            public void clickConfirm() {
                                Intent uppdate = new Intent("android.intent.action.VIEW", Uri.parse(addr));
                                uppdate.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(uppdate);
                            }

                            @Override
                            public void clickCancel() {

                            }
                        });
                    }
                }
                break;
            case APPCationStation.WALLETPASSWORD://更新是否设置了钱包密码
                if (mConnectResult != null && mConnectResult.getObject() != null) {
                    if (((BaseModel) mConnectResult.getObject()).getResult() == 81) {
                        OperationUtils.PutBoolean(OperationUtils.walletPassword, false);
                    } else if (((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                        OperationUtils.PutBoolean(OperationUtils.walletPassword, true);
                    }
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
                case "MyFragment.Refresh":
                    if (mMyFragment != null && MyFragment.isfirstloaddata) {
                        MyFragment.isfirstloaddata = false;
                        if (mPager.getCurrentItem() == 4) {
                            mMyFragment.loadData();
                        }
                    }
                    break;
                case "ALL.Refresh":
                    RedPacketFragment.isfirstloaddata = false;
                    LifeFragment.isfirstloaddata = false;
                    FriendFragment.isfirstloaddata = false;
                    ShareFragment.isfirstloaddata = false;
                    MyFragment.isfirstloaddata = false;
                    if (mPager.getCurrentItem() == 4 && mMyFragment != null) {
                        mMyFragment.loadData();
                    }
                    break;
                case "ALL.UpData":
                    louData();
                    break;
                case "MainActivity.Close":
                    finish();
                    break;
                case "HadUpVer":
                    TCApplication.isHasNEW = true;
                    setTabHintSpotVisibility(4, View.VISIBLE);
                    sendEventBusMessage("MyFragment.Refresh");
                    break;
                case "canUpLoadContents":
                    canUpLoadContents();
                    break;
            }
        }
    }
}
