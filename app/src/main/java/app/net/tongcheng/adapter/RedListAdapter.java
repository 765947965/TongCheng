package app.net.tongcheng.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import app.net.tongcheng.R;
import app.net.tongcheng.model.RedModel;
import app.net.tongcheng.util.MyRecyclerViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/5/24 16:07
 */
public class RedListAdapter extends MyBaseRecyclerViewAdapter<RedModel.GiftsBean> {


    public RedListAdapter(Context mContext, List<RedModel.GiftsBean> mDatas) {
        super(mContext, mDatas, R.layout.red_listviewadapter);
    }

    @Override
    public void onItemClick(View view, RedModel.GiftsBean itemdata, List<RedModel.GiftsBean> list, int position) {

    }

    @Override
    public void onCreateItemView(MyRecyclerViewHolder holder, RedModel.GiftsBean itemdata, List<RedModel.GiftsBean> list, int position) {
        if (itemdata != null) {
            ((TextView) holder.getView(R.id.red_type_text)).setText("红包：" + itemdata.getFromnickname());
            ((TextView) holder.getView(R.id.red_creatime_text)).setText("收到：" + itemdata.getCreate_time());
            ((TextView) holder.getView(R.id.red_has_open_text)).setText("收到：" + itemdata.getExp_time());
        }
    }
}
