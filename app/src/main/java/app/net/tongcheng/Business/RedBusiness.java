package app.net.tongcheng.Business;

import android.app.Activity;
import android.os.Handler;


import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BanckListModel;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.CardListModel;
import app.net.tongcheng.model.ExcreteRedModel;
import app.net.tongcheng.model.MoneyInfoModel;
import app.net.tongcheng.model.MoneyOutInputBean;
import app.net.tongcheng.model.MoneyOutListModel;
import app.net.tongcheng.model.RechargeInfoModel;
import app.net.tongcheng.model.RedModel;
import app.net.tongcheng.model.SplideGiftModel;
import app.net.tongcheng.model.TiXianMoreInfoModel;
import app.net.tongcheng.util.HttpBusinessListener;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.HttpUrls;
import app.net.tongcheng.util.MD5;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.RequestParams;
import app.net.tongcheng.util.Utils;
import app.net.tongcheng.util.VerificationCode;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/1 16:39
 */
public class RedBusiness extends BaseBusiness {

    public RedBusiness(HttpBusinessListener mCancelableClear, Activity mActivity, Handler mHandler) {
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
        goConnect(mActivity, mLoding_Type, params, message, RedModel.class.getName());
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
        goConnect(mActivity, mLoding_Type, params, message, ExcreteRedModel.class.getName(), 1500);
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
        goConnect(mActivity, mLoding_Type, params, message, BaseModel.class.getName());
    }

    public void getMoneyInfo(int mLoding_Type, String message) {
        RequestParams params = new RequestParams(HttpUrls.getMoneyInfo);
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("sign", MD5.toMD5(TCApplication.getmUserInfo().getUid() + Common.SIGN_KEY));
        goConnect(mActivity, mLoding_Type, params, message, MoneyInfoModel.class.getName());
    }

    public void getCarList(int mLoding_Type, String message) {
        RequestParams params = new RequestParams(HttpUrls.carlist);
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("sign", MD5.toMD5(TCApplication.getmUserInfo().getUid() + Common.SIGN_KEY));
        goConnect(mActivity, mLoding_Type, params, message, CardListModel.class.getName());
    }

    public void changeCard(int mLoding_Type, String message, String cardNo) {
        RequestParams params = new RequestParams(HttpUrls.changecard);
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("sign", MD5.toMD5(TCApplication.getmUserInfo().getUid() + Common.SIGN_KEY));
        params.addQueryStringParameter("cardid", cardNo);
        goConnect(mActivity, mLoding_Type, params, message, BaseModel.class.getName());
    }

    public void deleteCard(int mLoding_Type, String message, String cardNo) {
        RequestParams params = new RequestParams(HttpUrls.deletecard);
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("sign", MD5.toMD5(TCApplication.getmUserInfo().getUid() + Common.SIGN_KEY));
        params.addQueryStringParameter("cardid", cardNo);
        goConnect(mActivity, mLoding_Type, params, message, BaseModel.class.getName());
    }


    public void bandingCard(int mLoding_Type, String message, String bank_name, String bank_card_no, String card_holder, String branch_name) {
        RequestParams params = new RequestParams(HttpUrls.bandingCard);
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("sign", MD5.toMD5(TCApplication.getmUserInfo().getUid() + Common.SIGN_KEY));
        params.addQueryStringParameter("bank_name", bank_name);
        params.addQueryStringParameter("branch_name", branch_name);
        params.addQueryStringParameter("bank_card_no", bank_card_no);
        params.addQueryStringParameter("card_holder", card_holder);
        goConnect(mActivity, mLoding_Type, params, message, BaseModel.class.getName());
    }

    public void moneyOut(int mLoding_Type, String message, String cardid, double money) {
        RequestParams params = new RequestParams(HttpUrls.moneyout);
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("sign", MD5.toMD5(TCApplication.getmUserInfo().getUid() + Common.SIGN_KEY));
        params.addQueryStringParameter("money", String.valueOf(money));
        params.addQueryStringParameter("cardid", cardid);
        goConnect(mActivity, mLoding_Type, params, message, MoneyOutInputBean.class.getName());
    }


    public void rechargeInfo(int mLoding_Type, String message) {
        RequestParams params = new RequestParams(HttpUrls.rechage);
        params.addQueryStringParameter("ver", Utils.getVersionName());
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        goConnect(mActivity, mLoding_Type, params, message, RechargeInfoModel.class.getName());
    }


    public void moneyOutList(int mLoding_Type, String message) {
        RequestParams params = new RequestParams(HttpUrls.moneyoutList);
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("sign", MD5.toMD5(TCApplication.getmUserInfo().getUid() + Common.SIGN_KEY));
        goConnect(mActivity, mLoding_Type, params, message, MoneyOutListModel.class.getName());
    }


    public void getTiXianInfo(int mLoding_Type, String message, String id) {
        RequestParams params = new RequestParams(HttpUrls.tixianxianqing);
        params.addQueryStringParameter("id", id);
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("sign", MD5.toMD5(TCApplication.getmUserInfo().getUid() + Common.SIGN_KEY));
        goConnect(mActivity, mLoding_Type, params, message, TiXianMoreInfoModel.class.getName());
    }

    public void sendThank(int mLoding_Type, String message, String thanks, String gift_id) {
        RequestParams params = new RequestParams(HttpUrls.thank);
        params.addQueryStringParameter("gift_id", gift_id);
        params.addQueryStringParameter("thankyou", thanks);
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("sign", MD5.toMD5(TCApplication.getmUserInfo().getUid() + Common.SIGN_KEY));
        goConnect(mActivity, mLoding_Type, params, message, BaseModel.class.getName());
    }

    public void getListGiftInfo(int mLoding_Type, String message, String sender_gift_id){
        RequestParams params = new RequestParams(HttpUrls.ListGiftInfo);
        params.addQueryStringParameter("sender_gift_id", sender_gift_id);
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("sign", MD5.toMD5(TCApplication.getmUserInfo().getUid() + Common.SIGN_KEY));
        goConnect(mActivity, mLoding_Type, params, message, SplideGiftModel.class.getName());
    }

    public void getBanckCardList(int mLoding_Type, String message){
        RequestParams params = new RequestParams(HttpUrls.bank_list);
        goConnect(mActivity, mLoding_Type, params, message, BanckListModel.class.getName());
    }

    public void getWeiXinXiaDan(int mLoding_Type, String message, String enTry){
        RequestParams params = new RequestParams(HttpUrls.WXXD);
        params.setBodyContent(enTry);
        goPostConnect(mActivity, mLoding_Type, params, message, "");
    }
}
