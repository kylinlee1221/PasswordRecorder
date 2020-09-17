package com.example.passwordrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
        EditText userEdit=(EditText)findViewById(R.id.username);
        loginBtn=(Button)findViewById(R.id.login);
        //username=userEdit.getText().toString();
        SharedPreferences shared=getSharedPreferences("username",MODE_PRIVATE);
        username=shared.getString("username","");

        loginBtn.setOnClickListener(click->{
            if(!userEdit.getText().toString().equals("")) {
                Intent intent = new Intent(this, StartPage.class);
                intent.putExtra("typeUsername", userEdit.getText().toString());
                startActivityForResult(intent, 30);
            }else {
                Toast.makeText(this,getResources().getString(R.string.error1),Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected void onPause(){
        super.onPause();
        EditText userIn=(EditText)findViewById(R.id.username);
        SharedPreferences shared=getSharedPreferences("username",MODE_PRIVATE);
        SharedPreferences.Editor editor=shared.edit();
        editor.putString("UserName",userIn.getText().toString());
        editor.commit();
    }
}