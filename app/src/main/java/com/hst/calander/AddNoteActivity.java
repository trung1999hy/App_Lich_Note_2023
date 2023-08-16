package com.hst.calander;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.hst.calander.R;
import com.hst.calander.DatabaseHelper.DBHelper;


public class AddNoteActivity extends AppCompatActivity {
    Button btnAddNote;
    ImageView btnClose;
    DBHelper dbHelper;
    EditText edNoteDescription;
    EditText edNoteTitle;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_add_note);


//        AdAdmob adAdmob = new AdAdmob( this);
//        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
//        adAdmob.FullscreenAd_Counter(this);



        this.dbHelper = new DBHelper(this);
        this.edNoteTitle = (EditText) findViewById(R.id.edNoteTitle);
        this.edNoteDescription = (EditText) findViewById(R.id.edNoteDescription);
        this.btnClose = (ImageView) findViewById(R.id.btnClose);
        Button button = (Button) findViewById(R.id.btnAddNote);
        this.btnAddNote = button;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AddNoteActivity.this.edNoteDescription.getText().toString().trim().equals("") && !AddNoteActivity.this.edNoteTitle.getText().toString().trim().equals("")) {
                    Calendar calendar = Calendar.getInstance();
                    Date time = calendar.getTime();
                    String format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(time);
                    String format2 = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(time);
                    DBHelper dBHelper = AddNoteActivity.this.dbHelper;
                    String trim = AddNoteActivity.this.edNoteTitle.getText().toString().trim();
                    String trim2 = AddNoteActivity.this.edNoteDescription.getText().toString().trim();
                    dBHelper.insertNote(trim, trim2, format, format2, calendar.getTimeInMillis() + "");
                    AddNoteActivity.this.edNoteTitle.setText("");
                    AddNoteActivity.this.edNoteDescription.setText("");
                    return;
                }
                Toast.makeText(AddNoteActivity.this.getApplicationContext(), "Fill all details", Toast.LENGTH_SHORT).show();
            }
        });
        this.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNoteActivity.this.onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
