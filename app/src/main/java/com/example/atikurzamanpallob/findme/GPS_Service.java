package com.example.atikurzamanpallob.findme;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Atikur Zaman Pallob on 23-Feb-17.
 */

public class GPS_Service extends Service {
    GPSTracker Myobject;
    WindowManager windowManager;
    RelativeLayout relativeLayout;
    Button B1,B2;
    Database1 database1;
    Database2 database2;
    Database3 database3;

    Cursor cursor,cursor2;
    String variable;
    String TM1="1",TS2="30",newstate;
    Long TotalTime;


    TextView MytextView;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate ();
        database1=new Database1 ( this );
        database2=new Database2 ( this );
        database3=new Database3 ( this );
        cursor2=database2.getalldatatime ();
            while(cursor2.moveToNext ()){
                TM1=cursor2.getString ( 0 );
                TS2=cursor2.getString ( 1 );
            }

       cursor=database1.getall ();
        while (cursor.moveToNext ()){
            variable=cursor.getString ( 0 );

        }
        TotalTime=(Long.parseLong ( TM1 )*60000)+(Long.parseLong ( TS2)*1000);

        if(variable.equals ("TRUE")){
            database3.insertvalueone ( "OFF" );

            final Handler handler=new Handler ();
            handler.postDelayed ( new Runnable () {
                @Override
                public void run() {

                    handler.postDelayed ( this, TotalTime );
                    final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
                    if(manager.isProviderEnabled( LocationManager.GPS_PROVIDER )){
                        Intent myservice=new Intent ( GPS_Service.this,BackGroundService.class );

                        startService (myservice);


                    }else{

                        cursor=database1.getall ();
                        while(cursor.moveToNext ()){
                            variable=cursor.getString ( 0 );
                        }
                        if(variable.equals ( "FALSE" )){
                            //Toast.makeText ( getApplicationContext (), variable, Toast.LENGTH_SHORT ).show ();
                            handler.removeCallbacksAndMessages (null);
                            stopSelf ();
                        }

                        else{


                            Cursor cus=database3.getallzero ();
                            while(cus.moveToNext ()) {
                                newstate = cus.getString ( 0 );
                            }
                            if (newstate.equals ("ON")){

                            }else{
                                view ();
                            }

                        }


                    }
                }
            }, TotalTime );
        }else{

            stopSelf ();
        }

    }
/*

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final Handler handler=new Handler ();
        handler.postDelayed ( new Runnable () {
            @Override
            public void run() {

                handler.postDelayed ( this,10000 );
                final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
                if(manager.isProviderEnabled( LocationManager.GPS_PROVIDER )){
                    Intent myservice=new Intent ( GPS_Service.this,BackGroundService.class );

                    startService (myservice);

                }else{

                    if(variable.equals ( "TRUE" )){
                        view ();
                    }else{

                    }
                }
            }
        }, 10000 );


        return super.onStartCommand ( intent, flags, startId );
    }
*/

    public void view(){

        String STATE;
        database3=new Database3 ( this );
        database3.deleteone ();
        database3.insertvalueone ("ON");

        windowManager=(WindowManager)getSystemService (WINDOW_SERVICE);
        relativeLayout=new RelativeLayout (this);
        MytextView=new TextView ( this );
        final RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        relativeLayout.setBackgroundColor ( Color.GRAY);
        relativeLayout.setLayoutParams(layoutParams);
        relativeLayout.setPadding ( 15,15,15,15);

        RelativeLayout.LayoutParams textviewsize=new RelativeLayout.LayoutParams ( RelativeLayout.LayoutParams.MATCH_PARENT,70);
        textviewsize.addRule (RelativeLayout.ALIGN_PARENT_TOP);
        textviewsize.addRule (RelativeLayout.ABOVE);
        MytextView.setLayoutParams ( textviewsize );
        MytextView.setText ( "Turn GPS ON" );
        MytextView.setTextColor (Color.WHITE);
        MytextView.setTextSize ( 25 );
        MytextView.setGravity ( Gravity.CENTER );
        relativeLayout.addView ( MytextView );
        B1=new Button ( this );
        RelativeLayout.LayoutParams buttonsize= new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT
                ,RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonsize.addRule ( RelativeLayout.ALIGN_PARENT_BOTTOM );
        buttonsize.addRule (RelativeLayout.ALIGN_PARENT_LEFT);
        B1.setLayoutParams (buttonsize);
        B1.setText ( "Cancel" );
        relativeLayout.addView ( B1);
        B2=new Button ( this );
        RelativeLayout.LayoutParams buttonsize1= new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT
                ,RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonsize1.addRule ( RelativeLayout.ALIGN_PARENT_RIGHT);
        buttonsize1.addRule (RelativeLayout.ALIGN_PARENT_BOTTOM);
        B2.setLayoutParams ( buttonsize1);
        B2.setText ("Setting");
        B2.setLayoutParams (buttonsize1);
        relativeLayout.addView ( B2 );
        final WindowManager.LayoutParams params=new WindowManager.LayoutParams (400,250,WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.RGB_888);
        params.x=0;
        params.y=0;
        params.gravity= Gravity.CENTER|Gravity.CENTER;
        windowManager.addView (relativeLayout,params );

        relativeLayout.setOnTouchListener ( new View.OnTouchListener () {
            private WindowManager.LayoutParams updateparams=params;
            int x,y;
            float touchx,touchy;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction ()){
                    case MotionEvent.ACTION_DOWN:
                        x=updateparams.x;
                        y=updateparams.y;
                        touchx=event.getRawX();
                        touchy=event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        updateparams.x=(int)(x+(event.getRawX ()-touchx));
                        updateparams.y=(int)(y+(event.getRawY ()-touchy));
                        windowManager.updateViewLayout(relativeLayout,updateparams);
                        break;
                    default:
                        break;


                }
                return true;
            }
        } );

        B1.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                windowManager.removeView (relativeLayout);
                database3.deleteone ();
                database3.insertvalueone ("OFF");

            }
        } );
        B2.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GPS_Service.this,GPS_Activator.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                windowManager.removeView (relativeLayout);


            }
        } );


    }
}
