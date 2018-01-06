package com.example.atikurzamanpallob.findme;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Atikur Zaman Pallob on 08-May-17.
 */

public class Profile extends Activity{
    String Name,PhoneNumber,Password,ConfirmPassword,SMSNumber;
    UserDataBase userDataBase;
    boolean variable;
    String Minute,Seconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView (R.layout.profile);
        final EditText NAME=(EditText)findViewById ( R.id.NAME );
        final EditText PHONENUMBER=(EditText)findViewById ( R.id.PHONENUMBER );
        final EditText PASSWORD=(EditText)findViewById ( R.id.PASSWORD );
        final EditText CONFIRM=(EditText)findViewById ( R.id.CONFIRM );
        final EditText SMSNUMBER=(EditText)findViewById ( R.id.SMSNUMBER );
        Button SUBMIT=(Button)findViewById ( R.id.SUBMIT );


    userDataBase=new UserDataBase (this);

        SUBMIT.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Name=NAME.getText ().toString ();
                PhoneNumber=PHONENUMBER.getText ().toString ();
                Password=PASSWORD.getText ().toString ();
                ConfirmPassword=CONFIRM.getText ().toString ();
                SMSNumber=SMSNUMBER.getText ().toString ();

                if(Name.length ()!=0 && PhoneNumber.length ()!=0 && Password.length ()!=0 && SMSNumber.length ()!=0){

                    if(Password.equals (ConfirmPassword)){

                        if (PhoneNumber.length ()>=4 && PhoneNumber.length ()<=18){

                            if (SMSNumber.length ()>=4 && SMSNumber.length ()<=18){

                                variable=userDataBase.insertuserinfo (Name,PhoneNumber,Password,SMSNumber);
                                if(variable==true){

                                    Toast.makeText (getApplicationContext (),"Submit Successfully",Toast.LENGTH_SHORT).show ();

                                        startActivity ( new Intent ( Profile.this,LoginActivity.class ) );
                                        finish ();



                                }else{
                                    Toast.makeText (getApplicationContext (),"Something Went Wrong !!!",Toast.LENGTH_SHORT).show ();
                                }



                            }else{

                                Toast.makeText (getApplicationContext (),"SMS Number is not Valid",Toast.LENGTH_SHORT  ).show ();
                            }

                        }else{

                            Toast.makeText (getApplicationContext (),"Phone Number is not Valid",Toast.LENGTH_SHORT  ).show ();
                        }

                    }else{
                        Toast.makeText (getApplicationContext (),"Password Doesn't Match",Toast.LENGTH_SHORT  ).show ();
                    }


                }else{
                    Toast.makeText ( getApplicationContext () ,"Fill All The Fields",Toast.LENGTH_SHORT).show ();
                }

            }
        }

        );
    }
}
