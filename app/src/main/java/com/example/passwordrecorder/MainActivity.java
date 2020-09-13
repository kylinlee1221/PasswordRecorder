package com.example.passwordrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginButton,registerButton;
        EditText password,username;
        registerButton=(Button)findViewById(R.id.register);
        loginButton=(Button)findViewById(R.id.login);
        registerButton.setOnClickListener(click->{
            Intent toRegister=new Intent(MainActivity.this,Register.class);
            startActivityForResult(toRegister,30);
        });
        loginButton.setOnClickListener(click->{

        });
    }
}