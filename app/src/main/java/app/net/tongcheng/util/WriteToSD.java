package app.net.tongcheng.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

public class WriteToSD {

    private Context context;
    static String filePath = android.os.Environment.getExternalStorageDirectory() + "/junte";
    public static final String imgPath = filePath + "/icon_1.0.0.png";

    public WriteToSD(Context context) {
        this.context = context;
        if (!isExist()) {
            write();
        }
    }

    private void write() {
        InputStream inputStream;
        try {
            inputStream = context.getResources().getAssets().open("icon.png");
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(imgPath);
            byte[] buffer = new byte[512];
            int count = 0;
            while ((count = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, count);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
            System.out.println("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isExist() {
        File file = new File(imgPath);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }
}
