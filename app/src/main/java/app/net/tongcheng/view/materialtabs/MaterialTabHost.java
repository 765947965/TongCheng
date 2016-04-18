package app.net.tongcheng.view.materialtabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import java.util.LinkedList;
import java.util.List;

import app.net.tongcheng.R;

/**
 * @Description : materialtabs
 * @Author : mingweigao / gaomingwei@tuandai.com
 * @Date : 2015/12/18.
 * @Version : 1.0
 */
@SuppressLint("InflateParams")
public class MaterialTabHost extends RelativeLayout {
    private int primaryColor;
    private int accentColor;
    private int textColor;
    private List<MaterialTab> tabs;

    private String type;
    private float density;
    private boolean scrollable;

    private HorizontalScrollView scrollView;
    private LinearLayout layout;
    private ImageButton left;
    private ImageButton right;

    private static int tabSelected;

    private boolean clickAnim;//是否开启点击动画

    public MaterialTabHost(Context context) {
        this(context, null);
    }

    public MaterialTabHost(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialTabHost(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        scrollView = new HorizontalScrollView(context);
        scrollView.setOverScrollMode(HorizontalScrollView.OVER_SCROLL_NEVER);
        scrollView.setHorizontalScrollBarEnabled(false);
        layout = new LinearLayout(context);
        scrollView.addView(layout);

        // get attributes
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaterialTabHost, 0, 0);

            try {
                // custom attributes
                type = a.getString(R.styleable.MaterialTabHost_type);
                primaryColor = a.getColor(R.styleable.MaterialTabHost_primaryColor, Color.parseColor("#FFFFFF"));
                accentColor = a.getColor(R.styleable.MaterialTabHost_accentColor, Color.parseColor("#00b0ff"));
                textColor = a.getColor(R.styleable.MaterialTabHost_textColor, Color.WHITE);
                clickAnim = a.getBoolean(R.styleable.MaterialTabHost_clickAnim, false);
            } finally {
                a.recycle();
            }
        } else {
            type = MaterialTab.TYPE_TEXT;
        }


        this.isInEditMode();
        scrollable = false;
        density = this.getResources().getDisplayMetrics().density;
        tabSelected = 0;

        // initialize tabs list
        tabs = new LinkedList<MaterialTab>();

        // set background color
        super.setBackgroundColor(primaryColor);
    }

    public void setPrimaryColor(int color) {
        this.primaryColor = color;

        this.setBackgroundColor(primaryColor);

        for (MaterialTab tab : tabs) {
            tab.setPrimaryColor(color);
        }
    }

    public void setClickAnim(boolean clickAnim) {
        this.clickAnim = clickAnim;
    }

    public void setAccentColor(int color) {
        this.accentColor = color;

        for (MaterialTab tab : tabs) {
            tab.setAccentColor(color);
        }
    }

    public void setTextColor(int color) {
        this.textColor = color;

        for (MaterialTab tab : tabs) {
            tab.setTextColor(color);
        }
    }

    public void addTab(MaterialTab tab) {
        // add properties to tab
        tab.setAccentColor(accentColor);
        tab.setPrimaryColor(primaryColor);
        tab.setTextColor(textColor);

        tab.setPosition(tabs.size());

        // insert new tab in list
        tabs.add(tab);

//        if(tabs.size() > 4) {
//            // switch tabs to scrollable before its draw
//            scrollable = true;
//        }
    }

    public MaterialTab newTab() {
        return new MaterialTab(this.getContext(), type, clickAnim);
    }

    public void setTabType(String type) {
        this.type = type;
    }

    public void setSelectedNavigationItem(int position) {
        if (position < 0 || position > tabs.size()) {
            throw new RuntimeException("Index overflow");
        } else {
            // tab at position will select, other will deselect
            for (int i = 0; i < tabs.size(); i++) {
                MaterialTab tab = tabs.get(i);

                if (i == position) {
                    tab.activateTab();
                } else {
                    tabs.get(i).disableTab();
                }
            }

            // move the tab if it is slidable
            if (scrollable) {
                scrollTo(position);
            }

            tabSelected = position;
        }

    }

    public void setSelectedNavigationItem(int position, float positionOffset) {
        if (position < 0 || position > tabs.size()) {
            throw new RuntimeException("Index overflow");
        } else {
            if (positionOffset > 0) {
                MaterialTab leftTab = tabs.get(position);
                MaterialTab rightTab = tabs.get(position + 1);

                leftTab.hide(positionOffset);
                rightTab.show(positionOffset);
            }
            // move the tab if it is slidable
            if (scrollable) {
                scrollTo(position);
            }

            tabSelected = position;
        }

    }


    private void scrollTo(int position) {
        int totalWidth = 0;//(int) ( 60 * density);
        for (int i = 0; i < position; i++) {
            int width = tabs.get(i).getView().getWidth();
            if (width == 0) {
                width = (int) (tabs.get(i).getTabMinWidth() + (24 * density));
            }

            totalWidth += width;
        }
        scrollView.smoothScrollTo(totalWidth, 0);
    }

    @Override
    public void removeAllViews() {
        for (int i = 0; i < tabs.size(); i++) {
            tabs.remove(i);
        }
        layout.removeAllViews();
        super.removeAllViews();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.getWidth() != 0 && tabs.size() != 0)
            notifyDataSetChanged();
    }


    public void notifyDataSetChanged() {
        super.removeAllViews();
        layout.removeAllViews();


        if (!scrollable) { // not scrollable tabs
            int tabWidth = this.getWidth() / tabs.size();

            // set params for resizing tabs width
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(tabWidth, HorizontalScrollView.LayoutParams.MATCH_PARENT);
            for (MaterialTab t : tabs) {
                layout.addView(t.getView(), params);
            }

        } else { //scrollable tabs
            for (int i = 0; i < tabs.size(); i++) {
                LinearLayout.LayoutParams params;
                MaterialTab tab = tabs.get(i);

                int tabWidth = (int) (tab.getTabMinWidth() + (24 * density)); // 12dp + text/icon width + 12dp

                if (i == 0) {
                    // first tab
                    View view = new View(layout.getContext());
                    view.setMinimumWidth((int) (60 * density));
                    layout.addView(view);
                }

                params = new LinearLayout.LayoutParams(tabWidth, HorizontalScrollView.LayoutParams.MATCH_PARENT);
                layout.addView(tab.getView(), params);

                if (i == tabs.size() - 1) {
                    // last tab
                    View view = new View(layout.getContext());
                    view.setMinimumWidth((int) (60 * density));
                    layout.addView(view);
                }
            }
        }

        LayoutParams paramsScroll = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.addView(scrollView, paramsScroll);

        this.setSelectedNavigationItem(tabSelected);
    }

    public MaterialTab getCurrentTab() {
        for (MaterialTab tab : tabs) {
            if (tab.isSelected())
                return tab;
        }

        return null;
    }

    public void setTabHintSpotVisibility(int item, int visibility) {
        if (tabs != null && tabs.size() > item) {
            tabs.get(item).setHintSpotVisibility(visibility);
        }
    }

}
