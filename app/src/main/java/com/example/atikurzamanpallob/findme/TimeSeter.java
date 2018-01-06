package com.example.atikurzamanpallob.findme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

/**
 * Created by Atikur Zaman Pallob on 05-Jun-17.
 */

public class TimeSeter extends Activity {
Database2 database2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.timeinterval );
        database2=new Database2 ( this );
        Button OkButton=(Button)findViewById ( R.id.OKBUTTON);
        final NumberPicker MinutesPicker=(NumberPicker)findViewById ( R.id.numberpickerminutes );
        final NumberPicker SecondPicker=(NumberPicker)findViewById ( R.id.numberpickerseconds );
        final String[] numbers={"5","10","15","20","25","30","35","40","45","50","55"};
        MinutesPicker.setMinValue (0);
        MinutesPicker.setMaxValue (60);
        SecondPicker.setMinValue (0);
        SecondPicker.setMaxValue (10);

        MinutesPicker.setWrapSelectorWheel ( true );
        SecondPicker.setWrapSelectorWheel ( true );
        SecondPicker.setDisplayedValues ( numbers );


        OkButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                int position=SecondPicker.getValue ();
                String Minutes=String.valueOf ( MinutesPicker.getValue () );
                database2.deletealltime ();
                boolean var=database2.inserttime (Minutes,numbers[position]);
                if(var==true){
                    Toast.makeText (getApplicationContext () ,Minutes+" minutes "+numbers[position]+" seconds\nSet as interval\nRestart the Apps ",Toast.LENGTH_SHORT ).show ();
                    startActivity ( new Intent ( TimeSeter.this,MenuActivity.class ) );
                }else{
                    Toast.makeText (getApplicationContext () ,"Something Went Wrong",Toast.LENGTH_SHORT ).show ();

                }
                finish ();
            }
        } );
    }
}
