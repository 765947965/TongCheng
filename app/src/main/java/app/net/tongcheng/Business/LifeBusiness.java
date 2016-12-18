package app.net.tongcheng.Business;

import android.app.Activity;
import android.os.Handler;


import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.ADListModel;
import app.net.tongcheng.model.LifeDataModel;
import app.net.tongcheng.util.CancelableClear;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.HttpUrls;
import app.net.tongcheng.util.MD5;
import app.net.tongcheng.util.RequestParams;

/**
 * Created by 76594 on 2016/6/2.
 */
public class LifeBusiness extends BaseBusiness {

    public LifeBusiness(CancelableClear mCancelableClear, Activity mActivity, Handler mHandler) {
        super(mCancelableClear, mActivity, mHandler);
    }

    public void getLifeData(int mLoding_Type, String message) {
        RequestParams params = getRequestParams(HttpUrls.servicePage_URL, null, null);
        params.removeParameter("sn");
        params.removeParameter("brand");
        params.removeParameter("model");
        params.removeParameter("product");
        params.removeParameter("agent_id");
        params.removeParameter("netmode");
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("ver", "1.0");
        params.addQueryStringParameter("sign", MD5.toMD5(TCApplication.getmUserInfo().getUid() + Common.SIGN_KEY));
        goConnect(mActivity, mLoding_Type, params, message, LifeDataModel.class.getName());
    }

    public void getADData(int mLoding_Type, String message) {
        RequestParams params = getRequestParams(HttpUrls.URL + "config/getadlist", null, null);
        params.removeParameter("sn");
        params.removeParameter("product");
        params.removeParameter("netmode");
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("pictype", "4");
        params.addQueryStringParameter("sign", MD5.toMD5(TCApplication.getmUserInfo().getUid() + Common.SIGN_KEY));
        goConnect(mActivity, mLoding_Type, params, message, ADListModel.class.getName());
    }
}
