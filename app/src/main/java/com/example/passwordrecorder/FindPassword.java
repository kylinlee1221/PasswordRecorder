package com.example.passwordrecorder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FindPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        EditText user,pass;
        TextView passHint;
        Button check,change;
        final Boolean[] checkFlag = {false};
        AccountDBOpener opener=new AccountDBOpener(this);
        user=(EditText)findViewById(R.id.findUsername);
        pass=(EditText)findViewById(R.id.passwordFind);
        check=(Button)findViewById(R.id.checkBtn);
        change=(Button)findViewById(R.id.changeBtn);
        passHint=findViewById(R.id.passHint);
        pass.setVisibility(View.INVISIBLE);
        passHint.setVisibility(View.INVISIBLE);
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
                                check.setVisibility(View.INVISIBLE);
                                change.setVisibility(View.VISIBLE);
                                pass.setVisibility(View.VISIBLE);
                                passHint.setVisibility(View.VISIBLE);
                                pass.setText("");
                            }
                        }).create().show();
                        break;
                    }else if(i==data.size()-1&&!checkFlag[0]){
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
                    /*opener.update(user.getText().toString(),pass.getText().toString());
                    Toast.makeText(this,getResources().getString(R.string.success3),Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(FindPassword.this,MainActivity.class);
                    startActivity(intent);
                    finish();*/
                    ArrayList<Account> data=opener.getAllData();
                    for(int i=0;i<data.size();i++){
                        Account p=data.get(i);
                        if(!p.getPassword().equals(pass.getText().toString())&&p.getName().equals(user.getText().toString())){
                            opener.update(user.getText().toString(),pass.getText().toString());
                            Toast.makeText(this,getResources().getString(R.string.success3),Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(FindPassword.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else if (p.getPassword().equals(pass.getText().toString())&&p.getName().equals(user.getText().toString())){
                            Toast.makeText(this,getResources().getString(R.string.error7),Toast.LENGTH_LONG).show();
                        }
                    }
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(FindPassword.this,MainActivity.class);
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