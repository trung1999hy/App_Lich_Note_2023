package com.hst.calander;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.hst.calander.R;


public class ExitActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout lin_no;
    LinearLayout lin_rate;
    LinearLayout lin_yes;
    private Context mContext;

    @Override
    public void onBackPressed() {
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.adview_layout_exit);


//        AdAdmob adAdmob = new AdAdmob( this);
//        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);

        this.mContext = this;
        setContentView();
    }

    private void setContentView() {
        this.lin_yes = (LinearLayout) findViewById(R.id.lin_yes);
        this.lin_no = (LinearLayout) findViewById(R.id.lin_no);
        this.lin_rate = (LinearLayout) findViewById(R.id.lin_rate);
        this.lin_yes.setOnClickListener(this);
        this.lin_no.setOnClickListener(this);
        this.lin_rate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.lin_no) {
            finish();
        } else if (id != R.id.lin_rate) {
            if (id != R.id.lin_yes) {
                return;
            }

            finish();
        } else if (isOnline()) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + this.mContext.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast makeText = Toast.makeText(getApplicationContext(), "No Internet Connection..", Toast.LENGTH_SHORT);
            makeText.setGravity(17, 0, 0);
            makeText.show();
        }
    }

    public boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
