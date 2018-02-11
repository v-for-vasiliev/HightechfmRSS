package ru.vasiliev.hightechfmrss.di.component;

import javax.inject.Singleton;

import dagger.Component;
import ru.vasiliev.hightechfmrss.App;
import ru.vasiliev.hightechfmrss.di.module.AppModule;
import ru.vasiliev.hightechfmrss.di.module.DataModule;
import ru.vasiliev.hightechfmrss.di.module.NetworkModule;
import ru.vasiliev.hightechfmrss.repository.RssRepository;

/**
 * Created by vasiliev on 11/02/2018.
 */

@Component(modules = {AppModule.class, DataModule.class, NetworkModule.class})
@Singleton
public interface AppComponent {

    RssComponent plusRssComponent();

    App getApp();

    RssRepository getRssRepository();
}
