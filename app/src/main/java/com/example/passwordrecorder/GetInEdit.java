package com.example.passwordrecorder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class GetInEdit extends AppCompatActivity {
    String[] list;
    String[] phoneCodeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_in_edit);
        Intent fromLast=getIntent();
        //Intent fromLast=getIntent();
        final String[] websiteSelect = new String[1];
        final String[] phoneSelect=new String[1];
        String accUser=fromLast.getStringExtra("accUser");
        String username=fromLast.getStringExtra("username");
        String password=fromLast.getStringExtra("password");
        String secPhone=fromLast.getStringExtra("secPhone");
        String website=fromLast.getStringExtra("webSite");
        UserDBOpener opener=new UserDBOpener(this);
        EditText userET,passET,secET,webET;
        Button addBtn2=findViewById(R.id.addBtn2);
        userET=findViewById(R.id.usernameEnter2);
        passET=findViewById(R.id.passwordEnter2);
        secET=findViewById(R.id.phoneEnter2);
        webET=findViewById(R.id.webEnter2);
        Spinner webCode=findViewById(R.id.registerWeb2);
        Spinner phoneCode=findViewById(R.id.phoneCode2);
        SharedPreferences shared=getSharedPreferences("username",MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        if(accUser!=null) {
            editor.putString("username", accUser);
            editor.apply();
        }else{
            shared=getSharedPreferences("username",MODE_PRIVATE);
            accUser=shared.getString("username","");
        }
        if(username!=null&&password!=null&&secPhone!=null&&website!=null&&accUser!=null){
            userET.setText(username);
            passET.setText(password);
        }
        list=getResources().getStringArray(R.array.website).clone();
        phoneCodeList=getResources().getStringArray(R.array.numberCode).clone();
        webCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(InfoEnter.this,"The website you chose is "+list[position],Toast.LENGTH_LONG).show();
                websiteSelect[0] =list[position];
                if(list[position].equals("other")){
                    webET.setVisibility(View.VISIBLE);
                    //websiteET.setInputType(View.TEXT_ALIGNMENT_CENTER);
                    webET.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            websiteSelect[0]=list[position]+":"+webET.getText().toString();
                        }
                    });
                }else{
                    webET.setVisibility(View.INVISIBLE);
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
                secET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        phoneSelect[0]=phoneCodeList[position]+" "+secET.getText().toString();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(addBtn2!=null){
            String finalAccUser = accUser;
            addBtn2.setOnClickListener(click->{
                if(!userET.getText().toString().equals("") && !passET.getText().toString().equals("") && !phoneSelect[0].equals("")&&!websiteSelect[0].equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setTitle(getResources().getString(R.string.info2))
                            .setMessage(getResources().getString(R.string.infoUser)+userET.getText().toString()+"\n"+getResources().getString(R.string.infoPwd)+passET.getText().toString()+"\n"+getResources().getString(R.string.infoWeb)+websiteSelect[0]+"\n"+getResources().getString(R.string.infoSecurity)+phoneSelect[0]+"\n")
                            .setPositiveButton(getResources().getText(R.string.yesBtn), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent go=new Intent(GetInEdit.this,Info.class);
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
                                    opener.add(userET.getText().toString(),passET.getText().toString(),phoneSelect[0],websiteSelect[0],finalAccUser);

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
                    Toast.makeText(this,getResources().getString(R.string.error1),Toast.LENGTH_LONG).show();
                }
            });
        }
        final Drawable[] drawables=passET.getCompoundDrawables();
        final int eyeWidth=drawables[2].getBounds().width();
        final Drawable EyeOpen=getResources().getDrawable(R.drawable.open);
        final boolean[] isHide = {true};
        EyeOpen.setBounds(drawables[2].getBounds());
        passET.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    float et_pwdMinX = v.getWidth() - eyeWidth - passET.getPaddingRight();
                    float et_pwdMaxX = v.getWidth();
                    float et_pwdMinY = 0;
                    float et_pwdMaxY = v.getHeight();
                    float x = event.getX();
                    float y = event.getY();

                    if (x < et_pwdMaxX && x > et_pwdMinX && y > et_pwdMinY && y < et_pwdMaxY) {
                        // 点击了眼睛图标的位置
                        isHide[0] = !isHide[0];
                        if (isHide[0]) {
                            passET.setCompoundDrawables(null,
                                    null,
                                    drawables[2], null);

                            passET.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        } else {
                            passET.setCompoundDrawables(null, null,
                                    EyeOpen,
                                    null);
                            passET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }
                    }
                }
                return false;
            }
            //return false;
        });
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
            default:
                break;
        }

        return true;
    }
}