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
 * Created by Atikur Zaman Pallob on 05-Jun-17.
 */

public class Editprofile extends Activity{
    String Name,PhoneNumber,Password,ConfirmPassword,SMSNumber;
    UserDataBase userDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.edtitprofiles);
        userDataBase=new UserDataBase (this);
        final EditText NAME=(EditText)findViewById ( R.id.editname );
        final EditText PHONENUMBER=(EditText)findViewById ( R.id.editphonenumber);
        final EditText PASSWORD=(EditText)findViewById ( R.id.editpassword);
        final EditText CONFIRM=(EditText)findViewById ( R.id.editconfirm);
        final EditText SMSNUMBER=(EditText)findViewById ( R.id.editsmsnumber );
        Button SUBMIT=(Button)findViewById ( R.id.editsubmit);


        Cursor cursor=userDataBase.getalldata ();
        while(cursor.moveToNext ()){
            NAME.setText (cursor.getString ( 0 ));
            PHONENUMBER.setText ( cursor.getString (1) );
            PASSWORD.setText ( cursor.getString ( 2 ) );
            CONFIRM.setText (  cursor.getString ( 2 ));
            SMSNUMBER.setText ( cursor.getString ( 3 ) );
            userDataBase.deleteall ();
        }


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

                                boolean variable = userDataBase.insertuserinfo ( Name, PhoneNumber, Password, SMSNumber );
                                if(variable==true){

                                    Toast.makeText (getApplicationContext (),"Submit Successfully",Toast.LENGTH_SHORT).show ();
                                    startActivity ( new Intent ( Editprofile.this,MenuActivity.class ) );
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
        } );

    }
}
