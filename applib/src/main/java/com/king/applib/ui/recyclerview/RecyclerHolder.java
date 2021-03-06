package com.king.applib.ui.recyclerview;

import android.graphics.Bitmap;
import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 适配一切RecyclerView.ViewHolder
 *
 * @author kymjs (http://www.kymjs.com/) on 8/27/15.
 */
public class RecyclerHolder extends RecyclerView.ViewHolder {
    private static final int DEFAULT_ITEM_COUNT = 6;
    private final SparseArray<View> mViews;
    
    public RecyclerHolder(View itemView) {
        super(itemView);
        this.mViews = new SparseArray<>(DEFAULT_ITEM_COUNT);
    }

    public RecyclerHolder(View itemView, int itemCounts) {
        super(itemView);
        this.mViews = new SparseArray<>(Math.max(itemCounts, DEFAULT_ITEM_COUNT));
    }

    public SparseArray<View> getAllView() {
        return mViews;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            if (view != null) {
                mViews.put(viewId, view);
            }
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     */
    public RecyclerHolder setText(@IdRes int viewId, String text) {
        TextView view = getView(viewId);
        if (view != null) {
            view.setText(text);
        }
        return this;
    }

    /**
     * 为ImageView设置图片
     */
    public RecyclerHolder setImageResource(@IdRes int viewId, int drawableId) {
        ImageView view = getView(viewId);
        if (view != null) {
            view.setImageResource(drawableId);
        }
        return this;
    }

    /**
     * 为ImageView设置图片
     */
    public RecyclerHolder setImageBitmap(@IdRes int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        if (view != null) {
            view.setImageBitmap(bm);
        }
        return this;
    }

    public RecyclerHolder setViewVisibility(@IdRes int viewId, int visibility) {
        View view = getView(viewId);
        if (view != null) {
            view.setVisibility(visibility);
        }
        return this;
    }

    public RecyclerHolder setOnClickListener(@IdRes int viewId, View.OnClickListener l) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(l);
        }
        return this;
    }
}
