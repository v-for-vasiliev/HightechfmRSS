package ru.vasiliev.hightechfmrss.data.network;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by vasiliev on 04/02/2018.
 */

@Module
public class NetworkModule {
    public static final String API_BASE_URL = "https://hightech.fm";

    @Provides
    @Singleton
    public static OkHttpClient getNetworkClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(getNetworkClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
