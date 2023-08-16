package com.hst.calander.Alarm;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import com.hst.calander.R;


public class EditAlarm extends AppCompatActivity {
    static final int DATE_DIALOG_ID = 0;
    static final int DAYS_DIALOG_ID = 2;
    static final int TIME_DIALOG_ID = 1;
    LinearLayout btnCancel;
    ImageView btnClose;
    LinearLayout btnDone;
    private Context context;
    Typeface font1;
    Typeface font2;
    Typeface font3;
    private Alarm mAlarm;
    private CheckBox mAlarmEnabled;
    private GregorianCalendar mCalendar;
    private TextView mDateButton;
    private DateTime mDateTime;
    private int mDay;
    private int mHour;
    private int mMinute;
    private int mMonth;
    private Spinner mOccurence;
    private TextView mTimeButton;
    private EditText mTitle;
    private int mYear;
    Toolbar toolbar;
    TextView txt_can;
    TextView txt_save;
    private AppCompatTextView txt_toolbar_title;
    private final int PREFERENCES_ACTIVITY = 2;
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
            EditAlarm.this.mYear = i;
            EditAlarm.this.mMonth = i2;
            EditAlarm.this.mDay = i3;
            EditAlarm.this.mCalendar = new GregorianCalendar(EditAlarm.this.mYear, EditAlarm.this.mMonth, EditAlarm.this.mDay, EditAlarm.this.mHour, EditAlarm.this.mMinute);
            EditAlarm.this.mAlarm.setDate(EditAlarm.this.mCalendar.getTimeInMillis());
            EditAlarm.this.updateButtons();
        }
    };
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i2) {
            EditAlarm.this.mHour = i;
            EditAlarm.this.mMinute = i2;
            EditAlarm.this.mCalendar = new GregorianCalendar(EditAlarm.this.mYear, EditAlarm.this.mMonth, EditAlarm.this.mDay, EditAlarm.this.mHour, EditAlarm.this.mMinute);
            EditAlarm.this.mAlarm.setDate(EditAlarm.this.mCalendar.getTimeInMillis());
            EditAlarm.this.updateButtons();
        }
    };
    private TextWatcher mTitleChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            EditAlarm.this.mAlarm.setTitle(EditAlarm.this.mTitle.getText().toString());
        }
    };
    private AdapterView.OnItemSelectedListener mOccurenceSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
            EditAlarm.this.mAlarm.setOccurence(i);
            EditAlarm.this.updateButtons();
        }
    };
    private CompoundButton.OnCheckedChangeListener mAlarmEnabledChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            EditAlarm.this.mAlarm.setEnabled(z);
        }
    };

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setSoftInputMode(32);
        setContentView(R.layout.edit2);



