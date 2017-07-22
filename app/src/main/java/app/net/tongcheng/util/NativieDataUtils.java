package app.net.tongcheng.util;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.text.SimpleDateFormat;
import java.util.Date;

import app.net.tongcheng.model.ADListModel;
import app.net.tongcheng.model.BanckListModel;
import app.net.tongcheng.model.CardListModel;
import app.net.tongcheng.model.FriendModel;
import app.net.tongcheng.model.LifeDataModel;
import app.net.tongcheng.model.MSGModel;
import app.net.tongcheng.model.MoneyInfoModel;
import app.net.tongcheng.model.MoneyOutListModel;
import app.net.tongcheng.model.RechargeInfoModel;
import app.net.tongcheng.model.RedModel;
import app.net.tongcheng.model.ShareCodeModel;
import app.net.tongcheng.model.ShareTipsModel;
import app.net.tongcheng.model.StartPageModel;
import app.net.tongcheng.model.UpContentModel;
import app.net.tongcheng.model.UserMoreInfoModel;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/1 13:54
 */
public class NativieDataUtils {

    public static String getTodyYMDHM() {
        return new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
    }

    public static String getTodyYMD() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public static String getTodyY_M_D() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String getTodyY_M() {
        return new SimpleDateFormat("yyyy-MM").format(new Date());
    }

    public static String getTodyY() {
        return new SimpleDateFormat("yyyy").format(new Date());
    }

