package app.net.tongcheng.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import app.net.tongcheng.R;
import app.net.tongcheng.activity.changeFriendUserName;
import app.net.tongcheng.util.Common;
import app.net.tongcheng.util.DialogUtil;

/**
 * Created by 76594 on 2017/7/23.
 */

public class FriendMoreInfoMenuDialog extends Dialog implements View.OnClickListener {
    private FriendMoreInfoMenuDialogListener mFriendMoreInfoMenuDialogListener;


    public FriendMoreInfoMenuDialog(Activity mActivity, FriendMoreInfoMenuDialogListener mFriendMoreInfoMenuDialogListener) {
        super(mActivity, R.style.quick_option_dialog);
        this.mFriendMoreInfoMenuDialogListener = mFriendMoreInfoMenuDialogListener;
        initView();
    }

    private void initView() {
        View contentView = getLayoutInflater().inflate(
                R.layout.dialog_friend_moreinfo_menu, null);
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
                if (mFriendMoreInfoMenuDialogListener != null) {
                    mFriendMoreInfoMenuDialogListener.onUpdateRemark();
                }
                break;
            case R.id.tv_delete:
                dismiss();
                if (mFriendMoreInfoMenuDialogListener != null) {
                    mFriendMoreInfoMenuDialogListener.deleteFriend();
                }
                break;
        }
    }

    public interface FriendMoreInfoMenuDialogListener {
        void onUpdateRemark();

        void deleteFriend();
    }
}
