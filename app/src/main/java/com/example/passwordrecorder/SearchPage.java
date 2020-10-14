package com.example.passwordrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class SearchPage extends AppCompatActivity {

    private ArrayList<UserInfo> infoList=new ArrayList<UserInfo>();
    String[] list;
    String[] weblist;
    String[] phonelist;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        Intent fromLast=getIntent();
        String accUser=fromLast.getStringExtra("accUser");
        SharedPreferences shared=getSharedPreferences("username",MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        if(accUser!=null) {
            editor.putString("username", accUser);
            editor.apply();
        }else{
            shared=getSharedPreferences("username",MODE_PRIVATE);
            accUser=shared.getString("username","");
        }
        ListView searchList=findViewById(R.id.searchList);
        EditText userInput=findViewById(R.id.userInput),phoneInput=findViewById(R.id.phoneEnter3);
        Spinner searchByWhat=findViewById(R.id.searchBy),website=findViewById(R.id.webSelected),phone=findViewById(R.id.phoneCode3);
        Button search=findViewById(R.id.searchBtn),back=findViewById(R.id.BtnBackToFront2);
        UserDBOpener opener=new UserDBOpener(this);
        list=getResources().getStringArray(R.array.searchByWhat).clone();
        weblist=getResources().getStringArray(R.array.website).clone();
        phonelist=getResources().getStringArray(R.array.numberCode).clone();
        final String[] searchInfo = new String[1];
        final String[] webinfo = new String[1];
        final String[] phoneSelect=new String[1];
        phoneSelect[0]="empty3";
        final int[] selectedSearch=new int[1];
        String finalAccUser = accUser;
        searchByWhat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(list[position].equals("website")||list[position].equals("网站")){
                    userInput.setVisibility(View.GONE);
                    website.setVisibility(View.VISIBLE);
                    phoneInput.setVisibility(View.GONE);
                    phone.setVisibility(View.GONE);
                    selectedSearch[0]=position;
                    website.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            webinfo[0] =weblist[position];
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }else if(list[position].equals("username")||list[position].equals("用户名")){
                    userInput.setVisibility(View.VISIBLE);
                    website.setVisibility(View.GONE);
                    phoneInput.setVisibility(View.GONE);
                    phone.setVisibility(View.GONE);
                    selectedSearch[0]=position;
                    searchInfo[0]=userInput.getText().toString();
                    userInput.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if(userInput.getText().toString()!=null){
                                searchInfo[0] =userInput.getText().toString();
                            }else{
                                Toast.makeText(SearchPage.this,getResources().getString(R.string.error1),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else if(list[position].equals("security phone")||list[position].equals("安全手机")){
                    phoneInput.setVisibility(View.VISIBLE);
                    phone.setVisibility(View.VISIBLE);
                    userInput.setVisibility(View.GONE);
                    website.setVisibility(View.GONE);
                    selectedSearch[0]=position;
                    phone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            phoneInput.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    if(phoneInput.getText().toString()!=null){
                                        phoneSelect[0]=phonelist[position]+" "+phoneInput.getText().toString();
                                    }else{
                                        phoneSelect[0]="empty3";
                                    }
                                }
                            });
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        search.setOnClickListener(click->{
            //Log.e("searchby", String.valueOf(selectedSearch[0]));
            if(selectedSearch[0]==1){
                infoList.clear();
                infoList=opener.getUserDataByWeb(webinfo[0],finalAccUser);
                if(infoList.size()!=0) {
                    adapter = new MyAdapter();
                    searchList.setAdapter(adapter);
                    searchList.setSelection(adapter.getCount() - 1);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(SearchPage.this,getResources().getString(R.string.error3),Toast.LENGTH_LONG).show();
                }
            }else if(selectedSearch[0]==0){
                infoList.clear();
                if(!searchInfo[0].equals("")&userInput.getText().toString()!=null){
                    infoList=opener.getUserDataByUser(searchInfo[0],finalAccUser);
                    if(infoList.size()!=0){
                        adapter=new MyAdapter();
                        searchList.setAdapter(adapter);
                        searchList.setSelection(adapter.getCount()-1);
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(SearchPage.this,getResources().getString(R.string.error3),Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(SearchPage.this,getResources().getString(R.string.error1),Toast.LENGTH_LONG).show();
                }

            }else if(selectedSearch[0]==2){
                if(!phoneSelect[0].equals("")) {
                    infoList.clear();
                    infoList=opener.getUserDataByPhone(phoneSelect[0],finalAccUser);
                    if(infoList.size()!=0){
                        adapter=new MyAdapter();
                        searchList.setAdapter(adapter);
                        searchList.setSelection(adapter.getCount()-1);
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(SearchPage.this,getResources().getString(R.string.error3),Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(SearchPage.this,getResources().getString(R.string.error1),Toast.LENGTH_LONG).show();
                }

            }
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(this,StartPage.class);
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