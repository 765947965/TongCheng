package app.net.tongcheng.Business;

import android.app.Activity;
import android.os.Handler;

import org.xutils.http.RequestParams;

import app.net.tongcheng.model.LodingResultModel;
import app.net.tongcheng.util.CancelableClear;
import app.net.tongcheng.util.HttpUrls;

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

}
