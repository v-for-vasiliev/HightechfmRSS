package ru.vasiliev.hightechfmrss.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.vasiliev.hightechfmrss.repository.datasource.HightechFmApi;
import ru.vasiliev.hightechfmrss.repository.rss.RssRepository;
import ru.vasiliev.hightechfmrss.repository.rss.RssRepositoryImpl;

/**
 * Created by vasiliev on 24/02/2018.
 */

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    RssRepository provideRssRepository(HightechFmApi hightechFmApi) {
        return new RssRepositoryImpl(hightechFmApi);
    }
}
