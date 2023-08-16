package com.hst.calander.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class AlarmReceiver extends BroadcastReceiver {
    private final String TAG = "AlarmMe";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e(TAG, "onReceive: " + intent);

        Intent intent2 = new Intent(context, AlarmNotification.class);
        Alarm alarm = new Alarm(context);
        alarm.fromIntent(intent);
        alarm.toIntent(intent2);
        intent2.addFlags(805306368);
        context.startActivity(intent2);
    }
}
