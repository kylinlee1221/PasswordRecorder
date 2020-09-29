package com.example.passwordrecorder;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

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
    private SQLiteDatabase db;

    public final String DB_Run="create table if not exists "+Table_Name+"("+
            COL_ID+" integer PRIMARY KEY AUTOINCREMENT, "+COL_User+" TEXT, "+COL_Pass+" TEXT, "+COL_SEC+" TEXT, "+COL_Website+" TEXT, "+COL_OPEN_ACCOUNT+" TEXT) ";

    public UserDBOpener(Context cnt) {

        super(cnt, DB_Name, null, V);
        db=getReadableDatabase();
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

    public void add(String username,String password,String secPhone,String website,String openAccount){
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
    public void delete(UserInfo info){
        db.delete(UserDBOpener.Table_Name,UserDBOpener.COL_ID+"=?",new String[]{Long.toString(info.getUserId())});
    }
    public ArrayList<UserInfo> getUserData(String openAccount){
        ArrayList<UserInfo> list=new ArrayList<UserInfo>();
        if(openAccount!=null) {
            if (db != null) {
                Cursor cursor = db.rawQuery("select * from InfoTable where accuser = ?", new String[]{openAccount});
                if(cursor.moveToFirst()){
                    do{
                        if(cursor.getColumnIndex(COL_Website)!=-1&&cursor.getColumnIndex(COL_User)!=-1&&cursor.getColumnIndex(COL_ID)!=-1&&cursor.getColumnIndex(COL_Pass)!=-1&&cursor.getColumnIndex(COL_SEC)!=-1&&cursor.getColumnIndex(COL_OPEN_ACCOUNT)!=-1) {
                            String userDT = cursor.getString(cursor.getColumnIndex(COL_User));
                            String passDT = cursor.getString(cursor.getColumnIndex(COL_Pass));
                            String secDT = cursor.getString(cursor.getColumnIndex(COL_SEC));
                            String webDT = cursor.getString(cursor.getColumnIndex(COL_Website));
                            long idDT = cursor.getLong(cursor.getColumnIndex(COL_ID));
                            list.add(new UserInfo(userDT, passDT, webDT, secDT, idDT));
                            //adapter = new Info.MyAdapter();
                            //myList.setAdapter(adapter);
                            //myList.setSelection(adapter.getCount() - 1);
                            //adapter.notifyDataSetChanged();
                        }
                    }while(cursor.moveToNext());
                    cursor.close();
                }
            }else{
                //Toast.makeText(this,getResources().getString(R.string.error4),Toast.LENGTH_LONG).show();
            }

        }
        return list;
    }
}
