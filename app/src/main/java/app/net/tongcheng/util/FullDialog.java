package app.net.tongcheng.util;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import app.net.tongcheng.R;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/3/24 9:49
 */
public abstract class FullDialog extends Dialog {

    private Activity activity;


    public FullDialog(Activity activity, int mResLayoutId) {
        super(activity, R.style.quick_option_dialog);
        this.activity = activity;
        initView(mResLayoutId);
    }

    private void initView(int mResLayoutId) {
        View contentView = getLayoutInflater().inflate(mResLayoutId, null);
        createView(new ViewHolder(contentView));
        super.setContentView(contentView);
    }

    @Override
    public void show() {
        if (activity.isFinishing()) {
            return;
        }
        super.show();
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

    public abstract void createView(ViewHolder mViewHolder);
}