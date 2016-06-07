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
        private String remark;//通讯录姓名
        private String uid;
        private String ver = "1.0";
        private String province;
        private String picture;
        private String picmd5;
        private String company;
        private String profession;
        private String school;
        private String sex;
        private String birthday;
        private String signature;
        private String city;
        private String name;//签名
        private String SearchString;

        public void setInfo(String province, String picture, String picmd5, String company, String profession, String school, String sex, String birthday, String signature, String city, String name) {
            this.province = province;
            this.picture = picture;
            this.picmd5 = picmd5;
            this.company = company;
            this.profession = profession;
            this.school = school;
            this.sex = sex;
            this.birthday = birthday;
            this.signature = signature;
            this.city = city;
            this.name = name;
        }

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

        public String getVer() {
            return ver;
        }

        public void setVer(String ver) {
            this.ver = ver;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getPicmd5() {
            return picmd5;
        }

        public void setPicmd5(String picmd5) {
            this.picmd5 = picmd5;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSearchString() {
            return SearchString;
        }

        public void setSearchString(String searchString) {
            SearchString = searchString;
        }
    }
}
