package app.net.tongcheng.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import app.net.tongcheng.Business.MyBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.BaseModel;
import app.net.tongcheng.model.CheckEvent;
import app.net.tongcheng.model.ConnectResult;
import app.net.tongcheng.model.UserMoreInfoModel;
import app.net.tongcheng.util.APPCationStation;
import app.net.tongcheng.util.DialogUtil;
import app.net.tongcheng.util.NativieDataUtils;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;
import app.net.tongcheng.view.MyDatePickerDialog;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/5/25 17:23
 */
public class MyUserInfoActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,
        DatePickerDialog.OnDateSetListener {


    private ViewHolder mViewHolder;
    private MyBusiness mMyBusiness;
    private UserMoreInfoModel mUserMoreInfoModel;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private File fl;
    private List<String> sexlist = new ArrayList<>();
    private MyDatePickerDialog mMyDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_user_info_layout);
        setTitle("个人资料");
        initView();
        setEventBus();
        mMyBusiness = new MyBusiness(this, this, mHandler);
    }

    private void initView() {
        mViewHolder = new ViewHolder(findViewById(R.id.mSwipeRefreshLayout), this);
        mSwipeRefreshLayout = mViewHolder.getView(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refurush_color);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mViewHolder.setOnClickListener(R.id.rlt_head_image);
        mViewHolder.setOnClickListener(R.id.rlt_name);
        mViewHolder.setOnClickListener(R.id.rlt_password);
        mViewHolder.setOnClickListener(R.id.rlt_address);
        mViewHolder.setOnClickListener(R.id.rlt_sex);
        mViewHolder.setOnClickListener(R.id.rlt_area);
        mViewHolder.setOnClickListener(R.id.rlt_signature);
        mViewHolder.setOnClickListener(R.id.bt_sumbit);
        mViewHolder.setOnClickListener(R.id.rlt_self_mp);
        mViewHolder.setOnClickListener(R.id.rlt_birthday);
        getRightBtn().setVisibility(View.VISIBLE);
        getRightBtn().setText("保存");
        getRightBtn().setOnClickListener(this);
    }


    @Override
    public void loadData() {
        mHandler.sendEmptyMessageDelayed(10001, 100);
        sexlist.clear();
        sexlist.add("男");
        sexlist.add("女");
    }

    @Override
    public void mHandDoSomeThing(Message msg) {
        switch (msg.what) {
            case 10001:
                mUserMoreInfoModel = NativieDataUtils.getUserMoreInfoModel();
                if (mUserMoreInfoModel == null || !NativieDataUtils.getTodyYMD().equals(mUserMoreInfoModel.getUpdate())) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    mMyBusiness.getuserInfo(APPCationStation.LOADING, "");
                }
                if (mUserMoreInfoModel == null) {
                    mUserMoreInfoModel = new UserMoreInfoModel();
                }
                setData(mUserMoreInfoModel);
                break;
        }
    }

    @Override
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {
        switch (mLoadType) {
            case APPCationStation.LOADING:
                mSwipeRefreshLayout.setRefreshing(false);
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    UserMoreInfoModel mUserMoreInfoModel = (UserMoreInfoModel) mConnectResult.getObject();
                    mUserMoreInfoModel.setUpdate(NativieDataUtils.getTodyYMD());
                    NativieDataUtils.setUserMoreInfoModel(mUserMoreInfoModel);
                    mHandler.sendEmptyMessage(10001);
                }
                break;
            case APPCationStation.UPIMAGE:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    UserMoreInfoModel mUserMoreInfoModel = (UserMoreInfoModel) mConnectResult.getObject();
                    if (MyUserInfoActivity.this.mUserMoreInfoModel != null) {
                        MyUserInfoActivity.this.mUserMoreInfoModel.setPicurl_prefix(mUserMoreInfoModel.getPicurl_prefix());
                        MyUserInfoActivity.this.mUserMoreInfoModel.setPicture(mUserMoreInfoModel.getPicture());
                        MyUserInfoActivity.this.mUserMoreInfoModel.setPicmd5(mUserMoreInfoModel.getPicmd5());
                        MyUserInfoActivity.this.mUserMoreInfoModel.setVer(mUserMoreInfoModel.getVer());
                        NativieDataUtils.setUserMoreInfoModel(MyUserInfoActivity.this.mUserMoreInfoModel);
                        setData(MyUserInfoActivity.this.mUserMoreInfoModel);
                    }
                }
                break;
            case APPCationStation.UPOTHERINFO:
                if (mConnectResult != null && mConnectResult.getObject() != null && ((BaseModel) mConnectResult.getObject()).getResult() == 0) {
                    UserMoreInfoModel mUserMoreInfoModel = (UserMoreInfoModel) mConnectResult.getObject();
                    if (MyUserInfoActivity.this.mUserMoreInfoModel != null) {
                        MyUserInfoActivity.this.mUserMoreInfoModel.setVer(mUserMoreInfoModel.getVer());
                        NativieDataUtils.setUserMoreInfoModel(MyUserInfoActivity.this.mUserMoreInfoModel);
                        DialogUtil.showTipsDialog(this, "更新用户数据成功!", null);
                    }
                }
                break;
        }
    }

    @Override
    public void BusinessOnFail(int mLoadType) {
        switch (mLoadType) {
            case APPCationStation.LOADING:
                mSwipeRefreshLayout.setRefreshing(false);
                break;
            case APPCationStation.UPIMAGE:
                ToastUtil.showToast("上传图片失败!");
                break;
            case APPCationStation.UPOTHERINFO:
                ToastUtil.showToast("更新用户信息失败!");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (mUserMoreInfoModel == null) return;
        switch (v.getId()) {
            case R.id.rlt_head_image:
                ImageSelector.open(this, getImageConfig());
                break;
            case R.id.rlt_name:
                startActivity(new Intent(TCApplication.mContext, ChangeNameActivity.class).putExtra("name", mUserMoreInfoModel.getName()));
                break;
            case R.id.rlt_password://登录密码
                startActivity(new Intent(TCApplication.mContext, ChagnePassoword.class));
                break;
            case R.id.rlt_address://地址
                startActivity(new Intent(TCApplication.mContext, UserInfoAddressActivity.class).putExtra("location", mUserMoreInfoModel.getLocation()));
                break;
            case R.id.rlt_sex://性别
                DialogUtil.showListDilaog(this, sexlist, "请选择性别", R.layout.text_item_layout, new DialogUtil.OnListDialogListener<String>() {
                    @Override
                    public void CreateItem(ViewHolder holder, String item, List<String> list, int position) {
                        holder.setText(R.id.tv_item, item);
                    }

                    @Override
                    public void OnItemSelect(View view, List<String> mDatas, String mItemdata, int position) {
                        mUserMoreInfoModel.setSex(mItemdata);
                        setData(mUserMoreInfoModel);
                    }
                });
                break;
            case R.id.rlt_area://地区
                startActivity(new Intent(TCApplication.mContext, ChangeProvince.class));
                break;
            case R.id.rlt_signature://个性签名
                startActivity(new Intent(TCApplication.mContext, QianmingActivity.class).putExtra("signature", mUserMoreInfoModel.getSignature()));
                break;
            case R.id.bt_sumbit:
            case R.id.btnRight:
                saveInfo();
                break;
            case R.id.rlt_self_mp://个人名片
                startActivity(new Intent(this, MyUserInfoMP.class).putExtra("mUserMoreInfoModel", mUserMoreInfoModel));
                break;
            case R.id.rlt_birthday://生日
                if (mMyDatePickerDialog == null) {
                    Calendar mCalendar = Calendar.getInstance();
                    mMyDatePickerDialog = new MyDatePickerDialog(this, this,
                            mCalendar.get(Calendar.YEAR),
                            mCalendar.get(Calendar.MONTH),
                            mCalendar.get(Calendar.DAY_OF_MONTH));
                }
                mMyDatePickerDialog.showHasDate();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        sendEventBusMessage("MyFragment.Refresh");
        super.onDestroy();
    }

    private ImageConfig getImageConfig() {
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.refurush_color))
                .titleBgColor(getResources().getColor(R.color.refurush_color))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                // (截图默认配置：关闭    比例 1：1    输出分辨率  500*500)
                .crop()
                // 开启单选   （默认为多选）
                .singleSelect()
                // 开启拍照功能 （默认关闭）
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .build();
        return imageConfig;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (mUserMoreInfoModel != null) {
            mUserMoreInfoModel.setBirthday(String.valueOf(year)
                    + "-" + String.valueOf(monthOfYear + 1)
                    + "-" + String.valueOf(dayOfMonth));
            setData(mUserMoreInfoModel);
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            if (pathList == null || pathList.size() == 0) return;
            fl = new File(pathList.get(0));
            if (fl.exists()) {
                // 上传图片
                mMyBusiness.upImage(APPCationStation.UPIMAGE, "上传图片中...", fl);
            }
        }
    }

    @Override
    public void onRefresh() {
        mMyBusiness.getuserInfo(APPCationStation.LOADING, "");
    }

    private void setData(UserMoreInfoModel mUserMoreInfoModel) {
        if (!TextUtils.isEmpty(mUserMoreInfoModel.getPicture()) && !TextUtils.isEmpty(mUserMoreInfoModel.getPicurl_prefix())) {
            mViewHolder.setImage(R.id.iv_head_image, mUserMoreInfoModel.getPicurl_prefix() + mUserMoreInfoModel.getPicture(), 0, 360);
        } else {
            mViewHolder.setImage(R.id.iv_head_image, R.drawable.content5);
        }
        if (!TextUtils.isEmpty(mUserMoreInfoModel.getName())) {
            mViewHolder.setText(R.id.tv_name, mUserMoreInfoModel.getName());
        } else {
            mViewHolder.setText(R.id.tv_name, "未填写");
        }
        mViewHolder.setText(R.id.tv_num, TCApplication.getmUserInfo().getPhone());
        mViewHolder.setText(R.id.tv_tcnum, TCApplication.getmUserInfo().getUid());
        if (!TextUtils.isEmpty(mUserMoreInfoModel.getSex())) {
            mViewHolder.setText(R.id.tv_sex, mUserMoreInfoModel.getSex());
        } else {
            mViewHolder.setText(R.id.tv_sex, "");
        }
        if (!TextUtils.isEmpty(mUserMoreInfoModel.getProvince()) && !TextUtils.isEmpty(mUserMoreInfoModel.getCity())) {
            mViewHolder.setText(R.id.tv_area, mUserMoreInfoModel.getProvince() + mUserMoreInfoModel.getCity());
        } else {
            mViewHolder.setText(R.id.tv_area, "");
        }
        if (!TextUtils.isEmpty(mUserMoreInfoModel.getBirthday())) {
            mViewHolder.setText(R.id.tv_birthday, mUserMoreInfoModel.getBirthday());
        } else {
            mViewHolder.setText(R.id.tv_birthday, "未填写");
        }
    }

    @Subscribe
    public void onEvent(CheckEvent event) {
        if (event == null || TextUtils.isEmpty(event.getMsg())) {
            return;
        }
        if (event.getMsg().startsWith("userinfo=")) {
            mUserMoreInfoModel.setName(event.getMsg().replace("userinfo=", ""));
            setData(mUserMoreInfoModel);
        }
        if (event.getMsg().startsWith("signature=")) {
            mUserMoreInfoModel.setSignature(event.getMsg().replace("signature=", ""));
            setData(mUserMoreInfoModel);
        }
        if (event.getMsg().startsWith("location=")) {
            mUserMoreInfoModel.setLocation(event.getMsg().replace("location=", ""));
            setData(mUserMoreInfoModel);
        }
        if (event.getMsg().startsWith("provinceCity=")) {
            String[] vlues = event.getMsg().split("=")[1].split(":");
            mUserMoreInfoModel.setProvince(vlues[0]);
            mUserMoreInfoModel.setCity(vlues[1]);
            setData(mUserMoreInfoModel);
        }
        if (event.getMsg().startsWith("mUserMoreInfoModel=")) {
            String json = event.getMsg().split("=")[1];
            UserMoreInfoModel mUserMoreInfoModel = JSON.parseObject(json, UserMoreInfoModel.class);
            if (mUserMoreInfoModel != null) {
                this.mUserMoreInfoModel = mUserMoreInfoModel;
                setData(mUserMoreInfoModel);
            }
        }
    }

    private void saveInfo() {
        try {
            JSONObject json_userprofile = new JSONObject();
            json_userprofile.put("name", mUserMoreInfoModel.getName());
            json_userprofile.put("location", mUserMoreInfoModel.getLocation());
            json_userprofile.put("sex", mUserMoreInfoModel.getSex());
            json_userprofile.put("province", mUserMoreInfoModel.getProvince());
            json_userprofile.put("city", mUserMoreInfoModel.getCity());
            json_userprofile.put("signature", mUserMoreInfoModel.getSignature());
            json_userprofile.put("birthday", mUserMoreInfoModel.getBirthday());
            json_userprofile.put("company", mUserMoreInfoModel.getCompany());
            json_userprofile.put("profession", mUserMoreInfoModel.getProfession());
            json_userprofile.put("school", mUserMoreInfoModel.getSchool());
            JSONObject json = new JSONObject();
            json.put("userprofile", json_userprofile);
            mMyBusiness.upOtherUserInfo(APPCationStation.UPOTHERINFO, "提交中...", mUserMoreInfoModel.getVer(), json.toString());
        } catch (Exception e) {
        }
    }
}
