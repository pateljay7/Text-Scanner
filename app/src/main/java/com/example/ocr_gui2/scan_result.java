package com.example.ocr_gui2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.itextpdf.text.Document;


import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class scan_result extends home {
  TextToSpeech t1;
    EditText ed1,text;
    Button tts;
    ImageView pdf=(ImageView)findViewById(R.id.pdf);
    Button share;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);
        ed1=(EditText)findViewById(R.id.editText);
        tts=(Button)findViewById(R.id.tts);
        share=(Button)findViewById(R.id.share);
//=================================================================


   /*  /* pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT> Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
                    {
                        String[] parmission={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(parmission,1000);
                    }
                    else savepdf();
                }
                else savepdf();
            }
        });


//================================TEXT TO SPEECH==============================================
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if(status != TextToSpeech.ERROR) {
                        t1.setLanguage(Locale.UK);
                    }
                }
            });
        tts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = ed1.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
//==================================SHARE OPTION============================================


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText txtDescription = (EditText) findViewById(R.id.editText);


                String text = txtDescription.getText().toString();

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.app_name)));

            }
        });
//==============================================================================

    }

    public void onPause(){
        if(t1 !=null){
                t1.stop();

                t1.shutdown();

        }
        super.onPause();
    }

   /* private  void savepdf()
    {
        Document doc=new Document();
        String mfile=new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        String mfilepath= Environment.getExternalStorageDirectory()+"/"+mfile+".pdf";
        Font smallBold=new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD);
        try{
            PdfWriter.getInstance(doc,new FileOutputStream(mfilepath));
            doc.open();
            String mtext=textResult.getText().toString();

            doc.add(new Paragraph(mtext,smallBold));
            doc.close();
            Toast.makeText(this, ""+mfile+".pdf"+" is saved to "+mfilepath, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(this,"This is Error msg : " +e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1000:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    savepdf();
                } else Toast.makeText(this, "parmission denied..", Toast.LENGTH_SHORT).show();
        }
    }*/
    }}