package app.net.tongcheng.model;

import java.io.Serializable;

/**
 * Created by 76594 on 2017/7/27.
 */

public class UpLoadImage extends BaseModel implements Serializable {
    private String iid;
    private String imgurl;

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
