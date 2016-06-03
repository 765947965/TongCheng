package app.net.tongcheng.view;


import android.content.Context;

import android.util.AttributeSet;
import android.widget.GridView;

public class ScrollViewGridView extends GridView {

	public ScrollViewGridView(Context context) {
		super(context);
	}

	public ScrollViewGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	
	/**
	 * onMeasure函数决定了组件显示的高度与宽度；
	 * makeMeasureSpec函数中第一个函数决定布局空间的大小，第二个参数是布局模式
	 * MeasureSpec.AT_MOST的意思就是子控件需要多大的控件就扩展到多大的空间
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
