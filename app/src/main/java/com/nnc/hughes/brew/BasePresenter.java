package com.nnc.hughes.brew;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by marcus on 10/10/17.
 */

public interface BasePresenter<T> {
    void takeView(T view, CompositeDisposable compositeDisposable);

    void dropView();
}
