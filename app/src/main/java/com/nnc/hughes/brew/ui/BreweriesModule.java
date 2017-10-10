package com.nnc.hughes.brew.ui;

import com.nnc.hughes.brew.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Marcus on 10/10/2017.
 */
@Module
public abstract class BreweriesModule {

    @ActivityScoped
    @Binds
    abstract BreweriesContract.Presenter breweriesPresenter(BreweriesPresenter presenter);
}
