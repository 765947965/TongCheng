package app.net.tongcheng;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.xutils.x;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/16 10:33
 */
public class TCApplication extends Application {

    public static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志
        initImageLoader();
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
}
