package app.net.tongcheng.util;

import app.net.tongcheng.R;

/**
 * Created by 76594 on 2016/6/10.
 */
public class BanckCardUtil {
    public static int getImageIdRed4Name(String name) {
        int id = 0;
        switch (name) {
            case "工商银行":
                id = R.drawable.gsyh;
                break;
            case "华夏银行":
                id = R.drawable.hxyh;
                break;
            case "建设银行":
                id = R.drawable.jsyh;
                break;
            case "交通银行":
                id = R.drawable.jtyh;
                break;
            case "农业银行":
                id = R.drawable.nyyh;
                break;
            case "招商银行":
                id = R.drawable.zsyh;
                break;
            case "中国银行":
                id = R.drawable.zgyh;
                break;
            case "中信银行":
                id = R.drawable.zxyh;
                break;
        }
        return id;
    }
}
