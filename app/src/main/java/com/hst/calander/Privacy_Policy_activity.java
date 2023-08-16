package com.hst.calander;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class Privacy_Policy_activity extends AppCompatActivity {
    private static final String TAG = "Main";
    Context context;
    ImageView img_permission;
    ImageView img_privacy;
    private ProgressDialog progress;
    Toolbar toolbar;

    private WebView webvw;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.privacy_policy);


//        AdAdmob adAdmob = new AdAdmob( this);
//        adAdmob.FullscreenAd_Counter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        this.toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        getSupportActionBar().setTitle("Permission");
        this.context = this;
        this.webvw = (WebView) findViewById(R.id.webview);
        this.img_permission = (ImageView) findViewById(R.id.img_permission);
        this.img_privacy = (ImageView) findViewById(R.id.img_privacy);
        this.img_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Privacy_Policy_activity.this.context, 16974065);
                dialog.requestWindowFeature(1);
                dialog.setContentView(R.layout.permission_dialog);
                ((ImageView) dialog.findViewById(R.id.img_close)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        this.img_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Privacy_Policy_activity.this.context, 16973834);
                dialog.requestWindowFeature(1);
                dialog.setContentView(R.layout.privacy_dialog);
                WebView webView = (WebView) dialog.findViewById(R.id.privacy_webview);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
                webView.loadUrl("https://google.com");
                ((ImageView) dialog.findViewById(R.id.img_cancel)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        this.webvw.getSettings().setJavaScriptEnabled(true);
        this.webvw.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        final AlertDialog create = new AlertDialog.Builder(this).create();
        this.progress = ProgressDialog.show(this, "Please Wait...", "Loading...");
        this.webvw.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                Log.i(Privacy_Policy_activity.TAG, "Processing webview url click...");
                webView.loadUrl(str);
                return true;
            }

            @Override
            public void onPageFinished(WebView webView, String str) {
                Log.i(Privacy_Policy_activity.TAG, "Finished loading URL: " + str);
                if (Privacy_Policy_activity.this.progress.isShowing()) {
                    Privacy_Policy_activity.this.progress.dismiss();
                }
            }

            @Override
            public void onReceivedError(WebView webView, int i, String str, String str2) {
                Log.e(Privacy_Policy_activity.TAG, "Error: " + str);
                create.setTitle("Error");
                create.setMessage(str);
                create.setButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i2) {
                    }
                });
                create.show();
            }
        });
        this.webvw.loadUrl("https://google.com");
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
                finish();
                return true;
            case R.id.coin:
                Intent intent5 = new Intent(getApplicationContext(), PurchaseInAppActivity.class);
                intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent5);
                return true;
//            case R.id.privacy:
//                Intent intent2 = new Intent(this, Privacy_Policy_activity.class);
//                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent2);
//                return true;
//            case R.id.rate:
//                if (isOnline()) {
//                    Intent intent3 = new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + this.context.getPackageName()));
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
//                    intent4.putExtra("android.intent.extra.TEXT", "Hi! I'm using a USA Calendar application. Check it out:http://play.google.com/store/apps/details?id=" + this.context.getPackageName());
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
