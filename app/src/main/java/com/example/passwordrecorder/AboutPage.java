package com.example.passwordrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;

public class AboutPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);
        TextView aboutAppName=(TextView)findViewById(R.id.aboutAppName);
        aboutAppName.setText(getResources().getString(R.string.aboutUs)+getResources().getString(R.string.app_name));
        Button github,mail;
        github=(Button)findViewById(R.id.checkInGitHub);
        mail=(Button)findViewById(R.id.reportBug);
        github.setOnClickListener(click->{
            String gitUrl="https://github.com/kylinlee1221/PasswordRecorder";
            Uri chk=Uri.parse(gitUrl);
            Intent goGit=new Intent(Intent.ACTION_VIEW,chk);
            startActivity(goGit);
        });
        mail.setOnClickListener(click->{
            String address="26882137@qq.com";
            String subject="I found a bug in Password Recorder";
            String body="Here is the bug:";
            String content = "mailto:"+address+"?subject="+subject+"&body="+body;
            Intent returnIt = new Intent(Intent.ACTION_SENDTO);
            returnIt.setData(Uri.parse(content));
            startActivity(Intent.createChooser(returnIt, "Send email tips"));
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}