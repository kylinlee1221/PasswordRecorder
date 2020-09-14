package com.example.passwordrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class StartPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        Intent fromMain=getIntent();
        String userFrom=fromMain.getStringExtra("typeUsername");
        //SharedPreferences shared=getSharedPreferences("username",MODE_PRIVATE);
        //userFrom=shared.getString("username","");
        Log.i("username",userFrom);
        TextView showUser=(TextView)findViewById(R.id.showUser);
        showUser.setText(getResources().getString(R.string.info1)+" "+userFrom);
        showUser.setTextColor(Color.RED);

    }
}