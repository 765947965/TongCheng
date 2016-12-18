package app.net.tongcheng.util;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.xutils.common.Callback;
import org.xutils.x;

import java.io.File;
import java.util.Map;

import app.net.tongcheng.connector.ConnectCallInterface;
import app.net.tongcheng.connector.ConnectInterface;
import app.net.tongcheng.model.ConnectResult;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/12/18 16:51
 */

public class ConnectImper implements ConnectInterface {
    private static boolean isDebugModel = true;
    private static String tag = "TCdebug";

    @Override
    public ConnectCallInterface getNet(final int mLoding_Type, final RequestParams params, String message, final ConnectListener mConnectListener, final String className) {
        final org.xutils.http.RequestParams xutilParams = new org.xutils.http.RequestParams(params.getUrl());
        setRequestParams(params, xutilParams);
        ConnectCallImper mConnectCallImper = new ConnectCallImper();
        mConnectCallImper.setmCancelable(x.http().get(xutilParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (isDebugModel) {
                    Log.i(tag, xutilParams.getUri() + "\r\n" + result);
                }
                ConnectResult cr = new ConnectResult();
                if (mConnectListener != null) {
                    if (!TextUtils.isEmpty(className)) {
                        try {
                            cr.setObject(JSON.parseObject(result, Class.forName(className)));
                        } catch (Exception e) {
                            ToastUtil.showToast("数据解析错误");
                        }
                    } else {
                        cr.setObject(result);
                    }
                    mConnectListener.ConnectOnSuccess(mLoding_Type, cr);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (mConnectListener != null) {
                    mConnectListener.ConnectOnError(mLoding_Type, ex, isOnCallback);
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                if (mConnectListener != null) {
                    mConnectListener.ConnectOnCancelled(mLoding_Type, cex);
                }
            }

            @Override
            public void onFinished() {
                if (mConnectListener != null) {
                    mConnectListener.ConnectOnFinished(mLoding_Type);
                }
            }
        }));
        return mConnectCallImper;
    }

    @Override
    public ConnectCallInterface postNet(final int mLoding_Type, final RequestParams params, String message, final ConnectListener mConnectListener, final String className) {
        final org.xutils.http.RequestParams xutilParams = new org.xutils.http.RequestParams(params.getUrl());
        setRequestParams(params, xutilParams);
        ConnectCallImper mConnectCallImper = new ConnectCallImper();
        mConnectCallImper.setmCancelable(x.http().post(xutilParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (isDebugModel) {
                    Log.i(tag, xutilParams.getUri() + "\r\n" + result);
                }
                ConnectResult cr = new ConnectResult();
                if (mConnectListener != null) {
                    if (!TextUtils.isEmpty(className)) {
                        try {
                            cr.setObject(JSON.parseObject(result, Class.forName(className)));
                        } catch (Exception e) {
                            ToastUtil.showToast("数据解析错误");
                        }
                    } else {
                        cr.setObject(result);
                    }
                    mConnectListener.ConnectOnSuccess(mLoding_Type, cr);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (mConnectListener != null) {
                    mConnectListener.ConnectOnError(mLoding_Type, ex, isOnCallback);
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                if (mConnectListener != null) {
                    mConnectListener.ConnectOnCancelled(mLoding_Type, cex);
                }
            }

            @Override
            public void onFinished() {
                if (mConnectListener != null) {
                    mConnectListener.ConnectOnFinished(mLoding_Type);
                }
            }
        }));
        return mConnectCallImper;
    }


    private void setRequestParams(RequestParams params, org.xutils.http.RequestParams xutilParams) {
        for (Map.Entry<String, String> set : params.getQueryStringParameterMap().entrySet()) {
            xutilParams.addQueryStringParameter(set.getKey(), set.getValue());
        }
        for (Map.Entry<String, String> set : params.getBodyParameterMap().entrySet()) {
            xutilParams.addBodyParameter(set.getKey(), set.getValue());
        }
        if (params.isMultipart()) {
            xutilParams.setMultipart(true);
        }
        for (Map.Entry<String, File> set : params.getFileParameterMap().entrySet()) {
            xutilParams.addBodyParameter(set.getKey(), set.getValue(), null);
        }
        if (!TextUtils.isEmpty(params.getBodyContent())) {
            xutilParams.setBodyContent(params.getBodyContent());
        }
    }
}
