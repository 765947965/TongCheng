package app.net.tongcheng.util;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.text.SimpleDateFormat;
import java.util.Date;

import app.net.tongcheng.model.ADListModel;
import app.net.tongcheng.model.CardListModel;
import app.net.tongcheng.model.FriendModel;
import app.net.tongcheng.model.LifeDataModel;
import app.net.tongcheng.model.MoneyInfoModel;
import app.net.tongcheng.model.RechargeInfoModel;
import app.net.tongcheng.model.RedModel;
import app.net.tongcheng.model.StartPageModel;
import app.net.tongcheng.model.UpContentModel;

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
}
