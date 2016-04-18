package app.net.tongcheng.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.apache.http.conn.util.InetAddressUtils;

import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.net.tongcheng.TCApplication;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/6 13:41
 */
public class Utils {

    /**
     * 是否有网络
     *
     * @return boolean
     * @author longluliu
     * @date 2014-8-21 下午5:52:26
     */
    public static boolean isNetWorkAvailable() {
        ConnectivityManager mgr = (ConnectivityManager) TCApplication.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo info = mgr.getActiveNetworkInfo();
            if (info != null && info.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }


    /**
     * 版本名称
     *
     * @return String
     * @author longluliu
     * @date 2015-4-22 下午3:42:58
     */
    private String getVersionName() {
        String versionCode = "";
        try {
            versionCode = TCApplication.mContext.getPackageManager().getPackageInfo(TCApplication.mContext.getPackageName(), 0).versionName;
        } catch (Exception e) {
        }
        return versionCode;
    }

    /**
     * 设置控制输入框小数位数
     *
     * @param edtText
     * @param length
     * @return void
     * @author longluliu
     * @date 2015-7-27 下午9:44:31
     */
    public static void limitDecimalDigits(EditText edtText, final int length) {
        edtText.setFilters(new InputFilter[]{new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                // 删除等特殊字符，直接返回
                if ("".equals(source.toString())) {
                    return null;
                }
                String dValue = dest.toString();
                String[] splitArray = dValue.split("\\.");
                if (splitArray.length > 1) {
                    String dotValue = splitArray[1];
                    int diff = dotValue.length() + 1 - length;
                    if (diff > 0) {
                        return source.subSequence(start, end - diff);
                    }
                }
                return null;
            }
        }});
    }


    /**
     * 判断是否有SD卡
     *
     * @return boolean
     * @Description
     * @date： 2014-10-31 上午9:19:45
     * @author: hehaodong
     */
    public static boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) TCApplication.mContext
                .getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        if (cardId.length() < 15) {
            return false;
        }
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    // 判断是否不存在特殊字符串
    public static boolean isSpecialString(String ss) {
        String str = "^[\u4E00-\u9FA5A-Za-z0-9_]{2,12}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(ss);
        return m.matches();
    }


    /**
     * 获取手机IMEI
     *
     * @return String
     * @Description
     * @date： 2014-12-23 下午4:18:43
     * @author: hehaodong
     */
    public static String getIMEI() {
        try {
            String imei = ((TelephonyManager) TCApplication.mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            if (!TextUtils.isEmpty(imei)) {
                return imei;
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 生产sessionID，也就是UUID
     *
     * @return String
     * @Description
     * @date： 2014-12-23 下午5:07:10
     * @author: hehaodong
     */
    public static String getSessionID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取mac地址
     *
     * @return hehaodong  2014-3-11下午3:48:40
     */
    public static String getMacAddr() {
        WifiManager wifi = (WifiManager) TCApplication.mContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return TextUtils.isEmpty(info.getMacAddress()) ? "null" : info.getMacAddress().replaceAll(":", "-");
    }


    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return int
     * @author longluliu
     * @date 2013-6-3 上午10:35:37
     */
    public static int getScreenPixelsWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return int
     * @author longluliu
     * @date 2013-6-3 上午10:35:37
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取屏幕分辨率
     *
     * @return String
     * @Description
     * @date： 2014-12-24 下午2:17:23
     * @author: hehaodong
     */
    public static String getResolution() {
        return getScreenPixelsWidth(TCApplication.mContext) + "*" + getScreenHeight(TCApplication.mContext);
    }


    /**
     * 获取运营商信息
     *
     * @return String
     * @Description
     * @date： 2014-12-24 上午9:56:43
     * @author: hehaodong
     */
    public static String getSimOperatorInfo() {
        TelephonyManager telephonyManager = (TelephonyManager) TCApplication.mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String operatorString = telephonyManager.getSimOperator();

        if (operatorString == null) {
            return "";
        } else {
            return operatorString;
        }
    }

    /**
     * 获取联网方式
     *
     * @return String
     * @Description
     * @date： 2014-12-24 下午2:48:48
     * @author: hehaodong
     */
    public static String getNetWorkType() {
        ConnectivityManager cm = (ConnectivityManager) TCApplication.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info.getExtraInfo() != null) {
            return info.getExtraInfo();
        } else if (info.getTypeName().toUpperCase().contains("WIFI")) {
            return "WIFI";
        }
        return "未知网络";
    }

    /**
     * 获取操作系统版本
     *
     * @return String
     * @Description
     * @date： 2014-12-24 上午10:33:57
     * @author: hehaodong
     */
    public static String getOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机品牌
     *
     * @return String
     * @Description
     * @date： 2014-12-24 下午2:46:37
     * @author: hehaodong
     */
    public static String getBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机型号
     *
     * @return String
     * @Description
     * @date： 2014-12-24 下午2:47:17
     * @author: hehaodong
     */
    public static String getModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取版本号
     *
     * @return int
     * @author longluliu
     * @date 2015-7-8 下午3:38:09
     */
    public static int getVersionCode() {
        try {
            return TCApplication.mContext.getPackageManager().getPackageInfo(TCApplication.mContext.getPackageName(), 0).versionCode;
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 获取IP地址
     *
     * @return String
     * @Description
     * @date： 2014-12-24 上午9:34:06
     * @author: hehaodong
     */
    public static String getLocalIpAddress() {
        // 得到本机IP地址
        try {
            Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface nif = en.nextElement();
                Enumeration<InetAddress> enumIpAddr = nif.getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress mInetAddress = enumIpAddr.nextElement();
                    if (!mInetAddress.isLoopbackAddress()
                            && InetAddressUtils.isIPv4Address(mInetAddress
                            .getHostAddress())) {
                        return mInetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * 判断一个字符串是否为空
     *
     * @return String
     * @Description
     * @date： 2015-7-18 上午11:00:06
     * @author: xiejianzhong
     */
    public static boolean isEmpty(String str) {
        if ((str == null) || (str.equals("") || (str.trim().length() == 0)) || str.equals("null")) {
            return true;
        }
        return false;
    }


    /**
     * 转换数字格式为货币
     *
     * @param num
     * @return
     */
    public static String num2currency(String num) {
        if (TextUtils.isEmpty(num)) {
            return "0";
        }
        return num2currency(Double.parseDouble(num));
    }

    public static String num2currency(double numd) {
        try {
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            return numberFormat.format(numd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    /**
     * 不四舍五入保留2位小数
     *
     * @param num
     * @return double
     * @author longluliu
     * @date 2015-5-11 下午5:12:13
     */
    public static double numPoint2NoRounded(double num) {
        try {
            DecimalFormat df = new DecimalFormat("#,####.##");// 保留2位
            df.setRoundingMode(RoundingMode.FLOOR);
            return Double.valueOf(df.format(num));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 判断是否需要更新
     *
     * @param currentTimeMillis_now
     * @param currentTimeMillis_old
     * @return
     */
    public static boolean bitUp(long currentTimeMillis_now, long currentTimeMillis_old) {
        if (currentTimeMillis_now - currentTimeMillis_old > -1000 * 60 * 20 && areSameDay(currentTimeMillis_now, currentTimeMillis_old)) {
            return false;//不需要更新
        } else {
            return true;
        }
    }

    /**
     * 判断是否同一天
     *
     * @param currentTimeMillisA
     * @param currentTimeMillisB
     * @return
     */
    public static boolean areSameDay(long currentTimeMillisA, long currentTimeMillisB) {
        Date dateA = new Date(currentTimeMillisA);
        Date dateB = new Date(currentTimeMillisB);
        return areSameDay(dateA, dateB);
    }

    /**
     * 判断是否同一天
     *
     * @param dateA
     * @param dateB
     * @return
     */
    public static boolean areSameDay(Date dateA, Date dateB) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(dateA);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(dateB);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
    }
}
