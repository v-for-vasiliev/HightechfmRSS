package ru.vasiliev.hightechfmrss.di.main;

import dagger.Subcomponent;
import ru.vasiliev.hightechfmrss.di.scope.HomeScope;
import ru.vasiliev.hightechfmrss.presentation.main.MainPresenter;

/**
 * Created by vasiliev on 15/02/2018.
 */

@HomeScope
@Subcomponent(modules = MainModule.class)
public interface MainComponent {

    void inject(MainPresenter homeActivity);
}
