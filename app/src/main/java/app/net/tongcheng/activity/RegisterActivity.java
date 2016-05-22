package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.Utils;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/5/21 14:46
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private ViewHolder mViewHolder;
    private EditText et_phone, et_invite_code;
    private CheckBox checkBox;
    private TextView tv_protocol;
    private Button bt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        initView();
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.sll_main), this);
        et_phone = mViewHolder.getView(R.id.et_phone);
        et_invite_code = mViewHolder.getView(R.id.et_invite_code);
        checkBox = mViewHolder.getView(R.id.checkBox);
        tv_protocol = mViewHolder.getView(R.id.tv_protocol);
        bt_register = mViewHolder.getView(R.id.bt_register);
        mViewHolder.setOnClickListener(R.id.iv_clearphone);
        bt_register.setOnClickListener(this);
        et_phone.addTextChangedListener(this);
    }


    @Override
    public void loadData() {
        Utils.setInputMethodVisiable(et_phone, 250);
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clearphone://清除
                et_phone.setText("");
                break;
            case R.id.tv_protocol://查看合同
                break;
            case R.id.bt_register://注册
                if (!checkBox.isChecked()) {
                    ToastUtil.showToast("请先阅读并同意用户协议!");
                    return;
                }
                startActivity(new Intent(TCApplication.mContext, MainActivity.class));
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s.toString())) {
            mViewHolder.setVisibility(R.id.iv_clearphone, View.INVISIBLE);
        } else {
            mViewHolder.setVisibility(R.id.iv_clearphone, View.VISIBLE);
        }
        if (s.toString().length() == 11) {
            bt_register.setEnabled(true);
        } else {
            bt_register.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
