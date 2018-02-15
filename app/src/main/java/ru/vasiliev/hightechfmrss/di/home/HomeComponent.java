package ru.vasiliev.hightechfmrss.di.home;

import dagger.Subcomponent;
import ru.vasiliev.hightechfmrss.di.scope.HomeScope;
import ru.vasiliev.hightechfmrss.presentation.home.HomeActivity;
import ru.vasiliev.hightechfmrss.presentation.home.Router;

/**
 * Created by vasiliev on 15/02/2018.
 */

@HomeScope
@Subcomponent(modules = HomeModule.class)
public interface HomeComponent {

    Router getRouter();

    void inject(HomeActivity homeActivity);
}
