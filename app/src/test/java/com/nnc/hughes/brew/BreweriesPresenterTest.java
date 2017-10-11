package com.nnc.hughes.brew;

import com.nnc.hughes.brew.data.models.Breweries;
import com.nnc.hughes.brew.data.remote.BreweryAPI;
import com.nnc.hughes.brew.ui.list.BreweriesContract;
import com.nnc.hughes.brew.ui.list.BreweriesPresenter;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Marcus on 10/11/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class BreweriesPresenterTest {
    @ClassRule
    public static RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();
    @Mock
    private Breweries breweries;
    @Mock
    private BreweryAPI breweryAPI;
    @Mock
    private BreweriesContract.View view;
    private BreweriesPresenter breweriesPresenter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Before
    public void setupTasksPresenter() {

        MockitoAnnotations.initMocks(this);

        breweriesPresenter = new BreweriesPresenter(breweryAPI);


    }

    @Test
    public void loadAllTasksFromRepositoryAndLoadIntoView() {
        when(breweryAPI.getBreweryList(anyString(), anyInt())).thenReturn(Observable.just(breweries));
        breweriesPresenter.takeView(view, compositeDisposable);
        // breweriesPresenter.loadBreweries(true);

        // Then progress indicator is shown
        verify(view).setLoadingIndicator(true);
        // Then progress indicator is hidden and all tasks are shown in UI
        verify(view).setLoadingIndicator(false);
    }

}
