package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;

import app.net.tongcheng.model.ConnectResult;

/**
 * Created by 76594 on 2016/5/31.
 */
public class StartPageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("启动页");
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
}
