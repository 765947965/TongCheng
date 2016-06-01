package app.net.tongcheng.Business;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.CancelableClear;
import app.net.tongcheng.util.Check_network;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.ConnectListener;
import app.net.tongcheng.util.ConnectUtil;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.ErrorInfoUtil;
import app.net.tongcheng.util.MD5;
import app.net.tongcheng.util.Misc;
import app.net.tongcheng.util.Utils;
import app.net.tongcheng.util.VerificationCode;


/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/3/22 10:03
 */
public class BaseBusiness implements ConnectListener {
    public CancelableClear mCancelableClear;
    public Activity mActivity;
    public Handler mHandler;
    private static SparseArray<Dialog> mMessagesDialog = new SparseArray<>();

    public BaseBusiness(CancelableClear mCancelableClear, Activity mActivity, Handler mHandler) {
        this.mCancelableClear = mCancelableClear;
        this.mActivity = mActivity;
        this.mHandler = mHandler;
    }

    public RequestParams getRequestParams(String url) {
        return getRequestParams(url, TCApplication.getmUserInfo() == null ? null : TCApplication.getmUserInfo().getPhone(), TCApplication.getmUserInfo() == null ? null : Misc.cryptDataByPwd(TCApplication.getmUserInfo().getPwd()));
    }

    public RequestParams getRequestParams(String url, String phone, String pwd) {
        RequestParams params = new RequestParams(url);
        String sn = VerificationCode.getCode2();
        int netmode = Check_network.getNetworkClass(TCApplication.mContext);
        if (netmode == 4) {
            netmode = 3;
        }
        params.addQueryStringParameter("sn", sn);
        params.addQueryStringParameter("agent_id", TCApplication.getmUserInfo() == null ? "1" : TCApplication.getmUserInfo().getAgent_id());
        if (!TextUtils.isEmpty(phone)) {
            params.addQueryStringParameter("phone", phone);
        }
        if (!TextUtils.isEmpty(pwd)) {
            params.addQueryStringParameter("pwd", Misc.cryptDataByPwd(pwd.trim()));
        }
        params.addQueryStringParameter("pv", "android");
        params.addQueryStringParameter("v", Utils.getVersionName());
        if (!TextUtils.isEmpty(phone)) {
            params.addQueryStringParameter("sign", MD5.toMD5(sn + phone + Common.SIGN_KEY));
        }
        params.addQueryStringParameter("brand", Utils.getBrand());
        params.addQueryStringParameter("model", Utils.getModel());
        params.addQueryStringParameter("product", Common.BrandName);
        params.addQueryStringParameter("netmode", netmode + "");
        params.addQueryStringParameter("brandname", Common.BrandName);
        return params;
    }

    public void goConnect(int mLoding_Type, RequestParams params, String message, String className) {
        if (!TextUtils.isEmpty(message)) {
            Dialog mMessage = DialogUtil.loadingDialog(mActivity, message);
            if (mMessage != null) {
                Dialog mMessageold = mMessagesDialog.get(mLoding_Type);
                if (mMessageold != null && mMessageold.isShowing()) {
                    mMessageold.dismiss();
                }
                mMessagesDialog.put(mLoding_Type, mMessage);
            }
        }
        mCancelableClear.addCancelable(ConnectUtil.Connect(mLoding_Type, params, message, this, className));
    }

    @Override
    public void ConnectOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        Dialog mMessageold = mMessagesDialog.get(mLoding_Type);
        if (mMessageold != null && mMessageold.isShowing()) {
            mMessageold.dismiss();
        }
        if (mConnectResult.getObject() == null) {
            return;
        }
        if (mConnectResult.getObject() instanceof BaseModel) {
            BaseModel mBaseModel = (BaseModel) mConnectResult.getObject();
            if (mBaseModel.getResult() != 0 && mBaseModel.getResult() != 62 && mBaseModel.getResult() != 64 && mBaseModel.getResult() != 70) {
                DialogUtil.showTipsDialog(mActivity, ErrorInfoUtil.getErrorMessage(mBaseModel.getResult()), null);
                mConnectResult.setObject(null);
            }
        }
        Bundle mBundle = new Bundle();
        mBundle.putInt("mLoding_Type", mLoding_Type);
        mBundle.putSerializable("ConnectResult", mConnectResult);
        Message mMessage = new Message();
        mMessage.what = APPCationStation.SUCCESS;
        mMessage.setData(mBundle);
        mHandler.sendMessage(mMessage);
    }

    @Override
    public void ConnectOnError(int mLoding_Type, Throwable ex, boolean isOnCallback) {
        Dialog mMessageold = mMessagesDialog.get(mLoding_Type);
        if (mMessageold != null && mMessageold.isShowing()) {
            mMessageold.dismiss();
        }
        Bundle mBundle = new Bundle();
        mBundle.putInt("mLoding_Type", mLoding_Type);
        Message mMessage = new Message();
        mMessage.what = APPCationStation.FAIL;
        mMessage.setData(mBundle);
        mHandler.sendMessage(mMessage);
    }

    @Override
    public void ConnectOnCancelled(int mLoding_Type, Callback.CancelledException cex) {
        Dialog mMessageold = mMessagesDialog.get(mLoding_Type);
        if (mMessageold != null && mMessageold.isShowing()) {
            mMessageold.dismiss();
        }
        Bundle mBundle = new Bundle();
        mBundle.putInt("mLoding_Type", mLoding_Type);
        Message mMessage = new Message();
        mMessage.what = APPCationStation.FAIL;
        mMessage.setData(mBundle);
        mHandler.sendMessage(mMessage);
    }

    @Override
    public void ConnectOnFinished(int mLoding_Type) {

    }
}
