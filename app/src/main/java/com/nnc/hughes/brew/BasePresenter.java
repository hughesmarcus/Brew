package com.nnc.hughes.brew;

/**
 * Created by marcus on 10/10/17.
 */

public interface BasePresenter<T> {
    void takeView(T view);

    void dropView();
}
