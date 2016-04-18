package app.net.tongcheng.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


/**
 * 图片加载
 *
 * @author longluliu
 * @ClassName: PictureLoader
 * @Description: TODO
 * @date 2014-7-22 上午10:32:59
 */
public class PictureLoader {

    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;

    public PictureLoader(int defaultImg) {
        this(defaultImg, ImageScaleType.EXACTLY_STRETCHED);
    }

    public PictureLoader(int defaultImg, ImageScaleType imageScaleType) {
        options = new DisplayImageOptions.Builder()
                .showStubImage(defaultImg) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(defaultImg)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(defaultImg) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(imageScaleType)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                .build();// 构建完成
    }

    /**
     * 加载图片
     *
     * @param @param url
     * @param @param imgView
     * @return void
     * @author longluliu
     * @date 2014-7-22 上午10:33:06
     */
    public void displayImage(String url, ImageView imgView) {
        imageLoader.displayImage(url, imgView, options);
    }

    /**
     * 加载图片
     *
     * @param url
     * @param listener
     */
    public void loadImage(String url, ImageLoadingListener listener) {
        imageLoader.loadImage(url, options, listener);
    }

    public void clearMemoryCache() {
        imageLoader.clearMemoryCache();
        imageLoader.clearDiscCache();
    }
}
