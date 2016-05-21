package app.net.tongcheng.fragment;

import android.support.v4.app.Fragment;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

import app.net.tongcheng.util.CancelableClear;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/16 11:40
 */
public abstract class BaseFragment extends Fragment implements CancelableClear {

    public boolean isfirstloaddata;

    private List<Callback.Cancelable> mCancelableList = new ArrayList<>();

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
        if (isVisibleToUser && isVisible() && !isfirstloaddata) {
            isfirstloaddata = true;
            loadData();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    public abstract void loadData();

    public boolean isfirstloaddata() {
        return isfirstloaddata;
    }

    public void setIsfirstloaddata(boolean isfirstloaddata) {
        this.isfirstloaddata = isfirstloaddata;
    }
}
