package com.nnc.hughes.brew.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nnc.hughes.brew.data.models.Breweries;
import com.nnc.hughes.brew.data.models.Datum;
import com.nnc.hughes.brew.data.remote.BreweryAPI;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by Marcus on 10/10/2017.
 */

public class BreweriesPresenter implements BreweriesContract.Presenter {

    private final BreweryAPI breweriesRepository;

    public CompositeDisposable compositeDisposable;

    @Nullable
    private BreweriesContract.View view;

    private boolean firstLoad = true;

    @Inject
    BreweriesPresenter(BreweryAPI breweriesRepository, CompositeDisposable compositeDisposable) {
        this.breweriesRepository = breweriesRepository;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void loadBreweries(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced on first load.
        loadBreweries(forceUpdate || firstLoad, true);
        firstLoad = false;
    }

    private void loadBreweries(boolean forceUpdate, final boolean showLoadingUI) {
        view.setLoadingIndicator(true);
        view.setLoadingIndicator(false);
    }

    @Override
    public void openTaskDetails(@NonNull Datum requestedTask) {
        checkNotNull(requestedTask, "requestedTask cannot be null!");
        if (view != null) {
            view.showTaskDetailsUi(requestedTask.getId());
        }
    }

    @Override
    public void takeView(BreweriesContract.View view) {
        this.view = view;
        loadBreweries(false);
    }

    @Override
    public void dropView() {
        view = null;
    }
}
