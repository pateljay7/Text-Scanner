package com.example.ocr_gui2;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.itextpdf.text.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class home extends AppCompatActivity  {


    private static final int pic_id = 123;
    Button camera;
    EditText textResult;
    ImageView i1;
    Button b1,tts2,gallery;
    Bitmap photo;
    TextToSpeech t;
    EditText ed2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        i1 = (ImageView) findViewById(R.id.prev);
        camera=(Button)findViewById(R.id.camera);
        textResult= (EditText) findViewById(R.id.textView);
        b1=(Button)findViewById(R.id.button2);
        Button save=(Button)findViewById(R.id.save);
        Button tts2=(Button)findViewById(R.id.tts2);
        Button share=(Button)findViewById(R.id.share);
        ed2=(EditText)findViewById(R.id.editText);
        Button gallery =(Button)findViewById(R.id.gallery);
//==============================OCR========================================================
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), save.class);
                intent.putExtra("args",getText());
                startActivity(intent);

            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), gallery.class);
                startActivity(intent);

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettextfromimage(v);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, pic_id);
            }
        });


//===========================TEXT TO SPEECH========================================================
        t=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t.setLanguage(Locale.UK);
                }
            }
        });
        tts2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak =textResult.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                    t.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

            }
        });
//===========================SHARE OPTION==========================================================
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText txtDescription = (EditText) findViewById(R.id.textView);
                String text = txtDescription.getText().toString();
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.app_name)));

            }
        });

//=====================================================================================


    }

    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        // Match the request 'pic id with requestCode
        if (requestCode == pic_id) {

            // BitMap is data structure of image file
            // which stor the image in memory
            photo = (Bitmap) data.getExtras().get("data");

            // Set the image in imageview for display


//============================================================================
          File file;




                String path = Environment.getExternalStorageDirectory() + File.separator + "TextScanner"+File.separator+"CaptureImages";
                String name= new SimpleDateFormat("dd-MM-yyyy-HHmmss").format(new Date());
                file = new File(path, name.toString() + ".jpg");

                //String path = Environment.getExternalStorageDirectory().toString();


                OutputStream stream = null;
                try {
                    stream = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                try {
                    stream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri savedImageURI = Uri.parse(file.getAbsolutePath());


                //=============================================================================


                i1.setImageBitmap(photo);
                super.onActivityResult(requestCode, resultCode, data);

            }
        }

    public void gettextfromimage(View v ){

        //Bitmap picture = (Bitmap)data.getExtras().get("data");//this is your bitmap image and now you can do whatever you want with this


     //   Bitmap bitmap= BitmapFactory.decodeResource(getApplicationContext().getResources(),u);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if(!textRecognizer.isOperational())
        {
            Toast.makeText(getApplicationContext(),"Could not get the text",Toast.LENGTH_SHORT).show();
        }
        else
        {
            StringBuilder sb= new StringBuilder();
            Frame frame=new Frame.Builder().setBitmap(photo).build();
            SparseArray<TextBlock> item=textRecognizer.detect(frame);
            for(int i=0;i<item.size();i++)
            {
                TextBlock myitem=item.valueAt(i);
                sb.append(myitem.getValue());
                sb.append("\n");
            }

            textResult.setText(sb.toString());
        }
    }
    public String getText()
    {
        String data=(String)textResult.getText().toString();
        return data;
    }

    public void onPause(){
        if(t ==null){
            t.stop();
            t.shutdown();
        }
        super.onPause();
    }


}