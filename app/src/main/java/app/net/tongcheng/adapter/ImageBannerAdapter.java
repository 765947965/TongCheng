package app.net.tongcheng.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.viewpagerindicator.IconPagerAdapter;

import app.net.tongcheng.R;
import app.net.tongcheng.util.PictureLoader;

public class ImageBannerAdapter extends PagerAdapter implements IconPagerAdapter {
	private List<String> mBList;
	private LayoutInflater mInflate;
	private Context mContext;

	public ImageBannerAdapter(List<String> list, Context context) {
		// TODO Auto-generated constructor stub
		this.mBList = list;
		this.mInflate = LayoutInflater.from(context);
		mContext = context;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.mBList.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		final int mPos = position;
		View imageLayout = this.mInflate.inflate(R.layout.row_banner, container, false);
		ImageView imageView = (ImageView) imageLayout.findViewById(R.id.banner_top_IV);
		new PictureLoader(0).displayImage(this.mBList.get(position), imageView);
		imageLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
			}
		});
		container.addView(imageLayout, 0);

		return imageLayout;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public int getIconResId(int index) {
		// TODO Auto-generated method stub
		return R.drawable.banner_selector;
	}

}