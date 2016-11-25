package app.net.tongcheng.model;

import java.util.List;

/**
 * Created by 76594 on 2016/6/14.
 */
public class BanckListModel extends BaseModel {


    /**
     * message : 请求成功
     * data : [{"bank_id":"1","bank_name":"中国工商银行","logo_url":"http://pay.8hbao.com/recharge/images/bank_logo/1.png"},{"bank_id":"2","bank_name":"中国建设银行","logo_url":"http://pay.8hbao.com/recharge/images/bank_logo/2.png"},{"bank_id":"3","bank_name":"中国银行","logo_url":"http://pay.8hbao.com/recharge/images/bank_logo/3.png"},{"bank_id":"4","bank_name":"中国农业银行","logo_url":"http://pay.8hbao.com/recharge/images/bank_logo/4.png"},{"bank_id":"5","bank_name":"交通银行","logo_url":"http://pay.8hbao.com/recharge/images/bank_logo/5.png"},{"bank_id":"6","bank_name":"招商银行","logo_url":"http://pay.8hbao.com/recharge/images/bank_logo/6.png"},{"bank_id":"7","bank_name":"中国邮政","logo_url":"http://pay.8hbao.com/recharge/images/bank_logo/7.png"},{"bank_id":"8","bank_name":"北京银行","logo_url":"http://pay.8hbao.com/recharge/images/bank_logo/8.png"},{"bank_id":"9","bank_name":"中国民生银行","logo_url":"http://pay.8hbao.com/recharge/images/bank_logo/9.png"},{"bank_id":"10","bank_name":"中国光大银行","logo_url":"http://pay.8hbao.com/recharge/images/bank_logo/10.png"},{"bank_id":"11","bank_name":"兴业银行","logo_url":"http://pay.8hbao.com/recharge/images/bank_logo/11.png"},{"bank_id":"12","bank_name":"中国农业发展银行","logo_url":"http://pay.8hbao.com/recharge/images/bank_logo/12.png"},{"bank_id":"13","bank_name":"上海浦东发展银行","logo_url":"http://pay.8hbao.com/recharge/images/bank_logo/13.png"},{"bank_id":"14","bank_name":"广东发展银行","logo_url":"http://pay.8hbao.com/recharge/images/bank_logo/14.png"},{"bank_id":"15","bank_name":"深圳发展银行","logo_url":"http://pay.8hbao.com/recharge/images/bank_logo/15.png"},{"bank_id":"16","bank_name":"中国人民银行","logo_url":"http://pay.8hbao.com/recharge/images/bank_logo/16.png"},{"bank_id":"17","bank_name":"中信实业银行","logo_url":"http://pay.8hbao.com/recharge/images/bank_logo/17.png"},{"bank_id":"18","bank_name":"广州市商业银行","logo_url":"http://pay.8hbao.com/recharge/images/bank_logo/18.png"},{"bank_id":"19","bank_name":"广东市农村信用合作社","logo_url":"http://pay.8hbao.com/recharge/images/bank_logo/19.png"}]
     */
    /**
     * bank_id : 1
     * bank_name : 中国工商银行
     * logo_url : http://pay.8hbao.com/recharge/images/bank_logo/1.png
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String bank_id;
        private String bank_name;
        private String logo_url;

        public String getBank_id() {
            return bank_id;
        }

        public void setBank_id(String bank_id) {
            this.bank_id = bank_id;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }
    }
}
