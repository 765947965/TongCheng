package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import app.net.tongcheng.Business.RedBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.CheckEvent;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.Utils;
import app.net.tongcheng.util.ViewHolder;

/**
 * Created by 76594 on 2016/6/10.
 */
public class BandingNewCard extends BaseActivity implements View.OnClickListener {
    private ViewHolder mViewHolder;
    private RedBusiness mRedBusiness;
    private EditText et_port_address, et_bank_card_no, et_name;
    private TextView et_bank_name, tv_address;
    private Button bt_banding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banding_new_card_info);
        setTitle("绑定新银行卡");
        initView();
        setEventBus();
        mRedBusiness = new RedBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        mViewHolder.setOnClickListener(R.id.bt_banding);
        et_bank_name = mViewHolder.getView(R.id.et_bank_name);
        tv_address = mViewHolder.getView(R.id.tv_address);
        et_port_address = mViewHolder.getView(R.id.et_port_address);
        et_bank_card_no = mViewHolder.getView(R.id.et_bank_card_no);
        et_name = mViewHolder.getView(R.id.et_name);
        bt_banding = mViewHolder.getView(R.id.bt_banding);
        mViewHolder.setOnClickListener(R.id.et_bank_name);
        mViewHolder.setOnClickListener(R.id.tv_address);
    }


    @Override
    public void loadData() {

    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {
        switch (mLoadType) {
            case APPCationStation.SUMBIT:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    ToastUtil.showToast("绑定成功");
                    sendEventBusMessage("balance_rushe");
                    finish();
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoadType) {
        ToastUtil.showToast("网络不可用,请检查网络连接!");
        switch (mLoadType) {
            case APPCationStation.SUMBIT:
                bt_banding.setEnabled(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_banding:
                String bank_name = et_bank_name.getText().toString();
                String str_address = tv_address.getText().toString();
                String str_port_address = et_port_address.getText().toString();
                String bank_card_no = et_bank_card_no.getText().toString();
                String card_holder = et_name.getText().toString();

                if (TextUtils.isEmpty(card_holder)) {
                    ToastUtil.showToast("请输入开户名");
                } else if (TextUtils.isEmpty(bank_card_no)) {
                    ToastUtil.showToast("请输入银行卡号");
                } else if (!Utils.checkBankCard(bank_card_no)) {
                    ToastUtil.showToast("请输入正确的银行卡号");
                } else if (TextUtils.isEmpty(bank_name)) {
                    ToastUtil.showToast("请选择开户银行");
                } else if (TextUtils.isEmpty(str_address)) {
                    ToastUtil.showToast("请选择开户银行所在地");
                } else if (TextUtils.isEmpty(str_port_address)) {
                    ToastUtil.showToast("请填写开户行网点名称");
                } else {
                    bt_banding.setEnabled(false);
                    Map<String, String> map_value = new HashMap<>();
                    map_value.put("bank_name", bank_name);
                    map_value.put("branch_name", str_address+str_port_address);
                    map_value.put("card_holder", card_holder);
                    map_value.put("bank_card_no", bank_card_no);
                    MobclickAgent.onEvent(this, "BandingNewCard", map_value);
                    mRedBusiness.bandingCard(APPCationStation.SUMBIT, "提交中...", bank_name, bank_card_no, card_holder, str_address + str_port_address);
                }
                break;
            case R.id.et_bank_name:
                startActivity(new Intent(TCApplication.mContext, ChangeBanckName.class));
                break;
            case R.id.tv_address:
                startActivity(new Intent(TCApplication.mContext, ChangeProvince.class));
                break;
        }
    }


    @Subscribe
    public void onEvent(CheckEvent event) {
        if (event != null) {
            try {
                if (event.getMsg().startsWith("bank_name=")) {
                    et_bank_name.setText(event.getMsg().split("=")[1]);
                }
                if (event.getMsg().startsWith("provinceCity=")) {
                    String[] vlues = event.getMsg().split("=")[1].split(":");
                    tv_address.setText(vlues[0] + vlues[1] + vlues[2]);
                }
            } catch (Exception e) {
            }
        }
    }
}
