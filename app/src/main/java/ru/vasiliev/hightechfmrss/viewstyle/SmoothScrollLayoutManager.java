package ru.vasiliev.hightechfmrss.viewstyle;

import android.content.Context;
import android.graphics.PointF;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

/**
 * Created by vasiliev on 24/03/2018.
 */

public class SmoothScrollLayoutManager extends LinearLayoutManager {

    private static final float MILLISECONDS_PER_INCH = 25f;

    private Context mContext;

    public SmoothScrollLayoutManager(Context context) {
        super(context);
        mContext = context;
    }

    public SmoothScrollLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        mContext = context;
    }

    public SmoothScrollLayoutManager(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state,
            int position) {
        super.smoothScrollToPosition(recyclerView, state, position);

        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(mContext) {

            // Controls the direction in which smoothScroll looks for your view
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return SmoothScrollLayoutManager.this
                        .computeScrollVectorForPosition(targetPosition);
            }

            // Returns the milliseconds it takes to scroll one pixel
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
            }
        };

        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }
}
