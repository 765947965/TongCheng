package app.net.tongcheng.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import app.net.tongcheng.R;

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
}
