package com.example.passwordrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AccountDBOpener opener = new AccountDBOpener(this);
        EditText accountReg = (EditText) findViewById(R.id.usernameReg);
        EditText passwordReg = (EditText) findViewById(R.id.passwordReg);
        Button registerBtn = (Button) findViewById(R.id.registerBtn);
        Button backBtn=(Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(click->{
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        });
        registerBtn.setOnClickListener(click->{
            if(!accountReg.getText().toString().equals("")&&!passwordReg.getText().toString().equals("")){
                ArrayList<Account> data=opener.getAllData();
                boolean match=false;
                for(int i=0;i<data.size();i++){
                    Account account=data.get(i);
                    if(accountReg.getText().toString().equals(account.getName())){
                        match=true;
                        break;
                    }else{
                        match=false;
                    }
                }
                if(match){
                    Toast.makeText(this,getResources().getString(R.string.error2),Toast.LENGTH_LONG).show();
                }else{
                    opener.add(accountReg.getText().toString(),passwordReg.getText().toString());
                    Intent intent=new Intent(this,MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(this,getResources().getString(R.string.success1),Toast.LENGTH_LONG).show();
                    finish();
                }
            }else{
                Toast.makeText(this,getResources().getString(R.string.error1),Toast.LENGTH_LONG).show();
            }
        });
        final Drawable[] drawables = passwordReg.getCompoundDrawables();
        final int eyeWidth = drawables[2].getBounds().width();
        final Drawable EyeOpen = getResources().getDrawable(R.drawable.open);
        final boolean[] isHide = {true};
        EyeOpen.setBounds(drawables[2].getBounds());
        passwordReg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float et_pwdMinX = v.getWidth() - eyeWidth - passwordReg.getPaddingRight();
                    float et_pwdMaxX = v.getWidth();
                    float et_pwdMinY = 0;
                    float et_pwdMaxY = v.getHeight();
                    float x = event.getX();
                    float y = event.getY();

                    if (x < et_pwdMaxX && x > et_pwdMinX && y > et_pwdMinY && y < et_pwdMaxY) {
                        // 点击了眼睛图标的位置
                        isHide[0] = !isHide[0];
                        if (isHide[0]) {
                            passwordReg.setCompoundDrawables(null,
                                    null,
                                    drawables[2], null);

                            passwordReg.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        } else {
                            passwordReg.setCompoundDrawables(null, null,
                                    EyeOpen,
                                    null);
                            passwordReg.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }
                    }
                }
                return false;
            }
            //return false;
        });
    }
}