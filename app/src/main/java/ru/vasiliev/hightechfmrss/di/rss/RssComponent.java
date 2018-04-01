package ru.vasiliev.hightechfmrss.di.rss;

import dagger.Subcomponent;
import ru.vasiliev.hightechfmrss.di.scope.RssScope;
import ru.vasiliev.hightechfmrss.presentation.main.rss.RssPresenter;

@RssScope
@Subcomponent
public interface RssComponent {

    void inject(RssPresenter presenter);
}