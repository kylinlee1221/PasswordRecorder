package com.example.passwordrecorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.util.ArrayList;

public class Backup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);
        Button backupBtn=findViewById(R.id.backupBtn2);
        AccountDBOpener opener=new AccountDBOpener(this);
        UserDBOpener opener1=new UserDBOpener(this);

        ArrayList<Account> accounts=new ArrayList<Account>();
        accounts=opener.getAllData();
        ArrayList<AllInfo> allInfos=new ArrayList<AllInfo>();
        allInfos=opener1.getAllData();
        ArrayList<Account> finalAccounts = accounts;
        ArrayList<AllInfo> finalAllInfos = allInfos;
        int perMissionCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(perMissionCheck!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        backupBtn.setOnClickListener(click->{
            HSSFWorkbook workbook=new HSSFWorkbook();
            HSSFSheet sheet=workbook.createSheet(opener.Table_Name);
            HSSFSheet sheet1=workbook.createSheet(opener1.Table_Name);
            createExcelHead(sheet);
            for(Account account: finalAccounts){
                createCell(account.getId(),account.getName(),account.getPassword(),sheet);
            }
            createExcelHead1(sheet1);
            for(AllInfo allInfo: finalAllInfos){
                createCell1(allInfo.getUserId(),allInfo.getUsername(),allInfo.getPassword(),allInfo.getSecPhone(),allInfo.getWebsite(),allInfo.getOtherInfo(),allInfo.getAccUser(),sheet1);
            }
            File xlsFile=new File(Environment.getExternalStorageDirectory(),"InfoBackup.xls");
            try{
                if(!xlsFile.exists()){
                    if(xlsFile.createNewFile()){
                        Toast.makeText(this,getResources().getString(R.string.success4)+xlsFile.getAbsolutePath(),Toast.LENGTH_LONG).show();
                    }
                }else{
                    boolean flag=xlsFile.delete();
                    if(flag){
                        if(xlsFile.createNewFile()){
                            Toast.makeText(this,getResources().getString(R.string.success4)+xlsFile.getAbsolutePath(),Toast.LENGTH_LONG).show();
                        }
                    }
                }
                workbook.write(xlsFile);
                workbook.close();
            }catch (Exception e){
                Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
            }
        });

    }
    private void createExcelHead(HSSFSheet sheet){
        HSSFRow headRow=sheet.createRow(0);
        headRow.createCell(0).setCellValue(AccountDBOpener.COL_ID);
        headRow.createCell(1).setCellValue(AccountDBOpener.COL_USER);
        headRow.createCell(2).setCellValue(AccountDBOpener.COL_Pass);
    }
    private void createCell(Long id,String name,String password,HSSFSheet sheet){
        HSSFRow dataRow=sheet.createRow(sheet.getLastRowNum()+1);
        dataRow.createCell(0).setCellValue(id);
        dataRow.createCell(1).setCellValue(name);
        dataRow.createCell(2).setCellValue(password);
    }
    private void createExcelHead1(HSSFSheet sheet){
        HSSFRow headRow=sheet.createRow(0);
        headRow.createCell(0).setCellValue(UserDBOpener.COL_ID);
        headRow.createCell(1).setCellValue(UserDBOpener.COL_User);
        headRow.createCell(2).setCellValue(UserDBOpener.COL_Pass);
        headRow.createCell(3).setCellValue(UserDBOpener.COL_SEC);
        headRow.createCell(4).setCellValue(UserDBOpener.COL_Website);
        headRow.createCell(5).setCellValue(UserDBOpener.COL_OTHER_INFO);
        headRow.createCell(6).setCellValue(UserDBOpener.COL_OPEN_ACCOUNT);
    }
    private void createCell1(long id,String user,String password,String secPhone,String website,String otherInfo,String accUser,HSSFSheet sheet){
        HSSFRow dataRow=sheet.createRow(sheet.getLastRowNum()+1);
        dataRow.createCell(0).setCellValue(id);
        dataRow.createCell(1).setCellValue(user);
        dataRow.createCell(2).setCellValue(password);
        dataRow.createCell(3).setCellValue(secPhone);
        dataRow.createCell(4).setCellValue(website);
        dataRow.createCell(5).setCellValue(otherInfo);
        dataRow.createCell(6).setCellValue(accUser);
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