package com.example.passwordrecorder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class FindPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        EditText user,pass;
        Button check,change;
        final Boolean[] checkFlag = {false};
        AccountDBOpener opener=new AccountDBOpener(this);
        user=(EditText)findViewById(R.id.findUsername);
        pass=(EditText)findViewById(R.id.passwordFind);
        check=(Button)findViewById(R.id.checkBtn);
        change=(Button)findViewById(R.id.changeBtn);

        check.setOnClickListener(click->{
            if(!user.getText().toString().equals("")){
                ArrayList<Account> data=opener.getAllData();
                for(int i=0;i<data.size();i++){
                    Account sd=data.get(i);
                    if(sd.getName().equals(user.getText().toString())){
                        AlertDialog.Builder builder=new AlertDialog.Builder(FindPassword.this);
                        builder.setTitle(getResources().getString(R.string.info4)).setPositiveButton(getResources().getString(R.string.sureBtn), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checkFlag[0] =true;
                            }
                        }).create().show();
                        break;
                    }else{
                        Toast.makeText(this,getResources().getString(R.string.error5),Toast.LENGTH_LONG).show();
                    }
                }
            }else{
                Toast.makeText(this,getResources().getString(R.string.error1),Toast.LENGTH_LONG).show();
            }
        });
        change.setOnClickListener(click->{
            if(!pass.getText().toString().equals("")){
                if(checkFlag[0]){
                    opener.update(user.getText().toString(),pass.getText().toString());
                    Toast.makeText(this,getResources().getString(R.string.success3),Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(FindPassword.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        final Drawable[] drawables=pass.getCompoundDrawables();
        final int eyeWidth=drawables[2].getBounds().width();
        final Drawable EyeOpen=getResources().getDrawable(R.drawable.open);
        final boolean[] isHide = {true};
        EyeOpen.setBounds(drawables[2].getBounds());
        pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    float et_pwdMinX = v.getWidth() - eyeWidth - pass.getPaddingRight();
                    float et_pwdMaxX = v.getWidth();
                    float et_pwdMinY = 0;
                    float et_pwdMaxY = v.getHeight();
                    float x = event.getX();
                    float y = event.getY();

                    if (x < et_pwdMaxX && x > et_pwdMinX && y > et_pwdMinY && y < et_pwdMaxY) {
                        // 点击了眼睛图标的位置
                        isHide[0] = !isHide[0];
                        if (isHide[0]) {
                            pass.setCompoundDrawables(null,
                                    null,
                                    drawables[2], null);

                            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        } else {
                            pass.setCompoundDrawables(null, null,
                                    EyeOpen,
                                    null);
                            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }
                    }
                }
                return false;
            }
            //return false;
        });
    }
}