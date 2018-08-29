package com.example.mysmall.mycustomtvrecyclerview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysmall.mycustomtvrecyclerview.adapter.Constants;
import com.example.mysmall.mycustomtvrecyclerview.adapter.HomeTvAdapter;
import com.example.mysmall.mycustomtvrecyclerview.adapter.PopRecyclerAdapter;
import com.example.mysmall.mycustomtvrecyclerview.util.LogUtil;
import com.example.mysmall.mycustomtvrecyclerview.util.SpUtil;
import com.example.mysmall.mycustomtvrecyclerview.util.UiUtil;
import com.example.mysmall.mycustomtvrecyclerview.widget.CustomDialog;
import com.example.mysmall.mycustomtvrecyclerview.widget.CustomTvRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private CustomTvRecyclerView mRecyclerView;
    private HomeTvAdapter mAdapter;
    private TextView mCategoryBtn;
    private List<Integer> mData;
    private Button mLeftArr;
    private Button mRightArr;
    private int totalWidth;
    private StaggeredGridLayoutManager mLayoutManager;
    private PopupWindow mPopupWindow;
    private CustomTvRecyclerView mPopRecyclerView;
    private ImageButton mUpArr;
    private ImageButton mDownArr;
    private List<String> mChannelList;
    private PopRecyclerAdapter mPopRecyclerAdapter;

    public static final int LINE_NUM = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        mContext = this;
        mRecyclerView = (CustomTvRecyclerView) findViewById(R.id.id_recycler_view);
        mLeftArr = (Button) findViewById(R.id.arr_left);
        mRightArr = (Button) findViewById(R.id.arr_right);
        mCategoryBtn = (TextView) findViewById(R.id.category_btn);

        mRightArr.setOnClickListener(this);
        mLeftArr.setOnClickListener(this);
        initData();
        mAdapter = new HomeTvAdapter(this, mData);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        View popupView = getLayoutInflater().inflate(R.layout.list_menu_popwindow, null, false);
        mPopRecyclerView = (CustomTvRecyclerView) popupView.findViewById(R.id.recycler_view);
        mUpArr = (ImageButton) popupView.findViewById(R.id.up_arrow);
        mDownArr = (ImageButton) popupView.findViewById(R.id.down_arrow);
        mPopupWindow = new PopupWindow(popupView, 404, 1920);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.update();

        mLayoutManager = new StaggeredGridLayoutManager(LINE_NUM, StaggeredGridLayoutManager.HORIZONTAL);
        mLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyOnItemClickListener());
        mRecyclerView.setOnScrollListener(new MyOnScrollListener());

    }

    private void initData() {
        totalWidth = getResources().getDimensionPixelSize(R.dimen.total_width);
        mData = new ArrayList<>();
        for (int i=0; i<350; i++) {
            mData.add(i);
        }

        mChannelList = new ArrayList<>();

        mChannelList.add("少儿频道");
        mChannelList.add("音乐频道");
        mChannelList.add("电视剧频道");
        mChannelList.add("电影频道");
        mChannelList.add("新闻频道");
        mChannelList.add("综艺频道");
        mChannelList.add("体育频道");
        mChannelList.add("科教频道");
        mChannelList.add("国际频道");

        mCategoryBtn.setText(mChannelList.get(3));
        SpUtil.putInt(Constants.CATEGORY_POP_SELECT_POSITION, 3);
    }

    private void setListener() {
        mCategoryBtn.setOnClickListener(this);
        mUpArr.setOnClickListener(this);
        mDownArr.setOnClickListener(this);
        mPopRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));;
        mPopRecyclerAdapter = new PopRecyclerAdapter(mContext, mChannelList);
        mPopRecyclerView.setAdapter(mPopRecyclerAdapter);
        mPopRecyclerView.setLayoutAnimation(PopLayoutAnimation.orderAnimation());
        mPopRecyclerAdapter.setOnItemClickListener(new OnPopRecyclerItemClickListener());
        mPopRecyclerView.setOnScrollListener(new OnPopRecyclerViewScrollListener());
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                Drawable backBlurDrawable = UiUtil.getBackBlurDrawable(mContext, 25f);
                final CustomDialog customDialog = new CustomDialog(mContext, R.style.DialogStyle);
                customDialog.setBackground(backBlurDrawable);
                customDialog.setCallBackListen(new CustomDialog.OnCallResult() {
                    @Override
                    public void onOkBtnClick() {
                        LogUtil.i(this, "MainActivity.onOkBtnClick.");
                        customDialog.dismiss();
                    }

                    @Override
                    public void onCancelBtnClick() {
                        LogUtil.i(this, "MainActivity.onCancelBtnClick.");
                        customDialog.dismiss();
                    }
                });
                customDialog.show();
                return true;
            default:
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 设置上箭头的状态
     */
    private void setUpArrStatus() {
        if (mPopRecyclerView.isFirstItemVisible()) {
            mUpArr.setVisibility(View.INVISIBLE);
        } else {
            mUpArr.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置右侧箭头的状态
     */
    private void setDownArrStatus() {
        if (mPopRecyclerView.isLastItemVisible(1, mChannelList.size())) {
            LogUtil.i(this, "last can visit");
            mDownArr.setVisibility(View.INVISIBLE);
        } else {
            mDownArr.setVisibility(View.VISIBLE);
        }
    }



    private void setleftArrStatus() {
        if (mRecyclerView.isFirstItemVisible()) {
            mLeftArr.setVisibility(View.INVISIBLE);
        } else {
            mLeftArr.setVisibility(View.VISIBLE);
        }
    }

    private void setRightArrStatus() {
        if (mRecyclerView.isLastItemVisible(LINE_NUM, mData.size())) {
            mRightArr.setVisibility(View.INVISIBLE);
        } else {
            mRightArr.setVisibility(View.VISIBLE);
        }
    }

    private class OnPopRecyclerItemClickListener implements CustomTvRecyclerView.CustomAdapter.OnItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            SpUtil.putInt(Constants.CATEGORY_POP_SELECT_POSITION, position);
            mPopRecyclerAdapter.notifyDataSetChanged();
            mCategoryBtn.setText(mChannelList.get(position));
            mRecyclerView.smoothScrollToPosition(position * 12);
            mPopupWindow.dismiss();
        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    }

    private class OnPopRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            setUpArrStatus();
            setDownArrStatus();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arr_left:
                if (mRecyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                    mRecyclerView.smoothScrollBy(-totalWidth, 0);
                }
                break;
            case R.id.arr_right:
                LogUtil.i(this, "MainActivity.onClick.rightArr");
                if (mRecyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                    LogUtil.i(this, "MainActivity.onClick.smoothScrollBy-----" + totalWidth);
                    mRecyclerView.smoothScrollBy(totalWidth, 0);
                }
                break;
            case R.id.up_arrow:
                LogUtil.i("swj", "MainActivity.onClick.upArrow");
                if (mPopRecyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                    mPopRecyclerView.smoothScrollBy(0, -800);
                }
                break;
            case R.id.down_arrow:
                LogUtil.i("swj", "MainActivity.onClick.downArrow");
                if (mPopRecyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                    mPopRecyclerView.smoothScrollBy(0, 800);
                }
                break;
            case R.id.category_btn:
                LogUtil.i("swj", "CategoryActivity.onClick.category_btn");
                mPopRecyclerView.startLayoutAnimation();
                mPopupWindow.showAsDropDown(mCategoryBtn, 0, -150);
                break;
            default:
                break;
        }

    }

    private class MyOnItemClickListener implements HomeTvAdapter.OnItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(mContext, "click:" + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    }

    private class MyOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            setleftArrStatus();
            setRightArrStatus();
        }
    }

}
