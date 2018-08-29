package com.example.mysmall.mycustomtvrecyclerview.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;

public class UiUtil {
    private static final String TAG = "UiUtil";
    private static View view;

    public static Drawable getBackBlurDrawable(Context context, float radius) {
        return new BitmapDrawable(context.getResources(), getBackBlurBitmap(context, radius));
    }

    @SuppressLint("NewApi")
    private static Bitmap captureScreen(Activity context) {
        if (view != null) {
            view.destroyDrawingCache();
        }

        view = context.getWindow().getDecorView();

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        if (bmp == null) {
            return null;
        }

        bmp.setHasAlpha(false);
        bmp.prepareToDraw();

        return bmp;
    }


    public static Bitmap getBackBlurBitmap(Context context, float radius) {
        Bitmap bitmap = captureScreen((Activity) context);
        return blur(context, bitmap, radius);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap blur(Context context, Bitmap bitmap, float radius) {
        Bitmap output = Bitmap.createBitmap(bitmap);
        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur gaussianBlue = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, output);
        gaussianBlue.setRadius(radius);
        gaussianBlue.setInput(allIn);
        gaussianBlue.forEach(allOut);
        allOut.copyTo(output);
        rs.destroy();
        return output;
    }

}
