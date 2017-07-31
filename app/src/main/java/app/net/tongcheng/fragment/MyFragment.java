package app.net.tongcheng.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import org.json.JSONObject;

import app.net.tongcheng.Business.MyBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.activity.AboutAPP;
import app.net.tongcheng.activity.AccountSetActivity;
import app.net.tongcheng.activity.CertificationRecord;
import app.net.tongcheng.activity.FeedbackActivity;
import app.net.tongcheng.activity.InviterActivity;
import app.net.tongcheng.activity.MyUserInfoActivity;
import app.net.tongcheng.activity.PersonalitySetActivity;
import app.net.tongcheng.activity.ShowImageActivity;
import app.net.tongcheng.activity.WalletAccountSetActivity;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.UserMoreInfoModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.GeneralDateUtils;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.OperationUtils;
import app.net.tongcheng.util.ViewHolder;
import okhttp3.Response;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/16 15:55
 */
public class MyFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ViewHolder mViewHolder;
    private ToggleButton btnContent;
    private MyBusiness mMyBusiness;
    private UserMoreInfoModel mUserMoreInfoModel;
    public static boolean isfirstloaddata;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        className = this.getClass().getSimpleName();
        View view = inflater.inflate(R.layout.fragment_my_layout, null);
        initView(view);
        mMyBusiness = new MyBusiness(this, getActivity(), mHandler);
        isfirstloaddata = false;
        return view;
    }

    private void initView(View view) {
        mViewHolder = new ViewHolder(view, this);
        mViewHolder.setText(R.id.tv_title, "我");
        mViewHolder.setVisibility(R.id.bt_close, View.GONE);
        btnContent = mViewHolder.getView(R.id.btnContent);
        btnContent.setOnCheckedChangeListener(this);
        mViewHolder.setOnClickListener(R.id.rlt_user_info);
        mViewHolder.setOnClickListener(R.id.llt_abouttc);
        mViewHolder.setOnClickListener(R.id.llt_fk);
        mViewHolder.setOnClickListener(R.id.llt_zh);
        mViewHolder.setOnClickListener(R.id.iv_head_image);
        mViewHolder.setOnClickListener(R.id.llt_wallet);
        mViewHolder.setOnClickListener(R.id.llt_gx);
        mViewHolder.setOnClickListener(R.id.llt_tjr);
        mViewHolder.setOnClickListener(R.id.llt_id_card);
    }

    @Override
    public void loadData() {
        //读取数据
        if (isfirstloaddata) {
            return;
        }
        isfirstloaddata = true;
        if (TCApplication.isHasNEW) {
            mViewHolder.setVisibility(R.id.iv_new, View.VISIBLE);
        } else {
            mViewHolder.setVisibility(R.id.iv_new, View.GONE);
        }
        int type = GeneralDateUtils.getInt(GeneralDateUtils.CONTACTS_SWITCH, false);
        if (type == 2) {
            btnContent.setChecked(true);
        }
        if (!OperationUtils.getBoolean(OperationUtils.hadCertification, true)) {
            mViewHolder.setText(R.id.tv_id_card, "账号实名认证(未通过实名认证)");
            mHandler.sendEmptyMessageDelayed(10002, 100);
        } else {
            mViewHolder.setText(R.id.tv_id_card, "账号实名认证(已通过实名认证)");
        }
        mHandler.sendEmptyMessageDelayed(10001, 100);
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                mUserMoreInfoModel = NativieDataUtils.getUserMoreInfoModel();
                if (mUserMoreInfoModel == null || !NativieDataUtils.getTodyYMD().equals(mUserMoreInfoModel.getUpdate())) {
                    mMyBusiness.getuserInfo(APPCationStation.LOADING, "");
                }
                if (mUserMoreInfoModel == null) {
                    mUserMoreInfoModel = new UserMoreInfoModel();
                }
                setData(mUserMoreInfoModel);
                break;
            case 10002:
                mMyBusiness.queryCertificationStatus(APPCationStation.CHECK, "");
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {
        switch (mLoadType) {
            case APPCationStation.LOADING:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    UserMoreInfoModel mUserMoreInfoModel = (UserMoreInfoModel) mConnectResult.getObject();
                    mUserMoreInfoModel.setUpdate(NativieDataUtils.getTodyYMD());
                    NativieDataUtils.setUserMoreInfoModel(mUserMoreInfoModel);
                    mHandler.sendEmptyMessage(10001);
                }
                break;
            case APPCationStation.CHECK:
                try {
                    String jsonStr = (String) mConnectResult.getObject();
                    JSONObject json = new JSONObject(jsonStr);
                    if (json.getInt("result") == 41) {
                        OperationUtils.PutBoolean(OperationUtils.hadCertification, true, true);
                        mViewHolder.setText(R.id.tv_id_card, "账号实名认证(已通过实名认证)");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoadType, Response response) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlt_user_info://我的信心
                startActivity(new Intent(TCApplication.mContext, MyUserInfoActivity.class));
                break;
            case R.id.llt_abouttc:
                startActivity(new Intent(TCApplication.mContext, AboutAPP.class));
                break;
            case R.id.llt_fk:
                startActivity(new Intent(TCApplication.mContext, FeedbackActivity.class));
                break;
            case R.id.llt_zh:
                startActivity(new Intent(TCApplication.mContext, AccountSetActivity.class));
                break;
            case R.id.iv_head_image:
                if (mUserMoreInfoModel != null && !TextUtils.isEmpty(mUserMoreInfoModel.getPicture()) && !TextUtils.isEmpty(mUserMoreInfoModel.getPicurl_prefix())) {
                    startActivity(new Intent(TCApplication.mContext, ShowImageActivity.class).putExtra("url", mUserMoreInfoModel.getPicurl_prefix() + mUserMoreInfoModel.getPicture()));
                }
                break;
            case R.id.llt_wallet://钱包密码设置
                startActivity(new Intent(TCApplication.mContext, WalletAccountSetActivity.class));
                break;
            case R.id.llt_gx://设置开启通讯录读取开关
//                startActivity(new Intent(TCApplication.mContext, PersonalitySetActivity.class));
                break;
            case R.id.llt_tjr://查看我的邀请人信息
                startActivity(new Intent(TCApplication.mContext, InviterActivity.class));
                break;
            case R.id.llt_id_card:
                startActivity(new Intent(TCApplication.mContext, CertificationRecord.class));
                break;
        }
    }

    private void setData(UserMoreInfoModel mUserMoreInfoModel) {
        if (!TextUtils.isEmpty(mUserMoreInfoModel.getPicture()) && !TextUtils.isEmpty(mUserMoreInfoModel.getPicurl_prefix())) {
            mViewHolder.setImage(R.id.iv_head_image, mUserMoreInfoModel.getPicurl_prefix() + mUserMoreInfoModel.getPicture(), 0, 360);
        } else {
            mViewHolder.setImage(R.id.iv_head_image, R.drawable.content5);
        }
        if (!TextUtils.isEmpty(mUserMoreInfoModel.getName())) {
            mViewHolder.setText(R.id.iv_name, mUserMoreInfoModel.getName());
        } else {
            mViewHolder.setText(R.id.iv_name, "用户");
        }
        mViewHolder.setText(R.id.iv_phone, TCApplication.getmUserInfo().getPhone());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.btnContent:
                if (isChecked) {
                    GeneralDateUtils.PutInt(GeneralDateUtils.CONTACTS_SWITCH, 2, false);
                    sendEventBusMessage("canUpLoadContents");//通知上传通讯录
                } else {
                    GeneralDateUtils.PutInt(GeneralDateUtils.CONTACTS_SWITCH, 1, false);
                }
                break;
        }
    }
}
