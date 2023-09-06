package com.hst.calander;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hst.calander.Alarm.Alarm;
import com.hst.calander.Alarm.AlarmListAdapter;
import com.hst.calander.Alarm.EditAlarm;
import com.hst.calander.Alarm.Preferences;
import com.hst.calander.Alarm.onClick;


public class ReminderActivity extends AppCompatActivity {
    int ads_const;
    ImageView btnClose;

    private ListView mAlarmList;
    private AlarmListAdapter mAlarmListAdapter;
    private Alarm mCurrentAlarm;
    PrefManager prefManager;
    SharedPreferences spref;
    TextView point;
    LinearLayout viewPoint;

    public Bundle getNonPersonalizedAdsBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("npa", "1");
        return bundle;
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_reminder);
        SharedPreferences sharedPreferences = getSharedPreferences("pref_ads", 0);
        this.spref = sharedPreferences;
        this.ads_const = sharedPreferences.getInt("ads_const", 0);
        PrefManager prefManager = new PrefManager(this);
        this.prefManager = prefManager;
        this.point = (TextView) findViewById(R.id.pointWallet);
        this.btnClose = (ImageView) findViewById(R.id.btnClose);
        this.viewPoint = (LinearLayout) findViewById(R.id.pointView);
        this.mAlarmList = (ListView) findViewById(R.id.alarm_list);
        AlarmListAdapter alarmListAdapter = new AlarmListAdapter(this);
        alarmListAdapter.setOnClickSetUp(new onClick() {
            @Override
            public void onClickSetUp(int i, Alarm alarm) {
                Intent intent = new Intent(ReminderActivity.this.getBaseContext(), EditAlarm.class);
                ReminderActivity reminderActivity = ReminderActivity.this;
                reminderActivity.mCurrentAlarm = reminderActivity.mAlarmListAdapter.getItem(i);
                ReminderActivity.this.mCurrentAlarm.toIntent(intent);
                ReminderActivity.this.startActivityForResult(intent, 1);
            }
        });
        this.mAlarmListAdapter = alarmListAdapter;
        this.mAlarmList.setAdapter((ListAdapter) alarmListAdapter);
        registerForContextMenu(this.mAlarmList);
        this.mCurrentAlarm = null;
        this.viewPoint.setOnClickListener(v -> {
            ReminderActivity.this.startActivity(new Intent(ReminderActivity.this, PurchaseInAppActivity.class));
        });
        this.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReminderActivity.this.onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.point.setText(String.format("%d", App.getInstance().getValueCoin()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("AlarmMe", "AlarmMe.onDestroy()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("AlarmMe", "AlarmMe.onResume()");
        this.mAlarmListAdapter.updateAlarms();
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 0) {
            if (i2 == -1) {
                this.mCurrentAlarm.fromIntent(intent);
                this.mAlarmListAdapter.add(this.mCurrentAlarm);
            }
            this.mCurrentAlarm = null;
        } else if (i != 1) {
            if (i == 2) {
                this.mAlarmListAdapter.onSettingsUpdated();
            }
        } else {
            if (i2 == -1) {
                this.mCurrentAlarm.fromIntent(intent);
                this.mAlarmListAdapter.update(this.mCurrentAlarm);
            }
            this.mCurrentAlarm = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (R.id.menu_settings == menuItem.getItemId()) {
            startActivityForResult(new Intent(getBaseContext(), Preferences.class), 2);
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        if (view.getId() == R.id.alarm_list) {
            contextMenu.setHeaderTitle(this.mAlarmListAdapter.getItem(((AdapterView.AdapterContextMenuInfo) contextMenuInfo).position).getTitle());
            contextMenu.add(0, 0, 0, "Edit");
            contextMenu.add(0, 1, 0, "Delete");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();
        int itemId = menuItem.getItemId();
        if (itemId == 0) {
            Intent intent = new Intent(getBaseContext(), EditAlarm.class);
            Alarm item = this.mAlarmListAdapter.getItem(adapterContextMenuInfo.position);
            this.mCurrentAlarm = item;
            item.toIntent(intent);
            startActivityForResult(intent, 1);
        } else if (itemId == 1) {
            this.mAlarmListAdapter.delete(adapterContextMenuInfo.position);
        } else if (itemId == 2) {
            Alarm item2 = this.mAlarmListAdapter.getItem(adapterContextMenuInfo.position);
            Alarm alarm = new Alarm(this);
            Intent intent2 = new Intent();
            item2.toIntent(intent2);
            alarm.fromIntent(intent2);
            alarm.setTitle(item2.getTitle() + " (copy)");
            this.mAlarmListAdapter.add(alarm);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
