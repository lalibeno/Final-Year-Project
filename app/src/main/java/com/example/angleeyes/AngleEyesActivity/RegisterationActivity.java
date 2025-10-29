package com.example.angleeyes.AngleEyesActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.angleeyes.OCR.AskPermissionActivity;
import com.example.angleeyes.R;

public class RegisterationActivity extends AppCompatActivity {
    EditText nameEdTxt, pwdEdTxt;
    String user_name, user_pwd;
    Button register;
    Boolean isLocationPermissionAllowed = false;
    Boolean isCameraPermissionAllowed = false;
    Integer locationRequestCode = 11;
    Integer cameraRequestCode = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        //startActivity(new Intent(this, AskPermissionActivity.class));
        nameEdTxt = (EditText) findViewById(R.id.name);
        pwdEdTxt = (EditText) findViewById(R.id.pwd);
        register = (Button) findViewById(R.id.register);
        if (ActivityCompat.checkSelfPermission(RegisterationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(RegisterationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, locationRequestCode);

        } else if (ActivityCompat.checkSelfPermission(RegisterationActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterationActivity.this, new String[]{Manifest.permission.CAMERA}, cameraRequestCode);

        } else {
            isLocationPermissionAllowed = true;
            isCameraPermissionAllowed = true;
        }

        register.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                if (isLocationPermissionAllowed && isCameraPermissionAllowed) {
                    user_name = nameEdTxt.getText().toString();
                    user_pwd = pwdEdTxt.getText().toString();
                    if (user_name.isEmpty() || user_pwd.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please fill field", Toast.LENGTH_LONG).show();
                    } else {

                        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("User_Name", user_name);

                        editor.putString("Password", user_pwd);

                        editor.commit();

                        Toast.makeText(RegisterationActivity.this, "Registered!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegisterationActivity.this, Main2Activity.class);

                        startActivity(intent);

                    }
                }
                else {
                    if (!isLocationPermissionAllowed) {
                        ActivityCompat.requestPermissions(RegisterationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, locationRequestCode);

                    } else if (!isLocationPermissionAllowed) {
                        ActivityCompat.requestPermissions(RegisterationActivity.this, new String[]{Manifest.permission.CAMERA}, cameraRequestCode);

                    }
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == locationRequestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isLocationPermissionAllowed = true;
                Toast.makeText(this, "location permission granted", Toast.LENGTH_LONG).show();
                if (ActivityCompat.checkSelfPermission(RegisterationActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(RegisterationActivity.this, new String[]{Manifest.permission.CAMERA}, cameraRequestCode);

                } else {
                    isCameraPermissionAllowed = true;
                }
            }
        } else if (requestCode == cameraRequestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isCameraPermissionAllowed = true;
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            }
        } else {
            if (!isLocationPermissionAllowed) {
                ActivityCompat.requestPermissions(RegisterationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, locationRequestCode);

            } else if (!isLocationPermissionAllowed) {
                ActivityCompat.requestPermissions(RegisterationActivity.this, new String[]{Manifest.permission.CAMERA}, cameraRequestCode);

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //https://stackoverflow.com/questions/13674421/how-to-get-a-list-of-the-activity-history-stack
        Main2Activity.activity_history_list.add(RegisterationActivity.this);
    }
}