package com.example.passwordrecorder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDBOpener extends SQLiteOpenHelper {
    protected final static String DB_Name="UserInfo";
    protected final static int V=1;
    public final static String Table_Name="InfoTable";
    public final static String COL_User="username";
    public final static String COL_Pass="password";
    public final static String COL_ID="_ID";
    public final String DB_Run="create table "+Table_Name+"("+
            COL_ID+" integer PRIMARY KEY AUTOINCREMENT, "+COL_User+" TEXT, "+COL_Pass+" TEXT)";

    public UserDBOpener(Context cnt) {
        super(cnt, DB_Name, null, V);
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
}
