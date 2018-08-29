package com.example.mysmall.mycustomtvrecyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mysmall.mycustomtvrecyclerview.R;
import com.example.mysmall.mycustomtvrecyclerview.util.SpUtil;
import com.example.mysmall.mycustomtvrecyclerview.widget.CustomTvRecyclerView;

import java.util.List;

public class PopRecyclerAdapter extends CustomTvRecyclerView.CustomAdapter<String> {

    public PopRecyclerAdapter(Context context, List<String> mData) {
        super(context, mData);
    }

    @NonNull
    @Override
    protected int onSetItemlayout() {
        return R.layout.list_menu_popwindow_item;
    }

    @Override
    protected RecyclerView.ViewHolder onSetViewHolder(View view) {
        return new PopViewHolder(view);
    }

    @Override
    protected void onSetItemData(RecyclerView.ViewHolder viewHolder, int position) {
        PopViewHolder holder = (PopViewHolder) viewHolder;
        holder.tvItem.setText(mData.get(position));
        if (position == SpUtil.getInt(Constants.CATEGORY_POP_SELECT_POSITION, -1)) {
            holder.tvItem.setTextColor(mContext.getResources().getColor(R.color.orange));
        } else {
            holder.tvItem.setTextColor(mContext.getResources().getColor(R.color.common_white));;
        }
    }

    @Override
    protected void onItemFocus(View itemView, int position) {
        RelativeLayout layout = (RelativeLayout) itemView.findViewById(R.id.item_contain);
        layout.setBackgroundResource(R.drawable.pop_item_focus_drawable);;
        TextView itemText = (TextView) itemView.findViewById(R.id.tv_item);
        itemText.setTextColor(mContext.getResources().getColor(R.color.common_white));;
    }

    @Override
    protected void onItemGetNormal(View itemView, int position) {
        RelativeLayout layout = (RelativeLayout) itemView.findViewById(R.id.item_contain);
        layout.setBackgroundResource(R.drawable.pop_item_unfocus_drawable);;

        if (position == SpUtil.getInt(Constants.CATEGORY_POP_SELECT_POSITION, -1)) {
            TextView itemText = (TextView) itemView.findViewById(R.id.tv_item);
            itemText.setTextColor(mContext.getResources().getColor(R.color.orange));
        }
    }

    @Override
    protected int getCount() {
        return mData.size();
    }

    private class PopViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public PopViewHolder(View itemView) {
            super(itemView);
            tvItem = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }
}
