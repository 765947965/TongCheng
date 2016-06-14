package app.net.tongcheng.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.List;
import java.util.Random;

import app.net.tongcheng.R;
import app.net.tongcheng.model.FriendsBean;
import app.net.tongcheng.util.MyRecyclerViewHolder;
import app.net.tongcheng.util.ToastUtil;

/**
 * Created by 76594 on 2016/6/7.
 */
public class FriendVAdater extends MyBaseRecyclerViewAdapter<FriendsBean> {
    private Handler mHandler;
    private EditText et_search;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    public FriendVAdater(Context mContext, List<FriendsBean> mDatas, int itemLayoutId, Handler mHandler, EditText et_search, SwipeRefreshLayout mSwipeRefreshLayout) {
        super(mContext, mDatas, itemLayoutId);
        this.mHandler = mHandler;
        this.et_search = et_search;
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
    }

    @Override
    public void onItemClick(View view, FriendsBean itemdata, List<FriendsBean> list, int position) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            ToastUtil.showToast("数据同步中...");
            return;
        }
        itemdata.setSelect(!itemdata.isSelect());
        mHandler.sendEmptyMessage(10003);
    }

    @Override
    public void onCreateItemView(MyRecyclerViewHolder holder, FriendsBean itemdata, List<FriendsBean> list, int position) {
        if (!TextUtils.isEmpty(itemdata.getPicture())) {
            holder.setImage(R.id.pre_tx, itemdata.getPicture(), itemdata.getPictureRED(), 360);
        } else {
            holder.setImage(R.id.pre_tx, itemdata.getPictureRED());
        }
        String searth = et_search.getText().toString().toUpperCase();
        if (TextUtils.isEmpty(searth)) {
            holder.setText(R.id.nametext, TextUtils.isEmpty(itemdata.getRemark()) ? itemdata.getPhone() : itemdata.getRemark());
            holder.setText(R.id.phonetext, itemdata.getPhone());
        } else {
            holder.setText(R.id.nametext, Html.fromHtml((TextUtils.isEmpty(itemdata.getRemark()) ? itemdata.getPhone() : itemdata.getRemark()).replace(searth, "<font color=#FF6666>" + searth + "</font>")));
            holder.setText(R.id.phonetext, Html.fromHtml(itemdata.getPhone().replace(searth, "<font color=#FF6666>" + searth + "</font>")));
        }
        if (itemdata.isSelect()) {
            holder.setImage(R.id.item_radiobutton, R.drawable.aor);
        } else {
            holder.setImage(R.id.item_radiobutton, R.drawable.aos);
        }
        if (position == 0) {
            holder.setText(R.id.item_szm, itemdata.getFY()).setVisibility(View.VISIBLE);
        } else {
            FriendsBean itemdataU = list.get(position - 1);
            if (itemdata.getFY().equals(itemdataU.getFY())) {
                holder.setVisibility(R.id.item_szm, View.GONE);
            } else {
                holder.setText(R.id.item_szm, itemdata.getFY()).setVisibility(View.VISIBLE);
            }
        }
    }
}
