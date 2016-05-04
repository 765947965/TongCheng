package app.net.tongcheng.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import app.net.tongcheng.util.MyRecyclerViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/25 10:10
 */
public abstract class MyBaseRecyclerViewAdapter<T> extends RecyclerView.Adapter implements View.OnClickListener {
    protected Context mContext;
    protected List<T> mDatas;
    protected int itemLayoutId;

    public MyBaseRecyclerViewAdapter(Context mContext, List<T> mDatas, int itemLayoutId) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.itemLayoutId = itemLayoutId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(itemLayoutId, parent, false);
        MyRecyclerViewHolder holder = new MyRecyclerViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        onCreateItemView((MyRecyclerViewHolder) holder, getItem(position), mDatas, position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        onItemClick(v, getItem(position), mDatas, position);
    }

    private T getItem(int position) {
        return mDatas.get(position);
    }

    public abstract void onItemClick(View view, T itemdata, List<T> list, int position);

    public abstract void onCreateItemView(MyRecyclerViewHolder holder, T itemdata, List<T> list, int position);
}
