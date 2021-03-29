package com.thisrahul.miskaatask.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static NetworkService mInstance;
    private final Cache cache;
    private final File httpCacheDirectory;
    private final Gson gson;
    private final ApiInterface apiInterface;

    public NetworkService(File cacheDir) {
        httpCacheDirectory = new File(cacheDir, "offlineCache");

        //10 MB
        cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
        mInstance = this;
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .callTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .build();
        gson = new GsonBuilder().setLenient().create();
        apiInterface = new Retrofit.Builder()
                .baseUrl("https://restcountries.eu/rest/v2/region/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(ApiInterface.class);
    }

    public static NetworkService getInstance() {
        return mInstance;
    }

    public ApiInterface getWebApi() {
        return apiInterface;
    }
}
