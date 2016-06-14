package app.net.tongcheng.model;

import java.util.List;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/14 17:32
 */
public class SplideGiftModel extends BaseModel {

    /**
     * splitsnumber : 1
     * received_user_gift_info : [{"picture":"http://img.10086call.cn/group1/M00/00/0D/d5OYZ1XJ1waAIJYyAAEUH3X2v2Y760.jpg","open_time":"2016-06-14 17:02:18","mobileNumber":"15625589537","money":"3300","name":"谢文良","thankyou":"多谢多谢啦!","uid":"629502"}]
     * sended_gift_id : 201606141701496295022981
     */

    private int splitsnumber;
    private String sended_gift_id;
    /**
     * picture : http://img.10086call.cn/group1/M00/00/0D/d5OYZ1XJ1waAIJYyAAEUH3X2v2Y760.jpg
     * open_time : 2016-06-14 17:02:18
     * mobileNumber : 15625589537
     * money : 3300
     * name : 谢文良
     * thankyou : 多谢多谢啦!
     * uid : 629502
     */

    private List<ReceivedUserGiftInfoBean> received_user_gift_info;

    public int getSplitsnumber() {
        return splitsnumber;
    }

    public void setSplitsnumber(int splitsnumber) {
        this.splitsnumber = splitsnumber;
    }

    public String getSended_gift_id() {
        return sended_gift_id;
    }

    public void setSended_gift_id(String sended_gift_id) {
        this.sended_gift_id = sended_gift_id;
    }

    public List<ReceivedUserGiftInfoBean> getReceived_user_gift_info() {
        return received_user_gift_info;
    }

    public void setReceived_user_gift_info(List<ReceivedUserGiftInfoBean> received_user_gift_info) {
        this.received_user_gift_info = received_user_gift_info;
    }

    public static class ReceivedUserGiftInfoBean {
        private String picture;
        private String open_time;
        private String mobileNumber;
        private double money;
        private String name;
        private String thankyou;
        private String uid;

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getOpen_time() {
            return open_time;
        }

        public void setOpen_time(String open_time) {
            this.open_time = open_time;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getThankyou() {
            return thankyou;
        }

        public void setThankyou(String thankyou) {
            this.thankyou = thankyou;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
