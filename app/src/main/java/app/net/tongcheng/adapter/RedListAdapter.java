package app.net.tongcheng.adapter;

import android.content.Context;
import android.view.View;

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
public class RedListAdapter extends MyBaseRecyclerViewAdapter<RedModel> {


    public RedListAdapter(Context mContext, List<RedModel> mDatas) {
        super(mContext, mDatas, R.layout.red_listviewadapter);
    }

    @Override
    public void onItemClick(View view, RedModel itemdata, List<RedModel> list, int position) {

    }

    @Override
    public void onCreateItemView(MyRecyclerViewHolder holder, RedModel itemdata, List<RedModel> list, int position) {

    }
}
