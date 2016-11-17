package app.net.tongcheng.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Timer;
import java.util.TimerTask;

import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.activity.SetRetrievePassword;
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/3/24 9:49
 */
public class InputObjectDialog extends Dialog implements View.OnClickListener {
    private Activity activity;

    private ViewHolder mViewHolder;
    private EditText edt_pd_input;
    private ImageView iv_set_style;
    private TextView tv_password_forget;

    private InvestPayDialogType mType;
    private InvestPayObjectDialogListener mListener;

    private CountDowner mCd;
    private VoiceCodeCountDowner mVCd;

    private String phoneNum;

    private boolean isCanSendVoiceCode;

    private int SendVoiceCodeTime;

    private boolean isContinueLastTime;//在上一次发送验证码后 弹窗dismiss 第二次继续弹出后 验证码倒计时是否继续上一次

    public enum InvestPayDialogType {
        CODE, PASSWORD
    }

    public enum InvestSendCodeType {
        CODE, VOICE_CODE
    }

    public interface InvestPayObjectDialogListener {
        void submitPassword(String password);//提交密码

        void submitCode(String code);//提交验证码

        void getCode(InvestSendCodeType mType);//请求验证码
    }


    public InputObjectDialog(Activity activity, boolean isContinueLastTime, InvestPayObjectDialogListener mListener) {
        super(activity, R.style.quick_option_dialog);
        this.activity = activity;
        this.isContinueLastTime = isContinueLastTime;
        this.mListener = mListener;
        initView();
        setCanceledOnTouchOutside(false);
    }

    private void initView() {
        View contentView = getLayoutInflater().inflate(
                R.layout.dialog_invest_password, null);
        mViewHolder = new ViewHolder(contentView, this);
        edt_pd_input = mViewHolder.getView(R.id.edt_pd_input);
        iv_set_style = mViewHolder.getView(R.id.iv_set_style);
        tv_password_forget = mViewHolder.getView(R.id.tv_password_forget);
        mViewHolder.setOnClickListener(R.id.iv_close);
        mViewHolder.setOnClickListener(R.id.btnCancel);
        mViewHolder.setOnClickListener(R.id.tv_password_forget);
        mViewHolder.setOnClickListener(R.id.btnSubmit);
        mViewHolder.setOnClickListener(R.id.iv_set_style);
        super.setContentView(contentView);
    }

