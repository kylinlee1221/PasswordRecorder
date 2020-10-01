package com.example.passwordrecorder;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

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
        final String[] realCode = {null};
        EditText realCodeIn=findViewById(R.id.et_registeractivity_phoneCodes);
        ImageView realCodeImg=findViewById(R.id.iv_registeractivity_showCode);
        realCodeImg.setImageBitmap(Code.getInstance().createBitmap());
        realCode[0] =Code.getInstance().getCode().toLowerCase();
        final String[] finalRealCode = {realCode[0]};
        realCodeImg.setOnClickListener(click->{
            realCodeImg.setImageBitmap(Code.getInstance().createBitmap());
            finalRealCode[0] =Code.getInstance().getCode().toLowerCase();
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
                    if(realCodeIn.getText().toString().toLowerCase().equals(finalRealCode[0])) {
                        opener.add(accountReg.getText().toString(), passwordReg.getText().toString());
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(this, getResources().getString(R.string.success1), Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        Toast.makeText(this,getResources().getString(R.string.error6),Toast.LENGTH_LONG).show();
                        realCodeImg.setImageBitmap(Code.getInstance().createBitmap());
                        finalRealCode[0]=Code.getInstance().getCode().toLowerCase();
                    }
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(Register.this,MainActivity.class);
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