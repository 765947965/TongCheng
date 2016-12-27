package app.net.tongcheng;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.bugtags.library.Bugtags;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.analytics.MobclickAgent;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;
import java.util.logging.Level;

import app.net.tongcheng.model.UserInfo;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.Misc;
import app.net.tongcheng.util.OperationUtils;
import app.net.tongcheng.util.RedDateUtils;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/16 10:33
 */
public class TCApplication extends Application {

    public static Context mContext;
    private static UserInfo mUserInfo;
    public static boolean isHasNEW;
    public static String mRegId;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        if (shouldInit()) {
            Bugtags.start("7de15dfb8a2c8fa75a6557fe189ecd8b", this, Bugtags.BTGInvocationEventNone);
            MobclickAgent.setScenarioType(mContext, MobclickAgent.EScenarioType.E_UM_NORMAL);//友盟初始化
            MobclickAgent.enableEncrypt(true);
            MiPushClient.registerPush(mContext, "2882303761517509254", "5491750938254");//小米推送初始化
            String userinfo = OperationUtils.getUserInfo();
            if (!TextUtils.isEmpty(userinfo)) {
                TCApplication.mUserInfo = JSON.parseObject(userinfo, UserInfo.class);
            }
            initOkGo();
            initImageLoader();
        }
    }

    public static UserInfo getmUserInfo() {
        return mUserInfo;
    }

    public static void setmUserInfo(UserInfo mUserInfo) {
        MobclickAgent.onProfileSignOff();//登出友盟账户
        if (mUserInfo != null) {
            MobclickAgent.onProfileSignIn(Misc.cryptDataByPwd(mUserInfo.getPhone() + mUserInfo.getPwd()));//登入友盟账户
        }
        OperationUtils.getSharedPreference().edit().clear().apply();// 清除用户数据
        RedDateUtils.getSharedPreference().edit().clear().apply();// 清除用户红包数据
        OperationUtils.setUserInfo(mUserInfo == null ? "" : JSON.toJSONString(mUserInfo));
        TCApplication.mUserInfo = mUserInfo;
    }

    private void initOkGo() {
        OkGo.init(this);
        OkGo mOkGo = OkGo.getInstance();
        mOkGo.setCertificates();
        if (Common.isDebugModel) mOkGo.debug(Common.tag, Level.INFO, true);
        mOkGo.setConnectTimeout(20000);  //全局的连接超时时间
        mOkGo.setReadTimeOut(20000);    //全局的读取超时时间
        mOkGo.setWriteTimeOut(20000);    //全局的写入超时时间
        mOkGo.setCacheMode(CacheMode.NO_CACHE);
    }

    /**
     * 初始化ImageLoader
     *
     * @param
     * @return void
     * @author longluliu
     * @date 2014-7-22 上午11:35:21
     */
    private void initImageLoader() {
        int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 10);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPoolSize(3)
                // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                // .denyCacheImageMultipleSizesInMemory()
                // .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 *
                // 1024))
                // You can pass your own memory cache
                // implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(memoryCacheSize).discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO).discCacheFileCount(1000) // 缓存的文件数量

                // .discCache(new UnlimitedDiscCache(new
                // File("/qoocc_news/images/cache/")))//自定义缓存路径
                // .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                // .imageDownloader(new BaseImageDownloader(context, 5 * 1000,
                // 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                // .writeDebugLogs() // Remove for release app
                .build();// 开始构建
        ImageLoader.getInstance().init(config);
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
