package app.net.tongcheng.util;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;
import com.lzy.okgo.request.PostRequest;
import com.weixin.paydemo.*;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import app.net.tongcheng.connector.ConnectCallInterface;
import app.net.tongcheng.connector.ConnectInterface;
import app.net.tongcheng.model.ConnectResult;
import okhttp3.Call;
import okhttp3.Response;

import static app.net.tongcheng.util.Common.isDebugModel;
import static app.net.tongcheng.util.Common.tag;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/12/18 16:51
 */

public class ConnectImper implements ConnectInterface {

    @Override
    public ConnectCallInterface getNet(final Activity mActivity, final int mLoding_Type, final RequestParams params, String message, final ConnectListener mConnectListener, final String className) {
        BaseRequest mBaseRequest = OkGo.get(params.getUrl());
        checkEncrytionParams(params);
        setRequestParams(params, mBaseRequest);
        mBaseRequest.tag(mActivity);
        mBaseRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                if (isDebugModel) {
                    Log.i(tag, params.getUrl() + "\r\n" + s);
                }
                ConnectResult cr = new ConnectResult();
                if (mConnectListener != null) {
                    if (!TextUtils.isEmpty(className)) {
                        try {
                            cr.setObject(JSON.parseObject(s, Class.forName(className)));
                        } catch (Exception e) {
                            ToastUtil.showToast("数据解析错误");
                        }
                    } else {
                        cr.setObject(s);
                    }
                    try {
                        mConnectListener.ConnectOnSuccess(mLoding_Type, cr);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                if (mConnectListener != null) {
                    try {
                        mConnectListener.ConnectOnError(mLoding_Type);
                    } catch (Exception e2) {
                    }
                }
                if (response != null && response.code() == 403) {
                    ToastUtil.showToast("防火墙安全保障生效中，您可操作慢点。");
                }
            }
        });
        return null;
    }

    @Override
    public ConnectCallInterface postNet(final Activity mActivity, final int mLoding_Type, final RequestParams params, String message, final ConnectListener mConnectListener, final String className) {
        BaseRequest mBaseRequest = OkGo.post(params.getUrl());
        checkEncrytionParams(params);
        setRequestParams(params, mBaseRequest);
        mBaseRequest.tag(mActivity);
        mBaseRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                if (isDebugModel) {
                    Log.i(tag, params.getUrl() + "\r\n" + s);
                }
                ConnectResult cr = new ConnectResult();
                if (mConnectListener != null) {
                    if (!TextUtils.isEmpty(className)) {
                        try {
                            cr.setObject(JSON.parseObject(s, Class.forName(className)));
                        } catch (Exception e) {
                            ToastUtil.showToast("数据解析错误");
                        }
                    } else {
                        cr.setObject(s);
                    }
                    try {
                        mConnectListener.ConnectOnSuccess(mLoding_Type, cr);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                if (mConnectListener != null) {
                    try {
                        mConnectListener.ConnectOnError(mLoding_Type);
                    } catch (Exception e2) {
                    }
                }
                if (response != null && response.code() == 403) {
                    ToastUtil.showToast("防火墙安全保障生效中，您可操作慢点。");
                }
            }
        });
        return null;
    }

    private void checkEncrytionParams(RequestParams params) {
        if (params.getUrl().startsWith("https://mobile.zjtongchengshop.com")) {
            encryptionRequestParams(params.getQueryStringParameterMap());
            encryptionRequestParams(params.getBodyParameterMap());
        }
    }


    private void setRequestParams(RequestParams params, BaseRequest mBaseRequest) {

        for (Map.Entry<String, String> set : params.getQueryStringParameterMap().entrySet()) {
            mBaseRequest.params(set.getKey(), set.getValue());
        }
        for (Map.Entry<String, String> set : params.getBodyParameterMap().entrySet()) {
            mBaseRequest.params(set.getKey(), set.getValue());
        }
        if (params.isMultipart() && (mBaseRequest instanceof PostRequest)) {
            ((PostRequest) mBaseRequest).isMultipart(true);
        }
        for (Map.Entry<String, File> set : params.getFileParameterMap().entrySet()) {
            if (mBaseRequest instanceof PostRequest) {
                ((PostRequest) mBaseRequest).params(set.getKey(), set.getValue());
            }
        }
        if (!TextUtils.isEmpty(params.getBodyContent())) {
            if (mBaseRequest instanceof PostRequest) {
                ((PostRequest) mBaseRequest).upString(params.getBodyContent());
            }
        }
    }

    private void encryptionRequestParams(Map<String, String> mParameterMap) {
        if (mParameterMap != null && mParameterMap.size() > 0) {
            mParameterMap.put("securityver", "1");
            mParameterMap.put("nonce_str", UUID.randomUUID().toString().replace("-", "").substring(0, 14));
            mParameterMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
            List<String> mExpressList = new ArrayList<>();
            for (Map.Entry<String, String> param : mParameterMap.entrySet()) {
                mExpressList.add(param.getKey() + "=" + param.getValue());
            }
            Collections.sort(mExpressList, new SingComparator());
            StringBuilder mExpressBuilder = new StringBuilder();
            for (String value : mExpressList) {
                mExpressBuilder.append(value);
                mExpressBuilder.append("&");
            }
            String mExpressOld = mExpressBuilder.toString();
            mParameterMap.put("sign_new", MD5Util.MD5Encode(mExpressOld + NativeUtils.getMerchantSecretKey(), "UTF-8"));
        }
    }

}
