package app.net.tongcheng.model;

import java.util.List;

/**
 * Created by 76594 on 2016/6/10.
 */
public class CardListModel extends BaseModel {

    /**
     * message : 请求成功
     * data : [{"id":"9","field_account_id":"1638","uid":"601636","bank_name":"中国招商银行","branch_name":"","bank_card_no":"6214837804753345","card_holder":"林沛煌","bank_type":"personal","status":"normal","is_default":"1","addtime":"2016-05-04 19:23:51"},{"id":"1","field_account_id":"1638","uid":"601636","bank_name":"中国建设银行","branch_name":"前进支行","bank_card_no":"6217007200032958673","card_holder":"林沛煌","bank_type":"personal","status":"normal","is_default":"0","addtime":"2016-05-03 10:04:47"}]
     */

    private String message;
    /**
     * id : 9
     * field_account_id : 1638
     * uid : 601636
     * bank_name : 中国招商银行
     * branch_name :
     * bank_card_no : 6214837804753345
     * card_holder : 林沛煌
     * bank_type : personal
     * status : normal
     * is_default : 1
     * addtime : 2016-05-04 19:23:51
     */

    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String field_account_id;
        private String uid;
        private String bank_name;//银行名称
        private String branch_name;//支行名称
        private String bank_card_no;
        private String card_holder;//持卡人姓名
        private String bank_type;
        private String status;
        private int is_default;
        private String addtime;
        private String logo_url;//银行LOGO

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

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getBranch_name() {
            return branch_name;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }

        public String getBank_card_no() {
            return bank_card_no;
        }

        public void setBank_card_no(String bank_card_no) {
            this.bank_card_no = bank_card_no;
        }

        public String getCard_holder() {
            return card_holder;
        }

        public void setCard_holder(String card_holder) {
            this.card_holder = card_holder;
        }

        public String getBank_type() {
            return bank_type;
        }

        public void setBank_type(String bank_type) {
            this.bank_type = bank_type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
