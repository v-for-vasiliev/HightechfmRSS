package ru.vasiliev.hightechfmrss.presentation.main;

import com.arellomobile.mvp.InjectViewState;

import ru.vasiliev.hightechfmrss.App;
import ru.vasiliev.hightechfmrss.di.main.MainComponent;
import ru.vasiliev.hightechfmrss.presentation.MvpBasePresenter;

/**
 * Created by vasiliev on 15/02/2018.
 */

@InjectViewState
public class MainPresenter extends MvpBasePresenter<MainView> {

    MainPresenter() {
        MainComponent component = App.getAppComponent().plusHomeComponent();
        component.inject(this);
    }
}
