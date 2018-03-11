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
        ArticleCategory category = ArticleCategory.of(i);
        switch (category) {
            case ALL:
            case CASES:
            case IDEAS:
            case BLOCKCHAIN:
            case OPINIONS:
            case TRENDS:
                return RssFragment.newInstance(category.getId());
            default:
                return RssFragment.newInstance(ArticleCategory.ALL.getId());
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
