package app.net.tongcheng.db;

import android.text.TextUtils;

import com.yakivmospan.scytale.Crypto;
import com.yakivmospan.scytale.Options;
import com.yakivmospan.scytale.Store;

import javax.crypto.SecretKey;

import app.net.tongcheng.TCApplication;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/12/27 11:06
 */

public class EncryptionData {

    public static String encrypt(String key, String value) {
        if (TextUtils.isEmpty(value)) {
            return "";
        }
        Store store = new Store(TCApplication.mContext);
        if (!store.hasKey(key)) {
            store.generateSymmetricKey(key, null);
        }
        SecretKey mSecretKey = store.getSymmetricKey(key, null);
        Crypto crypto = new Crypto(Options.TRANSFORMATION_SYMMETRIC);
        return "EncryptionData" + crypto.encrypt(value, mSecretKey);
    }

    public static String decrypt(String key, String value) {
        if (TextUtils.isEmpty(value)) {
            return "";
        }
        if (!value.startsWith("EncryptionData")) {
            return value;
        }
        Store store = new Store(TCApplication.mContext);
        if (!store.hasKey(key)) {
            store.generateSymmetricKey(key, null);
        }
        SecretKey mSecretKey = store.getSymmetricKey(key, null);
        Crypto crypto = new Crypto(Options.TRANSFORMATION_SYMMETRIC);
        return crypto.decrypt(value.substring(14), mSecretKey);
    }
}
