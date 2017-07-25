package app.net.tongcheng.model;

import java.io.Serializable;

/**
 * Created by 76594 on 2016/6/11.
 */
public class UserMoreInfoModel extends BaseModel implements Serializable{

    private String uid;
    private String province;//省份
    private String picture;
    private String picmd5;
    private String ver;
    private String picurl_prefix;
    private String city;
    private String company;
    private String profession;//职业
    private String school;
    private String sex;
    private String birthday;
    private String location;//地址
    private String signature;//签名
    private String from_self;
    private String mobileNumber;
    private String email;
    private String name;
    private String update;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicmd5() {
        return picmd5;
    }

    public void setPicmd5(String picmd5) {
        this.picmd5 = picmd5;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getPicurl_prefix() {
        return picurl_prefix;
    }

    public void setPicurl_prefix(String picurl_prefix) {
        this.picurl_prefix = picurl_prefix;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getFrom_self() {
        return from_self;
    }

    public void setFrom_self(String from_self) {
        this.from_self = from_self;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
}
