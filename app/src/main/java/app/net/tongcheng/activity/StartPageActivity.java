package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.StartPageModel;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ViewHolder;
import okhttp3.Response;

/**
 * Created by 76594 on 2016/5/31.
 */
public class StartPageActivity extends BaseActivity implements View.OnClickListener {
    private ViewHolder mViewHolder;
    private StartPageModel mStartPageModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCheckLoad(false);
        setContentView(R.layout.start_page_layout);
        mStartPageModel = (StartPageModel) getIntent().getSerializableExtra("mStartPageModel");
        initView();
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        mViewHolder.setOnClickListener(R.id.iv_start_image);
    }

    @Override
    public void loadData() {
        if (mStartPageModel != null) {
            mStartPageModel.setTodayShowTimes(mStartPageModel.getTodayShowTimes() + 1);
            NativieDataUtils.setStartPageModel(mStartPageModel);
            mViewHolder.setImage(R.id.iv_start_image, mStartPageModel.getPic_prefix() + mStartPageModel.getPic_xhdpi());
        }
        mHandler.sendEmptyMessageDelayed(10001, 2000);
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                startActivity(new Intent(TCApplication.mContext, MainActivity.class));
                finish();
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {

    }

    @Override
    public void BusinessOnFail(int mLoadType, Response response) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_start_image:
                if (mStartPageModel != null && !TextUtils.isEmpty(mStartPageModel.getTo())) {
                    mHandler.removeMessages(10001);
                    startActivity(new Intent(TCApplication.mContext, MainActivity.class).putExtra("to", mStartPageModel.getTo()));
                    finish();
                }
                break;
        }
    }
}
