package ru.vasiliev.hightechfmrss.di;

import androidx.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.vasiliev.hightechfmrss.data.db.AppDatabase;

/**
 * Created by k.vasilev on 29/03/2018.
 */

@Module
public class DatabaseModule {

    private AppDatabase mAppDatabase;

    public DatabaseModule(@NonNull AppDatabase appDatabase) {
        mAppDatabase = appDatabase;
    }

    @Singleton
    @Provides
    AppDatabase provideAppDatabase() {
        return mAppDatabase;
    }
}
