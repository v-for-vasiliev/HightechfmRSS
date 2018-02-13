package ru.vasiliev.hightechfmrss.repository.rss;

import io.reactivex.Observable;
import ru.vasiliev.hightechfmrss.domain.model.RssFeed;
import ru.vasiliev.hightechfmrss.repository.datasource.HightechFmApi;

/**
 * Created by vasiliev on 11/02/2018.
 */

public class RssRepositoryImpl implements RssRepository {

    private HightechFmApi mHightechFmApi;

    public RssRepositoryImpl(HightechFmApi hightechFmApi) {
        mHightechFmApi = hightechFmApi;
    }

    @Override
    public Observable<RssFeed> getFeed() {
        return mHightechFmApi.getFeed();
    }
}
