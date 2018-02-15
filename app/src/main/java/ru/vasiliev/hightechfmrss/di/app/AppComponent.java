package ru.vasiliev.hightechfmrss.di.app;

import javax.inject.Singleton;

import dagger.Component;
import ru.vasiliev.hightechfmrss.App;
import ru.vasiliev.hightechfmrss.di.ApiModule;
import ru.vasiliev.hightechfmrss.di.NetworkModule;
import ru.vasiliev.hightechfmrss.di.article.ArticleComponent;
import ru.vasiliev.hightechfmrss.di.home.HomeComponent;
import ru.vasiliev.hightechfmrss.di.home.HomeModule;
import ru.vasiliev.hightechfmrss.di.rss.RssComponent;
import ru.vasiliev.hightechfmrss.repository.datasource.HightechFmApi;

/**
 * Created by vasiliev on 11/02/2018.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, ApiModule.class})
public interface AppComponent {

    App getApp();

    HightechFmApi getHightechFmApi();

    HomeComponent plusHomeComponent(HomeModule homeModule);

    RssComponent plusRssComponent();

    ArticleComponent plusArticleComponent();
}
