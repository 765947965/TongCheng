package app.net.tongcheng.Business;

import android.app.Activity;
import android.os.Handler;

import org.xutils.http.RequestParams;

import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.RegisterCode;
import app.net.tongcheng.model.RegisterInviteflagModel;
import app.net.tongcheng.model.StartPageModel;
import app.net.tongcheng.model.UpContentModel;
import app.net.tongcheng.model.UserInfo;
import app.net.tongcheng.util.CancelableClear;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.HttpUrls;
import app.net.tongcheng.util.MD5;
import app.net.tongcheng.util.Misc;

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
        params.addQueryStringParameter("account", phone);
        params.removeParameter("phone");
        goConnect(mLoding_Type, params, message, UserInfo.class.getName());
    }

    /**
     * 检查邀请码合法性
     *
     * @param mLoding_Type
     * @param message
     * @param inviteflag
     */
    public void registerInviteflagBusiness(int mLoding_Type, String message, String inviteflag) {
        RequestParams params = getRequestParams(HttpUrls.Checkyaoqingma, null, null);
        params.addQueryStringParameter("sign", MD5.toMD5(params.getStringParameter("sn") + inviteflag + Common.SIGN_KEY));
        params.addQueryStringParameter("inviteflag", inviteflag);
        params.removeParameter("pv");
        params.removeParameter("v");
        params.removeParameter("netmode");
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
        params.removeParameter("netmode");
        goConnect(mLoding_Type, params, message, RegisterCode.class.getName());
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
        RequestParams params = getRequestParams(HttpUrls.REG_V2, phone, null);
        params.addQueryStringParameter("invitedby", "");
        params.addQueryStringParameter("channel", "");
        params.addQueryStringParameter("inviteflag", inviteflag);
        params.addQueryStringParameter("authcode", authcode);
        params.removeParameter("netmode");
        goConnect(mLoding_Type, params, message, UserInfo.class.getName());
    }

    /**
     * 修改密码
     *
     * @param mLoding_Type
     * @param message
     * @param newPassword
     */
    public void registerChangePassword(int mLoding_Type, String message, String newPassword) {
        RequestParams params = getRequestParams(HttpUrls.ChangePWD_URL_V2, TCApplication.getmUserInfo().getPhone(), null);
        params.addQueryStringParameter("account", TCApplication.getmUserInfo().getPhone());
        params.removeParameter("phone");
        params.removeParameter("netmode");
        params.removeParameter("brandname");
        params.addQueryStringParameter("old_pwd", TCApplication.getmUserInfo().getPwd());
        params.addQueryStringParameter("new_pwd", Misc.cryptDataByPwd(newPassword.trim()));
        goConnect(mLoding_Type, params, message, BaseModel.class.getName());
    }

    /**
     * 获取启动页数据
     *
     * @param mLoding_Type
     * @param message
     */
    public void getStartPageImage(int mLoding_Type, String message) {
        RequestParams params = getRequestParams(HttpUrls.STARTPAGER_URL, null, null);
        params.removeParameter("sn");
        params.removeParameter("brand");
        params.removeParameter("model");
        params.removeParameter("product");
        params.removeParameter("netmode");
        params.addQueryStringParameter("pix_level", "mdpi");
        params.addQueryStringParameter("ver", "1.0");
        goConnect(mLoding_Type, params, message, StartPageModel.class.getName());
    }


    public void upContentModel(int mLoding_Type, String message){
        RequestParams params = new RequestParams(HttpUrls.COMMITFRIEND);
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        goConnect(mLoding_Type, params, message, UpContentModel.class.getName());
    }

}
