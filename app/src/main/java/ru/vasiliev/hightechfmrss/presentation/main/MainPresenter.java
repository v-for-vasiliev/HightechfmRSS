package ru.vasiliev.hightechfmrss.presentation.main;

import android.support.v7.app.AppCompatActivity;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import ru.vasiliev.hightechfmrss.App;
import ru.vasiliev.hightechfmrss.di.main.MainComponent;
import ru.vasiliev.hightechfmrss.presentation.MvpBasePresenter;
import ru.vasiliev.hightechfmrss.presentation.Router;

/**
 * Created by vasiliev on 15/02/2018.
 */

@InjectViewState
public class MainPresenter extends MvpBasePresenter<MainView> {

    @Inject
    Router mRouter;

    MainPresenter() {
        MainComponent component = App.getAppComponent().plusHomeComponent();
        component.inject(this);
    }

    void openRss(AppCompatActivity activity) {
        mRouter.openRss(activity);
    }
}
