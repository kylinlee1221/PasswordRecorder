package com.example.passwordrecorder;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class UserDBOpener extends SQLiteOpenHelper {
    protected final static String DB_Name="UserInfo";
    protected final static int V=1;
    public final static String Table_Name="InfoTable";
    public final static String COL_User="username";
    public final static String COL_Pass="password";
    public final static String COL_SEC="securityPhone";
    public final static String COL_Website="website";
    public final static String COL_ID="_ID";
    public final static String COL_OPEN_ACCOUNT="accuser";
    //public final static String COL_OPEN_PASSWORD="accPass";
    //private SQLiteDatabase db;

    public final String DB_Run="create table if not exists "+Table_Name+"("+
            COL_ID+" integer PRIMARY KEY AUTOINCREMENT, "+COL_User+" TEXT, "+COL_Pass+" TEXT, "+COL_SEC+" TEXT, "+COL_Website+" TEXT, "+COL_OPEN_ACCOUNT+" TEXT) ";

    public UserDBOpener(Context cnt) {

        super(cnt, DB_Name, null, V);
        //db=getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("DROP TABLE IF EXISTS "+Table_Name);
        db.execSQL(DB_Run);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table_Name);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+Table_Name);
        onCreate(db);
    }

    /*public void add(String username,String password,String secPhone,String website,String openAccount){
        //db.execSQL("INSERT INTO "+Table_Name+" (username,password,securityPhone,website,accUser) VALUES(?,?,?,?,?) ",new String[]{username,password,secPhone,website,openAccount});
        ContentValues newValue=new ContentValues();
        newValue.put(COL_User,username);
        newValue.put(COL_Pass,password);
        newValue.put(COL_SEC,secPhone);
        newValue.put(COL_Website,website);
        newValue.put(COL_OPEN_ACCOUNT,openAccount);
        db.insert(Table_Name,null,newValue);
        Log.e("SQL STATUS",db.toString());
    }
    public void delete(String username,String password){
        db.execSQL("DELETE FROM InfoTable WHERE username = AND password ="+username+password);
    }
    public ArrayList<UserInfo> getUserData(String openAccount){
        ArrayList<UserInfo> list=new ArrayList<UserInfo>();
        //@SuppressLint("Recycle") Cursor cursor=db.rawQuery("select username,password,securityPhone,website from InfoTable where accUser =? and accPass =?",new String[]{openAccount,openPassword});
        Cursor cursor=db.query(Table_Name,new String[]{"username","password","securityPhone","website","accUser"},"accUser=?",new String[]{openAccount},null,null,null);

        int idCID=cursor.getColumnIndex(COL_ID);
        int userCID=cursor.getColumnIndex(COL_User);
        int passCID=cursor.getColumnIndex(COL_Pass);
        int secCID=cursor.getColumnIndex(COL_SEC);
        int webCID=cursor.getColumnIndex(COL_Website);
        Log.e("SQL STATUS", cursor.toString());
        if(cursor.getColumnCount()!=0) {
            while (cursor.moveToNext()) {
                if(!cursor.isNull(idCID)) {
                    long idDT = cursor.getLong(idCID);
                    String userDT = cursor.getString(userCID);
                    String passDT = cursor.getString(passCID);
                    String secDT = cursor.getString(secCID);
                    String webDT = cursor.getString(webCID);
                    list.add(new UserInfo(userDT, passDT, webDT, secDT, idDT));
                }
            }
        }
        return list;
    }*/
}
