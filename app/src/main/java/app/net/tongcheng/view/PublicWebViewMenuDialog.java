package app.net.tongcheng.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import app.net.tongcheng.R;

/**
 * Created by 76594 on 2017/7/23.
 */

public class PublicWebViewMenuDialog extends Dialog implements View.OnClickListener {
    private MenuDialogListener mMenuDialogListener;


    public PublicWebViewMenuDialog(Activity mActivity, MenuDialogListener mMenuDialogListener) {
        super(mActivity, R.style.quick_option_dialog);
        this.mMenuDialogListener = mMenuDialogListener;
        initView();
    }

    private void initView() {
        View contentView = getLayoutInflater().inflate(
                R.layout.public_webview_menu, null);
        contentView.findViewById(R.id.tv_updata_name).setOnClickListener(this);
        contentView.findViewById(R.id.tv_delete).setOnClickListener(this);
        setContentView(contentView);
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
        switch (v.getId()) {
            case R.id.tv_updata_name:
                dismiss();
                if (mMenuDialogListener != null) {
                    mMenuDialogListener.onUpdateRemark();
                }
                break;
            case R.id.tv_delete:
                dismiss();
                if (mMenuDialogListener != null) {
                    mMenuDialogListener.deleteFriend();
                }
                break;
        }
    }

    public interface MenuDialogListener {
        void onUpdateRemark();

        void deleteFriend();
    }
}
