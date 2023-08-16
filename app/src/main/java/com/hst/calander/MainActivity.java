package com.hst.calander;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.hst.calander.Adapter.NoteAdapter;
import com.hst.calander.DatabaseHelper.DBHelper;
import com.hst.calander.Model.EventModel;
import com.hst.calander.Model.NoteModel;


public class MainActivity extends AppCompatActivity {
    public static Date selectedDate;
    int ad_code;
    int ads_const;
    LinearLayout btnHoliday;
    FloatingActionButton btnNoteAdd;
    LinearLayout btnReminder;
    DBHelper dbHelper;
    String[] fest_Array;
    TextView monthText;
    LinearLayout noEvent;
    NoteAdapter noteAdapter;
    PrefManager prefManager;
    RecyclerView rvNote;
    ImageView showNextMonthBut;
    ImageView showPreviousMonthBut;
    SharedPreferences spref;
    List<NoteModel> noteModelList = new ArrayList();
    List<EventModel> eventModelList = new ArrayList();

    TextView point;

    public Bundle getNonPersonalizedAdsBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("npa", "1");
        return bundle;
    }


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);


//        AdAdmob adAdmob = new AdAdmob( this);
//        adAdmob.FullscreenAd_Counter(this);


        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        SharedPreferences sharedPreferences = getSharedPreferences("pref_ads", 0);
        this.spref = sharedPreferences;
        this.ads_const = sharedPreferences.getInt("ads_const", 0);
        PrefManager prefManager = new PrefManager(this);
        this.prefManager = prefManager;
        if (MainActivity.this.ad_code == 1) {
            Intent intent = new Intent(MainActivity.this.getApplicationContext(), AddNoteActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MainActivity.this.startActivity(intent);
        }
        if (MainActivity.this.ad_code == 2) {
            Intent intent2 = new Intent(MainActivity.this.getApplicationContext(), HolidayActivity.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MainActivity.this.startActivity(intent2);
        }
        if (MainActivity.this.ad_code == 3) {
            Intent intent3 = new Intent(MainActivity.this.getApplicationContext(), ReminderActivity.class);
            intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MainActivity.this.startActivity(intent3);
        }
        if (MainActivity.this.ad_code == 4) {
            Intent intent4 = new Intent(MainActivity.this.getApplicationContext(), CalenderActivity.class);
            intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MainActivity.this.startActivity(intent4);
        }

        DBHelper dBHelper = new DBHelper(this);
        this.dbHelper = dBHelper;
        this.noteModelList = dBHelper.getAllNote();
        this.fest_Array = getResources().getStringArray(R.array.Genral_Holidays);
        this.btnHoliday = (LinearLayout) findViewById(R.id.btnHoliday);
        this.btnReminder = (LinearLayout) findViewById(R.id.btnReminder);
        int i = 0;
        while (true) {
            String[] strArr = this.fest_Array;
            if (i >= strArr.length) {
                break;
            }
            String[] split = strArr[i].split("\\.");
            EventModel eventModel = new EventModel();
            eventModel.setTitle(split[0]);
            eventModel.setDate(split[1]);
            this.eventModelList.add(eventModel);
            i++;
        }
        this.noEvent = (LinearLayout) findViewById(R.id.noEvent);
        this.btnNoteAdd = (FloatingActionButton) findViewById(R.id.btnNoteAdd);
        this.rvNote = (RecyclerView) findViewById(R.id.rvNote);
        this.point = (TextView) findViewById(R.id.pointWallet);
        this.monthText = (TextView) findViewById(R.id.monthText);
        this.showPreviousMonthBut = (ImageView) findViewById(R.id.showPreviousMonthBut);
        this.showNextMonthBut = (ImageView) findViewById(R.id.showNextMonthBut);
        final CompactCalendarView compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setFirstDayOfWeek(1);
        Date time = Calendar.getInstance().getTime();
        this.monthText.setText(((String) DateFormat.format("MMMM", time)) + " " + ((String) DateFormat.format("yyyy", time)));
        this.rvNote.setHasFixedSize(true);
        this.noteAdapter = new NoteAdapter(this, this.noteModelList);
        this.rvNote.setLayoutManager(new LinearLayoutManager(this));
        this.rvNote.setAdapter(this.noteAdapter);
        for (int i2 = 0; i2 < this.eventModelList.size(); i2++) {
            compactCalendarView.addEvent(new Event(Color.parseColor("#e7c730"), getEventMilli(this.eventModelList.get(i2).getDate()), this.eventModelList.get(i2).getTitle()));
        }
        for (int i3 = 0; i3 < this.noteModelList.size(); i3++) {
            if (i3 == 0) {
                compactCalendarView.addEvent(new Event(Color.parseColor("#8374EC"), getNoteMilli(this.noteModelList.get(i3).getDate()), this.noteModelList.get(i3).getDescription()));
            } else if (!this.noteModelList.get(i3).getDate().equals(this.noteModelList.get(i3 - 1).getDate())) {
                compactCalendarView.addEvent(new Event(Color.parseColor("#8374EC"), getNoteMilli(this.noteModelList.get(i3).getDate()), this.noteModelList.get(i3).getDescription()));
            }
        }
        this.btnNoteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.ad_code = 1;

                Intent intent = new Intent(MainActivity.this.getApplicationContext(), AddNoteActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });
        this.btnHoliday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.ad_code = 2;
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), HolidayActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });
        this.btnReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.ad_code = 3;
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), ReminderActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });
        this.showPreviousMonthBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compactCalendarView.scrollLeft();
            }
        });
        this.showNextMonthBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compactCalendarView.scrollRight();
            }
        });
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override

            public void onDayClick(Date date) {
                compactCalendarView.getEvents(date);
                MainActivity.selectedDate = date;
                MainActivity.this.ad_code = 4;
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), CalenderActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }

            @Override

            public void onMonthScroll(Date date) {
                Log.d("TAG", "Month was scrolled to: " + date);
                TextView textView = MainActivity.this.monthText;
                textView.setText(((String) DateFormat.format("MMMM", date)) + " " + ((String) DateFormat.format("yyyy", date)));
            }
        });
        this.point.setText(App.getInstance().getValueCoin() + "");
    }

    public long getEventMilli(String str) {
        try {
            return new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH).parse(str).getTime();
        } catch (Exception unused) {
            return 0L;
        }
    }

    public long getNoteMilli(String str) {
        try {
            return new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(str).getTime();
        } catch (Exception unused) {
            return 0L;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        this.noteModelList.clear();
        this.noteModelList = this.dbHelper.getAllNote();
        this.rvNote.setHasFixedSize(true);
        this.noteAdapter = new NoteAdapter(this, this.noteModelList);
        this.rvNote.setLayoutManager(new LinearLayoutManager(this));
        this.rvNote.setAdapter(this.noteAdapter);
        if (this.noteModelList.size() == 0) {
            this.noEvent.setVisibility(View.VISIBLE);
            this.rvNote.setVisibility(View.GONE);
            return;
        }
        this.noEvent.setVisibility(View.GONE);
        this.rvNote.setVisibility(View.VISIBLE);
        this.point.setText(String.format("%d", App.getInstance().getValueCoin()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;
            case R.id.coin:
                Intent intent5 = new Intent(getApplicationContext(), PurchaseInAppActivity.class);
                intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent5);
                return true;
//            case R.id.privacy:
//                Intent intent5 = new Intent(getApplicationContext(), Privacy_Policy_activity.class);
//                intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent5);
//                return true;
//            case R.id.rate:
//                if (isOnline()) {
//                    Intent intent3 = new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName()));
//                    intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent3);
//                } else {
//                    Toast makeText = Toast.makeText(getApplicationContext(), "No Internet Connection..", Toast.LENGTH_SHORT);
//                    makeText.setGravity(17, 0, 0);
//                    makeText.show();
//                }
//                return true;
//            case R.id.share:
//                if (isOnline()) {
//                    Intent intent4 = new Intent("android.intent.action.SEND");
//                    intent4.setType("text/plain");
//                    intent4.putExtra("android.intent.extra.TEXT", "http://play.google.com/store/apps/details?id=" + getPackageName());
//                    intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(Intent.createChooser(intent4, "Share with Friends"));
//                } else {
//                    Toast makeText2 = Toast.makeText(getApplicationContext(), "No Internet Connection..", Toast.LENGTH_SHORT);
//                    makeText2.setGravity(17, 0, 0);
//                    makeText2.show();
//                }
//                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
