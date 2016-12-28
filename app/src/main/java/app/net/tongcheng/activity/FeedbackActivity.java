package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.net.tongcheng.Business.MyBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;

/**
 * Created by 76594 on 2016/6/11.
 */
public class FeedbackActivity extends BaseActivity implements View.OnClickListener, TextWatcher, OnItemClickListener {

    private ViewHolder mViewHolder;
    private MyBusiness mMyBusiness;
    private LinearLayout removelayout;
    private TextView titleinput;
    private EditText spd_cn_inputqianming;
    private TextView sendfeedback;
    private ArrayList<String> items;
    private ListView listview;
    private boolean showd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedbackactivity);
        setTitle("用户反馈");
        initView();
        mMyBusiness = new MyBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        removelayout = mViewHolder.getView(R.id.llt_main);
        titleinput = mViewHolder.getView(R.id.titleinput);
        spd_cn_inputqianming = mViewHolder.getView(R.id.spd_cn_inputqianming);
        sendfeedback = mViewHolder.getView(R.id.sendfeedback);
        listview = mViewHolder.getView(R.id.listview);
        spd_cn_inputqianming.addTextChangedListener(this);
        sendfeedback.setOnClickListener(this);
        titleinput.setOnClickListener(this);
        removelayout.setOnClickListener(this);
    }

    @Override
    public void loadData() {
        items = new ArrayList<>();
        items.add("一、App使用异常（如闪退、卡顿、死机等）");
        items.add("二、生活页面问题（如无法显示，无法跳转，跳转异常等）");
        items.add("三、充值、提现问题 (如无法充值、充值不到账、提现不正常等)");
        items.add("四、返利问题(充值返利未收到、邀请朋友充值后的返利未收到等)");
        items.add("五、其他问题");
        items.add("六、意见建议");
        titleinput.setText(items.get(0));
        List<Map<String, Object>> listdata = new ArrayList<>();
        for (String itemtext : items) {
            Map<String, Object> map = new HashMap<>();
            map.put("itemtext", itemtext);
            listdata.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, listdata,
                R.layout.spinneritem, new String[]{"itemtext"},
                new int[]{R.id.itemtext});
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
        listview.setVisibility(View.GONE);
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {
        switch (mLoadType) {
            case APPCationStation.SUMBIT:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    DialogUtil.showTipsDialog(this, "提交成功!", null);
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoadType) {
        ToastUtil.showToast("网络不可用,请检查网络连接!");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sendfeedback) {
            // 上传问题
            mMyBusiness.sendProB(APPCationStation.SUMBIT, "提交中...", titleinput.getText().toString(), spd_cn_inputqianming.getText().toString());
        } else if (id == R.id.titleinput) {
            listview.setVisibility(View.VISIBLE);
            anim(listview, 200, -1f, 0);
            showd = true;
        } else if (id == R.id.llt_main) {
            if (showd) {
                showd = false;
                anim(listview, 200, 0, -1f);
                listview.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if ("".equals(spd_cn_inputqianming.getText().toString())) {
            sendfeedback.setEnabled(false);
        } else {
            sendfeedback.setEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        titleinput.setText(items.get(position));
        if (showd) {
            showd = false;
            anim(listview, 200, 0, -1f);
            listview.setVisibility(View.GONE);
        }
    }

    private void anim(View v, int time, float fromy, float toy) {
        AnimationSet animup = new AnimationSet(true);
        TranslateAnimation mup0 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, fromy, Animation.RELATIVE_TO_SELF,
                toy);
        mup0.setDuration(time);
        animup.addAnimation(mup0);
        v.startAnimation(animup);
    }
}
