package app.net.tongcheng.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import app.net.tongcheng.R;
import app.net.tongcheng.util.ViewHolder;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/16 16:51
 */
public abstract class FullScreenDialog extends Dialog implements View.OnClickListener {

    private ViewHolder mViewHolder;
    private Activity activity;


    public FullScreenDialog(Activity activity, int ResLayoutId) {
        super(activity, R.style.quick_option_dialog);
        this.activity = activity;
        mViewHolder = new ViewHolder(getLayoutInflater().inflate(ResLayoutId, null), this);
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
        CreatView(mViewHolder);
    }

    @Override
    public void onClick(View v) {
        FDonClick(v);
    }

    public abstract void FDonClick(View v);

    public abstract void CreatView(ViewHolder mViewHolder);
}
