package com.alipay.sdk.pay.demo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alipay.sdk.app.PayTask;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import app.net.tongcheng.TCApplication;
import app.net.tongcheng.util.DialogUtil;

public class PayDemoActivity {

    public PayDemoActivity(Activity mActivity, String subject, String body, String price) {
        this.mActivity = mActivity;
        this.subject = subject;
        this.body = body;
        this.price = price;
    }


    private Activity mActivity;
    private String subject;
    private String body;
    private String price;


    // 商户PID
    public static final String PARTNER = "2088121327291341";
    // 商户收款账号
    public static final String SELLER = "tongchengtx@sina.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMnFFe6SNX+FKTKPvgOFpXWTUMMjVuqvZrzeEBLzG66HBFiK+2052GVN1qyc5Muqy0CzK2pYo+C/P/5XqcWigLdhtxjoVhR8K3HTvDuPmrcTsvq0OAKabULmcAk01nsFJbpMVkvkPmLJE4c1tpC9PowH4jhaXbgI9Fk2aXH6rNYpAgMBAAECgYBKwRtcFY1+nn5h/kGfGm/v/NGKStiUAMJmrTt1Wd7iraFdkLiQgkL7XXhw4XwfPTsq0HcAYrDsvs7d0+rRj2ByaDzMGXcTPfUvCgxh4SgHMJj1yZvFze5+hUTf2tBkKCaQr31DAzrSv8/YEsTI1v4Dt3+fSwAvbfHmevEb9TuMJQJBAO+AADp6gHZ3MuPN/zDoKDTBcnodXZjyRAXTC4Hg+KWmeMuDxNuuImy0y20EWIgM3wsZ2Qkq73YlluXoImwK5OMCQQDXq6XvOU+AZB4M64bnokPhgQAQ17DT7jpwRWLVO4GLgedpKfe5izOQsJeipa1ggdQQ1RRA31cC02+NRSGQY1KDAkEA4pXUIX9SWEHvmIyEuY16tGasWpG7wn66ElSXl3nzZCz6LXjt3vSBRx1JNEufQqACyOrcZgsD4GAxwjN7lYI9BwJATq/Sp9hqGDbu+9nG66Y5TAJL6tk3K+ukKKg4KgI+/o5TxvvH5UtTcfvsJyx5eFeF7uo/LHgP//jyn0FUwKBsTwJBAOYXezablWx5onfvGvmRP68qNDH9ilYFsM2wPzUMzgIOXiLyaAnw1cSmStkaxhs4w5XyBBG19KwrffUVut10G/I=";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
    // 支付宝支付成功 回调地址
    public final String notify_url = "http://user.8hbao.com:8060/AliSecurity/notify_url.php";

    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        DialogUtil.showTipsDialog(mActivity, "支付成功,请稍后查询余额！", null);
                        Map<String, String> map_value = new HashMap<>();
                        map_value.put("body", body);
                        map_value.put("price", price);
                        MobclickAgent.onEventValue(mActivity, "recharge", map_value, Double.valueOf(price).intValue());
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            DialogUtil.showTipsDialog(mActivity, "支付结果确认中", null);
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            DialogUtil.showTipsDialog(mActivity, "支付失败", null);
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay() {
        String orderInfo = getOrderInfo(subject, body, price);

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(mActivity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(mActivity);
        String version = payTask.getVersion();
        Toast.makeText(mActivity, version, Toast.LENGTH_SHORT).show();
    }


    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + notify_url + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
        Date date = new Date();
        String strKey = format.format(date);
        java.util.Random r = new java.util.Random();
        strKey = strKey + Math.abs(r.nextInt());
        strKey = strKey.substring(0, 17);
        strKey = strKey + TCApplication.getmUserInfo().getUid();
        return strKey;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

}
