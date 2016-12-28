package app.net.tongcheng.fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.umeng.analytics.MobclickAgent;


import app.net.tongcheng.util.HttpBusinessListener;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/16 11:40
 */
public abstract class BaseFragment extends Fragment implements HttpBusinessListener {

    protected String className;

    public Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                mHandDoSomeThing(msg);
            } catch (Exception e) {
                e.toString();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isVisible()) {
            loadData();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    public abstract void loadData();

    public abstract void mHandDoSomeThing(Message msg);

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TextUtils.isEmpty(className) ? "BaseFragment" : className);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TextUtils.isEmpty(className) ? "BaseFragment" : className);
    }
}
