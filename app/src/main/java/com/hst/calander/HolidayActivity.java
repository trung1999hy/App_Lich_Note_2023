package com.hst.calander;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.hst.calander.Adapter.HolidayAdapter;
import com.hst.calander.Model.HolidayModel;


public class HolidayActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    int ads_const;
    String[] bankHoliday;
    ImageView btnBankHoliday;
    ImageView btnClose;
    ImageView btnFederalHoliday;
    ImageView btnFestival;
    ImageView btnInternationalHoliday;
    ImageView btnPublicHoliday;
    ImageView btnStockHoliday;
    String current_year;
    String[] federalHoliday;
    String[] festivalHoliday;
    String[] genraleHoliday;
    HolidayAdapter holidayAdapter;
    String[] internationalHolidays;
    PrefManager prefManager;
    RecyclerView rvHolidayList;
    RelativeLayout rvSpinnerView;
    ImageView selectedHoliday;
    Spinner spYear;
    SharedPreferences spref;
    String[] stokeHolidays;
    TextView tvHolidayName;
    String[] yearList;
    List<HolidayModel> holidayModelList = new ArrayList();
    int currentSelectionTab = 0;

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public Bundle getNonPersonalizedAdsBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("npa", "1");
        return bundle;
    }


    @SuppressLint("ResourceType")
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_holiday);


