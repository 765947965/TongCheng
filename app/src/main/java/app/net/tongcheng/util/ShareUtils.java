package app.net.tongcheng.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

import app.net.tongcheng.TCApplication;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/13 10:14
 */
public class ShareUtils {

    public static void SendIMInfo(Activity mActivity, String platform, String title, String shareContent, String shareUrl) {
        if (ShortMessage.NAME.equals(platform)) {
            try {
                // 短信
                String smsBody = shareContent + shareUrl;
                Uri smsToUri = Uri.parse("smsto:");
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
                // 短信内容
                sendIntent.putExtra("sms_body", smsBody);
                sendIntent.setType("vnd.android-dir/mms-sms");
                mActivity.startActivity(sendIntent);
                return;
            } catch (Exception e) {
                // 直接调用系统发短信如果发生异常，使用shareSDK相应发短信功能
            }
        }
        ShareSDK.initSDK(TCApplication.mContext);
        OnekeyShare oks = new OnekeyShare();

        oks.setSite("同城商城");
        oks.setTitleUrl(shareUrl);
        oks.setSiteUrl(shareUrl);
        if (WechatMoments.NAME.equals(platform)) {
            // 微信的标题就是分享内容
            oks.setTitle(shareContent);
        } else {
            // 分享内容
            oks.setTitle(title);
        }
        // 其它平台正常设置内容和链接
        oks.setUrl(shareUrl);
        oks.setText(shareContent);
        // 设置图标
        File fl = new File(WriteToSD.imgPath);
        if (!fl.exists()) {
            new WriteToSD(mActivity);
        }
        oks.setImagePath(WriteToSD.imgPath);
        // 是否直接分享
        oks.setSilent(false);
        oks.setDialogMode();
        oks.setPlatform(platform);
        oks.show(mActivity);
    }
}
