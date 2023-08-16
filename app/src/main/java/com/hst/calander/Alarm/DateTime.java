package com.hst.calander.Alarm;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateTime {
    private boolean m24hClock;
    private Context mContext;
    private SimpleDateFormat mDateFormat;
    private String[] mFullDayNames;
    private String[] mShortDayNames;
    private SimpleDateFormat mTimeFormat;
    private boolean mWeekStartsOnMonday;

    public DateTime(Context context) {
        this.mContext = context;
        update();
    }

    public void update() {
        GregorianCalendar gregorianCalendar;
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        this.mWeekStartsOnMonday = defaultSharedPreferences.getBoolean("week_starts_pref", false);
        this.m24hClock = defaultSharedPreferences.getBoolean("use_24h_pref", false);
        this.mDateFormat = new SimpleDateFormat("E MMM d, yyyy");
        if (this.m24hClock) {
            this.mTimeFormat = new SimpleDateFormat("H:mm");
        } else {
            this.mTimeFormat = new SimpleDateFormat("h:mm a");
        }
        this.mFullDayNames = new String[7];
        this.mShortDayNames = new String[7];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("E");
        if (this.mWeekStartsOnMonday) {
            gregorianCalendar = new GregorianCalendar(2012, 7, 6);
        } else {
            gregorianCalendar = new GregorianCalendar(2012, 7, 5);
        }
        for (int i = 0; i < 7; i++) {
            this.mFullDayNames[i] = simpleDateFormat.format(gregorianCalendar.getTime());
            this.mShortDayNames[i] = simpleDateFormat2.format(gregorianCalendar.getTime());
            gregorianCalendar.add(7, 1);
        }
    }

    public boolean is24hClock() {
        return this.m24hClock;
    }

    public String formatTime(Alarm alarm) {
        return this.mTimeFormat.format(new Date(alarm.getDate()));
    }

    public String formatDate(Alarm alarm) {
        return this.mDateFormat.format(new Date(alarm.getDate()));
    }

    public String formatDays(Alarm alarm) {
        boolean[] days = getDays(alarm);
        if (alarm.getDays() == 0) {
            return "Never";
        }
        if (alarm.getDays() == 127) {
            return "Every day";
        }
        String str = "";
        for (int i = 0; i < 7; i++) {
            if (days[i]) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("" == str ? this.mShortDayNames[i] : ", " + this.mShortDayNames[i]);
                str = sb.toString();
            }
        }
        return str;
    }

    public String formatDetails(Alarm alarm) {
        String formatDays;
        if (alarm.getOccurence() == 0) {
            formatDays = formatDate(alarm);
        } else {
            formatDays = alarm.getOccurence() == 1 ? formatDays(alarm) : "???";
        }
        return formatDays + ", " + formatTime(alarm);
    }

    public boolean[] getDays(Alarm alarm) {
        int i = !this.mWeekStartsOnMonday ? 1 : 0;
        boolean[] zArr = new boolean[7];
        int days = alarm.getDays();
        for (int i2 = 0; i2 < 7; i2++) {
            zArr[(i2 + i) % 7] = ((1 << i2) & days) > 0;
        }
        return zArr;
    }

    public void setDays(Alarm alarm, boolean[] zArr) {
        int i = !this.mWeekStartsOnMonday ? 1 : 0;
        int i2 = 0;
        for (int i3 = 0; i3 < 7; i3++) {
            i2 |= zArr[(i3 + i) % 7] ? 1 << i3 : 0 << i3;
        }
        alarm.setDays(i2);
    }

    public String[] getFullDayNames() {
        return this.mFullDayNames;
    }

    public String[] getShortDayNames() {
        return this.mShortDayNames;
    }
}
