package com.horty2.customviewpager;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

import com.horty2.customviewpager.PageTransformer.CrossfadePageTransformer;


public class TutorialActivity extends Activity implements ViewPager.OnPageChangeListener {
	private static final String TAG = TutorialActivity.class.getSimpleName();

	private ViewPager mPager;
	private TutorialAdapter mPagerAdapterView;
	private View mMainBackground;
	private int previousPosition = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		// Instantiate a ViewPager and a PagerAdapter.
		mPager = (ViewPager) findViewById(R.id.introPageSlider);
		mMainBackground = (View) findViewById(R.id.mainBackground);


		mPagerAdapterView = new TutorialAdapter(this);
//		mPagerAdapterView = new IntroPagerAdapter(this);
		mPager.setAdapter(mPagerAdapterView);

		mPager.setPageTransformer(true, new CrossfadePageTransformer());

		mPager.addOnPageChangeListener(this);

	}

	@Override
	public void onBackPressed() {
		if (mPager.getCurrentItem() == 0) {
//			goToMainActivity();
		} else {
			// Otherwise, select the previous step.
			mPager.setCurrentItem(mPager.getCurrentItem() - 1);
		}
	}

	public void goToMainActivity() {
		setResult(RESULT_OK);
		finish();
	}

	final static int TIME_DRAWABLE_TRANSITION = 500;

	public void startAnimationColor(View viewTarget, int colorInit, int colorFinal) {
		ColorDrawable transparentDrawable = new ColorDrawable();
		transparentDrawable.setColor(colorInit);
		ColorDrawable finalColorDrawable = new ColorDrawable();
		finalColorDrawable.setColor(colorFinal);
		Drawable[] layers = new Drawable[]{transparentDrawable, finalColorDrawable};
		TransitionDrawable transitionDrawable = new TransitionDrawable(layers);
		viewTarget.setBackground(transitionDrawable);
		transitionDrawable.startTransition(TIME_DRAWABLE_TRANSITION); //duration in milliseconds
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		int initColor = getResources().getColor(mPagerAdapterView.getBackgroundColorResource(previousPosition));
		int finalColor = getResources().getColor(mPagerAdapterView.getBackgroundColorResource(position));
		startAnimationColor(mMainBackground, initColor, finalColor);
		if (position == 2) {
			View buttonStart = mPagerAdapterView.getViews().get(2).findViewById(R.id.startButton);
//			View image = mPagerAdapterView.getViews().get(2).findViewById(R.id.backgroundImage);
			startAnimationVisibility(buttonStart, true, MOVE_VISIBILITY_ANIMATIONS_DURATION, 300);
		} else {
			View buttonStart = mPagerAdapterView.getViews().get(2).findViewById(R.id.startButton);
//			View image = mPagerAdapterView.getViews().get(2).findViewById(R.id.backgroundImage);
			startAnimationVisibility(buttonStart, false, MOVE_VISIBILITY_ANIMATIONS_DURATION, 0);
		}


//		if (Build.VERSION.SDK_INT >= 21) {
//			getWindow().setNavigationBarColor(finalColor);
//		}


		previousPosition = position;
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	private static final int MOVE_VISIBILITY_ANIMATIONS_DURATION = 200;

	/**
	 * animation to show or not the view
	 *
	 * @param visibility: true if view must be show or not
	 */
	public void startAnimationVisibility(View viewTarget, final boolean visibility, int duration, int delay) {
		final View view = viewTarget;
		final float scale = visibility ? 1f : 0f;
		final Interpolator interpolator = visibility ? new OvershootInterpolator() : new OvershootInterpolator();

		final ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", scale);
		final ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", scale);

		final AnimatorSet asScale = new AnimatorSet();
		asScale.setDuration(duration);
		asScale.setInterpolator(interpolator);
		asScale.playTogether(scaleX, scaleY);

		if (visibility) {
			view.setVisibility(View.VISIBLE);
		}

		asScale.setStartDelay(delay);
		asScale.start();
	}

}