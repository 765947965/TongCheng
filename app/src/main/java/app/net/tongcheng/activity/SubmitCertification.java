package app.net.tongcheng.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
import com.yancy.gallerypick.inter.ImageLoader;
import com.yancy.gallerypick.widget.GalleryImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import app.net.tongcheng.Business.MyBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.MSGModel;
import app.net.tongcheng.model.UpLoadImage;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;
import okhttp3.Response;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by 76594 on 2017/7/26.
 */

public class SubmitCertification extends BaseActivity implements View.OnClickListener {

    private MyBusiness mMyBusiness;
    private ViewHolder mViewHoler;
    private int mImageFlag;//当前选择的照片类型 0 正面, 1反面, 2手持照片
    private File fl0, fl1, fl2; //0 正面, 1反面, 2手持照片
    private String imageId0, imageId1, imageId2;//0 正面, 1反面, 2手持照片
    private EditText tv_name, tv_id_card;
    private GalleryConfig mGalleryConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_certification);
        setTitle("实名认证");
        initView();
        mMyBusiness = new MyBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHoler = new ViewHolder(findViewById(R.id.ssl_main), this);
        mViewHoler.setOnClickListener(R.id.iv_card_positive);
        mViewHoler.setOnClickListener(R.id.iv_card_other_side);
        mViewHoler.setOnClickListener(R.id.iv_synthesis);
        mViewHoler.setOnClickListener(R.id.bt_sumbit);
        tv_name = mViewHoler.getView(R.id.tv_name);
        tv_id_card = mViewHoler.getView(R.id.tv_id_card);
    }


    @Override
    public void loadData() {
        MSGModel mMSGModel = NativieDataUtils.getMSGModel();
        if (mMSGModel != null) {
            mViewHoler.setText(R.id.tv_tips, Html.fromHtml(mMSGModel.getCertification_tips()));
        }
        mViewHoler.setText(R.id.tv_phone, TCApplication.getmUserInfo().getPhone());
        mViewHoler.setText(R.id.tv_uid, TCApplication.getmUserInfo().getUid());
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mHttpLoadType, ConnectResult mConnectResult) {
        switch (mHttpLoadType) {
            case APPCationStation.UPIDCARD0:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    Glide.with(SubmitCertification.this).load(fl0).into((ImageView) mViewHoler.getView(R.id.iv_card_positive));
                    imageId0 = ((UpLoadImage) mConnectResult.getObject()).getIid();
                } else {
                    fl0 = null;
                    ((ImageView) mViewHoler.getView(R.id.iv_card_positive)).setImageResource(R.drawable.id_card_front);
                    ToastUtil.showToast("图片上传失败，请重新选择图片");
                }
                break;
            case APPCationStation.UPIDCARD1:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    Glide.with(SubmitCertification.this).load(fl1).into((ImageView) mViewHoler.getView(R.id.iv_card_other_side));
                    imageId1 = ((UpLoadImage) mConnectResult.getObject()).getIid();
                } else {
                    fl1 = null;
                    ((ImageView) mViewHoler.getView(R.id.iv_card_other_side)).setImageResource(R.drawable.id_card_reverse);
                    ToastUtil.showToast("图片上传失败，请重新选择图片");
                }
                break;
            case APPCationStation.UPIDCARD2:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    Glide.with(SubmitCertification.this).load(fl2).into((ImageView) mViewHoler.getView(R.id.iv_synthesis));
                    imageId2 = ((UpLoadImage) mConnectResult.getObject()).getIid();
                } else {
                    fl2 = null;
                    ((ImageView) mViewHoler.getView(R.id.iv_synthesis)).setImageResource(R.drawable.id_card_hand);
                    ToastUtil.showToast("图片上传失败，请重新选择图片");
                }
                break;
            case APPCationStation.SUMBIT:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    ToastUtil.showToast("提交成功");
                    sendEventBusMessage("CertificationRecord.Refresh");
                    finish();
                } else {
                    ToastUtil.showToast("提交失败，请重新尝试");
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mHttpLoadType, Response response) {
        switch (mHttpLoadType) {
            case APPCationStation.UPIDCARD0:
                fl0 = null;
                ((ImageView) mViewHoler.getView(R.id.iv_card_positive)).setImageResource(R.drawable.id_card_front);
                ToastUtil.showToast("图片上传失败，请重新选择图片");
                break;
            case APPCationStation.UPIDCARD1:
                fl1 = null;
                ((ImageView) mViewHoler.getView(R.id.iv_card_other_side)).setImageResource(R.drawable.id_card_reverse);
                ToastUtil.showToast("图片上传失败，请重新选择图片");
                break;
            case APPCationStation.UPIDCARD2:
                fl2 = null;
                ((ImageView) mViewHoler.getView(R.id.iv_synthesis)).setImageResource(R.drawable.id_card_hand);
                ToastUtil.showToast("图片上传失败，请重新选择图片");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_card_positive:
                mImageFlag = 0;
                initPermission();
                break;
            case R.id.iv_card_other_side:
                mImageFlag = 1;
                initPermission();
                break;
            case R.id.iv_synthesis:
                mImageFlag = 2;
                initPermission();
                break;
            case R.id.bt_sumbit:
                String name = tv_name.getText().toString();
                String carId = tv_id_card.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.showToast("请填写姓名");
                } else if (TextUtils.isEmpty(carId)) {
                    ToastUtil.showToast("请填写身份证号码");
                } else if (fl0 == null || fl1 == null || fl2 == null) {
                    // 没有选择完
                    ToastUtil.showToast("请选择图片");
                } else if (imageId0 == null || imageId1 == null || imageId2 == null) {
                    // 选择完了，但是没有上传完
                    ToastUtil.showToast("请等待图片上传完成");
                } else {
                    // 选择完并上传完成,提交认证
                    mMyBusiness.addCertificationInfo(APPCationStation.SUMBIT, "提交中...", name, carId, imageId0, imageId1, imageId2);
                }
                break;
        }
    }

    private void initPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // 拒绝过了 提示用户如果想要正常使用，要手动去设置中授权。
                ToastUtil.showToast("请在 设置-应用管理 中开启此应用的储存授权。");
            } else {
                // 进行授权
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100001);
            }
        } else {
            GalleryPick.getInstance().setGalleryConfig(getImageConfig()).open(this);
        }
    }

    private GalleryConfig getImageConfig() {
        if (mGalleryConfig == null) {
            mGalleryConfig = new GalleryConfig.Builder()
                    .imageLoader(new GlideLoader())    // ImageLoader 加载框架（必填）
                    .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                    .provider("app.net.tongchengzj.provider")   // provider (必填)
                    .pathList(new ArrayList<String>())                         // 记录已选的图片
                    .crop(false)             // 配置裁剪功能的参数
                    .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                    .filePath("/Gallery/Pictures")          // 图片存放路径
                    .build();
        }
        return mGalleryConfig;
    }

    private IHandlerCallBack iHandlerCallBack = new IHandlerCallBack() {
        @Override
        public void onStart() {
        }

        @Override
        public void onSuccess(List<String> pathList) {
            if (pathList == null || pathList.size() == 0) return;
            final File fl = new File(pathList.get(0));
            if (fl.exists()) {
                final int tempSelection = mImageFlag;
                Luban.with(SubmitCertification.this)
                        .load(fl)                     //传人要压缩的图片
                        .setCompressListener(new OnCompressListener() { //设置回调
                            @Override
                            public void onStart() {
                                ToastUtil.showToast("图片处理中...");
                            }

                            @Override
                            public void onSuccess(File file) {
                                switch (tempSelection) {
                                    case 0:
                                        fl0 = file;
                                        imageId0 = null;
                                        ((ImageView) mViewHoler.getView(R.id.iv_card_positive)).setImageResource(R.drawable.id_card_front);
                                        mMyBusiness.uploadImage(APPCationStation.UPIDCARD0, "上传图片中...", file);
                                        break;
                                    case 1:
                                        fl1 = file;
                                        imageId1 = null;
                                        ((ImageView) mViewHoler.getView(R.id.iv_card_other_side)).setImageResource(R.drawable.id_card_reverse);
                                        mMyBusiness.uploadImage(APPCationStation.UPIDCARD1, "上传图片中...", file);
                                        break;
                                    case 2:
                                        fl2 = file;
                                        imageId2 = null;
                                        ((ImageView) mViewHoler.getView(R.id.iv_synthesis)).setImageResource(R.drawable.id_card_hand);
                                        mMyBusiness.uploadImage(APPCationStation.UPIDCARD2, "上传图片中...", file);
                                        break;
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                ToastUtil.showToast("图片处理失败，请重新选择");
                            }
                        }).launch();
            }
        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onFinish() {
        }

        @Override
        public void onError() {
        }
    };

    private static class GlideLoader implements ImageLoader {

        @Override
        public void displayImage(Activity activity, Context context, String path, GalleryImageView galleryImageView, int width, int height) {
            Glide.with(context)
                    .load(path)
                    .placeholder(R.mipmap.gallery_pick_photo)
                    .centerCrop()
                    .into(galleryImageView);
        }

        @Override
        public void clearMemoryCache() {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == 100001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                GalleryPick.getInstance().setGalleryConfig(getImageConfig()).open(this);
            } else {
                ToastUtil.showToast("请在 设置-应用管理 中开启此应用的储存授权。");
            }
        }
    }
}
