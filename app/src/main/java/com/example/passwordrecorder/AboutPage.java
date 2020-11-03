package com.example.passwordrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AboutPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);
        TextView aboutAppName=(TextView)findViewById(R.id.aboutAppName);
        TextView versionNo=findViewById(R.id.versionNo);
        aboutAppName.setText(getResources().getString(R.string.aboutUs)+getResources().getString(R.string.app_name));
        Button github,mail;
        github=(Button)findViewById(R.id.checkInGitHub);
        mail=(Button)findViewById(R.id.reportBug);
        int code=packageCode(this);
        versionNo.setText(packageName(this));
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
    public static int packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }
    public static String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }
}