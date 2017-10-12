package com.nnc.hughes.brew.ui.list;

import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.nnc.hughes.brew.R;
import com.nnc.hughes.brew.data.models.Datum;
import com.nnc.hughes.brew.ui.BreweryIntentService;
import com.nnc.hughes.brew.ui.detail.BreweryDetailActivity;
import com.nnc.hughes.brew.utils.EspressoIdlingResource;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;

import static java.util.Collections.emptyList;

public class BreweriesActivity extends DaggerAppCompatActivity implements BreweriesContract.View, SwipeRefreshLayout.OnRefreshListener {
    private static final String CURRENT_YEAR = "CURRENT_YEAR";
    @Inject
    BreweriesPresenter presenter;
    BreweriesAdapter adapter;
    @BindView(R.id.brewery_rv)
    RecyclerView recyclerView;
    @BindView(R.id.activity_brewery_progressBar)
    ProgressBar progressBar;
    SearchView searchView;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    private int year = 2017;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindowAnimations();
        setContentView(R.layout.activity_brew_list);
        ButterKnife.bind(this);
        setupRefresh();
        setupAlarm();
        adapter = new BreweriesAdapter(this, emptyList(), (datum) -> presenter.openTaskDetails(datum));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setPresenter();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                year = Integer.parseInt(query);
                presenter.loadBreweries(year);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    public void setupRefresh() {

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
    }

    @Override
    public void onRefresh() {

        presenter.loadBreweries(year);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setPresenter() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        presenter.takeView(this, compositeDisposable);
        presenter.loadBreweries(year);
    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slideTransition = new Slide();
            slideTransition.setSlideEdge(Gravity.LEFT);
            slideTransition.setDuration(1000);

            getWindow().setExitTransition(slideTransition);
        }
    }

    private void setupAlarm() {
        //Create pending intent to trigger when alarm goes off
        Intent i = new Intent(this, BreweryIntentService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        //Set to go off once a day
        long triggerAtMillis = Calendar.getInstance().getTimeInMillis() + 1000;
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
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
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        year = savedInstanceState.getInt(CURRENT_YEAR);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(CURRENT_YEAR, year);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

        } else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showTasks(List<Datum> datums) {
        adapter.replaceData(datums);

    }

    @Override
    public void showTaskDetailsUi(Datum brewery) {
        Intent intent = new Intent(this, BreweryDetailActivity.class);
        intent.putExtra(BreweryDetailActivity.EXTRA_BREWERY_ID, brewery);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
            this.startActivity(intent, bundle);
        } else {
            this.startActivity(intent);
        }
    }

    @Override
    public void showLoadingTasksError() {
        showMessage(getString(R.string.loading_breweries_error));

    }

    private void showMessage(String message) {

        Snackbar.make(findViewById(R.id.brewery_rv), message, Snackbar.LENGTH_LONG).show();
    }

    /*
    Sets up a counter
     */
    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
