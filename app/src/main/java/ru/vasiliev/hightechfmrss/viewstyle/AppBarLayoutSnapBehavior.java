package ru.vasiliev.hightechfmrss.viewstyle;

import android.animation.ValueAnimator;
import android.content.Context;
import com.google.android.material.appbar.AppBarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * AppBar edge snapping behavior.
 * When scrolling ends, if the view is only partially visible, the view is snapped and scrolled to
 * its closest edge.
 */
public class AppBarLayoutSnapBehavior extends AppBarLayout.Behavior {

    private ValueAnimator mAnimator;

    private boolean mNestedScrollStarted = false;

    public AppBarLayoutSnapBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child,
            View directTargetChild, View target, int nestedScrollAxes, int type) {
        mNestedScrollStarted = super
                .onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes,
                        type);

        if (mNestedScrollStarted && mAnimator != null) {
            mAnimator.cancel();
        }

        return mNestedScrollStarted;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl,
            View target, int type) {
        super.onStopNestedScroll(coordinatorLayout, abl, target, type);

        if (!mNestedScrollStarted) {
            return;
        }

        mNestedScrollStarted = false;

        int scrollRange = abl.getTotalScrollRange();
        int topOffset = getTopAndBottomOffset();

        if (topOffset <= -scrollRange || topOffset >= 0) {
            // Already fully visible or fully invisible
            return;
        }

        if (topOffset < -(scrollRange / 2f)) {
            // Snap up (to fully invisible)
            animateOffsetTo(-scrollRange);
        } else {
            // Snap down (to fully visible)
            animateOffsetTo(0);
        }
    }

    private void animateOffsetTo(int offset) {
        if (mAnimator == null) {
            mAnimator = new ValueAnimator();
            mAnimator.setInterpolator(new DecelerateInterpolator());
            mAnimator.addUpdateListener(
                    animation -> setTopAndBottomOffset((int) animation.getAnimatedValue()));
        } else {
            mAnimator.cancel();
        }

        mAnimator.setIntValues(getTopAndBottomOffset(), offset);
        mAnimator.start();
    }
}