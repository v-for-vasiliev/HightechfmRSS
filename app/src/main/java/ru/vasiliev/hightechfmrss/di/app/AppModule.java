package ru.vasiliev.hightechfmrss.di.app;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.vasiliev.hightechfmrss.App;
import ru.vasiliev.hightechfmrss.presentation.Router;
import ru.vasiliev.hightechfmrss.presentation.RouterImpl;

@Module
public class AppModule {

    private final App mApp;

    public AppModule(@NonNull App appContext) {
        mApp = appContext;
    }

    @Singleton
    @Provides
    App provideApp() {
        return mApp;
    }

    @Singleton
    @Provides
    public Router provideRouter() {
        return new RouterImpl();
    }
}