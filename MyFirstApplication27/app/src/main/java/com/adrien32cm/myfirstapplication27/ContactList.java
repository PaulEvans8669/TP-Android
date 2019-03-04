package com.adrien32cm.myfirstapplication27;

import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adrien32cm.myfirstapplication27.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class ContactList extends AppCompatActivity{

    private MyCustomContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        adapter = new MyCustomContactAdapter(ContactList.this);
        loadContacts();
        ListView listView = ((ListView)findViewById(R.id.list_view));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();
                CharSequence text = ((Contact)adapter.getItem(i)).getTel();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact c= ((Contact)adapter.getItem(i));
                String filePath = getFilesDir() + "/" + c.getNom()+"_"+c.getPrenom()+".txt";
                adapter.deleteContact(i,filePath);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        final Button btnNewContact = (Button)findViewById(R.id.btnNewContact);

        btnNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ContactList.this, MainActivity.class), 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final String[] contactData = data.getStringArrayExtra("data");
        Uri imgUri = null;
        try {
            imgUri = Uri.parse(contactData[7]);
        }
        catch(Exception e) {
        }
        adapter.addContact(new Contact(contactData[1], contactData[0],contactData[3],contactData[2],contactData[4],contactData[5],contactData[6],false, imgUri ));
        adapter.notifyDataSetChanged();
    }

    private void loadContacts(){
        File[] files = getFilesDir().listFiles();
        if(files.length>0) {
            for (File f : files) {
                Log.d("appFile", f.getAbsolutePath());
                try {
                    InputStream inStream = openFileInput(f.getName());
                    ObjectInputStream ois = new ObjectInputStream(inStream);
                    Contact loadedContact = (Contact)(ois.readObject());
                    Log.d("loadedElement",loadedContact.toString());
                    adapter.addContact(loadedContact);
                    adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }else{
            Log.d("loadedElement","empty list");
        }
    }
}
