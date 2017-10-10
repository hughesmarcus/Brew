package com.nnc.hughes.brew.di;

import com.nnc.hughes.brew.ui.BreweriesActivity;
import com.nnc.hughes.brew.ui.BreweriesModule;

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


}