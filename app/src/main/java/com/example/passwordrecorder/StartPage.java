package com.example.passwordrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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
        Button add,show;
        add=(Button) findViewById(R.id.addPassword);
        show=(Button)findViewById(R.id.checkInfo);
        if(add!=null){
            add.setOnClickListener(click->{
                Intent gotoAdd=new Intent(this,InfoEnter.class);
                startActivityForResult(gotoAdd,30);
            });
        }
        if(show!=null){
            show.setOnClickListener(click->{
                Intent gotoShow=new Intent(this,Info.class);
                startActivityForResult(gotoShow,30);
            });
        }
    }
}