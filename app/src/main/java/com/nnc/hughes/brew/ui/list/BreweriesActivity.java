package com.nnc.hughes.brew.ui.list;

import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
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

public class BreweriesActivity extends DaggerAppCompatActivity implements BreweriesContract.View {
    private static final String CURRENT_YEAR = "CURRENT_YEAR";
    @Inject
    BreweriesPresenter presenter;
    BreweriesAdapter adapter;
    @BindView(R.id.brewery_rv)
    RecyclerView recyclerView;
    @BindView(R.id.activity_brewery_progressBar)
    ProgressBar progressBar;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindowAnimations();
        setContentView(R.layout.activity_brew_list);

        setupAlarm();
        ButterKnife.bind(this);
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
                presenter.loadBreweries(false, Integer.parseInt(query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    public void setPresenter() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        presenter.takeView(this, compositeDisposable);
        presenter.loadBreweries(true, 2017);
    }

    private void setupWindowAnimations() {

        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.LEFT);
        slideTransition.setDuration(1000);
        getWindow().setExitTransition(slideTransition);
    }

    private void setupAlarm() {
        //Create pending intent to trigger when alarm goes off
        Intent i = new Intent(this, BreweryIntentService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        //Set an alarm to trigger the pending intent in intervals of 15 minutes
        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        //Trigger the alarm starting 1 second from now
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
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_YEAR, 2012);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
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
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        this.startActivity(intent, bundle);
    }

    @Override
    public void showLoadingTasksError() {
        showMessage(getString(R.string.loading_breweries_error));

    }

    private void showMessage(String message) {

        Snackbar.make(findViewById(R.id.brewery_rv), message, Snackbar.LENGTH_LONG).show();
    }

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
