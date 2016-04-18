package app.net.tongcheng.util;

import org.xutils.common.Callback;

import app.net.tongcheng.model.ConnectResult;


/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/3/22 10:14
 */
public interface ConnectListener {
    void ConnectOnSuccess(int mLoding_Type, ConnectResult mConnectResult);
    void ConnectOnError(int mLoding_Type, Throwable ex, boolean isOnCallback);
    void ConnectOnCancelled(int mLoding_Type, Callback.CancelledException cex);
    void ConnectOnFinished(int mLoding_Type);
}
