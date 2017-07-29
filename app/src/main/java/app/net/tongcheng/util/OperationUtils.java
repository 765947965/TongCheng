package app.net.tongcheng.util;

import android.content.Context;
import android.content.SharedPreferences;

import app.net.tongcheng.TCApplication;
import app.net.tongcheng.db.EncryptionData;

/**
 * @author: xiewenliang
 * @Filename: OperationUtils
 * @Description: 账号相关数据
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/2/25 14:01
 */
public class OperationUtils {
    private static final String FILENAME = "OperationData";
    private static final String USERINFO = "nowuserinfo";
    public static final String walletPassword = "walletPassword";
    public static final String hadCertification = "hadCertification";
    public static final String requestPersonInfo = "requestPersonInfo";
    private static OperationUtils instance;
    private SharedPreferences mSp;

    public static SharedPreferences getSharedPreference() {
        if (instance == null || instance.mSp == null) {
            synchronized (OperationUtils.class) {
                if (instance == null || instance.mSp == null) {
                    instance = new OperationUtils();
                    instance.mSp = TCApplication.mContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
                }
            }
        }
        return instance.mSp;
    }

    public static void setUserInfo(String userinfo) {
        getSharedPreference().edit().putString(USERINFO, EncryptionData.encrypt(USERINFO, userinfo)).apply();
    }

    public static String getUserInfo() {
        return EncryptionData.decrypt(USERINFO, getSharedPreference().getString(USERINFO, ""));
    }

    public static void PutString(String key, String value) {
        PutString(key, value, true);
    }

    public static void PutString(String key, String value, boolean addUserID) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        if (addUserID) {
            key = TCApplication.getmUserInfo() == null ? "" : TCApplication.getmUserInfo().getUid() + key;
            editor.putString(key, EncryptionData.encrypt(key, value));
        } else {
            editor.putString(key, EncryptionData.encrypt(key, value));
        }
        editor.apply();
    }

    public static String getString(String key) {
        return getString(key, true);
    }

    public static String getString(String key, boolean addUserID) {
        if (addUserID) {
            key = TCApplication.getmUserInfo() == null ? "" : TCApplication.getmUserInfo().getUid() + key;
            return EncryptionData.decrypt(key, getSharedPreference().getString(key, null));
        } else {
            return EncryptionData.decrypt(key, getSharedPreference().getString(key, null));
        }
    }

    public static void PutInt(String key, int value) {
        PutInt(key, value, true);
    }

    public static void PutInt(String key, int value, boolean addUserID) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        if (addUserID) {
            editor.putInt(TCApplication.getmUserInfo() == null ? "" : TCApplication.getmUserInfo().getUid() + key, value);
        } else {
            editor.putInt(key, value);
        }
        editor.apply();
    }

    public static int getInt(String key) {
        return getInt(key, true);
    }

    public static int getInt(String key, boolean addUserID) {
        if (addUserID) {
            return getSharedPreference().getInt(TCApplication.getmUserInfo() == null ? "" : TCApplication.getmUserInfo().getUid() + key, 0);
        } else {
            return getSharedPreference().getInt(key, 0);
        }
    }

    public static void PutBoolean(String key, boolean value) {
        PutBoolean(key, value, true);
    }

    public static void PutBoolean(String key, boolean value, boolean addUserID) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        if (addUserID) {
            editor.putBoolean(TCApplication.getmUserInfo() == null ? "" : TCApplication.getmUserInfo().getUid() + key, value);
        } else {
            editor.putBoolean(key, value);
        }
        editor.apply();
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, true);
    }

    public static boolean getBoolean(String key, boolean addUserID) {
        if (addUserID) {
            return getSharedPreference().getBoolean(TCApplication.getmUserInfo() == null ? "" : TCApplication.getmUserInfo().getUid() + key, false);
        } else {
            return getSharedPreference().getBoolean(key, false);
        }
    }

}
