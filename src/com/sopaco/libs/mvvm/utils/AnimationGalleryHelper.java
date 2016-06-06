package com.sopaco.libs.mvvm.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by meng.jiang on 2015/2/12.
 */
public class AnimationGalleryHelper {
    public static void alphaTransitionOut(final Context ctx, View view) {
        if(true) {
            return;
        }
        AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
        aa.setDuration(250);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {

            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
            @Override
            public void onAnimationStart(Animation animation) {
            }
        });
    }
}