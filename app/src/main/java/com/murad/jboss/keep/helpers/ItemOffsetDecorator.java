package com.murad.jboss.keep.helpers;

import android.content.Context;
import android.graphics.Rect;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by murad on 10/01/18.
 */

public class ItemOffsetDecorator extends RecyclerView.ItemDecoration {

    private int mItemOffset;

    private ItemOffsetDecorator(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public ItemOffsetDecorator(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
    }
}