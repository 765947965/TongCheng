package app.net.tongcheng.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.CancelableClear;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/16 11:40
 */
public abstract class BaseFragment extends Fragment implements CancelableClear {


    private List<Callback.Cancelable> mCancelableList = new ArrayList<>();

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                mHandDoSomeThing(msg);
                switch (msg.what) {
                    case APPCationStation.SUCCESS:
                        Bundle mBundleSuccess = msg.getData();
                        BusinessOnSuccess(mBundleSuccess.getInt("mLoding_Type"), (ConnectResult) mBundleSuccess.getSerializable("ConnectResult"));
                        break;
                    case APPCationStation.FAIL:
                        Bundle mBundleFail = msg.getData();
                        BusinessOnFail(mBundleFail.getInt("mLoding_Type"));
                        break;
                }
            } catch (Exception e) {
                e.toString();
            }
        }
    };

    @Override
    public void addCancelable(Callback.Cancelable mCancelable) {
        mCancelableList.add(mCancelable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        for (Callback.Cancelable mCancelable : mCancelableList) {
            if (mCancelable != null && !mCancelable.isCancelled()) {
                mCancelable.cancel();
            }
        }
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

    public abstract void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult);

    public abstract void BusinessOnFail(int mLoding_Type);
}
