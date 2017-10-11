package com.nnc.hughes.brew.ui;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.nnc.hughes.brew.R;
import com.nnc.hughes.brew.data.models.Datum;
import com.nnc.hughes.brew.data.remote.BreweryAPI;
import com.nnc.hughes.brew.ui.detail.BreweryDetailActivity;

import java.util.Random;

import javax.inject.Inject;

import dagger.android.DaggerIntentService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class BreweryIntentService extends DaggerIntentService {
    @Inject
    BreweryAPI breweryAPI;
    Random randomGenerator = new Random();
    int year = 2000 + (int) Math.round(Math.random() * (2017 - 2000));

    public BreweryIntentService() {
        super("BreweryIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        breweryAPI.getBreweryList("d98bfcefa0e5bb66b490574d17e11230", year)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Breweries -> {
                    if (Breweries == null) {
                        return;
                    } else {
                        int index = randomGenerator.nextInt(Breweries.getData().size());
                        sendNotification(this, Breweries.getData().get(index));
                    }
                });

    }


    private void sendNotification(Context context, Datum datum) {
        Intent notificationIntent = new Intent(this, BreweryDetailActivity.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.putExtra("BREWERY", datum);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        NotificationManager nMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent intent2 = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder nBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_announce)
                        .setContentTitle("Check out this brewery")
                        .setContentText(datum.getName())
                        .setContentIntent(intent2)
                        .setAutoCancel(true);
        nMgr.notify(0, nBuilder.build());
    }
}

