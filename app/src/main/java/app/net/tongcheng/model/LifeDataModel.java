package app.net.tongcheng.model;

import java.util.List;

/**
 * Created by 76594 on 2016/6/2.
 */
public class LifeDataModel extends BaseModel {


    /**
     * enable : true
     * ver : 2.7
     * name : 发现
     * items : [{"to":"https://m.taobao.com/#index","pic":"http://m.zjtytx.com/image/1/tongchengshop.png","name":"淘宝商城"},{"to":"52","pic":"http://m.zjtytx.com/image/1/tongchengchongzhi.png","name":"环宇充值"},{"to":"53","pic":"http://img.10086call.cn/group1/M00/00/06/d5OYZ1WDlKKAMAmpAAATEk8yvy8308.png","name":"环宇红包"},{"to":"http://www.budejie.com/m/?from=aixin","pic":"http://img.10086call.cn/group1/M00/00/03/d5OYZ1VpPeCAU2RYAAANQPXhDJk671.png","name":"搞笑百科"},{"to":"http://yx.huosu.com/yx_ns/app","pic":"http://img.10086call.cn/group1/M00/00/04/d5OYZ1VsC9SAL5dkAAANwNSpi4o438.png","name":"趣味游戏"},{"to":"http://wap.ganji.com/?ca_name=mobile_ganji_com","pic":"http://img.10086call.cn/group1/M00/00/03/d5OYZ1VpPeqALNaMAAAM2TIgZ-k117.png","name":"同城服务"},{"to":"http://m.kuaidi100.com","pic":"http://img.10086call.cn/group1/M00/00/01/d5OYZ1Vi-iSAfkhAAAAM1dTyNhM082.png","name":"快递查询"},{"to":"http://touch.piao.qunar.com/touch/index.htm?bd_source=aixin","pic":"http://img.10086call.cn/group1/M00/00/01/d5OYZ1Vi-eeAMIDQAAAKUd2sU5E995.png","name":"景点门票"},{"to":"http://touch.qunar.com/h5/train/?from=touchindex","pic":"http://img.10086call.cn/group1/M00/00/01/d5OYZ1Vi-YiALbnFAAAHBs0mKWQ433.png","name":"火车票"},{"to":"http://touch.qunar.com/h5/flight/","pic":"http://img.10086call.cn/group1/M00/00/01/d5OYZ1Vi-bGAdgUGAAAJ5jVjWVs327.png","name":"机票"},{"to":"http://car.qunar.com/?from=9&bd_source=aixin","pic":"http://img.10086call.cn/group1/M00/00/03/d5OYZ1VpQE2ATIXlAAAL5WIzzpc282.png","name":"接送机"},{"to":"http://touch.qunar.com/h5/hotel/","pic":"http://img.10086call.cn/group1/M00/00/01/d5OYZ1Vi-gqAbOfrAAAHr3Hmzuk767.png","name":"酒店"}]
     * start_time : 201505060000
     * brandname : aixin
     * agent_id : 1
     * has_sub_sect : 0
     * end_time : 202006062359
     */

    private boolean enable;
    private String ver;
    private String name;
    private String start_time;
    private String brandname;
    private String agent_id;
    private String has_sub_sect;
    private String end_time;

    private String update;
    /**
     * to : https://m.taobao.com/#index
     * pic : http://m.zjtytx.com/image/1/tongchengshop.png
     * name : 淘宝商城
     */

    private List<ItemsBean> items;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getHas_sub_sect() {
        return has_sub_sect;
    }

    public void setHas_sub_sect(String has_sub_sect) {
        this.has_sub_sect = has_sub_sect;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public static class ItemsBean {
        private String to;
        private String pic;
        private String name;

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
