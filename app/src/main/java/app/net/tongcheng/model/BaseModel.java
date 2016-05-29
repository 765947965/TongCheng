package app.net.tongcheng.model;

import java.io.Serializable;

/**
 * Created by 76594 on 2016/5/29.
 */
public class BaseModel implements Serializable {
    public int result;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
