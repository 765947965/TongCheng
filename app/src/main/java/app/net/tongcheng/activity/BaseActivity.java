package app.net.tongcheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import app.net.tongcheng.R;
import app.net.tongcheng.TCApplication;
import app.net.tongcheng.model.CheckEvent;
import app.net.tongcheng.util.HttpBusinessListener;
import app.net.tongcheng.view.SlidingLayout;


/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/3/21 17:24
 */
public abstract class BaseActivity extends AppCompatActivity implements HttpBusinessListener {

    private LinearLayout lltMain;
    private RelativeLayout rlt_title;
    private FrameLayout flt_root;
    public boolean isLoadData;
    private TextView tv_title;
    private ImageView bt_close;
    private Button btnRight;
    private ImageView ivRight;
    private SlidingLayout rootView;
    private View status;
    private boolean isRegisterEventBus;
    private long lastClickTime;
    private boolean checkLoad = true;//是否检测登录状态
    public Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                mHandDoSomeThing(msg);
            } catch (Exception e) {
                e.toString();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        initView();
//        if (android.os.Build.VERSION.SDK_INT >= 21) {
////            导航栏
////            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            status = findViewById(R.id.view_status_bar);
//            status.setVisibility(View.VISIBLE);
//        }
//        rootView = new SlidingLayout(this);
//        rootView.bindActivity(this);
    }

    public void setEventBus() {
        if (!isRegisterEventBus) {
            isRegisterEventBus = true;
            EventBus.getDefault().register(this);
        }
    }

    public void sendEventBusMessage(String messsage) {
        EventBus.getDefault().post(new CheckEvent(messsage));
    }

    public void setStatusColor(int mColor) {
        if (status != null) {
            status.setBackgroundColor(mColor);
        }
    }

    private void initView() {
        lltMain = (LinearLayout) findViewById(R.id.llt_main);
        rlt_title = (RelativeLayout) findViewById(R.id.rlt_title);
        flt_root = (FrameLayout) findViewById(R.id.flt_root);
        tv_title = (TextView) findViewById(R.id.tv_title);
        bt_close = (ImageView) findViewById(R.id.bt_close);
        btnRight = (Button) findViewById(R.id.btnRight);
        ivRight = (ImageView) findViewById(R.id.ivRight);
        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public Button getRightBtn() {
        return btnRight;
    }

    public ImageView getRightIV() {
        return ivRight;
    }

    public ImageView getLeftClose() {
        return bt_close;
    }

    public void setTileLineGONE() {
        findViewById(R.id.viewBaseLine).setVisibility(View.GONE);
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            rlt_title.setVisibility(View.VISIBLE);
            tv_title.setText(title);
        }
    }

    public void setTitleView(View view) {
        rlt_title.removeAllViews();
        rlt_title.addView(view);
        initView();
    }

    public void setTitleView(int ResId) {
        rlt_title.removeAllViews();
        rlt_title.addView(getLayoutInflater().inflate(ResId, null));
        initView();
    }

    public void setContentView(View view) {
        flt_root.removeAllViews();
        flt_root.addView(view);
    }

    public void setContentView(int ResId) {
        flt_root.removeAllViews();
        flt_root.addView(getLayoutInflater().inflate(ResId, null));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isLoadData) {
            isLoadData = true;
            if (checkLoad) {
                if (TCApplication.getmUserInfo() != null) {
                    loadData();
                }
            } else {
                loadData();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);//取消请求
        if (isRegisterEventBus) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isFastDoubleClick()) {// 防止快速点击
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD >= 0 && timeD <= 300) {
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }

    public abstract void loadData();

    /**
     * 设置是否启用滑动关闭Activity
     *
     * @param CanSlidingClose
     */
    public void setCanSlidingClose(boolean CanSlidingClose) {
        if (rootView != null) {
            rootView.setCanSlidingClose(CanSlidingClose);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);//友盟
        if (checkLoad && TCApplication.getmUserInfo() == null) {
            startActivity(new Intent(TCApplication.mContext, LocationActivity.class));
            finish();
        }
    }

    public void setCheckLoad(boolean checkLoad) {
        this.checkLoad = checkLoad;
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);//友盟
    }

    public abstract void mHandDoSomeThing(Message msg);
}