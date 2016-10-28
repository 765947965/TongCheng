package app.net.tongcheng.model;

import java.io.Serializable;

/**
 * Created by 76594 on 2016/5/29.
 */
public class BaseModel implements Serializable {
    public int result;

    private String message;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
