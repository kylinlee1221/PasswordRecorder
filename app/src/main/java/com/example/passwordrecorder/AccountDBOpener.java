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
    public final static String Table_Name="user";
    public final static String COL_ID="_id";
    public final static String COL_USER="name";
    public final static String COL_Pass="password";
    private String Drop_SQL="DROP TABLE IF EXISTS user";
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
    public void update(String name,String password){
        db.execSQL("UPDATE user SET password = ? WHERE name = ?",new Object[]{password,name});
    }
    public ArrayList<Account> getAllData(){

        ArrayList<Account> list = new ArrayList<Account>();
        /*Cursor cursor = db.query("user",null,null,null,null,null,"name DESC");
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            long id=cursor.getLong(cursor.getColumnIndex("_id"));
            list.add(new Account(name,password,id));
        }*/
        if(db!=null){
            Cursor cursor=db.rawQuery("select * from user",new String[]{});
            if(cursor.moveToFirst()){
                do{
                    if(cursor.getColumnIndex(COL_ID)!=-1&&cursor.getColumnIndex(COL_Pass)!=-1&&cursor.getColumnIndex(COL_USER)!=-1){
                        String name=cursor.getString(cursor.getColumnIndex(COL_USER));
                        String password=cursor.getString(cursor.getColumnIndex(COL_Pass));
                        long id=cursor.getLong(cursor.getColumnIndex(COL_ID));
                        list.add(new Account(name,password,id));
                    }
                }while(cursor.moveToNext());
                cursor.close();
            }
        }
        return list;
    }
    public void dropTB(){
        db.execSQL(Drop_SQL);
        onCreate(db);
    }
}
