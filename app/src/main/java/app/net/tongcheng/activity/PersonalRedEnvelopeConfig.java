package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.net.tongcheng.Business.RedBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.fragment.FriendFragment;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.MoneyInfoModel;
import app.net.tongcheng.model.RedModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.EditTextUtil;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.Utils;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/8 16:12
 */
public class PersonalRedEnvelopeConfig extends BaseActivity implements View.OnClickListener, TextWatcher {
    private ViewHolder mViewHolder;
    private EditText whoimisedit, money_input, palpc_inputzfy;
    private String uids, name;
    private TextView sendpalpcredbt;
    private int nums;
    private double Almoney = -1d;
    private RedBusiness mRedBusiness;
    private List<String> moneytyps;
    private int moneytype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_red_envelopeconfig_layout);
        setTitle("个人红包");
        uids = getIntent().getStringExtra("uids");
        name = getIntent().getStringExtra("name");
        nums = getIntent().getIntExtra("nums", 1);
        initView();
        mRedBusiness = new RedBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        whoimisedit = mViewHolder.getView(R.id.whoimisedit);
        money_input = mViewHolder.getView(R.id.money_input);
        palpc_inputzfy = mViewHolder.getView(R.id.palpc_inputzfy);
        sendpalpcredbt = mViewHolder.getView(R.id.sendpalpcredbt);
        money_input.addTextChangedListener(this);
        mViewHolder.setOnClickListener(R.id.sendpalpcredbt);
        mViewHolder.setOnClickListener(R.id.red_lx);
    }

    @Override
    public void loadData() {
        if (nums == 1) {
            mViewHolder.setText(R.id.towhere, Html.fromHtml("<font color=#ED2E14>准备发给  </font><font color=#FF6506>" + name + " </font>"));
        } else {
            mViewHolder.setText(R.id.towhere, Html.fromHtml("<font color=#ED2E14>准备给  </font><font color=#FF6506>" + nums + " </font><font color=#ED2E14>  人发</font>"));
        }
        mViewHolder.setText(R.id.bttallmoney, Html.fromHtml("<font color=#AAA292>总金额  </font><font color=#FF6600>0.00</font><font color=#AAA292>元  </font>"));
        MoneyInfoModel mMoneyInfoModel = NativieDataUtils.getMoneyInfoModel();
        if (mMoneyInfoModel != null) {
            Almoney = mMoneyInfoModel.getData().getCanfetch_amount();
            money_input.setHint("可用:" + Almoney / 100d);
        }
        moneytyps = new ArrayList<>();
        moneytyps.add("拼手气红包");
        moneytyps.add("普通红包");
        mRedBusiness.getMoneyInfo(APPCationStation.LOADING, "");
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.SUMBIT:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    FriendFragment.isfirstloaddata = false;
                    RedModel mRedModel = NativieDataUtils.getRedModel(NativieDataUtils.getTodyY(), "sended");
                    if (mRedModel != null && NativieDataUtils.getTodyYMD().equals(mRedModel.getUpdate())) {
                        mRedModel.setUpdate("00000000");
                        NativieDataUtils.setRedModel(mRedModel, NativieDataUtils.getTodyY(), "sended");
                    }
                    // 启动成功页
                    startActivity(new Intent(TCApplication.mContext, PersonRedSendSucess.class).putExtra("nums", nums));
                    finish();
                }
                break;
            case APPCationStation.LOADING:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    MoneyInfoModel mMoneyInfoModel = (MoneyInfoModel) mConnectResult.getObject();
                    NativieDataUtils.setMoneyInfoModel(mMoneyInfoModel);
                    Almoney = mMoneyInfoModel.getData().getCanfetch_amount();
                    money_input.setHint("可用:" + Almoney / 100d);
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoding_Type) {
        ToastUtil.showToast("网络不可用，请检查网络连接！");
        if (mLoding_Type == APPCationStation.SUMBIT) {
            sendpalpcredbt.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendpalpcredbt:
                String name = whoimisedit.getText().toString();
                String outmoney = money_input.getText().toString();
                String tips = palpc_inputzfy.getText().toString();
                if (TextUtils.isEmpty(tips)) {
                    tips = palpc_inputzfy.getHint().toString();
                }
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.showToast("请输入姓名");
                } else if (TextUtils.isEmpty(outmoney)) {
                    ToastUtil.showToast("请输入金额");
                } else if (Double.parseDouble(outmoney) <= 0) {
                    ToastUtil.showToast("金额必须大于零");
                } else if (Almoney != -1d && Double.parseDouble(outmoney) > Almoney) {
                    ToastUtil.showToast("金额不能大于可用金额");
                } else {
                    sendpalpcredbt.setEnabled(false);
                    mRedBusiness.sendRed(APPCationStation.SUMBIT, "发送中...", name, Utils.numPointNoRounded(Double.parseDouble(outmoney) * 100d), moneytype + "", tips, uids, nums + "");
                }
                break;
            case R.id.red_lx:
                DialogUtil.showListDilaog(this, moneytyps, "红包类型", R.layout.text_item_layout, new DialogUtil.OnListDialogListener<String>() {
                    @Override
                    public void CreateItem(ViewHolder holder, String item, List<String> list, int position) {
                        holder.setText(R.id.tv_item, item);
                    }

                    @Override
                    public void OnItemSelect(View view, List<String> mDatas, String mItemdata, int position) {
                        moneytype = position;
                        ((TextView) mViewHolder.getView(R.id.red_lx)).setText(mItemdata);
                    }
                });
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        try {
            String outMoney = Utils.numPoint2NoRounded(Double.parseDouble(s.toString().length() == 0 ? "0" : s.toString()));
            mViewHolder.setText(R.id.bttallmoney, Html.fromHtml("<font color=#AAA292>总金额  </font><font color=#FF6600>" + outMoney + "</font><font color=#AAA292>元  </font>"));
        } catch (Exception e) {
            ToastUtil.showToast("请输入正确的金额");
            money_input.setText("");
            mViewHolder.setText(R.id.bttallmoney, Html.fromHtml("<font color=#AAA292>总金额  </font><font color=#FF6600>0.00</font><font color=#AAA292>元  </font>"));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
