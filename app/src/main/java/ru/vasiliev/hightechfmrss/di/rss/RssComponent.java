package ru.vasiliev.hightechfmrss.di.rss;

import dagger.Subcomponent;
import ru.vasiliev.hightechfmrss.di.scope.RssScope;
import ru.vasiliev.hightechfmrss.presentation.rss.RssPresenter;
import ru.vasiliev.hightechfmrss.repository.rss.RssRepository;

@RssScope
@Subcomponent(modules = RssModule.class)
public interface RssComponent {

    RssRepository getRssRepository();

    void inject(RssPresenter presenter);
}