package app.net.tongcheng.model;

public class OraLodingUser implements Comparable<OraLodingUser> {
    private String phonenum;
    private long time;

    public OraLodingUser() {

    }

    public OraLodingUser(String phonenum, long time) {
        super();
        this.phonenum = phonenum;
        this.time = time;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public long getTime() {
        return time;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public int compareTo(OraLodingUser another) {
        return (int) (another.time - this.time);
    }

}
