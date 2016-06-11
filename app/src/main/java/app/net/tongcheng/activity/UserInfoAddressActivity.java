package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import app.net.tongcheng.R;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.ViewHolder;

/**
 * Created by 76594 on 2016/6/11.
 */
public class UserInfoAddressActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private ViewHolder mViewHolder;
    private EditText spd_cn_inputqianming;
    private TextView tjsum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spd_changqianming);
        setTitle("详细地址");
        initView();
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        spd_cn_inputqianming = mViewHolder.getView(R.id.spd_cn_inputqianming);
        tjsum = mViewHolder.getView(R.id.tjsum);
        spd_cn_inputqianming.addTextChangedListener(this);
        mViewHolder.setText(R.id.tv_tips, "填写详细地址");
        spd_cn_inputqianming.setHint("输入详细地址");
    }

    @Override
    public void loadData() {
        String location = getIntent().getStringExtra("location");
        if (!TextUtils.isEmpty(location)) {
            spd_cn_inputqianming.setText(location);
            spd_cn_inputqianming.setSelection(spd_cn_inputqianming.getText().toString().length());
        }
    }

    @Override
    protected void onDestroy() {
        sendEventBusMessage("location=" + spd_cn_inputqianming.getText().toString());
        super.onDestroy();
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String temp = s.toString();
        tjsum.setText(30 - temp.length() + "");
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
