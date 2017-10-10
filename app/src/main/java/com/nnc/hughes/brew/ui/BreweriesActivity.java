package com.nnc.hughes.brew.ui;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nnc.hughes.brew.R;
import com.nnc.hughes.brew.data.models.Datum;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class BreweriesActivity extends DaggerAppCompatActivity implements BreweriesContract.View {
    @Inject
    BreweriesPresenter presenter;
    private static final String CURRENT_YEAR = "CURRENT_YEAR";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brew_list);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
        presenter.compositeDisposable.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();
        presenter.compositeDisposable.dispose();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_YEAR, 2012);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showTasks(List<Datum> datums) {

    }

    @Override
    public void showTaskDetailsUi(String taskId) {

    }

    @Override
    public void showLoadingTasksError() {
        showMessage(getString(R.string.loading_breweries_error));

    }

    private void showMessage(String message) {
        Snackbar.make(this, message, Snackbar.LENGTH_LONG).show();
    }

    public interface TaskItemListener {

        void onTaskClick(Datum clickedDatum);

    }



}
