package com.nnc.hughes.brew.di;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import com.nnc.hughes.brew.data.remote.*;

/**
 * Created by marcus on 10/10/17.
 */
@Module
public class NetworkModule {

    private static final String NAME_BASE_URL = "http://api.brewerydb.com/v2";

    @Provides
    @Singleton
    Converter.Factory provideGsonConverter() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Converter.Factory converter) {


        return new Retrofit.Builder()
                .baseUrl("http://api.brewerydb.com/v2")
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
