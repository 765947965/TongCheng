package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.widget.TextView;

import app.net.tongcheng.R;
import app.net.tongcheng.model.ConnectResult;

/**
 * Created by 76594 on 2016/6/17.
 */
public class TextTipsActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_tips_layout);
    }

    @Override
    public void loadData() {
        String tips = getIntent().getStringExtra("tips");
        if (!TextUtils.isEmpty(tips)) {
            ((TextView) findViewById(R.id.tv_tips)).setText(tips);
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
}
