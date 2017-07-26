package app.net.tongcheng.Business;

import android.app.Activity;
import android.os.Handler;


import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.FriendModel;
import app.net.tongcheng.model.UpContentModel;
import app.net.tongcheng.model.UpFriendInfoModel;
import app.net.tongcheng.util.HttpBusinessListener;
import app.net.tongcheng.util.HttpUrls;
import app.net.tongcheng.util.RequestParams;

/**
 * Created by 76594 on 2016/6/5.
 */
public class FriendBusiness extends BaseBusiness {


    public FriendBusiness(HttpBusinessListener mCancelableClear, Activity mActivity, Handler mHandler) {
        super(mCancelableClear, mActivity, mHandler);
    }

    public void uploadContens(int mLoding_Type, String message, String data) {
        RequestParams params = new RequestParams(HttpUrls.COMMITFRIEND);
        params.addBodyParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addBodyParameter("aixinContact", data);
        goPostConnect(mActivity, mLoding_Type, params, message, UpContentModel.class.getName());
    }

    public void getFriends(int mLoding_Type, String message, String ver) {
        RequestParams params = new RequestParams(HttpUrls.GETAixinFriends);
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("ver", "1.0");
        goConnect(mActivity, mLoding_Type, params, message, FriendModel.class.getName());
    }

    public void getFriendsInfo(int mLoding_Type, String message, String data) {
        RequestParams params = new RequestParams(HttpUrls.GETAixinFriendInfo);
        params.addBodyParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addBodyParameter("friends", data);
        goPostConnect(mActivity, mLoding_Type, params, message, UpFriendInfoModel.class.getName());
    }

    public void checkFriend(int mLoding_Type, String message, String sdata) {
        RequestParams params = new RequestParams(HttpUrls.CHECKF);
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("account", sdata);
        goConnect(mActivity, mLoding_Type, params, message, UpFriendInfoModel.class.getName());
    }

    public void deleteFriend(int mLoding_Type, String message, String ver, String friend_uid) {
        RequestParams params = new RequestParams(HttpUrls.DeleteFriend);
        params.addQueryStringParameter("uid", TCApplication.getmUserInfo().getUid());
        params.addQueryStringParameter("ver", ver);
        params.addQueryStringParameter("friend_uid", "[" + friend_uid + "]");
        goConnect(mActivity, mLoding_Type, params, message, "");
    }
}
