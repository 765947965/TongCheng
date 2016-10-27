package app.net.tongcheng.model;

import java.io.Serializable;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/10/27 10:03
 */

public class MoneyOutInputBean extends BaseModel implements Serializable {
    /**
     * 提现返回消息
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
