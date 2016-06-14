package app.net.tongcheng.model;

import java.util.List;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/14 17:26
 */
public class GiftInfoModel extends BaseModel {

    /**
     * splitsnumber : 1
     * money : 3300
     * direct : sended
     * shake_ratio : 0.3
     * create_time : 2016-06-14 17:01:49
     * touids : [u'629502']
     * from : 629502
     * open_time :
     * received_money : 3300
     * tips : 恭喜发财,万事如意!
     * exp_time : 2016-06-15
     * type : p2p_money
     * status : has_ended
     * from_phone : 15625589537
     * money_type : 0
     * returned_money : 0
     * sub_type : groupwithcommand
     * gift_id : 201606141701496295022981
     * name : 话费-拼手气红包
     * has_open : 1
     * command : 499
     * fromnickname : 隐隐约约
     */

    private List<GiftsBean> gifts;

    public List<GiftsBean> getGifts() {
        return gifts;
    }

    public void setGifts(List<GiftsBean> gifts) {
        this.gifts = gifts;
    }

    public static class GiftsBean {
        private int splitsnumber;
        private double money;
        private String direct;
        private String shake_ratio;
        private String create_time;
        private String touids;
        private String from;
        private String open_time;
        private double received_money;
        private String tips;
        private String exp_time;
        private String type;
        private String status;
        private String from_phone;
        private int money_type;
        private double returned_money;
        private String sub_type;
        private String gift_id;
        private String name;
        private int has_open;
        private String command;
        private String fromnickname;

        public int getSplitsnumber() {
            return splitsnumber;
        }

        public void setSplitsnumber(int splitsnumber) {
            this.splitsnumber = splitsnumber;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getDirect() {
            return direct;
        }

        public void setDirect(String direct) {
            this.direct = direct;
        }

        public String getShake_ratio() {
            return shake_ratio;
        }

        public void setShake_ratio(String shake_ratio) {
            this.shake_ratio = shake_ratio;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getTouids() {
            return touids;
        }

        public void setTouids(String touids) {
            this.touids = touids;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getOpen_time() {
            return open_time;
        }

        public void setOpen_time(String open_time) {
            this.open_time = open_time;
        }

        public double getReceived_money() {
            return received_money;
        }

        public void setReceived_money(double received_money) {
            this.received_money = received_money;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public String getExp_time() {
            return exp_time;
        }

        public void setExp_time(String exp_time) {
            this.exp_time = exp_time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getFrom_phone() {
            return from_phone;
        }

        public void setFrom_phone(String from_phone) {
            this.from_phone = from_phone;
        }

        public int getMoney_type() {
            return money_type;
        }

        public void setMoney_type(int money_type) {
            this.money_type = money_type;
        }

        public double getReturned_money() {
            return returned_money;
        }

        public void setReturned_money(double returned_money) {
            this.returned_money = returned_money;
        }

        public String getSub_type() {
            return sub_type;
        }

        public void setSub_type(String sub_type) {
            this.sub_type = sub_type;
        }

        public String getGift_id() {
            return gift_id;
        }

        public void setGift_id(String gift_id) {
            this.gift_id = gift_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getHas_open() {
            return has_open;
        }

        public void setHas_open(int has_open) {
            this.has_open = has_open;
        }

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public String getFromnickname() {
            return fromnickname;
        }

        public void setFromnickname(String fromnickname) {
            this.fromnickname = fromnickname;
        }
    }
}
