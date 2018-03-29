package ru.vasiliev.hightechfmrss.data.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by vasiliev on 13/02/2018.
 */

public class ClientFactory {

    public static OkHttpClient getDefaultClient() {
        return new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS).readTimeout(40, TimeUnit.SECONDS).build();
    }

    public static OkHttpClient getDefaultClientWithStetho() {
        return new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS).readTimeout(40, TimeUnit.SECONDS)
                .addNetworkInterceptor(new StethoInterceptor()).build();
    }
}
