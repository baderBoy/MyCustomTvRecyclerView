package com.example.mysmall.mycustomtvrecyclerview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mysmall.mycustomtvrecyclerview.util.AnimationUtil;

public class FocusVerticalLinearLayout extends LinearLayout implements View.OnHoverListener{
    public FocusVerticalLinearLayout(Context context) {
        super(context);
    }

    public FocusVerticalLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusVerticalLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        this.setClickable(true);
        this.setFocusable(true);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        setHovered(gainFocus);
    }

    @Override
    public void onHoverChanged(boolean hovered) {
        super.onHoverChanged(hovered);
        changeSelectedState(hovered);
    }

    @Override
    public boolean onHover(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_HOVER_ENTER:
                setHovered(true);
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                break;
            case MotionEvent.ACTION_HOVER_EXIT:
                setHovered(false);
                break;
            default:
                break;
        }
        return false;
    }

    private void changeSelectedState(boolean selected) {
        if (selected) {
            AnimationUtil.getInstance().zoomIn(this);
        } else {
            AnimationUtil.getInstance().zoomOut(this);
        }
    }
}
