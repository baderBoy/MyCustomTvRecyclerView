package com.example.mysmall.mycustomtvrecyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mysmall.mycustomtvrecyclerview.R;
import com.example.mysmall.mycustomtvrecyclerview.util.AnimationUtil;
import com.example.mysmall.mycustomtvrecyclerview.widget.CustomTvRecyclerView;

import java.util.List;

public class HomeTvAdapter extends CustomTvRecyclerView.CustomAdapter<Integer> {
    public HomeTvAdapter(Context context, List<Integer> mData) {
        super(context, mData);
    }

    @NonNull
    @Override
    protected int onSetItemlayout() {
        return R.layout.item;
    }

    @Override
    protected RecyclerView.ViewHolder onSetViewHolder(View view) {
        return new GalleryViewHolder(view);
    }

    @Override
    protected void onSetItemData(RecyclerView.ViewHolder viewHolder, int position) {
        GalleryViewHolder holder = (GalleryViewHolder) viewHolder;
        holder.tv.setText("17TV:" + position);
    }

    @Override
    protected void onItemFocus(View itemView, int position) {
        TextView tvFocus = (TextView) itemView.findViewById(R.id.tv_focus);
        ImageView focusBg = (ImageView) itemView.findViewById(R.id.focus_bg);

        tvFocus.setVisibility(View.VISIBLE);
        focusBg.setVisibility(View.VISIBLE);

        AnimationUtil.getInstance().zoomIn(itemView, 1.10f);
    }

    @Override
    protected void onItemGetNormal(View itemView, int position) {
        TextView tvFocus = (TextView) itemView.findViewById(R.id.tv_focus);
        ImageView focusBg = (ImageView) itemView.findViewById(R.id.focus_bg);

        tvFocus.setVisibility(View.VISIBLE);
        focusBg.setVisibility(View.INVISIBLE);

        AnimationUtil.getInstance().zoomOut(itemView);
    }

    @Override
    protected int getCount() {
        return mData.size();
    }

    private class GalleryViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_focus);
            iv = (ImageView) itemView.findViewById(R.id.im);
        }
    }
}
