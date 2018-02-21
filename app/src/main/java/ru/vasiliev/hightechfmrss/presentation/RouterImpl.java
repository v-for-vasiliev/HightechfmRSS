package ru.vasiliev.hightechfmrss.presentation;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import ru.vasiliev.hightechfmrss.domain.model.Article;

/**
 * Created by vasiliev on 15/02/2018.
 */

public class RouterImpl implements Router {

    public RouterImpl() {
    }

    @Override
    public void openRss(@NonNull AppCompatActivity activity) {
        /*
        FragmentUtils.replaceWithHistory(activity.getSupportFragmentManager(),
                RssFragment.newInstance(),
                R.id.fragment_container);
                */
    }

    @Override
    public void openArticle(@NonNull AppCompatActivity activity, Article article) {
        /*
        FragmentUtils.replaceWithHistory(activity.getSupportFragmentManager(),
                ArticleFragment.newInstance(article),
                R.id.fragment_container);
                */
    }
}
