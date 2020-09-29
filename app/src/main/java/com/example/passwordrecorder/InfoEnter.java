package com.example.passwordrecorder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
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
    String[] phoneCodeList;
    //ArrayAdapter<String> adapter;
    //private List<String> list;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_enter);
        Spinner website=(Spinner)findViewById(R.id.registerWeb);
        Spinner phoneCode=(Spinner)findViewById(R.id.phoneCode);
        EditText usernameET,passwordET,securityET,websiteET;
        UserDBOpener opener=new UserDBOpener(this);
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
        final String[] phoneSelect=new String[1];
        Intent fromLast=getIntent();
        String accUser=fromLast.getStringExtra("accUser");
        String accPass=fromLast.getStringExtra("accPass");
        //assert accUser != null;
        SharedPreferences shared=getSharedPreferences("username",MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        if(accUser!=null) {
            editor.putString("username", accUser);
            editor.apply();
        }
        if(accUser!=null) {
        }else{
            shared=getSharedPreferences("username",MODE_PRIVATE);
            accUser=shared.getString("username","");
        }
        //website.setPrompt(getResources().getString(R.string.registerInfo).toString());
        //initDatas();
        //adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        //website.setAdapter(adapter);
        list=getResources().getStringArray(R.array.website).clone();
        phoneCodeList=getResources().getStringArray(R.array.numberCode).clone();
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
        phoneCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                phoneSelect[0]=phoneCodeList[position];
                securityET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        phoneSelect[0]=phoneCodeList[position]+" "+securityET.getText().toString();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(addBtn!=null){
            String finalAccUser = accUser;
            //String finalAccUser1 = accUser;
            addBtn.setOnClickListener(click->{
                if(!usernameET.getText().toString().equals("") && !passwordET.getText().toString().equals("") && !phoneSelect[0].equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder(InfoEnter.this);
                    builder.setTitle(getResources().getString(R.string.info2))
                            .setMessage(getResources().getString(R.string.infoUser)+usernameET.getText().toString()+"\n"+getResources().getString(R.string.infoPwd)+passwordET.getText().toString()+"\n"+getResources().getString(R.string.infoWeb)+websiteSelect[0]+"\n"+getResources().getString(R.string.infoSecurity)+phoneSelect[0]+"\n")
                            .setPositiveButton(getResources().getText(R.string.yesBtn), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent go=new Intent(InfoEnter.this,Info.class);
                                    /*go.putExtra("username",usernameET.getText().toString());
                                    go.putExtra("password",passwordET.getText().toString());
                                    go.putExtra("secPhone",phoneSelect[0]);
                                    go.putExtra("accUser",finalAccUser);
                                    go.putExtra("accPass",accPass);
                                    if(!flag[0]) {
                                        go.putExtra("website", websiteSelect[0]);
                                    }else{
                                        go.putExtra("website",websiteET.getText().toString());
                                    }*/
                                    opener.add(usernameET.getText().toString(),passwordET.getText().toString(),phoneSelect[0],websiteSelect[0],finalAccUser);

                                    //go.putExtra("accUser", finalAccUser);
                                    //go.putExtra("accPass",accPass);
                                    //startActivityForResult(go,30);
                                    startActivity(go);
                                    finish();
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
        final Drawable[] drawables=passwordET.getCompoundDrawables();
        final int eyeWidth=drawables[2].getBounds().width();
        final Drawable EyeOpen=getResources().getDrawable(R.drawable.open);
        final boolean[] isHide = {true};
        EyeOpen.setBounds(drawables[2].getBounds());
        passwordET.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    float et_pwdMinX = v.getWidth() - eyeWidth - passwordET.getPaddingRight();
                    float et_pwdMaxX = v.getWidth();
                    float et_pwdMinY = 0;
                    float et_pwdMaxY = v.getHeight();
                    float x = event.getX();
                    float y = event.getY();

                    if (x < et_pwdMaxX && x > et_pwdMinX && y > et_pwdMinY && y < et_pwdMaxY) {
                        // 点击了眼睛图标的位置
                        isHide[0] = !isHide[0];
                        if (isHide[0]) {
                            passwordET.setCompoundDrawables(null,
                                    null,
                                    drawables[2], null);

                            passwordET.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        } else {
                            passwordET.setCompoundDrawables(null, null,
                                    EyeOpen,
                                    null);
                            passwordET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }
                    }
                }
                return false;
                }
                //return false;
            });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(InfoEnter.this,StartPage.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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