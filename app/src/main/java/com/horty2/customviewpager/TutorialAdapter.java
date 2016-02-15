package com.horty2.customviewpager;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class TutorialAdapter extends PagerAdapter {

	private Activity mActivity;
	private int[] mViewsId = new int[]{R.layout.view_intro1, R.layout.view_intro2, R.layout.view_intro3};
	private ArrayList<View> mViews = new ArrayList<View>();

	public TutorialAdapter(final Activity activity) {
		mActivity = activity;

		for (int i = 0; i < mViewsId.length; i++) {
			mViews.add(mActivity.getLayoutInflater().inflate(mViewsId[i], null));
		}

	}

	public ArrayList<View> getViews() {
		return mViews;
	}

	@Override
	public int getCount() {
		return mViews.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		final View view = mViews.get(position);
//        final FrameLayout.LayoutParams imageParams = new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//        view.setLayoutParams(imageParams);

		if (position == 2) {
			final Button mStartButton = (Button) view.findViewById(R.id.startButton);
			mStartButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					((TutorialActivity) mActivity).goToMainActivity();
				}
			});
		}

		((ViewPager) container).addView(view);
		return view;
	}

	public int getBackgroundColorResource(int position) {
		switch (position) {
			case 0:
				return R.color.intro1;
			case 1:
				return R.color.intro2;
			case 2:
				return R.color.intro3;
			default:
				return R.color.intro1;
		}

	}


	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

}
