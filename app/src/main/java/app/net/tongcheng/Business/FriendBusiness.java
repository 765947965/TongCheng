package app.net.tongcheng.Business;

import android.app.Activity;
import android.os.Handler;

import org.xutils.http.RequestParams;

import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.FriendModel;
import app.net.tongcheng.model.UpContentModel;
import app.net.tongcheng.model.UpFriendInfoModel;
import app.net.tongcheng.util.CancelableClear;
import app.net.tongcheng.util.HttpUrls;

/**
 * Created by 76594 on 2016/6/5.
 */
public class FriendBusiness extends BaseBusiness {


    public FriendBusiness(CancelableClear mCancelableClear, Activity mActivity, Handler mHandler) {
        super(mCancelableClear, mActivity, mHandler);
    }

    public void uploadContens(int mLoding_Type, String message, String data) {
        RequestParams params = new RequestParams(HttpUrls.COMMITFRIEND);
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addBodyParameter("aixinContact", data);
        goPostConnect(mLoding_Type, params, message, UpContentModel.class.getName());
    }

    public void getFriends(int mLoding_Type, String message, String ver) {
        RequestParams params = new RequestParams(HttpUrls.GETAixinFriends);
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("ver", "1.0");
        goConnect(mLoding_Type, params, message, FriendModel.class.getName());
    }

    public void getFriendsInfo(int mLoding_Type, String message, String data) {
        RequestParams params = new RequestParams(HttpUrls.GETAixinFriendInfo);
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addBodyParameter("friends", data);
        goPostConnect(mLoding_Type, params, message, UpFriendInfoModel.class.getName());
    }
}
