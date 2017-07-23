package app.net.tongcheng.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import app.net.tongcheng.R;

/**
 * Created by 76594 on 2017/7/23.
 */

public class FriendMoreInfoMenuDialog extends Dialog {


    public FriendMoreInfoMenuDialog(Context context) {
        super(context, R.style.quick_option_dialog);
        initView();
    }

    private void initView() {
        View contentView = getLayoutInflater().inflate(
                R.layout.dialog_friend_moreinfo_menu, null);
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
}
