package ru.vasiliev.hightechfmrss.repository.datasource;

import io.reactivex.Single;
import retrofit2.http.GET;
import ru.vasiliev.hightechfmrss.domain.model.RssFeed;

/**
 * Created by vasiliev on 04/02/2018.
 */

public interface HightechFmApi {
    @GET("feed.rss")
    Single<RssFeed> getFeed();
}
