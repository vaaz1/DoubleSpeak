package com.doublespeak.android.doublespeak.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.AnimRes;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class Global {

    public static void launchActivity(Activity activityFrom, Class<? extends Activity> activityTo) {
        Intent activityIntent = new Intent(activityFrom, activityTo);
        activityFrom.startActivity(activityIntent);
    }


    public static void startViewAnimation(Context context, View view, @AnimRes int animId) {
        if (view != null) {
            final Animation anim = AnimationUtils.loadAnimation(context, animId);
            view.startAnimation(anim);
        }
    }
    public static void fadeView(int from, int to, View view, long time) {
        Animation alpha = new AlphaAnimation(from, to);
        alpha.setInterpolator(new AccelerateInterpolator());
        alpha.setDuration(time);
        view.startAnimation(alpha);
    }

    /* Alpha Animations */

    public static void showViewWithFadeIn(Context context, View view) {
        if (view != null) {
            view.setVisibility(View.INVISIBLE);
            startViewAnimation(context, view, android.R.anim.fade_in);
            view.setVisibility(View.VISIBLE);
        }
    }

    public static float convertPixelsToDp(Context context, float px) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static void hideViewWithFadeOut(Context context, View view) {
        if (view != null) {
            view.setVisibility(View.VISIBLE);
            startViewAnimation(context, view, android.R.anim.fade_out);
            view.setVisibility(View.GONE);
        }
    }

    public static void setViewAlpha(@Nullable View v, float alpha) {
        if (v != null) {
            v.setAlpha(alpha);
            if (alpha != v.getAlpha()) {
                AlphaAnimation alphaAnimation = new AlphaAnimation(v.getAlpha(), alpha);
                alphaAnimation.setDuration(0); // Make animation instant
                alphaAnimation.setFillAfter(true); // Tell it to persist after the animation ends
                v.startAnimation(alphaAnimation);
            }
        }
    }


}