    /**
     * 显示验证码弹窗
     *
     * @param phoneNum
     */
    public void showCodeDialog(boolean isShowVoidCode, String phoneNum) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (!isContinueLastTime) {
            disCut();
        }
        this.phoneNum = phoneNum;
        mType = InvestPayDialogType.CODE;
        mViewHolder.setText(R.id.tvTitle, "请输入验证码");
        mViewHolder.setText(R.id.tv_desc, "正在发送验证码至" + phoneNum.substring(0, 3) + "****" + phoneNum.substring(7));
        mViewHolder.setVisibility(R.id.tv_payment_amount, View.GONE);
        mViewHolder.setVisibility(R.id.iv_set_style, View.GONE);
        setInputNumberType();
        edt_pd_input.setText("");
        edt_pd_input.setHint("请输入验证码");
        tv_password_forget.setTextColor(Color.parseColor("#fd7575"));
        mViewHolder.setText(R.id.tv_password_forget, "重新发送");
        tv_password_forget.setEnabled(true);
        mViewHolder.getView(R.id.btnSubmit).setEnabled(true);
        if (isShowVoidCode) {
            isCanSendVoiceCode = true;
            mViewHolder.setVisibility(R.id.lly_pac_LeftTips, View.VISIBLE).setOnClickListener(this);
        } else {
            mViewHolder.setVisibility(R.id.lly_pac_LeftTips, View.GONE);
        }
        if (!isShowing()) {
            show();
            setInputMethodVisiable(edt_pd_input);
        }
        if (isContinueLastTime && mCd != null) {
            // 上次发送验证码还未超过90秒
            mViewHolder.setText(R.id.tv_desc, "验证码已发送至" + phoneNum.substring(0, 3) + "****" + phoneNum.substring(7));
        } else {
            if (mListener != null) {
                // 请求验证码
                mListener.getCode(InvestSendCodeType.CODE);
            }
        }
    }

    /**
     * 弹出密码输入框
     *
     * @param payment_amount
     */
    public void showPasswordDialog(double payment_amount, String mTitle) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (!isContinueLastTime) {
            disCut();
        }
        mType = InvestPayDialogType.PASSWORD;
        mViewHolder.setText(R.id.tvTitle, "请输入交易密码");
        mViewHolder.setText(R.id.tv_desc, mTitle);
        mViewHolder.setVisibility(R.id.tv_payment_amount, View.VISIBLE);
        mViewHolder.setText(R.id.tv_payment_amount, String.valueOf(payment_amount));
        mViewHolder.setVisibility(R.id.iv_set_style, View.VISIBLE);
        mViewHolder.setVisibility(R.id.lly_pac_LeftTips, View.GONE);
        setInputType(false);
        edt_pd_input.setText("");
        edt_pd_input.setHint("请输入交易密码");
        tv_password_forget.setTextColor(Color.parseColor("#fd7575"));
        mViewHolder.setText(R.id.tv_password_forget, "忘记密码");
        tv_password_forget.setEnabled(true);
        mViewHolder.getView(R.id.btnSubmit).setEnabled(true);
        if (!isShowing()) {
            show();
            setInputMethodVisiable(edt_pd_input);
        }
    }

    /**
     * 设置输入框为纯数字输入
     */
    private void setInputNumberType() {
        edt_pd_input.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    /**
     * 设置输入框为密码的明密文
     *
     * @param isExpress
     */
    public void setInputType(boolean isExpress) {
        if (!isExpress) {
            iv_set_style.setTag(false);
            iv_set_style.setImageResource(R.drawable.withdraw_transaction_password_hidden);
            edt_pd_input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            iv_set_style.setTag(true);
            iv_set_style.setImageResource(R.drawable.withdraw_transaction_password_display);
            edt_pd_input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        Editable etable = edt_pd_input.getText();
        Selection.setSelection(etable, etable.length());
    }

    /**
     * 清除输入框
     */
    public void clearInputPwdEditView() {
        if (!isShowing() || activity == null || activity.isFinishing()) {
            return;
        }
        edt_pd_input.setText("");
    }

    /**
     * 弹出输入法
     *
     * @param edt
     */
    private void setInputMethodVisiable(final EditText edt) {
        edt.requestFocus();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) edt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(edt, 0);
            }
        }, 250);
    }

    /**
     * 获取验证码成功
     */
    public void getCodeSuccess() {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        mViewHolder.setText(R.id.tv_desc, "验证码已发送至" + phoneNum.substring(0, 3) + "****" + phoneNum.substring(7));
        mCd = new CountDowner(90000, 1000);
        mCd.start();
    }

    /**
     * 获取语音验证码成功
     */
    public void getVoidCodeSuccess() {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        mVCd = new VoiceCodeCountDowner(90000, 1000);
        mVCd.start();
    }

    /**
     * 获取验证码失败
     */
    public void getCodeFailure() {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        mViewHolder.setText(R.id.tv_desc, "验证码发送失败!");
    }

    /**
     * 交易密码或者验证码提交失败
     */
    public void submitInputFailure() {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        mViewHolder.getView(R.id.btnSubmit).setEnabled(true);

    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setGravity(Gravity.CENTER);

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth();
        getWindow().setAttributes(p);
    }

    @Override
    public void onClick(View v) {
        if (!isShowing() || activity == null || activity.isFinishing()) {
            return;
        }
        switch (v.getId()) {
            case R.id.btnCancel:
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.btnSubmit:
                String input = edt_pd_input.getText().toString();
                if (mType == InvestPayDialogType.CODE) {
                    if (TextUtils.isEmpty(input)) {
                        ToastUtil.showToast("请输入验证码");
                    } else {
                        if (mListener != null) {
                            mViewHolder.getView(R.id.btnSubmit).setEnabled(false);
                            mListener.submitCode(input);
                        }
                    }
                } else {
                    if (TextUtils.isEmpty(input)) {
                        ToastUtil.showToast("请输入交易密码");
                    } else {
                        if (mListener != null) {
                            mViewHolder.getView(R.id.btnSubmit).setEnabled(false);
                            mListener.submitPassword(input);
                        }
                    }
                }
                break;
            case R.id.iv_set_style:
                setInputType(!(boolean) iv_set_style.getTag());
                break;
            case R.id.tv_password_forget:
                if (mType == InvestPayDialogType.CODE) {
                    if (mListener != null) {
                        // 请求验证码
                        mViewHolder.setText(R.id.tv_desc, "正在发送验证码至" + phoneNum.substring(0, 3) + "****" + phoneNum.substring(7));
                        mListener.getCode(InvestSendCodeType.CODE);
                    }
                } else {
                    activity.startActivity(new Intent(TCApplication.mContext, SetRetrievePassword.class));
                }
                break;
            case R.id.lly_pac_LeftTips:
                if (mListener != null) {
                    if (isCanSendVoiceCode) {
                        // 请求语音验证码
                        mListener.getCode(InvestSendCodeType.VOICE_CODE);
                    } else {
                        ToastUtil.showToast("请稍后，等待" + SendVoiceCodeTime + "秒后，方能重新发送语音验证码。");
                    }
                }
                break;
        }
    }

    public InvestPayDialogType getmType() {
        return mType;
    }

    @Override
    public void dismiss() {
        if (!isContinueLastTime) {
            disCut();
        }
        super.dismiss();
    }

    public void disCut() {
        if (mCd != null) {
            mCd.cancel();
            mCd = null;
        }
        if (mVCd != null) {
            mVCd.cancel();
            mVCd = null;
        }
    }

    class CountDowner extends CountDownTimer {
        CountDowner(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            try {
                if (isShowing() && mType == InvestPayDialogType.CODE && tv_password_forget != null) {
                    tv_password_forget.setEnabled(true);
                    tv_password_forget.setTextColor(Color.parseColor("#fd7575"));
                    mViewHolder.setText(R.id.tv_password_forget, "重新发送");
                    mCd = null;
                }
            } catch (Exception e) {
                mCd = null;
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            try {
                if (isShowing() && mType == InvestPayDialogType.CODE && tv_password_forget != null) {
                    tv_password_forget.setEnabled(false);
                    tv_password_forget.setTextColor(Color.parseColor("#a1a1a1"));
                    tv_password_forget.setText(String.valueOf(millisUntilFinished / 1000) + "秒后重发");
                }
            } catch (Exception e) {
            }
        }

    }

    class VoiceCodeCountDowner extends CountDownTimer {
        VoiceCodeCountDowner(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            if (isShowing() && mType == InvestPayDialogType.CODE) {
                isCanSendVoiceCode = true;
                mVCd = null;
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            try {
                if (isShowing() && mType == InvestPayDialogType.CODE) {
                    isCanSendVoiceCode = false;
                    SendVoiceCodeTime = (int) (millisUntilFinished / 1000);
                }
            } catch (Exception e) {
            }
        }

    }
}