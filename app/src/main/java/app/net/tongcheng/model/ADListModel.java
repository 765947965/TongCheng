package app.net.tongcheng.model;

import java.util.List;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/3 15:35
 */
public class ADListModel extends BaseModel {

    /**
     * agentid : 1
     * urlprefix : http://m.calltc.com/image/1/
     * eff_time : 2014-11-18 11:04:05
     * adid : 2
     * exp_time : 2020-12-31 11:04:15
     * pn : [{"to":"","n":"aixin_0_640_434_android.png"}]
     */

    private String agentid;
    private String urlprefix;
    private String eff_time;
    private String adid;
    private String exp_time;
    private String update;
    /**
     * to :
     * n : aixin_0_640_434_android.png
     */

    private List<PnBean> pn;

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public String getUrlprefix() {
        return urlprefix;
    }

    public void setUrlprefix(String urlprefix) {
        this.urlprefix = urlprefix;
    }

    public String getEff_time() {
        return eff_time;
    }

    public void setEff_time(String eff_time) {
        this.eff_time = eff_time;
    }

    public String getAdid() {
        return adid;
    }

    public void setAdid(String adid) {
        this.adid = adid;
    }

    public String getExp_time() {
        return exp_time;
    }

    public void setExp_time(String exp_time) {
        this.exp_time = exp_time;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public List<PnBean> getPn() {
        return pn;
    }

    public void setPn(List<PnBean> pn) {
        this.pn = pn;
    }

    public static class PnBean {
        private String to;
        private String n;

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getN() {
            return n;
        }

        public void setN(String n) {
            this.n = n;
        }
    }
}
