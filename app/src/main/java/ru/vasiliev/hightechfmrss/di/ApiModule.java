package ru.vasiliev.hightechfmrss.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import ru.vasiliev.hightechfmrss.repository.datasource.HightechFmApi;

/**
 * Created by vasiliev on 13/02/2018.
 */

@Module
public class ApiModule {

    @Singleton
    @Provides
    public HightechFmApi provideHightechFmApi(Retrofit retrofit) {
        return retrofit.create(HightechFmApi.class);
    }
}
