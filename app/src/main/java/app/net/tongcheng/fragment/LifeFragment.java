package app.net.tongcheng.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.looppager.BannerView;

import java.util.ArrayList;
import java.util.List;

import app.net.tongcheng.Business.LifeBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.adapter.MyBaseAdapter;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.LifeDataModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ViewHolder;
import app.net.tongcheng.view.ScrollViewGridView;

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
    private BannerView mBannerView;
    private ScrollViewGridView mGridView;
    private LifeDataModel mLifeDataModel;
    public static boolean isfirstloaddata;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_life_layout, null);
        initView(view);
        isfirstloaddata = false;
        mLifeBusiness = new LifeBusiness(this, getActivity(), mHandler);
        return view;
    }

    private void initView(View view) {
        mViewHolder = new ViewHolder(view, this);
        mBannerView = mViewHolder.getView(R.id.image_banner);
        mGridView = mViewHolder.getView(R.id.gridlayout);
        mViewHolder.setOnClickListener(R.id.flt_life_head_1);
        mViewHolder.setOnClickListener(R.id.flt_life_head_2);
        mViewHolder.setOnClickListener(R.id.flt_life_head_3);
        mViewHolder.setOnClickListener(R.id.flt_life_head_4);
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
                this.mLifeDataModel = mLifeDataModel;
                // 设置数据
                setLifeData();
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

    private void setLifeData() {
        if (mLifeDataModel.getItems().size() > 3) {
            mViewHolder.setText(R.id.tv_life_head_1, mLifeDataModel.getItems().get(0).getName());
            mViewHolder.setImage(R.id.iv_life_head_1, mLifeDataModel.getItems().get(0).getPic());
            mViewHolder.setText(R.id.tv_life_head_2, mLifeDataModel.getItems().get(1).getName());
            mViewHolder.setImage(R.id.iv_life_head_2, mLifeDataModel.getItems().get(1).getPic());
            mViewHolder.setText(R.id.tv_life_head_3, mLifeDataModel.getItems().get(2).getName());
            mViewHolder.setImage(R.id.iv_life_head_3, mLifeDataModel.getItems().get(2).getPic());
            mViewHolder.setText(R.id.tv_life_head_4, mLifeDataModel.getItems().get(3).getName());
            mViewHolder.setImage(R.id.iv_life_head_4, mLifeDataModel.getItems().get(3).getPic());
        }
        if (mLifeDataModel.getItems().size() > 4) {
            mGridView.setAdapter(new MyBaseAdapter<LifeDataModel.ItemsBean>(mGridView, TCApplication.mContext, mLifeDataModel.getItems().subList(4, mLifeDataModel.getItems().size()), R.layout.life_adapter_item_layout) {
                @Override
                protected void convert(ViewHolder holder, LifeDataModel.ItemsBean item, List<LifeDataModel.ItemsBean> list, int position) {
                    if (item != null) {
                        holder.setText(R.id.tv_life_head, item.getName());
                        holder.setImage(R.id.iv_life_head, item.getPic());
                    }
                }

                @Override
                protected void MyonItemClick(AdapterView<?> parent, View view, LifeDataModel.ItemsBean item, List<LifeDataModel.ItemsBean> list, int position, long id) {
                    if (item != null) {

                    }
                }
            });
        } else {
            mGridView.setAdapter(null);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flt_life_head_1:
                break;
            case R.id.flt_life_head_2:
                break;
            case R.id.flt_life_head_3:
                break;
            case R.id.flt_life_head_4:
                break;
        }

    }
}
