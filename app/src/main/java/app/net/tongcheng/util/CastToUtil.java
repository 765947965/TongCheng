package app.net.tongcheng.util;

import android.content.Intent;

import app.net.tongcheng.TCApplication;
import app.net.tongcheng.activity.AccountSetActivity;
import app.net.tongcheng.activity.BalanceActivity;
import app.net.tongcheng.activity.PublicWebview;
import app.net.tongcheng.activity.ReChargeActivity;
import app.net.tongcheng.activity.RedListActivity;
import app.net.tongcheng.activity.ShareActivity;

/**
 * Created by 76594 on 2016/6/14.
 */
public class CastToUtil {

    public static Intent getIntent(String tourl) {
        Intent mIntent = null;
        switch (tourl) {
            case "51":
                mIntent = new Intent(TCApplication.mContext, BalanceActivity.class);
                break;
            case "52":
                mIntent = new Intent(TCApplication.mContext, ReChargeActivity.class);
                break;
            case "53":
                mIntent = new Intent(TCApplication.mContext, RedListActivity.class);
                break;
            case "54":
                mIntent = new Intent(TCApplication.mContext, AccountSetActivity.class);
                break;
            case "56":
                mIntent = new Intent(TCApplication.mContext, ShareActivity.class);
                break;
            case "57":
                mIntent = new Intent(TCApplication.mContext, PublicWebview.class).putExtra("title", "关于同城商城").putExtra("url", "http://user.8hbao.com:8060/about.html");
                break;
        }
        return mIntent;
    }
}
