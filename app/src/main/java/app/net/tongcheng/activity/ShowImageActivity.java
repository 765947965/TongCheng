package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

import app.net.tongcheng.R;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.ViewHolder;
import okhttp3.Response;

/**
 * Created by 76594 on 2016/6/17.
 */
public class ShowImageActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_iamge_layout);
    }

    @Override
    public void loadData() {
        ViewHolder mViewHolder = new ViewHolder(findViewById(R.id.rlt_main));
        String url = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(url) && url.startsWith("http")) {
            mViewHolder.setImage(R.id.iv_head_image, url);
        }
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
