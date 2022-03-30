package de.androidcrypto.androidcommonintents;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class IntentGroup03 extends AppCompatActivity {

    Button btn01, btn02, btn03, btn04,
            btn05, btn06, btn07, btn08;

    TextView tvG03;
    ListView lvG03;

    static final int REQUEST_SELECT_CONTACT = 1;

    static final int REQUEST_PERMISSION_READ_CONTACTS = 100;

    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_group03);

        btn01 = findViewById(R.id.btnG03B01);
        btn02 = findViewById(R.id.btnG03B02);
        btn03 = findViewById(R.id.btnG03B03);
        btn04 = findViewById(R.id.btnG03B04);
        btn05 = findViewById(R.id.btnG03B05);
        btn06 = findViewById(R.id.btnG03B06);
        btn07 = findViewById(R.id.btnG03B07);
        btn08 = findViewById(R.id.btnG03B08);

        tvG03 = findViewById(R.id.tvG03);
        lvG03 = findViewById(R.id.lvG03);

        arrayList = new ArrayList<>(); //empty array list.
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        lvG03.setAdapter(arrayAdapter);

        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // https://developer.android.com/guide/components/intents-common#Contacts
                // https://developer.android.com/guide/topics/providers/contacts-provider

                //  <uses-permission android:name="android.permission.READ_CONTACTS" />
                System.out.println("01 selectContact");
                //checking whether the read contact permission is granted.
                if (ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    // requesting to the user for permission.
                    ActivityCompat.requestPermissions(IntentGroup03.this, new String[]{Manifest.permission.READ_CONTACTS}, 100);
                } else {
                    //if app already has permission this block will execute.
                    //readContacts();
                    readContacts();
                }
                //selectContact();
            }
        });

        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    // ### contacts

    // if the user clicks ALLOW in dialog this method gets called.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readContacts();
            } else {
                Toast.makeText(this, "Read Contacts Permission is required to read contacts.", Toast.LENGTH_SHORT).show();
            }
        }

        //readContacts();
        //selectContact();
    }

    @SuppressLint("Range")
    public void readContacts() {
        System.out.println("### readContacts called");
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String displayLine = "";
                displayLine = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                arrayList.add(displayLine);
            } while (cursor.moveToNext());
            arrayAdapter.notifyDataSetChanged();
        }
        // the following shows how to get some data
        getContactList();
    }

    @SuppressLint("Range")
    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        System.out.println("Name: " + name);
                        System.out.println("Phone Number: " + phoneNo);
                        String displayLine = "";
                        displayLine = name + " # " + phoneNo;
                        arrayList.add(displayLine);

                    }
                    pCur.close();
                }
            }
            arrayAdapter.notifyDataSetChanged();
        }
        if (cur != null) {
            cur.close();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK) {
            System.out.println("### onActivityResult called for REQUEST_SELECT_CONTACT");
            Uri contactUri = data.getData();
            // Do something with the selected contact at contactUri
            System.out.println("contactUri:" + contactUri.toString());
            tvG03.setText(contactUri.toString());
        }
    }

}