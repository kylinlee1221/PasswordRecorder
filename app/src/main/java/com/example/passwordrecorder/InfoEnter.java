package com.example.passwordrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InfoEnter extends AppCompatActivity {

    String[] list;
    //ArrayAdapter<String> adapter;
    //private List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_enter);
        Spinner website=(Spinner)findViewById(R.id.registerWeb);
        final String[] websiteSelect = new String[1];
        //website.setPrompt(getResources().getString(R.string.registerInfo).toString());
        //initDatas();
        //adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        //website.setAdapter(adapter);
        list=getResources().getStringArray(R.array.website).clone();
        website.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(InfoEnter.this,"The website you chose is "+list[position],Toast.LENGTH_LONG).show();
                websiteSelect[0] =list[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }
    private void initDatas(){
        //list=new ArrayList<String>();
        /*<item>Google</item>
        <item>Facebook</item>
        <item>Outlook</item>
        <item>Amazon</item>
        <item>QQ</item>
        <item>163mail</item>
        <item>wechat</item>
        <item>blizzard</item>
        <item>Steam</item>
        <item>other</item>*/
        /*list.add("Google");
        list.add("Facebook");
        list.add("Outlook");
        list.add("QQ");
        list.add("Other");*/
    }
}