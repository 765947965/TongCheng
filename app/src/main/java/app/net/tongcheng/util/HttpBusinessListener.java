package app.net.tongcheng.util;


import app.net.tongcheng.model.ConnectResult;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/16 11:33
 */
public interface HttpBusinessListener {
    void BusinessOnSuccess(int mHttpLoadType, ConnectResult mConnectResult);
    void BusinessOnFail(int mHttpLoadType);
}
