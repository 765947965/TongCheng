package app.net.tongcheng.db;

import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;

import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import app.net.tongcheng.TCApplication;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/12/27 11:06
 */

public class EncryptionData {

    // 向量
    private final static String iv = "20161227";

    private static byte[] keyBytes;

    /**
     * 3DES加密
     *
     * @param key
     * @param plainText 待加密文本
     * @return 密文
     */
    public static String encrypt(String key, String plainText) {
        try {
            if (TextUtils.isEmpty(plainText)) {
                return "";
            }
            DESedeKeySpec spec = new DESedeKeySpec(getKeyBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
            Key desKey = keyFactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, desKey, ips);
            byte[] encryptData = cipher.doFinal(plainText.getBytes("utf-8"));
            return "3DES" + Base64.encode(encryptData);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 3DES解密
     *
     * @param key
     * @param encryptText
     * @return 返回解密后的明文
     */
    public static String decrypt(String key, String encryptText) {
        try {
            if (TextUtils.isEmpty(encryptText)) {
                return "";
            }
            if (!encryptText.startsWith("3DES")) {
                return encryptText;
            }
            DESedeKeySpec spec = new DESedeKeySpec(getKeyBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            Key deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
            byte[] decryptData = cipher.doFinal(Base64.decode(encryptText.substring(4)));
            return new String(decryptData, "utf-8");
        } catch (Exception e) {
            return null;
        }
    }

    private static byte[] getKeyBytes() {
        if (keyBytes == null) {
            synchronized (EncryptionData.class) {
                if (keyBytes == null) {
                    try {
                        PackageManager pm = TCApplication.mContext.getPackageManager();
                        Signature sig = pm.getPackageInfo(TCApplication.mContext.getPackageName(), PackageManager.GET_SIGNATURES).signatures[0];
                        String md5Fingerprint = doFingerprint(sig.toByteArray(), "MD5");
                        md5Fingerprint = md5Fingerprint.substring(0, 12) + md5Fingerprint.substring(md5Fingerprint.length() - 12);
                        keyBytes = md5Fingerprint.getBytes();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return keyBytes;
    }

    private static String doFingerprint(byte[] certificateBytes, String algorithm) throws Exception {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(certificateBytes);
        byte[] digest = md.digest();

        String toRet = "";
        for (int i = 0; i < digest.length; i++) {
            if (i != 0) {
                toRet += ":";
            }
            int b = digest[i] & 0xff;
            String hex = Integer.toHexString(b);
            if (hex.length() == 1) {
                toRet += "0";
            }
            toRet += hex;
        }
        return toRet;
    }
}
