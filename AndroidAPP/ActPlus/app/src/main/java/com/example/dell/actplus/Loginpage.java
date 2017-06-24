package com.example.dell.actplus;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public class Loginpage extends AppCompatActivity {

    private String userName, passWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        this.passWord = this.userName = "";
        setUpDefalutFragment();
        Intent intent = getIntent();
        Boolean auto_login = intent.getBooleanExtra("auto_login", true);
        if (auto_login) {
            getData();
        }
    }
    private void setUpDefalutFragment() {
        FragmentManager fragmentManager = this.getFragmentManager();
        login login = new login();
        fragmentManager.beginTransaction().replace(R.id.login_fragment, login).commit();
    }
    public void setCurrentUserInfo(String username, String password) {
        this.userName = username;
        this.passWord = password;
    }
    public Map<String, String> getCurrentUserInfo() {
        Map<String, String> ans = new HashMap<>();
        ans.put("password", this.passWord);
        ans.put("username", this.userName);
        return ans;
    }
    public void saveData(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("USER_INFO", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("password", this.passWord);
        editor.putString("username", this.userName);
        editor.commit();
    }
    public void getData() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("USER_INFO", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //默认为空
        this.userName = sharedPreferences.getString("username", "");
        this.passWord = sharedPreferences.getString("password", "");
    }
}
