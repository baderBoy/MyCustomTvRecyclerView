package com.example.mysmall.mycustomtvrecyclerview.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.mysmall.mycustomtvrecyclerview.R;

public class CustomDialog extends BaseDialog {

    private OnCallResult mOnCallResult;
    private FocusVerticalLinearLayout mOkBtn;
    private FocusVerticalLinearLayout mCancelBtn;
    private Drawable mBackDrawable;

    public CustomDialog(@NonNull Context context) {
        super(context);
        initListener();
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initListener();
    }

    @Override
    public void initView() {
        setContentView(R.layout.dialog_layout);
        mOkBtn = (FocusVerticalLinearLayout) findViewById(R.id.layout_video_call);
        mCancelBtn = (FocusVerticalLinearLayout) findViewById(R.id.layout_video_call2);
        mOkBtn.requestFocus();
    }

    @Override
    public void onDialogClick(View v) {
        if (mOnCallResult != null) {
            switch (v.getId()) {
                case R.id.layout_video_call:
                    mOnCallResult.onOkBtnClick();
                    break;
                case R.id.layout_video_call2:
                    mOnCallResult.onCancelBtnClick();
                    break;
                default:
                    break;
            }
        }
    }

    public void setBackground(Drawable backDrawable) {
        mBackDrawable = backDrawable;
    }

    @Override
    public void show() {
        super.show();
        View view = findViewById(R.id.layout_dialog);
        view.setBackground(mBackDrawable);
    }

    private void initListener() {
        mOkBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
    }

    public void setCallBackListen(OnCallResult onCallResult) {
        this.mOnCallResult = onCallResult;
    }

    public interface OnCallResult {
        void onOkBtnClick();

        void onCancelBtnClick();
    }
}
