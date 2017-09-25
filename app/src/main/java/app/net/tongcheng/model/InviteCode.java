package app.net.tongcheng.model;

import java.io.Serializable;

/**
 * <br> ClassName:   ${className}
 * <br> Description:
 * <br>
 * <br> Author:      谢文良
 * <br> Date:        2017/9/25 10:35
 */

public class InviteCode extends BaseModel implements Serializable {

    /**
     * should 非必填， must 必填
     */
    private String status;

    public boolean isMust() {
        return "must".equals(status);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
