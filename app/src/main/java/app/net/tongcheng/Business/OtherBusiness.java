package app.net.tongcheng.Business;

import android.app.Activity;
import android.os.Handler;

import org.xutils.http.RequestParams;

import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ChangeAccoutModel;
import app.net.tongcheng.model.GetPassordModel;
import app.net.tongcheng.model.MSGModel;
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
import app.net.tongcheng.util.Utils;
import app.net.tongcheng.util.VerificationCode;

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
        params.addQueryStringParameter("uuid", TCApplication.mRegId + phone);
        params.removeParameter("phone");
        goConnect(mLoding_Type, params, message, UserInfo.class.getName());
    }

    /**
     * 登出
     *
     * @param mLoding_Type
     * @param message
     */
    public void loadingOutBusiness(int mLoding_Type, String message) {
        RequestParams params = new RequestParams(HttpUrls.LodingOut_V2);
        String sn = VerificationCode.getCode2();
        params.addQueryStringParameter("sn", sn);
        params.addQueryStringParameter("account", TCApplication.getmUserInfo().getPhone());
        params.addQueryStringParameter("pv", "android");
        params.addQueryStringParameter("sign", MD5.toMD5(sn + TCApplication.getmUserInfo().getPhone() + Common.SIGN_KEY));
        goConnect(mLoding_Type, params, message, BaseModel.class.getName());
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
     * 修改密码
     *
     * @param mLoding_Type
     * @param message
     * @param newPassword
     */
    public void registerChangePassword(int mLoding_Type, String message, String phone, String oldPassword, String newPassword) {
        RequestParams params = getRequestParams(HttpUrls.ChangePWD_URL_V2, phone, null);
        params.addQueryStringParameter("account", phone);
        params.removeParameter("phone");
        params.removeParameter("netmode");
        params.removeParameter("brandname");
        params.addQueryStringParameter("old_pwd", oldPassword);
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


    public void getPassword(int mLoding_Type, String message, String phone) {
        RequestParams params = new RequestParams(HttpUrls.PWDPS);
        String sn = VerificationCode.getCode2();
        params.addQueryStringParameter("sn", sn);
        params.addQueryStringParameter("agent_id", "1.0");
        params.addQueryStringParameter("account", phone);
        params.addQueryStringParameter("pv", "android");
        params.addQueryStringParameter("v", Utils.getVersionName());
        params.addQueryStringParameter("sign", MD5.toMD5(sn + phone + Common.SIGN_KEY));
        params.addQueryStringParameter("brand", Utils.getBrand());
        params.addQueryStringParameter("model", Utils.getModel());
        params.addQueryStringParameter("product", Common.BrandName);
        params.addQueryStringParameter("brandname", Common.BrandName);
        goConnect(mLoding_Type, params, message, RegisterCode.class.getName());
    }


    public void getChagnePasswordColde(int mLoding_Type, String message, String phone, String authcode) {
        RequestParams params = new RequestParams(HttpUrls.CPGHD);
        String sn = VerificationCode.getCode2();
        params.addQueryStringParameter("sn", sn);
        params.addQueryStringParameter("agent_id", "1.0");
        params.addQueryStringParameter("account", phone);
        params.addQueryStringParameter("pv", "android");
        params.addQueryStringParameter("v", Utils.getVersionName());
        params.addQueryStringParameter("sign", MD5.toMD5(sn + phone + Common.SIGN_KEY));
        params.addQueryStringParameter("brand", Utils.getBrand());
        params.addQueryStringParameter("model", Utils.getModel());
        params.addQueryStringParameter("product", Common.BrandName);
        params.addQueryStringParameter("authcode", authcode);
        goConnect(mLoding_Type, params, message, GetPassordModel.class.getName());
    }


    public void getMSGModel(int mLoding_Type, String message) {
        RequestParams params = new RequestParams(HttpUrls.msglist);
        params.addQueryStringParameter("account", TCApplication.getmUserInfo().getPhone());
        params.addQueryStringParameter("pv", "android");
        params.addQueryStringParameter("v", Utils.getVersionName());
        params.addQueryStringParameter("sc", "10240");
        params.addQueryStringParameter("agent_id", TCApplication.getmUserInfo().getAgent_id());
        params.addQueryStringParameter("product", Common.BrandName);
        params.addQueryStringParameter("brandname", Common.BrandName);
        params.addQueryStringParameter("uuid", TCApplication.mRegId + TCApplication.getmUserInfo().getPhone());
        goConnect(mLoding_Type, params, message, MSGModel.class.getName());
    }

    public void getChangeAccoutCode(int mLoding_Type, String message, String code) {
        RequestParams params = new RequestParams(HttpUrls.URL + "send_changephone_authcode");
        params.addQueryStringParameter("phone", TCApplication.getmUserInfo().getPhone());
        params.addQueryStringParameter("message", "您好，本次操作的验证码为：" + code);
        params.addQueryStringParameter("sign", MD5.toMD5(TCApplication.getmUserInfo().getPhone() + Common.SIGN_KEY));
        params.addQueryStringParameter("brandname", Common.BrandName);
        goConnect(mLoding_Type, params, message, BaseModel.class.getName());
    }

    public void changeAccout(int mLoding_Type, String message, String old_phone, String new_phone, String pwd) {
        RequestParams params = new RequestParams(HttpUrls.ChangPhone_V2);
        String sn = VerificationCode.getCode2();
        params.addQueryStringParameter("sn", sn);
        params.addQueryStringParameter("agent_id", TCApplication.getmUserInfo().getAgent_id());
        params.addQueryStringParameter("account", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("pv", "android");
        params.addQueryStringParameter("v", Utils.getVersionName());
        params.addQueryStringParameter("sign", MD5.toMD5(sn + TCApplication.getmUserInfo().getUid() + Common.SIGN_KEY));
        params.addQueryStringParameter("brand", Utils.getBrand());
        params.addQueryStringParameter("model", Utils.getModel());
        params.addQueryStringParameter("product", Common.BrandName);
        params.addQueryStringParameter("old_phone", old_phone);
        params.addQueryStringParameter("new_phone", new_phone);
        params.addQueryStringParameter("pwd", Misc.cryptDataByPwd(pwd));
        goConnect(mLoding_Type, params, message, ChangeAccoutModel.class.getName());
    }
}
