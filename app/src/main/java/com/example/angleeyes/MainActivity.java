package com.example.angleeyes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.angleeyes.AngleEyesActivity.Main2Activity;
import com.example.angleeyes.AngleEyesActivity.RegisterationActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                String name = sharedPreferences.getString("User_Name","");
                String pwd = sharedPreferences.getString("Password","");
                if (name.isEmpty() && pwd.isEmpty())
                {
                    startActivity(new Intent(MainActivity.this, RegisterationActivity.class));
                }
                else
                {
                    startActivity(new Intent(MainActivity.this, Main2Activity.class));
                }
                finish();
            }
        },3000);





    }


}