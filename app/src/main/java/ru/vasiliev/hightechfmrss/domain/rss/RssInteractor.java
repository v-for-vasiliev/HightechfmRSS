package ru.vasiliev.hightechfmrss.domain.rss;

import javax.inject.Inject;

import io.reactivex.Observable;
import ru.vasiliev.hightechfmrss.domain.model.RssFeed;
import ru.vasiliev.hightechfmrss.repository.rss.RssRepository;

/**
 * Created by vasiliev on 11/02/2018.
 */

public class RssInteractor {

    private RssRepository mRssRepository;

    @Inject
    public RssInteractor(RssRepository rssRepository) {
        mRssRepository = rssRepository;
    }

    public Observable<RssFeed> getFeed() {
        return mRssRepository.getFeed();
    }
}