    public static String getTodyYMDHMS() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }


    public static StartPageModel getStartPageModel(boolean ischeck) {
        String str = OperationUtils.getString("start_page", false);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        StartPageModel mStartPageModel = JSON.parseObject(str, StartPageModel.class);
        if (!ischeck) {
            return mStartPageModel;
        }
        if (!getTodyYMD().equals(mStartPageModel.getTodayShowTimesupdate())) {
            mStartPageModel.setTodayShowTimesupdate(getTodyYMD());
            mStartPageModel.setTodayShowTimes(0);
            setStartPageModel(mStartPageModel);
        }
        if (mStartPageModel.getTodayShowTimes() > mStartPageModel.getShow_times_daily()) {
            return null;
        }
        long nowTime = Long.parseLong(getTodyYMDHM());
        long startTime = Long.parseLong(mStartPageModel.getStart_time());
        long endTime = Long.parseLong(mStartPageModel.getEnd_time());
        if (nowTime < startTime || nowTime > endTime) {
            return null;
        }
        return mStartPageModel;
    }

    public static void setStartPageModel(StartPageModel mStartPageModel) {
        OperationUtils.PutString("start_page", JSON.toJSONString(mStartPageModel), false);
    }

    public static RedModel getRedModel(String year, String direct) {
        String str = RedDateUtils.getString(year + direct + "red_model");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return JSON.parseObject(str, RedModel.class);
    }

    public static void setRedModel(RedModel mRedModel, String year, String direct) {
        String str = JSON.toJSONString(mRedModel);
        RedDateUtils.PutString(year + direct + "red_model", str);
    }

    public static LifeDataModel getLifeDataModel() {
        String str = OperationUtils.getString("life_data_model");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return JSON.parseObject(str, LifeDataModel.class);
    }

    public static void setLifeDataModel(LifeDataModel mLifeDataModel) {
        String str = JSON.toJSONString(mLifeDataModel);
        OperationUtils.PutString("life_data_model", str);
    }

    public static ADListModel getADListDataModel() {
        String str = OperationUtils.getString("life_ad_list_model");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return JSON.parseObject(str, ADListModel.class);
    }

    public static void setADListDataModel(ADListModel mADListModel) {
        String str = JSON.toJSONString(mADListModel);
        OperationUtils.PutString("life_ad_list_model", str);
    }

    public static UpContentModel getUpContentModel() {
        String str = OperationUtils.getString("UpContentModel_model");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return JSON.parseObject(str, UpContentModel.class);
    }

    public static void setUpContentModel(UpContentModel mUpContentModel) {
        String str = JSON.toJSONString(mUpContentModel);
        OperationUtils.PutString("UpContentModel_model", str);
    }

    public static FriendModel getFriendModel() {
        String str = OperationUtils.getString("mFriendModel");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return JSON.parseObject(str, FriendModel.class);
    }

    public static void setFriendModel(FriendModel mFriendModel) {
        String str = JSON.toJSONString(mFriendModel);
        OperationUtils.PutString("mFriendModel", str);
    }

    public static MoneyInfoModel getMoneyInfoModel() {
        String str = OperationUtils.getString("MoneyInfoModel");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return JSON.parseObject(str, MoneyInfoModel.class);
    }

    public static void setMoneyInfoModel(MoneyInfoModel mMoneyInfoModel) {
        String str = JSON.toJSONString(mMoneyInfoModel);
        OperationUtils.PutString("MoneyInfoModel", str);
    }

    public static CardListModel getCardListModel() {
        String str = OperationUtils.getString("CardListModel");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return JSON.parseObject(str, CardListModel.class);
    }

    public static void setCardListModel(CardListModel mCardListModel) {
        String str = JSON.toJSONString(mCardListModel);
        OperationUtils.PutString("CardListModel", str);
    }

    public static RechargeInfoModel getRechargeInfoModel() {
        String str = OperationUtils.getString("RechargeInfoModel");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return JSON.parseObject(str, RechargeInfoModel.class);
    }

    public static void setRechargeInfoModel(RechargeInfoModel mRechargeInfoModel) {
        String str = JSON.toJSONString(mRechargeInfoModel);
        OperationUtils.PutString("RechargeInfoModel", str);
    }

    public static UserMoreInfoModel getUserMoreInfoModel() {
        String str = OperationUtils.getString("UserMoreInfoModel");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return JSON.parseObject(str, UserMoreInfoModel.class);
    }

    public static void setUserMoreInfoModel(UserMoreInfoModel mUserMoreInfoModel) {
        String str = JSON.toJSONString(mUserMoreInfoModel);
        OperationUtils.PutString("UserMoreInfoModel", str);
    }

    public static MoneyOutListModel getMoneyOutListModel() {
        String str = OperationUtils.getString("MoneyOutListModel");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return JSON.parseObject(str, MoneyOutListModel.class);
    }

    public static void setMoneyOutListModel(MoneyOutListModel mMoneyOutListModel) {
        String str = JSON.toJSONString(mMoneyOutListModel);
        OperationUtils.PutString("MoneyOutListModel", str);
    }

    public static ShareTipsModel getShareTipsModel() {
        String str = OperationUtils.getString("ShareTipsModel");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return JSON.parseObject(str, ShareTipsModel.class);
    }

    public static void setShareTipsModel(ShareTipsModel mShareTipsModel) {
        String str = JSON.toJSONString(mShareTipsModel);
        OperationUtils.PutString("ShareTipsModel", str);
    }


    public static ShareCodeModel getShareCodeModel() {
        String str = OperationUtils.getString("ShareCodeModel");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return JSON.parseObject(str, ShareCodeModel.class);
    }

    public static void setShareCodeModel(ShareCodeModel mShareCodeModel) {
        String str = JSON.toJSONString(mShareCodeModel);
        OperationUtils.PutString("ShareCodeModel", str);
    }

    public static MSGModel getMSGModel() {
        String str = OperationUtils.getString("MSGModel");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return JSON.parseObject(str, MSGModel.class);
    }

    public static void setMSGModel(MSGModel mMSGModel) {
        String str = JSON.toJSONString(mMSGModel);
        OperationUtils.PutString("MSGModel", str);
    }

    public static BanckListModel getBanckListModel() {
        String str = OperationUtils.getString("BanckListModel");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return JSON.parseObject(str, BanckListModel.class);
    }

    public static void setBanckListModel(BanckListModel mBanckListModel) {
        String str = JSON.toJSONString(mBanckListModel);
        OperationUtils.PutString("BanckListModel", str);
    }
}
