package app.net.tongcheng.view.materialtabs;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Locale;

import app.net.tongcheng.R;
import app.net.tongcheng.util.PictureLoader;
import at.markushi.ui.RevealColorView;

/**
 * @Description : materialtabs
 * @Author : mingweigao / gaomingwei@tuandai.com
 * @Date : 2015/12/18.
 * @Version : 1.0
 */
@SuppressLint({"InflateParams", "ClickableViewAccessibility"})
public class MaterialTab implements View.OnTouchListener {

    private final static int REVEAL_DURATION = 100;
    private final static int HIDE_DURATION = 200;

    public final static String TYPE_TEXT = "text";
    public final static String TYPE_ICON = "icon";
    public final static String TYPE_LARGE_ICON = "large_icon";
    public final static String TYPE_ICON_TEXT = "icon_text";

    private final static int COLOR_DEFAULT_NORMAL = Color.parseColor("#626262");
    private final static int COLOR_DEFAULT_ACTIVATED = Color.parseColor("#fecb01");


    private View completeView;


    private ImageView iv_icon_activate;
    private ImageView iv_icon;
    private TextView tv_title;


    private RevealColorView background;
    private ImageView selector;
    private ImageView divider;
    private Resources res;
    private MaterialTabListener listener;

    //icon resource
    private int icon;
    private int iconNormal;
    private int iconActivated;
    private String iconNormalUrl;
    private String iconActivatedRul;
    private PictureLoader iconNormalLoader;
    private PictureLoader iconActivatedLoader;

    private int textColor;
    private int textColorNormal;
    private int textColorActivated;

    private int primaryColor;
    private int accentColor;

    private boolean active;
    private int position;
    private float density;
    private Point lastTouchedPoint;
    private String type;

    private boolean clickAnim;//是否开启点击动画

    private View iconView;
    private View textView;

    public MaterialTab(Context ctx, String type, boolean clickAnim) {
        density = ctx.getResources().getDisplayMetrics().density;
        res = ctx.getResources();
        this.clickAnim = clickAnim;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            completeView = LayoutInflater.from(ctx).inflate(R.layout.tab, null);
        } else {
            completeView = LayoutInflater.from(ctx).inflate(R.layout.material_tab, null);
            background = (RevealColorView) completeView.findViewById(R.id.reveal);
        }
        selector = (ImageView) completeView.findViewById(R.id.selector);
        divider = (ImageView) completeView.findViewById(R.id.divider);
        this.type = type;
        if (type.equals(TYPE_ICON)) {
            iconView = ((ViewStub) completeView.findViewById(R.id.vs_icon)).inflate();
            iv_icon = (ImageView) iconView.findViewById(R.id.iv_icon);
            iv_icon_activate = (ImageView) iconView.findViewById(R.id.iv_icon_activate);
            textColor = -1;
        } else if (type.equals(TYPE_LARGE_ICON)) {
            iconView = ((ViewStub) completeView.findViewById(R.id.vs_icon_large)).inflate();
            iv_icon = (ImageView) iconView.findViewById(R.id.iv_icon);
            iv_icon_activate = (ImageView) iconView.findViewById(R.id.iv_icon_activate);
            textColor = -1;
        } else if (type.equals(TYPE_ICON_TEXT)) {

            iconView = ((ViewStub) completeView.findViewById(R.id.vs_icon)).inflate();
            iv_icon = (ImageView) iconView.findViewById(R.id.iv_icon);
            iv_icon_activate = (ImageView) iconView.findViewById(R.id.iv_icon_activate);

            textView = ((ViewStub) completeView.findViewById(R.id.vs_text)).inflate();
            tv_title = (TextView) textView.findViewById(R.id.tv_title);

            textColor = COLOR_DEFAULT_NORMAL;

        } else {
            icon = -1;
            textView = ((ViewStub) completeView.findViewById(R.id.vs_text)).inflate();
            tv_title = (TextView) textView.findViewById(R.id.tv_title);

            textColor = COLOR_DEFAULT_NORMAL;
        }

