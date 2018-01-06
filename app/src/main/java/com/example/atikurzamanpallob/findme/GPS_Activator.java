package com.example.atikurzamanpallob.findme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

/**
 * Created by Atikur Zaman Pallob on 11-Feb-17.
 */

public class GPS_Activator extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.gpsettings);
        Intent intent=new Intent ( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
        startActivity (intent);
            finish ();


    }
}
