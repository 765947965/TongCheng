package app.net.tongcheng.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 76594 on 2016/6/11.
 */
public class RechargeInfoModel extends BaseModel {

    /**
     * message : 请求成功
     * data : [{"GoodsID":"603000","GoodsName":"充值3000积分到账4000","Price":"300000","GoodsInfo":"充值3000积分到账4000,并从充值成功第二天开始享受3个月补助,系统每天指定推送一个补助20积分红包(周末和法定节假日除外)"},{"GoodsID":"605000","GoodsName":"充值5000积分到账8000","Price":"500000","GoodsInfo":"充值5000积分到账8000,并从充值成功第二天开始享受3个月补助,系统每天指定推送一个补助50积分红包(周末和法定节假日除外)"},{"GoodsID":"610000","GoodsName":"充值10000积分到账15000","Price":"1000000","GoodsInfo":"充值10000积分到账15000,并从充值成功第二天开始享受3个月补助,系统每天指定推送一个补助100积分红包(周末和法定节假日除外)"},{"GoodsID":"620000","GoodsName":"充值20000积分到账40000","Price":"2000000","GoodsInfo":"充值20000积分到账40000,并从充值成功第二天开始享受3个月补助,系统每天指定推送一个补助200积分红包(周末和法定节假日除外)"}]
     */

    /**
     * GoodsID : 603000
     * GoodsName : 充值3000积分到账4000
     * Price : 300000
     * GoodsInfo : 充值3000积分到账4000,并从充值成功第二天开始享受3个月补助,系统每天指定推送一个补助20积分红包(周末和法定节假日除外)
     */

    private String update;

    private List<DataBean> data;

    private GuQaunObject diy_hehuoren_goods_info;

    private PayType pay_type;

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

    public GuQaunObject getDiy_hehuoren_goods_info() {
        return diy_hehuoren_goods_info;
    }

    public void setDiy_hehuoren_goods_info(GuQaunObject diy_hehuoren_goods_info) {
        this.diy_hehuoren_goods_info = diy_hehuoren_goods_info;
    }

    public PayType getPay_type() {
        return pay_type;
    }

    public void setPay_type(PayType pay_type) {
        this.pay_type = pay_type;
    }

    public static class DataBean implements Serializable {
        private String GoodsID;
        private String GoodsName;
        private double Price;
        private String GoodsInfo;

        public DataBean() {

        }

        public DataBean(String GoodsID, String GoodsName, double Price, String GoodsInfo) {
            this.GoodsID = GoodsID;
            this.GoodsName = GoodsName;
            this.Price = Price;
            this.GoodsInfo = GoodsInfo;
        }

        public String getGoodsID() {
            return GoodsID;
        }

        public void setGoodsID(String GoodsID) {
            this.GoodsID = GoodsID;
        }

        public String getGoodsName() {
            return GoodsName;
        }

        public void setGoodsName(String GoodsName) {
            this.GoodsName = GoodsName;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }

        public String getGoodsInfo() {
            return GoodsInfo;
        }

        public void setGoodsInfo(String GoodsInfo) {
            this.GoodsInfo = GoodsInfo;
        }
    }

    public static class GuQaunObject implements Serializable {
        /**
         * "合伙人自选(自定义充值金额)",
         */
        private String diy_hehuoren_goods_name;
        /**
         * 请输入300元整数倍充值金额
         */
        private String diy_hehuoren_goods_money_input_tips;

        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public String getDiy_hehuoren_goods_money_input_tips() {
            return diy_hehuoren_goods_money_input_tips;
        }

        public void setDiy_hehuoren_goods_money_input_tips(String diy_hehuoren_goods_money_input_tips) {
            this.diy_hehuoren_goods_money_input_tips = diy_hehuoren_goods_money_input_tips;
        }

        public String getDiy_hehuoren_goods_name() {
            return diy_hehuoren_goods_name;
        }

        public void setDiy_hehuoren_goods_name(String diy_hehuoren_goods_name) {
            this.diy_hehuoren_goods_name = diy_hehuoren_goods_name;
        }
    }

    public static class PayType{
        private int wxpay;
        private int alipay;
        private int allinpay;
        private int huanxun;

        public int getWxpay() {
            return wxpay;
        }

        public void setWxpay(int wxpay) {
            this.wxpay = wxpay;
        }

        public int getAlipay() {
            return alipay;
        }

        public void setAlipay(int alipay) {
            this.alipay = alipay;
        }

        public int getAllinpay() {
            return allinpay;
        }

        public void setAllinpay(int allinpay) {
            this.allinpay = allinpay;
        }

        public int getHuanxun() {
            return huanxun;
        }

        public void setHuanxun(int huanxun) {
            this.huanxun = huanxun;
        }
    }
}
