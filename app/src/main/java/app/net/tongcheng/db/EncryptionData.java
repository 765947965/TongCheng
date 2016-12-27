package app.net.tongcheng.db;

import android.text.TextUtils;

import com.yakivmospan.scytale.Crypto;
import com.yakivmospan.scytale.Options;
import com.yakivmospan.scytale.Store;

import java.security.MessageDigest;

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

    private static MessageDigest md5 = null;

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
        }
    }

    private static String getMd5(String str) {
        byte[] bs = md5.digest(str.getBytes());
        StringBuilder sb = new StringBuilder(40);
        for (byte x : bs) {
            if ((x & 0xff) >> 4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString().toUpperCase();
    }

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
        return getMd5(key) + crypto.encrypt(value, mSecretKey);
    }

    public static String decrypt(String key, String value) {
        if (TextUtils.isEmpty(value)) {
            return "";
        }
        if (!value.startsWith(getMd5(key))) {
            return value;
        }
        Store store = new Store(TCApplication.mContext);
        if (!store.hasKey(key)) {
            store.generateSymmetricKey(key, null);
        }
        SecretKey mSecretKey = store.getSymmetricKey(key, null);
        Crypto crypto = new Crypto(Options.TRANSFORMATION_SYMMETRIC);
        return crypto.decrypt(value.substring(32), mSecretKey);
    }
}
