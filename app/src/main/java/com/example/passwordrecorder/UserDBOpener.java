package com.example.passwordrecorder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDBOpener extends SQLiteOpenHelper {
    protected final static String DB_Name="UserInfo";
    protected final static int V=1;
    public final static String Table_Name="InfoTable";

    public UserDBOpener(Context cnt) {
        super(cnt, DB_Name, null, V);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS "+Table_Name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
