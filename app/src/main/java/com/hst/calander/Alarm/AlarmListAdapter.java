package com.hst.calander.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hst.calander.R;

public class AlarmListAdapter extends BaseAdapter {
    private final String TAG = "AlarmMe";
    private AlarmManager mAlarmManager;
    private int mColorActive;
    private int mColorOutdated;
    private Context mContext;
    private DataSource mDataSource;
    private DateTime mDateTime;
    private LayoutInflater mInflater;

    @Override
    public long getItemId(int i) {
        return i;
    }

    public AlarmListAdapter(Context context) {
        this.mContext = context;
        this.mDataSource = DataSource.getInstance(context);
        Log.i("AlarmMe", "AlarmListAdapter.create()");
        this.mInflater = LayoutInflater.from(context);
        this.mDateTime = new DateTime(context);
        this.mColorOutdated = this.mContext.getResources().getColor(R.color.alarm_title_outdated);
        this.mColorActive = this.mContext.getResources().getColor(R.color.alarm_title_active);
        this.mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        dataSetChanged();
    }

    public void save() {
        DataSource.save();
    }

    public void update(Alarm alarm) {
        DataSource.update(alarm);
        dataSetChanged();
    }

    public void updateAlarms() {
        Log.i("AlarmMe", "AlarmListAdapter.updateAlarms()");
        for (int i = 0; i < DataSource.size(); i++) {
            DataSource.update(DataSource.get(i));
        }
        dataSetChanged();
    }

    public void add(Alarm alarm) {
        DataSource.add(alarm);
        dataSetChanged();
    }

    public void delete(int i) {
        cancelAlarm(DataSource.get(i));
        DataSource.remove(i);
        dataSetChanged();
    }

    public void onSettingsUpdated() {
        this.mDateTime.update();
        dataSetChanged();
    }

    @Override
    public int getCount() {
        return DataSource.size();
    }

    @Override
    public Alarm getItem(int i) {
        return DataSource.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        Alarm alarm = DataSource.get(i);
        if (view == null) {
            view = this.mInflater.inflate(R.layout.list_item, (ViewGroup) null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.item_title);
            viewHolder.details = (TextView) view.findViewById(R.id.item_details);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        SharedPreferences.Editor edit = this.mContext.getSharedPreferences("mypfre", 0).edit();
        edit.putInt("reminder_total", DataSource.size());
        edit.commit();
        edit.apply();
        viewHolder.title.setText(alarm.getTitle());
        TextView textView = viewHolder.details;
        StringBuilder sb = new StringBuilder();
        sb.append(this.mDateTime.formatDetails(alarm));
        sb.append(alarm.getEnabled() ? "" : " [disabled]");
        textView.setText(sb.toString());
        if (alarm.getOutdated()) {
            viewHolder.title.setTextColor(this.mColorOutdated);
        } else {
            viewHolder.title.setTextColor(this.mColorActive);
        }
        return view;
    }

    private void dataSetChanged() {
        for (int i = 0; i < DataSource.size(); i++) {
            setAlarm(DataSource.get(i));
        }
        notifyDataSetChanged();
    }

    private void setAlarm(Alarm alarm) {
        PendingIntent broadcast;
        if (!alarm.getEnabled() || alarm.getOutdated()) {
            return;
        }
        Intent intent = new Intent(this.mContext, AlarmReceiver.class);
        alarm.toIntent(intent);
        broadcast = PendingIntent.getBroadcast(this.mContext, (int) alarm.getId(), intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
//        if (Build.VERSION.SDK_INT >= 31) {
//            broadcast = PendingIntent.getBroadcast(this.mContext, (int) alarm.getId(), intent, PendingIntent.FLAG_MUTABLE);
//        } else {
//            broadcast = PendingIntent.getBroadcast(this.mContext, (int) alarm.getId(), intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
//        }
        this.mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.getDate(), broadcast);
        Log.e("AlarmMe", "AlarmListAdapter.setAlarm(" + alarm.getId() + ", '" + alarm.getTitle() + "', " + alarm.getDate() + ")");
    }

    private void cancelAlarm(Alarm alarm) {
        PendingIntent broadcast;
        Intent intent = new Intent(this.mContext, AlarmReceiver.class);
        broadcast = PendingIntent.getBroadcast(this.mContext, (int) alarm.getId(), intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

//        if (Build.VERSION.SDK_INT >= 31) {
//            broadcast = PendingIntent.getBroadcast(this.mContext, (int) alarm.getId(), intent, PendingIntent.FLAG_MUTABLE);
//        } else {
//            broadcast = PendingIntent.getBroadcast(this.mContext, (int) alarm.getId(), intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
//        }
        this.mAlarmManager.cancel(broadcast);
    }


    static class ViewHolder {
        TextView details;
        TextView title;

        ViewHolder() {
        }
    }
}
