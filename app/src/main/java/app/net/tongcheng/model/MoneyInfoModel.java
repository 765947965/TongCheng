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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private String field_account_id;
        /**
         * 总积分
         */
        private double balance;
        /**
         * 可提现
         */
        private double canfetch_amount;
        /**
         * 提现中...
         */
        private double fetching_amount;
        /**
         * 赠送期权
         */
        private double freze_account;
        /**
         * 总之金额
         */
        private double charge_amount;
        /**
         * 手续费比例
         */
        private double fee_ratio;
        /**
         * 银行提现手续费
         */
        private double extra_fee;
        /**
         * 最低保留不可提现金额
         */
        private double min_cash_amount;
        /**
         * 手续费说明提示
         */
        private String description_plus;
        /**
         * 提现按钮下面说明
         */
        private String tips_plus;
        private String status;
        private String addtime;
        private String balance_key;
        private String canfetch_amount_key;
        private String fetching_amount_key;
        private String charge_amount_key;
        private String freze_account_key;

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

        public String getDescription_plus() {
            return description_plus;
        }

        public void setDescription_plus(String description_plus) {
            this.description_plus = description_plus;
        }

        public String getTips_plus() {
            return tips_plus;
        }

        public void setTips_plus(String tips_plus) {
            this.tips_plus = tips_plus;
        }

        public String getBalance_key() {
            return balance_key;
        }

        public void setBalance_key(String balance_key) {
            this.balance_key = balance_key;
        }

        public String getCanfetch_amount_key() {
            return canfetch_amount_key;
        }

        public void setCanfetch_amount_key(String canfetch_amount_key) {
            this.canfetch_amount_key = canfetch_amount_key;
        }

        public String getCharge_amount_key() {
            return charge_amount_key;
        }

        public void setCharge_amount_key(String charge_amount_key) {
            this.charge_amount_key = charge_amount_key;
        }

        public String getFetching_amount_key() {
            return fetching_amount_key;
        }

        public void setFetching_amount_key(String fetching_amount_key) {
            this.fetching_amount_key = fetching_amount_key;
        }

        public String getFreze_account_key() {
            return freze_account_key;
        }

        public void setFreze_account_key(String freze_account_key) {
            this.freze_account_key = freze_account_key;
        }

        public double getMin_cash_amount() {
            return min_cash_amount;
        }

        public void setMin_cash_amount(double min_cash_amount) {
            this.min_cash_amount = min_cash_amount;
        }

        public double getExtra_fee() {
            return extra_fee;
        }

        public void setExtra_fee(double extra_fee) {
            this.extra_fee = extra_fee;
        }
    }
}
