package app.net.tongcheng.util;


import app.net.tongcheng.model.ConnectResult;
import okhttp3.Response;


/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/3/22 10:14
 */
public interface ConnectListener {
    void ConnectOnSuccess(int mLoadType, ConnectResult mConnectResult);
    void ConnectOnError(int mLoadType, Response response);
}
