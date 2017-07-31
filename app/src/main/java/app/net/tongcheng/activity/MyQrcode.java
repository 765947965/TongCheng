package app.net.tongcheng.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.MSGModel;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.NativieDataUtils;
import okhttp3.Response;

/**
 * Created by 76594 on 2017/7/29.
 */

public class MyQrcode extends BaseActivity {
    private Bitmap mBitmap;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_qrcode);
        setTitle("我的二维码");
        mImageView = (ImageView) findViewById(R.id.iv_qrcode);
    }

    @Override
    public void loadData() {
        try {
            MSGModel mMSGModel = NativieDataUtils.getMSGModel();
            if (mMSGModel == null) {
                return;
            }
            String invite_url = mMSGModel.getInvite_url();
            String text = invite_url.replace("phone=%s", "phone=" + TCApplication.getmUserInfo().getPhone()).replace("channel=%s", "channel=" + Common.BrandName);
            int w = 250;
            int h = 250;
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, w, h);
            int[] rawData = new int[w * h];
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    int color = Color.WHITE;
                    if (matrix.get(i, j)) {
                        color = Color.BLACK;
                    }
                    rawData[i + (j * w)] = color;
                }
            }
            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
            mBitmap.setPixels(rawData, 0, w, 0, 0, w, h);
            if (mBitmap != null) {
                mImageView.setImageBitmap(mBitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mHttpLoadType, ConnectResult mConnectResult) {

    }

    @Override
    public void BusinessOnFail(int mHttpLoadType, Response response) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }
}
