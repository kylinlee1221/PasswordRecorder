package com.example.passwordrecorder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class EditInfo extends AppCompatActivity {

    private ArrayList<UserInfo> infoList=new ArrayList<>();
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        ListView myList=(ListView)findViewById(R.id.infoList2);
        Button back=findViewById(R.id.BtnBackToFront2);

        UserDBOpener opener=new UserDBOpener(this);
        Intent fromLast=getIntent();
        String accUser=fromLast.getStringExtra("accUser");
        SharedPreferences shared=getSharedPreferences("username",MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        if(accUser!=null) {
            editor.putString("username", accUser);
            editor.apply();
        }else{
            shared=getSharedPreferences("username",MODE_PRIVATE);
            accUser=shared.getString("username","");
        }
        if(accUser!=null) {
            infoList = opener.getUserData(accUser);
            adapter = new MyAdapter();
            myList.setAdapter(adapter);
            myList.setSelection(adapter.getCount() - 1);
            adapter.notifyDataSetChanged();
        }else if(opener.getUserData(accUser).size()==0){
            Toast.makeText(this,getResources().getString(R.string.error4),Toast.LENGTH_LONG).show();
        }
        back.bringToFront();
        String finalAccUser = accUser;
        myList.setOnItemLongClickListener((p, b, pos, id)->{
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            UserInfo infoToEdit=infoList.get(pos);
            builder.setTitle(getResources().getString(R.string.info5)).setPositiveButton(getResources().getString(R.string.yesBtn),(click,arg)->{
                String username=infoToEdit.getUsername();
                String password=infoToEdit.getPassword();
                String secPhone=infoToEdit.getSecPhone();
                String webSite=infoToEdit.getWebsite();
                Intent intent=new Intent(this,GetInEdit.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.putExtra("secPhone",secPhone);
                intent.putExtra("webSite",webSite);
                intent.putExtra("accUser", finalAccUser);
                opener.delete(infoToEdit);
                infoList.remove(pos);
                //adapter=new MyAdapter();
                adapter.notifyDataSetChanged();
                startActivity(intent);
                finish();
            }).setNegativeButton(getResources().getString(R.string.noBtn),(click,arg)->{

            }).create().show();
            return true;
        });
        back.setOnClickListener(click->{
            Intent intent=new Intent(this,StartPage.class);
            startActivity(intent);
            finish();
        });
    }
    protected class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return infoList.size();
        }

        @Override
        public UserInfo getItem(int i) {
            return infoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = getLayoutInflater();
            View rowView;
            TextView rowMessage;
            UserInfo thisRow = getItem(i);
            rowView = inflater.inflate(R.layout.show, viewGroup, false);
            rowMessage = rowView.findViewById(R.id.msg);
            ImageView opt = rowView.findViewById(R.id.img);
            opt.setImageResource(R.drawable.lyric);
            //rowMessage.setText(thisRow.getLyric());
            Locale locale=getResources().getConfiguration().locale;
            String lang=locale.getLanguage();
            //Log.e("Lang",lang);
            /*if(lang.equals("zh")){
                rowMessage.setText((thisRow.getInfoCN()));
            }else {
                rowMessage.setText((thisRow.getInfo()));
            }*/
            if(!thisRow.getOtherInfo().equals("empty3")&thisRow.getSecPhone().equals("empty3")){
                rowMessage.setText(getResources().getString(R.string.infoUser)+thisRow.getUsername()+"\n"+getResources().getString(R.string.infoPwd)+thisRow.getPassword()+"\n"+getResources().getString(R.string.infoWeb)+thisRow.getWebsite()+"\n"+getResources().getString(R.string.infoOther)+thisRow.getOtherInfo());
            } else if(thisRow.getOtherInfo().equals("empty3")&!thisRow.getSecPhone().equals("empty3")){
                rowMessage.setText(getResources().getString(R.string.infoUser)+thisRow.getUsername()+"\n"+getResources().getString(R.string.infoPwd)+thisRow.getPassword()+"\n"+getResources().getString(R.string.infoWeb)+thisRow.getWebsite()+"\n"+getResources().getString(R.string.infoSecurity)+thisRow.getSecPhone());
            }else if(!thisRow.getSecPhone().equals("empty3")&!thisRow.getSecPhone().equals("empty3")){
                rowMessage.setText(getResources().getString(R.string.infoUser)+thisRow.getUsername()+"\n"+getResources().getString(R.string.infoPwd)+thisRow.getPassword()+"\n"+getResources().getString(R.string.infoWeb)+thisRow.getWebsite()+"\n"+getResources().getString(R.string.infoSecurity)+thisRow.getSecPhone()+"\n"+getResources().getString(R.string.infoOther)+thisRow.getOtherInfo());
            }else if(thisRow.getSecPhone().equals("empty3")&thisRow.getOtherInfo().equals("empty3")){
                rowMessage.setText(getResources().getString(R.string.infoUser)+thisRow.getUsername()+"\n"+getResources().getString(R.string.infoPwd)+thisRow.getPassword()+"\n"+getResources().getString(R.string.infoWeb)+thisRow.getWebsite());
            }
            //rowMessage.setText(getResources().getString(R.string.infoUser)+thisRow.getUsername()+"\n"+getResources().getString(R.string.infoPwd)+thisRow.getPassword()+"\n"+getResources().getString(R.string.infoWeb)+thisRow.getWebsite()+"\n"+getResources().getString(R.string.infoSecurity)+thisRow.getSecPhone());
            return rowView;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        Intent fromMain=getIntent();
        String userFrom=fromMain.getStringExtra("accUser");
        SharedPreferences shared = getSharedPreferences("username", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        if(userFrom!=null) {
            editor.putString("username", userFrom);
            editor.apply();
        }
        //finish();
        super.onPause();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(this,StartPage.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu); //通过getMenuInflater()方法得到MenuInflater对象，再调用它的inflate()方法就可以给当前活动创建菜单了，第一个参数：用于指定我们通过哪一个资源文件来创建菜单；第二个参数：用于指定我们的菜单项将添加到哪一个Menu对象当中。
        return true; // true：允许创建的菜单显示出来，false：创建的菜单将无法显示。
    }

    /**
     *菜单的点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.aboutUs:
                Intent intent=new Intent(this,AboutPage.class);
                startActivity(intent);
                Toast.makeText(this, getResources().getString(R.string.aboutUs), Toast.LENGTH_SHORT).show();
                //finish();
                break;
            default:
                break;
        }

        return true;
    }
}