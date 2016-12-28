package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import app.net.tongcheng.R;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.GeneralDateUtils;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/11/19 11:42
 */

public class PersonalitySetActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private ViewHolder mViewHolder;
    private ToggleButton btnContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personality_set_layout);
        setTitle("通讯录开关");
        initView();
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        btnContent = mViewHolder.getView(R.id.btnContent);
    }

    @Override
    public void loadData() {
        int type = GeneralDateUtils.getInt(GeneralDateUtils.CONTACTS_SWITCH, false);
        if (type == 2) {
            btnContent.setChecked(true);
        }
        mHandler.sendEmptyMessageDelayed(10001, 100);
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                btnContent.setOnCheckedChangeListener(this);
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {

    }

    @Override
    public void BusinessOnFail(int mLoadType) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.btnContent:
                if (isChecked) {
                    GeneralDateUtils.PutInt(GeneralDateUtils.CONTACTS_SWITCH, 2, false);
                    sendEventBusMessage("canUpLoadContents");//通知上传通讯录
                } else {
                    GeneralDateUtils.PutInt(GeneralDateUtils.CONTACTS_SWITCH, 1, false);
                }
                break;
        }
    }
}
