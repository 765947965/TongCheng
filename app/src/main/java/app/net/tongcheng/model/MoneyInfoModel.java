package app.net.tongcheng.model;

import java.io.Serializable;

/**
 * Created by 76594 on 2016/6/10.
 */
public class MoneyInfoModel extends BaseModel implements Serializable {

    /**
     * message : 请求成功
     * data : {"field_account_id":"1638","balance":400,"canfetch_amount":400,"fetching_amount":600,"freze_account":0,"status":"","addtime":""}
     */

    private String message;
    /**
     * field_account_id : 1638
     * balance : 400
     * canfetch_amount : 400
     * fetching_amount : 600
     * freze_account : 0
     * status :状态(finish,fail,paid,apply)
     * addtime :
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

    public static class DataBean implements Serializable{
        private String field_account_id;
        private double balance;
        private double canfetch_amount;
        private double fetching_amount;
        private double freze_account;
        private double charge_amount;
        private double fee_ratio;
        private String description;
        private String tips;
        private String status;
        private String addtime;

        public String getField_account_id() {
            return field_account_id;
        }

        public void setField_account_id(String field_account_id) {
            this.field_account_id = field_account_id;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public double getCanfetch_amount() {
            return canfetch_amount;
        }

        public void setCanfetch_amount(double canfetch_amount) {
            this.canfetch_amount = canfetch_amount;
        }

        public double getFetching_amount() {
            return fetching_amount;
        }

        public void setFetching_amount(double fetching_amount) {
            this.fetching_amount = fetching_amount;
        }

        public double getFreze_account() {
            return freze_account;
        }

        public void setFreze_account(double freze_account) {
            this.freze_account = freze_account;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public double getCharge_amount() {
            return charge_amount;
        }

        public void setCharge_amount(double charge_amount) {
            this.charge_amount = charge_amount;
        }

        public double getFee_ratio() {
            return fee_ratio;
        }

        public void setFee_ratio(double fee_ratio) {
            this.fee_ratio = fee_ratio;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }
    }
}
