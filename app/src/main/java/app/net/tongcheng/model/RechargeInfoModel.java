package app.net.tongcheng.model;

import java.util.List;

/**
 * Created by 76594 on 2016/6/11.
 */
public class RechargeInfoModel extends BaseModel {

    /**
     * message : 请求成功
     * data : [{"GoodsID":"603000","GoodsName":"充值3000积分到账4000","Price":"300000","GoodsInfo":"充值3000积分到账4000,并从充值成功第二天开始享受3个月补助,系统每天指定推送一个补助20积分红包(周末和法定节假日除外)"},{"GoodsID":"605000","GoodsName":"充值5000积分到账8000","Price":"500000","GoodsInfo":"充值5000积分到账8000,并从充值成功第二天开始享受3个月补助,系统每天指定推送一个补助50积分红包(周末和法定节假日除外)"},{"GoodsID":"610000","GoodsName":"充值10000积分到账15000","Price":"1000000","GoodsInfo":"充值10000积分到账15000,并从充值成功第二天开始享受3个月补助,系统每天指定推送一个补助100积分红包(周末和法定节假日除外)"},{"GoodsID":"620000","GoodsName":"充值20000积分到账40000","Price":"2000000","GoodsInfo":"充值20000积分到账40000,并从充值成功第二天开始享受3个月补助,系统每天指定推送一个补助200积分红包(周末和法定节假日除外)"}]
     */

    private String message;
    /**
     * GoodsID : 603000
     * GoodsName : 充值3000积分到账4000
     * Price : 300000
     * GoodsInfo : 充值3000积分到账4000,并从充值成功第二天开始享受3个月补助,系统每天指定推送一个补助20积分红包(周末和法定节假日除外)
     */

    private String update;

    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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
        private String GoodsID;
        private String GoodsName;
        private double Price;
        private String GoodsInfo;

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
}
