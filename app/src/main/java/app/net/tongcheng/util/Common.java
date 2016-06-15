package app.net.tongcheng.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Common {
	public static final String SIGN_KEY = "&%&aicall$#$";
	public static final String BrandName = "aixin";
	public static final int LasMine = 60;

	public static synchronized String getValueForPro(InputStream in, String key) {
		Properties pro = new Properties();
		try {
			pro.load(in);
			return pro.getProperty(key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
}
