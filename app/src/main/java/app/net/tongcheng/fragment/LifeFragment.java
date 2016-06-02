package app.net.tongcheng.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.net.tongcheng.Business.LifeBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.LifeDataModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/16 15:50
 */
public class LifeFragment extends BaseFragment implements View.OnClickListener {

    private ViewHolder mViewHolder;
    private LifeBusiness mLifeBusiness;
    public static boolean isfirstloaddata;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_life_layout, null);
        initView(view);
        isfirstloaddata = false;
        mLifeBusiness = new LifeBusiness(this, getActivity(), mHandler);
        return view;
    }

    private void initView(View view) {
        mViewHolder = new ViewHolder(view, this);
    }

    @Override
    public void loadData() {
        if (isfirstloaddata) {
            return;
        }
        isfirstloaddata = true;
        mHandler.sendEmptyMessageDelayed(10001, 100);
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                LifeDataModel mLifeDataModel = NativieDataUtils.getLifeDataModel();
                if (mLifeDataModel == null || !NativieDataUtils.getTodyYMD().equals(mLifeDataModel.getUpdate())) {
                    mLifeBusiness.getLifeData(APPCationStation.LOADING, "");
                }
                if (mLifeDataModel == null || mLifeDataModel.getItems() == null || mLifeDataModel.getItems().size() == 0) {
                    return;
                }
                // 设置数据
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.LOADING:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    LifeDataModel mLifeDataModel = (LifeDataModel) mConnectResult.getObject();
                    mLifeDataModel.setUpdate(NativieDataUtils.getTodyYMD());
                    NativieDataUtils.setLifeDataModel(mLifeDataModel);
                    mHandler.sendEmptyMessage(10001);
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoding_Type) {

    }

    @Override
    public void onClick(View v) {

    }
}
