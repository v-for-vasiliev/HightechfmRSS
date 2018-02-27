package ru.vasiliev.hightechfmrss.di.rss;

import dagger.Module;
import dagger.Provides;
import ru.vasiliev.hightechfmrss.di.scope.RssScope;
import ru.vasiliev.hightechfmrss.presentation.Router;
import ru.vasiliev.hightechfmrss.presentation.RouterImpl;

/**
 * Created by vasiliev on 04/02/2018.
 */

@Module
public class RssModule {

    @RssScope
    @Provides
    public Router provideRouter() {
        return new RouterImpl();
    }
}
