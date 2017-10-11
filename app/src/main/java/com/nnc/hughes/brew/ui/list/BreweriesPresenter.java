package com.nnc.hughes.brew.ui.list;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nnc.hughes.brew.data.models.Datum;
import com.nnc.hughes.brew.data.remote.BreweryAPI;
import com.nnc.hughes.brew.utils.EspressoIdlingResource;

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
    public BreweriesPresenter(BreweryAPI breweriesRepository) {
        this.breweriesRepository = breweriesRepository;
    }

    @Override
    public void loadBreweries(boolean forceUpdate, int year) {
        loadBreweries(forceUpdate || firstLoad, true, year);
        firstLoad = false;
    }

    private void loadBreweries(boolean forceUpdate, final boolean showLoadingUI, int year) {
        if (showLoadingUI) {
            if (view != null) {
                view.setLoadingIndicator(true);
            }
        }
        EspressoIdlingResource.increment();
        compositeDisposable.add(breweriesRepository.getBreweryList("d98bfcefa0e5bb66b490574d17e11230", year)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                        EspressoIdlingResource.decrement();
                    }
                })
                .subscribe(Breweries -> view.showTasks(Breweries.getData()), throwable -> view.showLoadingTasksError()));
        if (showLoadingUI) {
            view.setLoadingIndicator(false);
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void openTaskDetails(@NonNull Datum requestedBrewery) {
        checkNotNull(requestedBrewery, "requestedTask cannot be null!");
        if (view != null) {
            view.showTaskDetailsUi(requestedBrewery);
        }
    }

    @Override
    public void takeView(BreweriesContract.View view, CompositeDisposable compositeDisposable) {
        this.view = view;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void dropView() {
        view = null;
        compositeDisposable.dispose();
    }

}
