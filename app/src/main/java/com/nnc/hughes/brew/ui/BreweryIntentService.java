package com.nnc.hughes.brew.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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


    private void sendNotification(Context context, Datum brewery) {
        Intent notificationIntent = new Intent(this, BreweryDetailActivity.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.putExtra("Brewery_ID", brewery);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        NotificationManager nMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String channelId = "some_channel_id";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


            CharSequence channelName = "Some Channel";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            nMgr.createNotificationChannel(notificationChannel);
        }
        PendingIntent intent2 = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder nBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_announce)
                        .setContentTitle("Check out this brewery")
                        .setContentText(brewery.getName())
                        .setContentIntent(intent2)
                        .setAutoCancel(true);
        nMgr.notify(0, nBuilder.build());
    }
}

