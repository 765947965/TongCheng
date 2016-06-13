package app.net.tongcheng.model;

import java.util.List;

/**
 * Created by 76594 on 2016/6/13.
 */
public class TiXianMoreInfoModel extends BaseModel {

    /**
     * message : 请求成功
     * data : [{"id":"4","field_account_id":"1638","uid":"601636","card_id":"1","money":"140","balance_before":"540","balance_after":"400","status":"apply","reason":null,"is_delete":"0","card_holder":"林沛煌","bank_name":"中国建设银行","bank_card_no":"6217007200032958673","logo_url":"http://pay.8hbao.com:8060/recharge/images/bank_logo/3.png","addtime":"2016-05-05 18:04:04","pay_time":null,"finish_time":null}]
     */

    private String message;
    /**
     * id : 4
     * field_account_id : 1638
     * uid : 601636
     * card_id : 1
     * money : 140
     * balance_before : 540
     * balance_after : 400
     * status : apply
     * reason : null
     * is_delete : 0
     * card_holder : 林沛煌
     * bank_name : 中国建设银行
     * bank_card_no : 6217007200032958673
     * logo_url : http://pay.8hbao.com:8060/recharge/images/bank_logo/3.png
     * addtime : 2016-05-05 18:04:04
     * pay_time : null
     * finish_time : null
     */

    private DataBean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String field_account_id;
        private String uid;
        private String card_id;
        private double money;
        private double balance_before;
        private double balance_after;
        private String status;
        private String reason;
        private int is_delete;
        private String card_holder;
        private String bank_name;
        private String bank_card_no;
        private String logo_url;
        private String addtime;
        private String pay_time;
        private String finish_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getField_account_id() {
            return field_account_id;
        }

        public void setField_account_id(String field_account_id) {
            this.field_account_id = field_account_id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCard_id() {
            return card_id;
        }

        public void setCard_id(String card_id) {
            this.card_id = card_id;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public double getBalance_before() {
            return balance_before;
        }

        public void setBalance_before(double balance_before) {
            this.balance_before = balance_before;
        }

        public double getBalance_after() {
            return balance_after;
        }

        public void setBalance_after(double balance_after) {
            this.balance_after = balance_after;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public int getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(int is_delete) {
            this.is_delete = is_delete;
        }

        public String getCard_holder() {
            return card_holder;
        }

        public void setCard_holder(String card_holder) {
            this.card_holder = card_holder;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getBank_card_no() {
            return bank_card_no;
        }

        public void setBank_card_no(String bank_card_no) {
            this.bank_card_no = bank_card_no;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getFinish_time() {
            return finish_time;
        }

        public void setFinish_time(String finish_time) {
            this.finish_time = finish_time;
        }
    }
}
