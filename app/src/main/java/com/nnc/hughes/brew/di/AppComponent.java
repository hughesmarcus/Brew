package com.nnc.hughes.brew.di;

import android.app.Application;

import com.nnc.hughes.brew.BrewApplication;
import com.nnc.hughes.brew.ui.BreweryIntentService;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Marcus on 10/10/2017.
 */
@Singleton
@Component(modules = {NetworkModule.class,
        ApplicationModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<DaggerApplication> {

    void inject(BrewApplication application);

    void inject(BreweryIntentService breweryIntentService);

    @Override
    void inject(DaggerApplication instance);

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}