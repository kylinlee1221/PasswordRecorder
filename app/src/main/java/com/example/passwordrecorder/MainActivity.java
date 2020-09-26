package com.example.passwordrecorder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String username;
        Button loginBtn,registerBtn;
        EditText userEdit = (EditText) findViewById(R.id.username);
        EditText passEdit=(EditText)findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.login);
        registerBtn=(Button)findViewById(R.id.register);
        //username=userEdit.getText().toString();
        SharedPreferences shared = getSharedPreferences("username", MODE_PRIVATE);
        username = shared.getString("username", "");
        AccountDBOpener dbOpener=new AccountDBOpener(this);
        registerBtn.setOnClickListener(click->{
            Intent intent=new Intent(this,Register.class);
            startActivity(intent);
            finish();
        });
        loginBtn.setOnClickListener(click -> {
            if (!userEdit.getText().toString().equals("")&&!passEdit.getText().toString().equals("")) {
                ArrayList<Account> data=dbOpener.getAllData();
                boolean match=false;
                for(int i=0;i<data.size();i++){
                    Account account=data.get(i);
                    if(userEdit.getText().toString().equals(account.getName())&&passEdit.getText().toString().equals(account.getPassword())){
                        match=true;
                        break;
                    }else{
                        match=false;
                    }
                }
                if(match) {
                    Intent intent = new Intent(this, StartPage.class);
                    intent.putExtra("typeUsername", userEdit.getText().toString());
                    intent.putExtra("typePassword",passEdit.getText().toString());
                    //startActivityForResult(intent, 30);
                    startActivity(intent);
                    Toast.makeText(this,getResources().getString(R.string.success2),Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(this,getResources().getString(R.string.error3),Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, getResources().getString(R.string.error1), Toast.LENGTH_LONG).show();
            }
        });
        final Drawable[] drawables=passEdit.getCompoundDrawables();
        final int eyeWidth=drawables[2].getBounds().width();
        final Drawable EyeOpen=getResources().getDrawable(R.drawable.open);
        final boolean[] isHide = {true};
        EyeOpen.setBounds(drawables[2].getBounds());
        passEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    float et_pwdMinX = v.getWidth() - eyeWidth - passEdit.getPaddingRight();
                    float et_pwdMaxX = v.getWidth();
                    float et_pwdMinY = 0;
                    float et_pwdMaxY = v.getHeight();
                    float x = event.getX();
                    float y = event.getY();

                    if (x < et_pwdMaxX && x > et_pwdMinX && y > et_pwdMinY && y < et_pwdMaxY) {
                        // 点击了眼睛图标的位置
                        isHide[0] = !isHide[0];
                        if (isHide[0]) {
                            passEdit.setCompoundDrawables(null,
                                    null,
                                    drawables[2], null);

                            passEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        } else {
                            passEdit.setCompoundDrawables(null, null,
                                    EyeOpen,
                                    null);
                            passEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
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
        EditText userIn = (EditText) findViewById(R.id.username);
        SharedPreferences shared = getSharedPreferences("username", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("UserName", userIn.getText().toString());
        editor.commit();
    }

}