package com.hst.calander;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.hst.calander.Alarm.Alarm;
import com.hst.calander.Alarm.DataSource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class App extends Application {
    private static Preference preference = null;
    private static App application = null;

    public static Preference getInstance() {
        if (preference == null) {
            preference = Preference.buildInstance(application);
        }
        preference.isOpenFirst();
        return preference;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        if (!Preference.buildInstance(this.getApplicationContext()).getDefaultListReminder()) {
            DataSource.getInstance(this.getApplicationContext());
            for (int i = 0; i < 8; i++) {
                Alarm alarm = new Alarm(this);
                alarm.setEnabled(false);
                alarm.setDate(Calendar.getInstance().getTimeInMillis());
                DataSource.add(alarm);
            }
            Preference.buildInstance(this.getApplicationContext()).setDefaultListReminder(true);
        }
    }

    public static final String[] REQUIRED_PERMISSIONS = getRequiredPermissions().toArray(new String[0]);

    public static boolean hasPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static List<String> getRequiredPermissions() {
        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.READ_PHONE_STATE);
        permissions.add(Manifest.permission.CAMERA);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.READ_MEDIA_IMAGES);
            permissions.add(Manifest.permission.READ_MEDIA_VIDEO);
            permissions.add(Manifest.permission.POST_NOTIFICATIONS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        }
        return permissions;
    }
}