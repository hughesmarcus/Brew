package com.nnc.hughes.brew;

import com.nnc.hughes.brew.di.AppComponent;
import com.nnc.hughes.brew.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.HasActivityInjector;


/**
 * Created by marcus on 10/10/17.
 */

public class BrewApplication extends DaggerApplication implements HasActivityInjector {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }

}
