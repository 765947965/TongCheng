package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

import app.net.tongcheng.Business.OtherBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.StartPageModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.OperationUtils;
import app.net.tongcheng.util.ViewHolder;

/**
 * Created by 76594 on 2016/5/31.
 */
public class StartPageActivity extends BaseActivity implements View.OnClickListener {
    private ViewHolder mViewHolder;
    private String startPageUrl;
    private int times;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page_layout);
        startPageUrl = getIntent().getStringExtra("startPageUrl");
        times = getIntent().getIntExtra("times", 0);
        initView();
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
    }

    @Override
    public void loadData() {
        if (!TextUtils.isEmpty(startPageUrl)) {
            OperationUtils.PutString("start_page_show_times", new SimpleDateFormat("yyyyMMdd").format(new Date()) + times);
            mViewHolder.setImage(R.id.iv_start_image, startPageUrl);
        }
        mHandler.sendEmptyMessageDelayed(10001, 3000);
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                startActivity(new Intent(TCApplication.mContext, MainActivity.class));
                break;
        }
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
