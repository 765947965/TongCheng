/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package cn.sharesdk.onekeyshare.themes.classic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * 编辑页面中删除图片“X”按钮
 */
public class XView extends View {
    private float ratio;
    private Paint paint1, paint2;

    public XView(Context context) {
        super(context);
        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(0xffa0a0a0);
        paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setStrokeWidth(3f * ratio);
        paint2.setColor(0xffffffff);
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    protected void onDraw(Canvas canvas) {
        int width = getWidth() / 2;
        int height = getHeight() / 2;

        canvas.drawRect(width, 0, getWidth(), height, paint1);

        float left = 8f * ratio;
        canvas.drawLine(width + left, left, getWidth() - left, width - left, paint2);
        canvas.drawLine(width + left, width - left, getWidth() - left, left, paint2);
    }

}
