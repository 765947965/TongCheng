package app.net.tongcheng.db;

import android.content.Context;
import android.content.SharedPreferences;

import app.net.tongcheng.TCApplication;

/**
 * @author: xiewenliang
 * @Filename: DataUtil
 * @Description: 数据存储
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/6 11:42
 */
public class DataUtil {

    private static final String FILENAME = "BaseData";
    private static DataUtil instance;
    private SharedPreferences mSp;

    private static SharedPreferences getSharedPreference() {
        if (instance == null || instance.mSp == null) {
            synchronized (DataUtil.class) {
                if (instance == null || instance.mSp == null) {
                    instance = new DataUtil();
                    instance.mSp = TCApplication.mContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
                }
            }
        }
        return instance.mSp;
    }

    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(String key) {
        return getSharedPreference().getString(key, null);
    }

    public static void putInt(String key, int value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(String key) {
        return getSharedPreference().getInt(key, 0);
    }

    public static void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(String key) {
        return getSharedPreference().getBoolean(key, false);
    }
}
