package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import app.net.tongcheng.Business.RedBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.model.BaseModel;
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
    }


    @Override
    public void loadData() {

    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
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
    public void BusinessOnFail(int mLoding_Type) {
        ToastUtil.showToast("网络不可用,请检查网络连接!");
        switch (mLoding_Type) {
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

                bank_name = "中国银行";
                str_address = "广东省东莞市";
                str_port_address = "东城支行";

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
                    mRedBusiness.bandingCard(APPCationStation.SUMBIT, "提交中...", bank_name, bank_card_no, card_holder, str_address + str_port_address);
                }
                break;
        }
    }
}
