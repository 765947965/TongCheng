package app.net.tongcheng.model;

import java.util.List;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/5/24 16:08
 */
public class RedModel extends BaseModel {

    private String update;

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    private List<GiftsBean> gifts;

    public List<GiftsBean> getGifts() {
        return gifts;
    }

    public void setGifts(List<GiftsBean> gifts) {
        this.gifts = gifts;
    }
}
