package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import app.net.tongcheng.R;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.ViewHolder;

/**
 * Created by 76594 on 2016/6/10.
 */
public class PersonRedSendSucess extends BaseActivity implements View.OnClickListener {

    private ViewHolder mViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personredsendsucess);
        setTitle("红包");
        initView();
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        mViewHolder.setOnClickListener(R.id.pdsuccessbt);
    }

    @Override
    public void loadData() {
        mViewHolder.setText(R.id.numforsuccess, getIntent().getIntExtra("nums", 1) + "个红包已发出");
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {

    }

    @Override
    public void BusinessOnFail(int mLoadType) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pdsuccessbt:
                finish();
                break;
        }
    }
}
