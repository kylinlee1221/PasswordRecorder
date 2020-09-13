package com.example.passwordrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button RegisterBtn;
        EditText username,password,dateBirth;
        String name,pwd,birth;
        username=(EditText)findViewById(R.id.usernameR);
        password=(EditText)findViewById(R.id.passwordR);
        dateBirth=(EditText)findViewById(R.id.birthDay);
        RegisterBtn=(Button)findViewById(R.id.registerR);
        RegisterBtn.setOnClickListener(click->{
            //name=username.getText().toString();
            //pwd=password.getText().toString();
            if(username.getText().toString()==""||password.getText().toString()==""){
                Toast.makeText(Register.this,getResources().getString(R.string.error1),Toast.LENGTH_LONG).show();
            }else{

            }
        });
    }
}