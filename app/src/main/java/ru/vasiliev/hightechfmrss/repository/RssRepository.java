package ru.vasiliev.hightechfmrss.repository;

import io.reactivex.Observable;
import ru.vasiliev.hightechfmrss.domain.model.RssFeed;

/**
 * Created by vasiliev on 11/02/2018.
 */

public interface RssRepository {
    Observable<RssFeed> getFeed();
}
