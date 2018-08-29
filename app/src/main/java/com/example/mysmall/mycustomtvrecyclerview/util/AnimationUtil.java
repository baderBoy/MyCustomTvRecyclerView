package com.example.mysmall.mycustomtvrecyclerview.util;

import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;

public class AnimationUtil {
    private static AnimationUtil animationUtil;
    private static final float SCALE = 1.1f;
    private static final int DURATION = 100;
    private int mMinSdkVersion = 21;

    public static AnimationUtil getInstance() {
        if (animationUtil == null) {
            animationUtil = new AnimationUtil();
        }

        return animationUtil;
    }

    public void zoomIn(View itemView) {
        if (itemView == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= mMinSdkVersion) {
            ViewCompat.animate(itemView)
                    .scaleX(SCALE).scaleY(SCALE)
                    .translationZ(1).setDuration(DURATION).start();
        } else {
            ViewCompat.animate(itemView)
                    .scaleX(SCALE).scaleY(SCALE)
                    .translationZ(1).setDuration(DURATION).start();
            ViewGroup parent = (ViewGroup) itemView.getParent();
            parent.requestLayout();
            parent.invalidate();
        }
    }

    public void zoomOut(View itemView) {
        if (itemView == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= mMinSdkVersion) {
            ViewCompat.animate(itemView)
                    .scaleX(1.0f).scaleY(1.0f)
                    .translationZ(0).setDuration(DURATION).start();
        } else {
            ViewCompat.animate(itemView).scaleX(1.0f).scaleY(1.0f)
                    .translationZ(0).setDuration(DURATION).start();
            ViewGroup parent = (ViewGroup)itemView.getParent();
            parent.requestLayout();
            parent.invalidate();
        }
    }

    public void zoomIn(View itemView, float scale) {
        if (itemView == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= mMinSdkVersion) {
            //抬高Z轴
            ViewCompat.animate(itemView).scaleX(scale).scaleY(scale).translationZ(1).setDuration(DURATION).start();
        } else {
            ViewCompat.animate(itemView).scaleX(scale).scaleY(scale).setDuration(DURATION).start();
            ViewGroup parent = (ViewGroup) itemView.getParent();
            parent.requestLayout();
            parent.invalidate();
        }
    }


    /**
     * @param view
     * @param dy
     */
    public void translationY(View view, int dy) {
        ViewCompat.animate(view).translationY(dy).setDuration(DURATION).start();
    }

    public void translation(View view, int dy, int dz) {
        ViewCompat.animate(view).translationY(dy).translationZ(dz).setDuration(DURATION).start();
    }




}
