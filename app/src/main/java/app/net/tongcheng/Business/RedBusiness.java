package app.net.tongcheng.Business;

import android.app.Activity;
import android.os.Handler;

import org.xutils.http.RequestParams;

import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ExcreteRedModel;
import app.net.tongcheng.model.MoneyInfoModel;
import app.net.tongcheng.model.RedModel;
import app.net.tongcheng.util.CancelableClear;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.HttpUrls;
import app.net.tongcheng.util.MD5;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.VerificationCode;

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


    public void sendRed(int mLoding_Type, String message, String name, String money, String moneytype, String gift_tips, String uids, String nums) {
        RequestParams params = new RequestParams(HttpUrls.add_gift_record_v2);
        params.addQueryStringParameter("uid", uids);
        params.addQueryStringParameter("receiver_gifts_number", nums);
        params.addQueryStringParameter("from", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("gift_id", NativieDataUtils.getTodyYMDHMS() + TCApplication.getmUserInfo().getUid() + VerificationCode.getCode2());
        params.addQueryStringParameter("gift_type", "p2p_money");
        params.addQueryStringParameter("gift_subtype", "personnocommand");
        params.addQueryStringParameter("sign", MD5.toMD5(TCApplication.getmUserInfo().getUid() + Common.SIGN_KEY));
        params.addQueryStringParameter("money", money);
        params.addQueryStringParameter("money_type", moneytype);
        params.addQueryStringParameter("gift_name", moneytype.equals("0") ? "拼手气红包" : "普通红包");
        params.addQueryStringParameter("gift_tips", gift_tips);
        params.addQueryStringParameter("fromnickname", name);
        goConnect(mLoding_Type, params, message, BaseModel.class.getName());
    }

    public void getMoneyInfo(int mLoding_Type, String message){
        RequestParams params = new RequestParams(HttpUrls.getMoneyInfo);
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("sign", MD5.toMD5(TCApplication.getmUserInfo().getUid() + Common.SIGN_KEY));
        goConnect(mLoding_Type, params, message, MoneyInfoModel.class.getName());
    }
}
