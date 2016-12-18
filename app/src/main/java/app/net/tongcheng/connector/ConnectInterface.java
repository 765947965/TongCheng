package app.net.tongcheng.connector;

import app.net.tongcheng.util.ConnectListener;
import app.net.tongcheng.util.RequestParams;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/12/18 16:39
 */

public interface ConnectInterface {
    ConnectCallInterface postNet(int mLoding_Type, RequestParams params, String message, ConnectListener mConnectListener, String className);
    ConnectCallInterface getNet(int mLoding_Type, RequestParams params, String message, ConnectListener mConnectListener, String className);
}
