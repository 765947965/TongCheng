package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import app.net.tongcheng.Business.RedBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.TiXianMoreInfoModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;

/**
 * Created by 76594 on 2016/6/13.
 */
public class TiXianMoreInfo extends BaseActivity implements View.OnClickListener {

    private ViewHolder mViewHolder;
    private RedBusiness mRedBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tixian_more_info);
        setTitle("提现");
        initView();
        mRedBusiness = new RedBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.rlt_main), this);
    }

    @Override
    public void loadData() {
        mRedBusiness.getTiXianInfo(APPCationStation.LOADING, "", getIntent().getStringExtra("id"));
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {
        switch (mLoding_Type) {
            case APPCationStation.LOADING:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    TiXianMoreInfoModel mTiXianMoreInfoModel = (TiXianMoreInfoModel) mConnectResult.getObject();
                    if (mTiXianMoreInfoModel.getData() == null) {
                        ToastUtil.showToast("没有查到详细信息!");
                        return;
                    }
                    TiXianMoreInfoModel.DataBean mDataBean = mTiXianMoreInfoModel.getData();
                    switch (mDataBean.getStatus()) {
                        case "finish"://提现成功
                            mViewHolder.setText(R.id.tv_result_tips, "提现成功");
                            mViewHolder.setText(R.id.tv_money, "-" + mDataBean.getMoney() / 100d + "元");
                            mViewHolder.setText(R.id.iv_banck_card, mDataBean.getLogo_url());
                            mViewHolder.setText(R.id.tv_cardname, "提现至" + mDataBean.getBank_name() + "(" + mDataBean.getBank_card_no().substring(mDataBean.getBank_card_no().length() - 4) + ")");
                            mViewHolder.setText(R.id.tv_to_card_info, "提现至" + mDataBean.getBank_name() + "(" + mDataBean.getBank_card_no().substring(mDataBean.getBank_card_no().length() - 4) + ")");
                            mViewHolder.getView(R.id.view_one).setBackgroundColor(getResources().getColor(R.color.refurush_color));
                            mViewHolder.getView(R.id.view_two).setBackgroundColor(getResources().getColor(R.color.refurush_color));
                            mViewHolder.setImage(R.id.image_one, R.drawable.out_list_info_red_point);
                            mViewHolder.setImage(R.id.image_two, R.drawable.out_list_info_success);
                            String[] times = mDataBean.getAddtime().split(" ");
                            mViewHolder.setText(R.id.tv_time_day_sq, times[0]);
                            mViewHolder.setText(R.id.tv_time_house_sq, times[1]);
                            String[] paytimes = mDataBean.getPay_time().split(" ");
                            mViewHolder.setText(R.id.tv_time_day_banck, paytimes[0]);
                            mViewHolder.setText(R.id.tv_time_house_banck, paytimes[1]);
                            mViewHolder.setText(R.id.tv_banck_status, "银行处理中");
                            String[] finishtimes = mDataBean.getFinish_time().split(" ");
                            mViewHolder.setText(R.id.tv_time_day_result, finishtimes[0]);
                            mViewHolder.setText(R.id.tv_time_house_result, finishtimes[1]);
                            mViewHolder.setText(R.id.tv_result_status, "提现成功");
                            mViewHolder.setText(R.id.tv_to_creat_time, mDataBean.getAddtime());
                            break;
                        case "fail"://提现失败
                            break;
                        case "pail"://已提交至银行
                            break;
                        case "apply"://申请中
                            break;
                    }

                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoding_Type) {
        ToastUtil.showToast("网络不可用,请检查网络连接!");
    }

    @Override
    public void onClick(View v) {

    }
}
