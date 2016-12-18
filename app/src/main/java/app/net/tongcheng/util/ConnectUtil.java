package app.net.tongcheng.util;


import app.net.tongcheng.connector.ConnectCallInterface;
import app.net.tongcheng.connector.ConnectInterface;


/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/3/22 10:13
 */
public class ConnectUtil {
    private static ConnectInterface mConnectInterface;
    private static ConnectInterface getConnectInterface(){
        if (mConnectInterface == null){
            synchronized (ConnectUtil.class){
                if (mConnectInterface == null){
                    mConnectInterface = new ConnectImper();
                }
            }
        }
        return mConnectInterface;
    }

    public static ConnectCallInterface Connect(int mLoding_Type, RequestParams params, String message, ConnectListener mConnectListener, String className) {
        return getConnectInterface().getNet(mLoding_Type, params, message, mConnectListener, className);
    }

    public static ConnectCallInterface PostConnect(final int mLoding_Type, final RequestParams params, String message, final ConnectListener mConnectListener, final String className) {
        return getConnectInterface().postNet(mLoding_Type, params, message, mConnectListener, className);
    }
}
