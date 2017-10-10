package com.nnc.hughes.brew.data.remote;

import com.nnc.hughes.brew.data.models.Breweries;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by marcus on 10/10/17.
 */

public interface BreweryAPI {
    @GET("/breweries")
    Observable<Breweries> getBreweryList(@Query("key")String key,
                       @Query("established")int year);

}
