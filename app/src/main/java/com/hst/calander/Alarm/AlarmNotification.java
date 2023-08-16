package com.hst.calander.Alarm;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

import com.hst.calander.R;
import com.hst.calander.ReminderActivity;


public class AlarmNotification extends Activity {
    private Alarm mAlarm;
    private Uri mAlarmSound;
    private DateTime mDateTime;
    private long mPlayTime;
    private Ringtone mRingtone;
    private TextView mTextView;
    private PlayTimerTask mTimerTask;
    private boolean mVibrate;
    private Vibrator mVibrator;
    private final String TAG = "AlarmMe";
    private final long[] mVibratePattern = {0, 500, 500};
    private Timer mTimer = null;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(2621568);
        setContentView(R.layout.notification);
        this.mDateTime = new DateTime(this);
        this.mTextView = (TextView) findViewById(R.id.alarm_title_text);
        readPreferences();
        this.mRingtone = RingtoneManager.getRingtone(getApplicationContext(), this.mAlarmSound);
        if (this.mVibrate) {
            this.mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        }
        start(getIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("AlarmMe", "AlarmNotification.onDestroy()");
        stop();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("AlarmMe", "AlarmNotification.onNewIntent()");
        addNotification(this.mAlarm);
        stop();
        start(intent);
    }

    private void start(Intent intent) {
        Alarm alarm = new Alarm(this);
        this.mAlarm = alarm;
        alarm.fromIntent(intent);
        Log.i("AlarmMe", "AlarmNotification.start('" + this.mAlarm.getTitle() + "')");
        this.mTextView.setText(this.mAlarm.getTitle());
        this.mTimerTask = new PlayTimerTask();
        Timer timer = new Timer();
        this.mTimer = timer;
        timer.schedule(this.mTimerTask, this.mPlayTime);
        this.mRingtone.play();
        if (this.mVibrate) {
            this.mVibrator.vibrate(this.mVibratePattern, 0);
        }
    }

    private void stop() {
        Log.i("AlarmMe", "AlarmNotification.stop()");
        this.mTimer.cancel();
        this.mRingtone.stop();
        if (this.mVibrate) {
            this.mVibrator.cancel();
        }
    }

    public void onDismissClick(View view) {
        finish();
    }

    private void readPreferences() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.mAlarmSound = Uri.parse(defaultSharedPreferences.getString("alarm_sound_pref", "DEFAULT_RINGTONE_URI"));
        this.mVibrate = defaultSharedPreferences.getBoolean("vibrate_pref", true);
        this.mPlayTime = Integer.parseInt(defaultSharedPreferences.getString("alarm_play_time_pref", "30")) * 1000;
    }


    public void addNotification(Alarm alarm) {
        PendingIntent activity;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Log.i("AlarmMe", "AlarmNotification.addNotification(" + alarm.getId() + ", '" + alarm.getTitle() + "', '" + this.mDateTime.formatDetails(alarm) + "')");
        Intent intent = new Intent(getApplicationContext(), ReminderActivity.class);
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        if (Build.VERSION.SDK_INT >= 31) {
            activity = PendingIntent.getActivity(this, (int) alarm.getId(), intent, PendingIntent.FLAG_MUTABLE);
        } else {
            activity = PendingIntent.getActivity(this, (int) alarm.getId(), intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        }
        NotificationCompat.Builder autoCancel = new NotificationCompat.Builder(this).setContentIntent(activity).setAutoCancel(true);
        notificationManager.notify((int) alarm.getId(), autoCancel.setContentTitle("Missed alarm: " + alarm.getTitle()).setContentText(this.mDateTime.formatDetails(alarm)).build());
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    public class PlayTimerTask extends TimerTask {
        private PlayTimerTask() {
        }

        @Override
        public void run() {
            Log.i("AlarmMe", "AlarmNotification.PalyTimerTask.run()");
            AlarmNotification alarmNotification = AlarmNotification.this;
            alarmNotification.addNotification(alarmNotification.mAlarm);
            AlarmNotification.this.finish();
        }
    }
}
