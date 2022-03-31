package de.androidcrypto.androidcommonintents;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.HashSet;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class IntentGroup03 extends AppCompatActivity  {

    Button btn01, btn02, btn03, btn04,
            btn05, btn06, btn07, btn08;

    TextView tvG03;
    ListView lvG03;

    static final int REQUEST_SELECT_CONTACT = 1;
    static final int REQUEST_SELECT_PHONE_NUMBER = 2;
    static final int PICK_CONTACT_REQUEST = 3;

    static final int REQUEST_PERMISSION_READ_CONTACTS = 100;
    static final int REQUEST_PERMISSION_READ_CONTACTS_PHONE_NUMBER = 101;
    static final int REQUEST_PERMISSION_READ_CONTACTS_VIEW = 102;

    static final int REQUEST_PERMISSION_READ_CONTACTS_ALL_PHONE_NUMBERS = 108;

    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;

    static Uri contactUri = Uri.parse(""); // filled by 03

    // for updating/inserting
    private String mEmail;
    private String mRawContactId;
    private String mDataId;

    // list of phone numbers
    ArrayList<Contact> contactList = new ArrayList<>();
    private static final String[] PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

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
                System.out.println("01 readContact");
                //checking whether the read contact permission is granted.
                if (ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    // requesting to the user for permission.
                    ActivityCompat.requestPermissions(IntentGroup03.this, new String[]{Manifest.permission.READ_CONTACTS}, 100);
                } else {
                    //if app already has permission this block will execute.
                    //readContacts();
                    readContacts();
                }
            }
        });

        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("02 selectContactPhoneNumber");
                //checking whether the read contact permission is granted.
                if (ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    // requesting to the user for permission.
                    ActivityCompat.requestPermissions(IntentGroup03.this, new String[]{Manifest.permission.READ_CONTACTS}, 100);
                } else {
                    //if app already has permission this block will execute.
                    selectContactPhoneNumber();
                }
            }
        });

        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("03 readContact and view");
                //checking whether the read contact permission is granted.
                if (ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    // requesting to the user for permission.
                    ActivityCompat.requestPermissions(IntentGroup03.this, new String[]{Manifest.permission.READ_CONTACTS}, 100);
                } else {
                    //if app already has permission this block will execute.
                    //readContacts();
                    readContactsView();
                }
            }
        });

        btn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // insert a new contact
                // https://developer.android.com/training/contacts-provider/modify-data#InsertContact
                // Creates a new Intent to insert a contact
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                // Sets the MIME type to match the Contacts Provider
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                RandomString gen = new RandomString(5);
                String firstName = "fiNa" + gen.nextString();
                String lastName = "laNa" + gen.nextString();
                String completeName = firstName + " " + lastName;
                String emailAddress = gen.nextString() + "@test.de";
                String phoneNumber = "1234567890";

                /*
                 * Inserts new data into the Intent. This data is passed to the
                 * contacts app's Insert screen
                 */
                intent.putExtra(ContactsContract.Intents.Insert.NAME, completeName);
                // Inserts an email address
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, emailAddress)
                        /*
                         * In this example, sets the email type to be a work email.
                         * You can set other email types as necessary.
                         */
                        .putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE,
                                ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                        // Inserts a phone number
                        .putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber)
                        /*
                         * In this example, sets the phone type to be a work phone.
                         * You can set other phone types as necessary.
                         */
                        .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE,
                                ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
                        /* Sends the Intent
                        */
                        startActivity(intent);
            }
        });

        btn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // updating an contact

            }
        });

        btn06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // insert or edit
                // https://developer.android.com/training/contacts-provider/modify-data#InsertEdit
                // Creates a new Intent to insert or edit a contact
                Intent intentInsertEdit = new Intent(Intent.ACTION_INSERT_OR_EDIT);
                // Sets the MIME type
                intentInsertEdit.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
                // Add code here to insert extended data, if desired
                // ...
                // Sends the Intent with an request ID
                startActivity(intentInsertEdit);
            }
        });

        btn07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), PICK_CONTACT_REQUEST);
            }
        });

        btn08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("08 getContactArrayList");
                //checking whether the read contact permission is granted.
                if (ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    // requesting to the user for permission.
                    ActivityCompat.requestPermissions(IntentGroup03.this, new String[]{Manifest.permission.READ_CONTACTS}, 100);
                } else {
                    //if app already has permission this block will execute.
                    getContactArrayListView();
                }
            }
        });

        lvG03.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("### setOnItemClicked");
                System.out.println("position: " + position);
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
        if (requestCode == REQUEST_PERMISSION_READ_CONTACTS_PHONE_NUMBER) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectContactPhoneNumber();
            } else {
                Toast.makeText(this, "Read Contacts Permission is required to read contacts.", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == REQUEST_PERMISSION_READ_CONTACTS_VIEW) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readContactsView();
            } else {
                Toast.makeText(this, "Read Contacts Permission is required to read contacts.", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == REQUEST_PERMISSION_READ_CONTACTS_ALL_PHONE_NUMBERS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContactArrayListView();
            } else {
                Toast.makeText(this, "Read Contacts Permission is required to read contacts.", Toast.LENGTH_SHORT).show();
            }
        }
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
        //getContactList();
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
     /*   if (requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK) {
            System.out.println("### onActivityResult called for REQUEST_SELECT_CONTACT");
            Uri contactUri = data.getData();
            // Do something with the selected contact at contactUri
            System.out.println("contactUri:" + contactUri.toString());
            tvG03.setText(contactUri.toString());
        }*/
        if (requestCode == REQUEST_SELECT_PHONE_NUMBER && resultCode == RESULT_OK) {
            System.out.println("### onActivityResult called for REQUEST_SELECT_PHONE_NUMBER");
            // Get the URI and query the content provider for the phone number
            Uri contactUri = data.getData();
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = getContentResolver().query(contactUri, projection,
                    null, null, null);
            // If the cursor returned is valid, get the phone number
            if (cursor != null && cursor.moveToFirst()) {
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberIndex);
                // Do something with the phone number
                tvG03.setText("phone number: " + number);
            }
            if (requestCode == PICK_CONTACT_REQUEST && resultCode == RESULT_OK) {
                loadContactInfo(data.getData());
            }
        }

    }

    public void selectContactPhoneNumber() {
        // Start an activity for the user to pick a phone number from contacts
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_SELECT_PHONE_NUMBER);
        }
    }

    @SuppressLint("Range")
    public void readContactsView() {
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
        contactUri = ContactsContract.Contacts.CONTENT_URI;
        //getContactList();

    }

    // section edit a contact

    /**
     * Load contact information on a background thread.
     */
    private void loadContactInfo(Uri contactUri) {

        /*
         * We should always run database queries on a background thread. The database may be
         * locked by some process for a long time.  If we locked up the UI thread while waiting
         * for the query to come back, we might get an "Application Not Responding" dialog.
         */
        // solution: https://stackoverflow.com/a/46166223/8166854
        AsyncTask<Uri, Void, Boolean> task = new AsyncTask<Uri, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Uri... uris) {
                Log.v("Retreived ContactURI", uris[0].toString());

                return doesContactContainHomeEmail(uris[0]);
            }

            @Override
            protected void onPostExecute(Boolean exists) {
                if(exists)  {
                    Log.v("", "Updating...");
                    updateContact();
                }
                else {
                    Log.v("", "Inserting...");
                    insertEmailContact();
                }
            }
        };

        /* original
        AsyncTask<Uri, Void, Boolean> task = new AsyncTask<Uri, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Uri... uris) {
                Log.v("Retreived ContactURI", uris[0].toString());

                return doesContactContainHomeEmail(uris[0]);
            }

            @Override
            protected void onPostExecute(Boolean exists) {
                if(exists)  {
                    Log.v("", "Updating...");
                    updateContact();
                }
                else {
                    Log.v("", "Inserting...");
                    insertEmailContact();
                }
            }
        };
         */
        task.execute(contactUri);
    }

    private Boolean doesContactContainHomeEmail(Uri contactUri) {
        boolean returnValue = false;
        Cursor mContactCursor = getContentResolver().query(contactUri, null, null, null, null);
        Log.v("Contact", "Got Contact Cursor");

        try {
            if (mContactCursor.moveToFirst()) {
                String mContactId = getCursorString(mContactCursor,
                        ContactsContract.Contacts._ID);

                Cursor mRawContactCursor = getContentResolver().query(
                        ContactsContract.RawContacts.CONTENT_URI,
                        null,
                        Data.CONTACT_ID + " = ?",
                        new String[] {mContactId},
                        null);

                Log.v("RawContact", "Got RawContact Cursor");

                try {
                    ArrayList<String> mRawContactIds = new ArrayList<String>();
                    while(mRawContactCursor.moveToNext()) {
                        String rawId = getCursorString(mRawContactCursor, ContactsContract.RawContacts._ID);
                        Log.v("RawContact", "ID: " + rawId);
                        mRawContactIds.add(rawId);
                    }

                    for(String rawId : mRawContactIds) {
                        // Make sure the "last checked" RawContactId is set locally for use in insert & update.
                        mRawContactId = rawId;
                        Cursor mDataCursor = getContentResolver().query(
                                Data.CONTENT_URI,
                                null,
                                Data.RAW_CONTACT_ID + " = ? AND " + Data.MIMETYPE + " = ? AND " + Email.TYPE + " = ?",
                                new String[] { mRawContactId, Email.CONTENT_ITEM_TYPE, String.valueOf(Email.TYPE_HOME)},
                                null);

                        if(mDataCursor.getCount() > 0) {
                            mDataCursor.moveToFirst();
                            mDataId = getCursorString(mDataCursor, Data._ID);
                            Log.v("Data", "Found data item with MIMETYPE and EMAIL.TYPE");
                            mDataCursor.close();
                            returnValue = true;
                            break;
                        } else {
                            Log.v("Data", "Data doesn't contain MIMETYPE and EMAIL.TYPE");
                            mDataCursor.close();
                        }
                        returnValue = false;
                    }
                } finally {
                    mRawContactCursor.close();
                }
            }
        } catch(Exception e) {
            Log.w("UpdateContact", e.getMessage());
            for(StackTraceElement ste : e.getStackTrace()) {
                Log.w("UpdateContact", "\t" + ste.toString());
            }
            throw new RuntimeException();
        } finally {
            mContactCursor.close();
        }

        return returnValue;
    }

    private static String getCursorString(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        if(index != -1) return cursor.getString(index);
        return null;
    }

    public void insertEmailContact() {
        try {
            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

            ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
                    .withValue(Data.RAW_CONTACT_ID, mRawContactId)
                    .withValue(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
                    .withValue(Data.DATA1, mEmail)
                    .withValue(Email.TYPE, Email.TYPE_HOME)
                    .withValue(Email.DISPLAY_NAME, "Email")
                    .build());
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            tvG03.setText("inserted");
        } catch (Exception e) {
            // Display warning
            Log.w("UpdateContact", e.getMessage());
            for(StackTraceElement ste : e.getStackTrace()) {
                Log.w("UpdateContact", "\t" + ste.toString());
            }
            Context ctx = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(ctx, "Update failed", duration);
            e.printStackTrace();
            toast.show();
        }
    }


    public void updateContact() {
        try {
            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(ContactsContract.RawContacts.Data.RAW_CONTACT_ID + " = ?", new String[] {mRawContactId})
                    .withSelection(Data._ID + " = ?", new String[] {mDataId})
                    .withValue(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
                    .withValue(Data.DATA1, mEmail)
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_HOME)
                    .build());
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            tvG03.setText("Updated");
        } catch (Exception e) {
            // Display warning
            Log.w("UpdateContact", e.getMessage());
            for(StackTraceElement ste : e.getStackTrace()) {
                Log.w("UpdateContact", "\t" + ste.toString());
            }
            Context ctx = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(ctx, "Update failed", duration);
            toast.show();
        }
    }

    private void getContactArrayListView() {
        getContactArrayList();
        int contactListSize = contactList.size();
        System.out.println("contactListSize: " + contactListSize);
    }

    // get a list with name and phonee number
    private void getContactArrayList() {
        ContentResolver cr = getContentResolver();

        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor != null) {
            HashSet<String> mobileNoSet = new HashSet<String>();
            try {
                final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                String name, number;
                while (cursor.moveToNext()) {
                    name = cursor.getString(nameIndex);
                    number = cursor.getString(numberIndex);
                    number = number.replace(" ", "");
                    if (!mobileNoSet.contains(number)) {
                        contactList.add(new Contact(name, number));
                        mobileNoSet.add(number);
                        Log.d("hvy", "onCreaterView  Phone Number: name = " + name
                                + " No = " + number);
                    }
                }
            } finally {
                cursor.close();
            }
        }
    }


}