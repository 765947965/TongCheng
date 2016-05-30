package app.net.tongcheng.util;

import android.content.Context;
import android.content.SharedPreferences;

import app.net.tongcheng.TCApplication;

/**
 * @author: xiewenliang
 * @Filename: OperationUtils
 * @Description: 运营配置数据存储
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/2/25 14:01
 */
public class OperationUtils {
    private static final String FILENAME = "OperationData";
    private static final String USERID = "nowuserid";
    private static final String USERINFO = "nowuserinfo";
    public static final String LODINGUSERJSON = "lodinguserjson";
    private static OperationUtils instance;
    private SharedPreferences mSp;
    private static String userid;

    private static SharedPreferences getSharedPreference() {
        if (instance == null || instance.mSp == null) {
            synchronized (OperationUtils.class) {
                if (instance == null || instance.mSp == null) {
                    instance = new OperationUtils();
                    instance.mSp = TCApplication.mContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
                    userid = instance.mSp.getString(USERID, "");
                }
            }
        }
        return instance.mSp;
    }

    /**
     * 设置userid
     *
     * @param userid
     */
    public static void setUserID(String userid) {
        OperationUtils.userid = userid;
        getSharedPreference().edit().putString(USERID, userid).commit();
    }

    public static void setUserInfo(String userinfo){
        getSharedPreference().edit().putString(USERINFO, userinfo).commit();
    }

    public static String getUserInfo(){
        return getSharedPreference().getString(USERINFO, "");
    }

    public static void PutString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putString(userid + key, value);
        editor.commit();
    }

    public static String getString(String key) {
        return getSharedPreference().getString(userid + key, null);
    }

    public static void PutInt(String key, int value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putInt(userid + key, value);
        editor.commit();
    }

    public static int getInt(String key) {
        return getSharedPreference().getInt(userid + key, 0);
    }

    public static void PutBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putBoolean(userid + key, value);
        editor.commit();
    }

    public static boolean getBoolean(String key) {
        return getSharedPreference().getBoolean(userid + key, false);
    }

}
