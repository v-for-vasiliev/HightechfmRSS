package ru.vasiliev.hightechfmrss.domain;

import io.reactivex.Observable;
import retrofit2.http.GET;
import ru.vasiliev.hightechfmrss.model.RssFeed;

/**
 * Created by vasiliev on 04/02/2018.
 */

public interface HightechFmApi {
    @GET("feed.rss")
    Observable<RssFeed> getFeed();
}
