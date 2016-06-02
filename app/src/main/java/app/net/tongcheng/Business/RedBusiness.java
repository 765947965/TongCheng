package app.net.tongcheng.Business;

import android.app.Activity;
import android.os.Handler;

import org.xutils.http.RequestParams;

import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.ExcreteRedModel;
import app.net.tongcheng.model.RedModel;
import app.net.tongcheng.util.CancelableClear;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.HttpUrls;
import app.net.tongcheng.util.MD5;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/1 16:39
 */
public class RedBusiness extends BaseBusiness {

    public RedBusiness(CancelableClear mCancelableClear, Activity mActivity, Handler mHandler) {
        super(mCancelableClear, mActivity, mHandler);
    }

    /**
     * 获取红包数据
     *
     * @param mLoding_Type
     * @param message
     * @param year
     * @param direct
     */
    public void getRedList(int mLoding_Type, String message, String year, String direct) {
        RequestParams params = getRequestParams(HttpUrls.RED_GETDATA_URL, TCApplication.getmUserInfo().getPhone(), null);
        params.removeParameter("sn");
        params.removeParameter("netmode");
        params.removeParameter("brandname");
        params.removeParameter("sign");
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("year", year);
        params.addQueryStringParameter("direct", direct);
        params.addQueryStringParameter("sign", MD5.toMD5(TCApplication.getmUserInfo().getUid() + Common.SIGN_KEY));
        goConnect(mLoding_Type, params, message, RedModel.class.getName());
    }

    public void excreteRed(int mLoding_Type, String message, String gift_id, String action) {
        RequestParams params = getRequestParams(HttpUrls.RED_Checkout_URL, TCApplication.getmUserInfo().getPhone(), null);
        params.removeParameter("sn");
        params.removeParameter("netmode");
        params.removeParameter("brandname");
        params.removeParameter("sign");
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("sign", MD5.toMD5(TCApplication.getmUserInfo().getUid() + Common.SIGN_KEY));
        params.addQueryStringParameter("gift_id", gift_id);
        params.addQueryStringParameter("action", action);
        goConnect(mLoding_Type, params, message, ExcreteRedModel.class.getName(), 1500);
    }


}
