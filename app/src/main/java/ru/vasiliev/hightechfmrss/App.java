package ru.vasiliev.hightechfmrss;

import com.facebook.stetho.Stetho;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import ru.vasiliev.hightechfmrss.data.db.AppDatabase;
import ru.vasiliev.hightechfmrss.di.DatabaseModule;
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

        init();
    }

    private void init() {
        // Application database
        AppDatabase db = Room
                .databaseBuilder(getApplicationContext(), AppDatabase.class, "hightechfm.db")
                .build();

        // Dagger application component
        sAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this))
                .databaseModule(new DatabaseModule(db))
                .networkModule(new NetworkModule(BuildConfig.API_BASE_URL)).build();

        // Timber loggin
        Timber.plant(new Timber.DebugTree());

        // Stetho debug tools
        Stetho.initializeWithDefaults(this);
    }

    public static Context getContext() {
        return sAppComponent.getApp();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
}
