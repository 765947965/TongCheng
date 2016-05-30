package app.net.tongcheng.model;

/**
 * Created by 76594 on 2016/5/30.
 */
public class RegisterCode extends BaseModel {
    private String authcode;

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }
}
