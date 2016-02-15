package com.horty2.customviewpager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Created by guido on 19/03/15.
 */
public class TutorialViewPager extends ViewPager {

	public static final int VELOCITY_NORMAL = 1;
	public static final int VELOCITY_MEDIUM = 3;
	public static final int VELOCITY_SLOW = 5;

	private boolean isPagingEnabled = true;
	private ScrollerCustomDuration mScroller = null;

	public TutorialViewPager(Context context) {
		super(context);
		postInitViewPager();
	}

	public TutorialViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		postInitViewPager();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.isPagingEnabled && super.onTouchEvent(event);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		return this.isPagingEnabled && super.onInterceptTouchEvent(event);
	}

	public void setPagingEnabled(boolean enabled) {
		this.isPagingEnabled = enabled;
	}


	private void postInitViewPager() {
		try {
			Class<?> viewpager = ViewPager.class;
			Field scroller = viewpager.getDeclaredField("mScroller");
			scroller.setAccessible(true);
			Field interpolator = viewpager.getDeclaredField("sInterpolator");
			interpolator.setAccessible(true);

			mScroller = new ScrollerCustomDuration(getContext(),
					(Interpolator) interpolator.get(null));
			scroller.set(this, mScroller);
		} catch (Exception e) {
		}

	}

	/**
	 * Set the factor by which the duration will change
	 */
	public void setScrollDurationFactor(double scrollFactor) {
		mScroller.setScrollDurationFactor(scrollFactor);
	}


	public class ScrollerCustomDuration extends Scroller {

		private double mScrollFactor = 1;

		public ScrollerCustomDuration(Context context) {
			super(context);
		}

		public ScrollerCustomDuration(Context context, Interpolator interpolator) {
			super(context, interpolator);
		}

		@SuppressLint("NewApi")
		public ScrollerCustomDuration(Context context, Interpolator interpolator, boolean flywheel) {
			super(context, interpolator, flywheel);
		}

		/**
		 * Set the factor by which the duration will change
		 */
		public void setScrollDurationFactor(double scrollFactor) {
			mScrollFactor = scrollFactor;
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy, int duration) {
			super.startScroll(startX, startY, dx, dy, (int) (duration * mScrollFactor));
		}

	}

}
