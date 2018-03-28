package ru.vasiliev.hightechfmrss.viewstyle;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;

import ru.vasiliev.hightechfmrss.R;

public class FadingToolbarOffsetChangedListener implements
        AppBarLayout.OnOffsetChangedListener {

    private ActionBar mSupportActionBar;

    private Context mContext;

    private Drawable mAlphaDrawable;

    public FadingToolbarOffsetChangedListener(@NonNull Context context,
            @NonNull ActionBar supportActionBar) {
        mSupportActionBar = supportActionBar;
        mContext = context;
        mAlphaDrawable = new ColorDrawable(
                mContext.getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            mSupportActionBar.setBackgroundDrawable(null);
            mSupportActionBar.setDisplayShowTitleEnabled(false);
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            mSupportActionBar.setDisplayShowTitleEnabled(true);
            mSupportActionBar.setBackgroundDrawable(new ColorDrawable(
                    mContext.getResources().getColor(R.color.colorPrimaryDark)));
        } else {
            mSupportActionBar.setDisplayShowTitleEnabled(false);
            int alpha =
                    100 - (int) ((verticalOffset * 100.0f) / appBarLayout.getTotalScrollRange());
            mAlphaDrawable.setAlpha(alpha);
            mSupportActionBar.setBackgroundDrawable(mAlphaDrawable);
        }
    }
}