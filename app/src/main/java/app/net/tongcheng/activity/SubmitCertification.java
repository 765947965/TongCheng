package app.net.tongcheng.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.io.File;
import java.util.List;

import app.net.tongcheng.Business.MyBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.ViewHolder;

/**
 * Created by 76594 on 2017/7/26.
 */

public class SubmitCertification extends BaseActivity implements View.OnClickListener {

    private MyBusiness mMyBusiness;
    private ViewHolder mViewHoler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_certification);
        setTitle("绑定新银行卡");
        initView();
        mMyBusiness = new MyBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHoler = new ViewHolder(findViewById(R.id.ssl_main), this);
        mViewHoler.setOnClickListener(R.id.iv_card_positive);
        mViewHoler.setOnClickListener(R.id.iv_card_other_side);
        mViewHoler.setOnClickListener(R.id.iv_synthesis);
    }


    @Override
    public void loadData() {
        mViewHoler.setText(R.id.tv_phone, TCApplication.getmUserInfo().getPhone());
        mViewHoler.setText(R.id.tv_uid, TCApplication.getmUserInfo().getUid());
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mHttpLoadType, ConnectResult mConnectResult) {

    }

    @Override
    public void BusinessOnFail(int mHttpLoadType) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_card_positive:
                ImageSelector.open(this, getImageConfig());
                break;
            case R.id.iv_card_other_side:
                ImageSelector.open(this, getImageConfig());
                break;
            case R.id.iv_synthesis:
                ImageSelector.open(this, getImageConfig());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            if (pathList == null || pathList.size() == 0) return;
            File fl = new File(pathList.get(0));
            if (fl.exists()) {
                // 上传图片
                mMyBusiness.upImage(APPCationStation.UPIMAGE, "上传图片中...", fl);
            }
        }
    }


    private ImageConfig getImageConfig() {
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.refurush_color))
                .titleBgColor(getResources().getColor(R.color.refurush_color))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                // (截图默认配置：关闭    比例 1：1    输出分辨率  500*500)
//                .crop()
                // 开启单选   （默认为多选）
                .singleSelect()
                // 开启拍照功能 （默认关闭）
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .build();
        return imageConfig;
    }

    class GlideLoader implements com.yancy.imageselector.ImageLoader {

        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context)
                    .load(path)
                    .placeholder(com.yancy.imageselector.R.mipmap.imageselector_photo)
                    .centerCrop()
                    .into(imageView);
        }

    }
}
