package ru.vasiliev.hightechfmrss.di.bookmarks;

import dagger.Subcomponent;
import ru.vasiliev.hightechfmrss.di.scope.BookmarksScope;
import ru.vasiliev.hightechfmrss.presentation.bookmarks.BookmarksPresenter;

@BookmarksScope
@Subcomponent
public interface BookmarksComponent {

    void inject(BookmarksPresenter presenter);
}