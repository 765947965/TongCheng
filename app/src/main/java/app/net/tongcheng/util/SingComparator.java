package app.net.tongcheng.util;

import java.util.Comparator;

/**
 * Created by 76594 on 2017/7/22.
 */

public class SingComparator implements Comparator<String> {
    @Override
    public int compare(String lhs, String rhs) {
        return lhs.substring(0, lhs.indexOf("=")).compareTo(rhs.substring(0, rhs.indexOf("=")));
    }
}
