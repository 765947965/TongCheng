package app.net.tongcheng.Business;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;


import java.io.File;

import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.UserMoreInfoModel;
import app.net.tongcheng.util.HttpBusinessListener;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.HttpUrls;
import app.net.tongcheng.util.MD5;
import app.net.tongcheng.util.RequestParams;
import app.net.tongcheng.util.Utils;
import app.net.tongcheng.util.VerificationCode;

/**
 * Created by 76594 on 2016/6/11.
 */
public class MyBusiness extends BaseBusiness {


    public MyBusiness(HttpBusinessListener mCancelableClear, Activity mActivity, Handler mHandler) {
        super(mCancelableClear, mActivity, mHandler);
    }

    public void getuserInfo(int mLoding_Type, String message) {
        RequestParams params = new RequestParams(HttpUrls.getUserInfo);
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("ver", "1.0");
        goConnect(mActivity, mLoding_Type, params, message, UserMoreInfoModel.class.getName());
    }

    public void upImage(int mLoding_Type, String message, File file) {
        RequestParams params = new RequestParams(HttpUrls.UPUserHeadURL);
        params.addBodyParameter("uid", TCApplication.getmUserInfo().getUid());
        params.setMultipart(true);
        params.addFileParameter("msg", file);
        goPostConnect(mActivity, mLoding_Type, params, message, UserMoreInfoModel.class.getName());
    }

    public void upOtherUserInfo(int mLoding_Type, String message, String ver, String data) {
        RequestParams params = new RequestParams(HttpUrls.UPUSERDATAURL);
        params.addBodyParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addBodyParameter("ver", TextUtils.isEmpty(ver) ? "1.0" : ver);
        params.addBodyParameter("userprofile", data);
        goPostConnect(mActivity, mLoding_Type, params, message, UserMoreInfoModel.class.getName());
    }

    public void sendProB(int mLoding_Type, String message, String title, String advice) {
        RequestParams params = new RequestParams(HttpUrls.ADVICE);
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("phone", TCApplication.getmUserInfo().getPhone());
        params.addQueryStringParameter("sign", MD5.toMD5(TCApplication.getmUserInfo().getUid() + Common.SIGN_KEY));
        params.addQueryStringParameter("brandname", Common.BrandName);
        params.addQueryStringParameter("agent_id", TCApplication.getmUserInfo().getAgent_id());
        params.addQueryStringParameter("pv", "android");
        params.addQueryStringParameter("v", Utils.getVersionName());
        params.addQueryStringParameter("title", title);
        params.addQueryStringParameter("advice", advice);
        goConnect(mActivity, mLoding_Type, params, message, BaseModel.class.getName());
    }


    public void queryCertificationStatus(int mLoding_Type, String message) {
        RequestParams params = new RequestParams(HttpUrls.queryCertificationStatus);
        String sn = VerificationCode.getCode2();
        params.addQueryStringParameter("sn", sn);
        params.addQueryStringParameter("sign", MD5.toMD5(sn + TCApplication.getmUserInfo().getUid() + Common.SIGN_KEY));
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        goConnect(mActivity, mLoding_Type, params, message, "");
    }

    public void uploadImage(int mLoding_Type, String message, File file) {
        RequestParams params = new RequestParams(HttpUrls.uploadImage);
        params.addBodyParameter("uid", TCApplication.getmUserInfo().getUid());
        params.setMultipart(true);
        params.addFileParameter("msg", file);
        goPostConnect(mActivity, mLoding_Type, params, message, UserMoreInfoModel.class.getName());
    }
}