//        AdAdmob adAdmob = new AdAdmob( this);
//        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
//        adAdmob.FullscreenAd_Counter(this);


        SharedPreferences sharedPreferences = getSharedPreferences("pref_ads", 0);
        this.spref = sharedPreferences;
        this.ads_const = sharedPreferences.getInt("ads_const", 0);
        PrefManager prefManager = new PrefManager(this);
        this.prefManager = prefManager;
        this.btnClose = (ImageView) findViewById(R.id.btnClose);
        this.spYear = (Spinner) findViewById(R.id.spYear);
        ArrayAdapter<CharSequence> createFromResource = ArrayAdapter.createFromResource(this, R.array.yearList, 17367048);
        createFromResource.setDropDownViewResource(17_367_049);
        this.spYear.setAdapter((SpinnerAdapter) createFromResource);
        this.spYear.setOnItemSelectedListener(this);
        this.current_year = String.valueOf(Calendar.getInstance().get(1));
        this.festivalHoliday = getResources().getStringArray(R.array.FestivalHolidays);
        this.bankHoliday = getResources().getStringArray(R.array.BankHoliday);
        this.federalHoliday = getResources().getStringArray(R.array.FederalHolidays);
        this.genraleHoliday = getResources().getStringArray(R.array.Genral_Holidays);
        this.internationalHolidays = getResources().getStringArray(R.array.InternationalHolidays);
        this.stokeHolidays = getResources().getStringArray(R.array.StokeHolidays);
        this.yearList = getResources().getStringArray(R.array.yearList);
        this.rvHolidayList = (RecyclerView) findViewById(R.id.rvHolidayList);
        this.rvSpinnerView = (RelativeLayout) findViewById(R.id.rvSpinnerView);
        this.selectedHoliday = (ImageView) findViewById(R.id.selectedHoliday);
        this.tvHolidayName = (TextView) findViewById(R.id.tvHolidayName);
        ImageView imageView = (ImageView) findViewById(R.id.btnFestival);
        this.btnFestival = imageView;
        imageView.setOnClickListener(this);
        ImageView imageView2 = (ImageView) findViewById(R.id.btnBankHoliday);
        this.btnBankHoliday = imageView2;
        imageView2.setOnClickListener(this);
        ImageView imageView3 = (ImageView) findViewById(R.id.btnPublicHoliday);
        this.btnPublicHoliday = imageView3;
        imageView3.setOnClickListener(this);
        ImageView imageView4 = (ImageView) findViewById(R.id.btnInternationalHoliday);
        this.btnInternationalHoliday = imageView4;
        imageView4.setOnClickListener(this);
        ImageView imageView5 = (ImageView) findViewById(R.id.btnFederalHoliday);
        this.btnFederalHoliday = imageView5;
        imageView5.setOnClickListener(this);
        ImageView imageView6 = (ImageView) findViewById(R.id.btnStockHoliday);
        this.btnStockHoliday = imageView6;
        imageView6.setOnClickListener(this);
        int i = 0;
        Log.e("TAG", "this.yearList.length: " + this.yearList.length);
        Log.e("TAG", "current_year: " + current_year);
        while (i >= this.yearList.length) {

            Log.e("TAG", "yearList[i]: " + yearList[i]);

            if (this.yearList[i].equals(this.current_year)) {
                this.spYear.setSelection(i);
            }
            i++;
        }
        this.rvSpinnerView.setVisibility(View.GONE);
        for (String str : this.festivalHoliday) {
            String[] split = str.split("\\.");
            HolidayModel holidayModel = new HolidayModel();
            holidayModel.setHeadingDate("");
            holidayModel.setHolidayName(split[0]);
            holidayModel.setHolidayDayName(split[1]);
            this.holidayModelList.add(holidayModel);
        }
        this.rvHolidayList.setHasFixedSize(true);
        this.holidayAdapter = new HolidayAdapter(this, this.holidayModelList, true);
        this.rvHolidayList.setLayoutManager(new LinearLayoutManager(this));
        this.rvHolidayList.setAdapter(this.holidayAdapter);
        this.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HolidayActivity.this.onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public String getHeadingDate(String str) {
        try {
            return new SimpleDateFormat("MMMM yyyy").format(new SimpleDateFormat("MMM d, yyyy").parse(str));
        } catch (Exception unused) {
            return "";
        }
    }

    public String getDateAndDayName(String str) {
        try {
            return new SimpleDateFormat("d, EEEE").format(new SimpleDateFormat("MMM d, yyyy").parse(str));
        } catch (Exception unused) {
            return "";
        }
    }

    public String getSelectedYear(String str) {
        try {
            return new SimpleDateFormat("yyyy").format(new SimpleDateFormat("MMM d, yyyy").parse(str));
        } catch (Exception unused) {
            return "";
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBankHoliday:
                setSelection(2);
                return;
            case R.id.btnClose:
            case R.id.btnHoliday:
            case R.id.btnNoteAdd:
            case R.id.btnReminder:
            default:
                return;
            case R.id.btnFederalHoliday:
                setSelection(5);
                return;
            case R.id.btnFestival:
                setSelection(1);
                return;
            case R.id.btnInternationalHoliday:
                setSelection(4);
                return;
            case R.id.btnPublicHoliday:
                setSelection(3);
                return;
            case R.id.btnStockHoliday:
                setSelection(6);
                return;
        }
    }

    public void setSelection(int i) {
        if (i == 1) {
            this.btnFestival.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9A7D87D1")));
            this.btnBankHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnPublicHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnInternationalHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnFederalHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnStockHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnFestival.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
            this.btnBankHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.btnPublicHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.btnInternationalHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.btnFederalHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.btnStockHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.selectedHoliday.setImageResource(R.drawable.ic_festivals_icon);
            this.tvHolidayName.setText("Festival Holiday");
            this.currentSelectionTab = 1;

            this.holidayModelList.clear();
            for (String str : this.festivalHoliday) {
                String[] split = str.split("\\.");
                HolidayModel holidayModel = new HolidayModel();
                holidayModel.setHeadingDate("");
                holidayModel.setHolidayName(split[0]);
                holidayModel.setHolidayDayName(split[1]);
                this.holidayModelList.add(holidayModel);
            }
            this.rvHolidayList.setHasFixedSize(true);
            this.holidayAdapter = new HolidayAdapter(this, this.holidayModelList, true);
            this.rvHolidayList.setLayoutManager(new LinearLayoutManager(this));
            this.rvHolidayList.setAdapter(this.holidayAdapter);
        }
        if (i == 2) {
            try {
                this.btnFestival.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
                this.btnBankHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9A7D87D1")));
                this.btnPublicHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
                this.btnInternationalHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
                this.btnFederalHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
                this.btnStockHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
                this.btnFestival.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
                this.btnBankHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                this.btnPublicHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
                this.btnInternationalHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
                this.btnFederalHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
                this.btnStockHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
                this.selectedHoliday.setImageResource(R.drawable.ic_bank_holiday_icon);
                this.tvHolidayName.setText("Bank Holiday");
                this.currentSelectionTab = 2;

                changeData(this.currentSelectionTab, this.current_year);
            } catch (Exception e) {
                Log.e("TAG", "setSelection: ", e);
            }

        }
        if (i == 3) {
            this.btnFestival.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnBankHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnPublicHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9A7D87D1")));
            this.btnInternationalHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnFederalHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnStockHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnFestival.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.btnBankHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.btnPublicHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
            this.btnInternationalHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.btnFederalHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.btnStockHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.selectedHoliday.setImageResource(R.drawable.ic_public_holiday_icon);
            this.tvHolidayName.setText("Public Holiday");
            this.currentSelectionTab = 3;

            changeData(this.currentSelectionTab, this.current_year);
        }
        if (i == 4) {
            this.btnFestival.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnBankHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnPublicHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnInternationalHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9A7D87D1")));
            this.btnFederalHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnStockHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnFestival.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.btnBankHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.btnPublicHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.btnInternationalHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
            this.btnFederalHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.btnStockHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.selectedHoliday.setImageResource(R.drawable.ic_international_holiday_icon);
            this.tvHolidayName.setText("International Holiday");
            this.currentSelectionTab = 4;

            this.holidayModelList.clear();
            for (String str2 : this.internationalHolidays) {
                String[] split2 = str2.split("\\.");
                HolidayModel holidayModel2 = new HolidayModel();
                holidayModel2.setHeadingDate("");
                holidayModel2.setHolidayName(split2[0]);
                holidayModel2.setHolidayDayName(split2[1]);
                this.holidayModelList.add(holidayModel2);
            }
            this.rvHolidayList.setHasFixedSize(true);
            this.holidayAdapter = new HolidayAdapter(this, this.holidayModelList, true);
            this.rvHolidayList.setLayoutManager(new LinearLayoutManager(this));
            this.rvHolidayList.setAdapter(this.holidayAdapter);
        }
        if (i == 5) {
            this.btnFestival.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnBankHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnPublicHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnInternationalHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnFederalHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9A7D87D1")));
            this.btnStockHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnFestival.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.btnBankHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.btnPublicHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.btnInternationalHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.btnFederalHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
            this.btnStockHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.selectedHoliday.setImageResource(R.drawable.ic_federal_holiday_icon);
            this.tvHolidayName.setText("Federal Holiday");
            this.currentSelectionTab = 5;

            changeData(this.currentSelectionTab, this.current_year);
        }
        if (i == 6) {
            this.btnFestival.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnBankHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnPublicHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnInternationalHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnFederalHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4C61F9")));
            this.btnStockHoliday.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9A7D87D1")));
            this.btnFestival.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.btnBankHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.btnPublicHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.btnInternationalHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.btnFederalHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B7C0FD")));
            this.btnStockHoliday.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
            this.selectedHoliday.setImageResource(R.drawable.ic_stock_exchange_holidays_icon);
            this.tvHolidayName.setText("Stock Exchange Holiday");
            this.currentSelectionTab = 6;

            changeData(this.currentSelectionTab, this.current_year);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        String str = this.yearList[i];
        this.current_year = str;
        changeData(this.currentSelectionTab, str);
    }

    public void changeData(int i, String str) {

        Log.d("TAG", "changeData i : " + i + " ,str :" + str);

        Log.e("TAG", "changeData holidayModelList: " + holidayModelList.size());
        Log.e("TAG", "changeData bankHoliday: " + bankHoliday);

        this.holidayModelList.clear();
        if (i == 2) {
            for (String str2 : this.bankHoliday) {
                String[] split = str2.split("\\.");
                if (getSelectedYear(split[1]).equals(str)) {
                    HolidayModel holidayModel = new HolidayModel();
                    holidayModel.setHeadingDate(getHeadingDate(split[1]));
                    holidayModel.setHolidayName(split[0]);
                    holidayModel.setHolidayDayName(getDateAndDayName(split[1]));
                    this.holidayModelList.add(holidayModel);
                }
            }
        }
        if (i == 3) {
            for (String str3 : this.genraleHoliday) {
                String[] split2 = str3.split("\\.");
                if (getSelectedYear(split2[1]).equals(str)) {
                    HolidayModel holidayModel2 = new HolidayModel();
                    holidayModel2.setHeadingDate(getHeadingDate(split2[1]));
                    holidayModel2.setHolidayName(split2[0]);
                    holidayModel2.setHolidayDayName(getDateAndDayName(split2[1]));
                    this.holidayModelList.add(holidayModel2);
                }
            }
        }
        if (i == 5) {
            for (String str4 : this.federalHoliday) {
                String[] split3 = str4.split("\\.");
                if (getSelectedYear(split3[1]).equals(str)) {
                    HolidayModel holidayModel3 = new HolidayModel();
                    holidayModel3.setHeadingDate(getHeadingDate(split3[1]));
                    holidayModel3.setHolidayName(split3[0]);
                    holidayModel3.setHolidayDayName(getDateAndDayName(split3[1]));
                    this.holidayModelList.add(holidayModel3);
                }
            }
        }
        if (i == 6) {
            for (String str5 : this.stokeHolidays) {
                String[] split4 = str5.split("\\.");
                if (getSelectedYear(split4[1]).equals(str)) {
                    HolidayModel holidayModel4 = new HolidayModel();
                    holidayModel4.setHeadingDate(getHeadingDate(split4[1]));
                    holidayModel4.setHolidayName(split4[0]);
                    holidayModel4.setHolidayDayName(getDateAndDayName(split4[1]));
                    this.holidayModelList.add(holidayModel4);
                }
            }
        }
        this.rvHolidayList.setHasFixedSize(true);
        this.holidayAdapter = new HolidayAdapter(this, this.holidayModelList, false);
        this.rvHolidayList.setLayoutManager(new LinearLayoutManager(this));
        this.rvHolidayList.setAdapter(this.holidayAdapter);
    }
}
