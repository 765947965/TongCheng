package app.net.tongcheng.model;

import java.util.List;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/6/6 11:07
 */
public class UpContentJSONModel {
    private String mac;
    private List<ContentModel> contactlist;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public List<ContentModel> getContactlist() {
        return contactlist;
    }

    public void setContactlist(List<ContentModel> contactlist) {
        this.contactlist = contactlist;
    }
}
