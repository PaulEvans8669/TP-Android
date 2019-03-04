package com.adrien32cm.myfirstapplication27;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import 	java.util.Calendar;
import android.widget.EditText;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.provider.MediaStore;

public class MainActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;

    private Uri imageUri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "onCreate");

        final Button btnSubmit = (Button)findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prenom = ((TextView)findViewById(R.id.fldPrenom)).getText().toString();
                String nom = ((TextView)findViewById(R.id.fldNom)).getText().toString();
                String tel = ((TextView)findViewById(R.id.fldTel)).getText().toString();
                String dateN = ((TextView)findViewById(R.id.fldBirth)).getText().toString();
                String mail = ((TextView)findViewById(R.id.fldMail)).getText().toString();
                String adresse = ((TextView)findViewById(R.id.fldAdresse)).getText().toString();
                String zip = ((TextView)findViewById(R.id.fldZip)).getText().toString();
                boolean male = ((RadioButton)findViewById(((RadioGroup)findViewById(R.id.radioGroup)).getCheckedRadioButtonId())).getText().equals("Mâle");
                if(formIsComplete()){
                    /*
                    Context context = getApplicationContext();
                    CharSequence text = prenom + " " + nom + "\n" + tel;
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    */

                    Intent i = new Intent();
                    String[] data = new String[8];
                    data[0] = prenom;
                    data[1] = nom;
                    data[2] = tel;
                    data[3] = dateN;
                    data[4] = mail;
                    data[5] = adresse;
                    data[6] = zip;

                    if(imageUri != null)
                        data[7] = imageUri.toString();

                    i.putExtra("data",data);
                    Contact c = new Contact(nom,prenom,dateN,tel,mail,adresse,zip,male,imageUri);
                    saveContact(c);
                    setResult(Activity.RESULT_OK, i);
                    finish();
                }
            }
        });

        final ImageView btnPhoto = (ImageView)findViewById(R.id.btnPhoto);
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });


        final Calendar myCalendar = Calendar.getInstance();

        EditText edittext= (EditText) findViewById(R.id.fldBirth);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                ((TextView)findViewById(R.id.fldBirth)).setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
            }

        };

        final TextView fldBirth = (TextView)findViewById(R.id.fldBirth);
        fldBirth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }



    private void saveContact(Contact c){
        try {
            String filePath = c.getNom()+"_"+c.getPrenom()+".txt";
            File f = new File(getFilesDir(),filePath);
            Log.d("filePath",f.getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(c);
            oos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean formIsComplete(){

        String prenom = ((TextView)findViewById(R.id.fldPrenom)).getText().toString();
        String nom = ((TextView)findViewById(R.id.fldNom)).getText().toString();
        String tel = ((TextView)findViewById(R.id.fldTel)).getText().toString();

        if(prenom.isEmpty() || nom.isEmpty() || tel.isEmpty()){
            Context context = getApplicationContext();
            CharSequence text = "Des champs nécessaires sont vides.";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return false;
        }else if(!(prenom.matches("[a-zA-Zéèïî\\-ê]+") && nom.matches("[a-zA-Zéèïî\\-ê]+") && tel.matches("[0-9]+") && tel.length() == 10))
        {
            Context context = getApplicationContext();
            CharSequence text = "Des champs nécessaires sont incorrects";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return false;
        }
        return true;
    }
}
