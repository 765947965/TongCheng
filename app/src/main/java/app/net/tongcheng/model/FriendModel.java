package app.net.tongcheng.model;

import java.util.List;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/6 14:31
 */
public class FriendModel extends BaseModel {

    /**
     * friends : [{"phone":"15914917329","remark":"令才","uid":"600003"},{"phone":"15625589573","remark":"老婆","uid":"600143"},{"phone":"18665433597","remark":"谢文良","uid":"605488"},{"phone":"13929201231","remark":"陈建东","uid":"600006"},{"phone":"15625589537","remark":"谢文良","uid":"600002"}]
     * ver : 1.1
     */

    private String ver;
    private String update;
    /**
     * phone : 15914917329
     * remark : 令才
     * uid : 600003
     */

    private List<FriendsBean> friends;

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public List<FriendsBean> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendsBean> friends) {
        this.friends = friends;
    }

    public static class FriendsBean {
        private String phone;
        private String remark;
        private String uid;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
