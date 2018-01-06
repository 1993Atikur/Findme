package com.example.atikurzamanpallob.findme;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
        Database1 database1;
        UserDataBase userDataBase;
        Database2 database2;

    String UserName,Minute,Seconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        userDataBase=new UserDataBase ( this );
        database2=new Database2 ( this );
        Cursor cursor=userDataBase.getalldata ();
        while (cursor.moveToNext ()){
            UserName=cursor.getString (0);
        }
    if(UserName!=null){
        database1=new Database1 ( this );
        final Intent gpsservice=new Intent ( this,GPS_Service.class);
            startService (gpsservice);
            database1.delete ();
            database1.insertvalue ("TRUE");
            startActivity ( new Intent ( MainActivity.this,LoginActivity.class ) );

            finish ();
        }
    else{

        startActivity ( new Intent ( MainActivity.this,Profile.class ) );
        finish ();
    }



    }
}
