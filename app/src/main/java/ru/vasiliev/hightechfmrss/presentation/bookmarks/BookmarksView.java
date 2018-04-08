package ru.vasiliev.hightechfmrss.presentation.bookmarks;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import ru.vasiliev.hightechfmrss.domain.model.Article;

/**
 * Created by vasiliev on 01/04/2018.
 */

public interface BookmarksView extends MvpView {

    void showLoader();

    void showBookmarks(List<Article> articleList);

    void showError(String error);

    void updateMenu(boolean bookmarksFound);

    void enableMenuItem(int id);

    void disableMenuItem(int id);
}
