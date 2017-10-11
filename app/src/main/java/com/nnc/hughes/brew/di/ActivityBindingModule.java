package com.nnc.hughes.brew.di;

import com.nnc.hughes.brew.ui.BreweryIntentService;
import com.nnc.hughes.brew.ui.list.BreweriesActivity;
import com.nnc.hughes.brew.ui.list.BreweriesModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Marcus on 10/10/2017.
 */

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = BreweriesModule.class)
    abstract BreweriesActivity breweriesActivity();

    @ServiceScoped
    @ContributesAndroidInjector
    abstract BreweryIntentService breweryIntentService();


}