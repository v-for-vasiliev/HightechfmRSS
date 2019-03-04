package ru.vasiliev.hightechfmrss.viewstyle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private int mHeight = -1;

    public DividerItemDecoration(@DrawableRes int drawable, @NonNull Context context) {
        mDivider = context.getResources().getDrawable(drawable);
    }

    public DividerItemDecoration(@DrawableRes int drawable, @NonNull Context context, int height) {
        mDivider = context.getResources().getDrawable(drawable);
        mHeight = height;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + ((mHeight == -1) ? mDivider.getIntrinsicHeight() : mHeight);

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}