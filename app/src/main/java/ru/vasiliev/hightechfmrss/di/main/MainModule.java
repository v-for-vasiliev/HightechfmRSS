package ru.vasiliev.hightechfmrss.di.main;

import dagger.Module;
import dagger.Provides;
import ru.vasiliev.hightechfmrss.di.scope.HomeScope;
import ru.vasiliev.hightechfmrss.presentation.Router;
import ru.vasiliev.hightechfmrss.presentation.RouterImpl;

/**
 * Created by vasiliev on 13/02/2018.
 */

@Module
public class MainModule {

    @Provides
    @HomeScope
    public Router provideRouter() {
        return new RouterImpl();
    }
}
