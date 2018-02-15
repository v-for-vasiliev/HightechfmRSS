package ru.vasiliev.hightechfmrss.di.home;

import dagger.Module;
import dagger.Provides;
import ru.vasiliev.hightechfmrss.di.scope.HomeScope;
import ru.vasiliev.hightechfmrss.presentation.Router;
import ru.vasiliev.hightechfmrss.presentation.RouterImpl;

/**
 * Created by vasiliev on 13/02/2018.
 */

@Module
public class HomeModule {

    @Provides
    @HomeScope
    public Router provideRouter() {
        return new RouterImpl();
    }
}
