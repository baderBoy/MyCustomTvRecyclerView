package com.example.mysmall.mycustomtvrecyclerview.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.mysmall.mycustomtvrecyclerview.util.LogUtil;

import java.util.List;

public class CustomTvRecyclerView extends RecyclerView {

    public CustomTvRecyclerView(Context context) {
        super(context);
    }

    public CustomTvRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTvRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //在recyclerView的move事件情况下，拦截调，只让它响应五向键和左右箭头移动
        return ev.getAction() == MotionEvent.ACTION_MOVE || super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int dx = this.getChildAt(0).getWidth();
        View focusView = this.getFocusedChild();
        //处理左右方向键移动Item到边之后RecyclerView跟着移动
        if (focusView != null) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    LogUtil.i(this, "CustomRecyclerView.KEYCODE_DPAD_RIGHT.");
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        return true;
                    } else {
                        View rightView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_RIGHT);
                        LogUtil.i(this, "rightView is null:" + (rightView == null));
                        if (rightView != null) {
                            LogUtil.i(this, "CustomRecyclerView.requestFocusFromTouch.");
                            rightView.requestFocusFromTouch();
                            return true;
                        } else {
                            this.smoothScrollBy(dx, 0);
                            //移动之后获得焦点，是在scroll方法中处理的。
                            return true;
                        }
                    }
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        return true;
                    } else {
                        View leftView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_LEFT);
                        if (leftView != null) {
                            leftView.requestFocusFromTouch();
                            return true;
                        } else {
                            this.smoothScrollBy(-dx, 0);
                            return true;
                        }
                    }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        //响应五向键，在Scroll时去获得下一个焦点
        final View focusView = this.getFocusedChild();
        if (focusView != null) {
            if (dx > 0) {
                View rightView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_RIGHT);
                if (rightView != null) {
                    rightView.requestFocusFromTouch();
                }
            } else {
                View leftView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_LEFT);
                if (leftView != null) {
                    leftView.requestFocusFromTouch();
                }
            }
        }
    }

    public boolean isFirstItemVisible() {
        return getFirstPosition() == 0;
    }


    public int getFirstPosition() {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] firstVisibleItems = null;
            firstVisibleItems = ((StaggeredGridLayoutManager) layoutManager)
                    .findFirstCompletelyVisibleItemPositions(firstVisibleItems);
            return firstVisibleItems[0];
        } else if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
        }
        return -1;
    }


    public boolean isLastItemVisible(int lineNum, int allItemNum) {
        int position = getLastPosition();
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            boolean isVisible = position >= (allItemNum - lineNum);
            return isVisible;
        } else if (layoutManager instanceof LinearLayoutManager) {
            return allItemNum - 1 == position;
        }
        return false;
    }

    public int getLastPosition() {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItems = null;
            lastVisibleItems = ((StaggeredGridLayoutManager) layoutManager)
                    .findLastCompletelyVisibleItemPositions(lastVisibleItems);
            return lastVisibleItems[0];
        } else if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        }

        return -1;
    }

    public static abstract class CustomAdapter<T> extends Adapter<ViewHolder> {
        private LayoutInflater mInflater;
        protected List<T> mData;
        protected Context mContext;
        public interface OnItemClickListener {
            void onItemClick(View view, int position);

            void onItemLongClick(View view, int position);
        }

        private OnItemClickListener mListener;

        public void setOnItemClickListener(OnItemClickListener listener) {
            mListener = listener;
        }

        public CustomAdapter(Context context, List<T> mData) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(mContext);
            this.mData = mData;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(onSetItemlayout(), parent, false);
            return onSetViewHolder(view);
        }

        @NonNull
        protected abstract int onSetItemlayout();

        protected abstract ViewHolder onSetViewHolder(View view);

        protected abstract void onSetItemData(ViewHolder viewHolder, int position);

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

            onSetItemData(holder, position);
            holder.itemView.setFocusable(true);
            holder.itemView.setOnHoverListener(new View.OnHoverListener() {
                @Override
                public boolean onHover(View v, MotionEvent event) {
                    int what = event.getAction();
                    switch (what) {
                        case MotionEvent.ACTION_HOVER_ENTER:
                            RecyclerView recyclerView = (RecyclerView) holder.itemView.getParent();
                            int[] location = new int[2];
                            recyclerView.getLocationOnScreen(location);
                            int x = location[0];
                            if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                                if (event.getRawX() > recyclerView.getWidth() + x || event.getRawX() < x) {
                                    return true;
                                }

                                v.requestFocusFromTouch();
                                v.requestFocus();
                                focusStatus(v, position);
                            }
                            break;
                        case MotionEvent.ACTION_HOVER_MOVE:
                            break;
                        case MotionEvent.ACTION_HOVER_EXIT:
                            normalStatus(v, position);
                            break;
                        default:
                            break;
                    }
                    return false;
                }
            });

            holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        focusStatus(v, position);
                    } else {
                        normalStatus(v, position);
                    }
                }
            });

            if (mListener != null) {
                holder.itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(v, position);
                    }
                });

                holder.itemView.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mListener.onItemLongClick(v, position);
                        return true;
                    }
                });
            }
        }

        protected abstract void onItemFocus(View itemView, int position);

        private void focusStatus(View itemView, int position) {
            if (itemView == null) {
                return;
            }

            onItemFocus(itemView, position);
        }

        protected abstract void onItemGetNormal(View itemView, int position);

        private void normalStatus(View itemView, int position) {
            if (itemView == null) {
                return;
            }

            onItemGetNormal(itemView, position);
        }

        @Override
        public int getItemCount() {
            if (mData != null) {
                return getCount();
            } else {
                return 0;
            }
        }

        protected abstract int getCount();
    }
}
