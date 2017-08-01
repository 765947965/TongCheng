package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.net.tongcheng.Business.MyBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.adapter.MyBaseAdapter;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.CertificationList;
import app.net.tongcheng.model.CheckEvent;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.OperationUtils;
import app.net.tongcheng.util.ViewHolder;
import okhttp3.Response;

/**
 * Created by 76594 on 2017/7/26.
 */

public class CertificationRecord extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    private MyBusiness mMyBusiness;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView listView;
    private List<CertificationList.CertificationInfo> certificationList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certification_record);
        setTitle("认证记录");
        initView();
        setEventBus();
        mMyBusiness = new MyBusiness(this, this, mHandler);
    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSwipeRefreshLayout);
        listView = (ListView) findViewById(R.id.listView);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refurush_color);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void loadData() {
        if (!OperationUtils.getBoolean(OperationUtils.hadCertification, true)) {
            getRightBtn().setVisibility(View.VISIBLE);
            getRightBtn().setText("提交认证");
            getRightBtn().setOnClickListener(this);
        }
        mHandler.sendEmptyMessageDelayed(10001, 200);
        mHandler.sendEmptyMessageDelayed(10002, 2000);
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                mSwipeRefreshLayout.setRefreshing(true);
                mMyBusiness.getCertificationInfo(APPCationStation.LOADING, "");
                break;
            case 10002:
                mMyBusiness.queryCertificationStatus(APPCationStation.CHECK, "");
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mHttpLoadType, ConnectResult mConnectResult) {
        switch (mHttpLoadType) {
            case APPCationStation.CHECK:
                try {
                    String jsonStr = (String) mConnectResult.getObject();
                    JSONObject json = new JSONObject(jsonStr);
                    if (json.getInt("result") == 41) {
                        boolean oldFlag = OperationUtils.getBoolean(OperationUtils.hadCertification, true);
                        OperationUtils.PutBoolean(OperationUtils.hadCertification, true, true);
                        getRightBtn().setVisibility(View.GONE);
                        if (!oldFlag) {
                            sendEventBusMessage("MyFragment.Refresh");
                        }
                    } else if (json.getInt("result") == 42) {
                        boolean oldFlag = OperationUtils.getBoolean(OperationUtils.hadCertification, true);
                        OperationUtils.PutBoolean(OperationUtils.hadCertification, false, true);
                        getRightBtn().setText("提交认证");
                        getRightBtn().setOnClickListener(this);
                        if (oldFlag) {
                            sendEventBusMessage("MyFragment.Refresh");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case APPCationStation.LOADING:
                mSwipeRefreshLayout.setRefreshing(false);
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    CertificationList mCertificationList = (CertificationList) mConnectResult.getObject();
                    if (mCertificationList.getCertification_list() != null && mCertificationList.getCertification_list().size() > 0) {
                        certificationList.clear();
                        certificationList.addAll(mCertificationList.getCertification_list());
                        if (listView.getAdapter() == null) {
                            listView.setAdapter(new MyBaseAdapter<CertificationList.CertificationInfo>(listView, CertificationRecord.this, certificationList, R.layout.certification_list) {
                                @Override
                                protected void convert(ViewHolder holder, CertificationList.CertificationInfo item, List<CertificationList.CertificationInfo> list, int position) {
                                    if (item != null) {
                                        holder.setText(R.id.tv_name, "姓名： " + item.getFull_name());
                                        holder.setText(R.id.tv_card_id, "身份证号码： " + item.getCard_number());
                                        holder.setText(R.id.tv_create_time, "提交时间： " + item.getCreate_time());
                                        holder.setText(R.id.process_time, "审核时间： " + (TextUtils.isEmpty(item.getProcess_time()) ? "" : item.getProcess_time()));
                                        holder.setText(R.id.tv_process_status, getStringFromStata(item.getProcess_status()));
                                        if (TextUtils.isEmpty(item.getProcess_desc())) {
                                            holder.setVisibility(R.id.tv_process_desc, View.GONE);
                                        } else {
                                            holder.setVisibility(R.id.tv_process_desc, View.VISIBLE);
                                            holder.setText(R.id.tv_process_desc, "审核说明： " + item.getProcess_desc());
                                        }
                                    }
                                }

                                @Override
                                protected void MyonItemClick(AdapterView<?> parent, View view, CertificationList.CertificationInfo item, List<CertificationList.CertificationInfo> list, int position, long id) {
                                    startActivity(new Intent(TCApplication.mContext, CertificationRecordMoreInfo.class).putExtra(Common.AGR1, item));
                                }

                                private String getStringFromStata(String process_status) {
                                    if ("prepare".equals(process_status)) {
                                        return "待审核";
                                    } else if ("failed".equals(process_status)) {
                                        return "审核不通过";
                                    } else if ("pass".equals(process_status)) {
                                        return "审核通过";
                                    } else {
                                        return "";
                                    }
                                }
                            });
                        } else {
                            ((ArrayAdapter) listView.getAdapter()).notifyDataSetChanged();
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mHttpLoadType, Response response) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRight:
                startActivity(new Intent(this, SubmitCertification.class));
                break;
            case APPCationStation.LOADING:
                mSwipeRefreshLayout.setRefreshing(false);
                break;
        }
    }

    @Subscribe
    public void onEvent(CheckEvent event) {
        if (event != null) {
            switch (event.getMsg()) {
                case "CertificationRecord.Refresh"://刷新列表
                    onRefresh();
                    break;
            }
        }
    }

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(10001, 200);
    }
}
