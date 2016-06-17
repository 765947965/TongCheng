package app.net.tongcheng.model;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by 76594 on 2016/6/7.
 */
public class FriendsBean implements Serializable, Comparable<FriendsBean> {
    private String phone;
    private String remark;//通讯录姓名
    private String uid;
    private String ver = "1.0";
    private String province;
    private String picture;
    private int pictureRED;
    private String picmd5;
    private String company;
    private String profession;
    private String school;
    private String sex;
    private String birthday;
    private String signature;
    private String city;
    private String name;//昵称
    private String SearchString;
    private String FY;
    private boolean select;
    private String mobileNumber;

    public void setInfo(String province, String picture, String picmd5, String company, String profession, String school, String sex, String birthday, String signature, String city, String name) {
        this.province = province;
        this.picture = picture;
        this.picmd5 = picmd5;
        this.company = company;
        this.profession = profession;
        this.school = school;
        this.sex = sex;
        this.birthday = birthday;
        this.signature = signature;
        this.city = city;
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchString() {
        return SearchString;
    }

    public void setSearchString(String searchString) {
        SearchString = searchString;
    }

    public String getFY() {
        return FY;
    }

    public void setFY(String FY) {
        this.FY = FY;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getPictureRED() {
        return pictureRED;
    }

    public void setPictureRED(int pictureRED) {
        this.pictureRED = pictureRED;
    }

    @Override
    public int compareTo(FriendsBean another) {
        if (this.FY.equals(another.FY)) {
            if (this.remark.equals(another.remark)) {
                return this.phone.compareTo(another.phone);
            }
            return this.remark.compareTo(another.remark);
        }
        return this.FY.compareTo(another.FY);
    }
}
