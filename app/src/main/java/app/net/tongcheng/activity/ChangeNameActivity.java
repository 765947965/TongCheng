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
public class ChangeNameActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private ViewHolder mViewHolder;
    private EditText spd_cn_inputname;
    private TextView tjsum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spd_changename);
        setTitle("姓名");
        initView();
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        spd_cn_inputname = mViewHolder.getView(R.id.spd_cn_inputname);
        tjsum = mViewHolder.getView(R.id.tjsum);
        spd_cn_inputname.addTextChangedListener(this);
    }

    @Override
    public void loadData() {
        String name = getIntent().getStringExtra("name");
        if (!TextUtils.isEmpty(name)) {
            spd_cn_inputname.setText(name);
            spd_cn_inputname.setSelection(spd_cn_inputname.getText().toString().length());
        }
    }

    @Override
    protected void onDestroy() {
        sendEventBusMessage("userinfo=" + spd_cn_inputname.getText().toString());
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
        tjsum.setText(String.valueOf(10 - temp.length()));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
