package com.nnc.hughes.brew.ui;

import android.support.annotation.NonNull;

import com.nnc.hughes.brew.BasePresenter;
import com.nnc.hughes.brew.BaseView;
import com.nnc.hughes.brew.data.models.Datum;

import java.util.List;

/**
 * Created by Marcus on 10/10/2017.
 */

public interface BreweriesContract {
    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showTasks(List<Datum> datums);

        void showTaskDetailsUi(String taskId);

        void showLoadingTasksError();
    }

    interface Presenter extends BasePresenter<View> {

        void loadBreweries(boolean forceUpdate);

        void openTaskDetails(@NonNull Datum requestedDatum);

        void dropView();

        void takeView(BreweriesContract.View view);
    }
}

