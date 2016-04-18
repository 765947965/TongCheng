package app.net.tongcheng.Business;

import android.content.Context;

import org.xutils.http.RequestParams;

import app.net.tongcheng.util.CancelableClear;
import app.net.tongcheng.util.ConnectListener;
import app.net.tongcheng.util.ConnectUtil;


/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/3/22 10:03
 */
public class BaseBusiness {
    public CancelableClear mCancelableClear;
    public Context mContext;
    public ConnectListener mConnectListener;

    public BaseBusiness(CancelableClear mCancelableClear, Context mContext, ConnectListener mConnectListener) {
        this.mCancelableClear = mCancelableClear;
        this.mContext = mContext;
        this.mConnectListener = mConnectListener;
    }

    public void goConnect(int mLoding_Type, RequestParams params, String message, String className) {
        mCancelableClear.addCancelable(ConnectUtil.Connect(mLoding_Type, params, message, mConnectListener, className));
    }

}
