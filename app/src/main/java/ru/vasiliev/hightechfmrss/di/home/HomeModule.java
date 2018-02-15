package ru.vasiliev.hightechfmrss.di.home;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import dagger.Module;
import dagger.Provides;
import ru.vasiliev.hightechfmrss.di.scope.HomeScope;
import ru.vasiliev.hightechfmrss.presentation.home.Router;
import ru.vasiliev.hightechfmrss.presentation.home.RouterImpl;

/**
 * Created by vasiliev on 13/02/2018.
 */

@Module
public class HomeModule {
    private final FragmentManager mFragmentManager;

    public HomeModule(@NonNull FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    @Provides
    @HomeScope
    public Router provideRouter() {
        return new RouterImpl(mFragmentManager);
    }
}
