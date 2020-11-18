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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        EditText usernameET,passwordET,securityET,websiteET,otherET;
        UserDBOpener opener=new UserDBOpener(this);
        Button addBtn;
        //Switch secFlag,otherFlag;
        TextView infoSec=findViewById(R.id.secInfo);
        final Boolean[] flag = {false};
        usernameET=(EditText)findViewById(R.id.usernameEnter);
        passwordET=(EditText)findViewById(R.id.passwordEnter);
        securityET=(EditText)findViewById(R.id.phoneEnter);
        addBtn=(Button)findViewById(R.id.addBtn);
        websiteET=findViewById(R.id.webEnter);
        //secFlag=findViewById(R.id.secSwitch);
        //otherFlag=findViewById(R.id.otherInfoSwitch);
        otherET=findViewById(R.id.otherInfoEnter);
        final String[] websiteSelect = new String[1];
        final String[] phoneSelect=new String[1];
        final String[] otherSelect=new String[1];
        phoneSelect[0]="empty3";
        otherSelect[0]="empty3";
        Intent fromLast=getIntent();
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
        Switch phoneSW,otherSW;
        phoneSW=findViewById(R.id.phoneSW);
        otherSW=findViewById(R.id.otherSW);
        phoneSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    phoneSelect[0]="empty3";
                    infoSec.setVisibility(View.GONE);
                    securityET.setVisibility(View.GONE);
                    phoneCode.setVisibility(View.GONE);
                }else{
                    infoSec.setVisibility(View.VISIBLE);
                    securityET.setVisibility(View.VISIBLE);
                    phoneCode.setVisibility(View.VISIBLE);
                }
            }
        });
        otherSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    otherSelect[0]="empty3";
                    otherET.setVisibility(View.GONE);
                }else{
                    otherET.setVisibility(View.VISIBLE);
                }
            }
        });
        list=getResources().getStringArray(R.array.website).clone();
        phoneCodeList=getResources().getStringArray(R.array.numberCode).clone();
        website.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                websiteSelect[0] =list[position];
                if(list[position].equals("other")||list[position].equals("其它")){
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
                    websiteET.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        /*if(!secFlag.isChecked()){
            phoneSelect[0]="empty3";
        }*/
        phoneCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //phoneSelect[0] = phoneCodeList[position];
                securityET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(securityET.getText().toString().equals("")){
                            phoneSelect[0]="empty3";
                        }else {
                            phoneSelect[0] = phoneCodeList[position] + " " + securityET.getText().toString();
                        }
                    }
                });
                if(securityET.getText().toString().equals("")){
                    phoneSelect[0]="empty3";
                }else {
                    phoneSelect[0] = phoneCodeList[position] + " " + securityET.getText().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        otherET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(otherET.getText().toString().equals("")){
                    otherSelect[0]="empty3";
                }else {
                    otherSelect[0] = otherET.getText().toString();
                }
            }
        });
        if(otherET.getText().toString().equals("")){
            otherSelect[0]="empty3";
        }else {
            otherSelect[0] = otherET.getText().toString();
        }
        if(addBtn!=null){
            String finalAccUser = accUser;
            //String finalAccUser1 = accUser;
            addBtn.setOnClickListener(click->{
                Log.e("phone",phoneSelect[0]);
                Log.e("web",websiteSelect[0]);
                Log.e("other",otherSelect[0]);
                if(!usernameET.getText().toString().equals("") && !passwordET.getText().toString().equals("") && !phoneSelect[0].equals("")&&!websiteSelect[0].equals("")&&!otherSelect[0].equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder(InfoEnter.this);
                    builder.setTitle(getResources().getString(R.string.info6))
                            .setPositiveButton(getResources().getText(R.string.yesBtn), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent go=new Intent(InfoEnter.this,Info.class);
                                    opener.add(usernameET.getText().toString(),passwordET.getText().toString(),phoneSelect[0],websiteSelect[0],finalAccUser,otherSelect[0]);
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