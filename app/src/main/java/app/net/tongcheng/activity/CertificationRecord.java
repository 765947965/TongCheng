package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import app.net.tongcheng.Business.MyBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.model.CheckEvent;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.OperationUtils;

/**
 * Created by 76594 on 2017/7/26.
 */

public class CertificationRecord extends BaseActivity implements View.OnClickListener {


    private MyBusiness mMyBusiness;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certification_record);
        setTitle("绑定新银行卡");
        initView();
        setEventBus();
        mMyBusiness = new MyBusiness(this, this, mHandler);
    }

    private void initView() {

    }


    @Override
    public void loadData() {
        if (!OperationUtils.getBoolean(OperationUtils.hadCertification, true)) {
            getRightBtn().setVisibility(View.VISIBLE);
            getRightBtn().setText("提交认证");
            getRightBtn().setOnClickListener(this);
            mMyBusiness.queryCertificationStatus(APPCationStation.CHECK, "");
        }
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mHttpLoadType, ConnectResult mConnectResult) {
        switch (mHttpLoadType) {
            case APPCationStation.CHECK:
                try {
                    String jsonStr = (String) mConnectResult.getObject();
                    JSONObject json = new JSONObject(jsonStr);
                    if (json.getInt("result") == 41) {
                        OperationUtils.PutBoolean(OperationUtils.hadCertification, true, true);
                        getRightBtn().setVisibility(View.GONE);
                        sendEventBusMessage("MyFragment.Refresh");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mHttpLoadType) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRight:
                startActivity(new Intent(this, SubmitCertification.class));
                break;
        }
    }

    @Subscribe
    public void onEvent(CheckEvent event) {

    }
}
