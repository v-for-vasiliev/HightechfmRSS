package ru.vasiliev.hightechfmrss.data.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by vasiliev on 13/02/2018.
 */

public class ClientFactory {

    public static OkHttpClient getDefaultClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .build();
    }
}
