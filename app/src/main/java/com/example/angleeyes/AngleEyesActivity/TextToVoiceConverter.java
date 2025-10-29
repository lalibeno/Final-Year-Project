package com.example.angleeyes.AngleEyesActivity;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;
//Class to convert text string into Voice
//https://www.youtube.com/watch?v=q_nepJy16_A
//https://www.tutorialspoint.com/android/android_text_to_speech.htm
public class TextToVoiceConverter {
    //array
    private  String[] arraytext;
    private  String text;
    private Context classcontext;

    TextToSpeech TTS;

    public TextToVoiceConverter(String data, Context context) {
        text = data;
        classcontext = context;
        //Go to   method
        generateVoice(text);
    }
    public TextToVoiceConverter(String[] data, Context context) {
        arraytext = data;
        classcontext = context;
        //Go to   method
        generateVoiceForArray(arraytext);
    }
//generateVoiceForArray
    private void generateVoiceForArray(String[] text) {
        //Synthesizes speech from text for immediate playback or to create a sound file.
        TTS = new TextToSpeech(classcontext, new TextToSpeech.OnInitListener() {
            @Override
             public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    TTS.setLanguage(Locale.UK);
                    TTS.setPitch((float) 1.0);
                    TTS.setSpeechRate((float) 0.8);
                    for (int i = 0; i < text.length; i++)
                    {
                        while(TTS.isSpeaking())
                        {
                         delay();
                        }
                        delay();
                        //speak array item one by one
                        TTS.speak(text[i],TextToSpeech.QUEUE_FLUSH,null);

                    }

                }
            }

        });
        if (TTS.isSpeaking()){
             delay();
        }
    }
    //generateVoiceForSingle String
    private void generateVoice(String text) {
        TTS = new TextToSpeech(classcontext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    TTS.setLanguage(Locale.UK);
                    TTS.setPitch((float) 1.0);
                    TTS.setSpeechRate((float) 0.8);
                    TTS.speak(text,TextToSpeech.QUEUE_FLUSH,null);

                    }

                }
        });
        if (TTS.isSpeaking()){
            delay();
        }
    }
    //delay 1 second in excution
    private void delay()
    {
        try {
            Thread.sleep(1000);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void onPause()
    {
        if(TTS!=null)
        {
            TTS.stop();
            TTS.shutdown();
        }
    }

    }
