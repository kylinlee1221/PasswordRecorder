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

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*UserDBOpener opener=new UserDBOpener(this);
        db=opener.getWritableDatabase();
        String[] cols={opener.COL_ID,opener.COL_User,opener.COL_Pass};
        Cursor results=db.query(false,opener.Table_Name,cols,null,null,null,null,null,null);

        Button loginButton,registerButton;
        EditText password,username;
        registerButton=(Button)findViewById(R.id.register);
        loginButton=(Button)findViewById(R.id.login);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        int UserIDX=results.getColumnIndex(opener.COL_User);
        int idX=results.getColumnIndex(opener.COL_ID);
        int passIDX=results.getColumnIndex(opener.COL_Pass);
        Intent fromLast=getIntent();
        String userFromLast=fromLast.getStringExtra("UserName");
        registerButton.setOnClickListener(click->{
            Intent toRegister=new Intent(MainActivity.this,Register.class);
            startActivityForResult(toRegister,30);
        });
        String user,pwd;
        user=username.getText().toString();
        pwd=password.getText().toString();
        loginButton.setOnClickListener(click->{
            if(userFromLast!=null){
                while(results.moveToNext()){

                    String pwdInDB=results.getString(passIDX);
                    String userInDB=results.getString(UserIDX);
                    if(userFromLast.equals(userInDB)&&pwd.equals(pwdInDB)){
                        Toast.makeText(this,getResources().getString(R.string.success2),Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this,getResources().getString(R.string.error3),Toast.LENGTH_LONG).show();
                    }
                }
            }else{
                if(results.isLast()){
                    results.moveToFirst();
                }
                while(results.moveToNext()){
                    String pwdInDB=results.getString(passIDX);
                    String userInDB=results.getString(UserIDX);
                    if(user.equals(userInDB)&&pwd.equals(pwdInDB)){
                        Toast.makeText(this,getResources().getString(R.string.success2),Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this,getResources().getString(R.string.error3),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });*/
        String username;
        Button loginBtn;
        EditText userEdit = (EditText) findViewById(R.id.username);
        EditText passEdit=(EditText)findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.login);
        //username=userEdit.getText().toString();
        SharedPreferences shared = getSharedPreferences("username", MODE_PRIVATE);
        username = shared.getString("username", "");

        loginBtn.setOnClickListener(click -> {
            if (!userEdit.getText().toString().equals("")) {
                Intent intent = new Intent(this, StartPage.class);
                intent.putExtra("typeUsername", userEdit.getText().toString());
                //startActivityForResult(intent, 30);
                startActivity(intent);
                finish();
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