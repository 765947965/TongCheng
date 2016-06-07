package app.net.tongcheng.model;

import java.util.List;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/7 16:46
 */
public class UpFriendInfoModel extends BaseModel {

    private List<FriendModel.FriendsBean> friendslist;

    public List<FriendModel.FriendsBean> getFriendslist() {
        return friendslist;
    }

    public void setFriendslist(List<FriendModel.FriendsBean> friendslist) {
        this.friendslist = friendslist;
    }
}
