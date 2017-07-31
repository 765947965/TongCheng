package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.looppager.BannerView;

import java.util.ArrayList;
import java.util.List;

import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.adapter.LoopBaseAdapter;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.ViewHolder;
import okhttp3.Response;

/**
 * Created by 76594 on 2016/6/17.
 */
public class NewVerTXActivity extends BaseActivity {

    private BannerView mBannerView;
    private List<Integer> ids;
    private boolean isFromStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCheckLoad(false);
        setContentView(R.layout.new_ver_tx_layout);
        mBannerView = (BannerView) findViewById(R.id.mBannerView);
    }

    @Override
    public void loadData() {
        isFromStart = getIntent().getBooleanExtra("isMain", false);
        ids = new ArrayList<>();
        ids.add(R.drawable.tx_spage1);
        mBannerView.setAdapter(new LoopBaseAdapter<Integer>(TCApplication.mContext, ids, R.layout.new_tx_banner) {
            @Override
            public void createView(ViewHolder mViewHolder, final Integer item, List<Integer> mDatas, int position) {
                if (item != null) {
                    mViewHolder.setImage(R.id.banner_top_IV, item);
                    if (position == ids.size() - 1) {
                        mViewHolder.getView(R.id.rlt_main).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isFromStart) {
                                    startActivity(new Intent(TCApplication.mContext, MainActivity.class));
                                }
                                finish();
                            }
                        });
                    } else {
                        mViewHolder.getView(R.id.rlt_main).setOnClickListener(null);
                    }
                }
            }
        }, ids.size(), 1.0d, 2);
        mBannerView.onPause();
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {

    }

    @Override
    public void BusinessOnFail(int mLoadType, Response response) {

    }
}
