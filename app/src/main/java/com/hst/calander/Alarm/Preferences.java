package com.hst.calander.Alarm;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.hst.calander.R;


public class Preferences extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.preferences);


//        AdAdmob adAdmob = new AdAdmob( this);
//        adAdmob.FullscreenAd_Counter(this);
    }
}
