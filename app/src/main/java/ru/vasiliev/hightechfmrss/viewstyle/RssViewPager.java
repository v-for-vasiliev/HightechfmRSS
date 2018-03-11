package ru.vasiliev.hightechfmrss.viewstyle;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class RssViewPager extends ViewPager {
    private Boolean mPagingEnabled = true;

    public RssViewPager(Context context) {
        super(context);
    }

    public RssViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mPagingEnabled && super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mPagingEnabled && super.onTouchEvent(event);
    }

    public void togglePaging(boolean pagingEnabled) {
        this.mPagingEnabled = pagingEnabled;
    }
}