package app.net.tongcheng.model;

/**
 * Created by 76594 on 2016/5/29.
 */
public class RegisterInviteflagModel extends BaseModel {

    private String phone;
    private String uid;
    private String username;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
