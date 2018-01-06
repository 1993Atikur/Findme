package com.example.atikurzamanpallob.findme;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

/**
 * Created by Atikur Zaman Pallob on 08-May-17.
 */

public class MenuActivity extends Activity{
    UserDataBase userDataBase;
    Database1 database1;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.menuwindow );
        database1=new Database1 (this);
        userDataBase=new UserDataBase ( this );
        Button STOP=(Button)findViewById ( R.id.Stop );
        final Button Minimize=(Button)findViewById ( R.id.Minimize );
        Button EditProfile=(Button)findViewById ( R.id.editprofile);
        Button TimeInterval=(Button)findViewById(R.id.timeinterval);

        STOP.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                database1.delete ();
                boolean var=database1.insertvalue ("FALSE");
                finish ();
            }
        } );

        Minimize.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                finish ();
            }
        } );

        EditProfile.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent ( MenuActivity.this,Editprofile.class ) );
                finish ();

            }
        } );

        TimeInterval.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
               startActivity ( new Intent ( MenuActivity.this,TimeSeter.class ) );
                finish();
            }
        } );
    }
}
