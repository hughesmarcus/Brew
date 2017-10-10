package com.nnc.hughes.brew.di;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Provides;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marcus on 10/10/17.
 */

public class NetworkModule {

    private static final String NAME_BASE_URL = "http://api.brewerydb.com/v2";

    @Provides
    @Singleton
    Converter.Factory provideGsonConverter() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Converter.Factory converter, @Named(NAME_BASE_URL) String baseUrl) {


        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    BreweryAPI providesNetworkService(Retrofit retrofit) {
        return retrofit.create(BreweryAPI.class);
    }

}
