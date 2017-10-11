package com.nnc.hughes.brew.ui.list;

import android.support.annotation.NonNull;

import com.nnc.hughes.brew.BasePresenter;
import com.nnc.hughes.brew.BaseView;
import com.nnc.hughes.brew.data.models.Datum;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Marcus on 10/10/2017.
 */

public interface BreweriesContract {
    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showTasks(List<Datum> datums);

        void showTaskDetailsUi(Datum brewery);

        void showLoadingTasksError();

        void setPresenter();
    }

    interface Presenter extends BasePresenter<View> {

        void loadBreweries(boolean forceUpdate, int year);

        void openTaskDetails(@NonNull Datum requestedDatum);

        void dropView();

        void takeView(BreweriesContract.View view, CompositeDisposable compositeDisposable);
    }
}

