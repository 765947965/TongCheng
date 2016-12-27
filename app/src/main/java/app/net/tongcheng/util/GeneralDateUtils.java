package app.net.tongcheng.util;

import android.content.Context;
import android.content.SharedPreferences;

import app.net.tongcheng.TCApplication;
import app.net.tongcheng.db.EncryptionData;

/**
 * @author: xiewenliang
 * @Filename: OperationUtils
 * @Description: 登录用户账号数据 是否开启通讯录记录
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/2/25 14:01
 */
public class GeneralDateUtils {
    private static final String FILENAME = "UserDate";
    public static final String LODINGUSERJSON = "lodinguserjson";
    public static final String CONTACTS_SWITCH = "ContactsSwitch";// 0 没有设置 1 关闭 2 开启
    private static GeneralDateUtils instance;
    private SharedPreferences mSp;

    private static SharedPreferences getSharedPreference() {
        if (instance == null || instance.mSp == null) {
            synchronized (GeneralDateUtils.class) {
                if (instance == null || instance.mSp == null) {
                    instance = new GeneralDateUtils();
                    instance.mSp = TCApplication.mContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
                }
            }
        }
        return instance.mSp;
    }


    public static void PutString(String key, String value) {
        PutString(key, value, true);
    }

    public static void PutString(String key, String value, boolean addUserID) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        if (addUserID) {
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
            editor.putInt(key, value);
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
            return getSharedPreference().getInt(key, 0);
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
            editor.putBoolean(key, value);
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
            return getSharedPreference().getBoolean(key, false);
        } else {
            return getSharedPreference().getBoolean(key, false);
        }
    }

}
