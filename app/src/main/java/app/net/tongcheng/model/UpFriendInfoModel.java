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

    private List<FriendsBean> friendslist;

    public List<FriendsBean> getFriendslist() {
        return friendslist;
    }

    public void setFriendslist(List<FriendsBean> friendslist) {
        this.friendslist = friendslist;
    }
}
