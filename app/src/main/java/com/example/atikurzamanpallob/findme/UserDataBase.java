package com.example.atikurzamanpallob.findme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Atikur Zaman Pallob on 08-May-17.
 */

public class UserDataBase extends SQLiteOpenHelper {
    public static final String database="Information.db";
    public static final String table_name="UserInfo";

    public static final String Col_1="NAME";
    public static final String Col_2="EMAIL";
    public static final String Col_3="PASSWORD";
    public static final String Col_4="SMS";
public static final String SQLQUERRY="CREATE TABLE UserInfo (NAME TEXT,EMAIL TEXT,PASSWORD TEXT, SMS TEXT);";

    public UserDataBase(Context context) {
        super ( context,database, null,1 );
        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLQUERRY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_name);
        onCreate ( db );
    }


    public boolean insertuserinfo(String Name,String Email,String Password,String Smsnumb){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Col_1,Name);
        contentValues.put(Col_2,Email);
        contentValues.put(Col_3,Password);
        contentValues.put ( Col_4,Smsnumb);
        long result=db.insert(table_name,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }



    public Cursor getalldata(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+table_name,null);
        return res;

    }

    public void deleteall(){

        SQLiteDatabase db=this.getWritableDatabase ();
        db.execSQL ("DELETE FROM "+ table_name);
    }

}
