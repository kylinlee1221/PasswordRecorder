package com.example.passwordrecorder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Info extends AppCompatActivity {
    private ArrayList<UserInfo> infoList=new ArrayList<>();
    //SQLiteDatabase db;

    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ListView myList=(ListView)findViewById(R.id.infoList);
        TextView hint=findViewById(R.id.infoHint);
        UserDBOpener opener=new UserDBOpener(this);
        Intent fromLast=getIntent();
        String username=fromLast.getStringExtra("username");
        String password=fromLast.getStringExtra("password");
        String secPhone=fromLast.getStringExtra("secPhone");
        String website=fromLast.getStringExtra("website");
        String accUser=fromLast.getStringExtra("accUser");
        String accPass=fromLast.getStringExtra("accPass");
        SharedPreferences shared=getSharedPreferences("username",MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        if(accUser!=null) {
            editor.putString("username", accUser);
            editor.apply();
        }else{
            shared=getSharedPreferences("username",MODE_PRIVATE);
            accUser=shared.getString("username","");
        }

        if(accUser!=null) {
            infoList = opener.getUserData(accUser);
            if(infoList.size()!=0) {
                myList.setVisibility(View.VISIBLE);
                hint.setText(R.string.info12);
                hint.setTextColor(Color.BLACK);
                adapter = new MyAdapter();
                myList.setAdapter(adapter);
                //myList.setSelection(adapter.getCount() - 1);
                adapter.notifyDataSetChanged();
            }else{
                myList.setVisibility(View.GONE);
                hint.setText(R.string.error4);
                hint.setTextColor(Color.RED);
                Toast.makeText(this,getResources().getString(R.string.error4),Toast.LENGTH_LONG).show();
            }
        }


        //assert username != null;

        Button goBack=(Button)findViewById(R.id.BtnBackToFront);
        goBack.bringToFront();
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
                //deleteInfo(info1);
                //opener.delete(info1.getUsername(),info1.getPassword());
                opener.delete(info1);
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
            //Log.i("otherinfo status", String.valueOf(thisRow.getOtherInfo().equals("empty3")));
            //Log.i("secinfo status", String.valueOf(thisRow.getSecPhone().equals("empty3")));
            if(!thisRow.getOtherInfo().equals("empty3")&thisRow.getSecPhone().equals("empty3")){
                rowMessage.setText(getResources().getString(R.string.infoUser)+thisRow.getUsername()+"\n"+getResources().getString(R.string.infoPwd)+thisRow.getPassword()+"\n"+getResources().getString(R.string.infoWeb)+thisRow.getWebsite()+"\n"+getResources().getString(R.string.infoOther)+thisRow.getOtherInfo());
            } else if(thisRow.getOtherInfo().equals("empty3")&!thisRow.getSecPhone().equals("empty3")){
                rowMessage.setText(getResources().getString(R.string.infoUser)+thisRow.getUsername()+"\n"+getResources().getString(R.string.infoPwd)+thisRow.getPassword()+"\n"+getResources().getString(R.string.infoWeb)+thisRow.getWebsite()+"\n"+getResources().getString(R.string.infoSecurity)+thisRow.getSecPhone());
            }else if(!thisRow.getSecPhone().equals("empty3")&!thisRow.getSecPhone().equals("empty3")){
                rowMessage.setText(getResources().getString(R.string.infoUser)+thisRow.getUsername()+"\n"+getResources().getString(R.string.infoPwd)+thisRow.getPassword()+"\n"+getResources().getString(R.string.infoWeb)+thisRow.getWebsite()+"\n"+getResources().getString(R.string.infoSecurity)+thisRow.getSecPhone()+"\n"+getResources().getString(R.string.infoOther)+thisRow.getOtherInfo());
            }else if(thisRow.getSecPhone().equals("empty3")&thisRow.getOtherInfo().equals("empty3")){
                rowMessage.setText(getResources().getString(R.string.infoUser)+thisRow.getUsername()+"\n"+getResources().getString(R.string.infoPwd)+thisRow.getPassword()+"\n"+getResources().getString(R.string.infoWeb)+thisRow.getWebsite());
            }
            //rowMessage.setText(getResources().getString(R.string.infoUser)+thisRow.getUsername()+"\n"+getResources().getString(R.string.infoPwd)+thisRow.getPassword()+"\n"+getResources().getString(R.string.infoWeb)+thisRow.getWebsite()+"\n"+getResources().getString(R.string.infoSecurity)+thisRow.getSecPhone());
            return rowView;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        Intent fromMain=getIntent();
        String userFrom=fromMain.getStringExtra("accUser");
        SharedPreferences shared = getSharedPreferences("username", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        if(userFrom!=null) {
            editor.putString("username", userFrom);
            editor.apply();
        }
        //finish();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu); //通过getMenuInflater()方法得到MenuInflater对象，再调用它的inflate()方法就可以给当前活动创建菜单了，第一个参数：用于指定我们通过哪一个资源文件来创建菜单；第二个参数：用于指定我们的菜单项将添加到哪一个Menu对象当中。
        return true; // true：允许创建的菜单显示出来，false：创建的菜单将无法显示。
    }

    /**
     *菜单的点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.aboutUs:
                Intent intent=new Intent(this,AboutPage.class);
                startActivity(intent);
                Toast.makeText(this, getResources().getString(R.string.aboutUs), Toast.LENGTH_SHORT).show();
                //finish();
                break;
            case R.id.backupRestore:
                Intent intent1=new Intent(this,Backup.class);
                startActivity(intent1);
                Toast.makeText(this,"backup",Toast.LENGTH_LONG).show();
                break;
            case R.id.restore:
                Intent intent2=new Intent(this,Restore.class);
                startActivity(intent2);
                Toast.makeText(this,"restore",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }

        return true;
    }
}