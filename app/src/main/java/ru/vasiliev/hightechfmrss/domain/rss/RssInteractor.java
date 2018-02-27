package ru.vasiliev.hightechfmrss.domain.rss;

import javax.inject.Inject;

import io.reactivex.Single;
import ru.vasiliev.hightechfmrss.di.scope.RssScope;
import ru.vasiliev.hightechfmrss.domain.model.RssFeed;
import ru.vasiliev.hightechfmrss.repository.rss.RssRepository;
import ru.vasiliev.hightechfmrss.utils.RxUtils;

/**
 * Created by vasiliev on 11/02/2018.
 */

@RssScope
public class RssInteractor {

    private RssRepository mRssRepository;

    @Inject
    public RssInteractor(RssRepository rssRepository) {
        mRssRepository = rssRepository;
    }

    public Single<RssFeed> getFeed(boolean allowCache) {
        return mRssRepository.getFeed(allowCache).compose(RxUtils.singleIoScheduler());
    }
}
