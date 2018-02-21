package ru.vasiliev.hightechfmrss.repository.rss;

import io.reactivex.Single;
import ru.vasiliev.hightechfmrss.domain.model.RssFeed;

/**
 * Created by vasiliev on 11/02/2018.
 */

public interface RssRepository {
    Single<RssFeed> getFeed(boolean allowCache);
}
