package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import app.net.tongcheng.R;
import app.net.tongcheng.model.CertificationList;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.MSGModel;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ViewHolder;
import okhttp3.Response;

/**
 * Created by 76594 on 2017/7/27.
 */

public class CertificationRecordMoreInfo extends BaseActivity {

    private ViewHolder mViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certification_record_more_info);
        setTitle("认证记录");
        initView();
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main));
    }


    @Override
    public void loadData() {
        MSGModel mMSGModel = NativieDataUtils.getMSGModel();
        if (mMSGModel != null) {
            mViewHolder.setText(R.id.tv_tips, Html.fromHtml(mMSGModel.getCertification_tips()));
        }
        CertificationList.CertificationInfo mCertificationInfo = (CertificationList.CertificationInfo) getIntent().getSerializableExtra(Common.AGR1);
        if (mCertificationInfo != null) {
            mViewHolder.setText(R.id.tv_name, mCertificationInfo.getFull_name());
            mViewHolder.setText(R.id.tv_card_id, mCertificationInfo.getCard_number());
            String process_desc = mCertificationInfo.getProcess_desc();
            process_desc = TextUtils.isEmpty(process_desc) ? "" : "(" + process_desc + ")";
            mViewHolder.setText(R.id.tv_status, getStringFromStata(mCertificationInfo.getProcess_status()) + process_desc);
            Glide.with(this).load(mCertificationInfo.getCard_img1()).into((ImageView) mViewHolder.getView(R.id.iv_card_positive));
            Glide.with(this).load(mCertificationInfo.getCard_img2()).into((ImageView) mViewHolder.getView(R.id.iv_card_other_side));
            Glide.with(this).load(mCertificationInfo.getCard_img3()).into((ImageView) mViewHolder.getView(R.id.iv_synthesis));
        }
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

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mHttpLoadType, ConnectResult mConnectResult) {

    }

    @Override
    public void BusinessOnFail(int mHttpLoadType, Response response) {

    }
}
