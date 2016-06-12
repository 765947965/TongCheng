package app.net.tongcheng.util;

import android.content.Context;
import android.text.Spanned;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.HashMap;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/3/22 14:04
 */
public class ViewHolder {

    private View mRootView;
    private SparseArray<View> viewmaps = new SparseArray<>();
    private View.OnClickListener mOnClickListener;

    public ViewHolder(Context context, int resource) {
        this.mRootView = LayoutInflater.from(context).inflate(resource, null);
    }

    public ViewHolder(View mRootView) {
        this.mRootView = mRootView;
    }

    public ViewHolder(Context context, int resource, View.OnClickListener mOnClickListener) {
        this.mRootView = LayoutInflater.from(context).inflate(resource, null);
        this.mOnClickListener = mOnClickListener;
    }

    public ViewHolder(View mRootView, View.OnClickListener mOnClickListener) {
        this.mRootView = mRootView;
        this.mOnClickListener = mOnClickListener;
    }

    public View getmRootView() {
        return mRootView;
    }

    public <T extends View> T getView(int resource) {
        View view = viewmaps.get(resource);
        if (view == null) {
            view = mRootView.findViewById(resource);
            viewmaps.put(resource, view);
        }
        return (T) view;
    }

    public TextView setText(int resource, CharSequence mCharSequence) {
        TextView mTextView = getView(resource);
        mTextView.setText(mCharSequence);
        return mTextView;
    }

    public ImageView setImage(int resource, int resourceImage) {
        ImageView imageView = getView(resource);
        imageView.setImageResource(resourceImage);
        return imageView;
    }

    public ImageView setImage(int resource, String imageUri) {
        ImageView imageView = getView(resource);
        new PictureLoader(0).displayImage(imageUri, imageView);
        return imageView;
    }

    public ImageView setImage(int resource, String imageUri, ImageScaleType imageScaleType) {
        ImageView imageView = getView(resource);
        new PictureLoader(0, imageScaleType).displayImage(imageUri, imageView);
        return imageView;
    }

    public ImageView setImage(int resource, String imageUri, int defaultReId) {
        ImageView imageView = getView(resource);
        new PictureLoader(defaultReId).displayImage(imageUri, imageView);
        return imageView;
    }

    public ImageView setImage(int resource, String imageUri, int defaultReId, ImageScaleType imageScaleType) {
        ImageView imageView = getView(resource);
        new PictureLoader(defaultReId, imageScaleType).displayImage(imageUri, imageView);
        return imageView;
    }

    // 加载圆角图片
    public ImageView setImage(int resource, String imageUri, int defaultReId, int cornerRadiusPixels) {
        ImageView imageView = getView(resource);
        new PictureLoader(defaultReId, cornerRadiusPixels).displayImage(imageUri, imageView);
        return imageView;
    }

    public <T extends View> T setOnClickListener(int resource) {
        View view = getView(resource);
        if (mOnClickListener != null) {
            view.setOnClickListener(mOnClickListener);
        }
        return (T) view;
    }

    public <T extends View> T setVisibility(int resource, int visibility) {
        View view = getView(resource);
        view.setVisibility(visibility);
        return (T) view;
    }
}
