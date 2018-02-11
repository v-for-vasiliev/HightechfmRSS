package ru.vasiliev.hightechfmrss.di.module;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.vasiliev.hightechfmrss.App;

@Module
public class AppModule {

    private final App mApp;

    public AppModule(@NonNull App appContext) {
        mApp = appContext;
    }

    @Provides
    @Singleton
    App provideApp() {
        return mApp;
    }

}