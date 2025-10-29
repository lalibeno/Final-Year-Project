package com.example.angleeyes.OCR;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.angleeyes.AngleEyesActivity.Main2Activity;
import com.example.angleeyes.AngleEyesActivity.TextToVoiceConverter;
import com.example.angleeyes.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;

import java.util.List;

//https://youtu.be/fmTlgwgKJmE
public class OpticalCharacterRecognition extends AppCompatActivity {
   //Blind Person OCR firbase project name
      Main2Activity homeActivity;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    TextToVoiceConverter textToVoiceConverter;
    Bitmap imageBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optical_character_recognition);
        homeActivity=new Main2Activity();
        //Go back to Method
        takePicture();

    }
    //https://stackoverflow.com/questions/13674421/how-to-get-a-list-of-the-activity-history-stack
    @Override
    public void onStart() {
        super.onStart();
        Main2Activity.activity_history_list.add(OpticalCharacterRecognition.this);
    }
    //take Picture
    private void takePicture() {
        Intent PictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            this.startActivityForResult(PictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {

        }
    }
    //come back from   camera activity by receiving image data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            //convert in Bitmap type
            imageBitmap = (Bitmap) extras.get("data");
            //Go back to Method
            detectTextFromImage();
        }

    }
    private void detectTextFromImage() {

        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextDetector firebaseVisionTextDetector= FirebaseVision.getInstance().getVisionTextDetector();
        firebaseVisionTextDetector.detectInImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                       displayTextFromImage(firebaseVisionText);
            }



        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OpticalCharacterRecognition.this,"Error" + e.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("Error" ,e.getMessage());
            }
        });
    }
    private void displayTextFromImage(FirebaseVisionText firebaseVisionText) {
        List<FirebaseVisionText.Block> blockList=firebaseVisionText.getBlocks();
        if (blockList.size()==0)
        {
           textToVoiceConverter=new TextToVoiceConverter("No text found",this);
        }
        else
        {
            String text="";
            for (FirebaseVisionText.Block block: firebaseVisionText.getBlocks())
            {
                text=text+block.getText();
            }
            textToVoiceConverter=new TextToVoiceConverter(text,this);
        }

    }

    @Override
    protected void onDestroy() {
        //Again take input from user for selecting feature
       startActivity(new Intent(this,Main2Activity.class));
        super.onDestroy();
    }
}