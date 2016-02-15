package com.horty2.customviewpager.PageTransformer;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.horty2.customviewpager.R;


/**
 * Created by guido on 12/02/16.
 */
public class CrossfadePageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();

        View title = page.findViewById(R.id.textTitle);
        View text = page.findViewById(R.id.text);
        View image = page.findViewById(R.id.backgroundImage);
        View buttonStart = page.findViewById(R.id.startButton);

        if (-1 < position && position < 0) {
            page.setTranslationX(pageWidth * -position / 2);
        }

        if (0 <= position && position < 1) {
            page.setTranslationX(pageWidth * -position);
        }


        if (position <= -1.0f || position >= 1.0f) {
        } else if (position == 0.0f) {
        } else {


            if (title != null) {
                title.setTranslationX(pageWidth * position * 1.5f);
                title.setAlpha(1.0f - Math.abs(position) * 1.5f);
            }

            if (text != null) {
                text.setTranslationX(pageWidth * position * 2);
                text.setAlpha(1.0f - Math.abs(position) * 2);
            }

            if (image != null) {
                image.setTranslationX(pageWidth * position);
                image.setAlpha(1.0f - Math.abs(position));
            }

            if (buttonStart != null) {
                buttonStart.setTranslationX(pageWidth * position * 3);
                buttonStart.setAlpha(1.0f - Math.abs(position));
            }


        }
    }
}
