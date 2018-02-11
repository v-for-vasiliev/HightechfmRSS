package ru.vasiliev.hightechfmrss.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import ru.vasiliev.hightechfmrss.api.HightechFmApi;
import ru.vasiliev.hightechfmrss.data.network.RssRepositoryImpl;
import ru.vasiliev.hightechfmrss.repository.RssRepository;

/**
 * Created by vasiliev on 04/02/2018.
 */

@Module(includes = NetworkModule.class)
public class DataModule {
    @Provides
    @Singleton
    public HightechFmApi provideHightechFmApi(Retrofit retrofit) {
        return retrofit.create(HightechFmApi.class);
    }

    @Provides
    @Singleton
    RssRepository provideBookRepository(HightechFmApi hightechFmApi) {
        return new RssRepositoryImpl(hightechFmApi);
    }
}
