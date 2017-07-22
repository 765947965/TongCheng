package app.net.tongcheng.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.NumberPicker;

import app.net.tongcheng.R;


/**
 * Created by 76594 on 2017/7/22.
 */

public class MyDatePickerDialog extends DatePickerDialog {


    public MyDatePickerDialog(Context context, OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
        super(context, R.style.datePickerTheme, listener, year, monthOfYear, dayOfMonth);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
//        getWindow().setGravity(Gravity.CENTER);

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth();
//        p.height = d.getHeight();
        getWindow().setAttributes(p);
    }

    @Override
    public void show() {
        super.show();
        hideDatePickerDay(this);
    }

    private void hideDatePickerDay(DatePickerDialog customDialog) {
        ViewGroup group = ((ViewGroup) ((ViewGroup) customDialog.getDatePicker().getChildAt(0))
                .getChildAt(0));
        boolean bool = false;
        for (int i = 0; i < group.getChildCount(); i++) {
            View view = group.getChildAt(i);
            String viewName = view.getClass().getName();
            if (bool) {
                view.setVisibility(View.GONE);
                continue;
            }
            if (view instanceof android.widget.NumberPicker) {
                int maxNum = ((NumberPicker) view).getMaxValue();
                if (maxNum == 11) bool = true;
            }
        }
    }
}
