package ru.vasiliev.hightechfmrss.repository.rss;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;
import ru.vasiliev.hightechfmrss.domain.model.RssFeed;
import ru.vasiliev.hightechfmrss.repository.datasource.HightechFmApi;
import timber.log.Timber;

/**
 * Created by vasiliev on 11/02/2018.
 */

public class RssRepositoryImpl implements RssRepository {

    private HightechFmApi mHightechFmApi;

    private RssFeed mCachedFeed;

    private final PublishSubject<RssFeed> mUpdatesSubject = PublishSubject.create();

    private boolean mLoading;

    public RssRepositoryImpl(HightechFmApi hightechFmApi) {
        Timber.d("RssRepositoryImpl(%d)", hashCode());
        mHightechFmApi = hightechFmApi;
        mCachedFeed = null;
        mLoading = false;
    }

    @Override
    public Single<RssFeed> loadFeed(boolean allowCache) {
        Timber.d("RssRepositoryImpl(%d) - loadFeed(allowCache = %b, hasCache = %b)", hashCode(),
                allowCache, (mCachedFeed != null));
        if (allowCache && mCachedFeed != null) {
            return Single.just(mCachedFeed);
        }
        setLoading(true);
        return mHightechFmApi.getFeed().map(rssFeed -> {
            mUpdatesSubject.onNext(mCachedFeed = rssFeed);
            setLoading(false);
            return rssFeed;
        }).onErrorResumeNext(throwable -> {
            setLoading(false);
            return Single.error(throwable);
        });
    }

    @Override
    public synchronized boolean isLoading() {
        return mLoading;
    }

    private synchronized void setLoading(boolean loading) {
        Timber.w("RssRepositoryImpl(%d) - setLoading(%b -> %b)", hashCode(), mLoading, loading);
        mLoading = loading;
    }

    @Override
    public Observable<RssFeed> getUpdates() {
        return mUpdatesSubject.map(rssFeed -> {
            Timber.w("RssRepositoryImpl(%d) - onUpdate()", hashCode());
            return rssFeed;
        });
    }
}
