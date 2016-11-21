package app.net.tongcheng.model;

import java.io.Serializable;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/11/21 16:22
 */

public class InviteInfo extends BaseModel implements Serializable {
    private String inviteflag;
    private String inviteByUID;
    private String inviteByPhone;

    public String getInviteByPhone() {
        return inviteByPhone;
    }

    public void setInviteByPhone(String inviteByPhone) {
        this.inviteByPhone = inviteByPhone;
    }

    public String getInviteByUID() {
        return inviteByUID;
    }

    public void setInviteByUID(String inviteByUID) {
        this.inviteByUID = inviteByUID;
    }

    public String getInviteflag() {
        return inviteflag;
    }

    public void setInviteflag(String inviteflag) {
        this.inviteflag = inviteflag;
    }
}
