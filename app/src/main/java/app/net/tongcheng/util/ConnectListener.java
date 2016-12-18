package app.net.tongcheng.util;


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
    void ConnectOnError(int mLoding_Type);
}
