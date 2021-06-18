package com.example.ocr_gui2;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;



public class save extends AppCompatActivity{

    ImageView pdf;
    EditText result,name;
    final Context context = this;
    String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        pdf=(ImageView) findViewById(R.id.pdf);
        result=(EditText)findViewById(R.id.result);


        //name=(EditText)findViewById(R.id.editTextDialogUserInput);
        String name1;

        String passArgs =getIntent().getExtras().getString("args");
        result.setText(passArgs);

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(save.this);
                builder.setTitle("pdf save as..");

// Set up the input
                final EditText input = new EditText(save.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                                String[] parmission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                requestPermissions(parmission, 1000);
                            } else savepdf();
                        } else savepdf();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();




                }

        });


    }
    private  void savepdf()
    {
        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "TextScanner" +File.separator+"Documents");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if(success) {
            Document doc = new Document();


            String mfilepath= Environment.getExternalStorageDirectory()+File.separator+"TextScanner"+File.separator+"Documents"+"/"+m_Text+".pdf";

            Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            try {
                PdfWriter.getInstance(doc, new FileOutputStream(mfilepath));
                doc.open();
                String mtext = result.getText().toString();

                doc.add(new Paragraph(mtext, smallBold));
                doc.close();
                Toast.makeText(this, "" + m_Text + ".pdf" + " is saved to " + mfilepath, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "This is Error msg : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }
}
