package app.net.tongcheng.util;

import org.xutils.common.Callback;

import app.net.tongcheng.connector.ConnectCallInterface;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/12/18 16:47
 */

public class ConnectCallImper implements ConnectCallInterface {

    private Callback.Cancelable mCancelable;

    @Override
    public void cancel() {
        if (mCancelable != null && !mCancelable.isCancelled()) mCancelable.cancel();
    }

    public void setmCancelable(Callback.Cancelable mCancelable) {
        this.mCancelable = mCancelable;
    }
}