        completeView.setOnTouchListener(this);
        active = false;
    }

    public void setHintSpotVisibility(int visibility) {
        completeView.findViewById(R.id.iv_tab_hint_spot).setVisibility(visibility);
    }

    public void setAccentColor(int color) {
        this.accentColor = color;
        this.textColor = color;
    }

    public void setPrimaryColor(int color) {
        this.primaryColor = color;

        if (deviceHaveRippleSupport()) {
            background.setBackgroundColor(color);
        } else {
            completeView.setBackgroundColor(color);
        }
    }

    public void setTextColor(int color) {
        textColor = color;
        if (tv_title != null) {
            tv_title.setTextColor(color);
        }
    }

    public MaterialTab setTextSize(float size) {
        tv_title.setTextSize(size);
        return this;
    }

    public MaterialTab setText(CharSequence text) {
        return setText(text, -1, -1);
    }

    public MaterialTab setText(CharSequence text, int textColorNormal, int textColorActivated) {
        if (tv_title != null) {
            this.tv_title.setText(text.toString().toUpperCase(Locale.US));
            if (-1 == textColorNormal) {
                this.textColorNormal = COLOR_DEFAULT_NORMAL;
            } else {
                this.textColorNormal = textColorNormal;
            }

            if (-1 == textColorActivated) {
                this.textColorActivated = COLOR_DEFAULT_ACTIVATED;
            } else {
                this.textColorActivated = textColorActivated;
            }
        }
        return this;
    }

    public MaterialTab setIcon(int iconNormal) {
        return setIcon(iconNormal, -1);
    }

    public MaterialTab setIcon(int iconNormal, int iconActivated) {
        this.iconNormal = iconNormal;
        this.iconActivated = iconActivated;
        iconNormalLoader = new PictureLoader(this.iconNormal);
        iconActivatedLoader = new PictureLoader(this.iconActivated);
        return this;
    }

    public MaterialTab setIconUrl(String iconNormalUrl, String iconActivatedRul) {
        this.iconNormalUrl = iconNormalUrl;
        this.iconActivatedRul = iconActivatedRul;
        return this;
    }

    public void hide(float positionOffset) {
        int c0 = (Integer) evaluate(positionOffset, textColorActivated, textColorNormal);
        if (tv_title != null) {
            tv_title.setTextColor(c0);
        }
        if (this.iv_icon != null) {
            icon = iconNormal;
            if (-1 == iconActivated) {
                setImageViewIcon(iconNormalLoader, iconNormalUrl, iconNormal, iv_icon_activate);
                setImageViewIcon(iconNormalLoader, iconNormalUrl, iconNormal, iv_icon);
                //iv_icon_activate.setImageDrawable(res.getDrawable(iconNormal));
                //iv_icon.setImageDrawable(res.getDrawable(iconNormal));
                iv_icon_activate.setAlpha(0xFF);
                iv_icon.setAlpha(0x99);
            } else {
                setImageViewIcon(iconActivatedLoader, iconActivatedRul, iconActivated, iv_icon_activate);
                setImageViewIcon(iconNormalLoader, iconNormalUrl, iconNormal, iv_icon);
                //iv_icon_activate.setImageDrawable(res.getDrawable(iconActivated));
                //iv_icon.setImageDrawable(res.getDrawable(iconNormal));
                iv_icon_activate.setAlpha(1 - positionOffset);
                iv_icon.setAlpha(positionOffset);

            }
        }
    }

    public void show(float positionOffset) {
        int c1 = (Integer) evaluate(positionOffset, textColorNormal, textColorActivated);
        if (tv_title != null) {
            tv_title.setTextColor(c1);
        }
        if (this.iv_icon != null) {
            icon = iconActivated;
            if (-1 == iconActivated) {
                setImageViewIcon(iconNormalLoader, iconNormalUrl, iconNormal, iv_icon_activate);
                setImageViewIcon(iconNormalLoader, iconNormalUrl, iconNormal, iv_icon);
                //iv_icon_activate.setImageDrawable(res.getDrawable(iconNormal));
                //iv_icon.setImageDrawable(res.getDrawable(iconNormal));
                iv_icon_activate.setAlpha(0x99);
                iv_icon.setAlpha(0xFF);
            } else {
                setImageViewIcon(iconActivatedLoader, iconActivatedRul, iconActivated, iv_icon_activate);
                setImageViewIcon(iconNormalLoader, iconNormalUrl, iconNormal, iv_icon);
                //iv_icon_activate.setImageDrawable(res.getDrawable(iconActivated));
                //iv_icon.setImageDrawable(res.getDrawable(iconNormal));
                iv_icon.setAlpha(1 - positionOffset);
                iv_icon_activate.setAlpha(positionOffset);
            }
        }

    }

    private void setDisableIcon() {
        if (this.iv_icon != null) {
            icon = iconNormal;
            if (-1 == iconActivated) {
                setIconAlpha(0x99);
                setImageViewIcon(iconNormalLoader, iconNormalUrl, iconNormal, iv_icon);
                //iv_icon.setImageDrawable(res.getDrawable(icon));
            } else {
                setImageViewIcon(iconActivatedLoader, iconActivatedRul, iconActivated, iv_icon_activate);
                setImageViewIcon(iconNormalLoader, iconNormalUrl, iconNormal, iv_icon);
                //iv_icon_activate.setImageDrawable(res.getDrawable(iconActivated));
                //iv_icon.setImageDrawable(res.getDrawable(iconNormal));
                iv_icon_activate.setAlpha(0.0f);
                iv_icon.setAlpha(1.0f);
            }
        }

    }

    private void setActivateIcon() {
        if (this.iv_icon != null) {
            icon = iconActivated;
            if (-1 == iconActivated) {
                setIconAlpha(0xFF);
                setImageViewIcon(iconNormalLoader, iconNormalUrl, iconNormal, iv_icon);
                //iv_icon.setImageDrawable(res.getDrawable(iconNormal));
            } else {
                setImageViewIcon(iconActivatedLoader, iconActivatedRul, iconActivated, iv_icon_activate);
                setImageViewIcon(iconNormalLoader, iconNormalUrl, iconNormal, iv_icon);
                //iv_icon_activate.setImageDrawable(res.getDrawable(iconActivated));
                //iv_icon.setImageDrawable(res.getDrawable(iconNormal));
                iv_icon_activate.setAlpha(1.0f);
                iv_icon.setAlpha(0.0f);
            }
        }
    }

    private void setImageViewIcon(PictureLoader mPctloder, String imageUrl, int imageReid, ImageView Imv) {
        if (TextUtils.isEmpty(imageUrl == null ? null : imageUrl.trim())) {
            Imv.setImageResource(imageReid);
        } else {
            mPctloder.displayImage(imageUrl, Imv);
        }
    }

    private void setDisableText() {
        if (tv_title != null) {
            textColor = textColorNormal;
            if (-1 != textColor) {
                this.tv_title.setTextColor(textColor);
            }
        }
    }

    private void setActivateText() {
        if (tv_title != null) {
            textColor = textColorActivated;
            if (-1 != textColor) {
                this.tv_title.setTextColor(textColor);
            }
        }
    }

    public void disableTab() {

        if (type.equals(TYPE_ICON) || type.equals(TYPE_LARGE_ICON)) {
            setDisableIcon();
        } else if (type.equals(TYPE_ICON_TEXT)) {
            setDisableIcon();
            setDisableText();
        } else {
            setDisableText();
        }
        // set transparent the selector view
        this.selector.setBackgroundColor(res.getColor(android.R.color.transparent));
        active = false;

        if (listener != null)
            listener.onTabUnselected(this);
    }

    public void activateTab() {
        if (type.equals(TYPE_ICON) || type.equals(TYPE_LARGE_ICON)) {
            setActivateIcon();
        } else if (type.equals(TYPE_ICON_TEXT)) {
            setActivateIcon();
            setActivateText();
        } else {
            setActivateText();
        }
        this.selector.setBackgroundColor(accentColor);
        active = true;
    }

    public boolean isSelected() {
        return active;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        lastTouchedPoint = new Point();
        lastTouchedPoint.x = (int) event.getX();
        lastTouchedPoint.y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (clickAnim && !deviceHaveRippleSupport()) {
                completeView.setBackgroundColor(Color.argb(0x80, Color.red(accentColor), Color.green(accentColor), Color.blue(accentColor)));
            }
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_CANCEL) {
            if (clickAnim && !deviceHaveRippleSupport()) {
                completeView.setBackgroundColor(primaryColor);
            }
            return true;
        }

        // new effects
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (clickAnim) {
                if (!deviceHaveRippleSupport()) {
                    completeView.setBackgroundColor(primaryColor);
                } else {
                    // set the backgroundcolor
                    this.background.reveal(lastTouchedPoint.x, lastTouchedPoint.y, Color.argb(0x80, Color.red(accentColor), Color.green(accentColor), Color.blue(accentColor)), 0, REVEAL_DURATION, new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            background.reveal(lastTouchedPoint.x, lastTouchedPoint.y, primaryColor, 0, HIDE_DURATION, null);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });
                }
            }

            // set the click
            if (listener != null) {
                listener.onTabSelected(this);
            }
            // if the tab is not activated, it will be active
            if (!active)
                this.activateTab();

            return true;
        }

        return false;
    }

    public View getView() {
        return completeView;
    }

    public MaterialTab setTabListener(MaterialTabListener listener) {
        this.listener = listener;
        return this;
    }

    public MaterialTabListener getTabListener() {
        return listener;
    }


    public int getPosition() {
        return position;
    }


    public void setPosition(int position) {
        this.position = position;
    }

    @SuppressLint({"NewApi"})
    private void setIconAlpha(int paramInt) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.iv_icon.setImageAlpha(paramInt);
            return;
        }
    }

    private int getTextLenght() {
        String textString = tv_title.getText().toString();
        Rect bounds = new Rect();
        Paint textPaint = tv_title.getPaint();
        textPaint.getTextBounds(textString, 0, textString.length(), bounds);
        return bounds.width();
    }

    private boolean deviceHaveRippleSupport() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return false;
        } else {
            return true;
        }

    }

    private int getIconWidth() {
        return (int) (density * 24);
    }

    public int getTabMinWidth() {
        if (type.equals(TYPE_ICON)) {
            return getIconWidth();
        } else {
            return getTextLenght();
        }

    }

    public MaterialTab setSelectorVisibility(int visibility) {
        selector.setVisibility(visibility);
        return this;
    }

    public MaterialTab setDividerVisibility(int visibility) {
        divider.setVisibility(visibility);
        return this;
    }


    public Object evaluate(float fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (int) ((startA + (int) (fraction * (endA - startA))) << 24) |
                (int) ((startR + (int) (fraction * (endR - startR))) << 16) |
                (int) ((startG + (int) (fraction * (endG - startG))) << 8) |
                (int) ((startB + (int) (fraction * (endB - startB))));
    }

}
