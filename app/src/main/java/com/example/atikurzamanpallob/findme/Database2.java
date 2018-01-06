package com.example.atikurzamanpallob.findme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Atikur Zaman Pallob on 05-Jun-17.
 */

public class Database2 extends SQLiteOpenHelper {

    public static final String database="Timer.db";
    public static final String table_name="TimeTable";
    public static final String Col_1="MINUTES";
    public static final String Col_2="SECONDS";
    public static final String SQLquerry="CREATE TABLE TimeTable (MINUTES TEXT ,SECONDS TEXT);";

    public Database2(Context context) {
        super ( context,database,null, 1 );
        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLquerry);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+table_name);
        onCreate ( db );

    }


    public boolean inserttime(String minutes,String seconds){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Col_1,minutes);
        contentValues.put ( Col_2,seconds );
        long result=db.insert(table_name,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;



    }
    public Cursor getalldatatime(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+table_name,null);
        return res;

    }

    public void deletealltime(){

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+table_name);
    }



}