//        AdAdmob adAdmob = new AdAdmob( this);
//        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
//        adAdmob.FullscreenAd_Counter(this);





        this.btnClose = (ImageView) findViewById(R.id.btnClose);
        this.font1 = Typeface.createFromAsset(getAssets(), "Roboto-Medium.ttf");
        this.font2 = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
        this.font3 = Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf");
        this.btnDone = (LinearLayout) findViewById(R.id.done);
        this.btnCancel = (LinearLayout) findViewById(R.id.cancel);
        this.txt_can = (TextView) findViewById(R.id.txt_can);
        this.txt_save = (TextView) findViewById(R.id.txt_save);
        this.txt_can.setTypeface(this.font3);
        this.txt_save.setTypeface(this.font3);
        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditAlarm.this.setResult(0, null);
                EditAlarm.this.finish();
            }
        });
        this.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                EditAlarm.this.mAlarm.toIntent(intent);
                EditAlarm.this.setResult(-1, intent);
                EditAlarm.this.finish();
            }
        });
        EditText editText = (EditText) findViewById(R.id.title);
        this.mTitle = editText;
        editText.setTypeface(this.font2);
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(this.mTitle, 1);
        this.mAlarmEnabled = (CheckBox) findViewById(R.id.alarm_checkbox);
        this.mOccurence = (Spinner) findViewById(R.id.repeat_spinner);
        this.mDateButton = (TextView) findViewById(R.id.date_button);
        this.mTimeButton = (TextView) findViewById(R.id.time_button);
        this.mDateButton.setTypeface(this.font2);
        this.mTimeButton.setTypeface(this.font2);
        this.mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InputMethodManager) EditAlarm.this.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(EditAlarm.this.mTitle, 1);
            }
        });
        Alarm alarm = new Alarm(this);
        this.mAlarm = alarm;
        alarm.fromIntent(getIntent());
        this.mDateTime = new DateTime(this);
        this.mTitle.setText(this.mAlarm.getTitle());
        this.mTitle.addTextChangedListener(this.mTitleChangedListener);
        this.mOccurence.setSelection(this.mAlarm.getOccurence());
        this.mOccurence.setOnItemSelectedListener(this.mOccurenceSelectedListener);
        this.mAlarmEnabled.setChecked(this.mAlarm.getEnabled());
        this.mAlarmEnabled.setOnCheckedChangeListener(this.mAlarmEnabledChangeListener);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        this.mCalendar = gregorianCalendar;
        gregorianCalendar.setTimeInMillis(this.mAlarm.getDate());
        this.mYear = this.mCalendar.get(1);
        this.mMonth = this.mCalendar.get(2);
        this.mDay = this.mCalendar.get(5);
        this.mHour = this.mCalendar.get(11);
        this.mMinute = this.mCalendar.get(12);
        updateButtons();
        this.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditAlarm.this.onBackPressed();
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int i) {
        if (i == 0) {
            return new DatePickerDialog(this, this.mDateSetListener, this.mYear, this.mMonth, this.mDay);
        }
        if (1 == i) {
            return new TimePickerDialog(this, this.mTimeSetListener, this.mHour, this.mMinute, this.mDateTime.is24hClock());
        }
        if (2 == i) {
            return DaysPickerDialog();
        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int i, Dialog dialog) {
        if (i == 0) {
            ((DatePickerDialog) dialog).updateDate(this.mYear, this.mMonth, this.mDay);
        } else if (1 == i) {
            ((TimePickerDialog) dialog).updateTime(this.mHour, this.mMinute);
        }
    }

    public void onDateClick(View view) {
        if (this.mAlarm.getOccurence() == 0) {
            showDialog(0);
        } else if (1 == this.mAlarm.getOccurence()) {
            showDialog(2);
        }
    }

    public void onTimeClick(View view) {
        showDialog(1);
    }

    public void onDoneClick(View view) {
        Intent intent = new Intent();
        this.mAlarm.toIntent(intent);
        setResult(-1, intent);
        finish();
    }

    public void onCancelClick(View view) {
        setResult(0, null);
        finish();
    }


    public void updateButtons() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MMMM");
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("d");
        SimpleDateFormat simpleDateFormat4 = new SimpleDateFormat("yyyy");
        if (this.mAlarm.getOccurence() == 0) {
            TextView textView = this.mDateButton;
            textView.setText(simpleDateFormat.format(date) + " " + simpleDateFormat2.format(date) + " " + simpleDateFormat3.format(date) + " " + simpleDateFormat4.format(date) + " ");
        } else if (1 == this.mAlarm.getOccurence()) {
            this.mDateButton.setText(this.mDateTime.formatDays(this.mAlarm));
        }
        this.mTimeButton.setText(this.mDateTime.formatTime(this.mAlarm));
    }

    private Dialog DaysPickerDialog() {
        final boolean[] days = this.mDateTime.getDays(this.mAlarm);
        String[] fullDayNames = this.mDateTime.getFullDayNames();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Week days");
        builder.setMultiChoiceItems(fullDayNames, days, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean z) {
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditAlarm.this.mDateTime.setDays(EditAlarm.this.mAlarm, days);
                EditAlarm.this.updateButtons();
            }
        });
        builder.setNegativeButton("Cancel", (DialogInterface.OnClickListener) null);
        return builder.create();
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
