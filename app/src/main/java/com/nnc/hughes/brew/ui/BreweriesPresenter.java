package com.nnc.hughes.brew.ui;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nnc.hughes.brew.data.models.Datum;
import com.nnc.hughes.brew.data.remote.BreweryAPI;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by Marcus on 10/10/2017.
 */

public class BreweriesPresenter implements BreweriesContract.Presenter {

    private final BreweryAPI breweriesRepository;

    private CompositeDisposable compositeDisposable;

    @Nullable
    private BreweriesContract.View view;

    private boolean firstLoad = true;

    @Inject
    BreweriesPresenter(BreweryAPI breweriesRepository) {
        this.breweriesRepository = breweriesRepository;
    }

    @Override
    public void loadBreweries(boolean forceUpdate) {
        loadBreweries(forceUpdate || firstLoad, true);
        firstLoad = false;
    }

    private void loadBreweries(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            if (view != null) {
                view.setLoadingIndicator(true);
            }
        }
        compositeDisposable.add(breweriesRepository.getBreweryList("d98bfcefa0e5bb66b490574d17e11230", 2012)
                .subscribeOn(Schedulers.io())
                .doOnError(error -> view.showLoadingTasksError())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Breweries -> view.showTasks(Breweries.getData())));
        Log.v("hello", "here");
        if (showLoadingUI) {
            view.setLoadingIndicator(false);
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void openTaskDetails(@NonNull Datum requestedTask) {
        checkNotNull(requestedTask, "requestedTask cannot be null!");
        if (view != null) {
            view.showTaskDetailsUi(requestedTask.getId());
        }
    }

    @Override
    public void takeView(BreweriesContract.View view, CompositeDisposable compositeDisposable) {
        this.view = view;
        this.compositeDisposable = compositeDisposable;
        loadBreweries(false);
    }

    @Override
    public void dropView() {
        view = null;
        compositeDisposable.dispose();
    }

}
