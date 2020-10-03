package com.example.passwordrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class BackupRestore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_restore);
        Button backup,restore;
        backup=findViewById(R.id.backupBtn);
        restore=findViewById(R.id.restoreBtn);
        backup.setOnClickListener(click->{
            Intent intent=new Intent(this,Backup.class);
            startActivity(intent);
            finish();
        });
    }
}