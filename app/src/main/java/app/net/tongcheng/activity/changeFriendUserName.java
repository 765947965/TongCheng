package app.net.tongcheng.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import app.net.tongcheng.Business.FriendBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.ContentModel;
import app.net.tongcheng.model.FriendModel;
import app.net.tongcheng.model.UpContentJSONModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.NativieDataUtils;
import okhttp3.Response;

/**
 * Created by 76594 on 2017/7/24.
 */

public class changeFriendUserName extends BaseActivity implements TextWatcher, View.OnClickListener {
    private FriendBusiness mFriendBusiness;
    private String remark;
    private String phone;

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_friend_user_name);
        setTitle("添加好友");
        initView();
        mFriendBusiness = new FriendBusiness(this, this, mHandler);
    }

    private void initView() {
        getRightBtn().setVisibility(View.VISIBLE);
        getRightBtn().setText("完成");
        getRightBtn().setEnabled(false);
        getRightBtn().setOnClickListener(this);
        mEditText = (EditText) findViewById(R.id.etRemark);
        mEditText.addTextChangedListener(this);
    }

    @Override
    public void loadData() {
        remark = getIntent().getStringExtra(Common.AGR1);
        phone = getIntent().getStringExtra(Common.AGR2);
        if (!TextUtils.isEmpty(remark)) {
            mEditText.setText(remark);
            mEditText.setSelection(mEditText.getText().toString().length());
        }
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mHttpLoadType, ConnectResult mConnectResult) {
        switch (mHttpLoadType) {
            case APPCationStation.SUMBIT:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    FriendModel mFriendModel = NativieDataUtils.getFriendModel();
                    if (mFriendModel != null && NativieDataUtils.getTodyYMD().equals(mFriendModel.getUpdate())) {
                        mFriendModel.setUpdate("00000000");
                        NativieDataUtils.setFriendModel(mFriendModel);
                    }
                    sendEventBusMessage("FriendFragment.Refresh");
                    sendEventBusMessage("changeFriendUserName=" + mEditText.getText().toString());
                    finish();
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mHttpLoadType, Response response) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mEditText.getText().toString().length() == 0) {
            getRightBtn().setEnabled(false);
        } else {
            getRightBtn().setEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        UpContentJSONModel mUpContentJSONModel = new UpContentJSONModel();
        List<ContentModel> mData = new ArrayList<>();
        List<String> phones = new ArrayList<>();
        phones.add(phone);
        mData.add(new ContentModel(System.currentTimeMillis(), mEditText.getText().toString(), phones));
        mUpContentJSONModel.setContactlist(mData);
        mUpContentJSONModel.setMac(((TelephonyManager) TCApplication.mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());
        mFriendBusiness.uploadContens(APPCationStation.SUMBIT, "添加中...", JSON.toJSONString(mUpContentJSONModel));
    }
}
