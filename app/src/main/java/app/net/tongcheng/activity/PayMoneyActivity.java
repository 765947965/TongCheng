package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import app.net.tongcheng.R;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/5/24 16:16
 */
public class PayMoneyActivity extends BaseActivity implements View.OnClickListener {

    private ViewHolder mViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_money_layout);
        setTitle("付款");
        initView();
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.rlt_main), this);
    }


    @Override
    public void loadData() {

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

    }
}
