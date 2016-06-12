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
public class UserDateUtils {
    private static final String FILENAME = "UserDate";
    public static final String LODINGUSERJSON = "lodinguserjson";
    private static UserDateUtils instance;
    private SharedPreferences mSp;

    private static SharedPreferences getSharedPreference() {
        if (instance == null || instance.mSp == null) {
            synchronized (UserDateUtils.class) {
                if (instance == null || instance.mSp == null) {
                    instance = new UserDateUtils();
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
            editor.putString(key, value);
        } else {
            editor.putString(key, value);
        }
        editor.commit();
    }

    public static String getString(String key) {
        return getString(key, true);
    }

    public static String getString(String key, boolean addUserID) {
        if (addUserID) {
            return getSharedPreference().getString(key, null);
        } else {
            return getSharedPreference().getString(key, null);
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
        editor.commit();
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
        editor.commit();
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
