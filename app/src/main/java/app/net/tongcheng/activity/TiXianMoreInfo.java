package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

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
        mViewHolder = new ViewHolder(findViewById(R.id.ssl_main), this);
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
                            if (mDataBean.getStatus().equals("finish")) {
                                mViewHolder.setText(R.id.tv_result_tips, "提现成功");
                                mViewHolder.setText(R.id.tv_money, "-" + mDataBean.getMoney() / 100d);
                                mViewHolder.setImage(R.id.iv_banck_card, mDataBean.getLogo_url());
                                mViewHolder.setText(R.id.tv_cardname, "提现至" + mDataBean.getBank_name() + "(" + mDataBean.getBank_card_no().substring(mDataBean.getBank_card_no().length() - 4) + ")");
                                mViewHolder.setText(R.id.tv_to_card_info, "提现至" + mDataBean.getBank_name() + "(" + mDataBean.getBank_card_no().substring(mDataBean.getBank_card_no().length() - 4) + ")");
                                mViewHolder.getView(R.id.view_one).setBackgroundColor(getResources().getColor(R.color.refurush_color));
                                mViewHolder.getView(R.id.view_two).setBackgroundColor(getResources().getColor(R.color.refurush_color));
                                mViewHolder.setImage(R.id.image_one, R.drawable.out_list_info_red_point);
                                mViewHolder.setImage(R.id.image_two, R.drawable.out_list_info_success);
                                mViewHolder.setText(R.id.tv_banck_status, "银行处理中");
                                mViewHolder.setText(R.id.tv_result_status, "提现成功");
                                mViewHolder.setText(R.id.tv_to_creat_time, mDataBean.getAddtime());
                                String[] times = mDataBean.getAddtime().split(" ");
                                mViewHolder.setText(R.id.tv_time_day_sq, times[0]);
                                mViewHolder.setText(R.id.tv_time_house_sq, times[1]);
                                String[] paytimes = mDataBean.getPay_time().split(" ");
                                mViewHolder.setText(R.id.tv_time_day_banck, paytimes[0]);
                                mViewHolder.setText(R.id.tv_time_house_banck, paytimes[1]);
                                String[] finishtimes = mDataBean.getFinish_time().split(" ");
                                mViewHolder.setText(R.id.tv_time_day_result, finishtimes[0]);
                                mViewHolder.setText(R.id.tv_time_house_result, finishtimes[1]);
                            }
                            break;
                        case "fail"://提现失败
                            if (mDataBean.getStatus().equals("fail")) {
                                mViewHolder.setText(R.id.tv_result_tips, "提现失败");
                                mViewHolder.setText(R.id.tv_money, "-" + mDataBean.getMoney() / 100d);
                                mViewHolder.setText(R.id.tv_error_info, mDataBean.getReason());
                                mViewHolder.setImage(R.id.iv_banck_card, mDataBean.getLogo_url());
                                mViewHolder.setText(R.id.tv_cardname, "提现至" + mDataBean.getBank_name() + "(" + mDataBean.getBank_card_no().substring(mDataBean.getBank_card_no().length() - 4) + ")");
                                mViewHolder.setText(R.id.tv_to_card_info, "提现至" + mDataBean.getBank_name() + "(" + mDataBean.getBank_card_no().substring(mDataBean.getBank_card_no().length() - 4) + ")");
                                mViewHolder.getView(R.id.view_one).setBackgroundColor(getResources().getColor(R.color.refurush_color));
                                mViewHolder.getView(R.id.view_two).setBackgroundColor(getResources().getColor(R.color.refurush_color));
                                mViewHolder.setImage(R.id.image_one, R.drawable.out_list_info_red_point);
                                mViewHolder.setImage(R.id.image_two, R.drawable.out_list_info_error);
                                mViewHolder.setText(R.id.tv_banck_status, "银行处理中");
                                mViewHolder.setText(R.id.tv_result_status, "提现失败");
                                mViewHolder.setText(R.id.tv_to_creat_time, mDataBean.getAddtime());
                                String[] times = mDataBean.getAddtime().split(" ");
                                mViewHolder.setText(R.id.tv_time_day_sq, times[0]);
                                mViewHolder.setText(R.id.tv_time_house_sq, times[1]);
                                String[] paytimes = mDataBean.getPay_time().split(" ");
                                mViewHolder.setText(R.id.tv_time_day_banck, paytimes[0]);
                                mViewHolder.setText(R.id.tv_time_house_banck, paytimes[1]);
                                String[] finishtimes = mDataBean.getFinish_time().split(" ");
                                mViewHolder.setText(R.id.tv_time_day_result, finishtimes[0]);
                                mViewHolder.setText(R.id.tv_time_house_result, finishtimes[1]);
                            }
                            break;
                        case "paid"://已提交至银行
                            if (mDataBean.getStatus().equals("paid")) {
                                mViewHolder.setText(R.id.tv_result_tips, "银行处理中");
                                mViewHolder.setText(R.id.tv_money, "-" + mDataBean.getMoney() / 100d);
                                mViewHolder.setImage(R.id.iv_banck_card, mDataBean.getLogo_url());
                                mViewHolder.setText(R.id.tv_cardname, "提现至" + mDataBean.getBank_name() + "(" + mDataBean.getBank_card_no().substring(mDataBean.getBank_card_no().length() - 4) + ")");
                                mViewHolder.setText(R.id.tv_to_card_info, "提现至" + mDataBean.getBank_name() + "(" + mDataBean.getBank_card_no().substring(mDataBean.getBank_card_no().length() - 4) + ")");
                                mViewHolder.getView(R.id.view_one).setBackgroundColor(getResources().getColor(R.color.refurush_color));
                                mViewHolder.getView(R.id.view_two).setBackgroundColor(getResources().getColor(R.color.refurush_gray));
                                mViewHolder.setImage(R.id.image_one, R.drawable.out_list_info_red_point);
                                mViewHolder.setImage(R.id.image_two, R.drawable.out_list_info_gerd_point);
                                mViewHolder.setText(R.id.tv_banck_status, "银行处理中");
                                mViewHolder.setText(R.id.tv_result_status, "提现结果");
                                mViewHolder.setText(R.id.tv_to_creat_time, mDataBean.getAddtime());
                                ((TextView) mViewHolder.getView(R.id.tv_result_status)).setTextColor(getResources().getColor(R.color.remark_tips));
                                String[] times = mDataBean.getAddtime().split(" ");
                                mViewHolder.setText(R.id.tv_time_day_sq, times[0]);
                                mViewHolder.setText(R.id.tv_time_house_sq, times[1]);
                                if (!TextUtils.isEmpty(mDataBean.getPay_time())) {
                                    String[] paytimes = mDataBean.getPay_time().split(" ");
                                    mViewHolder.setText(R.id.tv_time_day_banck, paytimes[0]);
                                    mViewHolder.setText(R.id.tv_time_house_banck, paytimes[1]);
                                }
                                if (!TextUtils.isEmpty(mDataBean.getFinish_time())) {
                                    String[] finishtimes = mDataBean.getFinish_time().split(" ");
                                    mViewHolder.setText(R.id.tv_time_day_result, finishtimes[0]);
                                    mViewHolder.setText(R.id.tv_time_house_result, finishtimes[1]);
                                }
                            }
                            break;
                        case "apply"://申请中
                            if (mDataBean.getStatus().equals("apply")) {
                                mViewHolder.setText(R.id.tv_result_tips, "提交申请中");
                                mViewHolder.setText(R.id.tv_money, "-" + mDataBean.getMoney() / 100d);
                                mViewHolder.setImage(R.id.iv_banck_card, mDataBean.getLogo_url());
                                mViewHolder.setText(R.id.tv_cardname, "提现至" + mDataBean.getBank_name() + "(" + mDataBean.getBank_card_no().substring(mDataBean.getBank_card_no().length() - 4) + ")");
                                mViewHolder.setText(R.id.tv_to_card_info, "提现至" + mDataBean.getBank_name() + "(" + mDataBean.getBank_card_no().substring(mDataBean.getBank_card_no().length() - 4) + ")");
                                mViewHolder.getView(R.id.view_one).setBackgroundColor(getResources().getColor(R.color.refurush_gray));
                                mViewHolder.getView(R.id.view_two).setBackgroundColor(getResources().getColor(R.color.refurush_gray));
                                mViewHolder.setImage(R.id.image_one, R.drawable.out_list_info_gerd_point);
                                mViewHolder.setImage(R.id.image_two, R.drawable.out_list_info_gerd_point);
                                mViewHolder.setText(R.id.tv_banck_status, "银行处理中");
                                mViewHolder.setText(R.id.tv_result_status, "提现结果");
                                mViewHolder.setText(R.id.tv_to_creat_time, mDataBean.getAddtime());
                                ((TextView) mViewHolder.getView(R.id.tv_banck_status)).setTextColor(getResources().getColor(R.color.remark_tips));
                                ((TextView) mViewHolder.getView(R.id.tv_result_status)).setTextColor(getResources().getColor(R.color.remark_tips));
                                String[] times = mDataBean.getAddtime().split(" ");
                                mViewHolder.setText(R.id.tv_time_day_sq, times[0]);
                                mViewHolder.setText(R.id.tv_time_house_sq, times[1]);
                                if (!TextUtils.isEmpty(mDataBean.getPay_time())) {
                                    String[] paytimes = mDataBean.getPay_time().split(" ");
                                    mViewHolder.setText(R.id.tv_time_day_banck, paytimes[0]);
                                    mViewHolder.setText(R.id.tv_time_house_banck, paytimes[1]);
                                }
                                if (!TextUtils.isEmpty(mDataBean.getFinish_time())) {
                                    String[] finishtimes = mDataBean.getFinish_time().split(" ");
                                    mViewHolder.setText(R.id.tv_time_day_result, finishtimes[0]);
                                    mViewHolder.setText(R.id.tv_time_house_result, finishtimes[1]);
                                }
                            }
                            break;
                    }
                    mViewHolder.setText(R.id.tvTXTips, TextUtils.isEmpty(mDataBean.getTips()) ? "" : mDataBean.getTips());
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
