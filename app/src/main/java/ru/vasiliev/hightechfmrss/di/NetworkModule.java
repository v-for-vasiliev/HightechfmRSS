package ru.vasiliev.hightechfmrss.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import ru.vasiliev.hightechfmrss.data.network.ClientFactory;
import ru.vasiliev.hightechfmrss.data.network.RetrofitFactory;

/**
 * Created by vasiliev on 11/02/2018.
 */

@Module
public class NetworkModule {

    private final String mBaseUrl;

    public NetworkModule(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    @Singleton
    @Provides
    public OkHttpClient provideNetworkClient() {
        return ClientFactory.getDefaultClient();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(OkHttpClient client) {
        return RetrofitFactory.getRssRetrofit(mBaseUrl, client);
    }
}
