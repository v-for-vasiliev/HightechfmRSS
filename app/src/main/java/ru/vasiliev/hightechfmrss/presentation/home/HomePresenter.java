package ru.vasiliev.hightechfmrss.presentation.home;

import android.support.v7.app.AppCompatActivity;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import ru.vasiliev.hightechfmrss.App;
import ru.vasiliev.hightechfmrss.di.home.HomeComponent;
import ru.vasiliev.hightechfmrss.presentation.MvpBasePresenter;
import ru.vasiliev.hightechfmrss.presentation.Router;

/**
 * Created by vasiliev on 15/02/2018.
 */

@InjectViewState
public class HomePresenter extends MvpBasePresenter<HomeView> {

    @Inject
    Router mRouter;

    HomePresenter() {
        HomeComponent component = App.getAppComponent().plusHomeComponent();
        component.inject(this);
    }

    void openRss(AppCompatActivity activity) {
        mRouter.openRss(activity);
    }
}
