package app.net.tongcheng.adapter;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import app.net.tongcheng.R;
import app.net.tongcheng.model.FriendsBean;
import app.net.tongcheng.util.MyRecyclerViewHolder;

/**
 * Created by 76594 on 2016/6/7.
 */
public class FriendHAdater extends MyBaseRecyclerViewAdapter<FriendsBean> {
    private Handler mHandler;


    public FriendHAdater(Context mContext, List<FriendsBean> mDatas, int itemLayoutId, Handler mHandler) {
        super(mContext, mDatas, itemLayoutId);
        this.mHandler = mHandler;
    }

    @Override
    public void onItemClick(View view, FriendsBean itemdata, List<FriendsBean> list, int position) {
        itemdata.setSelect(!itemdata.isSelect());
        mHandler.sendEmptyMessage(10003);
    }

    @Override
    public void onCreateItemView(MyRecyclerViewHolder holder, FriendsBean itemdata, List<FriendsBean> list, int position) {
        holder.setText(R.id.itemtext, TextUtils.isEmpty(itemdata.getRemark()) ? itemdata.getPhone() : itemdata.getRemark());
    }
}
