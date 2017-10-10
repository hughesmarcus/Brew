package com.nnc.hughes.brew;

import android.app.Application;

import com.nnc.hughes.brew.di.AppComponent;
import com.nnc.hughes.brew.di.DaggerAppComponent;


import dagger.android.AndroidInjector;

import dagger.android.DaggerApplication;


/**
 * Created by marcus on 10/10/17.
 */

public class BrewApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }
}
