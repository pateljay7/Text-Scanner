package com.example.ocr_gui2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Locale;

public class gallery extends AppCompatActivity {

    TextToSpeech t3;
    Button importphoto,save2,result2,share2,tts3;
    ImageView importprev;
     Bitmap selectedImage;
     TextView textprev2;
     String st;

    public static int RESULT_LOAD_IMG=123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        importphoto=(Button)findViewById(R.id.importphoto);
        importprev=(ImageView)findViewById(R.id.importprev);
        save2=(Button)findViewById(R.id.save2);
        result2=(Button)findViewById(R.id.result2);
        textprev2=(TextView)findViewById(R.id.textprev2);
        share2=(Button)findViewById(R.id.share2);
        tts3=(Button)findViewById(R.id.tts3);

        importphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

            }
        });
        //===============================================================================
        t3=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t3.setLanguage(Locale.UK);
                }
            }
        });
        tts3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak =textprev2.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t3.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

            }
        });
        //================================================================================
        share2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText txtDescription = (EditText) findViewById(R.id.textprev2);
                String text = txtDescription.getText().toString();
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.app_name)));

            }
        });

        result2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettextfromimage(v);
            }
        });
        importphoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });


        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(getApplicationContext(), save.class);
                intent.putExtra("args",getText());
                startActivity(intent);
                finish();

            }
        });

    }

    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri;
                imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                 selectedImage = BitmapFactory.decodeStream(imageStream);
                importprev.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getApplicationContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
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
            Frame frame=new Frame.Builder().setBitmap(selectedImage).build();
            SparseArray<TextBlock> item=textRecognizer.detect(frame);
            for(int i=0;i<item.size();i++)
            {
                TextBlock myitem=item.valueAt(i);
                sb.append(myitem.getValue());
                sb.append("\n");
            }

            textprev2.setText(sb.toString());
        }
}
    public String getText()
    {
        String data=(String)textprev2.getText().toString();
        return data;
    }
}
