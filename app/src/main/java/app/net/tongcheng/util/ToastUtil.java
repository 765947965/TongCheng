package app.net.tongcheng.util;

import android.view.Gravity;

import com.github.johnpersano.supertoasts.SuperToast;

import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;

/**
 * 提示工具类
 *
 * @author longluliu
 *         Create at: 2015-1-5 下午2:19:05
 * @Filename: ToastUtil.java
 * @Description: TODO
 */
public class ToastUtil {
    private static SuperToast mToast;
    private static SuperToast mMidToast;
    private static String mLastContent;

    /**
     * 显示toast
     *
     * @return void
     * @author longluliu
     * @date 2015-1-5 下午2:19:11
     */
    public static void showToast(String content) {
        try {
            if (mToast != null) {
                if (mLastContent.equals(content)) {
                    mToast.cancelAllSuperToasts();
                }
                mToast.setText(content);
            } else {
                mToast = new SuperToast(TCApplication.mContext);
                mToast.setDuration(SuperToast.Duration.SHORT);
                mToast.setTextSize(13);
                mToast.setText("" + content);
            }
            if (!content.contains("<html>")) {
                mToast.show();
                mLastContent = content;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToast(int content) {
        String msg = TCApplication.mContext.getString(content).toString();
        showToast(msg);
    }

    /**
     * 显示信息
     *
     * @return void
     * @author longluliu
     * @date 2015-1-5 下午2:19:16
     */
    public static void showResultToast(int info) {
        String msg = TCApplication.mContext.getString(info).toString();
        showResultToast(msg);
    }

    public static void showResultToast(String info) {
        showResultToast(info, R.mipmap.ic_success);
    }

    /**
     * 显示信息
     *
     * @return void
     * @author longluliu
     * @date 2015-1-5 下午2:19:16
     */
    public static void showResultToast(int info, int iconId) {
        String msg = TCApplication.mContext.getString(info).toString();
        showResultToast(msg, iconId);
    }

    /**
     * 显示信息
     *
     * @return void
     * @author longluliu
     * @date 2015-1-5 下午2:19:16
     */
    public static void showResultToast(String info, int iconId) {
        try {
            mMidToast = new SuperToast(TCApplication.mContext);
            mMidToast.setDuration(SuperToast.Duration.SHORT);
            mMidToast.setBackground(R.drawable.progress_hud_bg);
            mMidToast.setTextSize(14);
            mMidToast.setText("" + info);
            mMidToast.setGravity(Gravity.CENTER, 0, 0);
            mMidToast.setIcon(iconId, SuperToast.IconPosition.TOP);
            mMidToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
