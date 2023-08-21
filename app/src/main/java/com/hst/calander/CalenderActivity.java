package com.hst.calander;

import android.annotation.SuppressLint;
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

import com.hst.calander.R;
import com.hst.calander.Adapter.CalenderViewAdapter;
import com.hst.calander.DatabaseHelper.DBHelper;
import com.hst.calander.Model.CalenderViewModel;
import com.hst.calander.Model.EventModel;
import com.hst.calander.Model.NoteModel;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CalenderActivity extends AppCompatActivity {
    int ads_const;
    CalenderViewAdapter calenderViewAdapter;
    DBHelper dbHelper;
    String[] fest_Array;
    RelativeLayout layout;
    TextView monthText;
    PrefManager prefManager;
    RecyclerView rvToday;
    ImageView showNextMonthBut;
    ImageView showPreviousMonthBut;
    TextView point;
    LinearLayout pointView;
    SharedPreferences spref;
    List<NoteModel> noteModelList = new ArrayList();
    List<EventModel> eventModelList = new ArrayList();
    List<CalenderViewModel> calenderViewAdapterList = new ArrayList();

    public Bundle getNonPersonalizedAdsBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("npa", "1");
        return bundle;
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_calender);

//        AdAdmob adAdmob = new AdAdmob( this);
//        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
//        adAdmob.FullscreenAd_Counter(this);


        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        SharedPreferences sharedPreferences = getSharedPreferences("pref_ads", 0);
        this.spref = sharedPreferences;
        this.ads_const = sharedPreferences.getInt("ads_const", 0);
        this.layout = (RelativeLayout) findViewById(R.id.adView);
        PrefManager prefManager = new PrefManager(this);
        this.prefManager = prefManager;
        DBHelper dBHelper = new DBHelper(this);
        this.dbHelper = dBHelper;
        this.noteModelList = dBHelper.getAllNote();
        this.fest_Array = getResources().getStringArray(R.array.Genral_Holidays);
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
        this.rvToday = (RecyclerView) findViewById(R.id.rvToday);
        this.monthText = (TextView) findViewById(R.id.monthText);
        this.showPreviousMonthBut = (ImageView) findViewById(R.id.showPreviousMonthBut);
        this.showNextMonthBut = (ImageView) findViewById(R.id.showNextMonthBut);
        this.point = (TextView) findViewById(R.id.pointWallet);
        this.pointView = (LinearLayout) findViewById(R.id.pointView);
        final CompactCalendarView compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setFirstDayOfWeek(1);
        compactCalendarView.setCurrentDate(MainActivity.selectedDate);
        Date time = Calendar.getInstance().getTime();
        this.monthText.setText(((String) DateFormat.format("MMMM", time)) + " " + ((String) DateFormat.format("yyyy", time)));
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
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date date) {
                List<Event> events = compactCalendarView.getEvents(date);
                Log.d("TAG", "Day was clicked: " + date + " with events " + events);
                CalenderActivity.this.calenderViewAdapterList.clear();
                CalenderActivity calenderActivity = CalenderActivity.this;
                calenderActivity.getEventSameDate(calenderActivity.eventDateFormat(date));
                CalenderActivity calenderActivity2 = CalenderActivity.this;
                calenderActivity2.getNoteSameDate(calenderActivity2.noteDateFormat(date));
                CalenderActivity.this.rvToday.setHasFixedSize(true);
                CalenderActivity calenderActivity3 = CalenderActivity.this;
                CalenderActivity calenderActivity4 = CalenderActivity.this;
                calenderActivity3.calenderViewAdapter = new CalenderViewAdapter(calenderActivity4, calenderActivity4.calenderViewAdapterList);
                CalenderActivity.this.rvToday.setLayoutManager(new LinearLayoutManager(CalenderActivity.this));
                CalenderActivity.this.rvToday.setAdapter(CalenderActivity.this.calenderViewAdapter);
            }

            @Override
            public void onMonthScroll(Date date) {
                Log.d("TAG", "Month was scrolled to: " + date);
                TextView textView = CalenderActivity.this.monthText;
                textView.setText(((String) DateFormat.format("MMMM", date)) + " " + ((String) DateFormat.format("yyyy", date)));
            }
        });
        getEventSameDate(eventDateFormat(MainActivity.selectedDate));
        getNoteSameDate(noteDateFormat(MainActivity.selectedDate));
        System.out.println(this.calenderViewAdapterList.size());
        this.rvToday.setHasFixedSize(true);
        this.calenderViewAdapter = new CalenderViewAdapter(this, this.calenderViewAdapterList);
        this.rvToday.setLayoutManager(new LinearLayoutManager(this));
        this.rvToday.setAdapter(this.calenderViewAdapter);
        this.point.setText(App.getInstance().getValueCoin() + "");
        this.pointView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(getApplicationContext(), PurchaseInAppActivity.class);
                intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent5);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.point.setText(App.getInstance().getValueCoin() + "");
    }

    public void getEventSameDate(String str) {
        for (int i = 0; i < this.eventModelList.size(); i++) {
            if (this.eventModelList.get(i).getDate().equals(str)) {
                CalenderViewModel calenderViewModel = new CalenderViewModel();
                calenderViewModel.setId(0);
                calenderViewModel.setTitle(this.eventModelList.get(i).getTitle());
                calenderViewModel.setDescription("");
                calenderViewModel.setDate(this.eventModelList.get(i).getDate());
                calenderViewModel.setTime("");
                calenderViewModel.setMilli("");
                calenderViewModel.setNote(false);
                this.calenderViewAdapterList.add(calenderViewModel);
            }
        }
    }

    public void getNoteSameDate(String str) {
        for (int i = 0; i < this.noteModelList.size(); i++) {
            if (this.noteModelList.get(i).getDate().equals(str)) {
                CalenderViewModel calenderViewModel = new CalenderViewModel();
                calenderViewModel.setId(this.noteModelList.get(i).getId());
                calenderViewModel.setTitle(this.noteModelList.get(i).getTitle());
                calenderViewModel.setDescription(this.noteModelList.get(i).getDescription());
                calenderViewModel.setDate(this.noteModelList.get(i).getDate());
                calenderViewModel.setTime(this.noteModelList.get(i).getTime());
                calenderViewModel.setMilli(this.noteModelList.get(i).getMilli());
                calenderViewModel.setNote(true);
                this.calenderViewAdapterList.add(calenderViewModel);
            }
        }
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

    public String eventDateFormat(Date date) {
        return (String) DateFormat.format("MMM d, yyyy", date);
    }

    public String noteDateFormat(Date date) {
        return (String) DateFormat.format("dd-MM-yyyy", date);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
//                Intent intent2 = new Intent(getApplicationContext(), Privacy_Policy_activity.class);
//                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent2);
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
//                    intent4.putExtra("android.intent.extra.TEXT", "Hi! I'm using a USA Calendar application. Check it out:http://play.google.com/store/apps/details?id=" + getPackageName());
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
