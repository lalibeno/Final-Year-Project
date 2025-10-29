package com.example.angleeyes.OCR;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.angleeyes.MainActivity;
import com.example.angleeyes.R;

public class AskPermissionActivity extends AppCompatActivity {
    private String[] Permissions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_permission);


        Permissions=new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.VIBRATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        if (!hasPermission(AskPermissionActivity.this,Permissions))
        {
            ActivityCompat.requestPermissions(AskPermissionActivity.this,Permissions,1);

            checkPermisson(9000);

//            startActivity(new Intent(AskPermissionActivity.this, MainActivity.class));

        }
        else {
            startActivity(new Intent(AskPermissionActivity.this, MainActivity.class));
        }


    }

    public void checkPermisson(long delay) {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (hasPermission(AskPermissionActivity.this,Permissions))
                {
                    startActivity(new Intent(AskPermissionActivity.this, MainActivity.class));
                }
                else
                {
                    ActivityCompat.requestPermissions(AskPermissionActivity.this,Permissions,1);
                    checkPermisson(9000);
                }

            }
        }, delay);
    }
    public  boolean hasPermission(Context context,String[] Permissions) {
        boolean value = false;
        for (String permission : Permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                value = true;
            } else {
                value = false;
            }

        }
        return value;

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1)
        {
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Camera Permission is Granted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Camera Permission is not Granted", Toast.LENGTH_SHORT).show();
            }
            if (grantResults[1]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "ACCESS FINE LOCATION Permission is Granted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "ACCESS FINE LOCATION Permission is not Granted", Toast.LENGTH_SHORT).show();
            }
            if (grantResults[2]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "INTERNET Permission is Granted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "INTERNET Permission is not Granted", Toast.LENGTH_SHORT).show();
            }
            if (grantResults[3]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "VIBRATE Permission is Granted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "VIBRATE Permission is not Granted", Toast.LENGTH_SHORT).show();
            }
            if (grantResults[4]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "WRITE EXTERNAL STORAGE Permission is Granted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "WRITE EXTERNAL STORAGE Permission is not Granted", Toast.LENGTH_SHORT).show();
            }
        }

    }
}