package app.net.tongcheng.Business;

import android.app.Activity;
import android.os.Handler;


import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.ShareCodeModel;
import app.net.tongcheng.model.ShareTipsModel;
import app.net.tongcheng.util.CancelableClear;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.HttpUrls;
import app.net.tongcheng.util.MD5;
import app.net.tongcheng.util.RequestParams;
import app.net.tongcheng.util.Utils;
import app.net.tongcheng.util.VerificationCode;

/**
 * Created by 76594 on 2016/6/13.
 */
public class ShareBusiness extends BaseBusiness {

    public ShareBusiness(CancelableClear mCancelableClear, Activity mActivity, Handler mHandler) {
        super(mCancelableClear, mActivity, mHandler);
    }

    public void getShareTips(int mLoding_Type, String message) {
        RequestParams params = new RequestParams(HttpUrls.ShareTips);
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("sign", MD5.toMD5(TCApplication.getmUserInfo().getUid() + Common.SIGN_KEY));
        goConnect(mLoding_Type, params, message, ShareTipsModel.class.getName());
    }

    public void getShareCode(int mLoding_Type, String message) {
        RequestParams params = new RequestParams(HttpUrls.getShareCode);
        String sn = VerificationCode.getCode2();
        params.addQueryStringParameter("sn", sn);
        params.addQueryStringParameter("agent_id", TCApplication.getmUserInfo().getAgent_id());
        params.addQueryStringParameter("account", TCApplication.getmUserInfo().getPhone());
        params.addQueryStringParameter("sign", MD5.toMD5(sn + TCApplication.getmUserInfo().getPhone() + Common.SIGN_KEY));
        params.addQueryStringParameter("brand", Utils.getBrand());
        params.addQueryStringParameter("model", Utils.getModel());
        params.addQueryStringParameter("product", Common.BrandName);
        params.addQueryStringParameter("brandname", Common.BrandName);
        goConnect(mLoding_Type, params, message, ShareCodeModel.class.getName());
    }

}
