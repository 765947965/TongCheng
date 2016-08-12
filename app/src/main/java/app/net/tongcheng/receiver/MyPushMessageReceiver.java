package app.net.tongcheng.receiver;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

import app.net.tongcheng.TCApplication;
import app.net.tongcheng.activity.MainActivity;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/8/5 17:00
 */
public class MyPushMessageReceiver extends PushMessageReceiver {

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
    }

    @Override
    public void onNotificationMessageClicked(final Context context, MiPushMessage message) {

    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {

    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                TCApplication.mRegId = cmdArg1;
                if (TCApplication.getmUserInfo() != null) {
                    MiPushClient.setAlias(TCApplication.mContext, TCApplication.mRegId, null);
                }
            }
        }
    }
}
