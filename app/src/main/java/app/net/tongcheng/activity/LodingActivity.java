package app.net.tongcheng.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import app.net.tongcheng.Business.OtherBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.adapter.R_Loding4v2_SPLIST;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.CheckEvent;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.OraLodingUser;
import app.net.tongcheng.model.StartPageModel;
import app.net.tongcheng.model.UserInfo;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.OperationUtils;
import app.net.tongcheng.util.OraLodingUserTools;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.UserDateUtils;
import app.net.tongcheng.util.Utils;
import app.net.tongcheng.util.ViewHolder;
import app.net.tongcheng.view.LineEditText;
import app.net.tongcheng.view.OnListnerShearch;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/5/21 10:26
 */
public class LodingActivity extends BaseActivity implements View.OnClickListener, TextWatcher, OnListnerShearch, AdapterView.OnItemClickListener {

    private ViewHolder mViewHolder;
    private List<OraLodingUser> oldus;
    private ArrayList<OraLodingUser> oldus_showlist = new ArrayList<>();
    private R_Loding4v2_SPLIST rl4v2sadapter;
    private LineEditText r_loding4v2_pwd;
    private EditText r_loding4v2_phnum;
    private TextView r_loding4v2_lodingbt;
    private ListView lplist;
    private OtherBusiness mOtherBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loding_layout);
        setTitle("登录");
        initView();
        setEventBus();
        setChechLoding(false);
        mOtherBusiness = new OtherBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.flt_main), this);
        r_loding4v2_pwd = mViewHolder.getView(R.id.r_loding4v2_pwd);
        r_loding4v2_pwd.setShearchListner(this);
        r_loding4v2_phnum = mViewHolder.getView(R.id.r_loding4v2_phnum);
        r_loding4v2_phnum.addTextChangedListener(this);
        r_loding4v2_lodingbt = mViewHolder.getView(R.id.r_loding4v2_lodingbt);
        lplist = mViewHolder.getView(R.id.lplist);
        lplist.setOnItemClickListener(this);
        mViewHolder.setOnClickListener(R.id.rl4v2_clearpnum);
        mViewHolder.setOnClickListener(R.id.r_loding4v2_getpassword);
        r_loding4v2_lodingbt.setOnClickListener(this);
    }

    private void setData() {
        oldus = OraLodingUserTools.getolus(this);
        if (oldus == null) {
            oldus = new ArrayList<>();
        }
        if (oldus.size() > 0) {
            r_loding4v2_phnum.setText(oldus.get(0).getPhonenum());
            r_loding4v2_phnum.setSelection(r_loding4v2_phnum.getText().toString().length());
        }
    }


    @Override
    public void loadData() {
        setData();
        Utils.setInputMethodVisiable(r_loding4v2_phnum, 250);
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        if (msg.what == 101) {
            if (rl4v2sadapter == null) {
                rl4v2sadapter = new R_Loding4v2_SPLIST(this,
                        oldus_showlist);
                lplist.setAdapter(rl4v2sadapter);
            } else {
                rl4v2sadapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.SUMBIT:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    UserInfo mUserInfo = (UserInfo) mConnectResult.getObject();
                    TCApplication.setmUserInfo(mUserInfo);
                    OraLodingUserTools.addolus(TCApplication.mContext, new OraLodingUser(mUserInfo.getPhone(), System.currentTimeMillis()));
                    DialogUtil.showTipsDialog(this, "登录成功!", new DialogUtil.OnConfirmListener() {
                        @Override
                        public void clickConfirm() {
                            LodingActivity.this.sendEventBusMessage("loading_ok");
                            boolean isStartTX = UserDateUtils.getBoolean(Utils.getVersionName());
                            if (!isStartTX) {
                                // 开启新特性
                                UserDateUtils.PutBoolean(Utils.getVersionName(), true);
                                startActivity(new Intent(TCApplication.mContext, NewVerTXActivity.class).putExtra("isMain", true));
                            } else {
                                // 开启主页
                                LodingActivity.this.startActivity(new Intent(TCApplication.mContext, MainActivity.class));
                            }
                            LodingActivity.this.finish();
                        }

                        @Override
                        public void clickCancel() {

                        }
                    });
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoding_Type) {
        ToastUtil.showToast("网络不可用，请检查网络连接！");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl4v2_clearpnum:
                r_loding4v2_phnum.setText("");
                break;
            case R.id.r_loding4v2_lodingbt://登录
                mOtherBusiness.loadingBusiness(APPCationStation.SUMBIT, "登录中", r_loding4v2_phnum.getText().toString(), r_loding4v2_pwd.getText().toString());
                break;
            case R.id.r_loding4v2_getpassword://忘记密码
                startActivity(new Intent(TCApplication.mContext, SetRetrievePassword.class));
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().isEmpty()) {
            mViewHolder.setVisibility(R.id.rl4v2_clearpnum, View.INVISIBLE);
            oldus_showlist.clear();
            oldus_showlist.addAll(oldus);
            mViewHolder.setVisibility(R.id.lplist, View.VISIBLE);
            mHandler.sendEmptyMessage(101);
        } else if (s.toString().length() > 0 && s.toString().length() < 11) {
            mViewHolder.setVisibility(R.id.rl4v2_clearpnum, View.VISIBLE);
            oldus_showlist.clear();
            for (int i = 0; i < oldus.size(); i++) {
                if (oldus.get(i).getPhonenum().startsWith(s.toString())) {
                    oldus_showlist.add(oldus.get(i));
                }
            }
            mViewHolder.setVisibility(R.id.lplist, View.VISIBLE);
            mHandler.sendEmptyMessage(101);
        } else {
            mViewHolder.setVisibility(R.id.rl4v2_clearpnum, View.VISIBLE);
            oldus_showlist.clear();
            mViewHolder.setVisibility(R.id.lplist, View.GONE);
            mHandler.sendEmptyMessage(101);
            Utils.setInputMethodVisiable(r_loding4v2_pwd, 250);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void Search(String text) {
        if (r_loding4v2_phnum.getText().toString().trim().length() == 11
                && r_loding4v2_pwd.getText().toString().trim().length() > 0) {
            r_loding4v2_lodingbt.setEnabled(true);
        } else {
            r_loding4v2_lodingbt.setEnabled(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        mViewHolder.setVisibility(R.id.lplist, View.GONE);
        r_loding4v2_phnum.setText(oldus_showlist.get(position).getPhonenum());
        r_loding4v2_phnum.setSelection(r_loding4v2_phnum.getText().toString().length());
    }

    @Subscribe
    public void onEvent(CheckEvent event) {
        if (event != null && event.getMsg().equals("loading_ok")) {
            finish();
        }
    }
}
