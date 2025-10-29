package com.example.angleeyes.AngleEyesActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.angleeyes.OCR.OpticalCharacterRecognition;
import com.example.angleeyes.R;
import com.example.angleeyes.VoiceGuidenceOverDirection.VoiceDirectionsActivity;
import com.mapbox.android.core.permissions.PermissionsManager;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    public static List<Activity> activity_history_list = new ArrayList<>();
    TextToVoiceConverter TVC;
    private PermissionsManager permissionsManager;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //Go to input method

    }


    public void statusLocationCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else {
            ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

// ARE WE CONNECTED TO THE NET
            if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                    connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
                Toast.makeText(this, "network enable", Toast.LENGTH_LONG).show();
                this.input(500 * 2);
            } else {
                Toast.makeText(this, "network not " +
                        "enable", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please enable GPS")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                        statusLocationCheck();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    //Method to Call and excute/Go TextToVoiceConverter class
    private void speech() {
        TVC = new TextToVoiceConverter(getResources().getStringArray(R.array.instruction_array), this);
    }

    //Method to Call and excute/Go  the SpeechInput class and to give delay 35 second in excution
    public void input(long delay) {
        SpeechInput mic = new SpeechInput(Main2Activity.this);
//        final Handler handler = new Handler(Looper.getMainLooper());
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Finish_Recent_Activities();
//                SpeechInput mic = new SpeechInput(Main2Activity.this);
//            }
//        }, delay);
    }

    public void Finish_Recent_Activities() {
//https://stackoverflow.com/questions/13674421/how-to-get-a-list-of-the-activity-history-stack
//You can access activity_history_list anywhere as long as it's static,
// you can   access a specific activity in history and finish it
        for (Activity activity_name : activity_history_list) {
            // activity_history_list.remove(activity_name);
            activity_name.finish();
        }
    }

    //come back from speechinput and receive input data from RECOGNIZE SPEECH Intent
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String Word;
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String arr[] = result.get(0).split(" ", 2);

                    //combing input word in single string
                    Word = arr[0] + " " + arr[1];
                    //Call the TextToVoiceConverter class
                    TVC = new TextToVoiceConverter(Word, this);
                    if (arr.length < 1 && arr.length > 2) {
                        TVC = new TextToVoiceConverter("Sorry couldn't catch that operation. Speak again", this);
                    } else if (arr[0].equals("find")) {

                        String finduser = arr[1];
                        Intent intent = new Intent(this, VoiceDirectionsActivity.class);
                        intent.putExtra("finduser", finduser);
                        startActivity(intent);
                    }  else {
                        //taking decision on input based
                        switch (Word) {
                            case "optical character":
                                startActivity(new Intent(this, OpticalCharacterRecognition.class));
                                break;

                            case "stop input":
                                break;
                            case "operation guide":
                                this.speech();
                                break;
                            case "stop speaking":
                                TVC.onPause();
                                break;

                            default:
                                TVC = new TextToVoiceConverter("Sorry couldn't catch that operation. Speak again", this);
                        }
                    }
                }
            }
        }
        //Again take input from user for selecting feature
//            this.input(1000);
    }



    @Override
    protected void onStart() {
        statusLocationCheck();
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (TVC != null) {
            TVC.onPause();
        }
        super.onStop();
    }
}


