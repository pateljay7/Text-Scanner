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


public class scan_result2 extends AppCompatActivity {
    TextToSpeech t1;
    EditText ed1;
    Button tts;
    //ImageView pdf=(ImageView)findViewById(R.id.pdf);
    Button share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result2);







    }
}
