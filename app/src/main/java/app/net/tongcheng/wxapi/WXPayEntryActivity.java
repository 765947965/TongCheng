package app.net.tongcheng.wxapi;


import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.weixin.paydemo.WXContacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import app.net.tongcheng.util.DialogUtil;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, WXContacts.APP_ID);

        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            String message = "";
            if (resp.errCode == 0) {
                message = "支付成功,请稍后查询余额！";
            } else if (resp.errCode == -1) {
                message = "支付失败！";
            } else if (resp.errCode == -2) {
                message = "交易取消！";
            }
            DialogUtil.showTipsDialog(this, message, new DialogUtil.OnConfirmListener() {
                @Override
                public void clickConfirm() {
                    finish();
                }

                @Override
                public void clickCancel() {

                }
            });
        }
    }
}