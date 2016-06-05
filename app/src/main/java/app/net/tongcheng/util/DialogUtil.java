package app.net.tongcheng.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.List;

import app.net.tongcheng.Business.RedBusiness;
import app.net.tongcheng.R;
import app.net.tongcheng.adapter.MyBaseAdapter;
import app.net.tongcheng.model.CheckEvent;
import app.net.tongcheng.model.GiftsBean;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/16 16:39
 */
public class DialogUtil {

    /**
     * 弹出对话框
     */
    public static AlertDialog showTipsDialog(Activity activity, String title, String message, String comfirmText, String cancelText,
                                             final OnConfirmListener mListener) {
        try {
            if (activity.isFinishing()) {
                ToastUtil.showToast(message);
                return null;
            }
            final AlertDialog dialog = new AlertDialog.Builder(activity, R.style.dialog_style).create();
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
            View view = (View) LayoutInflater.from(activity).inflate(R.layout.view_dialog_tips_layout, null);
            dialog.setContentView(view);
            TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            if (TextUtils.isEmpty(title)) {
                txtTitle.setVisibility(View.GONE);
            } else {
                txtTitle.setVisibility(View.VISIBLE);
                txtTitle.setText(title);
            }
            TextView txtMessage = (TextView) view.findViewById(R.id.txtMessage);
            txtMessage.setText(message);

            Button comfirmBtn = (Button) view.findViewById(R.id.btnOk);
            comfirmBtn.setText(comfirmText);
            comfirmBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (mListener != null) {
                        mListener.clickConfirm();
                    }
                }
            });
            Button cancelBtn = (Button) view.findViewById(R.id.btnCancel);
            cancelBtn.setText(cancelText);
            cancelBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (mListener != null) {
                        mListener.clickCancel();
                    }
                }
            });
            return dialog;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Dialog loadingDialog(Activity mActivity, String mMessage) {
        try {
            if (mActivity.isFinishing()) {
                ToastUtil.showToast(mMessage);
                return null;
            }
            AlertDialog dialog = new AlertDialog.Builder(mActivity, R.style.dialog_style).create();
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
            View view = LayoutInflater.from(mActivity).inflate(R.layout.loading_layout, null);
            ((TextView) view.findViewById(R.id.tv_message)).setText(mMessage);
            dialog.setContentView(view);
            return dialog;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 弹出对话框
     */
    public static void showTipsDialog(Activity activity, int title, int message, int comfirmText, int cancelText,
                                      final OnConfirmListener mListener) {
        showTipsDialog(activity, activity.getString(title), activity.getString(message), activity.getString(comfirmText),
                activity.getString(cancelText), mListener);
    }

    public interface OnConfirmListener {
        public void clickConfirm();

        public void clickCancel();
    }

    public interface OnConfirmVipListener {
        public void clickConfirm(boolean isCheckMenoy);

        public void clickCancel();
    }

    public interface OnListDialogListener<T> {
        void CreateItem(ViewHolder holder, String item, List<String> list, int position);

        void OnItemSelect(View view, List<T> mDatas, T mItemdata, int position);
    }

    public static void showTipsDialog(Activity activity, String message, final OnConfirmListener listener) {
        showTipsDialog(activity, message, "确定", listener);
    }

    public static void showTipsDialog(Activity activity, String message, String confirmText, final OnConfirmListener mListener) {
        try {
            if (activity.isFinishing()) {
                return;
            }
            final AlertDialog dialog = new AlertDialog.Builder(activity, R.style.dialog_style).create();
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
            View view = (View) LayoutInflater.from(activity).inflate(R.layout.view_dialog_tips_layout, null);
            dialog.setContentView(view);
            TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            txtTitle.setVisibility(View.GONE);
            TextView txtMessage = (TextView) view.findViewById(R.id.txtMessage);
            txtMessage.setText(message);

            Button comfirmBtn = (Button) view.findViewById(R.id.btnOk);
            comfirmBtn.setBackgroundResource(R.drawable.bg_spinner_bottom_selector);
            comfirmBtn.setText(confirmText);
            comfirmBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.clickConfirm();
                    }
                    dialog.dismiss();
                }
            });
            View viewLine = view.findViewById(R.id.viewBtnLIne);
            viewLine.setVisibility(View.GONE);
            Button cancelBtn = (Button) view.findViewById(R.id.btnCancel);
            cancelBtn.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showTipsDialog(Activity activity, String message, final OnConfirmListener mListener, String title) {
        try {
            if (activity.isFinishing()) {
                return;
            }
            final AlertDialog dialog = new AlertDialog.Builder(activity, R.style.dialog_style).create();
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
            View view = (View) LayoutInflater.from(activity).inflate(R.layout.view_dialog_tips_layout, null);
            dialog.setContentView(view);
            TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            txtTitle.setText(title);
            TextView txtMessage = (TextView) view.findViewById(R.id.txtMessage);
            txtMessage.setText(message);

            Button comfirmBtn = (Button) view.findViewById(R.id.btnOk);
            comfirmBtn.setBackgroundResource(R.drawable.bg_spinner_bottom_selector);
            comfirmBtn.setText("确定");
            comfirmBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.clickConfirm();
                    }
                    dialog.dismiss();
                }
            });
            View viewLine = view.findViewById(R.id.viewBtnLIne);
            viewLine.setVisibility(View.GONE);
            Button cancelBtn = (Button) view.findViewById(R.id.btnCancel);
            cancelBtn.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static AlertDialog getExcreteRedDilaog(Activity mActivity, final GiftsBean mGiftsBean, final RedBusiness mRedBusiness) {
        if (mActivity == null || mActivity.isFinishing()) {
            return null;
        }
        final AlertDialog dialog = new AlertDialog.Builder(mActivity, R.style.quick_red_option_dialog).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        View view = LayoutInflater.from(mActivity).inflate(R.layout.red_excrete_dialog_layout, null);
        dialog.setContentView(view);
        TextView sendfromname_text = (TextView) view.findViewById(R.id.sendfromname_text);
        if (!TextUtils.isEmpty(mGiftsBean.getFromnickname())) {
            sendfromname_text.setText(mGiftsBean.getFromnickname());
        } else {
            if ("aixin_money".equals(mGiftsBean.getType()) || "system".equals(mGiftsBean.getFrom().trim())) {
                sendfromname_text.setText("同城商城");
            } else {
                sendfromname_text.setText(mGiftsBean.getFrom());
            }
        }
        TextView sendfromname_messages_text = (TextView) view.findViewById(R.id.sendfromname_messages_text);
        if (!TextUtils.isEmpty(mGiftsBean.getName())) {
            sendfromname_messages_text.setText("给您发来" + mGiftsBean.getName());
        }
        TextView red_anim_text = (TextView) view.findViewById(R.id.red_anim_text);
        red_anim_text.setText(mGiftsBean.getTips());
        final TextView red_errortext = (TextView) view.findViewById(R.id.red_errortext);
        final ImageView red_anim_image = (ImageView) view.findViewById(R.id.red_anim_image);
        red_anim_image.setImageResource(R.drawable.rpopen);
        red_anim_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                red_anim_image.setEnabled(false);
                red_errortext.setText("");
                // 开启动画
                try {
                    red_anim_image.setImageResource(R.drawable.redcheckoutanim);
                    AnimationDrawable animationDrawable = (AnimationDrawable) red_anim_image
                            .getDrawable();
                    animationDrawable.start();
                } catch (Exception e) {
                    red_anim_image.setImageResource(R.drawable.rpopen);
                }
                mRedBusiness.excreteRed(APPCationStation.EXCRETERED, "", mGiftsBean.getGift_id(), "open");
            }
        });
        view.findViewById(R.id.red_closedbt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public static void showExcreteRedTipsDilaog(Activity mActivity, int nums) {
        if (mActivity == null || mActivity.isFinishing()) {
            return;
        }
        final AlertDialog dialog = new AlertDialog.Builder(mActivity, R.style.quick_red_option_dialog).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        View view = LayoutInflater.from(mActivity).inflate(R.layout.red_excrete_dialog_nums, null);
        dialog.setContentView(view);
        ViewHolder mViewHolder = new ViewHolder(view);
        mViewHolder.setText(R.id.numstext, nums + "");
        mViewHolder.setText(R.id.sendfromname_messages_text, "你有" + nums + "个未拆红包");
        mViewHolder.getView(R.id.red_anim_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new CheckEvent("MainActivity.page.0"));
                dialog.dismiss();
            }
        });
        mViewHolder.getView(R.id.red_closedbt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public static void showListDilaog(Activity mActivity, List<String> mDatas, String title, int itemLayoutId, final OnListDialogListener<String> mOnListDialogListener) {
        if (mActivity == null || mActivity.isFinishing()) {
            return;
        }
        final AlertDialog dialog = new AlertDialog.Builder(mActivity, R.style.quick_red_option_dialog).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        View view = LayoutInflater.from(mActivity).inflate(R.layout.red_change_year_dialog_layout, null);
        dialog.setContentView(view);
        ((TextView) view.findViewById(R.id.tv_title)).setText(title);
        ListView mListView = (ListView) view.findViewById(R.id.mListView);
        mListView.setAdapter(new MyBaseAdapter<String>(mListView, mActivity, mDatas, itemLayoutId) {
            @Override
            protected void convert(ViewHolder holder, String item, List<String> list, int position) {
                if (mOnListDialogListener != null) {
                    mOnListDialogListener.CreateItem(holder, item, list, position);
                }
            }

            @Override
            protected void MyonItemClick(AdapterView<?> parent, View view, String item, List<String> list, int position, long id) {
                if (mOnListDialogListener != null) {
                    mOnListDialogListener.OnItemSelect(view, list, item, position);
                }
                dialog.dismiss();
            }
        });
    }
}
