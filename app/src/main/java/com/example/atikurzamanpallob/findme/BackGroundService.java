package com.example.atikurzamanpallob.findme;

import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

/**
 * Created by Atikur Zaman Pallob on 10-Feb-17.
 */

public class BackGroundService extends Service {
    Database1 database;
    UserDataBase userDataBase;
    Cursor cursor;
    GPSTracker Myobject;
    WindowManager windowManager;
    RelativeLayout relativeLayout;
    Button B1,B2;
    boolean variable=false;
    String Holder;
    TextView MytextView;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        database=new Database1 ( this );
        Cursor cursor=database.getall ();
        while (cursor.moveToNext ()){
            Holder=cursor.getString ( 0 );
        }
        if(Holder.equals ("TRUE")){
            Myobject= new GPSTracker(this);
            final LocationManager manager = (LocationManager)getSystemService( Context.LOCATION_SERVICE );

            double latitude = Myobject.getLatitude();
            double longitude = Myobject.getLongitude();
            getCompleteAddressString(latitude,longitude);

        }else{
            stopSelf ();
        }


           // Toast.makeText (getApplicationContext (),"Turn On GPS",Toast.LENGTH_SHORT).show();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        database=new Database1 ( this );
        String Number = "";
        Cursor cursor=database.getall ();
        while (cursor.moveToNext ()){
            Holder=cursor.getString ( 0 );
        }
        if(Holder.equals ("TRUE")){
            
            double latitude = Myobject.getLatitude();
            double longitude = Myobject.getLongitude();
            String Messege =  getCompleteAddressString(latitude,longitude);
            userDataBase=new UserDataBase ( this );
            cursor=userDataBase.getalldata ();
                while (cursor.moveToNext ()){
                    Number=cursor.getString (3);
                    
                    
                }
            
         //  Toast.makeText(getApplicationContext(),Messege +" sent to "+Number,Toast.LENGTH_SHORT).show();
              SmsManager smsManager=SmsManager.getDefault ();
               smsManager.sendTextMessage (Number,null,"My Location: "+Messege,null,null );

        }else{
            stopSelf ();
        }


        return super.onStartCommand ( intent, flags, startId );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder ReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    ReturnedAddress.append(returnedAddress.getAddressLine(i)).append(",");
                }
                strAdd = ReturnedAddress.toString();

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strAdd;
    }


}
