package com.nnc.hughes.brew.utils;

import android.support.test.espresso.IdlingResource;

/**
 * Created by Marcus on 10/11/2017.
 */
public class EspressoIdlingResource {

    private static final String RESOURCE = "GLOBAL";

    private static CountingIdlingResource countingIdlingResource =
            new CountingIdlingResource(RESOURCE);


    public static void decrement() {
        countingIdlingResource.decrement();
    }

    public static void increment() {
        countingIdlingResource.increment();
    }


    public static IdlingResource getIdlingResource() {
        return countingIdlingResource;
    }
}