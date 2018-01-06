package com.example.atikurzamanpallob.findme;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Atikur Zaman Pallob on 08-May-17.
 */

public class LoginActivity extends Activity {
    String Phone,Password1,DatabasePhone,DatabasePassword;
    UserDataBase dataBase;
    WindowManager windowManager;
    RelativeLayout relativeLayout;
    Button B1,B2;
    String pass,number;
    TextView MytextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.loginwindow );
        final EditText phone=(EditText)findViewById ( R.id.phone );
        final EditText Password=(EditText)findViewById ( R.id.password );
        Button Login=(Button)findViewById ( R.id.logenter );
        Button Forgot_Password=(Button)findViewById ( R.id.forgotpassword );
        dataBase=new UserDataBase ( this );

        Cursor cursor= dataBase.getalldata ();
        while (cursor.moveToNext ()){
           DatabasePhone =cursor.getString ( 1 );
            DatabasePassword=cursor.getString ( 2 );
        }


        Login.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Phone=phone.getText ().toString ();
                Password1=Password.getText ().toString ();

                if(Phone.equals ( DatabasePhone ) && Password1.equals ( DatabasePassword )){
                    startActivity ( new Intent ( LoginActivity.this,MenuActivity.class ) );
                    finish ();

                }
                else{
                    Toast.makeText ( getApplicationContext (),"Phone Number & Password \nDoesn't Match",Toast.LENGTH_SHORT ).show ();
                }



            }
        } );

        Forgot_Password.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                windowManager=(WindowManager)getSystemService (WINDOW_SERVICE);
                relativeLayout=new RelativeLayout (LoginActivity.this );
                MytextView=new TextView ( LoginActivity.this );
                final RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
                relativeLayout.setBackgroundColor ( Color.GRAY);
                relativeLayout.setLayoutParams(layoutParams);
                relativeLayout.setPadding ( 15,15,15,15);

                RelativeLayout.LayoutParams textviewsize=new RelativeLayout.LayoutParams ( RelativeLayout.LayoutParams.MATCH_PARENT,70);
                textviewsize.addRule (RelativeLayout.ALIGN_PARENT_TOP);
                textviewsize.addRule (RelativeLayout.ABOVE);
                MytextView.setLayoutParams ( textviewsize );
                MytextView.setText ( "Send Password " );
                MytextView.setTextColor (Color.WHITE);
                MytextView.setTextSize ( 25 );
                MytextView.setGravity ( Gravity.CENTER );
                relativeLayout.addView ( MytextView );
                B1=new Button ( LoginActivity.this );
                RelativeLayout.LayoutParams buttonsize= new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT
                        ,RelativeLayout.LayoutParams.WRAP_CONTENT);
                buttonsize.addRule ( RelativeLayout.ALIGN_PARENT_BOTTOM );
                buttonsize.addRule (RelativeLayout.ALIGN_PARENT_LEFT);
                B1.setLayoutParams (buttonsize);
                B1.setText ( "Cancel" );
                relativeLayout.addView ( B1);
                B2=new Button (LoginActivity.this  );
                RelativeLayout.LayoutParams buttonsize1= new RelativeLayout.LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT
                        ,RelativeLayout.LayoutParams.WRAP_CONTENT);
                buttonsize1.addRule ( RelativeLayout.ALIGN_PARENT_RIGHT);
                buttonsize1.addRule (RelativeLayout.ALIGN_PARENT_BOTTOM);
                B2.setLayoutParams ( buttonsize1);
                B2.setText ("OK");
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

                    }
                } );
                B2.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View v) {

                        final Dialog dialog=new Dialog ( LoginActivity.this );
                        dialog.requestWindowFeature ( Window.FEATURE_NO_TITLE );
                        dialog.setContentView ( R.layout.messegesent );
                        dialog.setCancelable ( true );
                        dialog.show ();
                        Button button=(Button)dialog.findViewById ( R.id.send );
                        final EditText editText=(EditText)dialog.findViewById ( R.id.passphone );
                        button.setOnClickListener ( new View.OnClickListener () {
                            @Override
                            public void onClick(View v) {
                                UserDataBase userDataBase=new UserDataBase ( LoginActivity.this );
                                Cursor object=userDataBase.getalldata ();
                                while (object.moveToNext ()){

                                    pass=object.getString ( 2);
                                    number=object.getString ( 3 );
                                }
                                SmsManager smsManager=SmsManager.getDefault ();
                                smsManager.sendTextMessage (editText.getText ().toString (),null,pass,null,null );
                                smsManager.sendTextMessage (number,null,editText.getText ().toString ()+" This Number is Trying to know your password",null,null );
                                    dialog.dismiss ();
                                Toast.makeText (  getApplicationContext (),"A Message Has Been Sent ",Toast.LENGTH_SHORT).show ();

                            }
                        } );
                        windowManager.removeView (relativeLayout);


                    }
                } );


            }

        } );


    }
}
