package app.net.tongcheng.Business;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.text.TextUtils;
import android.util.SparseArray;

import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.HttpBusinessListener;
import app.net.tongcheng.util.Check_network;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.ConnectListener;
import app.net.tongcheng.util.ConnectUtil;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.ErrorInfoUtil;
import app.net.tongcheng.util.MD5;
import app.net.tongcheng.util.Misc;
import app.net.tongcheng.util.RequestParams;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.Utils;
import app.net.tongcheng.util.VerificationCode;
import okhttp3.Response;


/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/3/22 10:03
 */
public class BaseBusiness implements ConnectListener {
    public HttpBusinessListener mHttpBusinessListener;
    public Activity mActivity;
    public Handler mHandler;
    private static SparseArray<Dialog> mMessagesDialog = new SparseArray<>();

    public BaseBusiness(HttpBusinessListener mHttpBusinessListener, Activity mActivity, Handler mHandler) {
        this.mHttpBusinessListener = mHttpBusinessListener;
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

    public void goConnect(Activity mActivity, int mLoding_Type, RequestParams params, String message, String className) {
        goConnect(mActivity, mLoding_Type, params, message, className, 0);
    }

    public void goConnect(final Activity mActivity, final int mLoding_Type, final RequestParams params, final String message, final String className, long delaytime) {
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
        if (delaytime > 0) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ConnectUtil.Connect(mActivity, mLoding_Type, params, message, BaseBusiness.this, className);
                }
            }, delaytime);
        } else {
            ConnectUtil.Connect(mActivity, mLoding_Type, params, message, this, className);
        }
    }

    public void goPostConnect(Activity mActivity, int mLoding_Type, RequestParams params, String message, String className) {
        goPostConnect(mActivity, mLoding_Type, params, message, className, 0);
    }

    public void goPostConnect(final Activity mActivity, final int mLoding_Type, final RequestParams params, final String message, final String className, long delaytime) {
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
        if (delaytime > 0) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ConnectUtil.PostConnect(mActivity, mLoding_Type, params, message, BaseBusiness.this, className);
                }
            }, delaytime);
        } else {
            ConnectUtil.PostConnect(mActivity, mLoding_Type, params, message, this, className);
        }
    }

    @Override
    public void ConnectOnSuccess(int mLoadType, ConnectResult mConnectResult) {
        Dialog mMessageOld = mMessagesDialog.get(mLoadType);
        if (mMessageOld != null && mMessageOld.isShowing()) {
            mMessageOld.dismiss();
        }
        if (mConnectResult.getObject() == null) {
            return;
        }
        if (mConnectResult.getObject() instanceof BaseModel) {
            BaseModel mBaseModel = (BaseModel) mConnectResult.getObject();
            if (mBaseModel.getResult() != 0 && mBaseModel.getResult() != 60 && mBaseModel.getResult() != 63) {
                String strMessage = TextUtils.isEmpty(mBaseModel.getMessage()) ? ErrorInfoUtil.getErrorMessage(mBaseModel.getResult()) : mBaseModel.getMessage();
                if (!TextUtils.isEmpty(strMessage)) {
                    ToastUtil.showToast(strMessage);
                }
            }
        }
        mHttpBusinessListener.BusinessOnSuccess(mLoadType, mConnectResult);
    }

    @Override
    public void ConnectOnError(int mLoadType, Response response) {
        Dialog mMessageOld = mMessagesDialog.get(mLoadType);
        if (mMessageOld != null && mMessageOld.isShowing()) {
            mMessageOld.dismiss();
        }
        if (response != null && response.code() == 403) {
            ToastUtil.showToast("防火墙安全保障生效中，您可操作慢点。");
        }
        mHttpBusinessListener.BusinessOnFail(mLoadType, response);
    }
}
