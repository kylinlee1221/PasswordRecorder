package com.example.passwordrecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class Restore extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore);
        Button restore=findViewById(R.id.restoreBtn2);
        TextView restoreHint=findViewById(R.id.restoreHint);
        requestPermissions();
        File xlsFile=new File(Environment.getExternalStorageDirectory(),"InfoBackup.xls");
        if(!xlsFile.exists()){
            restore.setVisibility(View.GONE);
            restoreHint.setText(R.string.error8);
            Toast.makeText(this,getResources().getString(R.string.error8),Toast.LENGTH_LONG).show();
        }else{
            String time=new SimpleDateFormat("yyyy-MM-dd").format(new Date(xlsFile.lastModified()));
            restore.setVisibility(View.VISIBLE);
            //restoreHint.setText(R.string.restoreHint);
            restoreHint.setTextColor(Color.GREEN);
            restoreHint.setText(getResources().getString(R.string.info13)+time);
        }
        restore.setOnClickListener(click->{

            try{
                if(!xlsFile.exists()){
                    Toast.makeText(this,getResources().getString(R.string.error8),Toast.LENGTH_LONG).show();
                }else{
                    readExcel(xlsFile.getAbsolutePath());
                    Toast.makeText(this,getResources().getString(R.string.info11),Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }catch (Exception e){
                Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        if(resultCode==RESULT_OK&&data!=null){
            //Log.e("File uri",data.toString());
            final String excelPath=getRealFilePath(this,data.getData());
            //assert excelPath != null;
            //Log.e("File path",excelPath);
            if(excelPath.contains(".xls")){
                readExcel(excelPath);
            }else{
                Toast.makeText(this,getResources().getString(R.string.error8),Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void readExcel(String excelPath){
        AccountDBOpener opener=new AccountDBOpener(this);
        UserDBOpener opener1=new UserDBOpener(this);
        opener.dropTB();
        opener1.dropTB();
        try{
            InputStream input=new FileInputStream(new File(excelPath));
            POIFSFileSystem fs=new POIFSFileSystem(input);
            HSSFWorkbook wb=new HSSFWorkbook(fs);
            HSSFSheet sheet=wb.getSheet(opener.Table_Name);
            HSSFSheet sheet1=wb.getSheet(opener1.Table_Name);
            Iterator<Row> rows=sheet.rowIterator();
            while(rows.hasNext()){
                HSSFRow row=(HSSFRow)rows.next();
                if(row.getRowNum()==0&&rows.hasNext()) row=(HSSFRow)rows.next();
                Account account=new Account();
                Iterator<Cell> cells=row.cellIterator();
                while(cells.hasNext()){
                    HSSFCell cell=(HSSFCell)cells.next();
                    switch (cell.getColumnIndex()){
                        case 0:
                            account.setId((long)cell.getNumericCellValue());
                            break;
                        case 1:
                            account.setName(cell.getStringCellValue());
                            break;
                        case 2:
                            account.setPassword(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                }
                opener.add(account.getName(),account.getPassword());
            }
            Iterator<Row> rows2=sheet1.rowIterator();
            while(rows2.hasNext()){
                HSSFRow row=(HSSFRow)rows2.next();
                if(row.getRowNum()==0&&rows2.hasNext()) row=(HSSFRow)rows2.next();
                AllInfo info=new AllInfo();
                Iterator<Cell> cells=row.cellIterator();
                while(cells.hasNext()){
                    HSSFCell cell=(HSSFCell)cells.next();
                    switch (cell.getColumnIndex()){
                        case 0:
                            info.setUserId((long)cell.getNumericCellValue());
                            break;
                        case 1:
                            info.setUsername(cell.getStringCellValue());
                            break;
                        case 2:
                            info.setPassword(cell.getStringCellValue());
                            break;
                        case 3:
                            info.setSecPhone(cell.getStringCellValue());
                            break;
                        case 4:
                            info.setWebsite(cell.getStringCellValue());
                            break;
                        case 5:
                            info.setOtherInfo(cell.getStringCellValue());
                            break;
                        case 6:
                            info.setAccUser(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                }
                opener1.add(info.getUsername(),info.getPassword(),info.getSecPhone(),info.getWebsite(),info.getAccUser(),info.getOtherInfo());
            }
        }catch (Exception e){
            Toast.makeText(this,e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void requestPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 先判断有没有权限
            if (Environment.isExternalStorageManager()) {
                //writeFile();
            } else {
                startActivityForResult(new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION), 1024);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 先判断有没有权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //writeFile();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1024);
            }
        } else {
            //writeFile();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1024) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // writeFile();
            } else {
                //ToastUtils.show("存储权限获取失败");
            }
        }
    }
}