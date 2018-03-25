package ru.vasiliev.hightechfmrss.viewstyle;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by vasiliev on 24/03/2018.
 */

public class TopSnapLayoutManager extends LinearLayoutManager {

    public TopSnapLayoutManager(Context context) {
        super(context);
    }

    public TopSnapLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public TopSnapLayoutManager(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state,
            int position) {
        super.smoothScrollToPosition(recyclerView, state, position);

        RecyclerView.SmoothScroller smoothScroller = new TopSnapSmoothScroller(
                recyclerView.getContext());
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    private class TopSnapSmoothScroller extends LinearSmoothScroller {
        public TopSnapSmoothScroller(Context context) {
            super(context);
        }

        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return TopSnapLayoutManager.this.computeScrollVectorForPosition(targetPosition);
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_START;
        }
    }
}
