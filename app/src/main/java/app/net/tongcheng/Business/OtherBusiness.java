package app.net.tongcheng.Business;

import android.app.Activity;
import android.os.Handler;

import org.xutils.http.RequestParams;

import app.net.tongcheng.model.LodingResultModel;
import app.net.tongcheng.model.RegisterInviteflagModel;
import app.net.tongcheng.util.CancelableClear;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.HttpUrls;
import app.net.tongcheng.util.MD5;

/**
 * Created by 76594 on 2016/5/29.
 */
public class OtherBusiness extends BaseBusiness {

    public OtherBusiness(CancelableClear mCancelableClear, Activity mActivity, Handler mHandler) {
        super(mCancelableClear, mActivity, mHandler);
    }

    public void loadingBusiness(int mLoding_Type, String message, String phone, String pwd) {
        RequestParams params = getRequestParams(HttpUrls.Loding_V2, phone, pwd);
        goConnect(mLoding_Type, params, message, LodingResultModel.class.getName());
    }

    public void registerInviteflagBusiness(int mLoding_Type, String message, String inviteflag) {
        RequestParams params = getRequestParams(HttpUrls.Loding_V2, null, null);
        params.addQueryStringParameter("sign", MD5.toMD5(params.getStringParameter("sn") + inviteflag + Common.SIGN_KEY));
        params.addQueryStringParameter("inviteflag", inviteflag);
        goConnect(mLoding_Type, params, message, RegisterInviteflagModel.class.getName());
    }
}
