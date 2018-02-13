package ru.vasiliev.hightechfmrss.di.app;

import javax.inject.Singleton;

import dagger.Component;
import ru.vasiliev.hightechfmrss.App;
import ru.vasiliev.hightechfmrss.di.ApiModule;
import ru.vasiliev.hightechfmrss.di.NetworkModule;
import ru.vasiliev.hightechfmrss.di.rss.RssComponent;
import ru.vasiliev.hightechfmrss.repository.datasource.HightechFmApi;

/**
 * Created by vasiliev on 11/02/2018.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, ApiModule.class})
public interface AppComponent {

    RssComponent plusRssComponent();

    App getApp();

    HightechFmApi getHightechFmApi();
}
