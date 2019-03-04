package com.adrien32cm.myfirstapplication27;

import android.content.ClipData;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.adrien32cm.myfirstapplication27.R;

import java.io.File;
import java.util.ArrayList;

public class MyCustomContactAdapter extends BaseAdapter{
    private ArrayList<Contact> contacts;
    private Context context;

    public MyCustomContactAdapter(Context context)
    {
        this.context = context;
        contacts = new ArrayList<Contact>();
    }

    public void addContact(Contact c)
    {
        contacts.add(c);
    }

    public void deleteContact(String phone)
    {
        for(int i = 0; i < contacts.size(); i++)
        {
            if(contacts.get(i).getTel()== phone)
                contacts.remove(i);
        }
    }

    public void deleteContact(int index, String txtPath)
    {
        contacts.remove(index);
        new File(txtPath).delete();

    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.layout_list_view_row_items, parent, false);
        }

        TextView textViewItemName = (TextView)
                convertView.findViewById(R.id.text_view_item_name);
        TextView textViewItemDescription = (TextView)
                convertView.findViewById(R.id.text_view_item_description);
        ImageView imageViewPhoto = (ImageView)
                convertView.findViewById(R.id.text_view_photo);

        textViewItemName.setText(contacts.get(position).getPrenom() + " " + contacts.get(position).getNom());
        textViewItemDescription.setText(contacts.get(position).getAdresse());
        imageViewPhoto.setImageURI(contacts.get(position).getPicture());

        // returns the view for the current row
        return convertView;
    }
}