package ru.vasiliev.hightechfmrss.di.app;

import javax.inject.Singleton;

import dagger.Component;
import ru.vasiliev.hightechfmrss.App;
import ru.vasiliev.hightechfmrss.data.db.AppDatabase;
import ru.vasiliev.hightechfmrss.di.ApiModule;
import ru.vasiliev.hightechfmrss.di.DatabaseModule;
import ru.vasiliev.hightechfmrss.di.NetworkModule;
import ru.vasiliev.hightechfmrss.di.RepositoryModule;
import ru.vasiliev.hightechfmrss.di.article.ArticleComponent;
import ru.vasiliev.hightechfmrss.di.main.MainComponent;
import ru.vasiliev.hightechfmrss.di.rss.RssComponent;
import ru.vasiliev.hightechfmrss.repository.datasource.HightechFmApi;
import ru.vasiliev.hightechfmrss.repository.rss.RssRepository;

/**
 * Created by vasiliev on 11/02/2018.
 */

@Singleton
@Component(modules = {AppModule.class, DatabaseModule.class, NetworkModule.class, ApiModule.class,
        RepositoryModule.class})
public interface AppComponent {

    App getApp();

    AppDatabase getAppDatabase();

    HightechFmApi getHightechFmApi();

    RssRepository getRssRepository();

    MainComponent plusHomeComponent();

    RssComponent plusRssComponent();

    ArticleComponent plusArticleComponent();
}
