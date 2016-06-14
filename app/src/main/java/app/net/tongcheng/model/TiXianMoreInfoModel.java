package app.net.tongcheng.model;

/**
 * Created by 76594 on 2016/6/13.
 */
public class TiXianMoreInfoModel extends BaseModel {

    /**
     * message : 请求成功
     * data : {"id":"5","field_account_id":"3","user_name":"600002","card_id":"1","money":"0","balance_before":"50000","balance_after":"50000","orderno":"P201606122051141890600002","status":"finish","reason":"","is_delete":"0","phone_num":"15625589537","addtime":"2016-06-12 20:51:14","pay_time":"2016-06-13 21:45:02","finish_time":"2016-06-13 21:45:06","bank_name":"中国银行","bank_card_no":"6228480402564890018","logo_url":"http://pay.8hbao.com:8060/recharge/images/bank_logo/3.png"}
     */

    private String message;
    /**
     * id : 5
     * field_account_id : 3
     * user_name : 600002
     * card_id : 1
     * money : 0
     * balance_before : 50000
     * balance_after : 50000
     * orderno : P201606122051141890600002
     * status : finish
     * reason :
     * is_delete : 0
     * phone_num : 15625589537
     * addtime : 2016-06-12 20:51:14
     * pay_time : 2016-06-13 21:45:02
     * finish_time : 2016-06-13 21:45:06
     * bank_name : 中国银行
     * bank_card_no : 6228480402564890018
     * logo_url : http://pay.8hbao.com:8060/recharge/images/bank_logo/3.png
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
        private String user_name;
        private String card_id;
        private double money;
        private double balance_before;
        private double balance_after;
        private String orderno;
        private String status;
        private String reason;
        private String is_delete;
        private String phone_num;
        private String addtime;
        private String pay_time;
        private String finish_time;
        private String bank_name;
        private String bank_card_no;
        private String logo_url;

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

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
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

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
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

        public String getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(String is_delete) {
            this.is_delete = is_delete;
        }

        public String getPhone_num() {
            return phone_num;
        }

        public void setPhone_num(String phone_num) {
            this.phone_num = phone_num;
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
    }
}
