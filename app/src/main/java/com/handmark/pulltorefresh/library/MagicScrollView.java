package com.handmark.pulltorefresh.library;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class MagicScrollView extends ScrollView {

	private List<ScrollListener> mListeners = new ArrayList<MagicScrollView.ScrollListener>();
	private int mMagicScrollViewState = 50;
	public static final int UP = 10001;
	public static final int DOWN = 20001;
	public static final int STOP = 30001;
	private static final long DELAY = 10;

	private int currentScroll;

	private Runnable scrollCheckTask;

	public interface ScrollListener {
		void onScrollChanged(int state, int t);
	}

	public MagicScrollView(Context context) {
		super(context);
		init();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public MagicScrollView(Context context, AttributeSet set) {
		super(context, set);
		init();
	}

	public MagicScrollView(Context context, AttributeSet set, int defStyle) {
		super(context, set, defStyle);
		init();
	}

	public void AddListener(ScrollListener listener) {
		if (mListeners == null)
			mListeners = new ArrayList<MagicScrollView.ScrollListener>();
		mListeners.add(listener);
	}

	public void removeAllListener() {
		if (mListeners != null)
			mListeners.clear();
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		if (y > oldy) {
			mMagicScrollViewState = UP;
		} else if (y < oldy) {
			mMagicScrollViewState = DOWN;
		} else {
			mMagicScrollViewState = STOP;
		}
		if (onScrollListener != null) {
			onScrollListener.onScrollChanged(x, y, oldx, oldy);
		}
		// sendScroll(mMagicScrollViewState, y);
	}

	public void sendScroll(int state, int scroll) {
		for (ScrollListener listener : mListeners) {
			listener.onScrollChanged(state, scroll);
		}
	}

	private void init() {
		scrollCheckTask = new Runnable() {
			@Override
			public void run() {
				int newScroll = getScrollY();
				if (currentScroll == newScroll) {
					if (onScrollListener != null) {
						onScrollListener.onScrollStopped();
					}
				} else {
					if (onScrollListener != null) {
						onScrollListener.onScrolling();
					}
					currentScroll = getScrollY();
					postDelayed(scrollCheckTask, DELAY);
				}
			}
		};
		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					currentScroll = getScrollY();
					postDelayed(scrollCheckTask, DELAY);
				}
				return false;
			}
		});
	}

	public interface OnScrollListener {
		public void onScrollChanged(int x, int y, int oldX, int oldY);

		public void onScrollStopped();

		public void onScrolling();
	}

	private OnScrollListener onScrollListener;

	/**
	 * @param onScrollListener
	 */
	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}

	/**
	 * @param child
	 * @return
	 */
	public boolean isChildVisible(View child) {
		if (child == null) {
			return false;
		}
		Rect scrollBounds = new Rect();
		getHitRect(scrollBounds);
		return child.getLocalVisibleRect(scrollBounds);
	}

	/**
	 * @return
	 */
	public boolean isAtTop() {
		return getScrollY() <= 0;
	}

	/**
	 * @return
	 */
	public boolean isAtBottom() {
		int a = getChildAt(getChildCount() - 1).getBottom() + getPaddingBottom();
		int b = getHeight() + getScrollY();
		Log.v("meizu", "a:" + a);
		Log.v("meizu", "b:" + b);
		boolean c = a == b;
		return c;
	}

}
