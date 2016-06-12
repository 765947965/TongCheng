package app.net.tongcheng.util;

/**
 * Created by 76594 on 2016/5/29.
 */
public class APPCationStation {
    public static final int SUCCESS = 0;
    public static final int FAIL = SUCCESS + 1;
    public static final int LOADING = FAIL + 1;
    public static final int RELOADING = LOADING + 1;
    public static final int SUMBIT = RELOADING + 1;
    public static final int CHECK = SUMBIT + 1;
    public static final int GETAOUTHCODE = CHECK + 1;
    public static final int GETSTARTPAGE = GETAOUTHCODE + 1;
    public static final int EXCRETERED = GETSTARTPAGE + 1;
    public static final int LOADINGAD = EXCRETERED + 1;
    public static final int MONEYOUT = LOADINGAD + 1;
    public static final int UPIMAGE = MONEYOUT + 1;
    public static final int UPOTHERINFO = UPIMAGE + 1;
    public static final int DCARD = UPOTHERINFO + 1;
}
