package com.example.passwordrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StartPage extends AppCompatActivity {

    private long lastBackTime = 0;
    private long currentBackTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        Intent fromMain=getIntent();
        String userFrom=fromMain.getStringExtra("typeUsername");
        String passFrom=fromMain.getStringExtra("typePassword");
        SharedPreferences shared=getSharedPreferences("username",MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        if(userFrom!=null) {
            editor.putString("username", userFrom);
            editor.apply();
        }
        //userFrom=shared.getString("username","");
        //Log.i("username",userFrom);
        TextView showUser=(TextView)findViewById(R.id.showUser);
        if(userFrom!=null) {
            showUser.setText(getResources().getString(R.string.info1) + " " + userFrom);
            showUser.setTextColor(Color.RED);
        }else{
            shared=getSharedPreferences("username",MODE_PRIVATE);
            userFrom=shared.getString("username","");
            showUser.setText(getResources().getString(R.string.info1) + " " + userFrom);
            showUser.setTextColor(Color.RED);
        }
        Button add,show,logout;
        add=(Button) findViewById(R.id.addPassword);
        show=(Button)findViewById(R.id.checkInfo);
        logout=(Button)findViewById(R.id.backToMain);
        if(add!=null){
            String finalUserFrom = userFrom;
            add.setOnClickListener(click->{
                Intent gotoAdd=new Intent(this,InfoEnter.class);
                //startActivityForResult(gotoAdd,30);
                gotoAdd.putExtra("accUser", finalUserFrom);
                //gotoAdd.putExtra("accPass",passFrom);
                startActivity(gotoAdd);
                finish();
            });
        }
        if(show!=null){
            String finalUserFrom = userFrom;
            show.setOnClickListener(click->{
                Intent gotoShow=new Intent(this,Info.class);
                gotoShow.putExtra("accUser", finalUserFrom);
                //gotoShow.putExtra("accPass",passFrom);
                //startActivityForResult(gotoShow,30);
                startActivity(gotoShow);
                finish();
            });
        }
        if(logout!=null){
            logout.setOnClickListener(click->{
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
            });
        }
        Button goEdit=findViewById(R.id.editInfo);
        //String finalAccUser = accUser;
        if(goEdit!=null) {
            String finalUserFrom = userFrom;
            goEdit.setOnClickListener(click -> {
                Intent edit = new Intent(this, EditInfo.class);
                edit.putExtra("accUser", finalUserFrom);
                startActivity(edit);
                finish();
            });
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            currentBackTime = System.currentTimeMillis();
            if(currentBackTime - lastBackTime > 2 * 1000){
                Toast.makeText(this,getResources().getString(R.string.exit1), Toast.LENGTH_SHORT).show();
                lastBackTime = currentBackTime;
            }else{
                System.exit(0);
                finish();

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onPause() {

        //EditText userIn = (EditText) findViewById(R.id.username);
        Intent fromMain=getIntent();
        String userFrom=fromMain.getStringExtra("typeUsername");
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