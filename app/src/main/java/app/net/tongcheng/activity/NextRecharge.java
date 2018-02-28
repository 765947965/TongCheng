package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.alipay.sdk.pay.demo.PayDemoActivity;
import com.umeng.analytics.MobclickAgent;
import com.weixin.paydemo.MD5;
import com.weixin.paydemo.PayActivity;
import com.weixin.paydemo.WXContacts;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import app.net.tongcheng.Business.RedBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.CheckEvent;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.RechargeInfoModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.ErrorInfoUtil;
import app.net.tongcheng.util.Misc;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;
import okhttp3.Response;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/9/9 15:40
 */
public class NextRecharge extends BaseActivity implements View.OnClickListener {
    private static final String WX = "WeiXin";
    private static final String ZFB = "ZhiFuBao";
    private static final String TL = "TLH5";
    private static final String HX = "HXH5";
    private String mCheckNow;
    private ViewHolder mViewHolder;
    private RedBusiness mRedBusiness;
    private RechargeInfoModel.DataBean selectbean;
    private String subject;
    private int rechargeFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_recharge);
        setTitle("账户支付");
        selectbean = (RechargeInfoModel.DataBean) getIntent().getSerializableExtra("RechargeInfoModel.DataBean");
        rechargeFlag = getIntent().getIntExtra("rechargeFlag", 7);
        initView();
        setEventBus();
        mRedBusiness = new RedBusiness(this, this, mHandler);
        if (selectbean == null) {
            finish();
        } else {
            subject = selectbean.getGoodsID().startsWith("6") ? "同城商城充值消费" : "同城商城直充购物";
        }
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.llt_main), this);
        mViewHolder.setOnClickListener(R.id.llt_zfb);
        mViewHolder.setOnClickListener(R.id.llt_wx);
        mViewHolder.setOnClickListener(R.id.llt_tl);
        mViewHolder.setOnClickListener(R.id.llt_hx);
        mViewHolder.setOnClickListener(R.id.bt_recharge);
        if ((rechargeFlag >> 3 & 1) == 0) {
            mViewHolder.setVisibility(R.id.llt_hx, View.GONE);
            mViewHolder.setVisibility(R.id.view_line_hx, View.GONE);
        }
        if ((rechargeFlag >> 2 & 1) == 0) {
            mViewHolder.setVisibility(R.id.llt_zfb, View.GONE);
            mViewHolder.setVisibility(R.id.view_line_zfb, View.GONE);
        }
        if ((rechargeFlag >> 1 & 1) == 0) {
            mViewHolder.setVisibility(R.id.llt_wx, View.GONE);
            mViewHolder.setVisibility(R.id.view_line_wx, View.GONE);
        }
        if ((rechargeFlag & 1) == 0) {
            mViewHolder.setVisibility(R.id.llt_tl, View.GONE);
            mViewHolder.setVisibility(R.id.view_line_tl, View.GONE);
        }
    }


    @Override
    public void loadData() {

    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {
        switch (mLoadType) {
            case APPCationStation.LOADING:
                if (mConnectResult != null && mConnectResult.getObject() != null) {
                    String json = (String) mConnectResult.getObject();
                    try {
                        JSONObject mJson = new JSONObject(json);
                        if (mJson.has("result")) {
                            if (mJson.getInt("result") == 0) {
                                new PayActivity(this, json).Pay();
                            } else {
                                ToastUtil.showToast(ErrorInfoUtil.getErrorMessage(mJson.getInt("result")));
                            }
                        } else {
                            ToastUtil.showToast("解析数据错误");
                        }
                    } catch (Exception e) {
                        ToastUtil.showToast("解析数据错误");
                    }
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoadType, Response response) {
        if (response == null || response.code() != 403) {
            ToastUtil.showToast("网络不可用，请检查网络连接！");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llt_zfb:
                mCheckNow = ZFB;
                mViewHolder.setImage(R.id.ivZFBCheck, R.drawable.check);
                mViewHolder.setImage(R.id.ivWXCheck, R.drawable.uncheck);
                mViewHolder.setImage(R.id.ivTLCheck, R.drawable.uncheck);
                mViewHolder.setImage(R.id.ivHXCheck, R.drawable.uncheck);
                break;
            case R.id.llt_wx:
                mCheckNow = WX;
                mViewHolder.setImage(R.id.ivZFBCheck, R.drawable.uncheck);
                mViewHolder.setImage(R.id.ivWXCheck, R.drawable.check);
                mViewHolder.setImage(R.id.ivTLCheck, R.drawable.uncheck);
                mViewHolder.setImage(R.id.ivHXCheck, R.drawable.uncheck);
                break;
            case R.id.llt_tl:
                mCheckNow = TL;
                mViewHolder.setImage(R.id.ivZFBCheck, R.drawable.uncheck);
                mViewHolder.setImage(R.id.ivWXCheck, R.drawable.uncheck);
                mViewHolder.setImage(R.id.ivTLCheck, R.drawable.check);
                mViewHolder.setImage(R.id.ivHXCheck, R.drawable.uncheck);
                break;
            case R.id.llt_hx:
                mCheckNow = HX;
                mViewHolder.setImage(R.id.ivZFBCheck, R.drawable.uncheck);
                mViewHolder.setImage(R.id.ivWXCheck, R.drawable.uncheck);
                mViewHolder.setImage(R.id.ivTLCheck, R.drawable.uncheck);
                mViewHolder.setImage(R.id.ivHXCheck, R.drawable.check);
                break;
            case R.id.bt_recharge:
                mRecharge();
                break;
        }
    }

    @Subscribe
    public void onEvent(CheckEvent event) {
        if (event != null) {
            if (event.getMsg().startsWith("WX=") || event.getMsg().startsWith("ZFB=")) {
                DialogUtil.showTipsDialog(this, event.getMsg().split("=")[1], new DialogUtil.OnConfirmListener() {
                    @Override
                    public void clickConfirm() {
                        finish();
                    }

                    @Override
                    public void clickCancel() {

                    }
                });
                if (event.getMsg().contains("支付成功")) {
                    Map<String, String> map_value = new HashMap<>();
                    map_value.put("body", subject);
                    map_value.put("price", selectbean.getPrice() / 100d + ":" + Misc.cryptDataByPwd(TCApplication.getmUserInfo().getPhone() + TCApplication.getmUserInfo().getPwd()));
                    MobclickAgent.onEventValue(this, "recharge", map_value, Double.valueOf(selectbean.getPrice() / 100d).intValue());
                }
            }
        }
    }

    private void mRecharge() {
        if (rechargeFlag >= 0 && TextUtils.isEmpty(mCheckNow)) {
            ToastUtil.showToast("请选择支付方式");
        } else if (ZFB.equals(mCheckNow)) {
            new PayDemoActivity(this, subject, subject, selectbean.getPrice() / 100d + "").pay();
        } else if (WX.equals(mCheckNow)) {
            mRedBusiness.getWeiXinXiaDan(APPCationStation.LOADING, "生成订单...", genProductArgs());
        } else if (TL.equals(mCheckNow)) {
            String url = "http://user.zjtongchengshop.com:8060/allinpay/payapi.php?uid=" + TCApplication.getmUserInfo().getUid()
                    + "&goods_id=" + selectbean.getGoodsID();
            startActivity(new Intent(TCApplication.mContext, PublicWebview.class).putExtra("url", url));
        } else if (HX.equals(mCheckNow)) {
            String url = "http://user.zjtongchengshop.com/ips/payapi.php?uid=" + TCApplication.getmUserInfo().getUid()
                    + "&goods_id=" + selectbean.getGoodsID();
            startActivity(new Intent(TCApplication.mContext, PublicWebview.class).putExtra("url", url));
        }
    }

    private String genProductArgs() {
        StringBuffer xml = new StringBuffer();

        try {
            String nonceStr = genNonceStr();

            xml.append("</xml>");
            Map<String, String> maps = new HashMap<>();
            maps.put("appid", WXContacts.APP_ID);
            maps.put("body", subject);// 商品简述
            maps.put("product_id", selectbean.getGoodsID());
            // packageParams.add(new BasicNameValuePair("mch_id",
            // WXContacts.MCH_ID));
            maps.put("nonce_str", nonceStr);
            // packageParams.add(new BasicNameValuePair("notify_url",
            // WXContacts.NOTIFY_URL));
            // packageParams.add(new BasicNameValuePair("out_trade_no",
            // genOutTradNo()));
            maps.put("spbill_create_ip", "8.8.8.8");
            maps.put("total_fee", selectbean.getPrice() * 100 + "");
            maps.put("trade_type", "APP");
            // String sign = genPackageSign(packageParams);
            // packageParams.add(new BasicNameValuePair("sign", sign));

            String xmlstring = toXml(maps);

            return "uid=" + TCApplication.getmUserInfo().getUid() + "&xml="
                    + xmlstring;

        } catch (Exception e) {
            return null;
        }

    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
                .getBytes());
    }

    private String toXml(Map<String, String> maps) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (Map.Entry<String, String> item : maps.entrySet()) {
            sb.append("<" + item.getKey() + ">");
            sb.append(item.getValue());
            sb.append("</" + item.getKey() + ">");
        }
        sb.append("</xml>");

        return sb.toString();
    }
}
