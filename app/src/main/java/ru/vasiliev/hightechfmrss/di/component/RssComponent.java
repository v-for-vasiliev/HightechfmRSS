package ru.vasiliev.hightechfmrss.di.component;

import dagger.Subcomponent;
import ru.vasiliev.hightechfmrss.presentation.rss.RssPresenter;

@Subcomponent
public interface RssComponent {

    void inject(RssPresenter presenter);
}