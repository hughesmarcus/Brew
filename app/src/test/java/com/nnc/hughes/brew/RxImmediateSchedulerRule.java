package com.nnc.hughes.brew;

import android.support.annotation.NonNull;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by Marcus on 10/11/2017.
 */
public class RxImmediateSchedulerRule implements TestRule {
    private final Scheduler immediateScheduler = new Scheduler() {
        @NonNull
        @Override
        public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
            // Hack to prevent stack overflows in unit tests when scheduling with a delay;
            return super.scheduleDirect(run, 0, unit);
        }

        @NonNull
        @Override
        public Scheduler.Worker createWorker() {
            return new ExecutorScheduler.ExecutorWorker(Runnable::run);
        }
    };

    @NonNull
    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediateScheduler);
                RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediateScheduler);
                RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediateScheduler);
                RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediateScheduler);
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediateScheduler);

                try {
                    base.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }
}