package app.net.tongcheng.Business;

import android.app.Activity;
import android.os.Handler;

import org.xutils.http.RequestParams;

import app.net.tongcheng.model.BaseModel;
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

    /**
     * 登录
     *
     * @param mLoding_Type
     * @param message
     * @param phone
     * @param pwd
     */
    public void loadingBusiness(int mLoding_Type, String message, String phone, String pwd) {
        RequestParams params = getRequestParams(HttpUrls.Loding_V2, phone, pwd);
        goConnect(mLoding_Type, params, message, LodingResultModel.class.getName());
    }

    /**
     * 检查邀请码合法性
     *
     * @param mLoding_Type
     * @param message
     * @param inviteflag
     */
    public void registerInviteflagBusiness(int mLoding_Type, String message, String inviteflag) {
        RequestParams params = getRequestParams(HttpUrls.Loding_V2, null, null);
        params.addQueryStringParameter("sign", MD5.toMD5(params.getStringParameter("sn") + inviteflag + Common.SIGN_KEY));
        params.addQueryStringParameter("inviteflag", inviteflag);
        goConnect(mLoding_Type, params, message, RegisterInviteflagModel.class.getName());
    }

    /**
     * 获取注册验证码
     *
     * @param mLoding_Type
     * @param message
     * @param phone
     */
    public void getRegisterAouthCode(int mLoding_Type, String message, String phone) {
        RequestParams params = getRequestParams(HttpUrls.REG_GETCODE, phone, null);
        goConnect(mLoding_Type, params, message, BaseModel.class.getName());
    }

    /**
     * 注册
     *
     * @param mLoding_Type
     * @param message
     * @param phone
     * @param inviteflag
     * @param authcode
     */
    public void registerBusiness(int mLoding_Type, String message, String phone, String inviteflag, String authcode) {
        RequestParams params = getRequestParams(HttpUrls.Loding_V2, phone, null);
        params.addQueryStringParameter("inviteflag", inviteflag);
        params.addQueryStringParameter("authcode", authcode);
        goConnect(mLoding_Type, params, message, RegisterInviteflagModel.class.getName());
    }
}
