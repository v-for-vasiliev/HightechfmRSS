package ru.vasiliev.hightechfmrss.presentation.home;

import android.support.v4.app.FragmentManager;

import ru.vasiliev.hightechfmrss.R;
import ru.vasiliev.hightechfmrss.domain.model.Article;
import ru.vasiliev.hightechfmrss.presentation.article.ArticleFragment;
import ru.vasiliev.hightechfmrss.presentation.rss.RssFragment;
import ru.vasiliev.hightechfmrss.utils.FragmentUtils;

/**
 * Created by vasiliev on 15/02/2018.
 */

public class RouterImpl implements Router {
    private FragmentManager mFragmentManager;

    public RouterImpl(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    @Override
    public void openRss() {
        FragmentUtils.replaceWithHistory(mFragmentManager, RssFragment.newInstance(),
                R.id.fragment_container);
    }

    @Override
    public void openArticle(Article article) {
        FragmentUtils.replaceWithHistory(mFragmentManager, ArticleFragment.newInstance(article),
                R.id.fragment_container);
    }
}
