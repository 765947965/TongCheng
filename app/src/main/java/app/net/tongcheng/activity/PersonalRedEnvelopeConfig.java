package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.net.tongcheng.Business.OtherBusiness;
import app.net.tongcheng.Business.RedBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.MoneyInfoModel;
import app.net.tongcheng.model.RedModel;
import app.net.tongcheng.model.UserMoreInfoModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.OperationUtils;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.Utils;
import app.net.tongcheng.util.ViewHolder;
import app.net.tongcheng.view.InputObjectDialog;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/8 16:12
 */
public class PersonalRedEnvelopeConfig extends BaseActivity implements View.OnClickListener, TextWatcher, InputObjectDialog.InvestPayObjectDialogListener {
    private ViewHolder mViewHolder;
    private EditText whoimisedit, money_input, palpc_inputzfy;
    private String uids, name;
    private TextView sendpalpcredbt;
    private int nums;
    private double Almoney = -1d;
    private RedBusiness mRedBusiness;
    private OtherBusiness mOtherBusiness;
    private List<String> moneytyps;
    private int moneytype = 1;
    private InputObjectDialog mDialog;
    private String outmoney, tips, fromname;

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
        mOtherBusiness = new OtherBusiness(this, this, mHandler);
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
            Almoney = mMoneyInfoModel.getData().getCanfetch_amount() - mMoneyInfoModel.getData().getMin_cash_amount();
            Almoney = Almoney < 0 ? 0 : Almoney;
            money_input.setHint("可用:" + Almoney / 100d);
        }
        UserMoreInfoModel mUserMoreInfoModel = NativieDataUtils.getUserMoreInfoModel();
        if (mUserMoreInfoModel != null && !TextUtils.isEmpty(mUserMoreInfoModel.getName())) {
            mViewHolder.setText(R.id.whoimisedit, mUserMoreInfoModel.getName());
        }
        moneytyps = new ArrayList<>();
        moneytyps.add("拼手气红包");
        moneytyps.add("普通红包");
        mRedBusiness.getMoneyInfo(APPCationStation.LOADING, "");
        if (!OperationUtils.getBoolean(OperationUtils.walletPassword)) {
            mOtherBusiness.getWalletPasswordType(APPCationStation.WALLETPASSWORD, "");
        }
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {
        switch (mLoadType) {
            case APPCationStation.SUMBIT:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    sendEventBusMessage("sendRedOk");
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
                    Almoney = mMoneyInfoModel.getData().getCanfetch_amount() - mMoneyInfoModel.getData().getMin_cash_amount();
                    Almoney = Almoney < 0 ? 0 : Almoney;
                    money_input.setHint("可用:" + Almoney / 100d);
                }
                break;
            case APPCationStation.WALLETPASSWORD:
                if (mConnectResult != null && mConnectResult.getObject() != null) {
                    if (((BaseModel) mConnectResult.getObject()).getResult() == 81) {
                        OperationUtils.PutBoolean(OperationUtils.walletPassword, false);
                    } else if (((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                        OperationUtils.PutBoolean(OperationUtils.walletPassword, true);
                    }
                }
                break;
            case APPCationStation.CHECKWALLETPASSWORD:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                    mRedBusiness.sendRed(APPCationStation.SUMBIT, "发送中...", fromname, Double.parseDouble(outmoney) * 100d + "", moneytype + "", tips, uids, nums + "");
                } else {
                    if (mDialog != null) {
                        mDialog.submitInputFailure();
                    }
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoadType) {
        switch (mLoadType) {
            case APPCationStation.CHECKWALLETPASSWORD:
                if (mDialog != null) {
                    mDialog.submitInputFailure();
                }
                break;
        }
        ToastUtil.showToast("网络不可用，请检查网络连接！");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendpalpcredbt:
                fromname = whoimisedit.getText().toString();
                outmoney = money_input.getText().toString();
                tips = palpc_inputzfy.getText().toString();
                if (TextUtils.isEmpty(tips)) {
                    tips = palpc_inputzfy.getHint().toString();
                }
                if (!OperationUtils.getBoolean(OperationUtils.walletPassword)) {
                    DialogUtil.showTipsDialog(this, "提示", "请先设置钱包密码!", "确定", "取消", new DialogUtil.OnConfirmListener() {
                        @Override
                        public void clickConfirm() {
                            startActivity(new Intent(TCApplication.mContext, SetWalletActivity.class));
                        }

                        @Override
                        public void clickCancel() {

                        }
                    });
                } else if (TextUtils.isEmpty(fromname)) {
                    ToastUtil.showToast("请输入姓名");
                } else if (TextUtils.isEmpty(outmoney)) {
                    ToastUtil.showToast("请输入金额");
                } else if (Double.parseDouble(outmoney) <= 0) {
                    ToastUtil.showToast("金额必须大于零");
                } else if (Almoney != -1d && Double.parseDouble(outmoney) > Almoney / 100d) {
                    ToastUtil.showToast("金额不能大于可用金额");
                } else {
                    if (mDialog == null) {
                        mDialog = new InputObjectDialog(this, true, this);
                    }
                    mDialog.showPasswordDialog(Double.parseDouble(outmoney), "红包金额");
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

    @Override
    public void submitPassword(String password) {
        mOtherBusiness.checkWalletPassword(APPCationStation.CHECKWALLETPASSWORD, "校验中...", password);
    }

    @Override
    public void submitCode(String code) {

    }

    @Override
    public void getCode(InputObjectDialog.InvestSendCodeType mType) {

    }
}
