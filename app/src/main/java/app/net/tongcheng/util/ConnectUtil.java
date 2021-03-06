package app.net.tongcheng.util;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import app.net.tongcheng.model.ConnectResult;


/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/3/22 10:13
 */
public class ConnectUtil {

    public static Callback.Cancelable Connect(final int mLoding_Type, RequestParams params, String message, final ConnectListener mConnectListener, final String className) {
        return x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ConnectResult cr = new ConnectResult();
                if (mConnectListener != null) {
                    if (!TextUtils.isEmpty(className)) {
                        try {
                            cr.setObject(JSON.parseObject(result, Class.forName(className)));
                        } catch (Exception e) {
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
        });
    }
}
