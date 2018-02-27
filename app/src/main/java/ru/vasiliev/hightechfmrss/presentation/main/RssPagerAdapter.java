package ru.vasiliev.hightechfmrss.presentation.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ru.vasiliev.hightechfmrss.domain.model.ArticleCategory;
import ru.vasiliev.hightechfmrss.presentation.main.rss.RssFragment;

/**
 * Created by vasiliev on 22/02/2018.
 */

public class RssPagerAdapter extends FragmentStatePagerAdapter {
    public RssPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (ArticleCategory.of(i)) {
            case ALL:
                return RssFragment.newInstance();
            case CASES:
                return RssFragment.newInstance();
            case IDEAS:
                return RssFragment.newInstance();
            case BLOCKCHAIN:
                return RssFragment.newInstance();
            case OPINIONS:
                return RssFragment.newInstance();
            case TRENDS:
                return RssFragment.newInstance();
            case OTHER:
            default:
                return RssFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return ArticleCategory.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ArticleCategory.of(position).getTitle();
    }
}
