package com.nnc.hughes.brew.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by Marcus on 10/11/2017.
 */

public class BreweryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Create pending intent to trigger when alarm goes off
        Intent i = new Intent(context, BreweryIntentService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        //Set an alarm to trigger the pending intent in intervals of 15 minutes
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //Trigger the alarm starting 1 second from now
        long triggerAtMillis = Calendar.getInstance().getTimeInMillis() + 1000;
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
