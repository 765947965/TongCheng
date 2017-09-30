package app.net.tongcheng.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 76594 on 2017/7/27.
 */

public class CertificationList extends BaseModel implements Serializable {

    private List<CertificationInfo> certification_list;

    public List<CertificationInfo> getCertification_list() {
        return certification_list;
    }

    public void setCertification_list(List<CertificationInfo> certification_list) {
        this.certification_list = certification_list;
    }

    public static class CertificationInfo implements Serializable{
        private String full_name;
        private String phone;
        private String process_time;
        private String certification_id;
        private String card_img3;
        private String card_img2;
        private String card_img1;
        private String create_time;
        private String process_desc = "";
        private String process_status;
        private String user_name;
        private String card_number;

        public String getCard_img1() {
            return card_img1;
        }

        public void setCard_img1(String card_img1) {
            this.card_img1 = card_img1;
        }

        public String getCard_img2() {
            return card_img2;
        }

        public void setCard_img2(String card_img2) {
            this.card_img2 = card_img2;
        }

        public String getCard_img3() {
            return card_img3;
        }

        public void setCard_img3(String card_img3) {
            this.card_img3 = card_img3;
        }

        public String getCard_number() {
            return card_number;
        }

        public void setCard_number(String card_number) {
            this.card_number = card_number;
        }

        public String getCertification_id() {
            return certification_id;
        }

        public void setCertification_id(String certification_id) {
            this.certification_id = certification_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getProcess_desc() {
            return process_desc;
        }

        public void setProcess_desc(String process_desc) {
            this.process_desc = process_desc;
        }

        public String getProcess_status() {
            return process_status;
        }

        public void setProcess_status(String process_status) {
            this.process_status = process_status;
        }

        public String getProcess_time() {
            return process_time;
        }

        public void setProcess_time(String process_time) {
            this.process_time = process_time;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }
}
