package app.net.tongcheng.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2015 Tuandai Inc. All rights reserved.
 * @date: 2015/12/17 9:37
 */
public class AutoFocusDownOrUpScrollView extends ScrollView {
    public AutoFocusDownOrUpScrollView(Context context) {
        super(context);
    }

    public AutoFocusDownOrUpScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoFocusDownOrUpScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        try {
            if (h < oldh) {
                this.fullScroll(ScrollView.FOCUS_DOWN);
            } else {
                this.fullScroll(ScrollView.FOCUS_UP);
            }
        } catch (Exception e) {

        }
    }
}
