package com.hst.calander;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hst.calander.R;


public class First_TaptoStart_Activity extends AppCompatActivity {
    public static final String ACTION_CLOSE = "ACTION_CLOSE";
    private static String INSTALL_PREF = "install_pref";
    public static final String TAG = "Start_Activity";
    ImageView btnClose;
    private Context context;
    private FirstReceiver firstReceiver;
   LinearLayout lin_start;
    PrefManager prefManager;
    String result = "";
    TextView txt_privacyPolicy;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.first_taptostart_activity);

//        AdAdmob adAdmob = new AdAdmob( this);
//        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
//        adAdmob.FullscreenAd_Counter(this);



        this.context = this;
        this.prefManager = new PrefManager(this);

        this.txt_privacyPolicy = (TextView) findViewById(R.id.txt_privacy);
        this.txt_privacyPolicy.setText(Html.fromHtml("<a href='https://google.com'>Privacy Policy</a>"));
        this.txt_privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(First_TaptoStart_Activity.this.getApplicationContext(), Privacy_Policy_activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                First_TaptoStart_Activity.this.startActivity(intent);
            }
        });
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lin_start);
        this.lin_start = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(First_TaptoStart_Activity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                First_TaptoStart_Activity.this.startActivity(intent);
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.btnClose);
        this.btnClose = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(First_TaptoStart_Activity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                First_TaptoStart_Activity.this.startActivity(intent);
            }
        });

        IntentFilter intentFilter = new IntentFilter(ACTION_CLOSE);
        FirstReceiver firstReceiver = new FirstReceiver();
        this.firstReceiver = firstReceiver;
        registerReceiver(firstReceiver, intentFilter);
        if (checkNewInstall()) {
            return;
        }
        new UpdateDownloadCounter().execute(new Void[0]);
    }

    private boolean checkNewInstall() {
        Context context = this.context;
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        boolean z = sharedPreferences.getBoolean(INSTALL_PREF, false);
        if (!z) {
            sharedPreferences.edit().putBoolean(INSTALL_PREF, true).commit();
        }
        return z;
    }


    private class UpdateDownloadCounter extends AsyncTask<Void, Void, Void> {
        private UpdateDownloadCounter() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        public Void doInBackground(Void... voidArr) {
            return null;
        }


        @Override
        public void onPostExecute(Void r1) {
            super.onPostExecute(r1);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this.context, ExitActivity.class));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.firstReceiver);
    }


    class FirstReceiver extends BroadcastReceiver {
        FirstReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("FirstReceiver", "FirstReceiver");
            if (intent.getAction().equals(First_TaptoStart_Activity.ACTION_CLOSE)) {
                First_TaptoStart_Activity.this.finish();
            }
        }
    }
}
