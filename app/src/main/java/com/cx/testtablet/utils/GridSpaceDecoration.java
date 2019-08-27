package com.cx.testtablet.utils;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridSpaceDecoration extends RecyclerView.ItemDecoration {
    private int space;
    public GridSpaceDecoration(int space){
        this.space = space;
    }
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) % 2 == 0)
            outRect.right = space;
        outRect.bottom = space;
    }
}
