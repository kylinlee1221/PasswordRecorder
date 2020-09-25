package com.example.passwordrecorder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AccountDBOpener extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    protected final static String DB_Name="LoginInfo";
    protected final static int V=1;
    public final static String Table_Name="LoginTable";
    public AccountDBOpener(Context cnt){
        super(cnt,DB_Name,null,V);
        db=getReadableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS user("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name TEXT,"+
                "password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }
    public void add(String name,String password){
        db.execSQL("INSERT INTO user (name,password) VALUES(?,?)",new Object[]{name,password});
    }
    public void delete(String name,String password){
        db.execSQL("DELETE FROM user WHERE name = AND password ="+name+password);
    }
    public void update(String password){
        db.execSQL("UPDATE user SET password = ?",new Object[]{password});
    }
    public ArrayList<Account> getAllData(){

        ArrayList<Account> list = new ArrayList<Account>();
        Cursor cursor = db.query("user",null,null,null,null,null,"name DESC");
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            list.add(new Account(name,password));
        }
        return list;
    }
}
