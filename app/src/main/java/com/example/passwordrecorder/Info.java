package com.example.passwordrecorder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class Info extends AppCompatActivity {
    ArrayList<UserInfo> infoList=new ArrayList<>();
    SQLiteDatabase db;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ListView myList=(ListView)findViewById(R.id.infoList);
        UserDBOpener opener=new UserDBOpener(this);
        db=opener.getWritableDatabase();
        String[] cols={opener.COL_ID,opener.COL_User,opener.COL_Pass,opener.COL_SEC,opener.COL_Website};
        Cursor results=db.query(false,opener.Table_Name,cols,null,null,null,null,null,null);
        int idCID=results.getColumnIndex(opener.COL_ID);
        int userCID=results.getColumnIndex(opener.COL_User);
        int passCID=results.getColumnIndex(opener.COL_Pass);
        int secCID=results.getColumnIndex(opener.COL_SEC);
        int webCID=results.getColumnIndex(opener.COL_Website);
        Intent fromLast=getIntent();
        String username=fromLast.getStringExtra("username");
        String password=fromLast.getStringExtra("password");
        String secPhone=fromLast.getStringExtra("secPhone");
        String website=fromLast.getStringExtra("website");
        while(results.moveToNext()){
            String userDT=results.getString(userCID);
            String passDT=results.getString(passCID);
            String secDT=results.getString(secCID);
            String webDT=results.getString(webCID);
            long idDT=results.getLong(idCID);
            infoList.add(new UserInfo(userDT,passDT,webDT,secDT,idDT));
            adapter=new MyAdapter();
            myList.setAdapter(adapter);
            myList.setSelection(adapter.getCount()-1);
            adapter.notifyDataSetChanged();

        }
        //assert username != null;
        if (username != null&&password!=null&&secPhone!=null&&website!=null) {
            adapter = new MyAdapter();
            myList.setAdapter(adapter);
            myList.setSelection(adapter.getCount() - 1);
            ContentValues newRow = new ContentValues();
            newRow.put(opener.COL_User, username);
            newRow.put(opener.COL_Pass, password);
            newRow.put(opener.COL_SEC, secPhone);
            newRow.put(opener.COL_Website, website);
            long newID = db.insert(opener.Table_Name, null, newRow);
            UserInfo newInfo = new UserInfo(username, password, website, secPhone, newID);
            infoList.add(newInfo);
            adapter = new MyAdapter();
            adapter.notifyDataSetChanged();
            myList.setSelection(adapter.getCount() - 1);
        }
        Button goBack=(Button)findViewById(R.id.BtnBackToFront);
        if(goBack!=null){
            goBack.setOnClickListener(click->{
                Intent back=new Intent(this,StartPage.class);
                //startActivityForResult(back,30);
                startActivity(back);
                finish();
            });
        }
        myList.setOnItemLongClickListener((p,b,pos,id)->{
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            UserInfo info1=infoList.get(pos);
            builder.setTitle(getResources().getString(R.string.info3)).setPositiveButton(getResources().getString(R.string.yesBtn),(click,arg)->{
                deleteInfo(info1);
                infoList.remove(pos);
                //adapter=new MyAdapter();
                adapter.notifyDataSetChanged();
                Intent intent=new Intent(Info.this,Info.class);
                startActivity(intent);
                finish();
            }).setNegativeButton(getResources().getString(R.string.noBtn),(click,arg)->{}).create().show();
            return true;
        });
    }
    private void deleteInfo(UserInfo info){
        db.delete(UserDBOpener.Table_Name,UserDBOpener.COL_ID+"=?",new String[]{Long.toString(info.getUserId())});
    }
    protected class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return infoList.size();
        }

        @Override
        public UserInfo getItem(int i) {
            return infoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = getLayoutInflater();
            View rowView;
            TextView rowMessage;
            UserInfo thisRow = getItem(i);
            rowView = inflater.inflate(R.layout.show, viewGroup, false);
            rowMessage = rowView.findViewById(R.id.msg);
            ImageView opt = rowView.findViewById(R.id.img);
            opt.setImageResource(R.drawable.lyric);
            //rowMessage.setText(thisRow.getLyric());
            Locale locale=getResources().getConfiguration().locale;
            String lang=locale.getLanguage();
            //Log.e("Lang",lang);
            /*if(lang.equals("zh")){
                rowMessage.setText((thisRow.getInfoCN()));
            }else {
                rowMessage.setText((thisRow.getInfo()));
            }*/
            rowMessage.setText(getResources().getString(R.string.infoUser)+thisRow.getUsername()+"\n"+getResources().getString(R.string.infoPwd)+thisRow.getPassword()+"\n"+getResources().getString(R.string.infoWeb)+thisRow.getWebsite()+"\n"+getResources().getString(R.string.infoSecurity)+thisRow.getSecPhone());
            return rowView;
        }
    }
    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(Info.this,StartPage.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}