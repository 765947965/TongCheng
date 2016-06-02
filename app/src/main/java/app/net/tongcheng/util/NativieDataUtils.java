package app.net.tongcheng.util;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.text.SimpleDateFormat;
import java.util.Date;

import app.net.tongcheng.model.RedModel;
import app.net.tongcheng.model.StartPageModel;

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

    public static String getTodyY() {
        return new SimpleDateFormat("yyyy").format(new Date());
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

    public static RedModel getRedModel() {
        String str = OperationUtils.getString("red_model");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return JSON.parseObject(str, RedModel.class);
    }

    public static void setRedModel(RedModel mRedModel) {
        String str = JSON.toJSONString(mRedModel);
        OperationUtils.PutString("red_model", str);
    }
}