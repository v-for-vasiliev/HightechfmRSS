package ru.vasiliev.hightechfmrss.di.rss;

import dagger.Subcomponent;
import ru.vasiliev.hightechfmrss.di.scope.RssScope;
import ru.vasiliev.hightechfmrss.presentation.Router;
import ru.vasiliev.hightechfmrss.presentation.main.rss.RssPresenter;

@RssScope
@Subcomponent(modules = RssModule.class)
public interface RssComponent {

    Router getRouter();

    void inject(RssPresenter presenter);
}