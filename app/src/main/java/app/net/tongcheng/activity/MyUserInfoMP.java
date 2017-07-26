package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;

import org.greenrobot.eventbus.Subscribe;

import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.CheckEvent;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.UserMoreInfoModel;
import app.net.tongcheng.util.ViewHolder;

/**
 * Created by 76594 on 2017/7/25.
 */

public class MyUserInfoMP extends BaseActivity implements View.OnClickListener, TextWatcher {

    private UserMoreInfoModel mUserMoreInfoModel;
    private ViewHolder mViewHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_mp);
        initView();
        setEventBus();
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        mViewHolder.setOnClickListener(R.id.spd_addressrl);
//        ((EditText) mViewHolder.getView(R.id.sdp_companytext)).addTextChangedListener(this);
//        ((EditText) mViewHolder.getView(R.id.sdp_professiontext)).addTextChangedListener(this);
//        ((EditText) mViewHolder.getView(R.id.sdp_schooltext)).addTextChangedListener(this);
    }

    @Override
    public void loadData() {
        mUserMoreInfoModel = (UserMoreInfoModel) getIntent().getSerializableExtra("mUserMoreInfoModel");
        if (!TextUtils.isEmpty(mUserMoreInfoModel.getProvince()) && !TextUtils.isEmpty(mUserMoreInfoModel.getCity())) {
            mViewHolder.setText(R.id.spd_addresstext, mUserMoreInfoModel.getProvince() + mUserMoreInfoModel.getCity());
        } else {
            mViewHolder.setText(R.id.spd_addresstext, "");
        }
        if (!TextUtils.isEmpty(mUserMoreInfoModel.getCompany())) {
            mViewHolder.setText(R.id.sdp_companytext, mUserMoreInfoModel.getCompany());
        } else {
            mViewHolder.setText(R.id.sdp_companytext, "");
        }
        if (!TextUtils.isEmpty(mUserMoreInfoModel.getProfession())) {
            mViewHolder.setText(R.id.sdp_professiontext, mUserMoreInfoModel.getProfession());
        } else {
            mViewHolder.setText(R.id.sdp_professiontext, "");
        }
        if (!TextUtils.isEmpty(mUserMoreInfoModel.getSchool())) {
            mViewHolder.setText(R.id.sdp_schooltext, mUserMoreInfoModel.getSchool());
        } else {
            mViewHolder.setText(R.id.sdp_schooltext, "");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserMoreInfoModel.setCompany(((EditText) mViewHolder.getView(R.id.sdp_companytext)).getText().toString());
        mUserMoreInfoModel.setProfession(((EditText) mViewHolder.getView(R.id.sdp_professiontext)).getText().toString());
        mUserMoreInfoModel.setSchool(((EditText) mViewHolder.getView(R.id.sdp_schooltext)).getText().toString());
        sendEventBusMessage("mUserMoreInfoModel=" + JSON.toJSONString(mUserMoreInfoModel));
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mHttpLoadType, ConnectResult mConnectResult) {

    }

    @Override
    public void BusinessOnFail(int mHttpLoadType) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spd_addressrl:
                startActivity(new Intent(TCApplication.mContext, ChangeProvince.class));
                break;
        }
    }

    @Subscribe
    public void onEvent(CheckEvent event) {
        if (event == null || TextUtils.isEmpty(event.getMsg())) {
            return;
        }
        if (event.getMsg().startsWith("provinceCity=")) {
            String[] vlues = event.getMsg().split("=")[1].split(":");
            mUserMoreInfoModel.setProvince(vlues[0]);
            mUserMoreInfoModel.setCity(vlues[1]);
            if (!TextUtils.isEmpty(mUserMoreInfoModel.getProvince()) && !TextUtils.isEmpty(mUserMoreInfoModel.getCity())) {
                mViewHolder.setText(R.id.spd_addresstext, mUserMoreInfoModel.getProvince() + mUserMoreInfoModel.getCity());
            } else {
                mViewHolder.setText(R.id.spd_addresstext, "");
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (((EditText) mViewHolder.getView(R.id.sdp_companytext)).getText().toString().length() == 0) {
            mViewHolder.setVisibility(R.id.tjsum_comp, View.GONE);
        } else {
            mViewHolder.setText(R.id.tjsum_comp, (20 - ((EditText) mViewHolder.getView(R.id.sdp_companytext)).getText().toString().length()) + "");
            mViewHolder.setVisibility(R.id.tjsum_comp, View.VISIBLE);
        }
        if (((EditText) mViewHolder.getView(R.id.sdp_professiontext)).getText().toString().length() == 0) {
            mViewHolder.setVisibility(R.id.tjsum_profession, View.GONE);
        } else {
            mViewHolder.setText(R.id.tjsum_profession, (20 - ((EditText) mViewHolder.getView(R.id.sdp_professiontext)).getText().toString().length()) + "");
            mViewHolder.setVisibility(R.id.tjsum_profession, View.VISIBLE);
        }
        if (((EditText) mViewHolder.getView(R.id.sdp_schooltext)).getText().toString().length() == 0) {
            mViewHolder.setVisibility(R.id.tjsum_school, View.GONE);
        } else {
            mViewHolder.setText(R.id.tjsum_school, (20 - ((EditText) mViewHolder.getView(R.id.sdp_schooltext)).getText().toString().length()) + "");
            mViewHolder.setVisibility(R.id.tjsum_school, View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
