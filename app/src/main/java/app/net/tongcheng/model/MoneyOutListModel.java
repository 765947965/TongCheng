package app.net.tongcheng.model;

import java.util.List;

/**
 * Created by 76594 on 2016/6/12.
 */
public class MoneyOutListModel extends BaseModel {

    /**
     * message : 请求成功
     * data : [{"id":"6","card_id":"1","money":"0","status":"apply","addtime":"2016-06-12 20:51:19","bank_name":"中国银行","bank_card_no":"6228480402564890018","logo_url":"http://pay.8hbao.com:8060/recharge/images/bank_logo/3.png"},{"id":"5","card_id":"1","money":"0","status":"apply","addtime":"2016-06-12 20:51:14","bank_name":"中国银行","bank_card_no":"6228480402564890018","logo_url":"http://pay.8hbao.com:8060/recharge/images/bank_logo/3.png"},{"id":"4","card_id":"2","money":"2698","status":"apply","addtime":"2016-06-12 20:46:52","bank_name":"中国银行","bank_card_no":"6217002710000684874","logo_url":"http://pay.8hbao.com:8060/recharge/images/bank_logo/3.png"},{"id":"1","card_id":"2","money":"37302","status":"apply","addtime":"2016-06-11 13:38:29","bank_name":"中国银行","bank_card_no":"6217002710000684874","logo_url":"http://pay.8hbao.com:8060/recharge/images/bank_logo/3.png"}]
     */

    /**
     * id : 6
     * card_id : 1
     * money : 0
     * status : apply
     * addtime : 2016-06-12 20:51:19
     * bank_name : 中国银行
     * bank_card_no : 6228480402564890018
     * logo_url : http://pay.8hbao.com:8060/recharge/images/bank_logo/3.png
     */

    private String update;

    private List<DataBean> data;

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String card_id;
        private double money;
        private String status;
        private String addtime;
        private String bank_name;
        private String bank_card_no;
        private String logo_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
