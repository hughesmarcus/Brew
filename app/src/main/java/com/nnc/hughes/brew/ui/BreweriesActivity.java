package com.nnc.hughes.brew.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nnc.hughes.brew.R;
import com.nnc.hughes.brew.data.models.Datum;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;

import static java.util.Collections.emptyList;

public class BreweriesActivity extends DaggerAppCompatActivity implements BreweriesContract.View {
    private static final String CURRENT_YEAR = "CURRENT_YEAR";
    @Inject
    BreweriesPresenter presenter;
    BreweriesAdapter adapter;
    @BindView(R.id.items_rv)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brew_list);
        ButterKnife.bind(this);
        adapter = new BreweriesAdapter(this, emptyList(), (datum) -> presenter.openTaskDetails(datum));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        presenter.takeView(this, compositeDisposable);
        presenter.loadBreweries(true);


    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();
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
        adapter.replaceData(datums);

    }

    @Override
    public void showTaskDetailsUi(String taskId) {

    }

    @Override
    public void showLoadingTasksError() {
        showMessage(getString(R.string.loading_breweries_error));

    }

    private void showMessage(String message) {
        //Snackbar.make(this, message, Snackbar.LENGTH_LONG).show();
    }


}
