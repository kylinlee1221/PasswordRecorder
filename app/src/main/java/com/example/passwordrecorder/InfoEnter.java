package com.example.passwordrecorder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Text;

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
        EditText usernameET,passwordET,securityET,websiteET;
        Button addBtn;
        final Boolean[] flag = {false};
        String usName,pwdET,secET;
        usernameET=(EditText)findViewById(R.id.usernameEnter);
        passwordET=(EditText)findViewById(R.id.passwordEnter);
        securityET=(EditText)findViewById(R.id.phoneEnter);
        addBtn=(Button)findViewById(R.id.addBtn);
        websiteET=findViewById(R.id.webEnter);
        usName=usernameET.getText().toString();
        pwdET=passwordET.getText().toString();
        secET=securityET.getText().toString();
        final String[] websiteSelect = new String[1];
        //website.setPrompt(getResources().getString(R.string.registerInfo).toString());
        //initDatas();
        //adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        //website.setAdapter(adapter);
        list=getResources().getStringArray(R.array.website).clone();
        website.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(InfoEnter.this,"The website you chose is "+list[position],Toast.LENGTH_LONG).show();
                websiteSelect[0] =list[position];
                if(list[position].equals("other")){
                    websiteET.setVisibility(View.VISIBLE);
                    //websiteET.setInputType(View.TEXT_ALIGNMENT_CENTER);
                    websiteET.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            websiteSelect[0]=list[position]+":"+websiteET.getText().toString();
                        }
                    });
                }else{
                    websiteET.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        if(addBtn!=null){
            addBtn.setOnClickListener(click->{
                if(!usernameET.getText().toString().equals("") && !passwordET.getText().toString().equals("") && !securityET.getText().toString().equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder(InfoEnter.this);
                    builder.setTitle(getResources().getString(R.string.info2))
                            .setMessage(getResources().getString(R.string.infoUser)+usernameET.getText().toString()+"\n"+getResources().getString(R.string.infoPwd)+passwordET.getText().toString()+"\n"+getResources().getString(R.string.infoWeb)+websiteSelect[0]+"\n"+getResources().getString(R.string.infoSecurity)+securityET.getText().toString()+"\n")
                            .setPositiveButton(getResources().getText(R.string.yesBtn), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent go=new Intent(InfoEnter.this,Info.class);
                                    go.putExtra("username",usernameET.getText().toString());
                                    go.putExtra("password",passwordET.getText().toString());
                                    go.putExtra("secPhone",securityET.getText().toString());
                                    if(!flag[0]) {
                                        go.putExtra("website", websiteSelect[0]);
                                    }else{
                                        go.putExtra("website",websiteET.getText().toString());
                                    }
                                    startActivityForResult(go,30);
                                }
                            }).setNegativeButton(getResources().getText(R.string.noBtn), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
                }else{
                    Toast.makeText(InfoEnter.this,getResources().getString(R.string.error1),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    @Override
    protected void onPause() {
        finish();
        super.onPause();
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