package ru.vasiliev.hightechfmrss;

import android.app.Application;
import android.content.Context;

import ru.vasiliev.hightechfmrss.di.NetworkModule;
import ru.vasiliev.hightechfmrss.di.app.AppComponent;
import ru.vasiliev.hightechfmrss.di.app.AppModule;
import ru.vasiliev.hightechfmrss.di.app.DaggerAppComponent;
import timber.log.Timber;

/**
 * Created by vasiliev on 11/02/2018.
 */

public class App extends Application {

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        sAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).networkModule(
                new NetworkModule(BuildConfig.API_BASE_URL)).build();
    }

    public static Context getContext() {
        return sAppComponent.getApp();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
}
