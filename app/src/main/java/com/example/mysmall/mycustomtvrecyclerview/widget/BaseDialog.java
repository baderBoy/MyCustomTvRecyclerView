package com.example.mysmall.mycustomtvrecyclerview.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

public abstract class BaseDialog extends Dialog implements View.OnClickListener {

    public Context mContext;

    public BaseDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        initView();
    }

    public abstract void initView();

    public abstract void onDialogClick(View v);

    <T> T findView(int id) {
        T view = (T)findViewById(id);
        return view;
    }

    @Override
    public void onClick(View v) {
        onDialogClick(v);
    }
}
