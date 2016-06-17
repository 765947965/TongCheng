package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.FriendsBean;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;

/**
 * Created by 76594 on 2016/6/17.
 */
public class FrienMoreInfoActivity extends BaseActivity implements View.OnClickListener {
    private ViewHolder mViewHolder;
    private FriendsBean itemdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frien_more_info_layout);
        setTitle("详细资料");
        initView();
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        mViewHolder.setOnClickListener(R.id.tv_sendRed);
        mViewHolder.setOnClickListener(R.id.iv_head_image);
    }

    @Override
    public void loadData() {
        itemdata = (FriendsBean) getIntent().getSerializableExtra("FriendsBean");
        if (itemdata != null) {
            if (!TextUtils.isEmpty(itemdata.getPicture()) && itemdata.getPicture().startsWith("http")) {
                mViewHolder.setImage(R.id.iv_head_image, itemdata.getPicture(), 0, 360);
            } else {
                mViewHolder.setImage(R.id.iv_head_image, itemdata.getPictureRED());
            }
            mViewHolder.setText(R.id.tv_remark, TextUtils.isEmpty(itemdata.getRemark()) ? itemdata.getPhone() : itemdata.getRemark());
            if (!TextUtils.isEmpty(itemdata.getSex())) {
                if ("男".equals(itemdata.getSex())) {
                    mViewHolder.setImage(R.id.iv_sex, R.drawable.one_profile_male_left_dark);
                } else if ("女".equals(itemdata.getSex())) {
                    mViewHolder.setImage(R.id.iv_sex, R.drawable.one_profile_female_left_dark);
                }
            }
            if (!TextUtils.isEmpty(itemdata.getName())) {
                mViewHolder.setText(R.id.tv_name, "昵称: " + itemdata.getName()).setVisibility(View.VISIBLE);
            }
            mViewHolder.setText(R.id.tv_phone, "手机号: " + itemdata.getPhone());
            mViewHolder.setText(R.id.tv_uid, itemdata.getUid());
            if (!TextUtils.isEmpty(itemdata.getSignature())) {
                mViewHolder.setVisibility(R.id.llt_signe, View.VISIBLE);
                mViewHolder.setText(R.id.tv_signe, itemdata.getSignature());
            }
            if (!TextUtils.isEmpty(itemdata.getProvince()) && !TextUtils.isEmpty(itemdata.getCity())) {
                mViewHolder.setVisibility(R.id.llt_address, View.VISIBLE);
                mViewHolder.setText(R.id.tv_address, itemdata.getProvince() + "-" + itemdata.getCity());
            }
        }
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {

    }

    @Override
    public void BusinessOnFail(int mLoding_Type) {

    }

    @Override
    public void onClick(View v) {
        if (itemdata == null) {
            ToastUtil.showToast("获取好友资料失败!");
            return;
        }
        switch (v.getId()) {
            case R.id.tv_sendRed:
                startActivity(new Intent(TCApplication.mContext, PersonalRedEnvelopeConfig.class).putExtra("uids", itemdata.getUid()).putExtra("name", itemdata.getRemark()).putExtra("nums", 1));
                break;
            case R.id.iv_head_image:
                if (!TextUtils.isEmpty(itemdata.getPicture()) && itemdata.getPicture().startsWith("http")) {
                    startActivity(new Intent(TCApplication.mContext, ShowImageActivity.class).putExtra("url", itemdata.getPicture()));
                }
                break;
        }
    }
}
