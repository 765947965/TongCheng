package app.net.tongcheng.util;

import android.content.pm.PackageManager;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

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
    private static String mLastContent;
    private static Toast mOldToast;

    /**
     * 显示toast
     *
     * @return void
     * @author longluliu
     * @date 2015-1-5 下午2:19:11
     */
    public static void showToast(String content) {
        if (!checkToastPermission()) {
            if (mOldToast != null) {
                mOldToast.cancel();
            }
            mOldToast = Toast.makeText(TCApplication.mContext, content, Toast.LENGTH_LONG);
            mOldToast.show();
            return;
        }
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


    private static boolean checkToastPermission() {
        PackageManager pm = TCApplication.mContext.getPackageManager();
        return PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.SYSTEM_ALERT_WINDOW", TCApplication.mContext.getPackageName());
    }

}
