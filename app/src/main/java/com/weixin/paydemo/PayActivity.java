package com.weixin.paydemo;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import android.app.Activity;
import android.util.Xml;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;


import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.xmlpull.v1.XmlPullParser;


public class PayActivity {
    private Activity mActivity;
    private PayReq req;
    private IWXAPI msgApi;
    private Map<String, String> resultunifiedorder;
    private StringBuffer sb;
    private String mJson;

    public PayActivity(Activity mActivity, String mJson) {
        this.mActivity = mActivity;
        this.mJson = mJson;
        msgApi = WXAPIFactory.createWXAPI(mActivity, null);
        req = new PayReq();
        sb = new StringBuffer();
        msgApi.registerApp(WXContacts.APP_ID);
    }

    public void Pay() {
        try {
            JSONObject json = new JSONObject(mJson);
            Map<String, String> xml = decodeXml(genMyxml(json));
            sb.append("prepay_id\n" + xml.get("prepay_id") + "\n\n");
            resultunifiedorder = xml;
            // 生成签名参数
            genPayReq();
            // 支付
            sendPayReq();
            String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(WXContacts.API_KEY);

        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return appSign;
    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");

            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        return sb.toString();
    }

    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            // 实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
        }
        return null;

    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
                .getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    private String genOutTradNo() {
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
        Date date = new Date();
        String strKey = format.format(date);
        Random r = new Random();
        strKey = strKey + Math.abs(r.nextInt());
        strKey = strKey.substring(0, 17);
        strKey = strKey + "629502";
        return strKey;
    }

    private String genMyxml(JSONObject json) {
        try {
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("return_code", "<![CDATA["
                    + json.optString("return_code") + "]]>"));
            packageParams.add(new BasicNameValuePair("return_msg", "<![CDATA["
                    + json.optString("return_msg") + "]]>"));
            packageParams.add(new BasicNameValuePair("appid", "<![CDATA["
                    + WXContacts.APP_ID + "]]>"));
            packageParams.add(new BasicNameValuePair("mch_id", "<![CDATA["
                    + json.optString("mch_id") + "]]>"));
            packageParams.add(new BasicNameValuePair("nonce_str", "<![CDATA["
                    + json.optString("nonceStr") + "]]>"));
            packageParams.add(new BasicNameValuePair("sign", "<![CDATA["
                    + json.optString("paySign") + "]]>"));
            packageParams.add(new BasicNameValuePair("result_code", "<![CDATA["
                    + json.optString("result_code") + "]]>"));
            packageParams.add(new BasicNameValuePair("prepay_id", "<![CDATA["
                    + json.optString("prepay_id") + "]]>"));
            packageParams.add(new BasicNameValuePair("trade_type",
                    "<![CDATA[APP]]>"));
            String xmlstring = toXml(packageParams);
            return xmlstring;
        } catch (Exception e) {
            return null;
        }
    }

    private void genPayReq() {

        req.appId = WXContacts.APP_ID;
        req.partnerId = WXContacts.MCH_ID;
        req.prepayId = resultunifiedorder.get("prepay_id");
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);

        sb.append("sign\n" + req.sign + "\n\n");


    }

    private void sendPayReq() {

        msgApi.registerApp(WXContacts.APP_ID);
        msgApi.sendReq(req);
    }

}
