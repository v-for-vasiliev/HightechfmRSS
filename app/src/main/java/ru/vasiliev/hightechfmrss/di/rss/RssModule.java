package ru.vasiliev.hightechfmrss.di.rss;

import dagger.Module;
import dagger.Provides;
import ru.vasiliev.hightechfmrss.di.scope.RssScope;
import ru.vasiliev.hightechfmrss.repository.datasource.HightechFmApi;
import ru.vasiliev.hightechfmrss.repository.rss.RssRepository;
import ru.vasiliev.hightechfmrss.repository.rss.RssRepositoryImpl;

/**
 * Created by vasiliev on 04/02/2018.
 */

@Module//(includes = ApiModule.class)
public class RssModule {

    @RssScope
    @Provides
    RssRepository provideRssRepository(HightechFmApi hightechFmApi) {
        return new RssRepositoryImpl(hightechFmApi);
    }
}
