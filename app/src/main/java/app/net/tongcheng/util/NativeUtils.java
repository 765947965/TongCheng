package app.net.tongcheng.util;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/20 10:05
 */
public class NativeUtils {

    static {
        System.loadLibrary("jniTongCheng");//重要
    }

    public static native String getPARTNER();

    public static native String getSELLER();

    public static native String getRSAPRIVATE();

    public static native String getRSAPUBLIC();

    public static native String getnotifyurl();
}
