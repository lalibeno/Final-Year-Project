package com.example.angleeyes.AngleEyesActivity;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

//Class to take input through mic prompt for/Voice commands
//https://youtu.be/ZoS37lUiWa0
public class SpeechInput extends AppCompatActivity {
    //make instance of MainActivity
    Main2Activity classcontext;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    public SpeechInput(Main2Activity context)
    {
        classcontext=context;
        //Go to speechInput method
        SpeechInput();
    }
//Method to Call mic prompt action
    private void SpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                classcontext.getPackageName());
       intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak operation");
        try {
            classcontext.startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (Exception a) {
            Toast.makeText(classcontext, "Error Occured Try again",Toast.LENGTH_SHORT ).show();
        }



    }

    }





