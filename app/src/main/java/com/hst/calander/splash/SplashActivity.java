package com.hst.calander.splash;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hst.calander.R;

import com.hst.calander.App;
import com.hst.calander.MainActivity;


public class SplashActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.splashactivity);
        getWindow().getDecorView().setSystemUiVisibility(8192);
        if (isOnline()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    OpenNext1();
                }
            }, 5000);

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Network error…").setMessage("Internet is not available, reconnect network and try again.").setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public final void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    finishAffinity();

                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.MULTIPLY);
    }

    public boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private SharedPreferences sharedPreferences;

    public void OpenNext1() {
        if (App.hasPermissions(this, App.REQUIRED_PERMISSIONS)) {
            sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            if (isFirstTime()) {
                Toast.makeText(this, "Welcome to the app!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, PermissionGrant_Activity.class).putExtra("isFromSplash", "true"));
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isFirstTime", false);
                editor.apply();
                finish();
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        } else {
            startActivity(new Intent(this, PermissionGrant_Activity.class));
            finish();
        }
    }

    private boolean isFirstTime() {
        return sharedPreferences.getBoolean("isFirstTime", true);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ExitApp();
    }

    public void ExitApp() {
        moveTaskToBack(true);
        finish();
        Process.killProcess(Process.myPid());
        System.exit(0);
    }
}
